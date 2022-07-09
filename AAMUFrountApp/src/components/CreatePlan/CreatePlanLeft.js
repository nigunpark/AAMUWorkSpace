import {
  faCalendar,
  faChevronDown,
  faClock,
  faEllipsisVertical,
  faLocationDot,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { useEffect, useRef, useState } from "react";
import "./CreatePlanLeft.css";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import Stack from "@mui/material/Stack";
import { TimePicker } from "@mui/x-date-pickers/TimePicker";
import { AdapterDateFns } from "@mui/x-date-pickers/AdapterDateFns";
import { TextField } from "@mui/material";
import { useDispatch, useSelector } from "react-redux";
import { addToAccum, corrTimeSetObj } from "../../redux/store";
const CreatePlanLeft = ({ currPosition, fromWooJaeData }) => {
  let reduxState = useSelector((state) => {
    return state;
  });
  const [whichModal, setWhichModal] = useState("전체일정");
  const dayRef = useRef();
  // console.log("fromWooJaeData:", fromWooJaeData);
  const [temp, setTemp] = useState("");
  useEffect(() => {
    setTemp(dayRef.current);
  }, []);
  return (
    <div className="createPlanLeft">
      <div className="createPlanLeft__days">
        <h5>일정</h5>
        <span
          className="wholeDays days-container-active"
          ref={dayRef}
          style={{ fontSize: "11px" }}
          onClick={(e) => {
            setWhichModal("전체일정");
            setTemp(e.target);
            temp.classList.remove("days-container-active");
            e.target.classList.add("days-container-active");
          }}
        >
          전체일정
        </span>
        <div className="createPlanLeft__days-container">
          {reduxState.tripPeriod.map((day, index) => {
            return (
              <span
                className="days-span"
                key={index}
                onClick={(e) => {
                  setWhichModal(`day${index + 1}`);
                  setTemp(e.target);
                  temp.classList.remove("days-container-active");
                  e.target.classList.add("days-container-active");
                }}
              >
                Day{index + 1}
              </span>
            );
          })}
        </div>
      </div>
      <div style={{ width: "100%" }}>
        <WhichModal
          whichModal={whichModal}
          currPosition={currPosition}
          fromWooJaeData={fromWooJaeData}
        />
      </div>
    </div>
  );
};

function WhichModal({ whichModal, currPosition, fromWooJaeData }) {
  if (whichModal === "전체일정") {
    return (
      <WholeSchedule
        currPosition={currPosition}
        fromWooJaeData={fromWooJaeData}
      />
    );
  } else {
    let obj = fromWooJaeData.find((obj, i) => {
      return Object.keys(obj).toString() == whichModal;
    });
    let arr = obj[Object.keys(obj)];
    let newArr = new Array(arr.length - 1).fill(0);
    return newArr.map((val, index) => {
      return <Step arr={arr} index={index} key={index} />;
    });
  }
}

function WholeSchedule({ currPosition, fromWooJaeData }) {
  let reduxState = useSelector((state) => {
    return state;
  });
  let [accumTime, setAccumTime] = useState(0);
  return (
    <div className="createPlanLeft__schedule">
      <div className="createPlanLeft__schedule-title">
        <div>
          대한민국 {currPosition} :{" "}
          <span style={{ fontSize: "20px", color: "var(--orange)" }}>
            {reduxState.tripPeriod.length}
          </span>
          일 여행
        </div>
      </div>
      <div className="createPlanLeft__schedule__content-container">
        {reduxState.tripPeriod.map((val, index) => {
          return (
            <Content
              index={index}
              key={index}
              fromWooJaeData={fromWooJaeData}
              accumTime={accumTime}
              setAccumTime={setAccumTime}
            />
          );
        })}
      </div>
    </div>
  );
}

function Content({ index, fromWooJaeData, accumTime, setAccumTime }) {
  let reduxState = useSelector((state) => {
    return state;
  });
  let dispatch = useDispatch();
  let [showTimePicker, setShowTimePicker] = useState(false);
  // console.log("accumTime:", accumTime);
  if (fromWooJaeData.length === 0) return;
  return (
    <div className="createPlanLeft__schedule__content">
      <select className="createPlanLeft__schedule__select">
        {reduxState.tripPeriod.map((val, i) => {
          return (
            <option
              key={i}
              defaultValue=""
              selected={index + 1 === i + 1 ? true : false}
            >
              {i + 1}DAY {reduxState.monthNdate[0].month}월{" "}
              {reduxState.monthNdate[0].date + i}일 수
            </option>
          );
        })}
      </select>
      <span
        style={{
          fontSize: "10px",
          color: "grey",
          fontWeight: "bold",
          marginTop: "10px",
        }}
      >
        일차를 누르면 일정 전체가 변경이 가능합니다.
      </span>
      <div style={{ marginBottom: "10px", fontSize: "13px" }}>
        <FontAwesomeIcon icon={faLocationDot} style={{ color: "var(--red)" }} />{" "}
        {fromWooJaeData[index]["day" + (index + 1)].length}장소
      </div>
      <div className="createPlanLeft__schedule__time">
        <span style={{ fontSize: "13px" }}>시작</span>{" "}
        <span className="createPlanLeft__schedule__time-span">
          <span>
            {
              reduxState.timeSetObj.find((obj) => {
                return obj.day === index + 1;
              }).ampm
            }
          </span>{" "}
          <span>
            {reduxState.timeSetObj
              .find((obj) => {
                return obj.day === index + 1;
              })
              .time.toString()
              .trim()
              .padStart(2, "0")}
          </span>
          {" : "}
          <span>
            {reduxState.timeSetObj
              .find((obj) => {
                return obj.day === index + 1;
              })
              .min.toString()
              .trim()
              .padStart(2, "0")}
          </span>
        </span>
        <FontAwesomeIcon
          icon={faClock}
          onClick={() => {
            setShowTimePicker(!showTimePicker);
          }}
        />
      </div>
      <hr />
      {showTimePicker ? (
        <LocalizationProvider
          dateAdapter={AdapterDateFns}
          className="createPlanLeft__timePicker"
        >
          <Stack sx={{ m: 1, p: 0 }}>
            <TimePicker
              className="timePicker"
              renderInput={(params) => <TextField {...params} />}
              value={
                reduxState.timeSetObj.find((obj) => {
                  return obj.day === index + 1;
                }).fullDate
              }
              showToolbar={true}
              label=""
              onChange={(newValue) => {
                let newObj = getAmpmTmStart(newValue, index);
                dispatch(corrTimeSetObj(newObj));
              }}
            />
          </Stack>
        </LocalizationProvider>
      ) : null}
      {fromWooJaeData[index]["day" + (index + 1)].map((obj, i) => {
        return (
          <DetailSetting
            obj={obj}
            key={i}
            index={i}
            accumTime={accumTime}
            setAccumTime={setAccumTime}
          />
        );
      })}
    </div>
  );
}

function DetailSetting({ obj, index, accumTime, setAccumTime }) {
  let reduxState = useSelector((state) => {
    return state;
  });
  const [upTime, setUpTime] = useState(0);
  const [downTime, setDownTime] = useState(0);
  useEffect(() => {
    if (index === 0) {
      let firstAccum = getNAccumDetailTime(index, reduxState, obj);
      // console.log(firstAccum);
      setUpTime(firstAccum);
      setDownTime(firstAccum + obj.atime / 1000 / 60);
      setAccumTime(firstAccum + obj.atime / 1000 / 60);
    }
    if (index !== 0) {
      setUpTime(accumTime + obj.mtime / 1000 / 60);
      setDownTime(accumTime + obj.mtime / 1000 / 60 + obj.atime / 1000 / 60);
      setAccumTime(accumTime + obj.mtime / 1000 / 60 + obj.atime / 1000 / 60);
    }
  }, []);
  const [showMemo, setShowMemo] = useState(false);
  if (
    obj.dto === null ||
    reduxState.timeSetObj.find((obj) => {
      return obj.day === index + 1;
    }) === undefined
  )
    return;
  return (
    <div className="detailSetting__container">
      <div className="movingTime">
        <FontAwesomeIcon icon={faEllipsisVertical} />
        <div className="movingTime__time">
          {/* 이동시간 받아서 뿌려주는 input */}
          <input
            type="number"
            style={{ width: "37px" }}
            defaultValue={obj.mtime / 1000 / 60}
          />
          <span>분</span>
        </div>
      </div>
      <div className="detailLocation">
        <div className="detailLocation__img-container">
          <img
            className="detailLocation__img"
            src={obj.dto.smallimage ?? "/images/no-image.jpg"}
            // src={"/images/no-image.jpg"}
            alt="이미지"
            onError={(e) => {
              e.target.src = "/images/no-image.jpg";
            }}
          />
        </div>
        <div className="detailLocation__inform-container">
          <div className="detailLocation__part-top">
            <div>
              <div>{obj.dto.title}</div>
              <div style={{ color: "var(--orange)" }}>
                {obj.atime / 1000 / 60 / 60 + "시간"}{" "}
                {((obj.atime / 1000 / 60) % 60) + "분"}
              </div>
            </div>
            <div className="detailLocation__clock">
              <span>
                {Math.floor(upTime / 60)}:{Math.floor(upTime % 60)}
              </span>
              <span>~</span>
              <span>
                {Math.floor(downTime / 60)}:{Math.floor(downTime % 60)}
              </span>
            </div>
          </div>
          <div className="detailLocation__part-bottom">
            <span>시간표</span>
            <a
              href={`https://www.myrealtrip.com/search?q=${obj.dto.title}`}
              target="blank"
            >
              <span>구매</span>
            </a>
            <span
              onClick={() => {
                setShowMemo(true);
              }}
            >
              메모
            </span>
            <span>삭제</span>
          </div>
        </div>
        {showMemo && <MemoArea setShowMemo={setShowMemo} />}
      </div>
    </div>
  );
}

function Step({ arr, index }) {
  return (
    <div className="step">
      <div className="step__container">
        <div className="step__step">
          <span>STEP{index + 1}</span>
        </div>
        <div className="step__content">
          <span>{arr[index].dto.title}</span>
          <FontAwesomeIcon icon={faChevronDown} />
          <span>{arr[index + 1].dto.title}</span>
        </div>
        <div className="step__btn">
          <a
            target="_blank"
            className="step__btn-atag"
            href={`https://map.kakao.com/?sName=${arr[index].dto.title}&eName=${
              arr[index + 1].dto.title
            }`}
          >
            상세경로
          </a>
        </div>
      </div>
    </div>
  );
}

function MemoArea({ setShowMemo }) {
  let textAreaRef = useRef();

  return (
    <div className="memoArea__container">
      <div className="memoArea__content">
        <div>
          <h5>Memo</h5>
        </div>
        <div className="memoArea__textArea">
          <textarea
            ref={textAreaRef}
            rows="3"
            cols="25"
            style={{ resize: "none" }}
          ></textarea>
        </div>
      </div>
      <div className="memoArea__btn">
        <span
          onClick={() => {
            setShowMemo(false);
          }}
        >
          완료
        </span>
      </div>
    </div>
  );
}
function getNAccumDetailTime(index, reduxState, obj) {
  // console.log("timeSetObj", reduxState.timeSetObj);
  let sTime = reduxState.timeSetObj.find((val) => {
    return val.day === index + 1;
  });
  if (sTime.ampm === "오후") return sTime.time + 12;
  let sumTime = sTime.time * 60 + sTime.min + obj.mtime / 1000 / 60;
  return sumTime;
}

function getAmpmTmStart(newValue, index) {
  let array = [];
  //오전오후 가져오는 코드
  let localeString = newValue.toLocaleString();
  let ampm = localeString.substring(
    localeString.indexOf("오"),
    localeString.indexOf("오") + 2
  );
  array.push(ampm);
  //시간가져오는 코드
  let time = localeString
    .substring(localeString.indexOf(":") - 2, localeString.indexOf(":"))
    .trim()
    .padStart(2, 0);
  array.push(time);
  //분 가져오는 코드
  let min = localeString.substring(
    localeString.indexOf(":") + 1,
    localeString.lastIndexOf(":")
  );

  return {
    day: index + 1,
    fullDate: newValue,
    ampm: ampm,
    time: time,
    min: min,
  };
}

export default CreatePlanLeft;
