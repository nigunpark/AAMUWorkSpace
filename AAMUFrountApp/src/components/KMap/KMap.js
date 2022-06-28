import "./KMap.css";
import React, { useEffect, useState } from "react";
import { useSelector, useDispatch } from "react-redux/es/exports";
import "./KMap.css";
import { changeInfo, changeShowWhichModal } from "../../redux/store";
const { kakao } = window;

const KMap = ({ currPosition, setTitleName, setConWhichModal }) => {
  let [lat, setLat] = useState("");
  let [lng, setLng] = useState("");
  //searchedLacation클릭시 해당 장소이름으로 redux state변경하기 위함
  let reduxState = useSelector((state) => {
    return state;
  });
  let dispatch = useDispatch();
  //위치정보 얻음을 성공했을 시
  // function onGeoOk(position) {
  // lat = position.coords.latitude;
  // lng = position.coords.longitude;
  // }
  // function onGeoFail() {
  //   alert("위치권한을 허용해주세요");
  // }
  //사용자의 현재 위치 얻기
  // navigator.geolocation.getCurrentPosition(onGeoOk, onGeoFail);
  //처음 지도 그리기

  useEffect(() => {
    var mapContainer = document.getElementById("map"), // 지도를 표시할 div
      mapOption = {
        center: new kakao.maps.LatLng(lat, lng), // 지도의 중심좌표
        level: 10, // 지도의 확대 레벨
      };

    var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
    var mapContainer = document.getElementById("map"), // 지도를 표시할 div
      mapOption = {
        center: new kakao.maps.LatLng(lat, lng), // 지도의 중심좌표
        level: 10, // 지도의 확대 레벨
      };

    var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
    var geocoder = new kakao.maps.services.Geocoder();
    // 주소로 좌표를 검색합니다
    geocoder.addressSearch(
      reduxState.localNameForMarker ?? currPosition,
      (result, status) => {
        // 정상적으로 검색이 완료됐으면
        if (1 == 1) {
          var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
          setLat(result[0].y);
          setLng(result[0].x);

          // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
          map.setCenter(coords);
        }
      }
    );
    // 마커가 표시될 위치입니다
    var markerPosition = new kakao.maps.LatLng(lat, lng);

    // 마커를 생성합니다
    var marker = new kakao.maps.Marker({
      position: markerPosition,
    });

    // 마커가 지도 위에 표시되도록 설정합니다
    marker.setMap(map);

    var iwContent =
        '<div class="iwContent__wrap">' +
        '    <div class="iwContent__info">' +
        '        <div class="iwContent__title">' +
        "            <h3>카카오 스페이스닷원</h3>" +
        // '            <div class="iwContent__close" onclick="closeOverlay()" title="닫기"></div>' +
        "        </div>                                                                        " +
        '         <div class="iwContent__body">' +
        '            <div class="iwContent__desc">' +
        '              <div class="iwContent__ellipsis">제주특별자치도 제주시 첨단로 242</div>' +
        '              <div class="iwContent__jibun iwContent__ellipsis">(우) 63309 (지번) 영평동 2181</div>' +
        '              <div><a href="https://www.kakaocorp.com/main" target="_blank" class="iwContent__link">홈페이지</a></div>' +
        "            </div>" +
        "        </div>" +
        "    </div>    " +
        "</div>", // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
      iwPosition = new kakao.maps.LatLng(lat, lng), //인포윈도우 표시 위치입니다
      iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

    // 인포윈도우를 생성하고 지도에 표시합니다
    var infowindow = new kakao.maps.InfoWindow({
      content: iwContent,
      removable: iwRemoveable,
    });
    kakao.maps.event.addListener(marker, "click", function () {
      infowindow.open(map, marker);
    });
  }, [lat, lng, reduxState.localNameForMarker]);

  return (
    <div>
      <div
        style={{
          width: "100%",
          display: "inline-block",
          paddingTop: "75px",
        }}>
        <div id="map" style={{ width: "100%", height: "99vh" }}></div>
        <div className="kmap__right-btn__container">
          <div
            onClick={() => {
              setTitleName("추천숙소");
              dispatch(changeInfo("추천숙소"));
              setConWhichModal(true);
            }}>
            추천숙소
          </div>
          <div
            onClick={() => {
              setTitleName("추천장소");
              dispatch(changeInfo("추천장소"));
              setConWhichModal(false);
            }}>
            추천장소
          </div>
        </div>
      </div>
    </div>
  );
};

export default KMap;
