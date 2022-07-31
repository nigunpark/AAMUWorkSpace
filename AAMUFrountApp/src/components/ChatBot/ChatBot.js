import React, { useEffect, useRef, useState } from "react";
import "./ChatBot.css";
import styled, { keyframes } from "styled-components";
import axios from "axios";
import { useDispatch } from "react-redux";
import { addChatBotData } from "../../redux/store";
import { useNavigate } from "react-router-dom";
// let chatArr = [];
const ChatBot = ({ showChatBot, chatArr }) => {
  let inputRef = useRef();
  let dispatch = useDispatch();
  let navigate = useNavigate();
  const [chats, setChats] = useState([]);
  function goRecommend(val) {
    if (val.rbn !== null) {
      dispatch(addChatBotData(val));
      navigate("/forum");
    }
  }
  return (
    <Container>
      <Content>
        <Title>챗봇에게 무엇이든 물어보세요</Title>
        <Body>
          <div className="chatBotContent">
            {chats.map((val, i) => {
              return (
                <p className={val.bool ? "chatBotBox bot" : "chatBotBox"}>
                  <span
                    style={val.rbn !== null ? { color: "blue" } : { color: "black" }}
                    className="chatBotBox__span"
                    onClick={() => {
                      goRecommend(val);
                    }}
                  >
                    {val.message}
                  </span>
                  <span>{val.title}</span>
                </p>
              );
            })}
            {/* <p className="chatBotBox ">
              <span className="chatBotBox__span">안녕</span>
            </p>
            <p className="chatBotBox bot">
              <span className="chatBotBox__span">무엇을도와드릴까요</span>
            </p> */}
          </div>
        </Body>

        <div className="chatBot__input-container">
          <input
            type="text"
            className="chatBot__input"
            ref={inputRef}
            onKeyDown={(e) => {
              if (e.key === "Enter") {
                chatArr.push({ message: inputRef.current.value, bool: false });
                setChats(chatArr);
                chatBotConn(inputRef, chats, setChats, chatArr);
                inputRef.current.value = "";
              }
            }}
          />
          <span
            className="chatBot__input-btn"
            onClick={() => {
              chatArr.push({ message: inputRef.current.value, bool: false });
              setChats(chatArr);
              chatBotConn(inputRef, chats, setChats, chatArr);
              inputRef.current.value = "";
            }}
          >
            보내기
          </span>
        </div>
      </Content>
    </Container>
  );
};
function chatBotConn(inputRef, chats, setChats, chatArr) {
  let token = sessionStorage.getItem("token");
  axios
    .post(
      "/aamurest/main/chatbot",
      {
        id: sessionStorage.getItem("username"),
        message: inputRef.current.value,
      },
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )
    .then((resp) => {
      chatArr.push(resp.data);
      console.log("resp", resp.data);
      setChats([...chatArr]);
    })
    .catch((err) => {
      console.log(err);
    });
}
const chatBotSlide = keyframes`
0% {
  transform: translateY(700px) scaleY(2) ;
  transform-origin: 50% 100%;
  filter: blur(40px);
  opacity: 0;
}
100% {
  transform: translateY(0) scaleY(1) ;
  transform-origin: 50% 50%;
  filter: blur(0);
  opacity: 1;
}
`;

const Container = styled.div`
  position: absolute;
  animation: ${chatBotSlide} 0.6s cubic-bezier(0.23, 1, 0.32, 1) both;
`;
const Content = styled.div`
  position: relative;
  background: var(--blueWhite);
  box-shadow: var(--lightShadow);
  z-index: 200;
  width: 330px;
  height: 600px;
  bottom: 620px;
  right: 300px;
  border-radius: 7px;
`;
const Title = styled.div`
  // position: absolute;
  width: 100%;
  height: 30px;
  background: var(--orange);
  border-radius: 7px 7px 0 0;
  color: white;
  font-weight: bold;
  display: flex;
  justify-content: center;
  align-items: center;
  letter-spacing: 2px;
`;

const Body = styled.div`
  position: absolute;
  width: 97%;
  height: 86%;
  margin: 5px;
  margin-top: 7px;
  background: white;
  border-radius: 7px;
  display: flex;
  flex-direction: column-reverse;
`;

export default React.memo(ChatBot);
