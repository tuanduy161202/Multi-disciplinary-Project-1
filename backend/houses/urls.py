from django.urls import path, include

from . import views

app_name = 'houses'
urlpatterns = [
    path('',views.house_detail_view,name='house_detail'),

    path('device_list/',views.device_list_view,name='device_list'),
    path('device/<int:pk>/',views.device_detail_view,name='device_detail'),

    path('sensor_data_list/',views.sensor_data_list_view,name='sensor_data_list'),
]