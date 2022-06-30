import React, { useEffect, useRef, useState } from "react";
import "./PlanTripTime.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faAngleDown,
  faAngleUp,
  faClock,
} from "@fortawesome/free-solid-svg-icons";
import TextField from "@mui/material/TextField";
import Stack from "@mui/material/Stack";
import { AdapterDateFns } from "@mui/x-date-pickers/AdapterDateFns";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { TimePicker } from "@mui/x-date-pickers/TimePicker";
import { useSelector, useDispatch } from "react-redux";
import { changeTimeSetObj, delTimeSetObj } from "../../redux/store";
const PlanTripTime = ({ saveDays, savePeriod }) => {
  const [period, setPeriod] = useState([]);
  const [appearTimeSet, setAppearTimeSet] = useState(false);

  let reff = useRef();
  let reduxState = useSelector((state) => {
    return state;
  });
  useEffect(() => {
    let newArr = new Array(saveDays + 1).fill(0);
    setPeriod(newArr);
  }, [saveDays]);
  return (
    <>
      <div className="planTripTime__container">
        <div
          className="planTripTime__title"
          onClick={() => {
            reff.current.classList.toggle("pttm_open");
          }}>
          <FontAwesomeIcon icon={faClock} />
          <p>여행시간 상세설정(총 {(saveDays + 1) * 12}시간)</p>
          <FontAwesomeIcon icon={appearTimeSet ? faAngleUp : faAngleDown} />
        </div>
        <div className="planTripTime__modal" ref={reff}>
          {period.map((val, index) => {
            return <TimeSet index={index + 1} key={index} />;
          })}
        </div>
      </div>
    </>
  );
};

//여행시간 상세설정하는 부분
const TimeSet = ({ index }) => {
  const [startTime, setStartTime] = useState(new Date("2022-01-01 10:00"));
  const [endTime, setEndTime] = useState(new Date("2020-01-01 10:00"));
  const [appear, setAppear] = useState(false);
  const [startTimeArray, setStartTimeArray] = useState(["오전", "--", "--"]);
  const [endTimeArray, setendTimeArray] = useState(["오후", "--", "--"]);
  let reduxState = useSelector((state) => {
    return state;
  });
  let dispatch = useDispatch();

  return (
    <>
      <div>
        <div
          className="setTime__container"
          onClick={() => {
            setAppear(!appear);
          }}>
          <p className="setTime__contiainer-day">{index} Day</p>
          <div className="setTime__container-timeNMin">
            <p>
              {startTimeArray[0]} {startTimeArray[1]} : {startTimeArray[2]}
            </p>
            <p> ~ </p>
            <p>
              {endTimeArray[0]} {endTimeArray[1]} : {endTimeArray[2]}
            </p>
          </div>
        </div>
        <div></div>
      </div>

      {appear && (
        <LocalizationProvider dateAdapter={AdapterDateFns}>
          <Stack spacing={2} sx={{ color: "red", m: 1 }}>
            <TimePicker
              className="timePicker"
              renderInput={(params) => <TextField {...params} />}
              value={startTime}
              showToolbar={true}
              label=""
              onChange={(newValue) => {
                getAmpmTmStart(
                  newValue,
                  setStartTimeArray,
                  index,
                  dispatch,
                  reduxState
                );

                if (Date.parse(newValue) >= Date.parse(endTime)) {
                  setStartTime(newValue);
                  setEndTime(newValue);
                } else setStartTime(newValue);
              }}
            />
            <TimePicker
              renderInput={(params) => <TextField {...params} />}
              value={endTime}
              label=""
              showToolbar={true}
              onChange={(newValue) => {
                getAmpmTmEnd(newValue, setendTimeArray);
                if (Date.parse(newValue) - Date.parse(startTime) < 0) {
                  setEndTime(startTime);
                } else {
                  setEndTime(newValue);
                }
              }}
            />
          </Stack>
        </LocalizationProvider>
      )}
    </>
  );
};
//getAm(오전)pm(오후)T(time)m(mins)
function getAmpmTmStart(
  newValue,
  setStartTimeArray,
  index,
  dispatch,
  reduxState
) {
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
  array.push(min);
  setStartTimeArray(array);
  if (
    reduxState.timeSetObj.find((data) => {
      return data.day === index;
    }) !== undefined
  ) {
    dispatch(delTimeSetObj(index));
  }
  dispatch(
    changeTimeSetObj({
      day: index,
      ampm: ampm,
      time: time,
      min: min,
    })
  );
}

function getAmpmTmEnd(newValue, setendTimeArray) {
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
  array.push(min);
  setendTimeArray(array);
}

export { PlanTripTime, TimeSet };
