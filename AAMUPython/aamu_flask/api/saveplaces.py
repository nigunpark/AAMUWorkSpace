from flask_restful import Resource,reqparse
from flask import make_response,request
import os
import json
import csv

class SavePlaces(Resource):
    def post(self):
        parser = reqparse.RequestParser()

        parser.add_argument('placelist', action='append')
        parser = parser.parse_args()
        placelist = parser['placelist']
        print(json.loads(placelist[0]))
        windowPath=os.path.expanduser('~')
'''
        with open('{}{}.csv'.format(),'w+',encoding='utf-8') as f:
            f.write()
'''