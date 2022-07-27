import {
  faChevronDown,
  faClock,
  faClockRotateLeft,
  faEllipsisVertical,
  faLocationDot,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { useEffect, useRef, useState } from "react";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import Stack from "@mui/material/Stack";
import { TimePicker } from "@mui/x-date-pickers/TimePicker";
import { AdapterDateFns } from "@mui/x-date-pickers/AdapterDateFns";
import { Alert, TextField } from "@mui/material";
import { useDispatch, useSelector } from "react-redux";
import {
  addWholeBlackBox,
  changeTimeSetObj,
  corrTimeSetObj,
  delAllTimeSetObj,
  delAllWholeBb,
} from "../../../redux/store";
import axios from "axios";
let count = 0;
const MyCreatePlanLeft = ({
  currPosition,
  fromWooJaeData,
  setForDayLine,
  setFromWooJaeData,
  routeMap
}) => {
  let reduxState = useSelector((state) => {
    return state;
  });
  const [whichModal, setWhichModal] = useState("전체일정");
  const dayRef = useRef();
  const [temp, setTemp] = useState("");

  useEffect(() => {
    setTemp(dayRef.current);
  }, []);
  if (fromWooJaeData.length === 0) return;
  if (fromWooJaeData === undefined) return;
  console.log("fromWooJaeData", fromWooJaeData);
  let tempWholeArr = [];
  fromWooJaeData.forEach((val, i) => {
    let temp = Object.values(val)[0];
    temp.map((obj, i) => {
      tempWholeArr.push(obj);
    });
  });
  tempWholeArr.forEach((val, i) => {
    val.id = i;
  });

  // console.log("reduxState.monthNdate 이거 : ", reduxState.monthNdate);
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
            setForDayLine(0);
            count++;
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
                  e.target.classList.add("days-container-active");
                  if (temp !== undefined)
                    temp.classList.remove("days-container-active");
                  if (count === 0)
                    dayRef.current.classList.remove("days-container-active");
                  setForDayLine(index + 1);
                  count++;
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
          setFromWooJaeData={setFromWooJaeData}
          routeMap={routeMap}
        />
      </div>
    </div>
  );
};

function WhichModal({
  whichModal,
  currPosition,
  fromWooJaeData,
  setFromWooJaeData,
  routeMap
}) {
  if (whichModal === "전체일정") {
    return (
      <WholeSchedule
        currPosition={currPosition}
        fromWooJaeData={fromWooJaeData}
        setFromWooJaeData={setFromWooJaeData}
        routeMap={routeMap}
      />
    );
  } else {
    let obj = fromWooJaeData.find((obj, i) => {
      return Object.keys(obj).toString() == whichModal;
    });
    let arr = obj[Object.keys(obj)];
    if (arr.length <= 1) {
      return (
        <Alert severity="info">
          <strong>장소가 최소 2개이상 있어야</strong>
          <br />
          <strong>상세경로 확인 가능해요</strong>
        </Alert>
      );
    } else {
      let newArr = new Array(arr.length - 1).fill(0);
      return newArr.map((val, index) => {
        return <Step arr={arr} index={index} key={index} />;
      });
    }
  }
}

function WholeSchedule({ currPosition, fromWooJaeData, setFromWooJaeData, routeMap }) {
  let reduxState = useSelector((state) => {
    return state;
  });
  let dispatch = useDispatch();
  useEffect(() => {
    dispatch(delAllWholeBb([]));
    dispatch(delAllTimeSetObj([]))
  }, []);

  

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
        {fromWooJaeData.map((val, index) => {
          return (
            <Content
              index={index}
              key={index}
              fromWooJaeData={fromWooJaeData}
              setFromWooJaeData={setFromWooJaeData}
              routeMap={routeMap}
            />
          );
        })}
      </div>
    </div>
  );
}

function Content({ index, fromWooJaeData, setFromWooJaeData, routeMap }) {
 
// console.log('fromWooJaeData :', fromWooJaeData);
  // console.log('routeMap 잘 되는지 :',(fromWooJaeData[index][`day${index+1}`][0].starttime)/(1000*60*60));

  
  // console.log('ㅇㅇㅇㅇㅇㅇ',new Date(`2022-01-01 ${bbcT/60}:${bbcT%60}`));
  // console.log('bbcT ;;', bbcT);
 
  console.log('fromWooJaeData :', fromWooJaeData);
  console.log('bbcTime :', bbcTime);

  let reduxState = useSelector((state) => {
    return state;
  });
  const [sortedList, setSortedList] = useState([]);
  let dispatch = useDispatch();
  const [bbcTime, setBbcTime] = useState({});
  let contentRef = useRef();
  let sourceElement = null;
  let bbcT=(fromWooJaeData[index][`day${index+1}`][0].starttime)/(1000*60);
  const [showTimePicker, setShowTimePicker] = useState(false);
  useEffect(() => {
    console.log('useEffet11111111111111111111')
    if(bbcT >= 13*60){
      console.log('위위위')
      setBbcTime({
        ampm : '오후',
        time : (bbcT/60)-12,
        fullDate:new Date(`2022-01-01 ${bbcT/60}:${bbcT%60}`),
        min : bbcT%60,
        day : index+1
      })
    }
    else{
      console.log('11111')
      setBbcTime({
        ampm : '오전',
        time : bbcT/60,
        fullDate:new Date(`2022-01-01 ${bbcT/60}:${bbcT%60}`),
        min : bbcT%60,
        day : index+1
      })
    }
    dispatch(changeTimeSetObj(bbcTime))
    fromWooJaeData.forEach((val, i) => {
      fromWooJaeData[index]["day" + (index + 1)][0].mtime = 0;
    });
    // console.log("fromWooJaeData[index]", fromWooJaeData[index]);
    if (fromWooJaeData[index] !== undefined) {
      let newArr = [...fromWooJaeData[index]["day" + (index + 1)]];
      setSortedList(newArr);
    }
  }, []);
  if (fromWooJaeData === undefined) return;
  if (fromWooJaeData.length === 0) return;
  if (fromWooJaeData[index] === undefined) return;

  const handleDragStart = (e) => {
    e.target.parentElement.parentElement.parentElement.style.opacity = 0.5;

    sourceElement = e.target;
    // e.dataTransfer.effectAllowed = "move";
    e.dataTransfer.effectAllowed = "move";
    // console.log("sourceElement", sourceElement);
    // console.log("sortedList", sortedList);
    // console.log("e.target", e.target);
  };
  const handleDragOver = (e) => {
    e.preventDefault();
    e.dataTransfer.dropEffect = "move";
  };
  const handleDragEnter = (e) => {
    e.target.parentElement.parentElement.parentElement.classList.add("over");
  };
  const handleDragLeave = (e) => {
    e.target.parentElement.parentElement.parentElement.classList.remove("over");
  };
  const handleDrop = (e, testRef) => {
    e.stopPropagation();
    if (sourceElement.id !== e.target.id) {
      //drag를 당하는 녀석을 제외하고 새로운 배열
      const list = sortedList.filter(
        (val, i) => i.toString() !== sourceElement.id
      );

      //지워지는 detail
      const removed = sortedList.filter(
        (val, i) => val.id === Number(sourceElement.id)
      )[0];
      const dropedIt = sortedList.filter(
        (val, i) => val.id === Number(e.target.id)
      )[0];
      let temp = removed.starttime;
      removed.starttime = dropedIt.starttime;
      dropedIt.starttime = temp;
      // insert removed item after this number.
      let insertAt = Number(e.target.id);
      let tempList = [];
      // 마지막 detailing위에 놓았을 때 id+1안되도록
      if (insertAt >= list.length) {
        tempList = list.slice(0).concat(removed);
        e.target.parentElement.parentElement.parentElement.classList.remove(
          "over"
        );
      }
      //마지막detailing이 아닌 다른녀석 위에 놓았을 때
      else if (insertAt < list.length) {
        //drag 당하는 녀석을 다시 배열에 추가
        tempList = list.slice(0, insertAt).concat(removed);
        //놓아짐 당했던 녀석 이후에 있던녀석들과 다시 합치기
        const newList = tempList.concat(list.slice(insertAt));
        tempList = [...newList];

        e.target.parentElement.parentElement.parentElement.classList.remove(
          "over"
        );
      }
      setSortedList(tempList);
      setFromWooJaeData((current) => {
        let newData = [...current];
        newData[index]["day" + (index + 1)] = tempList;
        return newData;
      });
    }
    //drag당하는 녀석을 다시 자기위치에 놓았을 때
    e.target.parentElement.parentElement.parentElement.classList.remove("over");
  };

  const handleDragEnd = (e) => {
    e.target.parentElement.parentElement.parentElement.style.opacity = 1;
  };

  const handleDelete = (e, testRef) => {
    e.preventDefault();
    const list = sortedList.filter(
      (item, i) => i !== Number(testRef.current.id)
    );
    setSortedList(list);
    setFromWooJaeData((current) => {
      let newData = [...current];
      newData[index]["day" + (index + 1)] = list;
      return newData;
    });
  };

  function getDow(reduxState, i) {
    switch (reduxState.monthNdate[0].dow + i) {
      case 0:
        return "일";

      case 1:
        return "월";

      case 2:
        return "화";

      case 3:
        return "수";

      case 4:
        return "목";

      case 5:
        return "금";

      default:
        return "토";
    }
  }
  return (
    <div className="createPlanLeft__schedule__content" ref={contentRef}>
      {
        // console.log('timeSetObj  ㅇㅇㅇㅇ :',reduxState.timeSetObj),
        console.log('bbcTime  ㅇㅇㅇㅇ :',bbcTime)
      }
      <select
        className="createPlanLeft__schedule__select"
        onChange={(e) => {
          let newWooJaeData = [...fromWooJaeData];
          newWooJaeData.map((val, i) => {
            //바꾸고싶은 날짜를 선택했을 때
            if (index === 0) {
              if (Object.keys(val)[0] === e.target.value) {
                let originDay = Object.keys(newWooJaeData[index])[0]; //day1
                let newOne = newWooJaeData.splice(i, 1); //{day2 : array}
                let newDay = Object.keys(newOne[0])[0]; //day2
                newWooJaeData.splice(index, 0, newOne[0]);
                newWooJaeData.map((val, idx) => {
                  if (Object.keys(val)[0] === newDay) {
                    Object.values(val)[0].forEach((obj) => {
                      obj.day = parseInt(
                        originDay.substring(
                          originDay.indexOf("y") + 1,
                          originDay.indexOf("y") + 2
                        )
                      );
                    });
                  }
                  if (Object.keys(val)[0] === originDay) {
                    Object.values(val)[0].forEach((obj) => {
                      obj.day = parseInt(
                        newDay.substring(
                          newDay.indexOf("y") + 1,
                          newDay.indexOf("y") + 2
                        )
                      );
                    });
                  }
                });
              }
            }
          });
          newWooJaeData.map((val, i) => {
            if (i === 0) {
              Object.values(newWooJaeData[0])[0][0] = Object.values(
                newWooJaeData[0]
              )[0][newWooJaeData.length];
            } else {
              if (i === 1) {
                Object.values(newWooJaeData[i])[0][0] = {
                  ...Object.values(newWooJaeData[0])[0][0],
                };
                Object.values(newWooJaeData[i])[0][0].day = i + 1;
              } else {
                Object.values(newWooJaeData[i])[0][0] = {
                  ...Object.values(newWooJaeData[i - 1])[0][
                    Object.values(newWooJaeData[i - 1])[0].length - 1
                  ],
                };
                Object.values(newWooJaeData[i])[0][0].day = i + 1;
              }
            }
          });

          // console.log("what", what);
          // console.log("newWooJaeData", newWooJaeData);
          let editData = [];
          newWooJaeData.map((val, i) => {
            Object.values(val)[0].map((obj, i) => {
              editData.push(obj);
            });
          });

          console.log("editData", editData);
          let token = sessionStorage.getItem("token");
          axios
            .post(
              "/aamurest/planner/change",
              {
                route: editData,
              },
              {
                headers: {
                  Authorization: `Bearer ${token}`,
                },
              }
            )
            .then((resp) => {
              console.log("resp.data.routeMap[0]", resp.data.routeMap);
              let keys = [];
              Object.keys(resp.data.routeMap).forEach((val) => {
                keys.push(val);
              });
              let values = [];
              Object.values(resp.data.routeMap).forEach((val) => {
                values.push(val);
              });
              let tempArr = [];
              // tempArr.push(resp.data.routeMap);
              values[values.length - 1].pop();
              keys.map((val, idx) => {
                tempArr.push({ [val]: values[idx] });
              });

              setFromWooJaeData(tempArr);
              // console.log("tempArr", tempArr);
            })
            .catch((err) => {
              console.log(err);
            });
        }}
      >
        {reduxState.tripPeriod.map((val, i) => {
          return (
            <option
              key={i}
              value={`day${i + 1}`}
              selected={index + 1 === i + 1 ? true : false}
            >
              {i + 1}DAY {reduxState.monthNdate[0].month}월{" "}
              {reduxState.monthNdate[0].date + i}일 {getDow(reduxState, i)}
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
          style={{ cursor: "pointer" }}
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
              onChange={() => {}}
              onAccept={(newValue) => {
                let newObj = getAmpmTmStart(newValue, index);
                dispatch(corrTimeSetObj(newObj));
                //시작시간 변경 시 fromWooJaeData의 해당날짜 호텔 starttime에 넣어줌
                if (newObj.ampm === "오후" && newObj.time >= 1) {
                  fromWooJaeData[index]["day" + (index + 1)][0].starttime =
                    (newObj.time + 12) * 60 * 60 * 1000 +
                    newObj.min * 60 * 1000;
                } else {
                  fromWooJaeData[index]["day" + (index + 1)][0].starttime =
                    newObj.time * 60 * 60 * 1000 + newObj.min * 60 * 1000;
                }
              }}
            />
          </Stack>
        </LocalizationProvider>
      ) : null}
      {sortedList.length !== 0 &&
        // fromWooJaeData[index]["day" + (index + 1)].map((obj, i) => {
        sortedList.map((obj, i) => {
          return (
            <DetailSetting
              obj={obj}
              key={i}
              i={i}
              periodIndex={index}
              fromWooJaeData={fromWooJaeData}
              handleDragStart={handleDragStart}
              handleDragOver={handleDragOver}
              handleDragEnter={handleDragEnter}
              handleDragLeave={handleDragLeave}
              handleDrop={handleDrop}
              handleDragEnd={handleDragEnd}
              handleDelete={handleDelete}
            />
          );
        })}
    </div>
  );
}

function DetailSetting({
  obj,
  i,
  fromWooJaeData,
  periodIndex,
  handleDragStart,
  handleDragOver,
  handleDragEnter,
  handleDragLeave,
  handleDrop,
  handleDragEnd,
  handleDelete,
}) {
  let reduxState = useSelector((state) => {
    return state;
  });
  let dispatch = useDispatch();
  const [upTime, setUpTime] = useState(0);
  const [downTime, setDownTime] = useState(0);
  const [memoBadge, setMemoBadge] = useState(false);
  const [showAdjustMTime, setShowAdjustMTime] = useState(false);
  let memoRef = useRef();
  let textAreaRef = useRef();
  let mTimeRef = useRef();
  let testRef = useRef();
  useEffect(() => {
    if (i === 0) {
      let firstAccum = getNAccumDetailTime(periodIndex, reduxState, obj);
      setUpTime(firstAccum);
      setDownTime(firstAccum + obj.atime / 1000 / 60);
      fromWooJaeData[periodIndex]["day" + (periodIndex + 1)][i + 1].starttime =
        firstAccum + obj.atime / 1000 / 60;
      const forBlackBoxRedux = getTimes(
        periodIndex,
        firstAccum,
        firstAccum + obj.atime / 1000 / 60
      );
      dispatch(addWholeBlackBox(forBlackBoxRedux));
    }
    if (i !== 0) {
      setUpTime(
        fromWooJaeData[periodIndex]["day" + (periodIndex + 1)][i].starttime +
          obj.mtime / 1000 / 60
      );
      setDownTime(
        fromWooJaeData[periodIndex]["day" + (periodIndex + 1)][i].starttime +
          obj.mtime / 1000 / 60 +
          fromWooJaeData[periodIndex]["day" + (periodIndex + 1)][i].atime /
            1000 /
            60
      );
      let forBlackBoxRedux = getTimes(
        periodIndex,
        fromWooJaeData[periodIndex]["day" + (periodIndex + 1)][i].starttime +
          obj.mtime / 1000 / 60,
        fromWooJaeData[periodIndex]["day" + (periodIndex + 1)][i].starttime +
          obj.mtime / 1000 / 60 +
          fromWooJaeData[periodIndex]["day" + (periodIndex + 1)][i].atime /
            1000 /
            60
      );
      dispatch(addWholeBlackBox(forBlackBoxRedux));
      if (
        i !==
        fromWooJaeData[periodIndex]["day" + (periodIndex + 1)].length - 1
      ) {
        fromWooJaeData[periodIndex]["day" + (periodIndex + 1)][
          i + 1
        ].starttime =
          fromWooJaeData[periodIndex]["day" + (periodIndex + 1)][i].starttime +
          obj.mtime / 1000 / 60 +
          fromWooJaeData[periodIndex]["day" + (periodIndex + 1)][i].atime /
            1000 /
            60;
      }
    }
  }, []);
  function getTimes(periodIndex, st, et) {
    return {
      day: periodIndex + 1,
      stime: Math.floor(st / 60)
        .toString()
        .padStart(2, "0"),
      smin: Math.floor(st % 60)
        .toString()
        .padStart(2, "0"),
      etime: Math.floor(et / 60)
        .toString()
        .padStart(2, "0"),
      emin: Math.floor(et % 60)
        .toString()
        .padStart(2, "0"),
    };
  }

  if (fromWooJaeData === undefined)
    // if (obj.dto === null) return;
    return;
  if (obj === undefined) return;

  return (
    <div className="detailSetting__container" id={i}>
      <div className="movingTime">
        <FontAwesomeIcon icon={faEllipsisVertical} />
        <div className="movingTime__time">
          {/* 이동시간 받아서 뿌려주는 input */}
          <input
            type="number"
            style={{ width: "37px" }}
            defaultValue={Math.round(obj.mtime / 1000 / 60)}
            ref={mTimeRef}
            onChange={(e) => {
              obj.mtime = e.target.value * 1000 * 60;
            }}
          />
          <span>분</span>
        </div>
      </div>
      <div className="detailLocation">
        <div className="detailLocation__img-container">
          <img
            className="detailLocation__img"
            src={obj.dto.smallimage ?? "/images/no-image.jpg"}
            alt="이미지"
            onError={(e) => {
              e.target.src = "/images/no-image.jpg";
            }}
            ref={testRef}
            id={obj.id}
            draggable="true"
            onDragStart={handleDragStart}
            onDragOver={handleDragOver}
            onDragEnter={handleDragEnter}
            onDragLeave={handleDragLeave}
            onDrop={(e) => {
              handleDrop(e, testRef);
            }}
            onDragEnd={handleDragEnd}
          />
        </div>
        <div className="detailLocation__inform-container">
          <div className="detailLocation__part-top">
            <div
              style={{
                display: "flex",
                flexDirection: "column",
                gap: "5px",
              }}
            >
              <div style={{ fontWeight: "bold", fontSize: "13px" }}>
                {obj.dto.title}
              </div>
              <div
                className="detailLocation__aTime"
                style={{ fontSize: "14px" }}
              >
                <span style={{ color: "var(--orange)" }}>
                  {Math.floor(obj.atime / 1000 / 60 / 60)}
                </span>{" "}
                시간{" "}
                <span style={{ color: "var(--orange)" }}>
                  {Math.floor((obj.atime / 1000 / 60) % 60)}
                </span>{" "}
                분{" "}
                <FontAwesomeIcon
                  icon={faClockRotateLeft}
                  style={{
                    color: "rgba(0,0,0,0.7)",
                    fontSize: "11px",
                    cursor: "pointer",
                  }}
                  onClick={() => {
                    setShowAdjustMTime(true);
                  }}
                />
              </div>
            </div>

            <div className="detailLocation__clock">
              <span>
                {Math.floor(upTime / 60)
                  .toString()
                  .padStart(2, "0")}
                :
                {Math.floor(upTime % 60)
                  .toString()
                  .padStart(2, "0")}
              </span>
              <span>~</span>
              <span>
                {Math.floor(downTime / 60)
                  .toString()
                  .padStart(2, "0")}
                :
                {Math.floor(downTime % 60)
                  .toString()
                  .padStart(2, "0")}
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
              className="memoBtn"
              onClick={() => {
                memoRef.current.classList.add("memo_visible");
              }}
            >
              메모
              {textAreaRef.current !== undefined ? (
                textAreaRef.current.value.length !== 0 ? (
                  <span className="memo__badge">.</span>
                ) : null
              ) : null}
            </span>
            <span
              onClick={(e) => {
                if (window.confirm("정말 삭제하시겠습니까?")) {
                  handleDelete(e, testRef);
                  // Object.values(fromWooJaeData[periodIndex])[0].splice(i, 1);
                }
              }}
            >
              삭제
            </span>
          </div>
        </div>
        <MemoArea
          memoRef={memoRef}
          textAreaRef={textAreaRef}
          memoBadge={memoBadge}
          setMemoBadge={setMemoBadge}
          fromWooJaeData={fromWooJaeData}
          periodIndex={periodIndex}
          index={i}
        />
        {showAdjustMTime && (
          <AdjustMTime setShowAdjustMTime={setShowAdjustMTime} obj={obj} />
        )}
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

function MemoArea({
  memoRef,
  textAreaRef,
  memoBadge,
  setMemoBadge,
  fromWooJaeData,
  periodIndex,
  index,
}) {
  if (fromWooJaeData === undefined) return;
  if (Object.values(fromWooJaeData[periodIndex])[0][index] === undefined)
    return;

  return (
    <div className="memoArea" ref={memoRef}>
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
            >
              {Object.values(fromWooJaeData[periodIndex])[0][index].comment}
            </textarea>
          </div>
        </div>
        <div className="memoArea__btn">
          <span
            onClick={(e) => {
              Object.values(fromWooJaeData[periodIndex])[0][index].comment =
                textAreaRef.current.value;
              memoRef.current.classList.remove("memo_visible");
              setMemoBadge(!memoBadge);
            }}
          >
            완료
          </span>
        </div>
      </div>
    </div>
  );
}

function AdjustMTime({ setShowAdjustMTime, obj }) {
  let timeRef = useRef();
  let minRef = useRef();
  return (
    <div className="adjustMTime">
      <div className="adjustMTime__container">
        <div className="adjustMTime__content">
          <h5>머무는 시간 설정</h5>
          <div className="adjustMTime__input-container">
            <input
              ref={timeRef}
              type="number"
              min={0}
              max={23}
              defaultValue={Math.floor(obj.atime / 1000 / 60 / 60)}
              onChange={(e) => {
                obj.atime =
                  e.target.value * 1000 * 60 * 60 +
                  minRef.current.value * 1000 * 60;
              }}
            />
            <span>시간</span>
            <input
              ref={minRef}
              type="number"
              min={0}
              max={59}
              defaultValue={Math.floor((obj.atime / 1000 / 60) % 60)}
              onChange={(e) => {
                obj.atime =
                  e.target.value * 1000 * 60 +
                  timeRef.current.value * 1000 * 60 * 60;
              }}
            />
            <span>분</span>
          </div>
        </div>
        <div className="adjustMTime__btn">
          <span
            onClick={() => {
              setShowAdjustMTime(false);
            }}
          >
            완료
          </span>
        </div>
      </div>
    </div>
  );
}

function getNAccumDetailTime(periodIndex, reduxState, obj) {
  let sumTime;
  let sTime = reduxState.timeSetObj.find((val) => {
    return val.day === periodIndex + 1;
  });
  if (sTime.ampm === "오후" && sTime.time >= 1 && sTime.time <= 11) {
    sumTime = (sTime.time + 12) * 60 + sTime.min + obj.mtime / 1000 / 60;
  } else {
    sumTime = sTime.time * 60 + sTime.min + obj.mtime / 1000 / 60;
  }
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
  let time = parseInt(
    localeString.substring(
      localeString.indexOf(":") - 2,
      localeString.indexOf(":")
    )
  );

  array.push(time);
  //분 가져오는 코드
  let min = parseInt(
    localeString.substring(
      localeString.indexOf(":") + 1,
      localeString.lastIndexOf(":")
    )
  );

  return {
    day: index + 1,
    fullDate: newValue,
    ampm: ampm,
    time: time,
    min: min,
  };
}

export default MyCreatePlanLeft;
