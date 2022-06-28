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
const ContainerLim = styled.div`
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

const OverlayLim = styled.div`
  position: absolute;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.6);
`;

const ContentsLim = styled.div`
  position: relative;
  top: 0px;
  padding: 0 auto;
  margin: auto;
  border-radius: 10px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.19), 0 6px 6px rgba(0, 0, 0, 0.23);
  background: rgba(255, 255, 255, 0.8);
  width: 855px;
  height: 300px;
  animation: ${modalAniLim} 0.6s ease forwards;
`;

const TitleLim = styled.div`
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
  overflow: hidden;
`;

const ImgLim = styled.div`
  position: absolute;
  width: 35%;
  height: 97%;
  background-image: url(/images/img-4.jpg);
  background-size: cover;
  background-repeat: no-repeat;
  margin: 5px;
  border-radius: 10px;
`;

const CloseLim = styled.div`
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

const BodyLim = styled.div`
  position: absolute;
  width: 55%;
  height: 97%;
  margin-left: 320px;
  margin-top: 5px;
  padding: 13px;
`;

export {
  ContainerLim,
  ContentsLim,
  OverlayLim,
  TitleLim,
  CloseLim,
  BodyLim,
  ImgLim,
};
