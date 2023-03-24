from django.db import models

from houses.models import Device, House

class Intent(models.Model):
    class Meta:
        managed = False
        db_table = 'intent'
    intent_name = models.CharField(max_length=255, primary_key=True)
    action = models.CharField(max_length=255)
    device = models.ForeignKey(Device, on_delete=models.CASCADE,
        db_column='device_id', related_name='intents')
    
    def __str__(self):
        return f'{self.intent_name}'


class Command(models.Model):
    class Meta:
        managed = False
        db_table = 'command'
    command_id = models.AutoField(primary_key=True)
    text = models.TextField()
    house = models.ForeignKey(House, on_delete=models.CASCADE,
        db_column='house_id', related_name='commands')
    intent = models.ForeignKey(Intent, on_delete=models.CASCADE,
        db_column='intent_name', related_name='commands')

    def __str__(self):
        return f'{self.command_id}'