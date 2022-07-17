import React, { useRef, useState } from "react";
import {
  Container,
  Content,
  Title,
  Body,
  Footer,
} from "../components/Modal/ForJoin.js";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUser } from "@fortawesome/free-regular-svg-icons";
import "./Join.css";
import { Typography } from "@mui/material";
import { Link, useNavigate } from "react-router-dom";
import {
  fa1,
  fa2,
  fa3,
  faCheck,
  faHouse,
} from "@fortawesome/free-solid-svg-icons";
import axios from "axios";
import { useDispatch, useSelector } from "react-redux";
import { addStepOne } from "../redux/store.js";
const Join = () => {
  const idRef = useRef();
  const pwdRef = useRef();
  const pwdCkRef = useRef();
  const idValidRef = useRef();
  const pwdValidRef = useRef();
  const pwdCkValidRef = useRef();
  const forSlideRef = useRef();
  let navigate = useNavigate();
  let dispatch = useDispatch();
  let reduxState = useSelector((state) => {
    return state;
  });
  const [doneDoubleCk, setDoneDoubleCk] = useState(false);
  const [whichIdValid, setWhichIdValid] = useState(false);
  return (
    <div className="join__step-one">
      <Container>
        <div ref={forSlideRef} className="join__slide-in-left">
          <Content>
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
              <FontAwesomeIcon
                icon={fa1}
                className="join__progress-icon join__progress-step"
              />
              -
              <FontAwesomeIcon icon={fa2} className="join__progress-icon" />-
              <FontAwesomeIcon icon={fa3} className="join__progress-icon" />
            </div>
            <Body>
              <div className="join__input-container">
                <div className="join__input-wrap">
                  <div>
                    <span style={{ fontSize: "13px" }}>아이디</span>{" "}
                    <span className="join__keyup-validSpan" ref={idValidRef}>
                      {whichIdValid
                        ? "(이미 존재하는 아이디입니다)"
                        : "(아이디는 영문자와 숫자 조합으로 7자이상)"}
                    </span>
                  </div>
                  <div
                    className="join__input-div"
                    style={{ display: "flex", justifyContent: "space-between" }}
                  >
                    <input
                      onKeyUp={() => {
                        idValidation(idRef, idValidRef);
                      }}
                      onChange={() => {
                        setDoneDoubleCk(false);
                        setWhichIdValid(false);
                      }}
                      type="text"
                      className="join__input"
                      size={35}
                      placeholder="영문자와 숫자 조합으로 7자이상"
                      ref={idRef}
                      defaultValue={reduxState.joinData.id}
                    />
                    <span
                      className="join__id-double-check"
                      onClick={() => {
                        if (
                          idRef.current.parentElement.style.borderColor ===
                          "grey"
                        ) {
                          idValidRef.current.style.visibility = "visible";
                          return;
                        }
                        doubleCheck(
                          idRef,
                          idValidRef,
                          setDoneDoubleCk,
                          setWhichIdValid
                        );
                      }}
                    >
                      {doneDoubleCk ? (
                        <FontAwesomeIcon icon={faCheck} />
                      ) : (
                        "중복확인"
                      )}
                    </span>
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
                      onKeyUp={() => {
                        pwdValidation(pwdRef, pwdValidRef);
                      }}
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
                      onKeyUp={() => {
                        pwdCkValidation(pwdRef, pwdCkRef, pwdCkValidRef);
                      }}
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
                  validation(
                    forSlideRef,
                    idRef,
                    pwdRef,
                    pwdCkRef,
                    navigate,
                    dispatch,
                    doneDoubleCk
                  );
                }}
              >
                Next
              </span>
            </div>
            <Footer>
              <Copyright />
            </Footer>
          </Content>
        </div>
      </Container>
    </div>
  );
};

function doubleCheck(idRef, idValidRef, setDoneDoubleCk, setWhichIdValid) {
  let token = sessionStorage.getItem("token");
  axios
    .get("/aamurest/users/checkid", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
      params: { id: idRef.current.value },
    })
    .then((resp) => {
      if (resp.data === 0) {
        setDoneDoubleCk(true);
        idRef.current.parentElement.style.borderColor = "yellowGreen";
      } else {
        alert("이미 존재하는 아이디입니다");
        setWhichIdValid(true);
        idRef.current.parentElement.style.borderColor = "grey";
        idValidRef.current.style.visibility = "visible";
      }
    })
    .catch((error) => {
      console.log(error);
    });
}

function idValidation(idRef, idValidRef) {
  if (idRef.current.value.trim().length === 0) {
    idValidRef.current.style.visibility = "hidden";
    idRef.current.parentElement.style.borderColor = "grey";
  } else {
    const regExp = new RegExp("^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{6,}$", "g");
    if (!regExp.test(idRef.current.value.trim())) {
      idValidRef.current.style.visibility = "visible";
      idRef.current.parentElement.style.borderColor = "grey";
    } else {
      idValidRef.current.style.visibility = "hidden";
      idRef.current.parentElement.style.borderColor = "yellowGreen";
    }
  }
}
function pwdValidation(pwdRef, pwdValidRef) {
  if (pwdRef.current.value.trim().length === 0) {
    pwdValidRef.current.style.visibility = "hidden";
    pwdRef.current.parentElement.style.borderColor = "grey";
  } else {
    const regExp = new RegExp(
      "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)-_=+]).{10,}",
      "g"
    );
    if (!regExp.test(pwdRef.current.value.trim())) {
      pwdValidRef.current.style.visibility = "visible";
      pwdRef.current.parentElement.style.borderColor = "grey";
    } else {
      pwdValidRef.current.style.visibility = "hidden";
      pwdRef.current.parentElement.style.borderColor = "yellowGreen";
    }
  }
}

function pwdCkValidation(pwdRef, pwdCkRef, pwdCkValidRef) {
  if (pwdCkRef.current.value.trim().length === 0) {
    pwdCkValidRef.current.style.visibility = "hidden";
    pwdCkRef.current.parentElement.style.borderColor = "grey";
  } else {
    if (pwdRef.current.value !== pwdCkRef.current.value) {
      pwdCkValidRef.current.style.visibility = "visible";
      pwdCkRef.current.parentElement.style.borderColor = "grey";
    } else {
      pwdCkValidRef.current.style.visibility = "hidden";
      pwdCkRef.current.parentElement.style.borderColor = "yellowGreen";
    }
  }
}

function validation(
  forSlideRef,
  idRef,
  pwdRef,
  pwdCkRef,
  navigate,
  dispatch,
  doneDoubleCk
) {
  if (idRef.current.value.trim().length === 0) {
    idRef.current.parentElement.classList.add("validation");
    idRef.current.focus();
    setTimeout(() => {
      idRef.current.parentElement.classList.remove("validation");
    }, 1100);
  } else if (pwdRef.current.value.trim().length === 0) {
    pwdRef.current.parentElement.classList.add("validation");
    pwdRef.current.focus();
    setTimeout(() => {
      pwdRef.current.parentElement.classList.remove("validation");
    }, 1100);
  } else if (pwdCkRef.current.value.trim().length === 0) {
    pwdCkRef.current.parentElement.classList.add("validation");
    pwdCkRef.current.focus();
    setTimeout(() => {
      pwdCkRef.current.parentElement.classList.remove("validation");
    }, 1100);
  } else if (pwdRef.current.value !== pwdCkRef.current.value) {
    pwdCkRef.current.parentElement.classList.add("validation");
    setTimeout(() => {
      pwdCkRef.current.parentElement.classList.remove("validation");
    }, 1100);
  } else {
    if (doneDoubleCk !== true) {
      alert("아이디 중복확인을 하세요");
      return;
    }
    forSlideRef.current.classList.remove("join__slide-in-left");
    forSlideRef.current.classList.add("join__slide-out-right");
    setTimeout(() => {
      dispatch(addStepOne([idRef.current.value, pwdRef.current.value]));
      navigate("/joinTwo");
    }, 300);
  }
}

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
export default Join;
