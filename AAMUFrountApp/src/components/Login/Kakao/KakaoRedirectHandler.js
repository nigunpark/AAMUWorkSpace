import React, { useEffect } from "react";
import axios from "axios";

const REST_API_KEY = "3a141ff9d81872fc2f019e5f2f7de619";

const KakaoRedirectHandler = ({ navigate }) => {
  const code = document.location.search.split("?code=")[1];
  if (code !== undefined) {
    requestToken(code)
      .then(({ data }) => {
        console.log("requestToken:", data);
      })
      .catch((err) => {
        console.error("requestToken:", err);
      });
  }

  function requestToken(code) {
    const JS_APP_KEY = "2eed708665efb1b1a0ec9c3e21d756ea";
    const REDIRECT_URI = "http://192.168.0.35:3000/oauth/callback/kakao";
    const makeFormData = (params) => {
      const searchParams = new URLSearchParams();
      Object.keys(params).forEach((key) => {
        searchParams.append(key, params[key]);
      });

      return searchParams;
    };

    return axios({
      method: "POST",
      headers: {
        "content-type": "application/x-www-form-urlencoded;charset=utf-8",
      },
      url: "https://kauth.kakao.com/oauth/token",
      data: makeFormData({
        grant_type: "authorization_code",
        client_id: JS_APP_KEY,
        redirect_uri: REDIRECT_URI,
        code,
      }),
    }).then(function (response) {
      console.log("response", response.data.access_token);
      const token = response.data.access_token;
      // Kakao Javascript SDK 초기화
      // if (!window.Kakao.isInitialized()) {
      // JavaScript key를 인자로 주고 SDK 초기화
      window.Kakao.init("2eed708665efb1b1a0ec9c3e21d756ea");
      // }
      // access token 설정
      window.Kakao.Auth.setAccessToken(token);
      navigate("../tokenuser", { replace: true });
    });
  }
};

export default KakaoRedirectHandler;
