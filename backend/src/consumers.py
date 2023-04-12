import json
from channels.generic.websocket import WebsocketConsumer
from asgiref.sync import async_to_sync

class Consumer(WebsocketConsumer):
    def connect(self):
        self.room_group_name = 'room'

        async_to_sync(self.channel_layer.group_add)(
            self.room_group_name,
            self.channel_name
        )

        self.accept()

        self.send(text_data= json.dumps({
            'type': 'connection_established',
            'temp_data': -1,
            'humid_data': -2
        }))
        #TODO: code lay status cua thiet bi ban dau khi mo ung dung
        light_status = 'on'     #vidu: on
        fan_status = 'on'
        watering_status = 'off'
        curtain_status = 'on'

        self.broadcast_status('light', light_status)
        self.broadcast_status('fan', fan_status)
        self.broadcast_status('watering', watering_status)
        self.broadcast_status('curtain', curtain_status)


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

    def broadcast(self, temp_data, humid_data):
        async_to_sync(self.channel_layer.group_send)(
            self.room_group_name,
            {
                'type': 'send_data',
                'temp_data': temp_data,
                'humid_data': humid_data
            }
        )

    def send_data(self, event):
        temp_data = event['temp_data']
        humid_data = event['humid_data']

        self.send(text_data= json.dumps({
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