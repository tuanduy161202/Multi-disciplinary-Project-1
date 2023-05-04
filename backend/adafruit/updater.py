import os
from datetime import datetime

from apscheduler.schedulers.background import BackgroundScheduler
from asgiref.sync import async_to_sync
from channels.layers import get_channel_layer

from adafruit import aio
from houses.models import SensorData, House


def update_sensor_data():
    delay_time = int(os.environ.get('QUERY_SENSOR_DELAY'))
    channel_layer = get_channel_layer()

    def _get_data():
        temp_data = aio.receive('temp')
        humid_data = aio.receive('humid')
        date = datetime.strptime(temp_data.created_at, "%Y-%m-%dT%H:%M:%SZ")
        return date, temp_data.value, humid_data.value

    def _update_sensor_data():
        date, temp_data, humid_data = _get_data()
        print(date, temp_data, humid_data)

        async_to_sync(channel_layer.group_send)(
            'room', # room_group_name ????
            {
                'type': 'send_data',
                'temp_data': temp_data,
                'humid_data': humid_data
            }
        )

        if not SensorData.objects.filter(date=date).exists():
            SensorData(date=date, humidity=humid_data, 
                house=House.objects.get(pk=1), temperature=temp_data).save() # house ???
        

    scheduler = BackgroundScheduler({'apscheduler.job_defaults.max_instances': 2})
    scheduler.add_job(_update_sensor_data, 'interval', seconds=delay_time)
    scheduler.start()