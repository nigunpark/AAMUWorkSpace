from requests import request
from flask_restful import Resource,reqparse
from flask import jsonify,make_response,request

import ast
import os,random,json#표준 라이브러리
import requests

from wordcloud import WordCloud
import matplotlib.pyplot as plt
from collections import Counter
from konlpy.tag import Okt
from PIL import Image
import numpy as np

class Word(Resource):
    def get(self):
        # 스프링에서 보낸 파라미터 받기
        allTnamesOfWomanStr = request.args['allTnamesOfWomanStr']
        allTnamesOfManStr = request.args['allTnamesOfManStr']
        print(allTnamesOfWomanStr)  # 서울
        print(allTnamesOfManStr)  # 서울
        allTnamesOfManStrList=allTnamesOfManStr.split(',')

        womanWord = Counter(allTnamesOfWomanStr) # 단어별 빈도수 형태의 딕셔너리 데이터를 구함
        manWord = Counter(allTnamesOfManStrList)
        print(manWord)
        wc = WordCloud(font_path='malgun', width=400, height=400, scale=2.0, max_font_size=250, background_color="white")
        womanGen=wc.generate_from_frequencies(womanWord)
        manGen = wc.generate_from_frequencies(manWord)
        fig=plt.figure(figsize=(10, 10))
        plt.imshow(womanGen)
        plt.axis("off")
        fig.savefig("wordCloud2.png")
        #plt.imshow(manGen)
        #plt.axis("off")
        #fig.savefig("wordCloud.png")




