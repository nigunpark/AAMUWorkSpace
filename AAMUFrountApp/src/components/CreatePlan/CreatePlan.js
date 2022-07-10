import React from "react";
import "./CreatePlan.css";
import CreatePlanMap from "./CreatePlanMap";
import CreatePlanLeft from "./CreatePlanLeft.js";
import { faRectangleXmark } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  DimmedContainer,
  Modal,
  TitleBar,
  Contents,
  CloseBtn,
} from "./CratePlanModal";
const CreatePlan = ({
  setShowCratePlan,
  showCreatePlan,
  currPosition,
  fromWooJaeData,
  setFromWooJaeData,
}) => {
  return (
    <div>
      <DimmedContainer>
        <Modal>
          <TitleBar>
            AAMU
            <CloseBtn
              onClick={() => {
                setShowCratePlan(false);
              }}
            >
              <FontAwesomeIcon icon={faRectangleXmark} />
            </CloseBtn>
          </TitleBar>
          <Contents>
            <CreatePlanLeft
              currPosition={currPosition}
              fromWooJaeData={fromWooJaeData}
              setFromWooJaeData={setFromWooJaeData}
            />
            <CreatePlanMap
              showCreatePlan={showCreatePlan}
              currPosition={currPosition}
            />
          </Contents>
        </Modal>
      </DimmedContainer>
    </div>
  );
};

export default CreatePlan;
