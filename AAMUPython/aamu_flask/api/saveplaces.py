from flask_restful import Resource,reqparse
from flask import make_response,request
import json

class SavePlaces(Resource):
    def post(self):
        parser = reqparse.RequestParser()

        parser.add_argument('area', action='append')
        parser = parser.parse_args()
        area = parser['area']

        print(area)