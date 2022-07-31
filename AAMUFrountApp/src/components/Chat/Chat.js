import React, { useEffect, useRef, useState } from "react";
import styled from "styled-components";
import SockJS from "sockjs-client";
import * as StompJs from "@stomp/stompjs";
import { useDispatch, useSelector } from "react-redux";
let chatArr = [];
function getConnet(roomno, setChats) {
  const client = new StompJs.Client();
  client.configure({
    brokerURL: "ws://192.168.0.19:8080/aamurest/ws/chat/websocket",
    onConnect: () => {
      console.log("onConnect");
      let subscription = client.subscribe(`/queue/chat/message/${roomno}`, (message) => {
        console.log(JSON.parse(message.body));
        chatArr.push({ message: JSON.parse(message.body).missage, bool: true });
        chatArr = [...chatArr];
        setChats(chatArr);
      });
    },
    debug: (str) => {
      console.log(new Date(), str);
    },
  });

  client.activate();
  return client;
}
const Chat = ({ showChat }) => {
  const [chats, setChats] = useState([]);
  const [client, setClient] = useState({});
  let reduxState = useSelector((state) => state);

  useEffect(() => {
    let client = getConnet(reduxState.forChatInfo.roomno, setChats);
    console.log("client", client);
    setClient(client);
    // if (showChat === false) subscription.unsubscribe();
  }, []);
  let inputRef = useRef();
  return (
    <Container>
      {/* <InnnerContainer> */}
      <Content>
        <Title>{reduxState.forChatInfo.id}님과 대화</Title>
        <Body>
          {chats.map((val, i) => {
            return (
              <p className={val.bool ? "chatBotBox bot" : "chatBotBox"}>
                <span className="chatBotBox__span">
                  {val.message}
                  <br />
                </span>
              </p>
            );
          })}
        </Body>

        <div className="chatBot__input-container">
          <input
            type="text"
            className="chatBot__input"
            ref={inputRef}
            onKeyDown={(e) => {
              if (e.key === "Enter") {
                chatArr.push({ message: inputRef.current.value, bool: false });
                chatArr = [...chatArr];
                sendChat(inputRef, client, reduxState, setChats);
                inputRef.current.value = "";
              }
            }}
          />
          <span
            className="chatBot__input-btn"
            onClick={(e) => {
              chatArr.push({ message: inputRef.current.value });
              chatArr = [...chatArr];
              setChats(chatArr);
              sendChat(inputRef, client, reduxState);
              inputRef.current.value = "";
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
function sendChat(inputRef, client, reduxState, setChats) {
  client.publish({
    destination: "/app/chat/message",
    body: JSON.stringify({
      roomno: reduxState.forChatInfo.roomno,
      authid: "ADMIN",
      missage: inputRef.current.value,
      senddate: new Date().getTime(),
    }),
  });
  setChats(chatArr);
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
  position: absolute;
  width: 97%;
  height: 350px;
  margin: 5px;
  margin-top: 5px;
  background: white;
  border-radius: 7px;
  display: flex;
  flex-direction: column;
`;
export default Chat;
