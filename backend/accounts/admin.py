from django.contrib import admin
from django.contrib.auth.admin import UserAdmin

from .forms import AccountCreationForm, AccountChangeForm
from .models import Account


class AccountAdmin(UserAdmin):
    add_form = AccountCreationForm
    form = AccountChangeForm
    model = Account
    list_display = ["username", "email"] 
    fieldsets = (
        (None, {'fields': ('username', "email", 'password', 'date_joined', 'house')}),
        ('Permissions', {'fields': ('is_staff', 'is_active')}),
    )
    search_fields = ('username',)
    ordering = ('username',)

admin.site.register(Account, AccountAdmin)