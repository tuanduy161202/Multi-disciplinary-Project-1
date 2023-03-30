from rest_framework import serializers

from .models import House, Device, SensorData, Watering


class SensorDataSerializer(serializers.ModelSerializer):
    class Meta:
        model = SensorData
        fields = ['date', 'temperature', 'humidity']


class WateringSerializer(serializers.ModelSerializer):
    class Meta:
        model = Watering
        fields = ['start_time', 'duration']


class DeviceSerializer(serializers.ModelSerializer):
    url = serializers.HyperlinkedIdentityField(
        view_name='houses:device_detail', lookup_field='pk')
    extra_info = serializers.SerializerMethodField()
        
    class Meta:
        model = Device
        fields = ['url', 'device_id', 'name', 'is_active', 'device_type', 'extra_info']
    
    def get_extra_info(self, obj):
        if obj.device_type != 'Watering':
            return {}
        return WateringSerializer(obj.get_child(),context=self.context).data


class HouseSerializer(serializers.ModelSerializer):
    # device_listing = serializers.HyperlinkedIdentityField(
    #     view_name='houses:device_list')
    # sensor_data_listing = serializers.HyperlinkedIdentityField(
    #     view_name='houses:sensor_data_list', lookup_field='pk')
    # intent_listing = serializers.HyperlinkedIdentityField(
    #     view_name='chatbot:intent_list', lookup_field='pk')
    # command_listing = serializers.HyperlinkedIdentityField(
    #     view_name='chatbot:command_list', lookup_field='pk')

    class Meta:
        model = House
        fields = '__all__' # ['house_id','io_username','io_key']
        # read_only_fields = ['house_id']