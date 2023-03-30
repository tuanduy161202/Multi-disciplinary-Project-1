from django.urls import path, include

from . import views

app_name = 'houses'
urlpatterns = [
    # path('<int:pk>/',views.house_detail_view,name='house_detail'),
    path('',views.house_detail_view,name='house_detail'),

    path('<int:pk>/device_list/',views.device_list_view,name='device_list'),
    path('device/<int:pk>/',views.device_detail_view,name='device_detail'),

    path('<int:pk>/sensor_data_list/',views.sensor_data_list_view,name='sensor_data_list'),
]