import "./KMap.css";
import React, { useEffect, useRef, useState } from "react";
import { useSelector, useDispatch } from "react-redux/es/exports";
import "./KMap.css";
import {
  changeArrForSukso,
  changeInfo,
  changePickedTransport,
  changeTimeSetObj,
  clearLatLng,
} from "../../redux/store";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import {
  faBed,
  faBus,
  faCar,
  faMapLocationDot,
  faUtensils,
} from "@fortawesome/free-solid-svg-icons";
import {
  ContainerLoRegi,
  ContentsLoRegi,
  OverlayLoRegi,
  TitleLoRegi,
  CloseLoRegi,
  BodyLoRegi,
} from "../Modal/LocationRegister.js";
import {
  DimmedAuSContainer,
  AuSModal,
  AusBtnContainer,
  AuSBtn,
} from "../Modal/AreUSurePlanModal.js";
import axios from "axios";
const { kakao } = window;

const KMap = ({
  currPosition,
  setTitleName,
  setConWhichModal,
  setShowCratePlan,
}) => {
  let [lat, setLat] = useState("");
  let [lng, setLng] = useState("");
  //searchedLacation클릭시 해당 장소이름으로 redux state변경하기 위함
  let reduxState = useSelector((state) => {
    return state;
  });
  let dispatch = useDispatch();
  let publicRef = useRef();
  let personalRef = useRef();
  let container = useRef();
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

  //이동수단 버튼클릭시 모달보이게하는 state
  let [showTransport, setShowTransport] = useState(false);
  //장소등록 버튼클릭시 모달 보이게하는 state
  let [appearRegisterModal, setAppearRegisterModal] = useState(false);
  let [jangsoName, setJangsoName] = useState("");
  //장소등록 모달에서 검색장소 입력안하고 엔터나 검색눌렀을 시 경고메세지 state
  let [showFillMes, setShowFillMes] = useState(false);
  //일정생성 버튼 클릭시 확인모달창 state
  let [showAuSModal, setShowAuSModal] = useState(false);
  //지도 레벨 state
  const [mapLevel, setMapLevel] = useState(9);
  const [kMap, setKMap] = useState(null);
  const [markers, setMarkers] = useState([1]);
  //-------------------------------------------------------------------------
  let pickedJangso = [];
  useEffect(() => {
    const center = new kakao.maps.LatLng(lat, lng);
    const options = {
      center,
      level: mapLevel,
    };
    const map = new kakao.maps.Map(container.current, options);
    let geocoder = new kakao.maps.services.Geocoder();
    // 주소로 좌표를 검색합니다
    geocoder.addressSearch(currPosition, (result, status) => {
      // 정상적으로 검색이 완료됐으면
      if (status === kakao.maps.services.Status.OK) {
        setLat(result[0].y);
        setLng(result[0].x);
      }
    });
    //지도 최대레벨 제한
    map.setMaxLevel(9);
    setKMap(map);
  }, [container]);

  //지도 줌인줌아웃시 지도레벨 setting state
  useEffect(() => {
    if (kMap === null) return;
    //지도레벨이벤트
    kakao.maps.event.addListener(kMap, "zoom_changed", function () {
      // 지도의 현재 레벨을 얻어옵니다
      let level = kMap.getLevel();
      setMapLevel(level);
      let center = kMap.getCenter();
      let newCoords = new kakao.maps.LatLng(center.getLat(), center.getLng());
      kMap.setCenter(newCoords);
    });
  }, []);

  //주소변경시 해당위치로 맵의 중심옮김 & 인포윈도우생성
  useEffect(() => {
    let coords;
    let infowindow;
    let mainMarker;
    if (kMap === null) return;
    // setMarkers((markers) => {
    //   console.log(markers);
    //   // markers.forEach((marker) => marker.setMap(null));
    // });
    let geocoder = new kakao.maps.services.Geocoder();
    // 주소로 좌표를 검색합니다
    geocoder.addressSearch(
      reduxState.localNameForMarker.addr ?? currPosition,
      (result, status) => {
        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
          coords = new kakao.maps.LatLng(result[0].y, result[0].x);
          kMap.panTo(coords);
        }

        let iwContent =
            '<div class="iwContent__wrap">' +
            '    <div class="iwContent__info">' +
            '        <div class="iwContent__title">' +
            "            <h3>" +
            (reduxState.localNameForMarker.title ?? currPosition) +
            "</h3>" +
            '            <div class="iwContent__close" onclick="closeOverlay()" title="닫기"></div>' +
            "        </div>                                                                        " +
            '         <div class="iwContent__body">' +
            '            <div class="iwContent__desc">' +
            '              <div class="iwContent__ellipsis">' +
            (reduxState.localNameForMarker.addr ?? "") +
            "</div>" +
            '              <div class="iwContent__jibun iwContent__ellipsis">(우) 63309 (지번) 영평동 2181</div>' +
            "<span class='iWContent__tel'>" +
            (reduxState.localNameForMarker.tel ?? "") +
            "</span>" +
            '              <div style="display:flex"><a href="https://place.map.kakao.com/' +
            reduxState.localNameForMarker.kakaokey +
            '" target="_blank" class="iwContent__link"><span>상세보기</span></a>&nbsp<span class="iwContent__link" onclick="' +
            roadview() +
            '">로드뷰</span></div>' +
            "            </div>" +
            "        </div>" +
            "    </div>    " +
            "</div>", // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
          // iwPosition = new kakao.maps.LatLng(lat, lng), //인포윈도우 표시 위치입니다
          iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

        // 인포윈도우를 생성하고 지도에 표시합니다
        infowindow = new kakao.maps.InfoWindow({
          content: iwContent,
          removable: iwRemoveable,
        });
        mainMarker = new kakao.maps.Marker({
          map: kMap,
          position: coords,
          clickable: true,
        });
        kakao.maps.event.addListener(mainMarker, "click", function () {
          infowindow.open(kMap, mainMarker);
        });
      }
    );

    // 마커를 표시할 위치와 title 객체 배열입니다
    reduxState.arrForPickJangso.map((jangsoObj) => {
      pickedJangso.push({
        title: jangsoObj.title,
        latlng: new kakao.maps.LatLng(jangsoObj.mapy, jangsoObj.mapx),
      });
    });
    // 마커 이미지의 이미지 주소입니다
    let imageSrc =
      "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
    // <i class="fa-solid fa-hotel"></i>

    for (let i = 0; i < pickedJangso.length; i++) {
      // 마커 이미지의 이미지 크기 입니다
      let imageSize = new kakao.maps.Size(24, 35);
      // 마커 이미지를 생성합니다
      let markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
      // 마커를 생성합니다
      let marker = new kakao.maps.Marker({
        // map: kMap, // 마커를 표시할 지도
        position: pickedJangso[i].latlng, // 마커를 표시할 위치
        title: pickedJangso[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
        image: markerImage, // 마커 이미지
      });

      marker.setMap(kMap);
    }
  }, [
    reduxState.localNameForMarker,
    reduxState.arrForPickJangso,
    pickedJangso,
  ]);

  return (
    <div>
      <div
        style={{
          width: "100%",
          display: "inline-block",
          paddingTop: "75px",
        }}
      >
        <div
          id="map"
          ref={container}
          style={{ width: "100%", height: "99vh" }}
        ></div>
        <div id="roadview" style={{ width: "100%", height: "99vh" }}></div>
        <div className="kmap__right-btn__container">
          <div
            onClick={() => {
              setTitleName("추천숙소");
              dispatch(changeInfo("추천숙소"));
              setConWhichModal(true);
              getCurrpositionHotel(currPosition, dispatch);
            }}
          >
            추천숙소
          </div>
          <div
            onClick={() => {
              setTitleName("추천장소");
              dispatch(changeInfo("추천장소"));
              setConWhichModal(false);
            }}
          >
            추천장소
          </div>
        </div>
        <div className="kmap__left-btn__container">
          <div>이용방법</div>
          <div
            onClick={() => {
              setAppearRegisterModal(true);
            }}
          >
            장소등록
          </div>
          <span className="kmap__transportation">
            <div
              onClick={() => {
                setShowTransport(!showTransport);
              }}
            >
              이동수단
            </div>
            {showTransport ? (
              <>
                <div
                  className="slide-in-left-public_transport public"
                  ref={publicRef}
                  onClick={(e) => {
                    dispatch(changePickedTransport("public"));
                    whichTransport(e);
                  }}
                >
                  <FontAwesomeIcon
                    icon={faBus}
                    className="kmap__left-icon bus"
                  />
                  <span>대중교통</span>
                </div>
                <span
                  style={{ fontSize: "15px", fontWeight: "bold" }}
                  className="slide-in-left-or"
                >
                  OR
                </span>
                <div
                  className="slide-in-left-personal pickedTransport personal"
                  ref={personalRef}
                  onClick={(e) => {
                    dispatch(changePickedTransport("personal"));
                    whichTransport(e);
                  }}
                >
                  <FontAwesomeIcon
                    icon={faCar}
                    className="kmap__left-icon car"
                  />
                  <span>자가용</span>
                </div>
              </>
            ) : null}
          </span>
          <div
            onClick={() => {
              setShowAuSModal(true);
            }}
          >
            일정생성
          </div>
        </div>
      </div>
      {appearRegisterModal ? (
        <RegisterModal
          setAppearRegisterModal={setAppearRegisterModal}
          jangsoName={jangsoName}
          setJangsoName={setJangsoName}
          showFillMes={showFillMes}
          setShowFillMes={setShowFillMes}
        />
      ) : null}
      {showAuSModal ? (
        <AreUSurePlan
          setShowAuSModal={setShowAuSModal}
          setShowCratePlan={setShowCratePlan}
          currPosition={currPosition}
        />
      ) : null}
    </div>
  );
};

//일정등록 버튼 시 보이는 확인모달창
function AreUSurePlan({ setShowAuSModal, setShowCratePlan, currPosition }) {
  let reduxState = useSelector((state) => {
    return state;
  });
  let dispatch = useDispatch();
  return (
    <>
      <DimmedAuSContainer>
        <AuSModal>
          <h4>일정을 생성하시겠습니까?</h4>
          <AusBtnContainer>
            <AuSBtn
              onClick={() => {
                setShowCratePlan(true);
                setShowAuSModal(false);
                reduxState.tripPeriod.map((day, index) => {
                  if (
                    reduxState.timeSetObj.find((obj) => {
                      return obj.day === index + 1;
                    }) === undefined
                  ) {
                    dispatch(
                      changeTimeSetObj({
                        day: index + 1,
                        fullDate:
                          "Sat Jan 01 2022 10:00:00 GMT+0900 (한국 표준시)",
                        ampm: "오전",
                        time: 10,
                        min: 0,
                      })
                    );
                  }
                });
                toWooJae(currPosition, reduxState);
              }}
            >
              확인
            </AuSBtn>
            <AuSBtn
              onClick={() => {
                setShowAuSModal(false);
              }}
            >
              취소
            </AuSBtn>
          </AusBtnContainer>
        </AuSModal>
      </DimmedAuSContainer>
    </>
  );
}

//장소등록 버튼 시 보이는 모달창
function RegisterModal({
  setAppearRegisterModal,
  jangsoName,
  setJangsoName,
  showFillMes,
  setShowFillMes,
}) {
  return (
    <ContainerLoRegi>
      <OverlayLoRegi
        onClick={() => {
          setAppearRegisterModal(false);
          setShowFillMes(false);
        }}
      />
      <ContentsLoRegi>
        <CloseLoRegi
          onClick={() => {
            setAppearRegisterModal(false);
            setShowFillMes(false);
          }}
        >
          X
        </CloseLoRegi>
        <TitleLoRegi>
          장 소 등 록
          <span style={{ fontSize: "13px", color: "grey" }}>
            검색해도 나오지 않는 장소를 이곳에서 등록 후 다시 검색해보세요.
          </span>
          <span style={{ fontSize: "13px", color: "grey" }}>
            추가하실 장소의 유형을 선택해주세요.
          </span>
        </TitleLoRegi>
        <BodyLoRegi>
          <div
            className="registerModal__typeof-container"
            onClick={(e) => {
              threeBtnToggle(e);
            }}
          >
            <span className="registerModal__btn jangso">
              <FontAwesomeIcon icon={faMapLocationDot} />
              장소
            </span>
            <span className="registerModal__btn restaurant">
              <FontAwesomeIcon icon={faUtensils} />
              식당
            </span>
            <span className="registerModal__btn sukso">
              <FontAwesomeIcon icon={faBed} />
              숙소
            </span>
          </div>
          <form className="registerModal__input-container">
            <Box
              component="form"
              sx={{
                "& > :not(style)": { mt: 3, mx: 1, width: "97%" },
              }}
              noValidate
            >
              <TextField
                label="장소이름"
                variant="outlined"
                color="warning"
                value={jangsoName}
                onInput={(e) => setJangsoName(e.target.value)}
                onKeyDown={(e) => {
                  if (e.key === "Enter") {
                    e.preventDefault();
                    if (e.target.value.length === 0) {
                      setShowFillMes(true);
                    } else if (e.target.value.length !== 0) {
                      setShowFillMes(false);
                      //밑에 서버로 비동기 검색보내기
                    }
                  } else {
                    if (jangsoName.length !== 0) setShowFillMes(false);
                  }
                }}
                required
                fullWidth={true}
              />
            </Box>
            <input type="hidden" />
            {showFillMes ? (
              <span className="fillInputMessage">검색할 장소를 입력하세요</span>
            ) : null}
            <button
              className="registerModal__search-btn"
              onClick={(e) => {
                e.preventDefault();
                if (jangsoName.length === 0) {
                  setShowFillMes(true);
                  //밑에 서버로 비동기 검색보내기
                }
              }}
            >
              검 색
            </button>
          </form>
        </BodyLoRegi>
      </ContentsLoRegi>
    </ContainerLoRegi>
  );
}

function threeBtnToggle(e) {
  if (e.target.nodeName === "SPAN") {
    if (e.target.classList.contains("jangso")) {
      e.target.nextElementSibling.classList.remove("click__threebtnToggle");
      e.target.nextElementSibling.nextElementSibling.classList.remove(
        "click__threebtnToggle"
      );
      e.target.classList.add("click__threebtnToggle");
    } else if (e.target.classList.contains("restaurant")) {
      e.target.previousElementSibling.classList.remove("click__threebtnToggle");
      e.target.nextElementSibling.classList.remove("click__threebtnToggle");
      e.target.classList.add("click__threebtnToggle");
    } else {
      e.target.previousElementSibling.classList.remove("click__threebtnToggle");
      e.target.previousElementSibling.previousElementSibling.classList.remove(
        "click__threebtnToggle"
      );
      e.target.classList.add("click__threebtnToggle");
    }
  }
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

function roadview() {
  var roadviewContainer = document.getElementById("roadview"); //로드뷰를 표시할 div
  var roadview = new kakao.maps.Roadview(roadviewContainer); //로드뷰 객체
  var roadviewClient = new kakao.maps.RoadviewClient(); //좌표로부터 로드뷰 파노ID를 가져올 로드뷰 helper객체

  var position = new kakao.maps.LatLng(33.450701, 126.570667);

  // 특정 위치의 좌표와 가까운 로드뷰의 panoId를 추출하여 로드뷰를 띄운다.
  roadviewClient.getNearestPanoId(position, 50, function (panoId) {
    roadview.setPanoId(panoId, position); //panoId와 중심좌표를 통해 로드뷰 실행
  });
}

function toWooJae(currPosition, reduxState) {
  let token = sessionStorage.getItem("token");
  let arr;
  //사용자가 선택한 여행일자만큼 obj를 만들고 day를 넣는 로직
  arr = reduxState.tripPeriod.map((val, index) => {
    return {
      contenttypeid: null,
      contentid: null,
      starttime: null,
      day: index + 1,
      atime: null,
    };
  });
  //사용자가 선택한 호텔을 우재한테 보내줄 data에 넣는 로직
  reduxState.saveDaysNPickedSuksoRedux.map((local, index) => {
    arr[
      arr.findIndex((obj) => {
        return obj.day === index + 1;
      })
    ].contenttypeid = local.contenttypeid;

    arr[
      arr.findIndex((obj) => {
        return obj.day === index + 1;
      })
    ].contentid = local.contentid;
  });

  // //각각 일자의 시작시간넣는 로직
  reduxState.timeSetObj.map((val, index) => {
    let timeMilSec;
    if (val.ampm === "오후") {
      timeMilSec = (val.time + 12) * 60 * 60 * 1000 + val.min * 60 * 1000;
    } else {
      timeMilSec = val.time * 60 * 60 * 1000 + val.min * 60 * 1000;
    }
    arr[
      arr.findIndex((obj) => {
        return obj.day === val.day;
      })
    ].starttime = timeMilSec;
  });

  //여행지 머무는 시간 및 여행지 넣기
  reduxState.arrForPickJangso.forEach((obj, index) => {
    arr.push({
      contenttypeid: obj.contenttypeid,
      contentid: obj.contentid,
      starttime: null,
      day: null,
      atime: obj.atime,
    });
  });
  axios
    .post(
      "/aamurest/planner/data",
      {
        route: {
          data: arr,
        },
      },
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )
    .then((resp) => {
      console.log("성공!");
      console.log(resp.data);
    })
    .catch((error) => {
      console.log(error);
    })
    .then(() => {
      console.log("get요청됨");
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
        contenttypeid: "32",
      },
    })
    .then((resp) => {
      dispatch(changeArrForSukso(resp.data));
    })
    .catch((error) => {
      console.log((error) => console.log("호텔가져오기 실패", error));
    });
}
export default KMap;
