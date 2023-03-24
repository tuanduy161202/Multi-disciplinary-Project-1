from django.shortcuts import render

from rest_framework.response import Response
from rest_framework.decorators import api_view


@api_view(['GET'])
def api_home(request, *args, **kwargs):
    return Response({
        'message': 'Welcome to API Response'
    })