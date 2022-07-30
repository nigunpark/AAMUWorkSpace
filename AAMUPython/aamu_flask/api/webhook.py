from flask_restful import Resource,reqparse
from flask import jsonify,request,make_response

class WebHook(Resource):
    def post(self):
        dialog_response = request.get_json()
        query = dialog_response['queryResult']['queryText']
        parameters = dialog_response['queryResult']['parameters']
        place = parameters['loc']
        when = parameters['schedule']

        print(place)
        print(when)
        #returnStr = '/porum/'+rbn
        return jsonify({'fulfillmentText':'{} {}searchRoute'.format(place,when)})