from settings.config import DIALOG_CONFIG
import os
from flask import Flask
from flask_restful import Api
from flask_cors import CORS
from api.dialog import DialogMessage
from api.webhook import WebHook
from api.saveplaces import SavePlaces
from api.imgcrawll import Imgcrawll
from api.weather import Weather
#from api.recommendapi import RecommendApi
#from api.aamureview import AamuReview
#from api.movingtime import MovingTime
from api.wordcloud import Word
app =Flask(__name__)
app.config['JSON_AS_ASCII']=False

os.environ['GOOGLE_APPLICATION_CREDENTIALS']=DIALOG_CONFIG['GOOGLE_APPLICATION_CREDENTIALS']
CORS(app)

api=Api(app)
api.add_resource(DialogMessage,'/message')
api.add_resource(WebHook,'/webhook')
api.add_resource(SavePlaces,'/saveplaces')
api.add_resource(Imgcrawll,'/imgcrawll')
api.add_resource(Weather,'/weather')
#api.add_resource(RecommendApi,'/recommend')
#api.add_resource(AamuReview,'/review')
#api.add_resource(MovingTime,'/mvtm')
api.add_resource(Word,'/word')
if __name__ == '__main__':
    app.run(debug=True)