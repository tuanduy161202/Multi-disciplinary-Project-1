#!/usr/bin/env python
"""Django's command-line utility for administrative tasks."""
import os
import sys


def main():
    """Run administrative tasks."""
    os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'src.settings')
    os.environ.setdefault('AIO_USERNAME', 'halac123b')
    os.environ.setdefault('AIO_KEY', 'aio_QOwQ45wWCSdhoiF7Ztw2jBkg7d7W')
    os.environ.setdefault('QUERY_SENSOR_DELAY', '2')
    os.environ.setdefault("RASA_HOST", 'http://localhost:5005/')

    try:
        from django.core.management import execute_from_command_line
    except ImportError as exc:
        raise ImportError(
            "Couldn't import Django. Are you sure it's installed and "
            "available on your PYTHONPATH environment variable? Did you "
            "forget to activate a virtual environment?"
        ) from exc
    execute_from_command_line(sys.argv)


if __name__ == '__main__':
    main()
