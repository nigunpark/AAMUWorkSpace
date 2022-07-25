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

const reviewSlide = keyframes`
0% {
  transform: translateY(-10px);
  opacity: 0;
}
100% {
  transform: translateY(0);
  opacity: 1;
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
  top: -20px;
  padding: 0 auto;
  margin: auto;
  border-radius: 10px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.19), 0 6px 6px rgba(0, 0, 0, 0.23);
  background: rgba(255, 255, 255, 0.8);
  width: 900px;
  height: 370px;
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
  position: relative;
  width: 35%;
  height: 97%;
  margin: 5px;
  border-radius: 10px;
  overflow: hidden;
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
    color: var(--orange);
  }
`;

const BodyLim = styled.div`
  position: absolute;
  width: 55%;
  height: 97%;
  top: 3px;
  right: 50px;
  padding: 13px;
`;

const Review = styled.div`
  width: 900px;
  height: 150px;
  background: rgba(255, 255, 255, 0.8);
  border-radius: 5px;
  transition: 0.1s;
  overflow: auto;
  animation: ${reviewSlide} 0.5s cubic-bezier(0.25, 0.46, 0.45, 0.94) both;
`;

export {
  ContainerLim,
  ContentsLim,
  OverlayLim,
  TitleLim,
  CloseLim,
  BodyLim,
  ImgLim,
  Review,
};
