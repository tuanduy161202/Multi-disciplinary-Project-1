import os
from Adafruit_IO import Client

aio = Client(
    username=os.environ.get('AIO_USERNAME'),
    key=os.environ.get('AIO_KEY')
)