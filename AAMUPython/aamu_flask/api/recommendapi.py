from flask_restful import Resource,reqparse
from flask import jsonify
from api.recommend import RecommendAlgorithm
from flask import jsonify,request,make_response
import csv
class RecommendApi(Resource):
    def post(self):
        parser = reqparse.RequestParser()
        parser.add_argument('id')
        args = parser.parse_args()

        # 1. 객체 생성
        id = args['id']
        routebbsList = []
        ratereviewList=[]
        for items in request.get_json()['routebbs']:
            for item in items:
                dict_ = dict(zip(['rbn','title'],[item['rbn'],item['title']]))
                routebbsList.append(dict_)

        for items in request.get_json()['ratereview']:
            for item in items:
                dict_ = dict(zip(['id', 'rbn','rate'], [item['id'],item['rbn'], item['rate']]))
                ratereviewList.append(dict_)

        with open('./review/ratereview.csv', 'w', newline='') as f:
            writer = csv.DictWriter(f, fieldnames=['id', 'rbn', 'rate'])
            writer.writerows(ratereviewList)
        with open('./review/routebbs.csv', 'w', encoding='utf-8', newline='') as f:
            writer = csv.DictWriter(f, fieldnames=['rbn', 'title'])
            writer.writerows(routebbsList)

        recommend = RecommendAlgorithm((1, 5), './review/ratereview.csv', './review/routebbs.csv',
                                       itemColumn=['itemId', 'item_name'])

        # 2. 모델 생성 및 훈련 그리고 예측및 평가
        recommend.makeModel()

        # 확인용
        # 확인용 첫번째-사용자 아이디가 100인 사람이 평점을 준 아이템들의 목록(리스트)
        itemIds = recommend.getRatingItems(id)
        # 확인용 두번째 -사용자 아이디가 100인 사람이 1005번인 아이템에 대한 평점이 있는지 판단하기
        #if not recommend.isRating('KIM', 1003):
        #    print('아이디 100인 사람은 음식 1003에 대한 평점이 없음')
        # 3. 사용자 아이디가 100인 사람이 평점을 매기지 않은 아이템들(리스트)
        noRatingItems = recommend.getNoRatingItem(id)
        # 4.사용자 아이디가 100인 사람에게 평점을 하지 않은 아이템들 중에서 모델이 예측한 평점이
        #  높은 아이템들을 추천하기
        # topItemPreds=recommend.recommendItems(100, noRatingItems)#디폴트 10개
        topItemPreds = recommend.recommendItems(id, noRatingItems, 5)  # 5개 추천하기
        list_=[]
        for item in topItemPreds:

            list_.append(dict({'rbn': item[0]}))
        return dict({'rbns':list_})