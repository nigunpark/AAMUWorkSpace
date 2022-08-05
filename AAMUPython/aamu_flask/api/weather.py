from requests import request
from selenium import webdriver
#키보드 키값이 정의된 클래스
from selenium.webdriver.common.keys import Keys
#위치 지정자(셀렉터)를 위한 클래스
from selenium.webdriver.common.by import By
#웹드라이버 생성을 위한 서비스 클래스
from selenium.webdriver.chrome.service import Service
#Explicit Wait시 지정한 시간동안 요소를 못찾을때 발생하는 예외
from selenium.common.exceptions import TimeoutException
import os,random,json#표준 라이브러리

import requests
from bs4 import BeautifulSoup

from flask_restful import Resource,reqparse
from flask import jsonify,make_response,request

import ast

class Weather(Resource):
    def get(self):
        try:
            options = webdriver.ChromeOptions()
            # 1.웹드라이버 객체 생성
            driver_path = '{}{}chromedriver.exe'.format(os.path.dirname(os.path.realpath(__file__)), os.path.sep)
            options.add_argument('headless')
            service = Service(driver_path)
            driver = webdriver.Chrome(service=service, options=options)

            #스프링에서 보낸 파라미터 받기
            searchWord = request.args['searchWord']
            print(searchWord) #서울
            sDates = request.args['searchDate'] #2022.8.04,2022.8.05 (문자열)
            print(sDates)
            searchDate=sDates.split(",") #[2022.8.04,2022.8.05]
            print(searchDate)

            # 스프링에서 보낸 requestBody 받기
            #parser = reqparse.RequestParser()8
            #parser.add_argument('searchWord')
            #parser.add_argument('searchDate')
            #args = parser.parse_args()
            #searchWord=args['searchWord']
            #searchDate = args['searchDate'] #리스트


            # 2.사이트 요청 즉 브라우저로 사이트 로딩
            driver.get(
                'https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query={}+날씨'.format(searchWord))
            url = "https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query={}+날씨'.format(searchWord)"

            # 3. 요소(태그) 찾기:WebDriver의 find_element계열 메소드의 반환타입은 WebElement
            from selenium.webdriver.common.by import By

            list_ = []
            for i in range(0, 10):
                date = driver.find_elements(By.CSS_SELECTOR,
                                            'div.list_box._weekly_weather > ul > li > div > div.cell_date > span > span')[i].text[:4]
                lowTemp = driver.find_elements(By.CSS_SELECTOR,
                                               'div.list_box._weekly_weather > ul > li > div > div.cell_temperature > span > span.lowest')[i].text[5:8]
                highTemp = driver.find_elements(By.CSS_SELECTOR,
                                                'div.list_box._weekly_weather > ul > li > div > div.cell_temperature > span > span.highest')[i].text[5:8]
                weatherState = driver.find_elements(By.CSS_SELECTOR,
                                                    'div.list_box._weekly_weather > ul > li > div > div.cell_weather > span:nth-child(1) > i > span')[i].text


                realDate = '2022.' + date
                for sDate in searchDate:
                    if sDate == realDate:
                        weather = dict(zip(['date', 'lowTemp', 'highTemp', 'weatherState'], [realDate, lowTemp, highTemp, weatherState]))  # [,,] weahter라는 키로
                        list_.append(weather)
                        print(list_)
            return jsonify({'weather':list_})
            # weathers={'weathers':weather}

            # print('[날씨]')
            # req = requests.get(url)
            # soup=BeautifulSoup(req.text, "html.parser")
            # weather = soup.find_all('i',{'class':'wt_icon ico_wt9'})
            # print(weather)

        except TimeoutException as e:  # implicitly_wait 는 TimeoutException예외가 발생하지 않는다
            print('지정한 요소를 찾을 수 없어요')

        finally:
            # 브라우저 닫기
            driver.quit()












