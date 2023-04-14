from django.urls import re_path
from adafruit.consumers import UIConsumer

websocket_urlpatterns = [
    re_path(r'ws/socket-server/', UIConsumer.as_asgi()),
]