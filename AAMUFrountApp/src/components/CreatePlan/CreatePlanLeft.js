import {
  faClock,
  faEllipsisVertical,
  faHeart,
  faLocationDot,
} from "@fortawesome/free-solid-svg-icons";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { useState } from "react";
import "./CreatePlanLeft.css";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import Stack from "@mui/material/Stack";
import { TimePicker } from "@mui/x-date-pickers/TimePicker";
import { AdapterDateFns } from "@mui/x-date-pickers/AdapterDateFns";
import { TextField } from "@mui/material";
import { useSelector } from "react-redux";
const CreatePlanLeft = () => {
  let reduxState = useSelector((state) => {
    return state;
  });
  let [heart, setHeart] = useState(false);
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
      <WholeSchedule heart={heart} setHeart={setHeart} />
    </div>
  );
};

function WholeSchedule({ heart, setHeart }) {
  let reduxState = useSelector((state) => {
    return state;
  });
  return (
    <div className="createPlanLeft__schedule">
      <div className="createPlanLeft__schedule-title">
        <div>
          대한민국 제주도 :{" "}
          <span style={{ fontSize: "20px", color: "var(--orange)" }}>
            {reduxState.tripPeriod.length}
          </span>
          일 여행
        </div>
      </div>
      <div className="createPlanLeft__schedule__content-container">
        {reduxState.tripPeriod.map((val, index) => {
          return <Content index={index} heart={heart} setHeart={setHeart} />;
        })}
      </div>
    </div>
  );
}

function Content({ index, heart, setHeart }) {
  let reduxState = useSelector((state) => {
    return state;
  });
  let [showTimePicker, setShowTimePicker] = useState(false);
  return (
    <div className="createPlanLeft__schedule__content">
      <select className="createPlanLeft__schedule__select">
        {reduxState.tripPeriod.map((val, index) => {
          return (
            <option key={index}>
              {index + 1}DAY {reduxState.monthNdate[0].month}월{" "}
              {reduxState.monthNdate[0].date + index}일 수
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
        }}>
        일차를 누르면 일정 전체가 변경이 가능합니다.
      </span>
      <div style={{ marginBottom: "10px", fontSize: "13px" }}>
        <FontAwesomeIcon icon={faLocationDot} style={{ color: "var(--red)" }} />{" "}
        3장소
        {heart ? (
          <FontAwesomeIcon
            icon={faHeart}
            style={{ color: "red" }}
            onClick={() => {
              setHeart(!heart);
            }}
          />
        ) : (
          <i
            class="fa-regular fa-heart"
            onClick={() => {
              setHeart(!heart);
            }}
          />
        )}
      </div>

      <div className="createPlanLeft__schedule__time">
        <span style={{ fontSize: "13px" }}>시작</span>{" "}
        {/* 메인페이지 leftSide에서 여행시작시간 설정 한 거 뿌려줌
           설정하지 않았으면 기본적으로 오전10시로 설정*/}
        {reduxState.timeSetObj.findIndex((obj) => {
          return obj.day === index + 1;
        }) !== -1 ? (
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
        ) : (
          <span className="createPlanLeft__schedule__time-span">
            <span>오전</span>
            <span>10</span>
            <span>:</span>
            <span>00</span>
          </span>
        )}
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
          className="createPlanLeft__timePicker">
          <Stack sx={{ m: 1, p: 0 }}>
            <TimePicker
              className="timePicker"
              renderInput={(params) => <TextField {...params} />}
              value={1}
              showToolbar={true}
              label=""
              onChange={(newValue) => {
                console.log(newValue);
              }}
            />
          </Stack>
        </LocalizationProvider>
      ) : null}
      <DetailSetting />
      <DetailSetting />
      <DetailSetting />
    </div>
  );
}

function DetailSetting() {
  return (
    <div className="detailSetting__container">
      <div className="movingTime">
        <FontAwesomeIcon icon={faEllipsisVertical} />
        <div className="movingTime__time">
          <input type="number" style={{ width: "37px" }} />
          <span>분</span>
        </div>
      </div>
      <div className="detailLocation">
        <div className="detailLocation__img-container">
          <img
            className="detailLocation__img"
            src="/images/img-5.jpg"
            alt="이미지"
            onError={(e) => {
              e.target.src = "/images/no-image.jpg";
            }}
          />
        </div>
        <div className="detailLocation__inform-container">
          <div className="detailLocation__part-top">
            <div>
              <div>제주공항</div>
              <div>0시 0분</div>
            </div>
            <div>전자시계</div>
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
export default CreatePlanLeft;
