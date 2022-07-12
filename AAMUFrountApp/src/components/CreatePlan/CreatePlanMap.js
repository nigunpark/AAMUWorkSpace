import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
const { kakao } = window;
const CreatePlanMap = ({ showCreatePlan, currPosition }) => {
  let [lat, setLat] = useState("");
  let [lng, setLng] = useState("");
  let reduxState = useSelector((state) => {
    return state;
  });
  useEffect(() => {
    var mapContainer = document.getElementById("cMap"), // 지도를 표시할 div
      mapOption = {
        center: new kakao.maps.LatLng(lat, lng), // 지도의 중심좌표
        level: 9, // 지도의 확대 레벨
      };

    var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
    var geocoder = new kakao.maps.services.Geocoder();
    // 주소로 좌표를 검색합니다
    geocoder.addressSearch(
      reduxState.localNameForMarker.addr ?? currPosition,
      (result, status) => {
        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
          var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
          // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
          map.panTo(coords);
        }
      }
    );
    map.relayout();
  }, [showCreatePlan]);
  return (
    <div
      style={{
        width: "100%",
        display: "inline-block",
      }}
    >
      <div
        id="cMap"
        style={{
          width: "100%",
          height: "100%",
        }}
      >
        aa
      </div>
    </div>
  );
};

export default CreatePlanMap;
