from django.shortcuts import render
from django.http import JsonResponse

from rest_framework.response import Response
from rest_framework import generics, mixins, permissions, authentication

from .models import Intent, Command
from .serializers import IntentSerializer, CommandSerializer
from .permissions import IsCommandOwner, IsIntentOwner

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