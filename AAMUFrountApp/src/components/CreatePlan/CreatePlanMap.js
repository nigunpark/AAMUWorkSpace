import React, { useEffect } from "react";
const { kakao } = window;
const CreatePlanMap = ({ showCreatePlan }) => {
  useEffect(() => {
    var cMapContainer = document.getElementById("cMap"), // 지도를 표시할 div
      cMapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3, // 지도의 확대 레벨
      };

    // 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
    var map = new kakao.maps.Map(cMapContainer, cMapOption);
    map.relayout();
  }, [showCreatePlan]);
  return (
    <div
      style={{
        width: "100%",
        display: "inline-block",
      }}>
      <div
        id="cMap"
        style={{
          width: "100%",
          height: "100%",
        }}>
        aa
      </div>
    </div>
  );
};

export default CreatePlanMap;
