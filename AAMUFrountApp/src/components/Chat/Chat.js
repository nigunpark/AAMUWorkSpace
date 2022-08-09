import React, { useEffect, useRef, useState } from "react";
import styled, { keyframes } from "styled-components";
import "./Chat.css";
import SockJS from "sockjs-client";
import * as StompJs from "@stomp/stompjs";
import { useDispatch, useSelector } from "react-redux";
let chatArr = [];
// function getConnet(roomno, setChats) {
//   const client = new StompJs.Client();
//   client.configure({
//     brokerURL: "ws://192.168.0.19:8080/aamurest/ws/chat/websocket",
//     onConnect: () => {
//       console.log("onConnect");
//       let subscription = client.subscribe(`/queue/chat/message/${roomno}`, (message) => {
//         console.log(JSON.parse(message.body));
//         chatArr.push({ message: JSON.parse(message.body).missage, bool: true });
//         chatArr = [...chatArr];
//         setChats(chatArr);
//       });
//     },
//     debug: (str) => {
//       console.log(new Date(), str);
//     },
//   });

//   client.activate();
//   return client;
// }
const Chat = ({ prevChats }) => {
  const [chats, setChats] = useState([]);
  const [client, setClient] = useState({});
  let reduxState = useSelector((state) => state);
  useEffect(() => {
    if (prevChats !== undefined && prevChats.length !== 0) setChats(prevChats);
    // let client = getConnet(reduxState.forChatInfo.roomno, setChats);
    // console.log("client", client);
    // setClient(client);
    // if (showChat === false) subscription.unsubscribe();
    // getChats();
    reduxState.forChatInfo.client.subscribe(
      `/queue/chat/message/${reduxState.forChatInfo.roomno}`,
      (message) => {
        console.log(JSON.parse(message.body));

        // setChats((curr) => {
        //   return [...curr, JSON.parse(message.body)];
        // });
        if (JSON.parse(message.body).authid !== sessionStorage.getItem("username")) {
          let newChat = {
            // missage: message.body.missage,
            missage: JSON.parse(message.body).missage,
            authid: JSON.parse(message.body).authid,
          };
          setChats((curr) => [...curr, newChat]);
        }
      }
    );
  }, []);
  function getChats() {}
  let inputRef = useRef();
  let bodyRef = useRef();
  console.log("리렌더링 채팅");
  // console.log("bodyRef", bodyRef.current.scrollTop);
  return (
    <Container>
      {/* <InnnerContainer> */}
      <Content>
        <Title>{reduxState.forChatInfo.id}님과 대화</Title>
        <Body ref={bodyRef}>
          <div style={{ display: "flex", flexDirection: "column" }}>
            {chats.map((val, i) => {
              return (
                <div
                  className={
                    val.authid !== sessionStorage.getItem("username") ? "chatBox box" : "chatBox"
                  }
                >
                  <div
                    style={{
                      display: "flex",
                      justifyContent: "center",
                      alignItems: "center",
                      gap: "5px",
                    }}
                  >
                    <div className="chat__profile__img-container">
                      {/* <img
                      className="chat__profile__img"
                      src={val.autopro ?? "/images/no-image.jpg"}
                      onError={(e) => {
                        e.target.src = "/images/no-image.jpg";
                      }}
                    /> */}
                      <span className="chatBox__time-span">
                        {new Date().getHours() > 12 ? "오후" : "오전"}
                        {new Date().getHours() > 12
                          ? new Date().getHours() - 12
                          : new Date().getHours()}
                        :{new Date().getMinutes().toString().padStart(2, "0")}
                      </span>
                    </div>

                    <span className="chatBox__span">
                      {val.missage}
                      <br />
                    </span>
                  </div>
                </div>
              );
            })}
          </div>
        </Body>

        <div className="chatBot__input-container">
          <input
            type="text"
            className="chatBot__input"
            ref={inputRef}
            onKeyDown={(e) => {
              if (e.key === "Enter") {
                let newChat = {
                  missage: e.target.value,
                  authid: sessionStorage.getItem("username"),
                };
                setChats((curr) => [...curr, newChat]);
                sendChat(inputRef, reduxState);
                e.target.value = "";

                // getChats();
                // chatArr.push({ message: inputRef.current.value, bool: false });
                // chatArr = [...chatArr];
              }
            }}
          />
          <span
            className="chatBot__input-btn"
            onClick={(e) => {
              let newChat = {
                missage: e.target.value,
                authid: sessionStorage.getItem("username"),
              };
              setChats((curr) => [...curr, newChat]);
              sendChat(inputRef, reduxState);
              e.target.value = "";
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

function sendChat(inputRef, reduxState) {
  reduxState.forChatInfo.client.publish({
    destination: "/app/chat/message",
    body: JSON.stringify({
      roomno: reduxState.forChatInfo.roomno,
      authid: sessionStorage.getItem("username"),
      missage: inputRef.current.value,
      senddate: new Date().getTime(),
      authpro: sessionStorage.getItem("userimg"),
    }),
  });
  // setChats(chatArr);
}
const swing_instaChat = keyframes`
0% {
  transform: rotateX(100deg);
  transform-origin: bottom;
  opacity: 0;
}
100% {
  transform: rotateX(0);
  transform-origin: bottom;
  opacity: 1;
}
`;

const Container = styled.div`
  position: absolute;
  z-index: 999;
  top: 410px;
  animation: ${swing_instaChat} 0.8s cubic-bezier(0.175, 0.885, 0.32, 1.275) both;
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
  flex-direction: column-reverse;
  // justify-content: end;
  overflow-y: auto;
  &::-webkit-scrollbar {
    width: 3px;
  }
`;
export default Chat;
