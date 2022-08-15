import React, { useEffect, useRef, useState } from "react";
import styled, { keyframes } from "styled-components";
import "./Chat.css";
import { useDispatch, useSelector } from "react-redux";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faXmark } from "@fortawesome/free-solid-svg-icons";
import { addChatPreview } from "../../redux/store";

const Chat = ({ setShowChat, showChat, prevChats }) => {
  const [chats, setChats] = useState([]);
  let reduxState = useSelector((state) => state);
  let dispatch = useDispatch();
  useEffect(() => {
    if (prevChats.length !== 0) {
      setChats(prevChats);
    }

    reduxState.forChatInfo.client.subscribe(
      `/queue/chat/message/${reduxState.forChatInfo.roomno}`,
      (message) => {
        console.log(JSON.parse(message.body));
        dispatch(addChatPreview([JSON.parse(message.body)]));
        if (JSON.parse(message.body).authid !== sessionStorage.getItem("username")) {
          let newChat = {
            // missage: message.body.missage,
            missage: JSON.parse(message.body).missage,
            authid: JSON.parse(message.body).authid,
            senddate: JSON.parse(message.body).senddate,
          };
          setChats((curr) => [...curr, newChat]);
        }
      }
    );
  }, [prevChats]);
  let inputRef = useRef();
  return (
    <Container>
      <Content>
        <Title>
          {reduxState.forChatInfo.id}님과 대화
          <div
            className="chat__close-btn"
            onClick={(e) => {
              e.stopPropagation();
              setShowChat(false);
            }}
          >
            <FontAwesomeIcon icon={faXmark} />
          </div>
        </Title>
        <Body>
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
                    {val.authid === sessionStorage.getItem("username") ? (
                      <>
                        <span className="chatBox__time-span">
                          {(new window.Date(val.senddate).getHours() >= 12 &&
                          new window.Date(val.senddate).getMinutes() >= 1
                            ? "오후"
                            : "오전") +
                            (new window.Date(val.senddate).getHours() > 12
                              ? new window.Date(val.senddate).getHours() - 12
                              : new window.Date(val.senddate).getHours()) +
                            ":" +
                            new window.Date(val.senddate).getMinutes().toString().padStart(2, "0")}
                        </span>

                        <span className="chatBox__span">
                          {val.missage}
                          <br />
                        </span>
                      </>
                    ) : (
                      <>
                        <span className="chatBox__span">
                          {val.missage}
                          <br />
                        </span>
                        <span className="chatBox__time-span">
                          {(new window.Date(val.senddate).getHours() >= 12 &&
                          new window.Date(val.senddate).getMinutes() >= 1
                            ? "오후"
                            : "오전") +
                            new window.Date(val.senddate).getHours() +
                            ":" +
                            new window.Date(val.senddate).getMinutes()}
                        </span>
                      </>
                    )}
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
                  senddate: new Date(),
                };
                setChats((curr) => [...curr, newChat]);
                sendChat(inputRef, reduxState);
                e.target.value = "";
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
