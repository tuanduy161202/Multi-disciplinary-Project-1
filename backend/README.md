# Backend

## Setup

1. Go to `src/settings.py` and setup `USER`, `PASSWORD` in `DATABASES`
2. Execute scripts in terminal
```bash
python pip install -r requirements.txt
python manage.py migrate
```

## Usage

### Launch app

```bash
python manage.py runserver [PORT]
```

### Admin account
Execute: `python manage.py createsuperuser` to create database
```
username: admin
password: admin
```
Go to http://127.0.0.1:8000/admin/ to acess admin dashboard

## API documentation

### [Homepage]
```python
method='GET'
URL=''
```

### [ObtainAPIToken] 
```python
method='POST'
URL='/auth'
body= {
    username: 'duy',
    password: 'smarthomeuser'
}
```

### [HouseDetail]
```python
method='GET'
URL='/house/'
header= {
    Authorization: f'Token {yourtoken}'
}
```

### [DeviceList]
```python
method='GET'
URL='/house/device_list/'
header= {
    Authorization: f'Token {yourtoken}'
}
```

### [DeviceDetail]
*Only owner of device <int:pk> has permission to access this view.*
```python
method='GET'
URL='/house/device/<int:pk>/'
header= {
    Authorization: f'Token {yourtoken}'
}
```

### [SensorDataList]
```python
method='GET'
URL='/house/sensor_data_list/'
header= {
    Authorization: f'Token {yourtoken}'
}
```

### [IntentList]
```python
method='GET'
URL='/chatbot/intent_list/'
header= {
    Authorization: f'Token {yourtoken}'
}
```

### [IntentDetail]
*Only owner of intent <str:slug> has permission to access this view.*
```python
method='GET'
URL='/chatbot/intent/<str:slug>/'
header= {
    Authorization: f'Token {yourtoken}'
}
```

### [CommandList]
```python
method='GET'
URL='/chatbot/command_list/'
header= {
    Authorization: f'Token {yourtoken}'
}
```

### [CommandDetail]
*Only owner of command <int:pk> has permission to access this view.*
```python
method='GET'
URL='/chatbot/command/<int:pk>/'
header= {
    Authorization: f'Token {yourtoken}'
}
```

### [CommandCreate] - *unavailable*
```python
method='POST'
URL='/chatbot/command/create/'
header= {
    Authorization: f'Token {yourtoken}'
}
```