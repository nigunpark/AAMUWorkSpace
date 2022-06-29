import { faLocationDot } from "@fortawesome/free-solid-svg-icons";
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
  const [timeVal, setTimeVal] = useState("");
  return (
    <div className="createPlanLeft">
      <div className="createPlanLeft__days">
        <h5>일정</h5>
        <span>전체일정</span>
        <div className="createPlanLeft__days-container">
          {reduxState.saveDaysNPickedSuksoRedux.map((day, index) => {
            return <span>Day{index + 1}</span>;
          })}
        </div>
      </div>
      <Scheduler />
    </div>
  );
};

function Scheduler() {
  return (
    <div>
      <div className="createPlanLeft__schedule">
        <div>대한민국 제주도 : 3일 여행</div>
      </div>
      <select>
        <option>1DAY 7월 6일 수</option>
        <option>2DAY 7월 7일 목</option>
        <option>3DAY 7월 8일 금</option>
      </select>
      <span>일차를 누르면 일정 전체가 변경이 가능합니다.</span>
      <FontAwesomeIcon icon={faLocationDot} /> 3장소
      <div>
        시작
        <LocalizationProvider dateAdapter={AdapterDateFns}>
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
      </div>
    </div>
  );
}
export default CreatePlanLeft;
