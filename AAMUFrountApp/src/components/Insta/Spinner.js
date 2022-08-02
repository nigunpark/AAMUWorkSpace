import React from "react";
import styled from "styled-components";

function Spinner() {
  return (
    <Container>
      <Overlay />
      <lottie-player
        src="https://assets9.lottiefiles.com/packages/lf20_cewufpii.json"
        background="transparent"
        speed="1"
        style={{ width: "150px", height: "150px", zIndex: "202", transition: ".3s" }}
        loop
        autoplay
      ></lottie-player>
    </Container>
  );
}
const Container = styled.div`
  position: fixed;
  width: 100%;
  height: 100%;
  z-index: 201;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: 0.3s;
`;
const Overlay = styled.div`
  position: absolute;
  width: 100%;
  height: 100%;
  z-index: 201;
  background-color: rgba(255, 255, 255, 0.8);
  transition: 0.3s;
`;
export default Spinner;
