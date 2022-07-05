const REST_API_KEY = "cc80502fc1a7fe4caaa624af80993d73";
const REDIRECT_URI =  "http://localhost:3000/oauth/callback/kakao";
export const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`;