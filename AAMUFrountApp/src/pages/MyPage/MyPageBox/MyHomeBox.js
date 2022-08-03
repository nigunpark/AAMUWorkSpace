import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { useEffect, useState } from "react";
import styled from "styled-components";
import {
  faRectangleXmark,
  faShareNodes,
  faSquareShareNodes,
} from "@fortawesome/free-solid-svg-icons";
import MyCreatePlanLeft from "./MyCreatePlanLeft";
import CreatePlanMap from "../../../components/CreatePlan/CreatePlanMap";
import { useParams } from "react-router-dom";
import axios from "axios";
import MyPlanMap from "./MyPlanMap";
import { useDispatch, useSelector } from "react-redux";
import { addMonthNDate, changeTripPeriod, resetMonthNDate } from "../../../redux/store";
import { faHourglass } from "@fortawesome/free-regular-svg-icons";
import { addMonthNDate, changeTripPeriod, resetMonthNDate } from "../../../redux/store";

const MyHomeBox = ({ setClickTab, planList, rbn, setSelectRbn, setPlanList, setUpload }) => {
  const [fromWooJaeData, setFromWooJaeData] = useState([]);
  const [newFromWooJae, setNewFromWooJae] = useState([]);
  const [newTimeSet, setNewTimeSet] = useState([]);
  const [currPosition, setCurrPosition] = useState("");
  const [isOpen, setIsOpen] = useState(false);
  const postDate = new Date(planList.routeDate);
  const [forDimmed, setForDimmed] = useState({});
  function dateFormat(date) {
    let month = date.getMonth() + 1;
    let day = date.getDate();
    month = month >= 10 ? month : "0" + month;
    day = day >= 10 ? day : "0" + day;
    return date.getFullYear() + "-" + month + "-" + day;
  }
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
            <img
              className="instaImg"
              src={planList.smallImage}
              style={{
                opacity: doneOrStillOpacity(),
              }}
            />
          </div>

          <div>
            <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
              <div
                className="myBox__title"
                style={{
                  opacity: doneOrStillOpacity(),
                }}
              >
                {planList.title}
              </div>
              <div className="myBox__icons">
                {forDimmed.start <= new Date() && forDimmed.end >= new Date() && (
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

                  <FontAwesomeIcon icon={faSquareShareNodes} className="shareIcon" />
                ) : null}
              </div>
            </div>
            <div
              style={{
                opacity: doneOrStillOpacity(),
              }}
            >
              {planList.plannerdate}
            </div>
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
          일정확인/수정
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

async function excAxios(rbn, setNewFromWooJae, dispatch, setCurrPosition, planList) {
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
    dispatch(addMonthNDate({ month: fristMonth, date: fristDate, dow: fristDow }));
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
      planList.title.substring(planList.title.indexOf("일") - 1, planList.title.indexOf("일"))
    );
    let tempArr = new Array(lengths).fill(0);
    dispatch(changeTripPeriod(lengths));
    tempArr.forEach((val, index) => {
      let bbcT = newFromWooJae[index][`day${index + 1}`][0].starttime / (1000 * 60);
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
              setAuSure(true);
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
          {auSure && (
            <AuSureModal
              setIsOpen={setIsOpen}
              // setShowCratePlan={setShowCratePlan}
              setAuSure={setAuSure}
            />
          )}
          <MyPlanMap
            setSavePlan={setSavePlan}
            currPosition={currPosition}
            fromWooJaeData={newFromWooJae}
            forDayLine={forDayLine}
          />
        </Contents>
      </Modal>
      {/* {savePlan && (
            <SavePlan
                auSure={auSure}
                setAuSure={setAuSure}
                setSavePlan={setSavePlan}
                fromWooJaeData={fromWooJaeData}
                currPosition={currPosition}
            />
            )} */}
    </DimmedContainer>
  );
}

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
