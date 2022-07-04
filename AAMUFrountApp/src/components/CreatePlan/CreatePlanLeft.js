import {
  faClock,
  faEllipsisVertical,
  faLocationDot,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { useEffect, useState } from "react";
import "./CreatePlanLeft.css";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import Stack from "@mui/material/Stack";
import { TimePicker } from "@mui/x-date-pickers/TimePicker";
import { AdapterDateFns } from "@mui/x-date-pickers/AdapterDateFns";
import { TextField } from "@mui/material";
import { useDispatch, useSelector } from "react-redux";
import { corrTimeSetObj } from "../../redux/store";
const CreatePlanLeft = ({ currPosition }) => {
  let reduxState = useSelector((state) => {
    return state;
  });
  let [selected, setSelected] = useState(false);
  return (
    <div className="createPlanLeft">
      <div className="createPlanLeft__days">
        <h5>일정</h5>
        <span style={{ fontSize: "11px" }}>전체일정</span>
        <div className="createPlanLeft__days-container">
          {reduxState.tripPeriod.map((day, index) => {
            return <span key={index}>Day{index + 1}</span>;
          })}
        </div>
      </div>
      <WholeSchedule
        selected={selected}
        setSelected={setSelected}
        currPosition={currPosition}
      />
    </div>
  );
};

function WholeSchedule({ selected, setSelected, currPosition }) {
  let reduxState = useSelector((state) => {
    return state;
  });

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
              selected={selected}
              setSelected={setSelected}
              key={index}
            />
          );
        })}
      </div>
    </div>
  );
}

function Content({ index }) {
  let reduxState = useSelector((state) => {
    return state;
  });
  let dispatch = useDispatch();
  let [showTimePicker, setShowTimePicker] = useState(false);
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
        3장소
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
            {
              reduxState.timeSetObj.find((obj) => {
                return obj.day === index + 1;
              }).time
            }
          </span>
          {" : "}
          <span>
            {
              reduxState.timeSetObj.find((obj) => {
                return obj.day === index + 1;
              }).min
            }
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

      {reduxState.arrForPickJangso.map((local, i) => {
        return <DetailSetting key={i} index={index} local={local} />;
      })}
    </div>
  );
}

function DetailSetting({ index, local }) {
  let reduxState = useSelector((state) => {
    return state;
  });
  return (
    <div className="detailSetting__container">
      <div className="movingTime">
        <FontAwesomeIcon icon={faEllipsisVertical} />
        <div className="movingTime__time">
          {/* 이동시간 받아서 뿌려주는 input */}
          <input type="number" style={{ width: "37px" }} defaultValue={5} />
          <span>분</span>
        </div>
      </div>
      <div className="detailLocation">
        <div className="detailLocation__img-container">
          <img
            className="detailLocation__img"
            src={local.image2}
            alt="이미지"
            onError={(e) => {
              e.target.src = "/images/no-image.jpg";
            }}
          />
        </div>
        <div className="detailLocation__inform-container">
          <div className="detailLocation__part-top">
            <div>
              <div>{local.title}</div>
              <div>0시 0분</div>
            </div>
            <div className="detailLocation__clock">
              <span>
                {
                  reduxState.timeSetObj.find((obj) => {
                    return obj.day === index + 1;
                  }).time
                }
                :
                {
                  reduxState.timeSetObj.find((obj) => {
                    return obj.day === index + 1;
                  }).min
                }
              </span>
              <span>~</span>
              <span>ddd</span>
            </div>
          </div>
          <div className="detailLocation__part-bottom">
            <span>시간표</span>
            <span>구매</span>
            <span>메모</span>
            <span>삭제</span>
          </div>
        </div>
      </div>
    </div>
  );
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
