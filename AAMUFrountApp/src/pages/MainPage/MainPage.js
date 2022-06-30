import React, { useEffect, useState } from "react";
import "./MainPage.css";
import KMap from "../../components/KMap/KMap.js";
import { useParams } from "react-router-dom";
import LeftPlanSide from "../../components/LeftPlanSide/LeftPlanSide";
import RightRecommandSide from "../../components/RightRecommandSide/RightRecommandSide";
import CreatePlan from "../../components/CreatePlan/CreatePlan";
import { useSelector, useDispatch } from "react-redux";
import { delAllSaveDaysRedux, deleteAllPickJanso } from "../../redux/store";
const MainPage = () => {
  const [titleName, setTitleName] = useState("");
  const [conWhichModal, setConWhichModal] = useState(false);
  let { currPosition } = useParams();
  const [showCreatePlan, setShowCratePlan] = useState(false);
  let reduxState = useSelector((state) => {
    return state;
  });
  let dispatch = useDispatch();
  useEffect(() => {
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
        />
      ) : null}
    </div>
  );
};

export default MainPage;
