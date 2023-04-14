import json
from time import sleep

from channels.generic.websocket import WebsocketConsumer, SyncConsumer
from asgiref.sync import async_to_sync
from adafruit import aio


def bin2status(b):
    return 'off' if b == 0 else 'on'


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
                #TODO: code thay doi status va bat den
                self.broadcast_status('light', 'on')
                pass
            elif device == 'fan':
                #TODO: code thay doi status va bat quat
                self.broadcast_status('fan', 'on')
                pass
            elif device == 'watering':
                #TODO: code thay doi status va bat tuoi cay
                self.broadcast_status('watering', 'on')
                pass
            elif device == 'curtain':
                #TODO: code thay doi status va bat keo rem
                self.broadcast_status('curtain', 'on')
                pass

        elif mess_type == 'turn_off':
            device = text_data_json['device']
            if device == 'light':
                #TODO: code thay doi status va tat den
                self.broadcast_status('light', 'off')
            elif device == 'fan':
                #TODO: code thay doi status va tat quat
                self.broadcast_status('fan', 'off')
                pass
            elif device == 'watering':
                #TODO: code thay doi status va tat tuoi cay
                self.broadcast_status('watering', 'off')
                pass
            elif device == 'curtain':
                #TODO: code thay doi status va tat keo rem
                self.broadcast_status('curtain', 'off')
                pass
        
        elif mess_type == 'set_timer':
            minute = text_data_json['minute']
            second = text_data_json['second']
            #TODO: code set thoi gian watering do user thay doi 
    
    def init_status(self):
        self.send_status({'temp_data': aio.receive('temp'), 
                          'humid_data': aio.receive('humid')})
        self.send_data({'device': 'light', 
                        'status': bin2status(aio.receive('light'))})
        self.send_data({'device': 'fan', 
                        'status': bin2status(aio.receive('fan'))})
        self.send_data({'device': 'watering', 
                        'status': bin2status(aio.receive('watering'))})
        self.send_data({'device': 'curtain', 
                        'status': bin2status(aio.receive('curtain'))})

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