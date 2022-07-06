import React, { useRef, useState } from "react";
import { DateRange } from "react-date-range";
import { addDays } from "date-fns";
import "react-date-range/dist/styles.css"; // main css file
import "react-date-range/dist/theme/default.css"; // theme css file
import ko from "date-fns/locale/ko";
import "./DateRangePicker.css";
import { useDispatch } from "react-redux";
import {
  changeAllSaveDaysRedux,
  changeSaveDaysRedux,
  changeTripPeriod,
} from "../../redux/store";
const DateRangePick = ({
  setDays,
  setPeriod,
  savePeriod,
  setSavePeriod,
  saveDays,
  setSaveDays,
  appearCalendar,
  setAppearCalendar,
}) => {
  const [state, setState] = useState([
    {
      startDate: new Date(),
      endDate: new Date(),
      key: "selection",
    },
  ]);
  let reff = useRef();
  let dispatch = useDispatch();
  return (
    <>
      <div className="dateRangePicker__container">
        <div>
          <DateRange
            ref={reff}
            id="calID"
            editableDateInputs={true}
            onChange={(item) => {
              setState([item.selection]);
              setSaveDays(
                (Date.parse(item.selection.endDate) -
                  Date.parse(item.selection.startDate)) /
                  1000 /
                  60 /
                  60 /
                  24
              );
              setSavePeriod(item.selection);
            }}
            moveRangeOnFirstSelection={false}
            ranges={state}
            months={2}
            direction="horizontal"
            locale={ko}
            minDate={addDays(new Date(), 0)}
            showDateDisplay={true}
          />
        </div>
        <div className="dateRangePicker__btn-container">
          <div
            className="dateRangePicker__closeBtn"
            onClick={() => {
              setAppearCalendar(!appearCalendar);
            }}>
            닫기
          </div>
          <div
            className="dateRangePicker__acceptBtn"
            onClick={() => {
              setPeriod(savePeriod);
              setDays(saveDays);
              setAppearCalendar(!appearCalendar);
              dispatch(changeSaveDaysRedux(saveDays));
              dispatch(changeTripPeriod(saveDays + 1));
            }}>
            적용하기
          </div>
        </div>
      </div>
    </>
  );
};

export default DateRangePick;
