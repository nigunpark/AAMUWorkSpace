import React, { useState, useEffect } from "react";
import axios from "axios"; // 액시오스
import { useLocation } from "react-router-dom";

const Test = () => {
  let token=sessionStorage.getItem('token');
  
  // const initializeNaverLogin = () => {
  //   const naverLogin = new naver.LoginWithNaverId({
  //     clientId: "amnFx3UOA94ulL_rvg9Y",
  //     callbackUrl: "http://localhost:3000/WholeMap/naver",
  //     isPopup: false, // popup 형식으로 띄울것인지 설정
  //     loginButton: { color: "white", type: 1, height: "47" }, //버튼의 스타일, 타입, 크기를 지정
  //   });
  //   naverLogin.init();
  // };

  // const location = useLocation();

  // const getNaverToken = () => {
  //   if (!location.hash) return;
  //   const token = location.hash.split("=")[1].split("&")[0];
  //   console.log(token);
  // };

  // useEffect(() => {
  //   initializeNaverLogin();
  //   getNaverToken();
  // }, []);

  // const [id, setId] = useState(0);

  // function stateTest() {
  //   setId(id + 1);
  // }
  // useEffect(() => {
  //   alert("ok");
  // }, []);

  // const api = "http://192.168.0.19:8080";

  function testAxios() {
    axios
      .get("http://192.168.0.19:8080/rest/info2?areacode=1&contenttypeid=12")
      .then((response) => {
        console.log(response.data);
      })
      .catch((error) => {
        console.log(error);
      })
      .then(() => {
        console.log("get요청실행됨");
      });
  }

 
  function test() {
    axios
      .get("/aamurest/info/places", {
        Headers:{
            Authorization:`Bearer ${token}`
        },
        params: {
          areacode: "1",
          contenttypeid: "12",
        },
      })
      .then((resp) => {
        console.log("get성공");
        console.log(resp.data);
      })
      .catch((error) => {
        console.log("error :%O ", error);
      })
      .then(() => {
        console.log("get요청됨");
      });
  }
  return (
    <div>
      <button
        style={{ background: "green", cursor: "pointer" }}
        onClick={() => testAxios()}
      >
        test
      </button>
      <button
        style={{ background: "yellow", cursor: "pointer" }}
        onClick={() => test()}
      >
        우재한테 get요청
      </button>
    </div>
  );
};
export default Test;
