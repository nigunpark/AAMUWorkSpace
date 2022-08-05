const CLIENT_ID = "02112bcb58254150b780fe90791bd5a2";
const REDIRECT_URI =  "http://localhost:3000/oauth/callback/kakao";
export const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URI}&response_type=code`;
    