from django.shortcuts import render
from django.http import JsonResponse

from rest_framework.response import Response
from rest_framework import generics, mixins, permissions, authentication
from rest_framework.generics import get_object_or_404

from .models import House, Device, SensorData
from .serializers import HouseSerializer, DeviceSerializer, SensorDataSerializer
from .permissions import IsDeviceOwner


# House
class HouseDetailAPIView(generics.RetrieveAPIView):
    queryset = House.objects.all()
    serializer_class = HouseSerializer
    authentication_classes = [authentication.TokenAuthentication, authentication.SessionAuthentication]
    permission_classes = [permissions.IsAuthenticated]

    def get_object(self):
        queryset = self.filter_queryset(self.get_queryset())
        house_id = self.request.user.house.house_id if self.request.user.house else None
        obj = get_object_or_404(queryset, pk=house_id)
        self.check_object_permissions(self.request, obj)
        return obj

house_detail_view = HouseDetailAPIView.as_view()


# Device
class DeviceListAPIView(generics.ListAPIView):
    queryset = Device.objects.all()
    serializer_class = DeviceSerializer
    authentication_classes = [authentication.TokenAuthentication, authentication.SessionAuthentication]
    permission_classes = [permissions.IsAuthenticated]

    def get_queryset(self):
        queryset = super().get_queryset()
        house_id = self.request.user.house.house_id if self.request.user.house else None
        return queryset.filter(house_id=house_id)

device_list_view = DeviceListAPIView.as_view()


class DeviceDetailAPIView(generics.RetrieveAPIView):
    queryset = Device.objects.all()
    serializer_class = DeviceSerializer
    authentication_classes = [authentication.TokenAuthentication, authentication.SessionAuthentication]
    permission_classes = [permissions.IsAuthenticated, IsDeviceOwner]

device_detail_view = DeviceDetailAPIView.as_view()


#  Sensor Data
class SensorDataListAPIView(generics.ListAPIView):
    queryset = SensorData.objects.all()
    serializer_class = SensorDataSerializer
    authentication_classes = [authentication.TokenAuthentication, authentication.SessionAuthentication]
    permission_classes = [permissions.IsAuthenticated]

    def get_queryset(self):
        queryset = super().get_queryset()
        house_id = self.request.user.house.house_id if self.request.user.house else None
        return queryset.filter(house_id=house_id)
    
sensor_data_list_view = SensorDataListAPIView.as_view()


