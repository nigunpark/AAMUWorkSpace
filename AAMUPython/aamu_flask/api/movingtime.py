from flask import Flask, request  # 서버 구현을 위한 Flask 객체 import
from flask_restful import Resource,reqparse
#from flask_restx import Api, Resource  # Api 구현을 위한 Api 객체 import

import logging
import json
import requests


class MovingTime(Resource):
    def get(self):  # GET 요청시 리턴 값에 해당 하는 dict를 JSON 형태로 반환

        firstx = request.args.get('firstx', default=126.8786525, type=float)
        firsty = request.args.get('firsty', default=37.4787503, type=float)
        secondx = request.args.get('secondx', default=128.9109195, type=float)
        secondy = request.args.get('secondy', default=37.8018357, type=float)

        headers = {"Authorization": "KakaoAK 18b03d1018f5fcf4158d54b7b1be4ba9"}
        jvtime_url = 'https://apis-navi.kakaomobility.com/v1/directions?origin={},{}&destination={},{}&waypoints=&priority=RECOMMEND&car_fuel=GASOLINE&car_hipass=false&alternatives=false&road_details=false'.format(
            firstx, firsty, secondx, secondy)
        jvtime_res = requests.get(jvtime_url, headers=headers)
        jvtime_data = jvtime_res.json()

        try:
            mvtime_data = jvtime_data['routes'][0]['sections'][0]['duration']

        except KeyError:
            mvtime_data = 0

        mvtime = {
            'MVTM': mvtime_data
        }

        return mvtime