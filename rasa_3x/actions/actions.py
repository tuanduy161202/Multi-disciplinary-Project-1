# This files contains your custom actions which can be used to run
# custom Python code.
#
# See this guide on how to implement these action:
# https://rasa.com/docs/rasa/custom-actions


# This is a simple example for a custom action which utters "Hello World!"

from typing import Any, Text, Dict, List

from rasa_sdk import Action, Tracker
from rasa_sdk.executor import CollectingDispatcher
from googletrans import Translator
import requests as re
# class ActionHelloWorld(Action):

#     def name(self) -> Text:
#         return "action_hello_world"

#     def run(self, dispatcher: CollectingDispatcher,
#             tracker: Tracker,
#             domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

#         dispatcher.utter_message(text="Hello World!")

#         return []
class ActionWeatherForecast(Action):

    def name(self) -> Text:
        return "action_weather_forecast"

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        message = tracker.latest_message['intent'].get('name')
        if message == 'weather_forecast':
            translate = Translator()
            city_vi = tracker.get_slot('city')
            city_en = translate.translate(city_vi).text
            print(city_en)
            data = re.get(f"http://api.weatherapi.com/v1/forecast.json?key=f5ae91f09eab42ea8d332001230504&q={city_en}&days=5&aqi=no&alerts=no&lang=vi").json()
            forecast_text = f'Dự báo thời tiết tại {city_vi}:\n'
            for forecast in data['forecast']['forecastday']:
              date = forecast['date']
              avgtemp = forecast['day']['avgtemp_c']
              avghumidity = forecast['day']['avghumidity']
              condition = forecast['day']['condition']['text']
              text = f"- Ngày {date}:\n\t Nhiệt độ trung bình: {avgtemp}.\n\t Độ ẩm trung bình: {avghumidity}.\n\t Dự đoán: {condition}.\n"
              forecast_text += text
            dispatcher.utter_message(forecast_text)
class ActionControlDevice(Action):

    def name(self) -> Text:
        return "action_control_device"

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        message = tracker.latest_message['intent'].get('name')
        if 'turn_' in message:
            on_off = 'tắt'
            if '_on_' in message:
                on_off = 'bật'
            device_name = ''
            if '_light' in message:
                device_name = 'đèn'
            elif '_fan' in message:
                device_name = 'quạt'
            elif '_water' in message:
                device_name = 'vòi nước'
            elif '_curtain' in message:
                device_name = 'rèm cửa'
                on_off = 'kéo' if on_off == 'bật' else 'thả'
            text = f'Xác nhận {on_off} {device_name}'
            dispatcher.utter_message(text + '.')
