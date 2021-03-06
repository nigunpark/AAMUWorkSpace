import styled from "styled-components";

const DimmedAuSContainer = styled.div`
  position: fixed;
  width: 100%;
  height: 100%;
  z-index: 1200;
  top: 0;
  left: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.6);
`;

const DimmedSavePlanContainer = styled.div`
  position: fixed;
  width: 100%;
  height: 100%;
  z-index: 1227;
  top: 0;
  left: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.6);
`;

const AuSModal = styled.div`
  position: absolute;
  background: white;
  width: 400px;
  height: 125px;
  padding: 30px 50px;
  border-radius: 5px;
  z-index: 102;
`;
const AuSModal2 = styled.div`
  position: absolute;
  background: white;
  width: 400px;
  height: 155px;
  padding: 30px 30px;
  border-radius: 5px;
`;

const SavePlanModal = styled.div`
  position: relative;
  background: white;
  width: 500px;
  height: 340px;
  padding: 30px 50px;
  border-radius: 5px;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const ToEmailModal = styled.div`
  position: relative;
  background: white;
  width: 400px;
  height: 155px;
  padding: 30px 30px;
  border-radius: 5px;
  z-index: 1300;
`;

const AusBtnContainer = styled.div`
  position: absolute;
  display: flex;
  justify-self: center;
  gap: 1rem;
  margin-top: 15px;
  width: auto;
  height: auto;
  left: 70px;
  right: auto;
`;

const SavePlanBtnContainer = styled.div`
  position: absolute;
  display: flex;
  justify-self: center;
  gap: 1rem;
  width: auto;
  height: auto;
`;

const AuSBtn = styled.div`
  padding: 8px 40px;
  background: var(--orange);
  color: white;
  cursor: pointer;
  border-radius: 5px;
`;

export {
  DimmedAuSContainer,
  SavePlanBtnContainer,
  DimmedSavePlanContainer,
  AuSModal,
  AuSModal2,
  AusBtnContainer,
  AuSBtn,
  SavePlanModal,
  ToEmailModal,
};
