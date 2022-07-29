from flask_restful import Resource,reqparse
from flask import jsonify
import google.cloud.dialogflow_v2 as dialogflow
from settings.config import DIALOG_CONFIG

class DialogMessage(Resource):

    def post(self):
        parser = reqparse.RequestParser()
        parser.add_argument('message')
        parser.add_argument('id')

        args = parser.parse_args()

        project_id=DIALOG_CONFIG['PROJECT_ID']

        session_client = dialogflow.SessionsClient()
        session_path = session_client.session_path(project_id, args['id'])

        message = args['message']
        # session_path()함수는 projects/프로젝트아이디/agent/sessions/세션아이디 로 생성해서 반환한다
        if message:  # 사용자가 대화를 입력한 경우.대화는 utf-8로 인코딩된 자연어.256자를 넘어서는 안된다
            # step2.사용자 메시지(일반 텍스트)로 TextInput생성
            text_input = dialogflow.types.TextInput(text=message, language_code='ko')

            # step 3. 생성된 TextInput객체로 QueryInput객체 생성(DialogFlow로 전송할 질의 생성)
            query_input = dialogflow.types.QueryInput(text=text_input)
            print(query_input)

            # step 4. DialogFlow로 SessionsClient객체.detect_intent()메소드로
            #        QueryInput객체를 보내고 다시 봇 응답(Responses섹션에 등록한 대화)을 받는다
            #        즉 A DetectIntentResponse instance
            response = session_client.detect_intent(session=session_path, query_input=query_input)


        # 다이얼로그 플로우에서 보낸 응답을 스프링으로 반환
        return jsonify({'message': response.query_result.fulfillment_text})
