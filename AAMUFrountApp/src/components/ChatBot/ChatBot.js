import React from "react";
import "./ChatBot.css";
import styled from "styled-components";
const ChatBot = () => {
  return (
    <div>
      <Content></Content>
    </div>
  );
};

const Container = styled.div``;
const Content = styled.div`
  position: relative;
  background: white;
  z-index: 106;
  width: 150px;
  height: 300px;
`;

export default ChatBot;
