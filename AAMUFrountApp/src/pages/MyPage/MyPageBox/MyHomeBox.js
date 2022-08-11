import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { useEffect, useState } from "react";
import styled from "styled-components";
import { saveAs } from "file-saver";
import * as ExcelJS from "exceljs";
import {
  faFlagCheckered,
  faRectangleXmark,
  faShareNodes,
  faSquareShareNodes,
  faX,
} from "@fortawesome/free-solid-svg-icons";
import {
  faCalendarPlus,
  faFileExcel,
} from "@fortawesome/free-regular-svg-icons";
import MyCreatePlanLeft from "./MyCreatePlanLeft";
import CreatePlanMap from "../../../components/CreatePlan/CreatePlanMap";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import MyPlanMap from "./MyPlanMap";
import { useDispatch, useSelector } from "react-redux";
import { faHourglass } from "@fortawesome/free-regular-svg-icons";
import {
  addMonthNDate,
  changeTripPeriod,
  resetMonthNDate,
} from "../../../redux/store";

const MyHomeBox = ({
  setClickTab,
  planList,
  rbn,
  setSelectRbn,
  setPlanList,
  setUpload,
}) => {
  const [newFromWooJae, setNewFromWooJae] = useState([]);
  const [newTimeSet, setNewTimeSet] = useState([]);
  const [currPosition, setCurrPosition] = useState("");
  const [isOpen, setIsOpen] = useState(false);
  const postDate = new Date(planList.routeDate);
  const [forDimmed, setForDimmed] = useState({});
  // console.log("MyHomeBox :", rbn);
  let reduxState = useSelector((state) => state);
  let dispatch = useDispatch();

  const [modalOpen, setModalOpen] = useState(false);
  const onModalSelect = () => {
    setModalOpen(true);
  };
  // console.log("planList :", planList.isBBS);

  useEffect(() => {
    let sMonth = planList.plannerdate.substring(
      planList.plannerdate.indexOf("월") - 2,
      planList.plannerdate.indexOf("월")
    );
    let sDate = planList.plannerdate.substring(
      planList.plannerdate.indexOf("일") - 2,
      planList.plannerdate.indexOf("일")
    );
    let eMonth = planList.plannerdate.substring(
      planList.plannerdate.lastIndexOf("월") - 2,
      planList.plannerdate.lastIndexOf("월")
    );
    let eDate = planList.plannerdate.substring(
      planList.plannerdate.lastIndexOf("일") - 2,
      planList.plannerdate.lastIndexOf("일")
    );
    let start = new Date(`${new Date().getFullYear()}-${sMonth}-${sDate}`);
    let end = new Date(`${new Date().getFullYear()}-${eMonth}-${eDate}`);
    setForDimmed({ start, end });
  }, []);
  function doneOrStillOpacity() {
    if (forDimmed.end < new Date()) {
      return "0.3";
    } else {
      return "1";
    }
  }
  return (
    <>
      {modalOpen == true ? (
        <MyBoxList
          setModalOpen={setModalOpen}
          rbn={rbn}
          setNewFromWooJae={setNewFromWooJae}
          dispatch={dispatch}
          setCurrPosition={setCurrPosition}
          setIsOpen={setIsOpen}
          setSelectRbn={setSelectRbn}
          setClickTab={setClickTab}
          bbs={planList.isBBS}
          setPlanList={setPlanList}
          setUpload={setUpload}
        />
      ) : null}
      {/* 홈에 표시되는 박스 */}

      <div className="MyBoxContainer" onClick={onModalSelect}>
        <div className="MyBox">
          <div className="myBox__img-container">
            <img className="instaImg" src={planList.smallImage} />
          </div>

          <div>
            <div
              style={{
                display: "flex",
                justifyContent: "space-between",
                alignItems: "center",
              }}
            >
              <div className="myBox__title">{planList.title}</div>
              <div className="myBox__icons">
                {forDimmed.end < new Date() && (
                  <FontAwesomeIcon
                    icon={faFlagCheckered}
                    style={{
                      background: "red",
                      color: "white",
                      padding: "2px",
                      borderRadius: "5px",
                    }}
                  />
                )}
                {forDimmed.start <= new Date() &&
                  forDimmed.end >= new Date() && (
                    <FontAwesomeIcon icon={faHourglass} className="glassIcon" />
                  )}

                {planList.isBBS == 1 ? (
                  // <img
                  //   style={{
                  //     position: "absolute",
                  //     width: "40px",
                  //     left: "85%",
                  //     top: "2%",
                  //   }}
                  //   className="starImg"
                  //   src="/images/star.png"
                  // />
                  // <FontAwesomeIcon icon={faHourglass} className="starImg" />

                  <FontAwesomeIcon
                    icon={faSquareShareNodes}
                    className="shareIcon"
                  />
                ) : null}
              </div>
            </div>
            <div>{planList.plannerdate}</div>
            <span
              style={{
                fontSize: "13px",
                fontWeight: "bold",
                color: "grey",
              }}
            >
              {planList.theme}
            </span>
          </div>
        </div>
      </div>
      {isOpen && (
        <MyDetailPlan
          setIsOpen={setIsOpen}
          currPosition={currPosition}
          setNewTimeSet={setNewTimeSet}
          newFromWooJae={newFromWooJae}
          setNewFromWooJae={setNewFromWooJae}
          newTimeSet={newTimeSet}
          planList={planList}
        />
      )}
    </>
  );
};

function MyBoxList({
  setModalOpen,
  rbn,
  setNewFromWooJae,
  dispatch,
  setCurrPosition,
  setIsOpen,
  setSelectRbn,
  setClickTab,
  bbs,
  setPlanList,
  setUpload,
}) {
  const bbsDelte = () => {
    let token = sessionStorage.getItem("token");
    axios
      .delete("/aamurest/bbs/edit", {
        params: {
          rbn: rbn,
        },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        if (resp.data) {
          alert("삭제 성공했습니다");
        } else alert("삭제 실패했습니다");
        console.log("삭제 성공 :", resp.data);
        selectList();
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const listDelete = () => {
    let token = sessionStorage.getItem("token");
    axios
      .delete("/aamurest/planner/edit", {
        params: {
          rbn: rbn,
        },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        console.log("삭제 성공 :", resp.data);
        selectList();
      })
      .catch((error) => {
        console.log(error);
      });
  };

  async function selectList() {
    let token = sessionStorage.getItem("token");

    await axios
      .get("/aamurest/planner/selectList", {
        params: {
          id: sessionStorage.getItem("username"),
        },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        setPlanList(resp.data);
        console.log("데이터 확인 : ", resp.data);

        setUpload(
          resp.data.reduce((acc, obj) => {
            const { isBBS } = obj;
            acc[isBBS] = acc[isBBS] ?? [];
            acc[isBBS].push(obj);
            return acc;
          }, {})
        );
      })
      .catch((error) => {
        console.log((error) => console.log("여행경로 가져오기 실패", error));
      });
  }

  return (
    <div className="myBox-List">
      <div className="myBox-List-overlay">
        <div
          className="myBox-List-Plan"
          onClick={() => {
            excAxios(rbn, setNewFromWooJae, dispatch, setCurrPosition);
            // getTimeSetArr(newFromWooJae, planList, setNewTimeSet, please);

            setTimeout(() => {
              setIsOpen(true);
              setModalOpen(false);
            }, 50);
          }}
        >
          일정확인
        </div>
        {bbs === 0 ? (
          <div
            className="myBox-List-Post"
            onClick={() => {
              setClickTab(10);
              setSelectRbn(rbn);
            }}
          >
            공유하기
          </div>
        ) : (
          <div
            className="myBox-List-Edit"
            onClick={() => {
              setClickTab(11);
              setSelectRbn(rbn);
            }}
          >
            수정하기
          </div>
        )}

        {bbs == 0 ? (
          <div
            className="myBox-List-Delete"
            onClick={() => {
              let bool = window.confirm("여행경로를 삭제 하시겠습니까?");
              if (bool) {
                listDelete();
                setModalOpen(false);
              } else {
                setModalOpen(false);
              }
            }}
          >
            여행경로 삭제
          </div>
        ) : (
          <div
            className="myBox-List-Post-Delete"
            onClick={() => {
              let bool = window.confirm("게시한 글을 삭제 하시겠습니까?");
              if (bool) {
                bbsDelte();
                setModalOpen(false);
              } else {
                setModalOpen(false);
              }
            }}
          >
            게시글 삭제
          </div>
        )}

        <div
          className="myBox-List-back"
          onClick={(e) => {
            setModalOpen(false);
          }}
        >
          취소
        </div>
      </div>
    </div>
  );
}

async function excAxios(
  rbn,
  setNewFromWooJae,
  dispatch,
  setCurrPosition,
  planList
) {
  try {
    let token = sessionStorage.getItem("token");
    //   /planner/selectonemap
    const resp = await axios.get("/aamurest/planner/selectonemap", {
      params: {
        rbn: rbn,
      },
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    console.log("resp.data", resp.data);
    setCurrPosition(resp.data.title.split(" ")[0]);

    let keys = Object.keys(resp.data.routeMap);
    let values = Object.values(resp.data.routeMap);
    let tempArr = keys.map((val, i) => {
      return { [keys[i]]: values[i] };
    });
    setNewFromWooJae(tempArr);
    let fristMonth = resp.data.plannerdate.substring(
      resp.data.plannerdate.indexOf("월") - 1,
      resp.data.plannerdate.indexOf("월")
    );
    let fristDate = resp.data.plannerdate.substring(
      resp.data.plannerdate.indexOf("일") - 2,
      resp.data.plannerdate.indexOf("일")
    );
    let fristDow = resp.data.plannerdate.substring(
      resp.data.plannerdate.indexOf("~") - 2,
      resp.data.plannerdate.indexOf("~") - 1
    );
    switch (fristDow) {
      case "일":
        fristDow = 0;
        break;
      case "월":
        fristDow = 1;
        break;
      case "화":
        fristDow = 2;
        break;
      case "수":
        fristDow = 3;
        break;
      case "목":
        fristDow = 4;
        break;
      case "금":
        fristDow = 5;
        break;
      case "토":
        fristDow = 6;
        break;
    }
    dispatch(resetMonthNDate([]));
    dispatch(
      addMonthNDate({ month: fristMonth, date: fristDate, dow: fristDow })
    );
  } catch (error) {
    console.log(error);
  }
}

function MyDetailPlan({
  setIsOpen,
  currPosition,
  setNewTimeSet,
  newFromWooJae,
  newTimeSet,
  planList,
  setNewFromWooJae,
}) {
  const [auSure, setAuSure] = useState(false);
  const [savePlan, setSavePlan] = useState(false);
  const [forDayLine, setForDayLine] = useState(0);
  let reduxState = useSelector((state) => state);
  let dispatch = useDispatch();
  useEffect(() => {
    let lengths = parseInt(
      planList.title.substring(
        planList.title.indexOf("일") - 1,
        planList.title.indexOf("일")
      )
    );
    let tempArr = new Array(lengths).fill(0);
    dispatch(changeTripPeriod(lengths));
    tempArr.forEach((val, index) => {
      let bbcT =
        newFromWooJae[index][`day${index + 1}`][0].starttime / (1000 * 60);
      if (bbcT >= 13 * 60) {
        let temp = {
          ampm: "오후",
          time: bbcT / 60 - 12,
          fullDate: new Date(`2022-01-01 ${bbcT / 60}:${bbcT % 60}`),
          min: bbcT % 60,
          day: index + 1,
        };
        setNewTimeSet((curr) => [...curr, temp]);
      } else {
        let temp = {
          ampm: "오전",
          time: bbcT / 60,
          fullDate: new Date(`2022-01-01 ${bbcT / 60}:${bbcT % 60}`),
          min: bbcT % 60,
          day: index + 1,
        };
        setNewTimeSet((curr) => [...curr, temp]);
      }
    });
  }, []);
  return (
    <DimmedContainer>
      <Modal>
        <TitleBar>
          AAMU
          <CloseBtn
            onClick={() => {
              // setAuSure(true);
              setIsOpen(false);
            }}
          >
            <FontAwesomeIcon icon={faRectangleXmark} />
          </CloseBtn>
        </TitleBar>
        <Contents>
          <MyCreatePlanLeft
            currPosition={currPosition} //지역명 넣기
            fromWooJaeData={newFromWooJae} //routeMap [{},{},{}] 형식으로 가공해야함
            setFromWooJaeData={setNewFromWooJae}
            setForDayLine={setForDayLine}
            newTimeSet={newTimeSet}
          />
          {/* {auSure && (
            <AuSureModal
              setIsOpen={setIsOpen}
              // setShowCratePlan={setShowCratePlan}
              setAuSure={setAuSure}
            />
          )} */}
          <MyPlanMap
            setSavePlan={setSavePlan}
            currPosition={currPosition}
            fromWooJaeData={newFromWooJae}
            forDayLine={forDayLine}
          />
        </Contents>
      </Modal>
      {savePlan && (
        <SavePlan
          auSure={auSure}
          setAuSure={setAuSure}
          setSavePlan={setSavePlan}
          fromWooJaeData={newFromWooJae}
          currPosition={currPosition}
        />
      )}
    </DimmedContainer>
  );
}

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
              savePlan(reduxState, currPosition, fromWooJaeData, navigate);
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
  let token = sessionStorage.getItem("token");
  toWoo.map((val, i) => {
    val["ordno"] = i + 1;
  });
  console.log("toWoo", toWoo);
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
      } else {
        alert("저장오류가 발생했습니다. 관리자에게 문의하세요");
      }
    })
    .catch((error) => {
      alert("저장오류가 발생했습니다. 관리자에게 문의하세요");
      console.log(error);
    });
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
    console.log("dayData", dayData);
    console.log("dayWholeBb", dayWholeBb);
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

const AuSureModal = ({ setIsOpen, setAuSure }) => {
  return (
    <DimmedSavePlanContainer>
      <AuSModal2>
        <h4>현재 창을 닫으시면 일정이 저장되지 않습니다.</h4>
        <h4> 창을 닫으시겠습니까?</h4>
        <AusBtnContainer>
          <AuSBtn
            onClick={() => {
              setIsOpen(false);
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
const SavePlanBtnContainer = styled.div`
  position: absolute;
  display: flex;
  justify-self: center;
  gap: 1rem;
  width: auto;
  height: auto;
`;

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
const Contents = styled.div`
  position: absolute;
  display: grid;
  grid-template-columns: 350px auto;
  padding: 3px;
  width: 100%;
  height: 97%;
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
const AuSModal2 = styled.div`
  position: absolute;
  background: white;
  width: 400px;
  height: 155px;
  padding: 30px 30px;
  border-radius: 5px;
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
const AuSBtn = styled.div`
  padding: 8px 40px;
  background: var(--orange);
  color: white;
  cursor: pointer;
  border-radius: 5px;
`;

export default MyHomeBox;
