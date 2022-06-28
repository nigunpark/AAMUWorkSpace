import styled, { keyframes } from "styled-components";
const modalAniLim = keyframes`
from{
  opacity:0;
}
to{
  opacity:1;
  transfrom:none;
}
`;
//LIM = LocalInfoModal
const Container_LoRegi = styled.div`
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

const Overlay_LoRegi = styled.div`
  position: absolute;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.6);
`;

const Contents_LoRegi = styled.div`
  position: relative;
  top: 0px;
  padding: 0 auto;
  margin: auto;
  border-radius: 10px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.19), 0 6px 6px rgba(0, 0, 0, 0.23);
  background: white;
  width: 575px;
  height: 425px;
  animation: ${modalAniLim} 0.6s ease forwards;
`;

const Title_LoRegi = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  font-size: 1.5em;
  width: 100%;
  margin-top: 30px;
  margin-botttm: 15px;
  overflow: auto;
  height: auto;
  border: 1px solid blue;
  overflow: hidden;
  gap: 1rem;
`;

const Img_LoRegi = styled.div`
  position: absolute;
  width: 35%;
  height: 97%;
  background-image: url(/images/img-4.jpg);
  background-size: cover;
  background-repeat: no-repeat;
  margin: 5px;
  border-radius: 10px;
`;

const Close_LoRegi = styled.div`
  position: absolute;
  margin-right: 15px;
  color: gray;
  right: 0;
  top: 3px;
  font-size: 20px;
  &:hover {
    cursor: pointer;
  }
`;

const Body_LoRegi = styled.div`
  position: absolute;
  width: 100%;
  height: auto%;

  border: 2px solid green;
`;

export {
  Container_LoRegi,
  Contents_LoRegi,
  Overlay_LoRegi,
  Title_LoRegi,
  Close_LoRegi,
  Body_LoRegi,
  Img_LoRegi,
};
