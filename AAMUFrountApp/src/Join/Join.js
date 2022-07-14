import React from "react";
import {
  Container,
  Content,
  Title,
  Body,
} from "../components/Modal/ForJoin.js";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUser } from "@fortawesome/free-regular-svg-icons";
import "./Join.css";
const Join = () => {
  return (
    <div className="join">
      <Container>
        <Content>
          <Title>
            <div className="loginTitle__container">
              <FontAwesomeIcon icon={faUser} className="joinIcon" />
              <h1>회원가입</h1>
            </div>
            <p style={{ color: "gray" }}>welcome to AAMU</p>
          </Title>
          <Body>
            <div className="join__input-container">
              <div className="join__input-wrap">
                <span style={{ fontSize: "15px", width: "20px" }}>
                  아이디 :
                </span>{" "}
                <input type="text" className="join__input" size={30} />
                <span className="join__id-double-check">중복확인</span>
              </div>
              <div className="join__input-wrap">
                <span style={{ fontSize: "15px", width: "20px" }}>
                  {" "}
                  비밀번호 :{" "}
                </span>
                <input type="text" className="join__input" size={30} />
              </div>
            </div>
          </Body>
        </Content>
      </Container>
    </div>
  );
};

export default Join;
