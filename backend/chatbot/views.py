from django.shortcuts import render
from django.http import JsonResponse

from rest_framework.response import Response
from rest_framework import generics, mixins, permissions

from .models import Intent, Command
from .serializers import IntentSerializer, CommandSerializer

# Intent
class IntentListAPIView(generics.ListAPIView):
    queryset = Intent.objects.all()
    serializer_class = IntentSerializer
    # permission_classes = [permissions.IsAuthenticated]

    def get(self, request, pk, *args, **kwargs):
        queryset = self.filter_queryset(self.get_queryset())
        # queryset = queryset.filter(house_id=pk)

        page = self.paginate_queryset(queryset)
        if page is not None:
            serializer = self.get_serializer(page, many=True)
            return self.get_paginated_response(serializer.data)

        serializer = self.get_serializer(queryset, many=True)
        return Response(serializer.data)
    

intent_list_view = IntentListAPIView.as_view()


class IntentDetailAPIView(generics.RetrieveAPIView):
    queryset = Intent.objects.all()
    serializer_class = IntentSerializer
    # permission_classes = [permissions.IsAuthenticated]

intent_detail_view = IntentDetailAPIView.as_view()


# Command
class CommandListAPIView(generics.ListAPIView):
    queryset = Command.objects.all()
    serializer_class = CommandSerializer
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
    
command_list_view = CommandListAPIView.as_view()


class CommandDetailAPIView(generics.RetrieveAPIView):
    queryset = Command.objects.all()
    serializer_class = CommandSerializer
    # permission_classes = [permissions.IsAuthenticated]

command_detail_view = CommandDetailAPIView.as_view()