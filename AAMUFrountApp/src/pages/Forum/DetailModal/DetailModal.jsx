import React, { useEffect, useRef, useState } from "react";
import styled from "styled-components";
import Notice from "../Notice/Notice";
import "../Board2/DetailButton.scss";
import { Link } from "react-router-dom";
import { Rating } from "@mui/material";
import axios from "axios";
import "./BookMark.css";
import { useDispatch, useSelector } from "react-redux";
import { addChatBotData, addWholeBlackBox } from "../../../redux/store";

const DetailModal = ({ postDay, detailRbn, setIsOpen, setShowCBModal }) => {
  function dateFormat(date) {
    let month = date.getMonth() + 1;
    let day = date.getDate();
    month = month >= 10 ? month : "0" + month;
    day = day >= 10 ? day : "0" + day;
    return date.getFullYear() + "-" + month + "-" + day;
  }

  let username = sessionStorage.getItem("username");
  let [star, setStar] = useState(0); //사용자가 입력하는 별점
  let [comment, setComment] = useState(""); // comment 사용자가 입력하는 댓글
  let [feedComments, setFeedComments] = useState([]); // feedComments 댓글 리스트 저장
  let [isValid, setIsValid] = useState(false); // 댓글 게시가능여부 (유효성 검사)

  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [theme, setTheme] = useState("");
  const [userId, setUserId] = useState("");
  const [photo, setPhoto] = useState([]);
  const [detailRoute, setDetailRoute] = useState([]);
  // const [rno, setRno] = useState(0);
  let dispatch = useDispatch();
  useEffect(() => {
    const $body = document.querySelector("body");
    $body.style.overflow = "hidden";

    return () => ($body.style.overflow = "auto");
  }, []);

  useEffect(() => {
    let token = sessionStorage.getItem("token");
    axios
      .get(`/aamurest/bbs/SelectOne/${detailRbn}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        console.log("게시판 상세보기 : ", resp.data);
        setTitle(resp.data.title);
        setContent(resp.data.content);
        setTheme(resp.data.themename);
        setPhoto(resp.data.photo);
        setFeedComments(resp.data.reviewList);
        setDetailRoute(resp.data.routeList);
        // setUserId(resp.data.id);
      })
      .catch((error) => {
        console.log((error) => console.log("게시판 상세보기 실패", error));
      });
  }, []);

  function isId(e) {
    if (e.id == username) {
      return true;
    }
  }

  const isReviewId = feedComments.filter(isId).length;
  // console.log("리뷰의 id만 가져오나", isReviewId);
  // console.log("ddd", feedComments);

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

  // console.log("routeData :", routeData);

  async function post() {
    let token = sessionStorage.getItem("token");
    await axios
      .post(
        "/aamurest/review/edit",
        {
          id: sessionStorage.getItem("username"),
          rbn: detailRbn,
          rate: star,
          review: comment,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      )
      .then((resp) => {
        console.log("리뷰 post 했고 돌아오는 데이터", resp.data);
        reviewData();
      });
  }

  async function reviewData() {
    let token = sessionStorage.getItem("token");
    await axios
      .get(`/aamurest/bbs/SelectOne/${detailRbn}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        setFeedComments(resp.data.reviewList);
      })
      .catch((error) => {
        console.log((error) => console.log("게시판 상세보기 실패", error));
      });
  }

  let reviewPost = (e) => {
    post(); // 리뷰 axios post

    //사용자가 입력한 댓글창과 별점 초기화
    setComment("");
    setStar(0);
    setIsValid(false);
  };

  const reviewDelete = (rno) => {
    let token = sessionStorage.getItem("token");
    axios
      .delete("/aamurest/review/edit", {
        params: {
          rno: rno,
        },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        console.log("삭제 성공 :", resp.data);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const CommentList = ({ val, setFeedComments, setIsOpen }) => {
    const rno = val.rno;
    // console.log("리뷰정보 :", rno);
    return (
      <div>
        <Stars>
          <img src="/images/star.png" style={{ width: "30px" }} />
          {val.rate}
        </Stars>

        <UserName>
          <Name>{val.id} 님</Name>
        </UserName>

        <p>{val.review}</p>
        <span></span>
        {username == val.id ? (
          <EditDelte
            type="button"
            onClick={() => {
              reviewDelete(rno);
              setFeedComments((curr) => {
                return curr.filter((e, index) => e.rno !== rno);
              });
            }}
          >
            <Name>삭제</Name>
          </EditDelte>
        ) : null}
      </div>
    );
  };

  return (
    <DetailContainer>
      <DetailOverlay>
        <DetailModalWrap>
          <Plan>
            <div>
              {routeData.map((route, idx) => {
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
                                    style={{
                                      fontSize: "15px",
                                      marginTop: "10px",
                                    }}
                                  >
                                    {obj.dto.title}
                                  </div>
                                </div>
                              </div>
                            </div>
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
                        ); //MyPost_clock
                      }) //내부 map
                    }
                  </div>
                );
              })}
            </div>
          </Plan>

          <DetailTitle>
            <span>
              <div className="anim-icon star">
                <input
                  type="checkbox"
                  id="star"
                  onClick={(e) => {
                    console.log("북마크 체크 여부 :", e.currentTarget.checked);
                  }}
                />
                <label for="star"></label>
              </div>
              {/* 여기가 글 작성할때 쓴 제목 */}
              {title}
              <div className="detail-button">
                <button
                  className="learn-more_exit"
                  type="button"
                  onClick={(e) => {
                    dispatch(addChatBotData({}));
                    setIsOpen(false);
                    setShowCBModal(false);
                  }}
                >
                  exit
                </button>
              </div>
            </span>
          </DetailTitle>

          <DetailContents>
            <Notice photo={photo} />
            {/* dummy={dummy.imgsdata} */}

            <Textarea>{content}</Textarea>
            <div>
              {isReviewId == 1 ? null : (
                <Rating
                  name="simple-controlled"
                  precision={0.5}
                  onChange={(e, newValue) => {
                    setStar(newValue);
                  }} // 사용자가 선택한만큼 setStar를 통해 star의 값 변경
                  value={star}
                />
              )}
            </div>
            <Tag>
              <Date>작성일. {postDay}</Date>
              {theme}
            </Tag>
            {isReviewId == 1 ? (
              <input type="text" placeholder="리뷰는 하나만!" disabled />
            ) : (
              <input
                type="text"
                placeholder="리뷰 달기..."
                onChange={(e) => {
                  setComment(e.target.value);
                }} //리뷰창 변할때마다 setComment를 통해 comment의 값 변경
                onKeyUp={(e) => {
                  e.target.value.length > 0
                    ? setIsValid(true)
                    : setIsValid(false);
                  // console.log(isValid);
                }} //사용자가 리뷰를 작성했을 때 빈공간인지 확인하여 유효성 검사
                value={comment}
              />
            )}
            {/* DetailButton.scss */}
            <div className="detail-button">
              {isReviewId == 1 ? null : isValid ? (
                <button
                  className="learn-more"
                  type="button"
                  onClick={(e) => {
                    reviewPost();
                  }}
                >
                  리뷰 등록
                </button>
              ) : (
                <button className="learn-more" type="button" disabled>
                  리뷰를 작성하세요
                </button>
              )}
            </div>
          </DetailContents>

          {/* <div style={{border:'1px blue solid'}}>사용목적 미정 공간</div> */}

          <DetailReview>
            {
              //feedComments 에 담겨있을 댓글 값을 CommentList 컴포넌트에 담아서 가져오기
              //CommentList 컴포넌트는 반복적으로 추가되는 사용자 댓글을 하나하나 담고있음
              //userName 은 위에서 ADMIN 을 담은 값을,
              //userComment 는 feedComments 에 담겨있는 배열 담는다
              //stars 는 commentStar의 인덱스 번호에 맞는 별점 배열을 담는다
              feedComments.map((val, idx) => {
                return (
                  <CommentList
                    val={val}
                    setFeedComments={setFeedComments}
                    // stars={commentStar[idx]}
                    // userName={userName}
                    // userComment={commnetArr}
                    // key={idx}
                    // index={idx}
                  />
                );
              })
            }
          </DetailReview>
        </DetailModalWrap>
      </DetailOverlay>
    </DetailContainer>
  );
};

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
const Plan = styled.div`
  display: flex;
  flex-direction: column;
  overflow: auto;
  grid-row: 1 / 4;
  &::-webkit-scrollbar {
    display: none;
  }
`;
const PlanTitle = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;
const PlanDate = styled.span`
  height: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #edf2f4;
  // border-radius: 8px;
`;
const DetailPlan = styled.div`
  width: 100%;
  font-size: 20px;
  margin-top: 3px;
  // border: 1px red solid;
`;
const PlanTime = styled.div``;
const PlanRegion = styled.div`
  margin-top: 5px;
  margin-bottom: 10px;
`;

const Textarea = styled.div`
  position: relative;
  width: 99%;
  height: 320px;
  overflow: auto;
  font-size: 20px;
  box-shadow: 0 0 0 2px #e9ebec, 0 0 0 11px #fcfdfe;
`;
// position: fixed; 모달창 열리면 외부 스크롤바 안되게
const DetailContainer = styled.div`
  position: fixed;
  display: auto;
  width: 100%;
  height: 100%;
  z-index: 1000;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: auto;
`;

const DetailOverlay = styled.div`
  position: absolute;
  width: 100%;
  height: 100%;
  background-color: rgba(255, 255, 255, 0.1);
`;

const DetailModalWrap = styled.div`
  display: grid;
  grid-template-columns: 300px 1000px;
  border: 1px white solid;
  height: 90%;
  // height: 650px;
  overflow: auto;

  border-radius: 15px;
  background-color: #fff;

  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  box-shadow: 0 0 0 10px #e9ebec, 0 0 0 11px #fcfdfe;

  &::-webkit-scrollbar {
    display: none;
  }
`;

const DetailTitle = styled.div`
  margin-top: 15px;
  margin-bottom: 10px;

  span {
    float: center;
    font-size: 1.5em;
    margin-left: 40px;
  }
  div {
    margin-right: 25px;
  }
`;

const DetailReview = styled.div`
  margin: 0px 20px;
  font-size: 18px;

  div {
    display: grid;
    grid-template-columns: 70px 200px 620px 30px 30px;
    margin-top: 2px;
    border-bottom: solid 1px #c3cff4;
  }
`;
const Stars = styled.span`
  // border: 1px solid red;
  // width: 80px;
`;
const UserName = styled.span`
  // border: 1px solid blue;
  // width: 100px;
`;
const Name = styled.span`
  font-size: 13px;
  color: #6d6875;
`;

const EditDelte = styled.button`
  // border: 1px solid black;
  height: 30px;
  margin-left: 3px;
`;

const DetailContents = styled.div`
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-template-rows: 400px 50px 50px;
  gap: 5px;

  // overflow: auto;

  margin: 10px 10px;

  h1 {
    font-size: 30px;
    font-weight: 600;
  }
  img {
    margin-top: 10px;
    width: 100%;
  }
  input {
    width: 100%;
    margin-left: 10px;
    font-size: 18px;
  }
`;
const Tag = styled.span`
  font-size: 12.5px;
  color: #8e8e8e;
`;
const Date = styled.div`
  float: right;
  justify-content: center;
  align-items: center;
  font-size: 12.5px;
  color: #8e8e8e;
  margin-right: 5px;
`;

const Button = styled.button`
  float: right;

  font-size: 14px;
  padding: 10px 20px;
  border: none;
  background-color: #ababab;
  border-radius: 10px;
  color: white;
  font-style: italic;
  font-weight: 200;
  cursor: pointer;

  &:hover {
    background-color: #898989;
  }
`;

export default DetailModal;
