import "./KMap.css";
import React, { useEffect, useRef, useState } from "react";
import { useSelector, useDispatch } from "react-redux/es/exports";
import "./KMap.css";
import { changeInfo, changePickedTransport } from "../../redux/store";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faBed,
  faBus,
  faCar,
  faMapLocationDot,
  faUtensils,
} from "@fortawesome/free-solid-svg-icons";
import {
  Container_LoRegi,
  Contents_LoRegi,
  Overlay_LoRegi,
  Title_LoRegi,
  Close_LoRegi,
  Body_LoRegi,
  Img_LoRegi,
} from "../Modal/LocationRegister.js";
const { kakao } = window;

const KMap = ({ currPosition, setTitleName, setConWhichModal }) => {
  let [lat, setLat] = useState("");
  let [lng, setLng] = useState("");
  //searchedLacation클릭시 해당 장소이름으로 redux state변경하기 위함
  let reduxState = useSelector((state) => {
    return state;
  });
  let dispatch = useDispatch();
  let publicRef = useRef();
  let personalRef = useRef();
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
  let [showTransport, setShowTransport] = useState(false);
  let [appearRegisterModal, setAppearRegisterModal] = useState(false);
  useEffect(() => {
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
    let markerPosition = new kakao.maps.LatLng(lat, lng);

    // 마커를 생성합니다
    let mainMarker = new kakao.maps.Marker({
      position: markerPosition,
    });

    // 마커가 지도 위에 표시되도록 설정합니다
    mainMarker.setMap(map);

    // 마커를 표시할 위치와 title 객체 배열입니다
    var positions = [
      {
        title: "경복궁",
        latlng: new kakao.maps.LatLng(37.5759040910202, 126.976842133821),
      },
      {
        title: "창덕궁",
        latlng: new kakao.maps.LatLng(37.5788697347103, 126.989710338876),
      },
      {
        title: "남산타워",
        latlng: new kakao.maps.LatLng(37.5489431139548, 126.989404159753),
      },
      {
        title: "한옥마을",
        latlng: new kakao.maps.LatLng(37.5595422700067, 126.994738735334),
      },
    ];

    // 마커 이미지의 이미지 주소입니다
    var imageSrc =
      "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";

    for (var i = 0; i < positions.length; i++) {
      // 마커 이미지의 이미지 크기 입니다
      var imageSize = new kakao.maps.Size(24, 35);

      // 마커 이미지를 생성합니다
      var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

      // 마커를 생성합니다
      var marker = new kakao.maps.Marker({
        map: map, // 마커를 표시할 지도
        position: positions[i].latlng, // 마커를 표시할 위치
        title: positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
        image: markerImage, // 마커 이미지
      });
    }

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
    kakao.maps.event.addListener(mainMarker, "click", function () {
      infowindow.open(map, mainMarker);
    });
  }, [lat, lng, reduxState.localNameForMarker]);
  // useEffect(() => {}, [showTransport, reduxState.pickedTransport]);
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
        <div className="kmap__left-btn__container">
          <div>이용방법</div>
          <div
            onClick={() => {
              setAppearRegisterModal(true);
            }}>
            장소등록
          </div>
          <span className="kmap__transportation">
            <div
              onClick={() => {
                setShowTransport(!showTransport);
              }}>
              이동수단
            </div>
            {showTransport ? (
              <>
                <div
                  className="slide-in-left-public_transport public"
                  ref={publicRef}
                  onClick={(e) => {
                    dispatch(changePickedTransport("public"));
                    console.log(reduxState.pickedTransport);
                    whichTransport(e);
                  }}>
                  <FontAwesomeIcon
                    icon={faBus}
                    className="kmap__left-icon bus"
                  />
                  <span>대중교통</span>
                </div>
                <span
                  style={{ fontSize: "15px", fontWeight: "bold" }}
                  className="slide-in-left-or">
                  OR
                </span>
                <div
                  className="slide-in-left-personal pickedTransport personal"
                  ref={personalRef}
                  onClick={(e) => {
                    dispatch(changePickedTransport("personal"));
                    whichTransport(e);
                  }}>
                  <FontAwesomeIcon
                    icon={faCar}
                    className="kmap__left-icon car"
                  />
                  <span>자가용</span>
                </div>
              </>
            ) : null}
          </span>
          <div>일정생성</div>
        </div>
      </div>
      {appearRegisterModal ? (
        <RegisterModal setAppearRegisterModal={setAppearRegisterModal} />
      ) : null}
    </div>
  );
};

function RegisterModal({ setAppearRegisterModal }) {
  return (
    <Container_LoRegi>
      <Overlay_LoRegi
        onClick={() => {
          setAppearRegisterModal(false);
        }}
      />
      <Contents_LoRegi>
        <Close_LoRegi
          onClick={() => {
            setAppearRegisterModal(false);
          }}>
          X
        </Close_LoRegi>
        <Title_LoRegi>
          장 소 등 록
          <span style={{ fontSize: "13px", color: "grey" }}>
            검색해도 나오지 않는 장소를 이곳에서 등록 후 다시 검색해보세요.
          </span>
          <span style={{ fontSize: "13px", color: "grey" }}>
            추가하실 장소의 유형을 선택해주세요.
          </span>
        </Title_LoRegi>
        <Body_LoRegi>
          <div className="registerModal__typeof-container">
            <span>
              <FontAwesomeIcon icon={faMapLocationDot} />
              장소
            </span>
            <span>
              <FontAwesomeIcon icon={faUtensils} />
              식당
            </span>
            <span>
              <FontAwesomeIcon icon={faBed} />
              숙소
            </span>
          </div>
        </Body_LoRegi>
      </Contents_LoRegi>
    </Container_LoRegi>
  );
}

function whichTransport(e) {
  if (e !== null && e.target.classList.contains("public")) {
    e.target.nextElementSibling.nextElementSibling.classList.remove(
      "pickedTransport"
    );
    e.target.classList.add("pickedTransport");
  } else if (e !== null && e.target.classList.contains("personal")) {
    e.target.previousElementSibling.previousElementSibling.classList.remove(
      "pickedTransport"
    );
    e.target.classList.add("pickedTransport");
  }
}

export default KMap;
