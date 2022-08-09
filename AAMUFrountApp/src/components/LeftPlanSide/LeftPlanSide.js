import React, { useEffect, useRef, useState } from "react";
import "./LeftPlanSide.css";
import WholeMapLocalData from "../WholeMap/WholeMapLocalData.js";
import DateRangePick from "../DateRangePicker/DateRangePicker2";
import { useSelector } from "react-redux/es/hooks/useSelector";
import { PlanTripTime } from "../PlanTripTime/PlanTripTime";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlus, faX } from "@fortawesome/free-solid-svg-icons";
import { faClock } from "@fortawesome/free-regular-svg-icons";
import { useDispatch } from "react-redux";
import {
  addArrInForJangso,
  addArrInForRestaurant,
  addAtimeArrForJangso,
  addMonthNDate,
  addWeather,
  changeLnfM,
  changeSaveDaysRedux,
  changeShowWhichModal,
  delAllPickMazzip,
  deleteAllPickJanso,
  deletePickJangso,
  delOneSaveDaysRedux,
  delPickMazzip,
  minSetter,
  resetMonthNDate,
  setInitForMin,
  setInitForTime,
  timeSetter,
} from "../../redux/store";
import axios from "axios";

const LeftPlanSide = ({ currPosition, setIsLoading }) => {
  const [findcurrPositionId, setFindcurrPositionId] = useState("");
  const [saveDays, setSaveDays] = useState(0);
  const [days, setDays] = useState(0);
  const [savePeriod, setSavePeriod] = useState({
    startDate: new Date(),
    endDate: new Date(),
    key: "selection",
  });
  const [appearCalendar, setAppearCalendar] = useState(false);
  const [period, setPeriod] = useState({
    startDate: new Date(),
    endDate: new Date(),
    key: "selection",
  });
  let reduxState = useSelector((state) => {
    return state;
  });
  let suksoRef = useRef();
  let jangsoRef = useRef();
  let restaurantRef = useRef();

  useEffect(() => {
    //WholeMapLocalData.js에서 현재클릭해서 들어온 지역명과 같은 지역명 찾기
    let findId = WholeMapLocalData.find((data) => {
      return data.localName === currPosition;
    });
    //그리고 그 지역명으로 setting해주기
    //WholeMapLocalData에는 한정된지역만 있으므로 api로 전체 지역명을 받아서 비교하자
    setFindcurrPositionId(findId.id);
    setAppearCalendar(true);
  }, []);
  useEffect(() => {
    if (reduxState.showWhichModal === 1) {
      suksoRef.current.classList.add("lps__type-btn-picked");
      jangsoRef.current.classList.remove("lps__type-btn-picked");
      restaurantRef.current.classList.remove("lps__type-btn-picked");
    } else if (reduxState.showWhichModal === 2) {
      suksoRef.current.classList.remove("lps__type-btn-picked");
      jangsoRef.current.classList.remove("lps__type-btn-picked");
      restaurantRef.current.classList.add("lps__type-btn-picked");
    } else {
      suksoRef.current.classList.remove("lps__type-btn-picked");
      jangsoRef.current.classList.add("lps__type-btn-picked");
      restaurantRef.current.classList.remove("lps__type-btn-picked");
    }
  }, [reduxState.showWhichModal]);
  let dispatch = useDispatch();

  return (
    <div className="LeftPlanSide">
      <div className="leftPlanSide__localNDay">
        <h2>{currPosition}</h2>
        <p>{findcurrPositionId}</p>
        <div className="leftPlanSide__localNDay__how-many-day">
          {days}박 {days + 1}일
        </div>
        <div className="changeDate__container">
          <ChangeDate
            period={period}
            appearCalendar={appearCalendar}
            setAppearCalendar={setAppearCalendar}
            currPosition={currPosition}
            saveDays={saveDays}
            setIsLoading={setIsLoading}
          />
          <div className="dateRangePicker">
            {appearCalendar ? (
              <DateRangePick
                setDays={setDays}
                setPeriod={setPeriod}
                savePeriod={savePeriod}
                setSavePeriod={setSavePeriod}
                saveDays={saveDays}
                setSaveDays={setSaveDays}
                appearCalendar={appearCalendar}
                setAppearCalendar={setAppearCalendar}
              />
            ) : null}
          </div>
        </div>
      </div>
      <div className="leftPlanSide__setting_detail_part">
        <div className="planTripTime__div">
          <PlanTripTime saveDays={saveDays} savePeriod={savePeriod} />
        </div>

        <h3 style={{ textAlign: "center", margin: "1rem 0" }}>선택목록</h3>
        <div className="leftPlanSide__pickLocal__type__container" onClick={toggleBtn}>
          <span
            className="leftPlanSide__pickLocal__type-btn sukbak"
            ref={suksoRef}
            onClick={() => {
              dispatch(changeShowWhichModal(1));
            }}
          >
            숙소
          </span>
          <span
            className="leftPlanSide__pickLocal__type-btn sukbak"
            ref={restaurantRef}
            onClick={() => {
              dispatch(changeShowWhichModal(2));
            }}
          >
            맛집
          </span>
          <span
            className="leftPlanSide__pickLocal__type-btn jangso lps__type-btn-picked"
            ref={jangsoRef}
            onClick={() => {
              dispatch(changeShowWhichModal(3));
            }}
          >
            장소
          </span>
        </div>
        {reduxState.showWhichModal === 1 ? (
          <SukSoMadal />
        ) : reduxState.showWhichModal === 2 ? (
          <MazzipModal />
        ) : (
          <JangSoModal />
        )}
      </div>
    </div>
  );
};

const ChangeDate = ({
  period,
  appearCalendar,
  setAppearCalendar,
  currPosition,
  saveDays,
  setIsLoading,
}) => {
  let sdy = period.startDate.getFullYear();
  let sdm = period.startDate.getMonth();
  let sdd = period.startDate.getDate();
  let sdDoW = period.startDate.getDay();
  let edy = period.endDate.getFullYear();
  let edm = period.endDate.getMonth();
  let edd = period.endDate.getDate();
  let dispatch = useDispatch();
  const getWeather = async () => {
    let arr = [];
    let forArr = new Array(saveDays + 1).fill(0);
    forArr.forEach((val, i) => {
      arr.push(`${sdy}\.${sdm + 1}\.${(sdd + i).toString().padStart(2, 0)}`);
    });
    let token = sessionStorage.getItem("token");

    await axios
      .get("/aamurest/weather", {
        params: {
          searchWord: currPosition,
          searchDate: arr.toString(),
        },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((weather) => {
        // console.log("weather", weather.data);
        dispatch(addWeather(weather.data.weather));
        setIsLoading(false);
      })
      .catch((err) => {
        console.log(err);
      });
  };
  useEffect(() => {
    setIsLoading(true);
    dispatch(resetMonthNDate([]));
    dispatch(
      addMonthNDate({
        month: sdm + 1,
        date: sdd,
        dow: sdDoW,
      })
    );
    getWeather();
  }, [period]);

  return (
    <div
      className="changeDate__container"
      onClick={() => {
        setAppearCalendar(!appearCalendar);
      }}
    >{`${sdy}.${sdm + 1}.${sdd} ~ ${edy}.${edm + 1}.${edd}`}</div>
  );
};

function JangSoModal() {
  let reduxState = useSelector((state) => {
    return state;
  });
  let dispatch = useDispatch();

  return (
    <div className="jangSoModal__container">
      <div className="jangSoModal__counter__container">
        <span className="jangSoModal__counter">{reduxState.arrForPickJangso.length}</span>
        <span>
          (총{reduxState.leftSideTimeSetter}시간{reduxState.leftSideMinSetter}
          분)
        </span>
      </div>
      <div
        className="jangSoModal__allDel-btn"
        onClick={() => {
          {
            reduxState.arrForPickJangso.map((local, index) => {
              dispatch(addArrInForJangso(local));
            });
          }
          dispatch(deleteAllPickJanso([]));
          dispatch(setInitForTime(0));
          dispatch(setInitForMin(0));
        }}
      >
        장소전체삭제
      </div>
      {reduxState.arrForPickJangso.length !== 0 ? (
        <div className="jangSoModal__pickedJangso__container">
          {reduxState.arrForPickJangso.map((local, index) => {
            return <PickedLocation local={local} key={index} />;
          })}
        </div>
      ) : (
        <div className="jangSoModal__desc">
          <span>가고 싶은 장소들을 검색하여 추가해주세요</span>
          <span> 설정하신 일자별 여행시간내에서</span>
          <span> 하루 평균 최대 8개의 장소까지 선택 가능합니다</span>
          <FontAwesomeIcon icon={faPlus} className="jangSoModal__plus-btn" />
        </div>
      )}
    </div>
  );
}

function MazzipModal() {
  let reduxState = useSelector((state) => {
    return state;
  });
  let dispatch = useDispatch();

  return (
    <div className="jangSoModal__container">
      <div className="jangSoModal__counter__container">
        <span className="jangSoModal__counter">{reduxState.arrForPickMazzip.length}</span>
        <span>
          (총{reduxState.leftSideTimeSetter}시간{reduxState.leftSideMinSetter}
          분)
        </span>
      </div>
      <div
        className="jangSoModal__allDel-btn"
        onClick={() => {
          {
            reduxState.arrForPickMazzip.map((local, index) => {
              dispatch(addArrInForRestaurant(local));
            });
          }
          dispatch(delAllPickMazzip([]));
          dispatch(setInitForTime(0));
          dispatch(setInitForMin(0));
        }}
      >
        맛집전체삭제
      </div>
      {reduxState.arrForPickMazzip.length !== 0 ? (
        <div className="jangSoModal__pickedJangso__container">
          {reduxState.arrForPickMazzip.map((local, index) => {
            return <PickedMazzip local={local} key={index} />;
          })}
        </div>
      ) : (
        <div className="jangSoModal__desc">
          <span>가고 싶은 맛집들을 검색하여 추가해주세요</span>
          <FontAwesomeIcon icon={faPlus} className="jangSoModal__plus-btn" />
        </div>
      )}
    </div>
  );
}

function PickedLocation({ local }) {
  let [timeVal, setTimeVal] = useState(2);
  let [minVal, setMinVal] = useState(0);
  let dispatch = useDispatch();
  let reduxState = useSelector((state) => {
    return state;
  });
  return (
    <div className="pickedLocation__container slide-in-right">
      <div
        className="pickedLocation__img-container"
        onClick={(e) => {
          e.stopPropagation();
          dispatch(changeLnfM(local));
        }}
      >
        <img
          src={local.smallimage ?? "/images/no-image.jpg"}
          onError={(e) => {
            e.target.src = "/images/no-image.jpg";
          }}
        />
      </div>
      <div className="pickedLocation__info-container">
        <div className="pickedLocation__info-title">
          <div>
            <h5>{local.title}</h5>
          </div>
          <FontAwesomeIcon
            icon={faX}
            className="pickedLocation__info-title__closeBtn"
            onClick={(e) => {
              e.stopPropagation();
              dispatch(deletePickJangso(local));
              dispatch(timeSetter(-2));
              dispatch(minSetter(-0));
              dispatch(addArrInForJangso(local));
            }}
          />
        </div>
        <div className="pickedLocation__time-container">
          <FontAwesomeIcon icon={faClock} style={{ marginRight: "3px" }} />
          <input
            type="number"
            name={1}
            min={0}
            max={24}
            maxLength={5}
            defaultValue={timeVal}
            style={{ width: "40px", fontSize: "20px", color: "var(--orange)" }}
            onChange={(e) => {
              e.stopPropagation();
              if (
                reduxState.leftSideTimeSetter * 60 + reduxState.leftSideMinSetter >
                reduxState.tripPeriod.length * 1440
              ) {
                alert("여행일자 x 24시간을 초과할 수 없습니다");
                setTimeVal(timeVal);
                // return;
              }
              // if (e.target.value !== 0) {
              if (timeVal > e.target.value) dispatch(timeSetter(e.target.value - timeVal));
              else dispatch(timeSetter(e.target.value - timeVal));
              setTimeVal(e.target.value);
              dispatch(addAtimeArrForJangso(timeVal * 60 * 60 * 1000));
              // }
            }}
          />
          <span style={{ fontSize: "13px" }}>시간</span>
          <input
            type="number"
            min={0}
            max={59}
            style={{ width: "40px", fontSize: "20px", color: "var(--orange)" }}
            defaultValue={minVal}
            onChange={(e) => {
              e.stopPropagation();
              if (
                reduxState.leftSideTimeSetter * 60 + reduxState.leftSideMinSetter >
                reduxState.tripPeriod.length * 1440
              ) {
                alert("여행일자 x 24시간을 초과할 수 없습니다");
                setMinVal(minVal);
                // return;
              }
              // if (e.target.value !== 0) {
              if (e.target.value >= 60) {
                timeVal++;
                e.target.value = 0;
              }
              if (minVal > e.target.value) dispatch(minSetter(e.target.value - minVal));
              else dispatch(minSetter(e.target.value - minVal));
              setMinVal(e.target.value);
              dispatch(addAtimeArrForJangso(minVal * 60 * 1000));
              // }
            }}
          />
          <span style={{ fontSize: "13px" }}>분</span>
        </div>
      </div>
    </div>
  );
}

function PickedMazzip({ local }) {
  let [timeVal, setTimeVal] = useState(2);
  let [minVal, setMinVal] = useState(0);
  let dispatch = useDispatch();
  let reduxState = useSelector((state) => {
    return state;
  });
  return (
    <div className="pickedLocation__container slide-in-right">
      <div
        className="pickedLocation__img-container"
        onClick={(e) => {
          e.stopPropagation();
          dispatch(changeLnfM(local));
        }}
      >
        <img
          src={local.smallimage ?? "/images/no-image.jpg"}
          onError={(e) => {
            e.target.src = "/images/no-image.jpg";
          }}
        />
      </div>
      <div className="pickedLocation__info-container">
        <div className="pickedLocation__info-title">
          <div>
            <h5>{local.title}</h5>
          </div>
          <FontAwesomeIcon
            icon={faX}
            className="pickedLocation__info-title__closeBtn"
            onClick={(e) => {
              e.stopPropagation();
              dispatch(delPickMazzip(local));
              console.log("맛집", local);
              dispatch(timeSetter(-2));
              dispatch(minSetter(-0));
              dispatch(addArrInForRestaurant(local));
            }}
          />
        </div>
        <div className="pickedLocation__time-container">
          <FontAwesomeIcon icon={faClock} style={{ marginRight: "3px" }} />
          <input
            type="number"
            name={1}
            min={0}
            max={24}
            maxLength={5}
            defaultValue={timeVal}
            style={{ width: "40px", fontSize: "20px", color: "var(--orange)" }}
            onChange={(e) => {
              e.stopPropagation();
              if (
                reduxState.leftSideTimeSetter * 60 + reduxState.leftSideMinSetter >
                reduxState.tripPeriod.length * 1440
              ) {
                alert("여행일자 x 24시간을 초과할 수 없습니다");
                setTimeVal(timeVal);
                // return;
              }
              // if (e.target.value !== 0) {
              if (timeVal > e.target.value) dispatch(timeSetter(e.target.value - timeVal));
              else dispatch(timeSetter(e.target.value - timeVal));
              setTimeVal(e.target.value);
              dispatch(addAtimeArrForJangso(timeVal * 60 * 60 * 1000));
              // }
            }}
          />
          <span style={{ fontSize: "13px" }}>시간</span>
          <input
            type="number"
            min={0}
            max={59}
            style={{ width: "40px", fontSize: "20px", color: "var(--orange)" }}
            defaultValue={minVal}
            onChange={(e) => {
              e.stopPropagation();
              if (
                reduxState.leftSideTimeSetter * 60 + reduxState.leftSideMinSetter >
                reduxState.tripPeriod.length * 1440
              ) {
                alert("여행일자 x 24시간을 초과할 수 없습니다");
                setMinVal(minVal);
                // return;
              }
              // if (e.target.value !== 0) {
              if (e.target.value >= 60) {
                timeVal++;
                e.target.value = 0;
              }
              if (minVal > e.target.value) dispatch(minSetter(e.target.value - minVal));
              else dispatch(minSetter(e.target.value - minVal));
              setMinVal(e.target.value);
              dispatch(addAtimeArrForJangso(minVal * 60 * 1000));
              // }
            }}
          />
          <span style={{ fontSize: "13px" }}>분</span>
        </div>
      </div>
    </div>
  );
}

function SukSoMadal() {
  let reduxState = useSelector((state) => {
    return state;
  });
  let dispatch = useDispatch();
  return (
    <div className="suksoModal__container">
      <div className="suksoModal__counter__container">
        <span className="suksoModal__counter">0</span>
      </div>
      <div
        className="suksoModal__allDel-btn"
        onClick={() => {
          dispatch(changeSaveDaysRedux(reduxState.saveDaysNPickedSuksoRedux.length));
        }}
      >
        숙소전체삭제
      </div>
      <div className="suksoModal__desc">
        <span>숙소는 일정의 시작 지점과 종료 지점으로 설정됩니다.</span>
        <span> 마지막 날은 시작 지점으로만 설정됩니다.</span>
      </div>
      {reduxState.saveDaysNPickedSuksoRedux.map((local, index) => {
        return <SukSoSubModal index={index} local={local} key={index} />;
      })}
    </div>
  );
}

function SukSoSubModal({ index, local }) {
  let reduxState = useSelector((state) => {
    return state;
  });

  return (
    <div className="sukSoSubModal__container">
      <div className="sukSoSubModal__dayBtn">
        DAY {index + 1}
        <span>
          {" "}
          ({reduxState.monthNdate[0].month}. {reduxState.monthNdate[0].date + index} ~{" "}
          {reduxState.monthNdate[0].month}. {reduxState.monthNdate[0].date + index + 1})
        </span>
      </div>
      {local !== 0 ? (
        <PickedSukso local={local} />
      ) : (
        <div className={`suksoModal__desc ${index}`}>
          <span>일자버튼을 누르고 숙소를 추가하세요</span>
          <FontAwesomeIcon icon={faPlus} className="sukSoSubModal__plus-btn" onClick={() => {}} />
        </div>
      )}
    </div>
  );
}

function PickedSukso({ local }) {
  let dispatch = useDispatch();

  return (
    <div className="pickedSukso__container slide-in-right">
      <div
        className="pickedSukso__img-container"
        onClick={(e) => {
          e.stopPropagation();
          console.log("123");
          dispatch(changeLnfM(local));
        }}
      >
        <img
          src={local.smallimage ?? "/images/no-image.jpg"}
          onError={(e) => {
            e.target.src = "/images/no-image.jpg";
          }}
        />
      </div>
      <div className="pickedSukso__info-container">
        <div className="pickedSukso__info-title">
          <h4>{local.title}</h4>
          <FontAwesomeIcon
            icon={faX}
            className="pickedSukso__info-title__closeBtn"
            onClick={(e) => {
              e.stopPropagation();
              dispatch(delOneSaveDaysRedux(local));
            }}
          />
        </div>
      </div>
    </div>
  );
}

function toggleBtn(e) {
  if (e.target.nodeName === "SPAN") {
    if (e.target.classList.contains("sukbak")) {
      e.target.nextElementSibling.classList.remove("lps__type-btn-picked");
      e.target.classList.add("lps__type-btn-picked");
    } else {
      e.target.previousElementSibling.classList.remove("lps__type-btn-picked");
      e.target.classList.add("lps__type-btn-picked");
    }
  }
}

export default LeftPlanSide;
