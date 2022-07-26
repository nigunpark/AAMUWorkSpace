from flask_restful import Resource,reqparse
from flask import jsonify,request,make_response

class WebHook(Resource):
    def post(self):
        dialog_response = request.get_json()
        query = dialog_response['queryResult']['queryText']
        parameters = dialog_response['queryResult']['parameters']
        print('여행지 얘기 안함:',not parameters['location'])
        print('출발일 얘기 안함:', not parameters['date-time'])

        if parameters:
            if not parameters['location']:
                return jsonify({'fulfillmentText':'여행 목적지를 말씀해 주셔야죠'})
            if not parameters['date-time']:
                return jsonify({'fulfillmentText': '출발 일자를 말씀해 주셔야죠'})

        place = parameters['location']
        when = parameters['date-time']
        if place=='제주도':
            place
        return jsonify({'fulfillmentText':'{}날짜에 {}목적지로 예약 진행할게'.format(when,place)})