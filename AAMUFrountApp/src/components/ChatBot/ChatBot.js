import React from "react";
import "./ChatBot.css";
import styled, { keyframes } from "styled-components";
const ChatBot = () => {
  return (
    <Container>
      <Content>
        <Title>챗봇에게 무엇이든 물어보세요</Title>
        <Body>내용</Body>
        <Body>내용</Body>
        <div className="chatBot__input-container">
          <input type="text" className="chatBot__input" />
          <span className="chatBot__input-btn">보내기</span>
        </div>
      </Content>
    </Container>
  );
};
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
  z-index: 9999;
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
`;

export default ChatBot;