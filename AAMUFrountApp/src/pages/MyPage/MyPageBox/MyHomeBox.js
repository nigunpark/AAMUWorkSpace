import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { useEffect, useState } from "react";
import styled from "styled-components";
import { faRectangleXmark } from "@fortawesome/free-solid-svg-icons";
import MyCreatePlanLeft from "./MyCreatePlanLeft";
import CreatePlanMap from "../../../components/CreatePlan/CreatePlanMap";
import { useParams } from "react-router-dom";
import axios from "axios";
import MyPlanMap from "./MyPlanMap";
import { useDispatch, useSelector } from "react-redux";
import {
  addMonthNDate,
  changeTripPeriod,
  resetMonthNDate,
} from "../../../redux/store";

const MyHomeBox = ({ setClickTab, planList, rbn, setSelectRbn }) => {
  const [fromWooJaeData, setFromWooJaeData] = useState([]);
  const [newFromWooJae, setNewFromWooJae] = useState([]);
  const [newTimeSet, setNewTimeSet] = useState([]);
  const [currPosition, setCurrPosition] = useState("");
  const [isOpen, setIsOpen] = useState(false);
  const postDate = new Date(planList.routeDate);
  function dateFormat(date) {
    let month = date.getMonth() + 1;
    let day = date.getDate();
    month = month >= 10 ? month : "0" + month;
    day = day >= 10 ? day : "0" + day;
    return date.getFullYear() + "-" + month + "-" + day;
  }

  let reduxState = useSelector((state) => state);
  let dispatch = useDispatch();

  const [modalOpen, setModalOpen] = useState(false);
  const onModalSelect = () => {
    setModalOpen(true);
  };

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
        />
      ) : null}

      <div className="MyBoxContainer" onClick={onModalSelect}>
        <div className="MyBox">
          <img className="instaImg" src={planList.smallImage} />
          <div style={{ fontSize: "20px" }}>{planList.title}</div>

          <div>
            {planList.plannerdate} (rbn :{rbn})
          </div>

          <div style={{ marginLeft: "170px" }}>{dateFormat(postDate)}</div>
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
}) {
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
            }, 100);
          }}
        >
          ????????????/??????
        </div>
        <div
          className="myBox-List-Post"
          onClick={() => {
            setClickTab(10);
            setSelectRbn(rbn);
          }}
        >
          ????????????
        </div>
        <div className="myBox-List-Delete">????????????</div>
        <div
          className="myBox-List-back"
          onClick={(e) => {
            setModalOpen(false);
          }}
        >
          ??????
        </div>
      </div>
    </div>
  );
}

function getTimeSetArr(newFromWooJae, planList, setNewTimeSet) {
  // let lengths = planList.title.substring(
  //   planList.title.indexOf("???") - 1,
  //   planList.title.indexOf("???")
  // );
  // console.log("lengths", lengths);
  // // if (newFromWooJae.length === 0) return;
  // let tempArr = new Array(lengths).fill(0);
  // tempArr.forEach((val, index) => {
  //   let bbcT = newFromWooJae[index][`day${index + 1}`][0].starttime / (1000 * 60);
  //   if (bbcT >= 13 * 60) {
  //     console.log("???");
  //     let temp = {
  //       ampm: "??????",
  //       time: bbcT / 60 - 12,
  //       fullDate: new Date(`2022-01-01 ${bbcT / 60}:${bbcT % 60}`),
  //       min: bbcT % 60,
  //       day: index + 1,
  //     };
  //     // dispatch(changeTimeSetObj(temp));
  //     setNewTimeSet((curr) => [...curr, temp]);
  //   } else {
  //     console.log("??????");
  //     let temp = {
  //       ampm: "??????",
  //       time: bbcT / 60,
  //       fullDate: new Date(`2022-01-01 ${bbcT / 60}:${bbcT % 60}`),
  //       min: bbcT % 60,
  //       day: index + 1,
  //     };
  //     // dispatch(changeTimeSetObj(temp));
  //     setNewTimeSet((curr) => [...curr, temp]);
  //   }
  // });
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
      resp.data.plannerdate.indexOf("???") - 1,
      resp.data.plannerdate.indexOf("???")
    );
    let fristDate = resp.data.plannerdate.substring(
      resp.data.plannerdate.indexOf("???") - 2,
      resp.data.plannerdate.indexOf("???")
    );
    let fristDow = resp.data.plannerdate.substring(
      resp.data.plannerdate.indexOf("~") - 2,
      resp.data.plannerdate.indexOf("~") - 1
    );
    switch (fristDow) {
      case "???":
        fristDow = 0;
        break;
      case "???":
        fristDow = 1;
        break;
      case "???":
        fristDow = 2;
        break;
      case "???":
        fristDow = 3;
        break;
      case "???":
        fristDow = 4;
        break;
      case "???":
        fristDow = 5;
        break;
      case "???":
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
        planList.title.indexOf("???") - 1,
        planList.title.indexOf("???")
      )
    );
    let tempArr = new Array(lengths).fill(0);
    dispatch(changeTripPeriod(lengths));
    tempArr.forEach((val, index) => {
      let bbcT =
        newFromWooJae[index][`day${index + 1}`][0].starttime / (1000 * 60);
      if (bbcT >= 13 * 60) {
        let temp = {
          ampm: "??????",
          time: bbcT / 60 - 12,
          fullDate: new Date(`2022-01-01 ${bbcT / 60}:${bbcT % 60}`),
          min: bbcT % 60,
          day: index + 1,
        };
        setNewTimeSet((curr) => [...curr, temp]);
      } else {
        let temp = {
          ampm: "??????",
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
            currPosition={currPosition} //????????? ??????
            fromWooJaeData={newFromWooJae} //routeMap [{},{},{}] ???????????? ???????????????
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
        <h4>?????? ?????? ???????????? ????????? ???????????? ????????????.</h4>
        <h4> ?????? ??????????????????????</h4>
        <AusBtnContainer>
          <AuSBtn
            onClick={() => {
              setIsOpen(false);
            }}
          >
            ??????
          </AuSBtn>
          <AuSBtn
            onClick={() => {
              setAuSure(false);
            }}
          >
            ??????
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
