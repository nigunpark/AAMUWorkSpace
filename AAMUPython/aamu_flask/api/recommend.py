from surprise import SVD
from surprise import accuracy
from surprise import Dataset,Reader
from surprise.model_selection import train_test_split
import pandas as pd


#ratings.csv는 userId,itemId,rating
#items.csv는 itemId,title,genre로 ,title,genre는 아이템에따라 달라질수 있다

class RecommendAlgorithm:
    '''
    rating_scale : (최저평정,최고평점) 예:1부터 5까지 별점을 주는 경우 (1,5)
    ratingCsv:userId,itemId,rating 데이타가 있는 csv파일
    itemCsv : 첫번째 컬럼은 반드시 itemId,컬럼은 두개 이상인 csv파일
    itemColumn : itemCsv파일의 헤더명(컬럼명).타입은 list
    ratingColumn : ratingCsv파일의 헤더명(컬럼명).타입은 list. 디폴트는 ['userId','itemId','rating']
    '''
    def __init__(self,rating_scale,ratingCsv,itemCsv,itemColumn,ratingColumn=['userId','itemId','rating']):
        self.reader = Reader(line_format='user item rating',sep=',',rating_scale=rating_scale)

        data = Dataset.load_from_file(ratingCsv,reader=self.reader)

        self.train,self.test = train_test_split(data,test_size=0.2)

        self.items = pd.read_csv(itemCsv, names=itemColumn)

        self.ratings = pd.read_csv(ratingCsv, names=ratingColumn)

        self.model=None
    #모델 생성부터 평가까지 시작
    #모델 생성
    def createModel(self):self.model = SVD()
    #훈련
    def fit(self):self.model.fit(self.train)
    #예측
    def predict(self)->object:return self.model.test(self.test)
    #평가
    def evaluate(self,predictions):
        print('모델의 정확도:',end='')
        accuracy.rmse(predictions)

    # 모델 생성부터 평가까지 끝

    #위의 4개의 메소드를 한번에 실행
    def makeModel(self):
        self.createModel()
        self.fit()
        predictions=self.predict()
        self.evaluate(predictions)




    '''
    userId가 평점을 매긴 itemId들만 DataFrame로 반환
    userId : 사용자 아이디   
    '''
    def getRatingItems(self,userId):
        itemIds = pd.DataFrame(self.ratings[self.ratings.iloc[:, 0] == userId].iloc[:, 1])
        return itemIds


    '''
    userId가  특정 아이템에 대한 평점 부여 여부 확인
    userId : 사용자 아이디 
    itemId : 아이템 아이디    
    '''
    def isRating(self,userId,itemId):
        itemIds=self.getRatingItems(userId)
        if not itemIds[itemIds.iloc[:, 0] == itemId].empty:
           return False
        return True


    '''
    특정 아이템에 대한 아이템 정보 출력    
    itemId : 아이템 아이디   
    '''
    def showItem(self,itemId):
        return self.items[self.items.iloc[:, 0] == itemId]

    '''
    특정 유저가 평점을 주지 않은 아이템들을 리스트로 저장    
    userId : 사용자 아이디 
    '''
    def getNoRatingItem(self,userId):
        # 특정 유저가 평점을 준 아이템들을 리스트로 저장
        ratingItems = self.ratings[self.ratings.iloc[:, 0] == userId].iloc[:, 1].tolist()
        totalItems = self.items.iloc[:, 0].tolist()

        noRatingItems = [item for item in totalItems if item not in ratingItems]
        print('{}인 유저가 평점을 준 아이템 수:{},평점을 안준 아이템 수:{},전체 아이템 수:{}'.format(userId, len(ratingItems), len(noRatingItems),
                                                                      len(totalItems)))

        return noRatingItems
    '''
    예측 평점이 높은 최상위 n_top개의 추천 아이템 리스트로 반환
    userId : 사용자 아이디     
    noRatingItems : getNoRatingItem()함수에서 반환된 리스트
    n_top : 추천 상위 갯수.디폴트 10개
    '''
    def recommendItems(self,userId,noRatingItems, n_top=10):
        # userId가 평점을 안준  아이템에 대한  평점 예측
        predictions = [self.model.predict(str(userId), str(itemId)) for itemId in noRatingItems]
        # 예측 평점을 정렬
        predictions.sort(key=lambda pred: pred.est, reverse=True)
        # 상위 n_top개의 예측값들
        topPredictions = predictions[:n_top]

        # 상위 n_top개의 예측값들에서 itemId,rating,title 추출
        topitemIds = [int(pred.iid) for pred in topPredictions]
        topItemRatings = [pred.est for pred in topPredictions]


        topItemTitles = self.items[self.items.iloc[:,0].isin(topitemIds)].iloc[:, 1]

        return [(ids, rating, title) for ids, rating, title in zip(topitemIds, topItemRatings, topItemTitles)]


if __name__ == '__main__':
    #1. 객체 생성
    recommend = RecommendAlgorithm((0.5, 5),'./rating/rating.csv', './rating/movie/movie.csv',itemColumn=['movieId', 'title','genre'])

    #2. 모델 생성 및 훈련 그리고 예측및 평가
    recommend.makeModel()

    #확인용
    #확인용 첫번째-사용자 아이디가 100인 사람이 평점을 준 아이템들의 목록(리스트)
    itemIds=recommend.getRatingItems(100)
    #확인용 두번째 -사용자 아이디가 100인 사람이 1005번인 아이템에 대한 평점이 있는지 판단하기
    if not recommend.isRating(100,1005):
        print('아이디 100인 사람은 영화 1005에 대한 평점이 없음')
    #3. 사용자 아이디가 100인 사람이 평점을 매기지 않은 아이템들(리스트)
    noRatingItems=recommend.getNoRatingItem(100)
    #4.사용자 아이디가 100인 사람에게 평점을 하지 않은 아이템들 중에서 모델이 예측한 평점이
    #  높은 아이템들을 추천하기
    #topItemPreds=recommend.recommendItems(100, noRatingItems)#디폴트 10개
    topItemPreds=recommend.recommendItems(100,noRatingItems,5)#5개 추천하기

    for item in topItemPreds:
        print('추천 아이템:{} 예측평점:{}'.format(item[2],item[1]))

