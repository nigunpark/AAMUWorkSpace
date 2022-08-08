from flask_restful import Resource,reqparse
from flask import make_response,request
from datetime import datetime
import os
import json
import csv
import ast



class SavePlaces(Resource):
    def post(self):
        parser = reqparse.RequestParser()

        parser.add_argument('placelist', action='append')
        parser = parser.parse_args()
        placelist = parser['placelist']
        list_ = []
        for i in placelist:
            list_.append(ast.literal_eval(i))
        dir = '{}\\DESKTOP\\AAMUCSV\\'.format(os.path.expanduser('~'))
        date = datetime.now()

        if not os.path.isdir(dir):
            os.makedirs(dir)
        file = '{}{}_{}_{}{}.csv'.format(dir,list_[0].get('areacode'),list_[0].get('contenttypeid'),date.hour,date.minute)

        with open(file,'w',encoding='utf-8-sig',newline='') as f:
            writer = csv.DictWriter(f, fieldnames=['areacode', 'sigungucode', 'contentid', 'contenttypeid', 'mapx', 'mapy', 'title', 'addr', 'image'])
            writer.writeheader()
            writer.writerows(list_)
