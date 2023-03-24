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