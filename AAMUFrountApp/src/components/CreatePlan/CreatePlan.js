import React, { useRef, useState } from "react";
import "./CreatePlan.css";
import CreatePlanMap from "./CreatePlanMap";
import CreatePlanLeft from "./CreatePlanLeft.js";
import { faRectangleXmark, faX } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCalendarPlus,
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
  AuSModal2,
  DimmedAuSContainer,
  DimmedSavePlanContainer,
  SavePlanBtnContainer,
  SavePlanModal,
} from "../Modal/AreUSurePlanModal";
import axios from "axios";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { saveAs } from "file-saver";
import * as ExcelJS from "exceljs";
const CreatePlan = ({
  setShowCratePlan,
  currPosition,
  fromWooJaeData,
  setFromWooJaeData,
}) => {
  const [forDayLine, setForDayLine] = useState(0);
  const [savePlan, setSavePlan] = useState(false);
  const [auSure, setAuSure] = useState(false);
  let reduxState = useSelector((state) => {
    return state;
  });
  console.log("reduxState.monthNdate", reduxState.monthNdate);
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
            {auSure && (
              <AuSureModal
                setShowCratePlan={setShowCratePlan}
                setAuSure={setAuSure}
              />
            )}
            <CreatePlanMap
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
      </DimmedContainer>
    </div>
  );
};

const SavePlan = ({ setSavePlan, fromWooJaeData, currPosition }) => {
  let reduxState = useSelector((state) => {
    return state;
  });
  // console.log('reduxState.monthNdate',reduxState.monthNdate)
  let navigate = useNavigate();
  return (
    <DimmedAuSContainer>
      <SavePlanModal>
        <FontAwesomeIcon
          className="savePlan__close"
          icon={faX}
          onClick={(e) => {
            e.stopPropagation();
            setSavePlan(false);
          }}
        />
        <SavePlanBtnContainer>
          <div
            className="savePlan__icon-container"
            onClick={(e) => {
              e.stopPropagation();
              let bol = window.confirm("일정을 저장하시겠습니까?");
              if (bol)
                savePlan(reduxState, currPosition, fromWooJaeData, navigate);
            }}
          >
            <FontAwesomeIcon icon={faCalendarPlus} className="savePlan__icon" />
            <span className="savePlan__comment">일정저장</span>
          </div>
          <div
            className="savePlan__icon-container"
            onClick={(e) => {
              e.stopPropagation();
              handleExcel(fromWooJaeData, reduxState, currPosition);
            }}
          >
            <FontAwesomeIcon icon={faFileExcel} className="savePlan__icon" />
            <span className="savePlan__comment">엑셀로 다운로드</span>
          </div>
        </SavePlanBtnContainer>
      </SavePlanModal>
    </DimmedAuSContainer>
  );
};
const handleExcel = async (fromWooJaeData, reduxState, currPosition) => {
  let xlData = [];
  let plannerDate = [];
  reduxState.tripPeriod.forEach((val, i) => {
    Object.values(fromWooJaeData[i])[0].forEach((obj, idx) => {
      xlData.push(obj);
    });
  });

  //x월x일~ x월x일 구하는 로직
  reduxState.tripPeriod.map((val, i) => {
    if (i === 0 || i === reduxState.tripPeriod.length - 1)
      plannerDate.push(
        `${reduxState.monthNdate[0].month}월 ${
          reduxState.monthNdate[0].date + i
        }일 ${getDow(reduxState, i)}`
      );
  });

  const workBook = new ExcelJS.Workbook();
  const workSheet = workBook.addWorksheet("MyTravel"); // sheet 이름이 My Sheet
  //타이틀
  workSheet.mergeCells("B1:F1");
  workSheet.getCell("B1").value = `${currPosition} ${
    reduxState.tripPeriod.length - 1
  }박 ${reduxState.tripPeriod.length}일 여행`;
  workSheet.getCell("B1").font = { size: 16, bold: true };
  workSheet.getCell("B1").alignment = { horizontal: "center" };
  workSheet.getCell("B1").fill = {
    type: "pattern",
    pattern: "solid",
    fgColor: { argb: "FFFAA307" },
    bgColor: { argb: "FF000000" },
  };
  //기간
  workSheet.mergeCells("B2:F2");
  workSheet.getCell("B2").value = plannerDate[0] + "~" + plannerDate[1];
  workSheet.getCell("B2").font = { size: 14, bold: true };
  workSheet.getCell("B2").alignment = { horizontal: "center" };

  reduxState.tripPeriod.map((val, i) => {
    if (i === 0) {
      workSheet.columns = [
        {
          key: "arrTime",
          width: 10,
          style: {
            font: { size: 13 },
            alignment: { horizontal: "center" },
          },
        },
        {
          key: "stTime",
          width: 10,
          style: {
            font: { size: 13 },
            alignment: { horizontal: "center" },
          },
        },
        {
          key: "location",
          width: 32,
          style: {
            font: { size: 13 },
            alignment: { horizontal: "center" },
          },
        },
        {
          key: "addr",
          width: 50,
          style: {
            font: { size: 13 },
            alignment: { horizontal: "center" },
          },
        },
        {
          key: "memo",
          width: 20,
          style: {
            font: { size: 13 },
            alignment: { horizontal: "center" },
          },
        },
      ];
      workSheet.addRow([]);
      workSheet.addRow([`DAY ${i + 1}`]);
      workSheet.addRow(["도착시간", "출발시간", "장소", "주소", "메모"]);
    } else if (i !== 0) {
      workSheet.addRow([]);
      workSheet.addRow([`DAY ${i + 1}`]);
      workSheet.addRow(["도착시간", "출발시간", "장소", "주소", "메모"]);
    }

    let dayWholeBb = reduxState.wholeBlackBox.filter((val) => {
      return val.day === i + 1;
    });

    let dayData = xlData.filter((val) => {
      return val.day === i + 1;
    });

    let data = [];
    dayWholeBb.forEach((val, idx) => {
      data.push({
        arrTime: `${dayWholeBb[idx].stime} :${dayWholeBb[idx].smin}`,
        stTime: `${dayWholeBb[idx].etime} :${dayWholeBb[idx].emin}`,
        location: `${dayData[idx].dto.title}`,
        addr: `${dayData[idx].dto.addr}`,
        memo: `${dayData[idx].comment ?? ""}`,
      });
    });

    workSheet.addRows(data);
  });

  // 다운로드
  const mimeType = {
    type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
  };
  const buffer = await workBook.xlsx.writeBuffer();
  const blob = new Blob([buffer], mimeType);
  saveAs(blob, "MyTravel.xlsx");
};

const AuSureModal = ({ setShowCratePlan, setAuSure }) => {
  return (
    <DimmedSavePlanContainer>
      <AuSModal2>
        <h4>현재 창을 닫으시면 일정이 저장되지 않습니다.</h4>
        <h4> 창을 닫으시겠습니까?</h4>
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
      </AuSModal2>
    </DimmedSavePlanContainer>
  );
};

const savePlan = (reduxState, currPosition, fromWooJaeData, navigate) => {
  let toWoo = [];
  let plannerDate = [];
  reduxState.tripPeriod.forEach((val, i) => {
    Object.values(fromWooJaeData[i])[0].forEach((obj, idx) => {
      toWoo.push(obj);
    });
  });
  reduxState.tripPeriod.map((val, i) => {
    if (i === 0 || i === reduxState.tripPeriod.length - 1)
      plannerDate.push(
        `${reduxState.monthNdate[0].month}월 ${
          reduxState.monthNdate[0].date + i
        }일 ${getDow(reduxState, i)}`
      );
  });
  // console.log("toWoo", toWoo);
  // console.log(
  //   "title",
  //   `${currPosition} ${reduxState.tripPeriod.length - 1}박 ${
  //     reduxState.tripPeriod.length
  //   }일`
  // );
  // console.log(
  //   "plannerdate",
  //   `${plannerDate[0]} ~ ${plannerDate[plannerDate.length - 1]}`
  // );
  let token = sessionStorage.getItem("token");
  axios
    .post(
      "/aamurest/planner/edit",
      {
        title: `${currPosition} ${reduxState.tripPeriod.length - 1}박 ${
          reduxState.tripPeriod.length
        }일`,
        plannerdate: `${plannerDate[0]} ~ ${
          plannerDate[plannerDate.length - 1]
        }`,
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
    })
    .catch((error) => {
      alert("저장오류가 발생했습니다. 관리자에게 문의하세요");
      console.log(error);
    });
};

function getDow(reduxState, i) {
  switch (reduxState.monthNdate[0].dow + i) {
    case 0:
      return "일";

    case 1:
      return "월";

    case 2:
      return "화";

    case 3:
      return "수";

    case 4:
      return "목";

    case 5:
      return "금";

    default:
      return "토";
  }
}
export default CreatePlan;
