from flask_restful import Resource,reqparse
from flask import jsonify

from api.recommend import RecommendAlgorithm

class RecommendApi(Resource):
    def post(self):
        parser = reqparse.RequestParser()
        parser.add_argument('id')

        args = parser.parse_args()
        # 1. 객체 생성
        id = args['id']
        recommend = RecommendAlgorithm((1, 5), './rating/test_rating.csv', './rating/movie/test_food.csv',
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
        topItemPreds = recommend.recommendItems(id, noRatingItems, 3)  # 5개 추천하기

        for item in topItemPreds:
            print('추천 아이템:{} 예측 평점:{}'.format(item[2], item[1]))
