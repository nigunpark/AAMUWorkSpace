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
        print('allTnamesOfWomanStr:',allTnamesOfWomanStr)  # 서울
        print('allTnamesOfManStr',allTnamesOfManStr)  # 서울
        allTnamesOfManStrList=allTnamesOfManStr.split(',')
        allTnamesOfWomanList = allTnamesOfWomanStr.split(',')

        womanWord = Counter(allTnamesOfWomanList) # 단어별 빈도수 형태의 딕셔너리 데이터를 구함
        manWord = Counter(allTnamesOfManStrList)
        print(womanWord)
        print(manWord)

        wc = WordCloud(font_path='malgun', width=1000, height=800, scale=2.0, max_font_size=250, background_color="white")
        womanGen=wc.generate_from_frequencies(womanWord)
        fig=plt.figure(figsize=(10, 8))
        plt.imshow(womanGen)
        plt.tight_layout(pad=0)
        plt.axis("off")
        #fig.savefig("D:\KKH\Workspace\AAMUWorkSpace\AamuAdminProj\src\main\webapp\\resources\images\woman.png")
        fig.savefig("D:\LWJ\Workspace\AAMUWorkSpace\AamuAdminProj\src\main\webapp\\resources\images\woman.png")

        wc2 = WordCloud(font_path='malgun', width=1000, height=800, scale=2.0, max_font_size=250, background_color="white")
        manGen = wc2.generate_from_frequencies(manWord)
        fig2 = plt.figure(figsize=(10, 8))
        plt.imshow(manGen)
        plt.tight_layout(pad=0)
        plt.axis("off")
        #fig2.savefig("D:\KKH\Workspace\AAMUWorkSpace\AamuAdminProj\src\main\webapp\\resources\images\man.png")
        fig2.savefig("D:\LWJ\Workspace\AAMUWorkSpace\AamuAdminProj\src\main\webapp\\resources\images\man.png")





