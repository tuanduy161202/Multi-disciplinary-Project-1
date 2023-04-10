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
            'temp_data': '-1',
            'humid_data': '-2'
        }))


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