from django.db import models
from django.contrib.auth.models import AbstractBaseUser, PermissionsMixin, BaseUserManager
from django.utils import timezone

from houses.models import House


class AccountManager(BaseUserManager):
    def create_user(self, username, password, **extra_fields):
        if not username:
            raise ValueError(_('The username must be set'))
        user = self.model(username=username, **extra_fields)
        user.set_password(password)
        user.save()
        return user

    def create_superuser(self, username, password, **extra_fields):
        extra_fields.setdefault('is_staff', True)
        extra_fields.setdefault('is_superuser', True)
        extra_fields.setdefault('is_active', True)
        extra_fields.setdefault('last_login', '2023-01-01 00:00:00.000000')

        if extra_fields.get('is_staff') is not True:
            raise ValueError(_('Superuser must have is_staff=True.'))
        if extra_fields.get('is_superuser') is not True:
            raise ValueError(_('Superuser must have is_superuser=True.'))
        return self.create_user(username, password, **extra_fields)


class Account(AbstractBaseUser, PermissionsMixin):
    class Meta:
        managed = False
        db_table = 'account'

    username = models.CharField(max_length=255, unique=True)
    email = models.EmailField()
    name = models.CharField(max_length=255)
    house = models.ForeignKey(House, on_delete=models.CASCADE,
        db_column='house_id',related_name='accounts',null=True,blank=True)
        
    is_staff = models.BooleanField(default=False)
    is_active = models.BooleanField(default=True)
    date_joined = models.DateTimeField(default=timezone.now)
    
    objects = AccountManager()

    USERNAME_FIELD = 'username'

    def __str__(self):
        return self.username