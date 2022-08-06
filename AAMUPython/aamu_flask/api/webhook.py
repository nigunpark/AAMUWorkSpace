from flask_restful import Resource,reqparse
from flask import jsonify,request,make_response

class WebHook(Resource):
    def post(self):
        dialog_response = request.get_json()
        query = dialog_response['queryResult']['queryText']
        parameters = dialog_response['queryResult']['parameters']
        if parameters.get('ROUTE') != None:
            print('경로 추천')
            return jsonify({'fulfillmentText': 'searchRoute'})
        if parameters.get('place') != None:
            print('지역 추천')
            return jsonify({'fulfillmentText': 'searchPlace'})