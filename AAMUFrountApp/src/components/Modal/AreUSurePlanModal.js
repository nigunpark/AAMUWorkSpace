import styled from "styled-components";

const DimmedAuSContainer = styled.div`
  position: fixed;
  width: 100%;
  height: 100%;
  z-index: 2000;
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
  z-index: 2100;
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
  z-index: 10;
`;

const SavePlanModal = styled.div`
  position: relative;
  background: white;
  width: 640px;
  height: 340px;
  padding: 30px 50px;
  border-radius: 5px;
  display: flex;
  justify-content: center;
  align-items: center;
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
  AusBtnContainer,
  AuSBtn,
  SavePlanModal,
};
