import axios from "axios";
import React, { useCallback, useEffect, useState } from "react";
import styled from "styled-components";
import { addWholeBlackBox } from "../../../redux/store";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

const MyPostBox = ({ selectRbn }) => {
  let navigate = useNavigate();

  const [detailPostData, setDetailPostData] = useState({});
  const [detailTitle, setDetailTitle] = useState("");
  const [detailRoute, setDetailRoute] = useState([]);

  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [tag, setTag] = useState();
  const [detailRbn, setDetailRbn] = useState(0);
  const themeid = 1;
  let [writeTag, setWriteTag] = useState([]);

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

  // useEffect(async () => {
  //   try {
  //     let token = sessionStorage.getItem("token");

  //     let resp = await axios.get("/aamurest/planner/selectonemap", {
  //       params: {
  //         rbn: selectRbn,
  //       },
  //       headers: {
  //         Authorization: `Bearer ${token}`,
  //       },
  //     });
  //     console.log("글 작성 페이지에서 상세경로 데이터 확인 : ", resp.data);
  //     setDetailPostData(resp.data);
  //     setDetailRbn(resp.data.rbn);
  //     setDetailTitle(resp.data.title);

  //     let keys = Object.keys(resp.data.routeMap);
  //     let values = Object.values(resp.data.routeMap);
  //     let keyValueData = Object.entries(resp.data.routeMap).map((val, idx) => {
  //       return { [keys[idx]]: values[idx] };
  //     });
  //     setDetailRoute(keyValueData);
  //   } catch (error) {
  //     console.log((error) => console.log("상세경로 가져오기 실패", error));
  //   }
  // }, []);

  const getPlanData = async () => {
    try {
      let token = sessionStorage.getItem("token");

      let resp = await axios.get("/aamurest/planner/selectonemap", {
        params: {
          rbn: selectRbn,
        },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      console.log("글 작성 페이지에서 상세경로 데이터 확인 : ", resp.data);
      setDetailPostData(resp.data);
      setDetailRbn(resp.data.rbn);
      setDetailTitle(resp.data.title);

      let keys = Object.keys(resp.data.routeMap);
      let values = Object.values(resp.data.routeMap);
      let keyValueData = Object.entries(resp.data.routeMap).map((val, idx) => {
        return { [keys[idx]]: values[idx] };
      });
      setDetailRoute(keyValueData);
    } catch (error) {
      console.log((error) => console.log("상세경로 가져오기 실패", error));
    }
  };

  useEffect(() => {
    getPlanData();
  }, []);

  // console.log("detailPostData :", detailPostData);
  // console.log("detailRoute :", detailRoute);

  const [isOpen, setIsOpen] = useState(false);
  const onClickModal = () => {
    setIsOpen(true);
  };

  const [postTheme, setPostTheme] = useState("");
  const [postThemeNum, setPostThemeNum] = useState(0);

  const themes = [
    {
      themeId: 1,
      themeName: "ㅇㅅㅇ",
    },
    {
      themeId: 15,
      themeName: "봄",
    },
    {
      themeId: 16,
      themeName: "여름",
    },
    {
      themeId: 17,
      themeName: "가을",
    },
    {
      themeId: 18,
      themeName: "겨울",
    },
    {
      themeId: 19,
      themeName: "산/트레킹",
    },
    {
      themeId: 20,
      themeName: "바다/해수욕장",
    },
    {
      themeId: 21,
      themeName: "호캉스",
    },
    {
      themeId: 22,
      themeName: "자전거",
    },
    {
      themeId: 23,
      themeName: "섬",
    },
    {
      themeId: 24,
      themeName: "맛집",
    },
    {
      themeId: 25,
      themeName: "힐링",
    },
    {
      themeId: 26,
      themeName: "드라이브",
    },
  ];

  const canSubmit = useCallback(() => {
    return content !== "" && title !== "" && postThemeNum !== 0;
  }, [title, content, postThemeNum]); //제목, 내용, 테마 입력 안되면 공유버튼 비활성화

  return (
    <div className="MyWrite-container">
      <div className="write-box plan">
        <div className="plan-title">{detailTitle} 여행경로</div>

        <div>
          {detailRoute.map((route, idx) => {
            // console.log("route 외부 map:", route);

            return (
              <div key={idx} className="detail-plan">
                <span className="paln-date">{idx + 1} 일차</span>
                {
                  route[`day${idx + 1}`].map((obj, i) => {
                    // console.log("내부 map obj:", obj);

                    return (
                      <div style={{ display: "flex" }}>
                        <div className="plan-region">
                          <div className="myPlan-container">
                            <div style={{ borderRadius: "5px" }}>
                              <img
                                style={{
                                  width: "100%",
                                  height: "80px",
                                  objectFit: "cover",
                                  border: "1px #edf2f4 solid",
                                  paddingRight: "2px",
                                  borderRadius: "5px",
                                }}
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
                            <div style={{ marginLeft: "1px" }}>
                              <div
                                style={{ fontSize: "15px", marginTop: "10px" }}
                              >
                                {obj.dto.title}
                              </div>
                            </div>
                          </div>
                        </div>
                        <div className="plan-clock">
                          <DetailSetting
                            fromWooJaeData={detailRoute}
                            obj={obj}
                            key={i}
                            i={i}
                            periodIndex={idx}
                          />
                        </div>
                      </div>
                    ); //MyPost_clock
                  }) //내부 map
                }
              </div>
            );
          })}
        </div>
      </div>

      <div className="write-box">
        <input
          onChange={(e) => {
            setTitle(e.target.value);
          }}
          name="title"
          type="text"
          className="wirte-title"
          placeholder="제목을 입력하세요"
          value={title}
        />
      </div>

      <div className="write-box">
        <input
          multiple
          className="write-picture-input"
          type="file"
          id="upload"
          onChange={handleAddImages}
          onClick={(e) => (e.target.value = null)}
        />
        <label className="write-picture-label" for="upload">
          Img Upload
        </label>
      </div>

      {/* <div className="write-box">
        <Imgs src='/images/imageMap.png'/>
      </div> */}
      <div className="write-box add-delete">
        {showImages.length == 0 ? (
          <img style={{ width: "400px" }} src="/images/no-image.jpg" />
        ) : (
          showImages.map((image, id) => (
            <Imgs
              className="swing-in-top-fwd"
              src={image}
              alt={`${image}-${id}`}
              onClick={() => handleDeleteImage(id)}
            />
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
        {/* <div className='detail-button'> */}
        {canSubmit() ? (
          <button
            style={{ color: "black" }}
            className="navbar-btn"
            type="button"
            onClick={() => {
              let write = uploadFile(showImagesFile);
              bordWrite(
                write,
                title,
                content,
                detailRbn,
                navigate,
                postThemeNum
              );
            }}
          >
            공유하기
          </button>
        ) : (
          <button type="button" disabled>
            제목과 내용 그리고 테마를 모두 입력하세요
          </button>
        )}
        {/* </div> */}
      </div>
    </div>
  );
};
// className="slide-in-left" edf2f4
// box-shadow: 0 0 0 2px #e9ebec, 0 0 0 11px #fcfdfe;
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
                console.log("테마 e.target.value :", val.themeId);
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

  // if (i === 0) {
  //   let firstAccum = getNAccumDetailTime(periodIndex, reduxState, obj);
  //   setUpTime(firstAccum);
  //   setDownTime(firstAccum + obj.atime / 1000 / 60);
  //   fromWooJaeData[periodIndex]["day" + (periodIndex + 1)][i + 1].starttime =
  //     firstAccum + obj.atime / 1000 / 60;
  //   const forBlackBoxRedux = getTimes(
  //     periodIndex,
  //     firstAccum,
  //     firstAccum + obj.atime / 1000 / 60
  //   );
  //   dispatch(addWholeBlackBox(forBlackBoxRedux));
  // }

  useEffect(() => {
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

// function getNAccumDetailTime(periodIndex, reduxState, obj) {
//   let sumTime;
//   let sTime = reduxState.timeSetObj.find((val) => {
//     return val.day === periodIndex + 1;
//   });
//   if (sTime.ampm === "오후" && sTime.time >= 1 && sTime.time <= 11) {
//     sumTime = (sTime.time + 12) * 60 + sTime.min + obj.mtime / 1000 / 60;
//   } else {
//     sumTime = sTime.time * 60 + sTime.min + obj.mtime / 1000 / 60;
//   }
//   return sumTime;
// }

function uploadFile(showImages) {
  //이미지 업로드
  let formData = new FormData(); // formData 객체를 생성한다.
  for (let i = 0; i < showImages.length; i++) {
    formData.append("multifiles", showImages[i]); // 반복문을 활용하여 파일들을 formData 객체에 추가한다
  }
  return formData;
}
function bordWrite(write, title, content, detailRbn, navigate, postThemeNum) {
  write.append("id", sessionStorage.getItem("username"));
  write.append("title", title);
  write.append("content", content);
  write.append("rbn", detailRbn);
  write.append("themeid", postThemeNum);

  let token = sessionStorage.getItem("token");
  axios
    .post("/aamurest/bbs/edit", write, {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "multipart/form-data",
      },
    })
    .then((resp) => {
      console.log(resp.data.result);
      if (resp.data.result === "insertSuccess") {
        alert("글이 저장되었습니다");
        let bool = window.confirm("게시판으로 이동하겠습니까?");
        if (bool) navigate("/forum");
      } else {
        alert("저장오류가 발생했습니다. 관리자에게 문의하세요");
        navigate("/myPage");
      }
    })
    .catch((error) => {
      console.log(error);
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
