import React, { useEffect, useState } from "react";
import "./MainPage.css";
import KMap from "../../components/KMap/KMap.js";
import { useParams } from "react-router-dom";
import LeftPlanSide from "../../components/LeftPlanSide/LeftPlanSide";
import RightRecommandSide from "../../components/RightRecommandSide/RightRecommandSide";
import CreatePlan from "../../components/CreatePlan/CreatePlan";
import { useDispatch } from "react-redux";
import {
  delAllSaveDaysRedux,
  deleteAllPickJanso,
  changeArrForJangso,
} from "../../redux/store";
import axios from "axios";
const MainPage = () => {
  const [titleName, setTitleName] = useState("추천장소");
  const [conWhichModal, setConWhichModal] = useState(false);
  let { currPosition } = useParams();
  const [showCreatePlan, setShowCratePlan] = useState(false);
  const [fromWooJaeData, setFromWooJaeData] = useState([]);
  let dispatch = useDispatch();
  useEffect(() => {
    getCurrpositionLocal(currPosition, dispatch);
    dispatch(deleteAllPickJanso([]));
    dispatch(delAllSaveDaysRedux([]));
  }, []);
  return (
    <div className="MainPage">
      <LeftPlanSide currPosition={currPosition} />
      <KMap
        currPosition={currPosition}
        setTitleName={setTitleName}
        setConWhichModal={setConWhichModal}
        setShowCratePlan={setShowCratePlan}
        setFromWooJaeData={setFromWooJaeData}
      />

      <RightRecommandSide
        setTitleName={setTitleName}
        titleName={titleName}
        conWhichModal={conWhichModal}
      />
      {showCreatePlan ? (
        <CreatePlan
          setShowCratePlan={setShowCratePlan}
          showCreatePlan={showCreatePlan}
          currPosition={currPosition}
          fromWooJaeData={fromWooJaeData}
          setFromWooJaeData={setFromWooJaeData}
        />
      ) : null}
    </div>
  );
};

function getCurrpositionLocal(currPosition, dispatch) {
  let token = sessionStorage.getItem("token");
  let areacode;
  switch (currPosition) {
    case "서울":
      areacode = 1;
      break;
    case "인천":
      areacode = 2;
      break;
    case "대전":
      areacode = 3;
      break;
    case "대구":
      areacode = 4;
      break;
    case "광주":
      areacode = 5;
      break;
    case "부산":
      areacode = 6;
      break;
    case "울산":
      areacode = 7;
      break;
    case "세종":
      areacode = 8;
      break;
    case "경기도":
      areacode = 31;
      break;
    case "강원도":
      areacode = 32;
      break;
    case "충청북도":
      areacode = 33;
      break;
    case "충청남도":
      areacode = 34;
      break;
    case "경상북도":
      areacode = 35;
      break;
    case "경상남도":
      areacode = 36;
      break;
    case "전라북도":
      areacode = 37;
      break;
    case "전라남도":
      areacode = 38;
      break;
    case "제주도":
      areacode = 39;
      break;
  }
  axios
    .get("/aamurest/info/places", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
      params: {
        areacode: areacode,
        contenttypeid: "12",
      },
    })
    .then((resp) => {
      dispatch(changeArrForJangso(resp.data));
    })
    .catch((error) => {
      console.log(error);
    });
}

export default MainPage;
