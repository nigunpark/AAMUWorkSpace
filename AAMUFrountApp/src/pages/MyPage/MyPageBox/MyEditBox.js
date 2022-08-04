import axios from "axios";
import React, { useCallback, useEffect, useRef, useState } from "react";
import styled, { keyframes } from "styled-components";
import { addWholeBlackBox } from "../../../redux/store";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCircleXmark } from "@fortawesome/free-solid-svg-icons";

const MyEditBox = ({ selectRbn }) => {
  let navigate = useNavigate();

  const [detailPostData, setDetailPostData] = useState({});
  const [detailTitle, setDetailTitle] = useState("");
  const [detailRoute, setDetailRoute] = useState([]);
  const [plannerDate, setPlannerDate] = useState({});
  const [title, setTitle] = useState("");
  let titleRef = useRef();
  const [content, setContent] = useState("");
  let contentRef = useRef();
  const [rbn, setDetailRbn] = useState(0);
  useEffect(() => {
    setDetailRbn(selectRbn);
  }, []);

  //--------------------------------이미지 시작--------------------------------
  const [showImages, setShowImages] = useState([]);
  const [showImagesFile, setShowImagesFile] = useState([]);

  //이미지 등록
  const handleAddImages = (e) => {
    const imageLists = e.target.files;
    let imageUrlLists = [...showImages];
    let imgs = [...showImagesFile];

    for (let i = 0; i < imageLists.length; i++) {
      const currentImageUrl = URL.createObjectURL(imageLists[i]); //이거 안됨..
      imageUrlLists.push(currentImageUrl);

      imgs.push(imageLists[i]); // 한개씩 첨부했을때 post로 잘 넘어가게
    }

    if (imageUrlLists.length > 6) {
      //사진 6개 제한
      imageUrlLists = imageUrlLists.slice(0, 6);
    }

    setShowImages(imageUrlLists);
    setShowImagesFile(imgs);
  };

  const handleDeleteImage = (id) => {
    //등록한 사진 삭제
    setShowImages(showImages.filter((_, index) => index !== id));
  };
  //--------------------------------이미지 끝--------------------------------

  async function getSelectOne() {
    let token = sessionStorage.getItem("token");
    await axios
      .get(`/aamurest/bbs/SelectOne/${selectRbn}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        console.log("myEditBox", resp.data);
        setTitle(resp.data.title);
        setContent(resp.data.content);
        setPostTheme(resp.data.themename); // 테마 가져온거 기본 세팅하기
        setShowImages(resp.data.photo); // 업로드했던 사진 세팅하기
        setDetailRoute(resp.data.routeList);
        let month = resp.data.planner.plannerdate.substring(
          resp.data.planner.plannerdate.indexOf("월") - 1,
          resp.data.planner.plannerdate.indexOf("월")
        );
        let date = resp.data.planner.plannerdate.substring(
          resp.data.planner.plannerdate.indexOf("일") - 1,
          resp.data.planner.plannerdate.indexOf("일")
        );
        let dow = resp.data.planner.plannerdate.substring(
          resp.data.planner.plannerdate.indexOf("~") - 2,
          resp.data.planner.plannerdate.indexOf("~") - 1
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
      })
      .catch((error) => {
        console.log((error) => console.log("수정할 데이터 불러오기 실패", error));
      });
  }

  useEffect(() => {
    getSelectOne();
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

  const themes = [
    {
      themeId: 1,
      themeName: "봄",
    },
    {
      themeId: 2,
      themeName: "여름",
    },
    {
      themeId: 3,
      themeName: "가을",
    },
    {
      themeId: 4,
      themeName: "겨울",
    },
    {
      themeId: 5,
      themeName: "산/트레킹",
    },
    {
      themeId: 6,
      themeName: "바다/해수욕장",
    },
    {
      themeId: 7,
      themeName: "호캉스",
    },
    {
      themeId: 8,
      themeName: "자전거",
    },
    {
      themeId: 9,
      themeName: "섬",
    },
    {
      themeId: 10,
      themeName: "맛집",
    },
    {
      themeId: 11,
      themeName: "힐링",
    },
    {
      themeId: 12,
      themeName: "드라이브",
    },
  ];

  const canSubmit = useCallback(() => {
    return content !== "" && title !== "" && postThemeNum !== 0;
  }, [title, content, postThemeNum]); //제목, 내용, 테마 입력 안되면 공유버튼 비활성화
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
  return (
    <div className="MyWrite-container">
      <div>
        <Plan>
          <div style={{ display: "flex", flexDirection: "column", gap: "10px" }}>
            {routeData.map((route, idx) => {
              return (
                <div key={idx} className="detail-plan">
                  <div className="paln-date">
                    {idx + 1}일차 ({plannerDate.month}월 {plannerDate.date + idx}일&nbsp;
                    {getDow(plannerDate.dow)})
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
      </div>

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
              value={title}
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
        <div style={{ display: "flex", flexDirection: "column", height: "100%", gap: "1rem" }}>
          <div className="write-box add-delete">
            {showImages.length == 0 ? (
              <img style={{ width: "400px" }} src="/images/no-image.jpg" />
            ) : (
              showImages.map((image, id) => (
                <>
                  <div className="previewImage">
                    <FontAwesomeIcon icon={faCircleXmark} className="test-i" />
                    <img src={image} alt={`${image}-${id}`} />
                  </div>
                  <div className="previewImage">
                    <FontAwesomeIcon icon={faCircleXmark} className="test-i" />
                    <img src={image} alt={`${image}-${id}`} />
                  </div>
                  <div className="previewImage">
                    <FontAwesomeIcon icon={faCircleXmark} className="test-i" />
                    <img src={image} alt={`${image}-${id}`} />
                  </div>
                  {/* <div className="previewImage">
                    <FontAwesomeIcon icon={faCircleXmark} className="test-i" />
                    <img src={image} alt={`${image}-${id}`} />
                  </div>
                  <div className="previewImage">
                    <FontAwesomeIcon icon={faCircleXmark} className="test-i" />
                    <img src={image} alt={`${image}-${id}`} />
                  </div>
                  <div className="previewImage">
                    <FontAwesomeIcon icon={faCircleXmark} className="test-i" />
                    <img src={image} alt={`${image}-${id}`} />
                  </div> */}
                </>
              ))
            )}
          </div>

          <div className="write-box writer">
            <textarea
              onChange={(e) => {
                setContent(e.target.value);
              }}
              name="content"
              className="write-section"
              placeholder="글 쓰기"
              value={content}
              ref={contentRef}
            />
            <div className="box-gab"></div>

            {/* 테마 */}
            <button type="button" className="theme-section" onClick={onClickModal}>
              {postTheme == 0 ? "테마를 선택하세요" : postTheme}
            </button>

            {isOpen == true ? (
              <Theme
                setIsOpen={setIsOpen}
                themes={themes}
                setPostTheme={setPostTheme}
                setPostThemeNum={setPostThemeNum}
              />
            ) : null}
          </div>

          <div className="write-box" style={{ textAlign: "end" }}>
            <button
              style={{ color: "black" }}
              className="navbar-btn"
              type="button"
              onClick={() => {
                // let write = uploadFile(showImagesFile);
                bordWrite(
                  // write,
                  title,
                  content,
                  rbn,
                  navigate,
                  postThemeNum
                );
              }}
            >
              수정하기
            </button>
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
  grid-row: 1 / 4;
  &::-webkit-scrollbar {
    display: none;
  }
  animation: ${bounce_modal_plan} 1.4s cubic-bezier(0.175, 0.885, 0.32, 1.275) both;
  animation-delay: 0.2s;
`;
function Theme({ setIsOpen, themes, setPostTheme, setPostThemeNum }) {
  return (
    <div className="theme-modal">
      <div className="theme-modal-overlay">
        {themes.map((val, idx) => {
          return (
            <div
              className="theme-modal-select"
              value={val.themeId}
              onClick={(e) => {
                setPostThemeNum(val.themeId);
                setPostTheme(val.themeName);
                setIsOpen(false);
              }}
            >
              {val.themeName}
            </div>
          );
        })}
      </div>
    </div>
  );
}

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
function bordWrite(title, content, rbn, navigate) {
  // console.log("titleRef :", titleRef.current.value);
  // console.log("contentRef :", contentRef.current.value);
  let token = sessionStorage.getItem("token");

  axios
    .put(
      `/aamurest/bbs/edit`,
      {
        rbn: rbn,
        title: title,
        content: content,
      },
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )
    .then((resp) => {
      console.log(resp.data);
      if (resp.data.result === "updateSuccess") {
        alert("글이 저장되었습니다");
        navigate("/forum");
      } else {
        alert("저장오류가 발생했습니다. 관리자에게 문의하세요");
        navigate("/");
      }
    })
    .catch((error) => {
      console.log((error) => console.log("수정 실패", error));
    });
}

const Imgs = styled.img`
  width: 100%;
  height: 100%;
  overflow: hidden;
  object-fit: contain;
  position: relative;
`;

export default MyEditBox;
