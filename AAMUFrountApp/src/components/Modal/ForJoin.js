import styled from "styled-components";

const Container = styled.div`
  position: fixed;
  // background: url();
  // background-image: url("/images/window.jpg");
  // background-repeat: no-repeat;
  // background-size: cover;
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
  width: 25%;
  height: 600px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 3rem;
  // border: 1px solid rgba(250, 163, 7, 0.5);
  border: 1px solid rgba(255, 255, 255, 0.5);
  padding: 0 30px;
  border-radius: 15px;
  box-shadow: var(--shadow);
  margin: auto;
  background: rgba(255, 255, 255, 0.6);
`;
const Title = styled.div`
  margin-top: 60px;
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 5px;
  border: 1px solid red;
`;
const Body = styled.div`
  width: 100%;
  height: auto;
  border: 1px solid blue;
`;
export { Container, Overlay, Content, Title, Body };
