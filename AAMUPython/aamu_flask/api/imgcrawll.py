from flask_restful import Resource,reqparse
from flask import make_response,request
import time

from  selenium import webdriver
from  selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.service import Service
from selenium.common.exceptions import TimeoutException
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import requests
import os
import ast
class Imgcrawll(Resource):
    def post(self):
        parser = reqparse.RequestParser()
        parser.add_argument('placelist', action='append')
        parser = parser.parse_args()
        placelist = parser['placelist']
        list_ = []
        for i in placelist:
            list_.append(ast.literal_eval(i))
        index =0;
        for dict_ in list_:
            title = dict_.get('title')
            if title.find('(') != -1:
                title = title.split('(')[0]
            if title.find('[') != -1:
                title = title.split('[')[0]
            print(title)
            url = 'https://www.google.com/search?q=' + title + '&source=lnms&tbm=isch'
            selector = '#islrg > div.islrc > div:nth-child(2) > a.wXeWr.islib.nfEiy > div.bRMDJf.islir > img'

            try:
                options = webdriver.ChromeOptions()
                options.add_experimental_option("prefs", {"profile.default_content_setting_values.notifications": 1})
                driver_path = '{}{}chromedriver.exe'.format(os.path.dirname(os.path.realpath(__file__)), os.path.sep)
                options.add_argument('headless')
                options.add_argument(
                    'user-agent:Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.5060.114 Safari/537.36')
                service = Service(driver_path)
                driver = webdriver.Chrome(service=service,options=options)

                driver.get(url)
                driver.find_element(By.CSS_SELECTOR, selector).click()
                image = driver.find_element(By.CSS_SELECTOR,'#Sva75c > div > div > div > div > c-wiz > div > div > div > div > div > a > img')
                src = image.get_attribute('src') if image.get_attribute('src') else image.get_attribute('data-src')

                dir = '{}\\DESKTOP\\AAMUIMG\\'.format(os.path.expanduser('~'))
                if not os.path.isdir(dir):
                    os.makedirs(dir)
                file = '{}hotel{}{}.jpg'.format(dir,index,title)
                print(src)
                #if src != None and (src.find('https') == 0 or src.find('http') == 0):
                #    # 이미지로 저장
                #    res = requests.get(src)
                #    print('들어몸')
                #    with open(file, 'wb') as f:
                #        f.write(res.content)

                #    index += 1
            except Exception as e:
                print(e)


