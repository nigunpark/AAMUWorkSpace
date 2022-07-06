import styled from "@emotion/styled";
import {
  faKey,
  faUnlockKeyhole,
  faUser,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Typography } from "@mui/material";
import React from "react";
import { Link } from "react-router-dom";
import "./Login.css";
const LoginTest = () => {
  return (
    <div>
      <Container>
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
            <form action="#" className="loginForm">
              <div className="loginInput__container">
                <FontAwesomeIcon icon={faUser} />
                <input
                  placeholder="아이디를 입력해주세요"
                  className="loginInput"
                />
              </div>
              <div className="loginInput__container">
                <FontAwesomeIcon icon={faKey} />
                <input
                  placeholder="비밀번호를 입력해주세요"
                  className="loginInput"
                />
              </div>
              <a
                href="#"
                style={{
                  border: "1px solid red",
                  fontSize: "12px",
                  textAlign: "end",
                }}
              >
                비밀번호를 잊어버리셨나요
              </a>
              <button className="loginBtn">Login</button>
            </form>
          </Body>
          <Copyright sx={{ mt: 10 }} />
        </Content>
      </Container>
    </div>
  );
};

function Copyright(props) {
  return (
    <Typography
      variant="body2"
      color="text.secondary"
      align="center"
      {...props}
    >
      {"Copyright © "}
      <Link color="inherit" to="https://localhost:3000/">
        AAMU
      </Link>{" "}
      {new Date().getFullYear()}
      {"."}
    </Typography>
  );
}

const Container = styled.div`
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
  width: 22%;
  height: 600px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 3rem;
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
