from django.shortcuts import render
from django.http import JsonResponse

from rest_framework.response import Response
from rest_framework import generics, mixins, permissions

from .models import House, Device, SensorData
from .serializers import HouseSerializer, DeviceSerializer, SensorDataSerializer


# House
class HouseDetailAPIView(generics.RetrieveAPIView):
    queryset = House.objects.all()
    serializer_class = HouseSerializer
    # permission_classes = [permissions.IsAuthenticated]

house_detail_view = HouseDetailAPIView.as_view()


# Device
class DeviceListAPIView(generics.ListAPIView):
    queryset = Device.objects.all()
    serializer_class = DeviceSerializer
    # permission_classes = [permissions.IsAuthenticated]

    def get(self, request, pk, *args, **kwargs):
        queryset = self.filter_queryset(self.get_queryset())
        queryset = queryset.filter(house_id=pk)

        page = self.paginate_queryset(queryset)
        if page is not None:
            serializer = self.get_serializer(page, many=True)
            return self.get_paginated_response(serializer.data)

        serializer = self.get_serializer(queryset, many=True)
        return Response(serializer.data)
    

device_list_view = DeviceListAPIView.as_view()


class DeviceDetailAPIView(generics.RetrieveAPIView):
    queryset = Device.objects.all()
    serializer_class = DeviceSerializer
    # permission_classes = [permissions.IsAuthenticated]

device_detail_view = DeviceDetailAPIView.as_view()


#  Sensor Data
class SensorDataListAPIView(generics.ListAPIView):
    queryset = SensorData.objects.all()
    serializer_class = SensorDataSerializer
    # permission_classes = [permissions.IsAuthenticated]

    def get(self, request, pk, *args, **kwargs):
        queryset = self.filter_queryset(self.get_queryset())
        queryset = queryset.filter(house_id=pk)

        page = self.paginate_queryset(queryset)
        if page is not None:
            serializer = self.get_serializer(page, many=True)
            return self.get_paginated_response(serializer.data)

        serializer = self.get_serializer(queryset, many=True)
        return Response(serializer.data)
    
sensor_data_list_view = SensorDataListAPIView.as_view()


