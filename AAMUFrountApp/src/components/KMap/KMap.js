import "./KMap.css";
import React, { useEffect, useRef, useState } from "react";
import { useSelector, useDispatch } from "react-redux/es/exports";
import "./KMap.css";
import {
  changeArrForJangso,
  changeArrForSukso,
  changeInfo,
  changePickedTransport,
  changeTimeSetObj,
} from "../../redux/store";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import {
  faBed,
  faBus,
  faCar,
  faCircleXmark,
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
import { Alert } from "@mui/material";
import { ContainerInValid, OverlayInValid } from "../Modal/InVaildModal.js";
const { kakao } = window;
let mainMarkers = [];
let markers = [];
let tempCoords;
const KMap = ({
  currPosition,
  setTitleName,
  setConWhichModal,
  setShowCratePlan,
  setFromWooJaeData,
  setForSearchTypeId,
  isLoading,
}) => {
  let [lat, setLat] = useState("");
  let [lng, setLng] = useState("");
  //searchedLacation클릭시 해당 장소이름으로 redux state변경하기 위함
  let reduxState = useSelector((state) => {
    return state;
  });
  let dispatch = useDispatch();
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
  const [showAlert, setShowAlert] = useState(false);
  const [showAlertTime, setAlertTime] = useState(false);

  //-------------------------------------------------------------------------
  let pickedJangso = [];
  let mainMarker;

  useEffect(() => {
    let map;
    const center = new kakao.maps.LatLng(lat, lng);
    const options = {
      center,
      level: mapLevel,
    };
    map = new kakao.maps.Map(container.current, options);
    let geocoder = new kakao.maps.services.Geocoder();
    // 주소로 좌표를 검색합니다
    geocoder.addressSearch(currPosition, (result, status) => {
      // 정상적으로 검색이 완료됐으면
      if (status === kakao.maps.services.Status.OK) {
        setLat(result[0].y);
        setLng(result[0].x);
        let coords = new kakao.maps.LatLng(result[0].y, result[0].x);
        map.panTo(coords);
      }
    });
    //지도 최대레벨 제한
    map.setMaxLevel(10);
    setKMap(map);
    setFromWooJaeData([]);
  }, []);

  //지도 줌인줌아웃시 지도레벨 setting state
  useEffect(() => {
    // if (kMap === null) return;
    //지도레벨이벤트
    // kakao.maps.event.addListener(kMap, "zoom_changed", function () {
    //   console.log("11", center.getLat());
    //   // 지도의 현재 레벨을 얻어옵니다
    //   let level = kMap.getLevel();
    //   setMapLevel(level);
    //   let center = kMap.getCenter();
    //   let newCoords = new kakao.maps.LatLng(center.getLat(), center.getLng());
    //   kMap.setCenter(newCoords);
    //   // zoomAxios()
    // });
  }, []);

  //주소변경시 해당위치로 맵의 중심옮김 & 인포윈도우생성
  useEffect(() => {
    let coords;
    let infowindow;

    if (kMap === null) return;
    let geocoder = new kakao.maps.services.Geocoder();
    if (reduxState.localNameForMarker.addr !== undefined) {
      // 주소로 좌표를 검색합니다
      geocoder.addressSearch(reduxState.localNameForMarker.addr, (result, status) => {
        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
          coords = new kakao.maps.LatLng(result[0].y, result[0].x);
          if (tempCoords !== coords) {
            tempCoords = coords;
            kMap.panTo(coords);
          }
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
            '" target="_blank" class="iwContent__link"><span style="color:var(--skyblue);font-weight:bold">상세보기</span></a>&nbsp' +
            '<a href="https://map.kakao.com/link/roadview/' +
            reduxState.localNameForMarker.kakaokey +
            '" target="_blank" class="iwContent__link"><span style="color:var(--skyblue);font-weight:bold">로드뷰</span></div><a/>' +
            "           </div>" +
            "        </div>" +
            "    </div>    " +
            "</div>",
          // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
          // iwPosition = new kakao.maps.LatLng(lat, lng), //인포윈도우 표시 위치입니다
          iwRemoveable = true;
        // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

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

        if (mainMarkers.length != 0) {
          mainMarkers.pop().setMap(null);
        }
        mainMarkers.push(mainMarker);
        kakao.maps.event.addListener(mainMarker, "click", function () {
          infowindow.open(kMap, mainMarker);
        });
      });
    }

    if (reduxState.arrForPickJangso.length !== 0) {
      let jangsoObj = reduxState.arrForPickJangso[reduxState.arrForPickJangso.length - 1];
      pickedJangso.push({
        title: jangsoObj.title,
        latlng: new kakao.maps.LatLng(jangsoObj.mapy, jangsoObj.mapx),
      });
    }
    if (reduxState.arrForPickJangso.length === 0) {
      if (markers.length !== 0) {
        markers.forEach((val) => {
          val.setMap(null);
        });
      }
    }

    // 마커 이미지의 이미지 주소입니다
    let imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";

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
      markers.push(marker);
    }
  }, [
    // reduxState.localNameForMarker,
    // reduxState.arrForPickJangso,
    pickedJangso,
  ]);

  useEffect(() => {
    if (kMap === null) return;
    kakao.maps.event.addListener(kMap, "rightclick", function (mouseEvent) {
      let center = kMap.getCenter();
      zoomAxios(center.getLat(), center.getLng());
    });
  }, [handleRightClick]);
  function handleRightClick() {}
  window.addEventListener("contextMenu", handleRightClick);
  const zoomAxios = (placey, placex) => {
    let token = sessionStorage.getItem("token");
    axios
      .get("/aamurest/info/recentplace", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        params: {
          placey: placey,
          placex: placex,
          distance: "7",
        },
      })
      .then((resp) => {
        // console.log(resp.data);
        let jangso = resp.data.filter((val, i) => {
          return val.contenttypeid === 12;
        });
        let sukso = resp.data.filter((val, i) => {
          return val.contenttypeid === 32;
        });
        dispatch(changeArrForJangso(jangso));
        dispatch(changeArrForSukso(sukso));
      })
      .catch((error) => {
        console.log(error);
      });
  };
  return (
    <div>
      <div
        style={{
          width: "100%",
          display: "inline-block",
          paddingTop: "75px",
        }}
      >
        <div id="map" ref={container} style={{ width: "100%", height: "92vh" }}></div>
        <div className="kmap__weather__container">
          {/* <span style={{ position: "absolute", zIndex: "101", right: "0" }}>11</span> */}
          <div className="kmap__weather__wrapper">
            {isLoading && (
              <lottie-player
                src="https://assets9.lottiefiles.com/packages/lf20_cewufpii.json"
                background="transparent"
                speed="1"
                style={{
                  width: "50px",
                  height: "50px",
                  zIndex: "202",
                  transition: ".3s",
                  margin: "0 auto",
                }}
                loop
                autoplay
              ></lottie-player>
            )}
            {reduxState.forWeather.map((val, i) => {
              return (
                <div className="kmap__weather">
                  <div className="kmap__weather-img">
                    <div className="kmap__weather-img-container">
                      <img src={val.weatherImage} alt="날씨" />
                    </div>
                    <span style={{ fontWeight: "bold" }}>{val.weatherState}</span>
                  </div>
                  <div style={{ display: "flex", flexDirection: "column", gap: "4px" }}>
                    <div style={{ fontWeight: "bold", fontSize: "15px" }}>{val.date}</div>
                    <div>
                      <span style={{ fontWeight: "bold", fontSize: "13px", color: "blue" }}>
                        {val.lowTemp}
                      </span>
                      <span>&nbsp;/&nbsp;</span>
                      <span style={{ fontWeight: "bold", fontSize: "13px", color: "red" }}>
                        {val.highTemp}
                      </span>
                    </div>
                  </div>
                </div>
              );
            })}
          </div>
        </div>
        <div className="kmap__right-btn__container">
          <div
            onClick={(e) => {
              e.stopPropagation();
              setTitleName("추천숙소");
              dispatch(changeInfo("추천숙소"));
              setConWhichModal(true);
              setForSearchTypeId(32);
            }}
          >
            추천숙소
          </div>
          <div
            onClick={(e) => {
              e.stopPropagation();
              setTitleName("추천장소");
              dispatch(changeInfo("추천장소"));
              setConWhichModal(false);
              setForSearchTypeId(12);
            }}
          >
            추천장소
          </div>
        </div>
        <div className="kmap__left-btn__container">
          <div>이용방법</div>
          {/* <div
            onClick={(e) => {
              e.stopPropagation();
              setAppearRegisterModal(true);
            }}
          >
            장소등록
          </div> */}

          {/* <span className="kmap__transportation">
            <div
              onClick={(e) => {
                e.stopPropagation();
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
                    e.stopPropagation();
                    dispatch(changePickedTransport("public"));
                    whichTransport(e);
                  }}
                >
                  <FontAwesomeIcon icon={faBus} className="kmap__left-icon bus" />
                  <span>대중교통</span>
                </div>
                <span style={{ fontSize: "15px", fontWeight: "bold" }} className="slide-in-left-or">
                  OR
                </span>
                <div
                  className="slide-in-left-personal pickedTransport personal"
                  ref={personalRef}
                  onClick={(e) => {
                    e.stopPropagation();
                    dispatch(changePickedTransport("personal"));
                    whichTransport(e);
                  }}
                >
                  <FontAwesomeIcon icon={faCar} className="kmap__left-icon car" />
                  <span>자가용</span>
                </div>
              </>
            ) : null}
          </span> */}
          <div
            onClick={(e) => {
              e.stopPropagation();
              if (
                reduxState.leftSideTimeSetter * 60 + reduxState.leftSideMinSetter >
                1440 * reduxState.tripPeriod.length
              ) {
                setAlertTime(true);
                return;
              }
              if (
                reduxState.saveDaysNPickedSuksoRedux.filter((val) => {
                  return val !== 0;
                }).length !==
                  reduxState.tripPeriod.length - 1 ||
                reduxState.arrForPickJangso.length < reduxState.tripPeriod.length
              ) {
                setShowAlert(true);

                return;
              }
              setShowAuSModal(true);
              reduxState.tripPeriod.map((day, index) => {
                if (
                  reduxState.timeSetObj.find((obj) => {
                    return obj.day === index + 1;
                  }) === undefined
                ) {
                  dispatch(
                    changeTimeSetObj({
                      day: index + 1,
                      fullDate: "Sat Jan 01 2022 10:00:00 GMT+0900 (한국 표준시)",
                      ampm: "오전",
                      time: 10,
                      min: 0,
                    })
                  );
                }
              });
            }}
          >
            일정생성
          </div>
        </div>
      </div>
      {/* {appearRegisterModal ? (
        <RegisterModal
          setAppearRegisterModal={setAppearRegisterModal}
          jangsoName={jangsoName}
          setJangsoName={setJangsoName}
          showFillMes={showFillMes}
          setShowFillMes={setShowFillMes}
        />
      ) : null} */}
      {showAuSModal ? (
        <AreUSurePlan
          setShowAuSModal={setShowAuSModal}
          setShowCratePlan={setShowCratePlan}
          currPosition={currPosition}
          setFromWooJaeData={setFromWooJaeData}
        />
      ) : null}
      {(showAlert || showAlertTime) && (
        <ContainerInValid>
          <OverlayInValid
            onClick={(e) => {
              e.stopPropagation();
              setShowAlert(false);
              setAlertTime(false);
            }}
          />
          <Alert
            severity="warning"
            className="alertBox"
            style={{ zIndex: "1000", padding: "30px 100px", fontSize: "20px" }}
          >
            {/* <AlertTitle></AlertTitle> */}
            {showAlert && <strong> 여행일자만큼 숙소와 여행지를 선택해주세요</strong>}
            {showAlertTime && <strong> 여행일자 x 24시간을 초과할 수 없습니다.</strong>}
            <FontAwesomeIcon
              icon={faCircleXmark}
              className="alert_X"
              onClick={(e) => {
                e.stopPropagation();
                setShowAlert(false);
                setAlertTime(false);
              }}
            />
          </Alert>
        </ContainerInValid>
      )}
    </div>
  );
};

//일정등록 버튼 시 보이는 확인모달창
function AreUSurePlan({ setShowAuSModal, setShowCratePlan, currPosition, setFromWooJaeData }) {
  let reduxState = useSelector((state) => {
    return state;
  });
  return (
    <>
      <DimmedAuSContainer>
        <AuSModal>
          <h4>일정을 생성하시겠습니까?</h4>
          <AusBtnContainer>
            <AuSBtn
              onClick={(e) => {
                e.stopPropagation();
                toWooJae(currPosition, reduxState, setFromWooJaeData);
                setShowAuSModal(false);
                setShowCratePlan(true);
              }}
            >
              확인
            </AuSBtn>
            <AuSBtn
              onClick={(e) => {
                e.stopPropagation();
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
        onClick={(e) => {
          e.stopPropagation();
          setAppearRegisterModal(false);
          setShowFillMes(false);
        }}
      />
      <ContentsLoRegi>
        <CloseLoRegi
          onClick={(e) => {
            e.stopPropagation();
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
                e.stopPropagation();
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
      e.target.nextElementSibling.nextElementSibling.classList.remove("click__threebtnToggle");
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

async function toWooJae(currPosition, reduxState, setFromWooJaeData) {
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
  // console.log("arr", arr);
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

  console.log("arr", arr);
  try {
    const resp = await axios.post(
      "/aamurest/planner/data",
      {
        route: arr,
      },
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );
    console.log("resp.data", resp.data);
    console.log("resp.data.route", resp.data.route);
    let settedData = await manufacData(resp.data.route, reduxState);
    await setFromWooJaeData(settedData);
  } catch (error) {
    console.log("error", error);
  }
}
//일정생성버튼 눌렀을 시 우재한테 받은 데이터를 다시 가공하는 함수
function manufacData(data, reduxState) {
  let final = [];
  return reduxState.tripPeriod.map((val, tripPeriodI) => {
    let arr = data.filter((obj) => {
      return obj.day === tripPeriodI + 1;
    });
    // console.log("arr", arr);
    if (tripPeriodI === 0) {
      let newObj = { ...arr[0] };
      newObj.starttime = 0;
      arr.push(newObj);
    }
    if (tripPeriodI !== 0) {
      arr.push(arr[0]);
      let hotel = data.filter((obj) => {
        return obj.day === tripPeriodI && obj.contenttypeid === 32;
      })[0];
      // console.log("hotel", hotel);
      let temp = { ...hotel };
      temp.starttime = arr[0].starttime;
      temp.day = tripPeriodI + 1;
      arr.splice(0, 1, temp);
    }
    return { ["day" + (tripPeriodI + 1)]: arr };
  });

  // let temp;
  // return reduxState.tripPeriod.map((val, periodIndex) => {
  //   let arr = data.filter((obj) => {
  //     return obj.day === periodIndex + 1;
  //   });
  //   // if (periodIndex !== reduxState.tripPeriod.length - 1) {
  //   let newArr = { ...arr[0] };
  //   newArr.starttime = 0;
  //   arr.push(newArr);
  //   if (periodIndex === reduxState.tripPeriod.length - 2) {
  //     temp = { ...arr[arr.length - 1] };
  //   }
  //   if (periodIndex === reduxState.tripPeriod.length - 1) {
  //     temp.starttime = arr[0].starttime;
  //     temp.day = arr[0].day;
  //     arr.splice(0, 1, temp);
  //   }
  //   arr = arr.filter((val, i) => {
  //     return val.dto !== null;
  //   });
  //   return { ["day" + (periodIndex + 1)]: arr };
  // });
}

export default KMap;
