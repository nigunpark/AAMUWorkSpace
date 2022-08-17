import styled from "styled-components";
import React, { useEffect, useRef, useState } from "react";
import MenuModal from "../MenuModal";
import axios from "axios";
import "../Slider/slick.css";
import "../Slider/slick-theme.css";
import { SwiperSlide, Swiper } from "swiper/react";
import Picker from "emoji-picker-react";
import { A11y, Autoplay, Navigation, Pagination, Scrollbar } from "swiper";
import "swiper/css"; //basic
import "swiper/css/navigation";
import "swiper/css/pagination";
import "../Upload/UploadSwiper.css";
import dayjs from "dayjs";
import { CommentsDisabled } from "@mui/icons-material";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faLocationDot } from "@fortawesome/free-solid-svg-icons";

function Comment({
  val,
  setlist,
  forReRender,
  setForReRender,
  seteditModal,
  setcommentModal,
  setloading,
  page,
  list,
}) {
  let menuRef = useRef();
  let replyRef = useRef("");
  let deleteRef = useRef();
  let commentRef = useRef();
  const [commentHeart, setCommentHeart] = useState(false);
  const [modalShow, setModalShow] = useState(false);
  const [reply, setReply] = useState(false);
  let [comment, setComment] = useState("");
  let [commentbb, setCommentbb] = useState("");
  // const [replyOne, setreplyOne] = useState("");

  const [emoji, setemoji] = useState(false);
  const onEmojiClick = (event, emojiObject) => {
    // setChosenEmoji(emojiObject); 
    replyRef.current.value =  replyRef.current.value+emojiObject.emoji;
  };

  let [isValid, setisValid] = useState(false);

  function btn_check() {
    replyRef.current !== undefined &&
      (replyRef.current.value.length >= 1 //사용자가 키를 눌렀다 떼었을때 길이가 0을 넘는 값인지 유효성 검사 결과 값을 담는다
        ? setisValid(true)
        : setisValid(false));
  }

  function menuModalRef(e) {
    e.stopPropagation();
    let divdiv = document.getElementById("#scrollDiv"); 
    divdiv.scrollTop = divdiv.scrollHeight;
  }
  function menuModalRef(e) {
    e.stopPropagation();
    if (e.target != menuRef.current) setModalShow(false);
  }
  window.addEventListener("click", menuModalRef);

  const [comments, setcomments] = useState([]);
  function commentModal(setcomments) {
    
    // const copyFeedComments = [...comments]; //feedComments에 담겨있던 댓글 받아옴
    // copyFeedComments.push(comment); //copyFeedComments에 있는 기존 댓글에 push하기 위함
    setcomments([...comments]); //copyFeedComments 담겨있을 comment를 setfeedComments로 변경
    let token = sessionStorage.getItem("token");
    axios
      .get("/aamurest/gram/SelectOne", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        params: {
          id: sessionStorage.getItem("username"),
          lno: val.lno,
        },
      })
      .then((resp) => {
        setcomments(resp.data.commuCommentList);
      })
      .catch((error) => {
        
      });
    replyRef.current.value = "";
  }

  useEffect(() => {
    commentModal(setcomments);
  }, []);

  function fillLike(setForReRender, forReRender) {
    //백이랑 인스타 리스드를 뿌려주기 위한 axios

    let token = sessionStorage.getItem("token");
    axios
      .get("/aamurest/gram/like", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        params: {
          lno: parseInt(val.lno),
          id: sessionStorage.getItem("username"),
        },
      })
      .then((resp) => {
        val.islike = resp.data.isLike;
        val.likecount = resp.data.likecount;
        setForReRender(!forReRender);
      })
      .catch((error) => {
      
      });
  }

  function post(replyRef) {
    //유효성 검사를 통과하고 게시버튼 클릭시 발생하는 함수

    let token = sessionStorage.getItem("token");
    axios
      .post(
        "/aamurest/gram/comment/edit",
        {
          id: sessionStorage.getItem("username"),
          reply: replyRef.current.value,
          lno: val.lno,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      )
      .then((resp) => {
        const replyOne = resp.data.reply;
        const copyComments = [...replyOne];
        setcomments({ ...comments }, copyComments);
        setisValid(false);
        commentModal(setcomments);
        feedList(setloading, setlist, page, list);
      })
      .catch((error) => {
      });

    replyRef.current.value = ""; //사용자 댓글창을 빈 댓글 창으로 초기화
  }

  function deleteOne(cno) {
    let token = sessionStorage.getItem("token");

    axios
      .delete("/aamurest/gram/comment/edit", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        params: {
          lno: val.lno,
          cno: cno,
        },
      })
      .then((resp) => {
        setcomments((curr) => {
          return curr.filter((item) => {
            return item.cno != cno;
          });
        });
        // commentModal(setcomments);
        feedList(setloading, setlist, page, list);
      })
      .catch((error) => {
      });
  }
  const feedList = async (setloading, setlist, page, list) => {
    //백이랑 인스타 리스드를 뿌려주기 위한 axios
    let token = sessionStorage.getItem("token");
    const temp = await axios.get(`/aamurest/gram/selectList?page=${page}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
      params: {
        id: sessionStorage.getItem("username"),
      },
    });

    let removelist = list;
    for (let k = 0; k < temp.data.length; k += 1) {
      removelist = removelist.filter((item) => item.lno != temp.data[k].lno);
    }
    const tempComments = removelist.concat(temp.data);
    setlist([...tempComments]);
    setloading(false);
  };

  return (
    <Container1>
      <Overlay
        ref={commentRef}
        onClick={(e) => {
          e.stopPropagation();
          if (e.target == commentRef.current) setcommentModal(false);
        }}
      >
        <Contents>
          <div className="swiperUi">
            <ul>
              <Swiper
                className="swiperContainer"
                modules={[Navigation, Pagination, Scrollbar, A11y]}
                spaceBetween={10}
                slidesPerView={1}
                // navigation
                loop={true}
                pagination={{ clickable: true }}
                scrollbar={{ draggable: true }}
              >
                {val.photo.map((image, i) => {
                  return (
                    <SwiperSlide key={i}>
                      <li>
                        <img className="divimage1" alt="sample" src={image} />
                        {/* <img className='divimage' alt="sample" src='/images/bg1.png'/> */}
                      </li>
                    </SwiperSlide>
                  );
                })}
              </Swiper>
            </ul>
          </div>
          <div className="contents">
            <div className="feeds-settingCom">
              <div className="search-contents">
                <div className="gradient">
                  <img
                    src={val.userprofile}
                    alt="프사"
                    onError={(e) => {
                      e.target.src = "/images/user.jpg";
                    }}
                  />
                </div>
                <div>
                  <p className="user-id">
                    <strong>{val.id}</strong>
                  </p>
                  <a href={`https://map.kakao.com/?q=${val.title}`} target="_blank" >
                    <div
                      className="commentSearch_position"
                      style={{ marginLeft: "7px" }}
                    >
                      <FontAwesomeIcon icon={faLocationDot} />
                      <span style={{ fontSize: "14px" }}>{val.title}</span>
                    </div>
                  </a>
                </div>
              </div>
              <div className="dot1">
                <i
                  className="fa-solid fa-ellipsis fa-2x"
                  ref={menuRef}
                  onClick={() => setModalShow(!modalShow)}
                ></i>
                {modalShow && (
                  <MenuModal
                    setcommentModal={setcommentModal}
                    setlist={setlist}
                    setModalShow={setModalShow}
                    seteditModal={seteditModal}
                    val={val}
                  />
                )}
                {/* {
                            editModal && <Edit val={val} setlist={setlist} seteditModal={seteditModal} />
                        } */}
              </div>
            </div>
            <div className="recommend"  id="scrollDiv" style={{overflow:'auto'}} onChange={(e)=>{menuModalRef(e)}}> 
              <div className="recommend-down">
                <div className="recommendContents">
                  <img
                    className="userimg"
                    src={val.userprofile}
                    alt="프사"
                    onError={(e) => {
                      e.target.src = "/images/user.jpg";
                    }}
                  />
                  <div
                    style={{
                      display: "flex",
                      flexDirection: "column",
                      marginTop: "10px",
                      marginLeft: "10px",
                    }}
                  >
                    <div
                      style={{
                        display: "flex",
                        flexDirection: "row",
                        paddingRight: "15px",
                      }}
                    >
                      <div className="feeds-title">
                        <p>
                          <span className="userName">
                            <strong>제목 </strong>
                          </span>
                          <span style={{ fontFamily: "normal" }}>
                            {" "}
                            {val.ctitle}
                          </span>
                        </p>
                        <p className="userName">
                          <strong
                            style={{ fontSize: "13px", marginRight: "5px" }}
                          >
                            {val.id}
                          </strong>
                          <span
                            style={{
                              fontFamily: "normal",
                              whiteSpace: "pre-wrap",
                            }}
                          >
                            {val.content}
                          </span>

                          {val.tname === null
                            ? ""
                            : val.tname.map((tname, i) => {
                                return (
                                  <span style={{ color: "#333333" }} key={i}>
                                    <strong>{tname}</strong>
                                  </span>
                                );
                              })}
                        </p>
                      </div>
                    </div>
                    <div
                      style={{
                        fontSize: "10px",
                        color: "#a5a5a5",
                        marginTop: "8px",
                      }}
                    >
                      <p className="postDate">
                        {dayjs(new Date(val.postdate)).format("YYYY/MM/DD")}
                      </p>
                    </div>
                  </div>
                </div>
                {comments.map((val, i) => {
                  //feedComments에 담겨있을 댓글 값을 CommentList 컴포넌트에 담아서 가져온다
                  return (
                    <div
                      key={i}
                      className="recommendContents"
                      onClick={(e) => {
                      }}
                    >
                      <div>
                        <img
                          className="likeimg"
                          src={val.userprofile}
                          alt="추사"
                          onError={(e) => {
                            e.stopPropagation();
                            e.target.src = "/images/user.jpg";
                          }}
                        />
                      </div>
                      <div
                        style={{
                          width: "100%",
                          display: "flex",
                          flexDirection: "column",
                          marginTop: "10px",
                          marginLeft: "10px",
                        }}
                      >
                        <div style={{ display: "flex", flexDirection: "row" }}>
                          <div
                            style={{
                              display: "flex",
                              flexDirection: "row",
                              width: "90%",
                            }}
                          >
                            <p
                              className="userName"
                              style={{ fontSize: "13px" }}
                            >
                              <strong>{val.id}</strong>
                            </p>
                            <p
                              className="userName"
                              style={{ fontFamily: "normal" }}
                            >
                              {val.reply}
                            </p>
                          </div>
                          <div
                            style={{
                              fontSize: "13px",
                              alignSelf: "center",
                              padding: "15px",
                            }}
                            className="comment-heart"
                          >
                            {val.id === sessionStorage.getItem('username') &&
                            <i
                              className="fa-regular fa-trash-can "
                              onClick={() => {
                                deleteOne(val.cno);
                              }}
                            ></i>
                            }
                          </div>
                        </div>
                        <div
                          style={{
                            fontSize: "10px",
                            color: "#a5a5a5",
                            marginTop: "8px",
                          }}
                        >
                          <p className="postDate">
                            {dayjs(val.postdate).format("YYYY/MM/DD")}
                          </p>
                        </div>
                      </div>
                    </div>
                  );
                })}
              </div>
            </div>
            <div className="contentIcon">
              <div className="feeds-icons">
                <div
                  className="heart-icon"
                  onClick={(e) => {
                    e.stopPropagation();
                    fillLike(setForReRender, forReRender);
                  }}
                >
                  {val.islike ? (
                    <i
                      className="fa-solid fa-heart fa-2x"
                      style={{ color: "red" }}
                    ></i>
                  ) : (
                    <i className="fa-regular fa-heart fa-2x"></i>
                  )}
                </div>
                <div
                  className="talk-icon"
                  onClick={() => {
                    replyRef.current.focus();
                  }}
                >
                  <i className=" fa-regular fa-comment fa-2x"></i>
                </div>
                <div className="share-icon">
                  <i className="fa-regular fa-paper-plane fa-2x"></i>
                </div>
              </div>
              <div className="likeCount">
                <h3>
                  <strong>좋아요 {val.likecount}개</strong>
                </h3>
              </div>
              <div className="postDate">
                <h3>{dayjs(new Date(val.postdate)).format("YYYY/MM/DD")}</h3>
              </div>
            </div>
            <div className="commentss">
              <div className="emoji">
                <i
                  className="fa-regular fa-face-smile"
                  style={{ fontSize: "24px", left: "5px" }}
                  onClick={() => {
                    setemoji(!emoji);
                  }}
                />
              </div>
              {emoji && (
                <div
                  className="emoji-all"
                  onClick={() => {
                    setisValid(true);
                    setemoji(false);
                  }}
                >
                  <Picker onEmojiClick={onEmojiClick} />
                </div>
              )}
              <input
                type="text"
                ref={replyRef}
                className="inputComment_"
                placeholder="댓글 달기..."
                style={{ width: "80%", fontSize: "13px" }}
                // onChange={(e) => {
                //   setComment(e.target.value); //댓글 창의 상태가 변할때마다 setComment를 통해 comment값을 바꿔준다
                // }}
                onKeyUp={(e) => {
                  btn_check();
                }}
                // value={comment}
              />

              <button
                className={
                  //클래스명을 comment창의 글자 길에 따라서 다르게 주면서 버튼색에 css디자인을 줄 수 있음
                  isValid ? "submitCommentActive" : "submitCommentInactive"
                }
                onClick={() => {
                  post(replyRef);
                }} //클릭하면 위서 선언한 post함수를 실행하여 feedComments에 담겨서 re-rendering 된 댓글창을 확인할 수 있다
                disabled={isValid ? false : true} //사용자가 아무것도 입력하지 않았을 경우 게시를 할 수 없도록
                type="button"
              >
                게시
              </button>
            </div>
          </div>
        </Contents>
      </Overlay>
    </Container1>
  );
}

const Container1 = styled.div`
  position: fixed;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  z-index: 1;
  justify-content: center;
  align-items: center;
`;
const Overlay = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
  z-index: 15;
  background-color: rgba(0, 0, 0, 0.3);
`;

const Contents = styled.div`
  position: absolute;
  width: 60%;
  height: 750px;
  left: 50%;
  top: 54%;
  background: white;
  transform: translate(-50%, -50%);
  display: flex;
  flex-direction: row;
  border-radius: 7px;
  padding: 5px;
`;

export default Comment;
