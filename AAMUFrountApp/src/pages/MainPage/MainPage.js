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
  changeArrForSukso,
  changeArrForRestaurant,
} from "../../redux/store";
import axios from "axios";
const MainPage = () => {
  const [titleName, setTitleName] = useState("추천장소");
  const [conWhichModal, setConWhichModal] = useState(3);
  let { currPosition } = useParams();
  const [showCreatePlan, setShowCratePlan] = useState(false);
  const [fromWooJaeData, setFromWooJaeData] = useState([]);
  const [areaCode, setAreaCode] = useState(0);
  const [forSearchTypeId, setForSearchTypeId] = useState(12);
  const [isLoading, setIsLoading] = useState(true);
  let dispatch = useDispatch();
  useEffect(() => {
    getCurrpositionLocal(currPosition, dispatch, setAreaCode);
    getCurrpositionHotel(currPosition, dispatch);
    getCurrpositionRest(currPosition, dispatch);
    dispatch(deleteAllPickJanso([]));
    dispatch(delAllSaveDaysRedux([]));
  }, []);
  return (
    <div className="MainPage">
      <LeftPlanSide currPosition={currPosition} setIsLoading={setIsLoading} />
      <KMap
        currPosition={currPosition}
        setTitleName={setTitleName}
        setConWhichModal={setConWhichModal}
        setShowCratePlan={setShowCratePlan}
        setFromWooJaeData={setFromWooJaeData}
        setForSearchTypeId={setForSearchTypeId}
        isLoading={isLoading}
      />

      <RightRecommandSide
        setTitleName={setTitleName}
        titleName={titleName}
        conWhichModal={conWhichModal}
        areaCode={areaCode}
        forSearchTypeId={forSearchTypeId}
        setForSearchTypeId={setForSearchTypeId}
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

function getCurrpositionLocal(currPosition, dispatch, setAreaCode) {
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
    case "광주광역시":
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
  setAreaCode(areacode);
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
function getCurrpositionHotel(currPosition, dispatch) {
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
    case "광주광역시":
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
        contenttypeid: "32",
      },
    })
    .then((resp) => {
      dispatch(changeArrForSukso(resp.data));
      // console.log(resp.data);
    })
    .catch((error) => {
      console.log((error) => console.log("호텔가져오기 실패", error));
    });
}

function getCurrpositionRest(currPosition, dispatch) {
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
    case "광주광역시":
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
        contenttypeid: "39",
      },
    })
    .then((resp) => {
      dispatch(changeArrForRestaurant(resp.data));
      // console.log("맛집", resp.data);
    })
    .catch((error) => {
      console.log((error) => console.log("맛집가져오기 실패", error));
    });
}
export default MainPage;
