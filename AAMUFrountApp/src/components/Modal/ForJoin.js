import styled, { keyframes } from "styled-components";

const flip = keyframes`
0% {
  box-shadow: 0 0 #3e3e3e, 0 0 #3e3e3e, 0 0 #3e3e3e, 0 0 #3e3e3e, 0 0 #3e3e3e, 0 0 #3e3e3e, 0 0 #3e3e3e, 0 0 #3e3e3e;
  transform: translateX(0) translateY(0);
}
100% {
  box-shadow: 1px -1px #3e3e3e, 2px -2px #3e3e3e, 3px -3px #3e3e3e, 4px -4px #3e3e3e, 5px -5px #3e3e3e, 6px -6px #3e3e3e, 7px -7px #3e3e3e, 8px -8px #3e3e3e;
  transform: translateX(-8px) translateY(8px);
}
`;

const Container = styled.div`
  position: fixed;
  background-image: url("/images/tropical.jpg");
  background-repeat: no-repeat;
  background-size: cover;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  // filter: blur(2px);
`;
const Overlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
`;
const Content = styled.div`
  position: relative;
  width: 470px;
  height: 610px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
  border: 1px solid rgba(0, 0, 0, 0.2);
  padding: 0 30px;
  border-radius: 15px;
  box-shadow: var(--shadow);
  margin: auto;
  background: rgba(255, 255, 255, 0.8);
  animation: ${flip} 0.5s cubic-bezier(0.47, 0, 0.745, 0.715) both;
`;
const ContentStep2 = styled.div`
  position: relative;
  width: 470px;
  height: 800px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
  border: 1px solid rgba(0, 0, 0, 0.2);
  padding: 0 30px;
  border-radius: 15px;
  box-shadow: var(--shadow);
  margin: auto;
  background: rgba(255, 255, 255, 0.8);
  animation: ${flip} 0.5s cubic-bezier(0.47, 0, 0.745, 0.715) both;
`;

const Title = styled.div`
  margin-top: 40px;
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 5px;
`;
const Body = styled.div`
  position: relative;
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: column;
  gap: 2rem;
`;

const Footer = styled.div`
  position: absolute;
  width: 100%;
  hight: auto;
  bottom: 20px;
`;
export { Container, Overlay, Content, ContentStep2, Title, Body, Footer };
