import React, { useState } from "react";
import "./CreatePlan.css";
import CreatePlanMap from "./CreatePlanMap";
import CreatePlanLeft from "./CreatePlanLeft.js";
import { faRectangleXmark, faX } from "@fortawesome/free-solid-svg-icons";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCalendarPlus,
  faEnvelope,
  faFileExcel,
} from "@fortawesome/free-regular-svg-icons";
import {
  DimmedContainer,
  Modal,
  TitleBar,
  Contents,
  CloseBtn,
} from "./CratePlanModal";
import {
  AuSBtn,
  AusBtnContainer,
  AuSModal,
  DimmedAuSContainer,
  DimmedSavePlanContainer,
  SavePlanBtnContainer,
  SavePlanModal,
} from "../Modal/AreUSurePlanModal";
import axios from "axios";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
const CreatePlan = ({
  setShowCratePlan,
  showCreatePlan,
  currPosition,
  fromWooJaeData,
  setFromWooJaeData,
}) => {
  const [forDayLine, setForDayLine] = useState(0);
  const [savePlan, setSavePlan] = useState(false);
  const [auSure, setAuSure] = useState(false);

  return (
    <div>
      <DimmedContainer>
        <Modal>
          <TitleBar>
            AAMU
            <CloseBtn
              onClick={() => {
                setAuSure(true);
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
              setForDayLine={setForDayLine}
            />

            <CreatePlanMap
              // showCreatePlan={showCreatePlan}
              setSavePlan={setSavePlan}
              currPosition={currPosition}
              fromWooJaeData={fromWooJaeData}
              forDayLine={forDayLine}
            />
          </Contents>
        </Modal>
        {savePlan && (
          <SavePlan
            auSure={auSure}
            setAuSure={setAuSure}
            setSavePlan={setSavePlan}
            fromWooJaeData={fromWooJaeData}
            currPosition={currPosition}
          />
        )}
        {auSure && (
          <AuSureModal
            setShowCratePlan={setShowCratePlan}
            setAuSure={setAuSure}
          />
        )}
      </DimmedContainer>
    </div>
  );
};

const SavePlan = ({ setSavePlan, fromWooJaeData, currPosition }) => {
  let reduxState = useSelector((state) => {
    return state;
  });
  let navigate = useNavigate();
  return (
    <DimmedAuSContainer
      onClick={() => {
        setSavePlan(false);
      }}
    >
      <SavePlanModal>
        <FontAwesomeIcon
          className="savePlan__close"
          icon={faX}
          onClick={() => {
            setSavePlan(false);
          }}
        />
        <SavePlanBtnContainer>
          <div
            className="savePlan__icon-container"
            onClick={() => {
              let bol = window.confirm("일정을 저장하시겠습니까?");
              if (bol)
                savePlan(reduxState, currPosition, fromWooJaeData, navigate);
            }}
          >
            <FontAwesomeIcon icon={faCalendarPlus} className="savePlan__icon" />
            <span className="savePlan__comment">일정저장</span>
          </div>
          <div className="savePlan__icon-container">
            <FontAwesomeIcon icon={faFileExcel} className="savePlan__icon" />
            <span className="savePlan__comment">엑셀로 다운로드</span>
          </div>
          <div className="savePlan__icon-container">
            <FontAwesomeIcon icon={faEnvelope} className="savePlan__icon" />
            <span className="savePlan__comment">이메일로 받기</span>
          </div>
        </SavePlanBtnContainer>
      </SavePlanModal>
    </DimmedAuSContainer>
  );
};

const AuSureModal = ({ setShowCratePlan, setAuSure }) => {
  return (
    <DimmedSavePlanContainer>
      <AuSModal>
        <div>현재 창을 닫으시면 일정이 저장되지 않습니다.</div>
        <div> 창을 닫으시겠습니까?</div>
        <AusBtnContainer>
          <AuSBtn
            onClick={() => {
              setShowCratePlan(false);
            }}
          >
            확인
          </AuSBtn>
          <AuSBtn
            onClick={() => {
              setAuSure(false);
            }}
          >
            취소
          </AuSBtn>
        </AusBtnContainer>
      </AuSModal>
    </DimmedSavePlanContainer>
  );
};

const savePlan = (reduxState, currPosition, fromWooJaeData, navigate) => {
  let toWoo = [];
  reduxState.tripPeriod.forEach((val, i) => {
    Object.values(fromWooJaeData[i])[0].forEach((obj, idx) => {
      toWoo.push(obj);
    });
  });
  let token = sessionStorage.getItem("token");
  axios
    .post(
      "/aamurest/planner/edit",
      {
        title: `${currPosition} ${reduxState.tripPeriod.length - 1}박 ${
          reduxState.tripPeriod.length
        }일`,
        route: toWoo,
        id: sessionStorage.getItem("username"),
      },
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )
    .then((resp) => {
      if (resp.data === 1) {
        alert("일정이 저장되었습니다");
        let bool = window.confirm("마이페이지로 이동하겠습니까?");
        if (bool) navigate("/myPage");
      } else {
        alert("저장오류가 발생했습니다. 관리자에게 문의하세요");
      }
      console.log(resp.data);
    })
    .catch((error) => {
      console.log(error);
    });
};
export default CreatePlan;
