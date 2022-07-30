import React, { useEffect } from "react";
import styled from "styled-components";
import SockJS from "sockjs-client";
import * as StompJs from "@stomp/stompjs";
function getConnet() {
  const client = new StompJs.Client();

  client.configure({
    brokerURL: "ws://192.168.0.19:8080/aamurest/ws/chat/websocket",
    onConnect: () => {
      console.log("onConnect");
      client.subscribe("/queue/chat/message/1", (message) => {
        console.log(JSON.parse(message.body));
      });
    },
    debug: (str) => {
      console.log(new Date(), str);
    },
  });
  client.activate();
}

const Chat = () => {
  useEffect(() => {
    getConnet();
  }, []);
  return (
    <Container>
      {/* <InnnerContainer> */}
      <Content>
        <Title>KIM1111님과 대화</Title>
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
          <input type="text" className="chatBot__input" />
          <span className="chatBot__input-btn">보내기</span>
        </div>
      </Content>
      {/* </InnnerContainer> */}
    </Container>
  );
};

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
