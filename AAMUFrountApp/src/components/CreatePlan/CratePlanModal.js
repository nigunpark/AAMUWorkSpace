import styled from "styled-components";

const DimmedContainer = styled.div`
  position: fixed;
  width: 100%;
  height: 100%;
  z-index: 1000;
  top: 0;
  left: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.6);
`;
const Modal = styled.div`
  position: relative;
  background: white;
  width: 98%;
  height: 97%;
`;

const TitleBar = styled.div`
  text-align: center;
  background: inherit;
  box-shadow: var(--shadow);
  height: 3%;
  width: 100%;
`;

const Contents = styled.div`
  position: absolute;
  display: grid;
  grid-template-columns: 350px auto;
  padding: 3px;
  width: 100%;
  height: 97%;
`;
const CloseBtn = styled.div`
  position: absolute;
  margin-right: 10px;
  right: 0;
  top: 0;
  padding: 0 3px;
  width: auto;
  height: auto;
  cursor: pointer;
`;
export { DimmedContainer, Modal, TitleBar, Contents, CloseBtn };
