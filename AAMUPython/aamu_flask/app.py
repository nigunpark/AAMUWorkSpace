from settings.config import DIALOG_CONFIG
import os
from flask import Flask
from flask_restful import Api
from flask_cors import CORS
from api.dialog import DialogMessage
from api.webhook import WebHook
from api.saveplaces import SavePlaces
from api.imgcrawll import Imgcrawll
from api.recommendapi import RecommendApi
app =Flask(__name__)
app.config['JSON_AS_ASCII']=False

os.environ['GOOGLE_APPLICATION_CREDENTIALS']=DIALOG_CONFIG['GOOGLE_APPLICATION_CREDENTIALS']
CORS(app)

api=Api(app)
api.add_resource(DialogMessage,'/message')
api.add_resource(WebHook,'/webhook')
api.add_resource(SavePlaces,'/saveplaces')
api.add_resource(Imgcrawll,'/imgcrawll')
api.add_resource(RecommendApi,'/recommend')
if __name__ == '__main__':
    app.run(debug=True)