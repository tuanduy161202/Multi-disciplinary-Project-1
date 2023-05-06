import os
import requests as re
from asgiref.sync import async_to_sync
from channels.layers import get_channel_layer

from django.shortcuts import render
from django.http import JsonResponse

from rest_framework.response import Response
from rest_framework import generics, mixins, permissions, authentication
from rest_framework.decorators import api_view

from .models import Intent, Command
from .serializers import IntentSerializer, CommandSerializer
from .permissions import IsCommandOwner, IsIntentOwner
from adafruit import aio


# Chat
@api_view(['POST'])
def chat(request, *args, **kwargs):
    def broadcast_status(device, status):
        async_to_sync(get_channel_layer().group_send)(
            'room',
            {
                'type': 'send_status',
                'device': device,
                'status': 'on' if status == 1 else 'off'
            }
        )

    try:
        prompt = request.POST.get('prompt','')
        # print(f"veryimport: {prompt}")
        rasa_host = os.environ.get('RASA_HOST')
        
        intent_endpoint = f'{rasa_host}model/parse'
        payload = {
            "text": prompt
        }
        intent = re.post(url=intent_endpoint, json=payload).json().get('intent',{}).get('name','nlu_fallback')

        # control device and broacast status
        if 'turn' in intent:
            status = 1 if 'on' in intent else 0
            if 'light' in intent:
                broadcast_status('light', status)
                # aio.send('led1', status)
            elif 'fan' in intent:
                broadcast_status('fan', status)
                # aio.send('fan', status)
            elif 'water' in intent:
                broadcast_status('watering', status)
                # aio.send('pump', status)
            elif 'curtain' in intent:
                broadcast_status('curtain', status)
                # aio.send('hang-clothe', status)
            

        text_endpoint = f'{rasa_host}webhooks/rest/webhook'
        payload = {
            "message": prompt
        }
        reponse = re.post(url=text_endpoint, json=payload).json()
        if len(reponse) > 0:
            message = reponse[0].get('text')
        else:
            message = 'Tôi không hiểu'
            
    except:
        message = 'Xin vui lòng thử lại'
    return Response({
        'message': message
    })



# Intent
class IntentListAPIView(generics.ListAPIView):
    queryset = Intent.objects.all()
    serializer_class = IntentSerializer
    authentication_classes = [authentication.TokenAuthentication, authentication.SessionAuthentication]
    permission_classes = [permissions.IsAuthenticated]

    def get_queryset(self):
        queryset = super().get_queryset()

        if self.request.user.house is None:
            return queryset.none()

        house = self.request.user.house
        devices = house.devices.all()
        return queryset.filter(device__in=devices)
    
intent_list_view = IntentListAPIView.as_view()


class IntentDetailAPIView(generics.RetrieveAPIView):
    queryset = Intent.objects.all()
    serializer_class = IntentSerializer
    authentication_classes = [authentication.TokenAuthentication, authentication.SessionAuthentication]
    permission_classes = [permissions.IsAuthenticated, IsIntentOwner]

intent_detail_view = IntentDetailAPIView.as_view()


# Command
class CommandListAPIView(generics.ListAPIView):
    queryset = Command.objects.all()
    serializer_class = CommandSerializer
    authentication_classes = [authentication.TokenAuthentication, authentication.SessionAuthentication]
    permission_classes = [permissions.IsAuthenticated]

    def get_queryset(self):
        queryset = super().get_queryset()
        house_id = self.request.user.house.house_id if self.request.user.house else None
        return queryset.filter(house_id=house_id)
    
command_list_view = CommandListAPIView.as_view()


class CommandDetailAPIView(generics.RetrieveAPIView):
    queryset = Command.objects.all()
    serializer_class = CommandSerializer
    authentication_classes = [authentication.TokenAuthentication, authentication.SessionAuthentication]
    permission_classes = [permissions.IsAuthenticated,IsCommandOwner]

command_detail_view = CommandDetailAPIView.as_view()