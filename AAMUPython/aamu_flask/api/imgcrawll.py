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
from base64 import b64decode
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
        index = 1
        if placelist != None:
            for i in placelist:
                list_.append(ast.literal_eval(i))
            for dict_ in list_:
                title = dict_.get('title')
                contentid = dict_.get('contentid')
                if title.find('(') != -1:
                    if title.split('(')[0]:
                        title = title.split('(')[0]
                if title.find('[') != -1:
                    if title.split('[')[0]:
                        title = title.split('[')[0]
                print(title)
                print(type(dict_.get('contenttypeid')))
                contenttypeid = dict_.get('contenttypeid')
                print(type(contenttypeid))
                print(contenttypeid)
                print(contenttypeid == '32')
                if contenttypeid == '32':
                    title = '숙소 '+title
                elif contenttypeid == '39':
                    title = '음식점 '+title
                elif contenttypeid == '12':
                    title = '관광지 ' + title
                url = 'https://www.google.com/search?q=' + title + '&source=lnms&tbm=isch'
                selector = '#islrg > div.islrc > div:nth-child(2) > a.wXeWr.islib.nfEiy > div.bRMDJf.islir > img'

                try:
                    options = webdriver.ChromeOptions()
                    driver_path = '{}{}chromedriver.exe'.format(os.path.dirname(os.path.realpath(__file__)), os.path.sep)
                    options.add_argument('headless')
                    service = Service(driver_path)
                    driver = webdriver.Chrome(service=service,options=options)

                    driver.get(url)
                    driver.find_element(By.CSS_SELECTOR, selector).click()
                    image = driver.find_element(By.XPATH,'//*[@id="Sva75c"]/div/div/div[3]/div[2]/c-wiz/div/div[1]/div[1]/div[3]/div/a/img')
                    src = image.get_attribute('src') if image.get_attribute('src') else image.get_attribute('data-src')



                    #if src != None and (src.find('https') == 0 or src.find('http') == 0):
                    dir = '{}\\DESKTOP\\AAMUIMG\\'.format(os.path.expanduser('~'))
                    if not os.path.isdir(dir):
                        os.makedirs(dir)
                    file = '{}hotel{}.jpg'.format(dir,title)

                    with open(file, 'wb') as f:
                        if src != None and (src.find('https') == 0 or src.find('http') == 0):
                            res = requests.get(src)
                            f.write(res.content)
                        else:
                            f.write(b64decode(src[23:]))
                    springurl='http://192.168.0.19:8080/aamurest/img/upload'
                    #headers={'Content-Type': 'application/json; charset=utf-8'}
                    files = {"files":open(file,'rb')}
                    params = {'contentid':contentid}
                    #res = requests.post(url,headers=headers,data={'contentid':contentid},file=file)
                    responseSpring = requests.post(springurl, params=params, files=files)

                    index+=1

                except Exception as e:
                    print(e)
                finally:
                    driver.quit()
        print(index)
        return index;



