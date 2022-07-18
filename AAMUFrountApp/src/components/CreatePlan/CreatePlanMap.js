import React, { useEffect, useRef, useState } from "react";
import { useSelector } from "react-redux";
import axios from "axios";

const { kakao } = window;
let markersArr = [];
let polylineArr = [];
const CreatePlanMap = ({
  showCreatePlan,
  currPosition,
  fromWooJaeData,
  forDayLine,
  setSavePlan,
}) => {
  let [lat, setLat] = useState("");
  let [lng, setLng] = useState("");
  let [cMap, setCMap] = useState(null);

  const [mapLevel, setMapLevel] = useState(9);
  let cMapRef = useRef();
  let reduxState = useSelector((state) => {
    return state;
  });
  useEffect(() => {
    let mapContainer = cMapRef.current, // 지도를 표시할 div
      mapOption = {
        center: new kakao.maps.LatLng(lat, lng), // 지도의 중심좌표
        level: mapLevel, // 지도의 확대 레벨
      };
    let map = new kakao.maps.Map(mapContainer, mapOption);
    // if (cMap != null) {
    //   // map = new kakao.maps.Map(mapContainer, mapOption);
    //   map = cMap; // 지도를 생성합니다
    // } else {
    //   map = new kakao.maps.Map(mapContainer, mapOption);
    // }
    let geocoder = new kakao.maps.services.Geocoder();
    // 주소로 좌표를 검색합니다
    geocoder.addressSearch(
      reduxState.localNameForMarker.addr ?? currPosition,
      (result, status) => {
        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
          let coords = new kakao.maps.LatLng(result[0].y, result[0].x);
          setLat(result[0].y);
          setLng(result[0].x);
          map.panTo(coords);
        }
      }
    );
    setCMap(map);
    map.relayout();
  }, [cMapRef]);

  //지도 줌 관련
  useEffect(() => {
    if (cMap === null) return;
    //지도레벨이벤트
    kakao.maps.event.addListener(cMap, "zoom_changed", function () {
      // 지도의 현재 레벨을 얻어옵니다
      let level = cMap.getLevel();
      setMapLevel(level);
      let center = cMap.getCenter();
      let newCoords = new kakao.maps.LatLng(center.getLat(), center.getLng());
      cMap.setCenter(newCoords);
    });
  }, []);

  useEffect(() => {
    if (cMap === null) return;
    let linePath = [];
    let forMarkers = [];
    const colors = [
      "red",
      "green",
      "deepskyblue",
      "blue",
      "deeppink",
      "indigo",
      "black",
    ];
    if (fromWooJaeData.length !== 0) {
      reduxState.tripPeriod.map((val, index) => {
        let tempArr = [];
        let tempForMarkers = [];
        fromWooJaeData[index]["day" + (index + 1)].map((obj, i) => {
          tempForMarkers.push({
            title: obj.dto.title,
            latlng: new kakao.maps.LatLng(obj.dto.mapy, obj.dto.mapx),
            contenttypeid: obj.contenttypeid,
            contentid: obj.contentid,
          });
          tempArr.push(new kakao.maps.LatLng(obj.dto.mapy, obj.dto.mapx));
        });
        linePath.push(tempArr);
        forMarkers.push(tempForMarkers);
      });

      //전체일정 선택시
      if (forDayLine === 0) {
        reduxState.tripPeriod.map((val, i) => {
          // 지도에 표시할 선을 생성합니다
          let polyline = new kakao.maps.Polyline({
            path: linePath[i], // 선을 구성하는 좌표배열 입니다
            strokeWeight: 3, // 선의 두께 입니다
            strokeColor: colors[i], // 선의 색깔입니다
            strokeOpacity: 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
            strokeStyle: "solid", // 선의 스타일입니다
          });
          // 지도에 선을 표시합니다
          polyline.setMap(cMap);
          polylineArr.push(polyline);
        });

        // 마커 이미지의 이미지 주소입니다
        let imageSrcHotel = "/images/hotel.png";
        let imageSrcDesti = "/images/destination.png";
        let imageSrc = "/images/destination.png";
        reduxState.tripPeriod.map((val, index) => {
          forMarkers[index].map((val, i) => {
            // 마커 이미지의 이미지 크기 입니다
            let imageSize = new kakao.maps.Size(30, 30);
            // 마커 이미지를 생성합니다
            if (val.contenttypeid === 12) imageSrc = imageSrcDesti;
            if (val.contenttypeid === 32) imageSrc = imageSrcHotel;
            let markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
            // 마커를 생성합니다
            let marker = new kakao.maps.Marker({
              // map: kMap, // 마커를 표시할 지도
              position: forMarkers[index][i].latlng, // 마커를 표시할 위치
              title: forMarkers[index][i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
              image: markerImage, // 마커 이미지
            });
            markersArr.push(marker);
          });
        });
        markersArr.map((val, index) => {
          val.setMap(cMap);
        });
      }
      //전체일정 외 DAY1..등 선택시
      else {
        let marker;
        markersArr.map((val, index) => {
          val.setMap(null);
        });
        polylineArr.map((val, index) => {
          val.setMap(null);
        });
        markersArr = [];
        polylineArr = [];

        let i = forDayLine - 1;
        let polyline = new kakao.maps.Polyline({
          path: linePath[i], // 선을 구성하는 좌표배열 입니다
          strokeWeight: 3, // 선의 두께 입니다
          strokeColor: colors[i], // 선의 색깔입니다
          strokeOpacity: 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
          strokeStyle: "solid", // 선의 스타일입니다
        });

        // 지도에 선을 표시합니다
        polyline.setMap(cMap);
        polylineArr.push(polyline);
        // 마커 이미지의 이미지 주소입니다
        let imageSrcHotel = "/images/hotel.png";
        let imageSrcDesti = "/images/destination.png";
        let imageSrc = "/images/destination.png";
        forMarkers[i].map((val, idx) => {
          // 마커 이미지의 이미지 크기 입니다
          let imageSize = new kakao.maps.Size(30, 30);
          // 마커 이미지를 생성합니다
          if (val.contenttypeid === 12) imageSrc = imageSrcDesti;
          if (val.contenttypeid === 32) imageSrc = imageSrcHotel;
          let markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
          // 마커를 생성합니다
          marker = new kakao.maps.Marker({
            // map: kMap, // 마커를 표시할 지도
            position: forMarkers[i][idx].latlng, // 마커를 표시할 위치
            title: forMarkers[i][idx].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
            image: markerImage, // 마커 이미지
          });
          markersArr.push(marker);
        });

        markersArr.map((val, index) => {
          val.setMap(cMap);
        });
      }
    }
  }, [fromWooJaeData, forDayLine]);
  return (
    <div
      style={{
        position: "relative",
        width: "100%",
        display: "inline-block",
      }}
    >
      <div
        ref={cMapRef}
        id="cMap"
        style={{
          width: "100%",
          height: "100%",
        }}
      ></div>
      <div className="cMap__btn-container">
        <div>수정저장</div>
        <div>버튼버튼</div>
        <div
          onClick={() => {
            setSavePlan(true);
          }}
        >
          일정저장
        </div>
      </div>
    </div>
  );
};

export default CreatePlanMap;
