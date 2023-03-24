from django.contrib import admin

from .models import Intent, Command


admin.site.register(Intent)
admin.site.register(Command)