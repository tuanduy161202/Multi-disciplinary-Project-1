from django.db import models

DEVICE_TYPE_KEY_MAPPING = {
    'Watering' : 'watering_child',
    'Light' : 'light_child',
    'Fan' : 'fan_child',
    'Curtain' : 'curtain_child'
}


class House(models.Model):
    class Meta:
        managed = False
        db_table = 'house'

    house_id = models.AutoField(primary_key=True)
    io_username = models.CharField(max_length=255)
    io_key = models.CharField(max_length=255)

    def __str__(self):
        return f'{self.house_id}'


class Device(models.Model):
    class Meta:
        managed = False
        db_table = 'device'
    device_id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=255)
    is_active = models.BooleanField(default=False)
    house = models.ForeignKey(House, on_delete=models.CASCADE,
        db_column='house_id', related_name='devices')

    @property
    def device_type(self):
        for typ, att in DEVICE_TYPE_KEY_MAPPING.items():
            if hasattr(self, att): return typ
        return None

    def get_child(self):
        if self.device_type == None:
            return None
        return getattr(self, DEVICE_TYPE_KEY_MAPPING[self.device_type])

    def __str__(self):
        return f'{self.name} - {self.device_type}'


class Watering(Device):
    class Meta:
        managed = False
        db_table = 'watering'
    c_id = models.OneToOneField(Device, on_delete=models.CASCADE, 
        primary_key=True, db_column="watering_id", parent_link=True,
        related_name=DEVICE_TYPE_KEY_MAPPING['Watering'])
    start_time = models.DateTimeField()
    duration = models.IntegerField()


class Light(Device):
    class Meta:
        managed = False
        db_table = 'light'
    c_id = models.OneToOneField(Device, on_delete=models.CASCADE, 
        primary_key=True, db_column="light_id", parent_link=True,
        related_name=DEVICE_TYPE_KEY_MAPPING['Light'])


class Fan(Device):
    class Meta:
        managed = False
        db_table = 'fan'
    c_id = models.OneToOneField(Device, on_delete=models.CASCADE, 
        primary_key=True, db_column="fan_id", parent_link=True,
        related_name=DEVICE_TYPE_KEY_MAPPING['Fan'])


class Curtain(Device):
    class Meta:
        managed = False
        db_table = 'curtain'
    c_id = models.OneToOneField(Device, on_delete=models.CASCADE, 
        primary_key=True, db_column="curtain_id", parent_link=True,
        related_name=DEVICE_TYPE_KEY_MAPPING['Curtain'])


class SensorData(models.Model):
    class Meta:
        managed = False
        db_table = 'sensor_data'
    date = models.DateTimeField(primary_key=True)
    house = models.ForeignKey(House, on_delete=models.CASCADE,
        db_column='house_id',related_name='datum')
    temperature = models.FloatField()
    humidity = models.FloatField()
    
    def __str__(self):
        return f'{self.date}'