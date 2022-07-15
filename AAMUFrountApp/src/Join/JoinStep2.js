import React, { useRef } from "react";
import {
  Container,
  ContentStep2,
  Title,
  Body,
  Footer,
} from "../components/Modal/ForJoin.js";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUser } from "@fortawesome/free-regular-svg-icons";
import "./Join.css";
import { Typography } from "@mui/material";
import { Link, useNavigate } from "react-router-dom";
import { fa1, fa2, fa3, faHouse } from "@fortawesome/free-solid-svg-icons";
import { useSelector } from "react-redux";
const JoinStep2 = ({ forSlideRef }) => {
  let idRef = useRef();
  let pwdRef = useRef();
  let pwdCkRef = useRef();
  let idValidRef = useRef();
  let pwdValidRef = useRef();
  let pwdCkValidRef = useRef();
  let photoRef = useRef();
  let navigate = useNavigate();
  let reduxState = useSelector((state) => {
    return state;
  });
  //   console.log(reduxState.joinData);
  return (
    <div className="join__step-two">
      <Container>
        <div className="join__slide-in-right">
          <ContentStep2>
            <Link to="/">
              <FontAwesomeIcon className="home-btn" icon={faHouse} />
            </Link>
            <Title>
              <div className="loginTitle__container">
                <FontAwesomeIcon icon={faUser} className="joinIcon" />
                <h1>회원가입</h1>
              </div>
              <p style={{ color: "gray" }}>welcome to AAMU</p>
            </Title>
            <div className="join__progress-container">
              <FontAwesomeIcon icon={fa1} className="join__progress-icon " />
              -
              <FontAwesomeIcon
                icon={fa2}
                className="join__progress-icon join__progress-step"
              />
              -
              <FontAwesomeIcon icon={fa3} className="join__progress-icon" />
            </div>
            <Body>
              <div className="join__input-container">
                <div className="join__stepTwo-top">
                  <div className="join__profile-img-container">
                    <div className="join__profile-img-preview">
                      <label for="photo" className="join__stepTwo-label" />
                      <img
                        className="join__stepTwo-img"
                        ref={photoRef}
                        src="/images/no-image.jpg"
                      />
                    </div>
                    <input
                      id="photo"
                      type="file"
                      onChange={(e) => {
                        if (e.target.files && e.target.files[0]) {
                          let reader = new FileReader();
                          reader.onload = function (e) {
                            photoRef.current.src = e.target.result;
                          };
                          reader.readAsDataURL(e.target.files[0]);
                        } else {
                          photoRef.current.src = "";
                        }
                      }}
                      style={{ display: "none" }}
                    />
                  </div>
                  <div className="join__stepTwo-input-wrap">
                    <div style={{ display: "flex" }}>
                      <span style={{ fontSize: "13px" }}>이름</span>{" "}
                      <span className="join__keyup-validSpan" ref={idValidRef}>
                        (이름은 한글로만)
                      </span>
                    </div>
                    <div
                      className="join__stepTwo-input-div"
                      style={{
                        display: "flex",
                        justifyContent: "space-between",
                      }}
                    >
                      <input type="text" size={20} placeholder="" ref={idRef} />
                    </div>
                  </div>
                </div>
                <div className="join__input-wrap">
                  <div>
                    <span style={{ fontSize: "13px" }}> 비밀번호</span>
                    <span className="join__keyup-validSpan" ref={pwdValidRef}>
                      (영문자,특수문자,숫자 조합으로 10자이상)
                    </span>
                  </div>
                  <div
                    className="join__input-div"
                    style={{ display: "flex", justifyContent: "space-between" }}
                  >
                    <input
                      type="password"
                      className="join__input"
                      size={35}
                      placeholder="영문자,특수문자,숫자 조합으로 10자이상"
                      ref={pwdRef}
                    />
                  </div>
                </div>
                <div className="join__input-wrap">
                  <div>
                    <span style={{ fontSize: "13px" }}> 비밀번호 확인</span>
                    <span className="join__keyup-validSpan" ref={pwdCkValidRef}>
                      (비밀번호와 일치하지 않습니다)
                    </span>
                  </div>
                  <div
                    className="join__input-div"
                    style={{ display: "flex", justifyContent: "space-between" }}
                  >
                    <input
                      type="password"
                      className="join__input"
                      size={35}
                      placeholder="영문자,특수문자,숫자 조합으로 10자이상"
                      ref={pwdCkRef}
                    />
                  </div>
                </div>
              </div>
            </Body>
            <div className="join__next-btn-container">
              <span
                className="join__next-btn"
                onClick={() => {
                  navigate(-1);
                }}
              >
                이전
              </span>
              <span className="join__next-btn">다음</span>
            </div>
            <Footer>
              <Copyright />
            </Footer>
          </ContentStep2>
        </div>
      </Container>
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

export default JoinStep2;
