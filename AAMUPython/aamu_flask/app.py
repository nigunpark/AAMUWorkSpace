from settings.config import DIALOG_CONFIG
import os
from flask import Flask
from flask_restful import Api
from flask_cors import CORS
from api.dialog import DialogMessage
from api.webhook import WebHook
app =Flask(__name__)
app.config['JSON_AS_ASCII']=False

os.environ['GOOGLE_APPLICATION_CREDENTIALS']=DIALOG_CONFIG['GOOGLE_APPLICATION_CREDENTIALS']
CORS(app)

api=Api(app)
api.add_resource(DialogMessage,'/message')
api.add_resource(WebHook,'/webhook')
if __name__ == '__main__':
    app.run(debug=True)