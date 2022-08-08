import axios from "axios";
import React, { useCallback, useEffect, useRef, useState } from "react";
import styled, { keyframes } from "styled-components";
import { addWholeBlackBox } from "../../../redux/store";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { faRectangleXmark } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
const MyPostBox = ({ selectRbn, setClickTab }) => {
  let navigate = useNavigate();
  const [detailRoute, setDetailRoute] = useState([]);
  const [plannerDate, setPlannerDate] = useState({});
  const [title, setTitle] = useState("");
  const [plannerTitle, setPlannerTitle] = useState("");
  const [themes, setThemes] = useState([]);
  let titleRef = useRef();
  let selectRef = useRef();
  let contentRef = useRef();
  const [rbn, setDetailRbn] = useState(0);
  useEffect(() => {
    setDetailRbn(selectRbn);
    getThemes();
  }, []);

  //--------------------------------이미지 시작--------------------------------
  const [showImages, setShowImages] = useState([]);
  const [showImagesFile, setShowImagesFile] = useState([]); //전송할이미지들
  //--------------------------------이미지 끝--------------------------------

  const getPlanData = async () => {
    try {
      let token = sessionStorage.getItem("token");

      let resp = await axios.get("aamurest/planner/selectone", {
        params: {
          rbn: selectRbn,
        },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      console.log("MyPostBox : ", resp.data);
      setPlannerTitle(resp.data.title);
      // setTitle(resp.data.title);
      // setContent(resp.data.content);
      setPostTheme(resp.data.themename); // 테마 가져온거 기본 세팅하기
      // setShowImages(resp.data.photo); // 업로드했던 사진 세팅하기
      setDetailRoute(resp.data.route);
      let month = resp.data.plannerdate.substring(
        resp.data.plannerdate.indexOf("월") - 1,
        resp.data.plannerdate.indexOf("월")
      );
      let date = resp.data.plannerdate.substring(
        resp.data.plannerdate.indexOf("일") - 2,
        resp.data.plannerdate.indexOf("일")
      );
      let dow = resp.data.plannerdate.substring(
        resp.data.plannerdate.indexOf("~") - 2,
        resp.data.plannerdate.indexOf("~") - 1
      );
      switch (dow) {
        case "일":
          dow = 0;
          break;
        case "월":
          dow = 1;
          break;
        case "화":
          dow = 2;
          break;
        case "수":
          dow = 3;
          break;
        case "목":
          dow = 4;
          break;
        case "금":
          dow = 5;
          break;
        default:
          dow = 6;
      }
      setPlannerDate({ month, date, dow });
    } catch (error) {
      console.log("error", error);
    }
  };
  function uploadFile() {
    //이미지 업로드
    let formData = new FormData(); // formData 객체를 생성한다.
    for (let i = 0; i < showImagesFile.length; i++) {
      formData.append("multifiles", showImagesFile[i]); // 반복문을 활용하여 파일들을 formData 객체에 추가한다
    }
    return formData;
  }
  useEffect(() => {
    // getSelectOne();
    getPlanData();
  }, []);

  const dRoute = detailRoute.reduce((acc, obj) => {
    const { day } = obj;
    acc[day] = acc[day] ?? [];
    acc[day].push(obj);
    return acc;
  }, {});

  let keys = Object.keys(dRoute);
  let values = Object.values(dRoute);
  let routeData = Object.entries(dRoute).map((val, idx) => {
    return { ["day" + keys[idx]]: values[idx] };
  });

  const [isOpen, setIsOpen] = useState(false);
  const onClickModal = () => {
    setIsOpen(true);
  };

  const [postTheme, setPostTheme] = useState("");
  const [postThemeNum, setPostThemeNum] = useState(0);

  const getDow = (dow) => {
    switch (dow) {
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
  };
  const addImages = (e) => {
    const nowSelectImageList = e.target.files;
    const nowImageURLList = [...showImages];
    for (let i = 0; i < nowSelectImageList.length; i++) {
      nowImageURLList.push(URL.createObjectURL(nowSelectImageList[i]));
    }

    if (nowImageURLList.length > 5) {
      alert("사진은 5장 이하로만 가능합니다");
      nowImageURLList = nowImageURLList.slice(0, 5);
    }
    setShowImages(nowImageURLList); //뿌려줄 사진배열
    setShowImagesFile(nowSelectImageList); //전송할 사진파일들이 담김
  };
  const delPhoto = (image) => {
    setShowImages((curr) => {
      return curr.filter((val) => {
        return val !== image;
      });
    });
  };

  const getThemes = async () => {
    let token = sessionStorage.getItem("token");
    await axios
      .get("/aamurest/users/theme", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        let tempArr = [];
        resp.data.forEach((val) => {
          tempArr.push(val.THEMENAME);
        });
        setThemes(tempArr);
      })
      .catch((err) => {
        console.log("err", err);
      });
  };
  return (
    <div className="MyWrite-container">
      <Plan>
        <div className="detail_planner_title">{plannerTitle}</div>
        <div style={{ display: "flex", flexDirection: "column", gap: "10px" }}>
          {routeData.map((route, idx) => {
            return (
              <div key={idx} className="detail-plan">
                <div className="paln-date">
                  {idx + 1}일차 ({plannerDate.month}월 {parseInt(plannerDate.date) + idx}일&nbsp;
                  {getDow(plannerDate.dow + idx)})
                </div>
                <div className="plan__over-container">
                  {route[`day${idx + 1}`].map((obj, i) => {
                    return (
                      <div className="plan__container">
                        <div className="plan-region">
                          <div className="myPlan-container">
                            <div className="myPlan-img-container">
                              <img
                                onError={(e) => {
                                  e.target.src = "/images/no-image.jpg";
                                }}
                                src={
                                  obj.dto.smallimage == null
                                    ? "/images/no-image.jpg"
                                    : obj.dto.smallimage
                                }
                              />
                            </div>
                            <div
                              style={{
                                display: "flex",
                                flexDirection: "column",
                                alignItems: "start",
                                padding: "5px 0",
                                gap: "5px",
                              }}
                            >
                              <span
                                style={{
                                  fontSize: "15px",
                                  fontWeight: "bold",
                                }}
                              >
                                {obj.dto.title}
                              </span>
                              <div className="plan-clock">
                                <DetailSetting
                                  fromWooJaeData={routeData}
                                  obj={obj}
                                  key={i}
                                  i={i}
                                  periodIndex={idx}
                                />
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    ); //MyPost_clock
                  })}
                </div>
              </div>
            );
          })}
        </div>
      </Plan>

      <div className="editBox__rightSide">
        <div>
          <div className="write-box-title">
            <input
              onChange={(e) => {
                setTitle(e.target.value);
              }}
              name="title"
              type="text"
              className="wirte-title"
              placeholder="제목을 입력하세요"
              ref={titleRef}
            />
          </div>

          <div className="write-box">
            {/* <input
          multiple
          className="write-picture-input"
          type="file"
          id="upload"
          onChange={handleAddImages}
          onClick={(e) => (e.target.value = null)}
        />
        <label className="write-picture-label" for="upload">
          Img Upload
        </label> */}
          </div>

          {/* <div className="write-box">
        <Imgs src='/images/imageMap.png'/>
      </div> */}
        </div>
        <div style={{ display: "flex", justifyContent: "flex-end", margin: "7px 0" }}>
          <form encType="multipart/form-data">
            <input
              id="myEditBox_imgInput"
              type="file"
              multiple
              accept=".jpg, .jpeg, .png"
              style={{ display: "none" }}
              onChange={(e) => {
                e.stopPropagation();
                addImages(e);
              }}
            />
          </form>
          <label htmlFor="myEditBox_imgInput">
            <span className="myEditBox_btn">이미지 업로드</span>
          </label>
        </div>
        <div className="add-delete-container">
          <div className="write-box add-delete">
            {showImages.map((image, id) => (
              <>
                <div className="previewImage" key={id}>
                  <FontAwesomeIcon
                    icon={faRectangleXmark}
                    className="test-i"
                    onClick={() => delPhoto(image)}
                  />
                  <img src={image} alt={`${image}-${id}`} />
                </div>
              </>
            ))}
          </div>

          <div className="write-box writer">
            <textarea
              name="content"
              className="write-section"
              placeholder="내용을 입력하세요"
              ref={contentRef}
            />
          </div>

          <div
            className=""
            style={{ display: "flex", justifyContent: "space-between", gap: "1rem" }}
          >
            <div className="box-gab">
              {/* 테마 */}
              <span className="theme-section" onClick={onClickModal}>
                {/* theme : {postTheme == 0 ? "테마를 선택하세요" : postTheme} */}
                theme :
                <select ref={selectRef} style={{ fontWeight: "bold" }}>
                  {themes.map((val) => {
                    return <option>{val}</option>;
                  })}
                </select>
              </span>
            </div>
            <span
              className="myEditBox_btn"
              onClick={() => {
                let write = uploadFile(showImagesFile);
                bordWrite(write, titleRef, contentRef, rbn, navigate, selectRef);
              }}
            >
              공유하기
            </span>
          </div>
        </div>
      </div>
    </div>
  );
};

const bounce_modal_plan = keyframes`
0% {
  transform: rotateX(70deg);
  transform-origin: top;
  opacity: 0;
}
100% {
  transform: rotateX(0deg);
  transform-origin: top;
  opacity: 1;
}
`;
const Plan = styled.div`
  position: relative;
  // background: white;
  // padding: 0 5px;
  // display: flex;
  // flex-direction: column;
  overflow: auto;
  width: 100%;
  grid-row: 1 / 4;
  &::-webkit-scrollbar {
    display: none;
  }
  animation: ${bounce_modal_plan} 1.4s cubic-bezier(0.175, 0.885, 0.32, 1.275) both;
  animation-delay: 0.2s;
`;

function DetailSetting({ fromWooJaeData, periodIndex, obj, i }) {
  let reduxState = useSelector((state) => {
    return state;
  });

  const [upTime, setUpTime] = useState(0);
  const [downTime, setDownTime] = useState(0);
  let dispatch = useDispatch();

  useEffect(() => {
    if (i !== 0) {
      setUpTime(
        fromWooJaeData[periodIndex]["day" + (periodIndex + 1)][i].starttime + obj.mtime / 1000 / 60
      );
      setDownTime(
        fromWooJaeData[periodIndex]["day" + (periodIndex + 1)][i].starttime +
          obj.mtime / 1000 / 60 +
          fromWooJaeData[periodIndex]["day" + (periodIndex + 1)][i].atime / 1000 / 60
      );
      let forBlackBoxRedux = getTimes(
        periodIndex,
        fromWooJaeData[periodIndex]["day" + (periodIndex + 1)][i].starttime + obj.mtime / 1000 / 60,
        fromWooJaeData[periodIndex]["day" + (periodIndex + 1)][i].starttime +
          obj.mtime / 1000 / 60 +
          fromWooJaeData[periodIndex]["day" + (periodIndex + 1)][i].atime / 1000 / 60
      );
      dispatch(addWholeBlackBox(forBlackBoxRedux));
      if (i !== fromWooJaeData[periodIndex]["day" + (periodIndex + 1)].length - 1) {
        fromWooJaeData[periodIndex]["day" + (periodIndex + 1)][i + 1].starttime =
          fromWooJaeData[periodIndex]["day" + (periodIndex + 1)][i].starttime +
          obj.mtime / 1000 / 60 +
          fromWooJaeData[periodIndex]["day" + (periodIndex + 1)][i].atime / 1000 / 60;
      }
    }
  }, []);

  return (
    <div className="MyPost_clock">
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
  );
}

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
function bordWrite(write, titleRef, contentRef, rbn, navigate, selectRef) {
  write.append("rbn", rbn);
  write.append("title", titleRef.current.value);
  write.append("content", contentRef.current.value);
  write.append("themeid", selectRef.current.value);
  write.append("id", sessionStorage.getItem("username"));
  let token = sessionStorage.getItem("token");

  axios
    .post(`/aamurest/bbs/edit`, write, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
    .then((resp) => {
      console.log(resp.data);
      if (resp.data.result === "insertSuccess") {
        alert("글이 저장되었습니다");
        navigate("/forum");
      } else {
        alert("저장오류가 발생했습니다. 관리자에게 문의하세요");
        navigate(-1);
      }
    })
    .catch((error) => {
      console.log((error) => console.log("공유 실패", error));
    });
}

const Imgs = styled.img`
  width: 100%;
  height: 100%;
  overflow: hidden;
  object-fit: contain;
  position: relative;
`;

export default MyPostBox;
