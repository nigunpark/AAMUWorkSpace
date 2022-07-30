import React, { useEffect, useRef, useState } from "react";
import styled from "styled-components";
import SockJS from "sockjs-client";
import * as StompJs from "@stomp/stompjs";
import { useDispatch, useSelector } from "react-redux";
function getConnet(roomno) {
  console.log("roomno", roomno);
  const client = new StompJs.Client();
  client.configure({
    brokerURL: "ws://192.168.0.19:8080/aamurest/ws/chat/websocket",
    onConnect: () => {
      console.log("onConnect");
      client.subscribe(`/queue/chat/message/${roomno}`, (message) => {
        console.log(JSON.parse(message.body));
      });
    },
    debug: (str) => {
      console.log(new Date(), str);
    },
  });
  client.activate();
  return client;
}

const Chat = () => {
  const [client, setClient] = useState();
  let reduxState = useSelector((state) => state);
  useEffect(() => {
    let what = getConnet(reduxState.forChatInfo.roomno);
    setClient(what);
  }, []);
  let inputRef = useRef();
  return (
    <Container>
      {/* <InnnerContainer> */}
      <Content>
        <Title>{reduxState.forChatInfo.id}님과 대화</Title>
        <Body>
          <div className="chatBotContent">
            <p className="chatBotBox mine">
              <span className="chatBotBox__span">안녕</span>
            </p>
            <p className="chatBotBox">
              <span className="chatBotBox__span">무엇을도와드릴까요</span>
            </p>
            <p className="chatBotBox mine">
              <span className="chatBotBox__span">안녕</span>
            </p>
            <p className="chatBotBox">
              <span className="chatBotBox__span">무엇을도와드릴까요</span>
            </p>
            <p className="chatBotBox mine">
              <span className="chatBotBox__span">안녕</span>
            </p>
            <p className="chatBotBox">
              <span className="chatBotBox__span">무엇을도와드릴까요</span>
            </p>
            <p className="chatBotBox mine">
              <span className="chatBotBox__span">안녕</span>
            </p>
            <p className="chatBotBox">
              <span className="chatBotBox__span">무엇을도와드릴까요</span>
            </p>
            <p className="chatBotBox mine">
              <span className="chatBotBox__span">안녕</span>
            </p>
            <p className="chatBotBox">
              <span className="chatBotBox__span">무엇을도와드릴까요</span>
            </p>
            <p className="chatBotBox mine">
              <span className="chatBotBox__span">안녕</span>
            </p>
            <p className="chatBotBox">
              <span className="chatBotBox__span">무엇을도와드릴까요</span>
            </p>
            <p className="chatBotBox mine">
              <span className="chatBotBox__span">안녕</span>
            </p>
            <p className="chatBotBox">
              <span className="chatBotBox__span">무엇을도와드릴까요</span>
            </p>
          </div>
        </Body>

        <div className="chatBot__input-container">
          <input
            type="text"
            className="chatBot__input"
            ref={inputRef}
            onKeyDown={() => {
              inputRef.current.value = "";
              sendChat(inputRef, client, reduxState);
            }}
          />
          <span
            className="chatBot__input-btn"
            onClick={() => {
              inputRef.current.value = "";
              sendChat(inputRef, client, reduxState);
            }}
          >
            보내기
          </span>
        </div>
      </Content>
      {/* </InnnerContainer> */}
    </Container>
  );
};
function sendChat(inputRef, client, reduxState) {
  client.publish({
    destination: "/app/chat/message",
    body: JSON.stringify({
      roomno: reduxState.forChatInfo.roomno,
      authid: "ADMIN",
      missage: inputRef.current.value,
      senddate: new Date().getTime(),
    }),
  });
}
const Container = styled.div`
  position: absolute;
  z-index: 999;
  top: 410px;
`;
const InnnerContainer = styled.div`
  position: relative;
`;
const Content = styled.div`
  position: relative;
  background: var(--blueWhite);
  box-shadow: var(--lightShadow);
  width: 280px;
  height: 440px;
  //   bottom: 620px;
  //   right: 300px;
  border-radius: 7px;
`;
const Title = styled.div`
  //   position: absolute;
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
  //   position: absolute;
  width: 97%;
  height: 350px;
  margin: 5px;
  margin-top: 5px;
  background: white;
  border-radius: 7px;
  display: flex;
  flex-direction: column-reverse;
`;
export default Chat;
