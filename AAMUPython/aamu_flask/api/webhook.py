from flask_restful import Resource,reqparse
from flask import jsonify,request,make_response

class WebHook(Resource):
    def post(self):
        dialog_response = request.get_json()
        query = dialog_response['queryResult']['queryText']
        parameters = dialog_response['queryResult']['parameters']
        if parameters.get('schedule') != None:
            return jsonify({'fulfillmentText': '{} {}searchRoute'.format(parameters['loc'], parameters['schedule'])})
        elif parameters.get('ROUTE') != None:
            return jsonify({'fulfillmentText': 'recommendRoute'})
        elif parameters.get('place') != None:
            return jsonify({'fulfillmentText': '{} {}searchPlace'.format(parameters['loc'], parameters['place'])})



