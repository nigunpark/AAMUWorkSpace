from waitress import serve
from app import app
print('REST API 서버가 포트 5020에서 시작되었습니다')
serve(app,host='0.0.0.0',port=5020)
