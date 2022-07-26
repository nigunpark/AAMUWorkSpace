from waitress import serve
from flask import Flask, request

import logging
import json
import math
import requests

app = Flask(__name__)

logging.basicConfig(level=logging.DEBUG)
log = logging.getLogger('werkzeug')
log.setLevel(logging.ERROR)


@app.route("/review", methods=["GET"])
def review():
    map = request.args['map']

    basic_url = 'https://place.map.kakao.com/main/v/' + str(map)
    basic_res = requests.get(basic_url)
    basic_text = basic_res.text
    basic_data = json.loads(basic_text)

    comment_url = 'https://place.map.kakao.com/commentlist/v/' + str(map)
    comment_res = requests.get(comment_url)
    comment_text = comment_res.text
    comment_data = json.loads(comment_text)

    if comment_data['comment']['kamapComntcnt'] != 0:

        page_num = math.ceil(comment_data['comment']['kamapComntcnt'] / 5)

        try:

            kakao_comment = []
            for page_list in range(page_num):
                comment_list_url = 'https://place.map.kakao.com/commentlist/v/' + str(map) + '/' + f'{page_list + 1}'
                comment_list_res = requests.get(comment_list_url)
                comment_list_text = comment_list_res.text
                comment_list_data = json.loads(comment_list_text)
                kakao_comment.append(comment_list_data)

        except KeyError:
            ''

    aamu_basic_info = basic_data['basicInfo']

    fullname = aamu_basic_info['placenamefull']
    try:
        score_avg = round(aamu_basic_info['feedback']['scoresum'] / aamu_basic_info['feedback']['scorecnt'], 1)
    except ZeroDivisionError:
        score_avg = 0
    score_sum = aamu_basic_info['feedback']['scoresum']
    score_cnt = aamu_basic_info['feedback']['scorecnt']

    aamu_basic = {
        'name': fullname,
        'star': score_avg,
        'score': score_sum,
        'feedback': score_cnt
    }

    if comment_data['comment']['kamapComntcnt'] == 0:

        aamu_none = None

        total_data = {
            'basic_info': aamu_basic,
            'comment_info': aamu_none
        }

        return json.dumps(total_data, ensure_ascii=False), 200, {'Content-Type': 'application/json'}

    else:

        aamu_comment = []
        for comment_list in range(page_num):
            aamu_data = kakao_comment[comment_list]['comment']['list']

            for comment_content in aamu_data:

                try:
                    kakao_username = comment_content['username']
                    kakao_point = comment_content['point']
                    kakao_contents = comment_content['contents']

                except KeyError:
                    ''

                aamu_data = {
                    'username': kakao_username,
                    'point': kakao_point,
                    'review': kakao_contents
                }

                aamu_comment.append(aamu_data)

                total_data = {
                    'basic_info': aamu_basic,
                    'comment_info': aamu_comment
                }

        return json.dumps(total_data, ensure_ascii=False), 200, {'Content-Type': 'application/json'}


@app.route("/mvtm", methods=["GET"])
def mvtm():
    firstx = request.args.get('firstx', default=126.8786525, type=float)
    firsty = request.args.get('firsty', default=37.4787503, type=float)
    secondx = request.args.get('secondx', default=128.9109195, type=float)
    secondy = request.args.get('secondy', default=37.8018357, type=float)

    headers = {"Authorization": "KakaoAK 18b03d1018f5fcf4158d54b7b1be4ba9"}
    jvtime_url = 'https://apis-navi.kakaomobility.com/v1/directions?origin={},{}&destination={},{}&waypoints=&priority=RECOMMEND&car_fuel=GASOLINE&car_hipass=false&alternatives=false&road_details=false'.format(
        firstx, firsty, secondx, secondy)
    jvtime_res = requests.get(jvtime_url, headers=headers)
    jvtime_data = jvtime_res.json()

    try:
        mvtime_data = jvtime_data['routes'][0]['sections'][0]['duration']

    except KeyError:
        mvtime_data = 0

    mvtime = {
        'MVTM': mvtime_data
    }

    return json.dumps(mvtime, ensure_ascii=False), 200, {'Content-Type': 'application/json'}


if __name__ == '__main__':
    serve(app, host='0.0.0.0', port=5000)