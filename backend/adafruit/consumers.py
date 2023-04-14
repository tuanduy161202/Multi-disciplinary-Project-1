import json
from time import sleep

from channels.generic.websocket import WebsocketConsumer, SyncConsumer
from asgiref.sync import async_to_sync
from adafruit import aio


def bin2status(b):
    return 'off' if b == '0' else 'on'


class UIConsumer(WebsocketConsumer):
    def connect(self):
        self.room_group_name = 'room'

        async_to_sync(self.channel_layer.group_add)(
            self.room_group_name,
            self.channel_name
        )

        self.accept()
        self.init_status()

    def disconnect(self, code):
        async_to_sync(self.channel_layer.group_discard)(
            self.room_group_name, self.channel_name
        )

    def receive(self, text_data):
        print(f'Mess type: {text_data}')
        text_data_json = json.loads(text_data)
        mess_type = text_data_json['type']
        if mess_type == 'turn_on':
            device = text_data_json['device']
            if device == 'light':
                aio.send('led1', 1)
                self.broadcast_status('light', 'on')
            elif device == 'fan':
                aio.send('fan', 1)
                self.broadcast_status('fan', 'on')
            elif device == 'watering':
                aio.send('pump', 1)
                self.broadcast_status('watering', 'on')
            elif device == 'curtain':
                aio.send('hang-clothe', 1)
                self.broadcast_status('curtain', 'on')

        elif mess_type == 'turn_off':
            device = text_data_json['device']
            if device == 'light':
                aio.send('led1', 0)
                self.broadcast_status('light', 'off')
            elif device == 'fan':
                aio.send('fan', 0)
                self.broadcast_status('fan', 'off')
            elif device == 'watering':
                aio.send('pump', 0)
                self.broadcast_status('watering', 'off')
            elif device == 'curtain':
                aio.send('hang-clothe', 0)
                self.broadcast_status('curtain', 'off')
        
        elif mess_type == 'set_timer':
            minute = text_data_json['minute']
            second = text_data_json['second']
            #TODO: code set thoi gian watering do user thay doi 
    
    def init_status(self):
        self.send_data({'temp_data': aio.receive('temp').value, 
                          'humid_data': aio.receive('humid').value})
        self.send_status({'device': 'light', 
                        'status': bin2status(aio.receive('led1').value)})
        self.send_status({'device': 'fan', 
                        'status': bin2status(aio.receive('fan').value)})
        self.send_status({'device': 'watering', 
                        'status': bin2status(aio.receive('pump').value)})
        self.send_status({'device': 'curtain', 
                        'status': bin2status(aio.receive('hang-clothe').value)})

    def send_data(self, event):
        temp_data = event['temp_data']
        humid_data = event['humid_data']

        self.send(text_data=json.dumps({
            'type': 'sensor_data',
            'temp_data': temp_data,
            'humid_data': humid_data
        }))

    def broadcast_status(self, device, status):
        async_to_sync(self.channel_layer.group_send)(
            self.room_group_name,
            {
                'type': 'send_status',
                'device': device,
                'status': status
            }
        )

    def send_status(self, event):
        device = event['device']
        status = event['status']

        self.send(text_data=json.dumps({
            'type': 'update_status',
            'device': device,
            'status': status
        }))