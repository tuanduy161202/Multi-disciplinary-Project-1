from django.urls import path, include

from . import views

app_name = 'chatbot'
urlpatterns = [
    path('chat/',views.chat,name='chat'),

    path('intent_list/',views.intent_list_view,name='intent_list'),
    path('intent/<str:pk>/',views.intent_detail_view,name='intent_detail'),

    path('command_list/',views.command_list_view,name='command_list'),
    path('command/<int:pk>/',views.command_detail_view,name='command_detail'),
]