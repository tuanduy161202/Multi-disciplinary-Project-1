from django.apps import AppConfig


class AdafruitConfig(AppConfig):
    default_auto_field = 'django.db.models.BigAutoField'
    name = 'adafruit'

    def ready(self):
        from .updater import update_sensor_data
        update_sensor_data()