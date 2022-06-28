import React, { useState } from "react";
import "./MainPage.css";
import KMap from "../../components/KMap/KMap.js";
import { useParams } from "react-router-dom";
import LeftPlanSide from "../../components/LeftPlanSide/LeftPlanSide";
import RightRecommandSide from "../../components/RightRecommandSide/RightRecommandSide";
const MainPage = () => {
  const [titleName, setTitleName] = useState("");
  const [conWhichModal, setConWhichModal] = useState(false);
  let { currPosition } = useParams();
  return (
    <div className="MainPage">
      <LeftPlanSide currPosition={currPosition} />
      <KMap
        currPosition={currPosition}
        setTitleName={setTitleName}
        setConWhichModal={setConWhichModal}
      />
      <RightRecommandSide
        setTitleName={setTitleName}
        titleName={titleName}
        conWhichModal={conWhichModal}
      />
    </div>
  );
};

export default MainPage;
