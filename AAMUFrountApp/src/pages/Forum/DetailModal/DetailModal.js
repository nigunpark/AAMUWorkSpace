import React, { useEffect, useRef, useState } from "react";
import styled, { keyframes } from "styled-components";
import Notice from "../Notice/Notice";
import "../Board2/DetailButton.scss";
import { Link, useNavigate } from "react-router-dom";
import { Rating } from "@mui/material";
import axios from "axios";
import "./BookMark.css";
import { useDispatch, useSelector } from "react-redux";
import {
  addChatBotData,
  addForChatInfo,
  addWholeBlackBox,
} from "../../../redux/store";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faX } from "@fortawesome/free-solid-svg-icons";
import { faBookmark } from "@fortawesome/free-solid-svg-icons";
import { faStar } from "@fortawesome/free-solid-svg-icons";
const DetailModal = ({
  detailOne,
  setIsOpen,
  setShowCBModal,
  setIsLoading,
}) => {
  let navigate = useNavigate();
  function dateFormat(date) {
    let month = date.getMonth() + 1;
    let day = date.getDate();
    month = month >= 10 ? month : "0" + month;
    day = day >= 10 ? day : "0" + day;
    return date.getFullYear() + "-" + month + "-" + day;
  }

  let [star, setStar] = useState(0); //사용자가 입력하는 별점
  let [comment, setComment] = useState(""); // comment 사용자가 입력하는 댓글
  let [feedComments, setFeedComments] = useState([]); // feedComments 댓글 리스트 저장
  let [isValid, setIsValid] = useState(false); // 댓글 게시가능여부 (유효성 검사)

  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [theme, setTheme] = useState("");
  const [userId, setUserId] = useState("");
  const [photo, setPhoto] = useState([]);
  const [showChat, setShowChat] = useState(false);
  const [plannerDate, setPlannerDate] = useState({});
  const [detailRoute, setDetailRoute] = useState([]);
  const [prevChats, setPrevChats] = useState([]);
  const [myBookMark, setMyBookMark] = useState(false);

  const [roomno, setRoomno] = useState(0);

  let loginId = sessionStorage.getItem("username");

  let dispatch = useDispatch();

  useEffect(() => {
    const $body = document.querySelector("body");
    $body.style.overflow = "hidden";
    return () => ($body.style.overflow = "auto");
  }, []);
  let modalRef = useRef();
  let commentRef = useRef();
  let reviewBtn = useRef();
  useEffect(() => {
    setIsLoading(true);
    let token = sessionStorage.getItem("token");
    axios
      .get(`/aamurest/bbs/SelectOne/${detailOne.rbn}`, {
        params: {
          id: sessionStorage.getItem("username"),
        },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        console.log("");
        setTitle(resp.data.title);
        setContent(resp.data.content);
        setTheme(resp.data.themename);
        setPhoto(resp.data.photo);
        setFeedComments(resp.data.reviewList);
        setDetailRoute(resp.data.routeList);
        setUserId(resp.data.id);
        setMyBookMark(resp.data.bookmark);

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
        setIsLoading(false);
      })
      .catch((error) => {
        console.log((error) => console.log("게시판 상세보기 실패", error));
      });
  }, []);

  function isId(e) {
    if (e.id == sessionStorage.getItem("username")) {
      return true;
    }
  }
  const isReviewId = feedComments.filter(isId).length;
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

  async function post() {
    let token = sessionStorage.getItem("token");
    await axios
      .post(
        "/aamurest/review/edit",
        {
          id: sessionStorage.getItem("username"),
          rbn: detailOne.rbn,
          rate: star,
          review: commentRef.current.value,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      )
      .then((resp) => {
        reviewData();
      });
  }

  const [detailRateAvg, setRateAvg] = useState(0);
  async function reviewData() {
    let token = sessionStorage.getItem("token");
    await axios
      .get(`/aamurest/bbs/SelectOne/${detailOne.rbn}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        setRateAvg(resp.data.rateavg);
        setFeedComments(resp.data.reviewList);
      })
      .catch((error) => {
        console.log((error) => console.log("게시판 상세보기 실패", error));
      });
  }

  let reviewPost = () => {
    post(); // 리뷰 axios post

    setComment("");
    setStar(0);
    setIsValid(false);
  };
  useEffect(() => {
    reviewData();
  }, []);

  const reviewDelete = (rno, rbn) => {
    //리뷰 삭제
    let token = sessionStorage.getItem("token");
    axios
      .delete("/aamurest/review/edit", {
        params: {
          rno,
          rbn,
        },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        reviewData();
        alert("리뷰가 삭제되었어요.");
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const bookMarkOne = () => {
    let token = sessionStorage.getItem("token");
    let id = sessionStorage.getItem("username");
    let rbn = detailOne.rbn;

    axios
      .get("/aamurest/bbs/bookmark", {
        params: {
          id,
          rbn,
        },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        // console.log("북마크 추가/취소 성공 (DetailModal.js) :", resp.data);
        if (myBookMark === false) {
          alert("북마크가 되었어요.");
          let bool = window.confirm("마이페이지로 이동하시겠어요?");
          if (bool) navigate("/myPage");
        } else if (myBookMark === true) {
          alert("북마크가 취소 되었어요.");
        }
      })
      .catch((error) => {
        // console.log("북마크 실패 (DetailModal.js) :", error);
      });
  };

  const CommentList = ({ val, setFeedComments, setIsOpen }) => {
    const rno = val.rno;
    const rbn = val.rbn;
    return (
      <div className="detail__modal-commentsList">
        <Stars>
          <FontAwesomeIcon
            icon={faStar}
            style={{ marginRight: "3px", color: "gold" }}
          />
          <span>{val.rate.toString().padEnd(3, ".0")}</span>
        </Stars>
        <span
          style={{
            display: "flex",
            alignItems: "center",
            fontSize: "14px",
            marginLeft: "5px",
          }}
        >
          {val.id}
        </span>
        <p>{val.review}</p>
        {sessionStorage.getItem("username") == val.id ? (
          <div style={{ color: "red" }}>
            <Name
              onClick={() => {
                reviewDelete(rno, rbn);
                setFeedComments((curr) => {
                  return curr.filter((e, index) => e.rno !== rno);
                });
                reviewBtn.current.style.display = "initial";
              }}
            >
              삭제
            </Name>
          </div>
        ) : null}
      </div>
    );
  };
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

  let stars = 0;
  detailOne.reviewList.forEach((obj, i) => {
    stars += Number(obj.rate);
  });
  stars = Math.round((stars / detailOne.reviewList.length) * 10) / 10;

  async function getChatRoom(detailOne, dispatch, setRoomno) {
    await axios
      .post(
        "/aamurest/chat/room?fromid=" +
          sessionStorage.getItem("username") +
          "&toid=" +
          detailOne.id
      )
      .then((resp) => {
        console.log("resp.data", resp.data);
        setPrevChats(resp.data.list);
        setRoomno(resp.data.roomno);
        dispatch(addForChatInfo({ ...resp.data, id: detailOne.id }));
      })
      .catch((err) => {
        console.log(err);
      });
  }
  return (
    <DetailContainer>
      <DetailOverlay>
        {/* <DetailModalWrap> */}
        <div className="detailModalWrap" ref={modalRef}>
          <Plan>
            <div
              style={{ display: "flex", flexDirection: "column", gap: "10px" }}
            >
              {routeData.map((route, idx) => {
                return (
                  <div key={idx} className="detail-plan">
                    <div className="paln-date">
                      {idx + 1}일차 ({plannerDate.month}월{" "}
                      {parseInt(plannerDate.date) + idx}
                      일&nbsp;
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
          <div>
            <span
              className="detail__plan-exit"
              onClick={(e) => {
                dispatch(addChatBotData({}));
                setIsOpen(false);
                setShowCBModal(false);
                setShowChat(false);
              }}
            >
              <FontAwesomeIcon icon={faX} />
            </span>
            <DetailContents>
              <div>
                <div className="detail__plan-card-photo">
                  <Notice photo={photo} />

                  <div className="detail__plan-card">
                    <div
                      style={{
                        fontSize: "20px",
                        display: "flex",
                        justifyContent: "space-between",
                      }}
                    >
                      <div>
                        <FontAwesomeIcon
                          icon={faStar}
                          style={{ marginRight: "3px", color: "gold" }}
                        />
                        <span style={{ fontWeight: "bold" }}>
                          {detailRateAvg === 0
                            ? "리뷰가 없어요"
                            : detailRateAvg}
                        </span>
                      </div>
                      <div className="detail__plan-underTitle">
                        <div>
                          by{""}
                          <span
                            className="detail__plan-writer"
                            onClick={(e) => {
                              e.stopPropagation();
                              getChatRoom(detailOne, dispatch, setRoomno);
                              setShowChat(!showChat);
                              setTimeout(() => {
                                if (!showChat)
                                  modalRef.current.style.left = "1%";
                                else modalRef.current.style.left = "8%";
                              }, 100);
                            }}
                          >
                            {" "}
                            {detailOne.id}
                          </span>
                        </div>
                        <div onClick={bookMarkOne}>
                          {userId === loginId ? null : myBookMark == true ? (
                            <FontAwesomeIcon
                              icon={faBookmark}
                              className="detail__plan-bookMark"
                              onClick={() => {
                                setMyBookMark(false);
                              }}
                            />
                          ) : (
                            <i
                              class="fa-regular fa-bookmark detail__plan-bookMark"
                              onClick={() => {
                                setMyBookMark(true);
                              }}
                            ></i>
                          )}
                        </div>
                      </div>
                    </div>
                    <h4 style={{ fontSize: "23px", fontWeight: "bold" }}>
                      {detailOne.title}
                    </h4>
                    <Tag>
                      <Date>
                        작성일.{" "}
                        {dateFormat(new window.Date(detailOne.postdate))}
                      </Date>
                      tag : #{theme}
                    </Tag>
                  </div>
                  <Textarea>
                    <p style={{ padding: "10px" }}>{content}</p>
                  </Textarea>
                </div>
              </div>
              <div className="detail__modal__comments-container">
                <DetailReview>
                  {feedComments.map((val, idx) => {
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
                  })}
                </DetailReview>
                <div>
                  <div>
                    {isReviewId == 1 ? null : (
                      <Rating
                        name="simple-controlled"
                        precision={0.5}
                        onChange={(e, newValue) => {
                          setStar(newValue);
                          console.log("newValue", newValue);
                        }} // 사용자가 선택한만큼 setStar를 통해 star의 값 변경
                        value={star}
                      />
                    )}
                  </div>

                  <div
                    style={{ display: "grid", gridTemplateColumns: "auto 17%" }}
                  >
                    <div className="detail_modal_input-container">
                      {sessionStorage.getItem("username") == userId ? (
                        <input
                          type="text"
                          placeholder="글쓴이는 댓글 안되요!"
                          disabled
                        />
                      ) : isReviewId === 1 ? (
                        <input
                          type="text"
                          placeholder="댓글은 하나만!"
                          disabled
                        />
                      ) : (
                        <input
                          type="text"
                          placeholder="댓글 달기..."
                          ref={commentRef}
                          onKeyUp={(e) => {
                            e.target.value.length > 0
                              ? setIsValid(true)
                              : setIsValid(false);
                          }} //사용자가 리뷰를 작성했을 때 빈공간인지 확인하여 유효성 검사
                        />
                      )}
                    </div>
                    <div className="detail-button">
                      {sessionStorage.getItem("username") === detailOne.id ? (
                        <span className="detail__plan__review-btn" disabled>
                          댓글쓰기
                        </span>
                      ) : (
                        <span
                          ref={reviewBtn}
                          className="detail__plan__review-btn"
                          onClick={(e) => {
                            e.stopPropagation();
                            reviewPost(commentRef);
                            commentRef.current.value = "";
                            // e.target.style.display = "none";
                          }}
                        >
                          댓글쓰기
                        </span>
                      )}
                    </div>
                  </div>
                </div>
              </div>
            </DetailContents>

            {/* <div style={{border:'1px blue solid'}}>사용목적 미정 공간</div> */}
          </div>
          {/* </DetailModalWrap> */}
        </div>
        {showChat && <Chat detailOne={detailOne} roomno={roomno} />}
      </DetailOverlay>
    </DetailContainer>
  );
};

function DetailSetting({ fromWooJaeData, periodIndex, obj, i }) {
  const [upTime, setUpTime] = useState(0);
  const [downTime, setDownTime] = useState(0);
  let dispatch = useDispatch();

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
    } else if (i === 0) {
      setUpTime(
        (fromWooJaeData[periodIndex]["day" + (periodIndex + 1)][i].starttime +
          obj.mtime / 1000 / 60) /
          60000
      );
      setDownTime(
        (fromWooJaeData[periodIndex]["day" + (periodIndex + 1)][i].starttime +
          obj.mtime / 1000 / 60) /
          60000 +
          fromWooJaeData[periodIndex]["day" + (periodIndex + 1)][i].atime /
            1000 /
            60
      );
    }
  }, []);

  return (
    <div className="MyPost_clock">
      <span>
        {Math.floor(upTime / 60)
          .toString()
          .padStart(2, "0")}{" "}
        :{" "}
        {Math.floor(upTime % 60)
          .toString()
          .padStart(2, "0")}
      </span>
      <span>&nbsp;~&nbsp;</span>
      <span>
        {Math.floor(downTime / 60)
          .toString()
          .padStart(2, "0")}{" "}
        :{" "}
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

const Chat = ({ detailOne, roomno }) => {
  const [chats, setChats] = useState([]);
  let reduxState = useSelector((state) => state);
  useEffect(() => {
    // let client = getConnet(reduxState.forChatInfo.roomno, setChats);
    // console.log("client", client);
    // setClient(client);
    // if (showChat === false) subscription.unsubscribe();
    console.log("reduxState.forChatInfo", reduxState.forChatInfo);
    reduxState.forChatInfo.client.subscribe(
      `/queue/chat/message/${roomno}`,
      (message) => {
        console.log(JSON.parse(message.body));
        if (
          JSON.parse(message.body).authid !== sessionStorage.getItem("username")
        ) {
          let newChat = {
            // missage: message.body.missage,
            missage: JSON.parse(message.body).missage,
            authid: JSON.parse(message.body).authid,
          };
          setChats((curr) => [...curr, newChat]);
        }
      }
    );
  }, []);
  function getChats() {}
  let inputRef = useRef();
  let bodyRef = useRef();
  console.log("리렌더링 채팅");
  // console.log("bodyRef", bodyRef.current.scrollTop);
  return (
    <Container>
      {/* <InnnerContainer> */}
      <Content>
        <Title>{detailOne.id}님과 대화</Title>
        <Body ref={bodyRef}>
          <div style={{ display: "flex", flexDirection: "column" }}>
            {chats.map((val, i) => {
              return (
                <div
                  className={
                    val.authid !== sessionStorage.getItem("username")
                      ? "chatBox box"
                      : "chatBox"
                  }
                >
                  <div
                    style={{
                      display: "flex",
                      justifyContent: "center",
                      alignItems: "center",
                      gap: "5px",
                    }}
                  >
                    <div className="chat__profile__img-container">
                      {/* <img
                      className="chat__profile__img"
                      src={val.autopro ?? "/images/no-image.jpg"}
                      onError={(e) => {
                        e.target.src = "/images/no-image.jpg";
                      }}
                    /> */}
                      <span className="chatBox__time-span">
                        {new window.Date().getHours() > 12 ? "오후" : "오전"}
                        {new window.Date().getHours() > 12
                          ? new window.Date().getHours() - 12
                          : new window.Date().getHours()}
                        :
                        {new window.Date()
                          .getMinutes()
                          .toString()
                          .padStart(2, "0")}
                      </span>
                    </div>

                    <span className="chatBox__span">
                      {val.missage}
                      <br />
                    </span>
                  </div>
                </div>
              );
            })}
          </div>
        </Body>

        <div className="chatBot__input-container">
          <input
            type="text"
            className="chatBot__input"
            ref={inputRef}
            onKeyDown={(e) => {
              if (e.key === "Enter") {
                let newChat = {
                  missage: e.target.value,
                  authid: sessionStorage.getItem("username"),
                };
                setChats((curr) => [...curr, newChat]);
                sendChat(inputRef, reduxState);
                e.target.value = "";

                // getChats();
                // chatArr.push({ message: inputRef.current.value, bool: false });
                // chatArr = [...chatArr];
              }
            }}
          />
          <span
            className="chatBot__input-btn"
            onClick={(e) => {
              let newChat = {
                missage: e.target.value,
                authid: sessionStorage.getItem("username"),
              };
              setChats((curr) => [...curr, newChat]);
              sendChat(inputRef, reduxState);
              e.target.value = "";
            }}
          >
            보내기
          </span>
        </div>
      </Content>
      {/* </InnnerContainer> */}
    </Container>
  );
};

function sendChat(inputRef, reduxState) {
  reduxState.forChatInfo.client.publish({
    destination: "/app/chat/message",
    body: JSON.stringify({
      roomno: reduxState.forChatInfo.roomno,
      authid: sessionStorage.getItem("username"),
      missage: inputRef.current.value,
      senddate: new window.Date().getTime(),
      authpro: sessionStorage.getItem("userimg"),
    }),
  });
  // setChats(chatArr);
}

const swing_chat = keyframes`
0% {
  transform: rotateX(-100deg);
  transform-origin: top;
  opacity: 0;
}
100% {
  transform: rotateX(0deg);
  transform-origin: top;
  opacity: 1;
}
`;
const Container = styled.div`
  position: absolute;
  z-index: 100;
  top: 47px;
  right: 20px;
  transition: 0.3s;
  animation: ${swing_chat} 0.7s cubic-bezier(0.175, 0.885, 0.32, 1.275) 0.5s
    both;
`;
const InnnerContainer = styled.div`
  position: relative;
`;
const Content = styled.div`
  position: relative;
  background: var(--blueWhite);
  box-shadow: var(--lightShadow);
  width: 300px;
  height: 840px;
  border-radius: 7px;
`;
const Title = styled.div`
  //   position: absolute;
  width: 100%;
  height: 30px;
  background: var(--orange);
  border-radius: 7px 7px 0 0;
  color: white;
  font-weight: bold;
  display: flex;
  justify-content: center;
  align-items: center;
  letter-spacing: 2px;
`;

const Body = styled.div`
  position: absolute;
  width: 97%;
  height: 755px;
  margin: 5px;
  margin-top: 5px;
  background: white;
  border-radius: 7px;
  display: flex;
  flex-direction: column-reverse;
  // justify-content: end;
  overflow-y: auto;
  &::-webkit-scrollbar {
    width: 3px;
  }
`;
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
  animation: ${bounce_modal_plan} 1.4s cubic-bezier(0.175, 0.885, 0.32, 1.275)
    both;
  animation-delay: 0.2s;
`;

const Textarea = styled.div`
  position: relative;
  box-shadow: var(--shadow);
  border-top: 1px solid rgba(128, 128, 128, 0.4);
  width: 100%;
  height: 215px;
  overflow: auto;
  font-size: 17px;
  background: white;
  border-radius: 7px;
`;
// position: fixed; 모달창 열리면 외부 스크롤바 안되게
const DetailContainer = styled.div`
  position: fixed;
  display: auto;
  width: 100%;
  height: 100%;
  z-index: 200;
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
  position: relative;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.4);
`;

const DetailTitle = styled.div`
  margin-top: 35px;
  width: 50%;
  position: relative;
  height: 150px;
  margin-bottom: -80px;
  display: flex;
  flex-direction: column;
  gap: 1rem;
  // border: 1px solid red;
  margin-left: auto;
`;

const DetailReview = styled.div`
  font-size: 18px;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  // border: 1px solid green;
`;
const Stars = styled.span`
  // border: 1px solid red;
  // width: 80px;
  display: flex;
  justify-content: center;
  align-items: center;
`;
const UserName = styled.span`
  // border: 1px solid blue;
  // width: 100px;
`;
const Name = styled.span`
  font-size: 13px;
  color: #6d6875;
  &:hover {
    color: black;
    font-weight: bold;
    cursor: pointer;
  }
`;

const DetailContents = styled.div`
  display: flex;
  gap: 10px;
  width: 100%;
  height: 100%;
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
