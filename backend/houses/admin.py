from django.contrib import admin

from .models import House, Device, SensorData, Watering, Light, Fan, Curtain

class DeviceInline(admin.StackedInline):
    model = Device
    extra = 0

class HouseAdmin(admin.ModelAdmin):
    inlines = [DeviceInline,]

admin.site.register(House,HouseAdmin)
admin.site.register(Device)
admin.site.register(SensorData)
admin.site.register(Watering)
admin.site.register(Light)
admin.site.register(Fan)
admin.site.register(Curtain)