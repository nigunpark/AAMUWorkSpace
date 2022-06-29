import React from "react";
import "./CreatePlan.css";
import styled from "styled-components";
import CreatePlanMap from "../KMap/CreatePlanMap.js";
import CreatePlanLeft from "./CreatePlanLeft.js";
import KMap from "../KMap/KMap";

const CreatePlan = ({ setShowCratePlan, showCreatePlan }) => {
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
    height: 95%;
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
  return (
    <div>
      <DimmedContainer>
        <Modal>
          <TitleBar>
            AAMU
            <CloseBtn
              onClick={() => {
                setShowCratePlan(false);
              }}>
              X
            </CloseBtn>
          </TitleBar>
          <Contents>
            <CreatePlanLeft />
            {/* <KMap /> */}
            <CreatePlanMap showCreatePlan={showCreatePlan} />
          </Contents>
        </Modal>
      </DimmedContainer>
    </div>
  );
};

export default CreatePlan;
