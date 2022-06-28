import styled, { keyframes } from "styled-components";
const modalAni = keyframes`
from{
  opacity:0;
}
to{
  opacity:1;
  transfrom:none;
}
`;

const Container = styled.div`
  position: fixed;
  width: 100%;
  height: 100%;
  z-index: 1000;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const Overlay = styled.div`
  position: absolute;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.6);
`;

const Contents = styled.div`
  position: relative;
  top: 0px;
  padding: 0 auto;
  border-radius: 10px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.19), 0 6px 6px rgba(0, 0, 0, 0.23);
  background: rgba(0, 0, 0, 0.8);
  text-align: center;
  width: 25%;
  height: 700px;
  animation: ${modalAni} 0.6s ease forwards;
`;

const Title = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 1.5em;

  width: 100%;
  overflow: auto;
  height: 10%;
  border-radius: 10px;
  color: white;
  background: rgba(0, 0, 0, 0.8);
`;

const Close = styled.div`
  position: absolute;
  margin-right: 15px;
  color: gray;
  right: 0;

  &:hover {
    cursor: pointer;
  }
`;

const Body = styled.div`
  margin: 10px 10px 10px 10px;
`;

export { Container, Contents, Overlay, Title, Close, Body };
