from flask_restful import Resource,reqparse
from flask import jsonify,request,make_response

class WebHook(Resource):
    def post(self):
        dialog_response = request.get_json()
        query = dialog_response['queryResult']['queryText']
        parameters = dialog_response['queryResult']['parameters']
        loc = parameters['loc']
        place = parameters['place']
        print('진행하자')
        if place.find('박')==-1:
            print('숙소나 여행지')
            return jsonify({'fulfillmentText': '{} {}searchPlace'.format(loc, parameters['place'])})
        else:
            print('경로추천')
            return jsonify({'fulfillmentText': '{} {}searchRoute'.format(loc, parameters['place'])})
        #returnStr = '/porum/'+rbn
