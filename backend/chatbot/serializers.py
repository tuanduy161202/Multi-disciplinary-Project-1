from rest_framework import serializers

from .models import Command, Intent
from houses.serializers import DeviceSerializer
from houses.models import Device


class IntentSerializer(serializers.ModelSerializer):
    url = serializers.HyperlinkedIdentityField(
        view_name='chatbot:intent_detail', lookup_field='pk')
    device = serializers.HyperlinkedRelatedField(
        queryset=Device.objects.all(),
        view_name='houses:device_detail', lookup_field='pk')

    class Meta:
        model = Intent
        fields = ['url', 'intent_name', 'action', 'device']


class CommandSerializer(serializers.ModelSerializer):
    url = serializers.HyperlinkedIdentityField(
        view_name='chatbot:command_detail', lookup_field='pk')
    intent = serializers.HyperlinkedRelatedField(
        queryset=Intent.objects.all(),
        view_name='chatbot:intent_detail', lookup_field='pk')

    class Meta:
        model = Command
        fields = ['url', 'command_id', 'text', 'intent']