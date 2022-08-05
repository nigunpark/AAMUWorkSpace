from waitress import serve # waitress 서버
from flask import Flask, request  # 서버 구현을 위한 Flask 객체 import
from flask_restx import Api, Resource  # Api 구현을 위한 Api 객체 import

import logging
import json
import requests

app = Flask(__name__)  # Flask 객체 선언, 파라미터로 어플리케이션 패키지의 이름을 넣어줌.
api = Api(app)  # Flask 객체에 Api 객체 등록

logging.basicConfig(level=logging.DEBUG)

@api.route('/review', methods=["GET"])  # 데코레이터 이용, '/' 경로에 클래스 등록
class AamuReview(Resource):
    def get(self):  # GET 요청시 리턴 값에 해당 하는 dict를 JSON 형태로 반환

        map = request.args['map']

        basic_url = 'https://place.map.kakao.com/main/v/'+str(map)
        basic_res = requests.get(basic_url)
        basic_text = basic_res.text
        basic_data = json.loads(basic_text)

        comment_url = 'https://place.map.kakao.com/commentlist/v/'+str(map)
        comment_res = requests.get(comment_url)
        comment_text = comment_res.text
        comment_data = json.loads(comment_text)

        aamu_basic_info = basic_data['basicInfo']
        fullname = aamu_basic_info['placenamefull']
        try:
            score_avg = round(aamu_basic_info['feedback']['scoresum'] / aamu_basic_info['feedback']['scorecnt'], 1)
        except ZeroDivisionError:
            score_avg = 0
        score_sum = aamu_basic_info['feedback']['scoresum']
        score_cnt = aamu_basic_info['feedback']['scorecnt']

        aamu_basic = {
        'name' : fullname,
        'star': score_avg,
        'score' : score_sum,
        'feedback': score_cnt
        }

        if comment_data['comment']['kamapComntcnt'] == 0:
            
            aamu_none = None

            total_data = {
                'basic_info' : aamu_basic,
                'comment_info' : aamu_none
            }

            return total_data

        elif comment_data['comment']['kamapComntcnt'] <= 5:

            aamu_data = comment_data['comment']['list']
                
            aamu_comment=[] 
            for comment_content in aamu_data:

                try:
                    kakao_username = comment_content['username']
                except KeyError:
                    kakao_username = ""
                kakao_point = comment_content['point']
                try:
                    kakao_contents = comment_content['contents']
                except KeyError:
                    kakao_contents = ""

                aamu_data = {
                    'username': kakao_username,
                    'point': kakao_point,
                    'review': kakao_contents
                    }

                aamu_comment.append(aamu_data)

                total_data = {
                    'basic_info' : aamu_basic,
                    'comment_info' : aamu_comment
                }

            return total_data
                
        else:

            has_next = True
            last_commentid = ""
            aamu_comment=[]

            while comment_data['comment']['hasNext'] == has_next:

                comment_list_url = 'https://place.map.kakao.com/commentlist/v/'+str(map)+'/'+str(last_commentid)
                comment_list_res = requests.get(comment_list_url)
                comment_list_text = comment_list_res.text
                comment_list_data = json.loads(comment_list_text)

                comment_next_data = comment_list_data['comment']['list']
                comment_data = comment_list_data

                for comment_content in comment_next_data:

                    try:
                        kakao_username = comment_content['username']
                    except KeyError:
                        kakao_username = ""
                    kakao_point = comment_content['point']
                    try:
                        kakao_contents = comment_content['contents']
                    except KeyError:
                        kakao_contents = ""

                    aamu_data = {
                        'username': kakao_username,
                        'point': kakao_point,
                        'review': kakao_contents
                        }

                    aamu_comment.append(aamu_data)

                last_commentid = comment_next_data[-1]['commentid']

                total_data = {
                    'basic_info' : aamu_basic,
                    'comment_info' : aamu_comment
                }

            return total_data

@api.route('/mvtm', methods=["GET"])  # 데코레이터 이용, '/' 경로에 클래스 등록
class MovingTime(Resource):
    def get(self):  # GET 요청시 리턴 값에 해당 하는 dict를 JSON 형태로 반환

        firstx = request.args.get('firstx', default = 126.8786525, type = float)
        firsty = request.args.get('firsty', default = 37.4787503, type = float)
        secondx = request.args.get('secondx', default = 128.9109195, type = float)
        secondy = request.args.get('secondy', default = 37.8018357, type = float)

        headers = {"Authorization": "KakaoAK 18b03d1018f5fcf4158d54b7b1be4ba9"}
        jvtime_url = 'https://apis-navi.kakaomobility.com/v1/directions?origin={},{}&destination={},{}&waypoints=&priority=RECOMMEND&car_fuel=GASOLINE&car_hipass=false&alternatives=false&road_details=false'.format(firstx,firsty,secondx,secondy)
        jvtime_res = requests.get(jvtime_url, headers=headers)
        jvtime_data = jvtime_res.json()
        
        try:
            mvtime_data = jvtime_data['routes'][0]['sections'][0]['duration']
            
        except KeyError:
            mvtime_data = 0

        mvtime = {
            'MVTM' : mvtime_data
        }

        return mvtime

if __name__ == "__main__":
    serve(app, host = '0.0.0.0', port=5000)