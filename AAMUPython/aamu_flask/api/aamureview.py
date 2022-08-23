from flask import Flask, request  # 서버 구현을 위한 Flask 객체 import
from flask_restful import Resource,reqparse

import logging
import json
import requests

class AamuReview(Resource):
    def get(self):  # GET 요청시 리턴 값에 해당 하는 dict를 JSON 형태로 반환

        map = request.args['map']

        basic_url = 'https://place.map.kakao.com/main/v/' + str(map)
        basic_res = requests.get(basic_url)
        basic_text = basic_res.text
        basic_data = json.loads(basic_text)

        comment_url = 'https://place.map.kakao.com/commentlist/v/' + str(map)
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

            return total_data

        elif comment_data['comment']['kamapComntcnt'] <= 5:

            aamu_data = comment_data['comment']['list']

            aamu_comment = []
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
                    'basic_info': aamu_basic,
                    'comment_info': aamu_comment
                }

            return total_data

        else:

            has_next = True
            last_commentid = ""
            aamu_comment = []

            while comment_data['comment']['hasNext'] == has_next:

                comment_list_url = 'https://place.map.kakao.com/commentlist/v/' + str(map) + '/' + str(last_commentid)
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
                    'basic_info': aamu_basic,
                    'comment_info': aamu_comment
                }

            return total_data