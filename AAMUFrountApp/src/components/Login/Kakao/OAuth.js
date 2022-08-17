const CLIENT_ID = "3a141ff9d81872fc2f019e5f2f7de619";
const REDIRECT_URI = "http://192.168.0.35:3000/oauth/callback/kakao";
export const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URI}&response_type=code`;
