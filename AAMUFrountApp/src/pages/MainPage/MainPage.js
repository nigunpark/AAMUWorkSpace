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
  clearLatLng,
  setLatLng,
} from "../../redux/store";
const { kakao } = window;
const MainPage = () => {
  const [titleName, setTitleName] = useState("");
  const [conWhichModal, setConWhichModal] = useState(false);
  let { currPosition } = useParams();
  const [showCreatePlan, setShowCratePlan] = useState(false);
  const [stateForMapCenter, setStateForMapCenter] = useState([]);
  let dispatch = useDispatch();
  useEffect(() => {
    getLatLng(currPosition, setStateForMapCenter, stateForMapCenter);
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
        stateForMapCenter={stateForMapCenter}
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
        />
      ) : null}
    </div>
  );
};
const getLatLng = (currPosition, setStateForMapCenter, stateForMapCenter) => {
  var geocoder = new kakao.maps.services.Geocoder();
  // 주소로 좌표를 검색합니다
  geocoder.addressSearch(currPosition, (result, status) => {
    // 정상적으로 검색이 완료됐으면
    if (status === kakao.maps.services.Status.OK) {
      // var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
      let temp = [...stateForMapCenter];
      temp.push(result[0].y);
      temp.push(result[0].x);
      setStateForMapCenter(temp);
    }
  });
};
export default MainPage;
