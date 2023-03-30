from django.shortcuts import render
from django.urls import reverse
from django.http import HttpRequest

from rest_framework.response import Response
from rest_framework.decorators import api_view


@api_view(['GET'])
def api_home(request, *args, **kwargs):
    house_url = request.build_absolute_uri(reverse('houses:house_detail'))
    return Response({
        'message': 'Welcome to API Response',
        'house': house_url
    })