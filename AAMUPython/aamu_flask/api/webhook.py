from flask_restful import Resource,reqparse
from flask import jsonify,request,make_response

class WebHook(Resource):
    def post(self):
        dialog_response = request.get_json()
        query = dialog_response['queryResult']['queryText']
        parameters = dialog_response['queryResult']['parameters']
        queryResult = dialog_response['queryResult']
        if queryResult.get('action') != None and dialog_response['queryResult']['action'] == 'ROUTEINTENT.ROUTEINTENT-no':
            return jsonify({'fulfillmentText': 'recommendRoute'})
        elif parameters.get('place') != None:
            return jsonify({'fulfillmentText': '{} {}searchPlace'.format(parameters['loc'], parameters['place'])})
        elif parameters.get('loc') != None and parameters.get('schedule') == None:
            return jsonify({'fulfillmentText': '{}searchRoute'.format(parameters['loc'])})
        elif parameters.get('loc') == None and parameters.get('schedule') != None:
            return jsonify({'fulfillmentText': '{}searchRoute'.format(parameters['schedule'])})
        elif parameters.get('loc') != None and parameters.get('schedule') != None:
            return jsonify({'fulfillmentText': '{} {}searchRoute'.format(parameters['loc'],parameters['schedule'])})




