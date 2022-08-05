
import React, { useEffect } from "react";
import axios from 'axios';



const REST_API_KEY = "02112bcb58254150b780fe90791bd5a2";

const KakaoRedirectHandler = ({navigate}) => {
  const code = document.location.search.split('?code=')[1];
if (code !== undefined) {
  requestToken(code)
    .then(({ data }) => {
      console.log('requestToken:', data)
    })
    .catch(err => {
      console.error('requestToken:', err)
    })
}

function requestToken(code) {
  const JS_APP_KEY ="e18c7e895cf8f488cfc40dbb42762981";
  const REDIRECT_URI = "http://localhost:3000/oauth/callback/kakao";
  const makeFormData = params => {
    const searchParams = new URLSearchParams()
    Object.keys(params).forEach(key => {
      searchParams.append(key, params[key])
    })

    return searchParams
  }

    return axios({   
      method: 'POST',
      headers: {
        'content-type': 'application/x-www-form-urlencoded;charset=utf-8',
      },
      url: 'https://kauth.kakao.com/oauth/token',
      data: makeFormData({
        grant_type: 'authorization_code',
        client_id: JS_APP_KEY,
        redirect_uri: REDIRECT_URI,
        code,
      })
     
    }).then(function (response) {
     console.log('response',response.data.access_token);
     const token = response.data.access_token
    // Kakao Javascript SDK 초기화
    // if (!window.Kakao.isInitialized()) {
      // JavaScript key를 인자로 주고 SDK 초기화
      window.Kakao.init('e18c7e895cf8f488cfc40dbb42762981');
    // }
    // access token 설정
    window.Kakao.Auth.setAccessToken(token);
    navigate("../tokenuser",  { replace: true});
 

  });
  }
};

export default KakaoRedirectHandler;