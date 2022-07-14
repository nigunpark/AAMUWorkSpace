import styled from "@emotion/styled";
import {
  faKey,
  faUnlockKeyhole,
  faUser,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Typography } from "@mui/material";
import axios from "axios";
import React, { useRef, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "./Login.css";
const LoginTest = () => {
  let idRef = useRef();
  let pwdRef = useRef();
  let navigate = useNavigate();
  const [showValid, setShowValid] = useState(false);
  return (
    <div>
      {/* <video src="/images/video1.mp4" autoPlay muted loop /> */}
      <Container />
      <Overlay>
        <Content>
          <Title>
            <div className="loginTitle__container">
              <FontAwesomeIcon
                icon={faUnlockKeyhole}
                className="loginLockIcon"
              />
              <h1>Login</h1>
            </div>
            <p style={{ color: "gray" }}>welcome to AAMU</p>
          </Title>
          <Body>
            <form
              autoComplete="off"
              method="post"
              className="loginForm"
              onSubmit={() => {
                return login(idRef, pwdRef, navigate, setShowValid);
              }}
            >
              <div className="loginInput__container">
                <FontAwesomeIcon icon={faUser} />
                <input
                  ref={idRef}
                  placeholder="아이디를 입력해주세요"
                  className="loginInput"
                  name="username"
                  onKeyDown={() => {
                    setShowValid(false);
                  }}
                />
              </div>
              <div className="loginInput__container">
                <FontAwesomeIcon icon={faKey} />
                <input
                  type="password"
                  ref={pwdRef}
                  placeholder="비밀번호를 입력해주세요"
                  className="loginInput"
                  name="password"
                  onKeyDown={() => {
                    setShowValid(false);
                  }}
                />
              </div>

              <div className="forgotPwd">
                {showValid ? (
                  <span style={{ color: "red", fontWeight: "bold" }}>
                    아이디 또는 비밀번호를 잘못 입력했습니다.
                  </span>
                ) : (
                  <div></div>
                )}
                <Link to="#" style={{ color: "gray", textAlign: "end" }}>
                  <span>비밀번호를 잊어버리셨나요</span>
                </Link>
              </div>
              <button
                className="loginBtn"
                onClick={(e) => {
                  e.preventDefault();
                  login(idRef, pwdRef, navigate, setShowValid);
                }}
              >
                Login
              </button>
              <div className="socialLoginBtn__container">
                <button className="socialLoginBtn kakao">
                  <img
                    src="/images/kakaoLoginBtn.png"
                    style={{
                      objectFit: "cover",
                      width: "100%",
                      height: "auto",
                    }}
                  />
                </button>
                <button className="socialLoginBtn naver">
                  <img
                    src="/images/naverLoginBtn.png"
                    style={{
                      objectFit: "cover",
                      width: "100%",
                      height: "auto",
                    }}
                  />
                </button>
              </div>
            </form>
          </Body>
          <Copyright sx={{ mt: 6 }} />
        </Content>
      </Overlay>
    </div>
  );
};

function Copyright(props) {
  return (
    <Typography variant="body2" color="text.warning" align="center" {...props}>
      {"Copyright © "}
      <Link
        color="inherit"
        to="https://localhost:3000/"
        style={{ color: "var(--orange)" }}
      >
        AAMU
      </Link>{" "}
      {new Date().getFullYear()}
      {"."}
    </Typography>
  );
}

const login = (idRef, pwdRef, navigate, setShowValid) => {
  if (idRef.current.value.length === 0) {
    idRef.current.parentElement.classList.add("validation");
    idRef.current.focus();
    setTimeout(() => {
      idRef.current.parentElement.classList.remove("validation");
    }, 1100);
    return false;
  } else if (
    idRef.current.value.length !== 0 &&
    pwdRef.current.value.length === 0
  ) {
    pwdRef.current.parentElement.classList.add("validation");
    pwdRef.current.focus();
    setTimeout(() => {
      pwdRef.current.parentElement.classList.remove("validation");
    }, 1100);
    return false;
  }
  axios
    .post("/aamurest/authenticate", {
      username: idRef.current.value,
      password: pwdRef.current.value,
    })
    .then((resp) => {
      sessionStorage.setItem("token", resp.data.token);
      sessionStorage.setItem("username", idRef.current.value);
      navigate(-1);
    })
    .catch((error) => {
      console.log("error:", error);
      // setShowValid(true);
    });
};
const Container = styled.div`
  position: fixed;
  // background: url();
  // background-image: url("/images/window.jpg");
  // background-repeat: no-repeat;
  // background-size: cover;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  // filter: blur(2px);
`;
const Overlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
`;
const Content = styled.div`
  position: relative;
  width: 25%;
  height: 600px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 3rem;
  // border: 1px solid rgba(250, 163, 7, 0.5);
  border: 1px solid rgba(255, 255, 255, 0.5);
  padding: 0 30px;
  border-radius: 15px;
  box-shadow: var(--shadow);
  margin: auto;
  background: rgba(255, 255, 255, 0.6);
`;
const Title = styled.div`
  margin-top: 60px;
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 5px;
`;
const Body = styled.div`
  width: 100%;
  height: auto;
`;
export default LoginTest;
