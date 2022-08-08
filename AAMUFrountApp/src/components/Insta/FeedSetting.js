import styled from "styled-components";
import React, { useEffect, useMemo, useRef, useState } from "react";
import Profile from "./ProfileModal";
import Picker from "emoji-picker-react";
import Comment from "./ModalGroup/Comment/Comment";
import Slider from "react-slick";
import "./ModalGroup/Slider/slick.css";
import "./ModalGroup/Slider/slick-theme.css";
import dayjs from "dayjs";
import "react-confirm-alert/src/react-confirm-alert.css"; // Import css
import axios from "axios";
import Edit from "./ModalGroup/Edit/Edit";
import { confirmAlert } from "react-confirm-alert";
import { useDispatch, useSelector } from "react-redux";
import { addForChatInfo } from "../../redux/store";
import FeeduserModal from "./ModalGroup/FeeduserModal";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faLocationDot } from "@fortawesome/free-solid-svg-icons";
function FeedSetting({
  val,
  setlist,
  forReRender,
  setForReRender,
  showChat,
  setShowChat,
  setPrevChats,
}) {
  let profileRef = useRef();
  let replyRef = useRef();
  const [chosenEmoji, setChosenEmoji] = useState(null);

  const [feedComments, setfeedComments] = useState([]);
  const [editModal, seteditModal] = useState(false);
  const [comeditModal, setcomeditModal] = useState(false);
  const [modalShow, setModalShow] = useState(false);
  const [profileModal, setprofileModal] = useState(false);
  const [commentModal, setcommentModal] = useState(false);

  let [isValid, setisValid] = useState(false);

  const [isShowMore, setIsShowMore] = useState(false); // 더보기 열고 닫는 스위치
  const textLimit = useRef(10); // 글자수 제한 선언

  const [emoji, setemoji] = useState(false);
  const onEmojiClick = (event, emojiObject) => {
    setChosenEmoji(emojiObject);
    replyRef.current.value = emojiObject.emoji;
  };

  const commenter = useMemo(() => {
    // 조건에 따라 게시글을 보여주는 함수
    const shortReview = val.content.slice(0, textLimit.current); // 원본에서 글자 수만큼 잘라서 짧은 버전을 준비하자

    if (val.content.length > textLimit.current) {
      // 원본이 길면 (원본 글자수 > 제한된 갯수)
      if (isShowMore) {
        return val.content;
      } // 더보기가 true 면 원본 바로 리턴
      return shortReview; // (더보기가 false면) 짧은 버전 리턴해주자
    }
    return val.content; // 그렇지않으면 (짧은 글에는) 쓴글 그대로 리턴!
  }, [isShowMore]); // 얘는 isShowMore의 상태가 바뀔때마다 호출된다

  // document.getElementById('button_hint').disabled = false;

  // function uploadReply(replyUp){//이미지 업로드
  //     let formData = new FormData(); // formData 객체를 생성한다.
  //       formData.append("reply", replyUp[0]); // 반복문을 활용하여 파일들을 formData 객체에 추가한다

  //     return formData;
  //   }
  let dispatch = useDispatch();
  let reduxState = useSelector((state) => state);
  function post(replyRef) {
    //유효성 검사를 통과하고 게시버튼 클릭시 발생하는 함수
    // feedComments = val.commuComment;
    // console.log('id',sessionStorage.getItem('username'))
    // console.log('lno',val.lno)
    //    console.log('comment(11)',comment)
    // const copyFeedComments = [...feedComments];//feedComments에 담겨있던 댓글 받아옴
    // copyFeedComments.push(comment);//copyFeedComments에 있는 기존 댓글에 push하기 위함
    // setfeedComments(copyFeedComments);//copyFeedComments 담겨있을 comment를 setfeedComments로 변경

    // temp.append('id',sessionStorage.getItem('username'))
    // temp.append('reply',replyRef.current.value)
    // temp.append('lno',val.lno)
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
        console.log("resp.data.reply", resp.data.reply);
        console.log("resp.data.id", resp.data.id);
        console.log("resp.data", resp.data);
        val.commuComment = resp.data;
        // val.commuComment = resp.data.reply;
        setfeedComments(resp.data);
        setForReRender(!forReRender);
      })
      .catch((error) => {
        console.log("error", error);
      });

    replyRef.current.value = ""; //사용자 댓글창을 빈 댓글 창으로 초기화
  }

  const settings = {
    //이미지 슬라이드
    dots: true,
    infinite: false,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
  };

  let CommentList = ({ val }) => {
    return (
      <div className="writing">
        <span className="id">{val.commuComment === null ? null : val.commuComment.id}</span>
        <span>{val.commuComment === null ? null : val.commuComment.reply}</span>
      </div>
    );
  };

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
        console.log(resp.data);
        val.islike = resp.data.isLike;
        val.likecount = resp.data.likecount;
        setForReRender(!forReRender);
      })
      .catch((error) => {
        console.log(error);
      });
  }

  return (
    <div>
      <div className="feeds-setting">
        <div className="title-profile">
          <img
            src={val.userprofile}
            alt="프사"
            onError={(e) => {
              e.stopPropagation();
              e.target.src = "/images/user.jpg";
            }}
          />
          <span
            ref={profileRef}
            className="profileSpan"
            onMouseOver={() => {
              setprofileModal(true);
            }}
          >
            {val.id}
          </span>
          <div className="profile" style={{ position: "relative" }}>
            {profileModal && (
              <div
                ref={profileRef}
                onMouseOver={(e) => {
                  setprofileModal(true);
                }}
                onMouseOut={(e) => {
                  setprofileModal(false);
                }}
              >
                <Profile val={val}></Profile>
              </div>
            )}
          </div>
          <div className="dot">
            <i className="fa-solid fa-ellipsis fa-2x" onClick={() => setModalShow(!modalShow)}></i>
            {modalShow && (
              <FeeduserModal
                setlist={setlist}
                setModalShow={setModalShow}
                seteditModal={seteditModal}
                val={val}
              />
            )}
            {editModal && <Edit val={val} setlist={setlist} seteditModal={seteditModal} />}
          </div>
        </div>
        <a href={`https://map.kakao.com/?q=${val.title}`}>
          <div className="location">
            <FontAwesomeIcon icon={faLocationDot} />
            <span style={{ fontSize: "14px" }}>{val.title}</span>
          </div>
        </a>
        <Slider {...settings}>
          {val.photo.map((image, i) => (
            <div className="container">
              <img
                src={image}
                // setMyImage={setMyImage}
                className="main-image"
                alt="메인"
              />
            </div>
          ))}
        </Slider>
        <div className="feeds-contents">
          <div className="feeds-icons">
            <div
              className="heart-icon"
              onClick={(e) => {
                e.stopPropagation();
                fillLike(setForReRender, forReRender);
              }}
            >
              {val.islike ? (
                <i className="fa-solid fa-heart fa-2x" style={{ color: "red" }}></i>
              ) : (
                <i className="fa-regular fa-heart fa-2x"></i>
              )}
            </div>
            <div>
              <div className="talk-icon">
                <i
                  className="fa-regular fa-comment fa-2x"
                  onClick={() => {
                    setcommentModal(!commentModal);
                  }}
                ></i>
              </div>
              {commentModal && (
                // <Container1>
                //   <Overlay
                // ref={commentRef}
                // onClick={(e) => {
                //   e.stopPropagation();
                //   if (e.target == commentRef.current) setcommentModal(false);
                // }}
                // >
                <Comment
                  onClick={() => {
                    setcommentModal(false);
                  }}
                  setcommentModal={setcommentModal}
                  seteditModal={seteditModal}
                  val={val}
                  forReRender={forReRender}
                  setForReRender={setForReRender}
                  setlist={setlist}
                />
                //   </Overlay>
                // </Container1>
              )}
              {comeditModal && <Edit val={val} setlist={setlist} seteditModal={seteditModal} />}
            </div>
            <div
              className="share-icon"
              onClick={() => {
                getChatRoom(val, dispatch, setPrevChats);
                setTimeout(() => {
                  setShowChat(!showChat);
                }, 100);
              }}
            >
              <i className="fa-regular fa-paper-plane fa-2x"></i>
            </div>
          </div>
          <div className="feeds-like">
            <span>
              <span className="like-bold">
                <strong>좋아요</strong>
              </span>
              <span className="like-bold"> {val.likecount}개</span>
            </span>
          </div>
          <div className="feeds-title">
            <p>
              <span className="userName">제목 </span>
              <span> {val.ctitle}</span>
            </p>
            <p className="userName">
              <strong style={{ fontSize: "13px", marginRight: "5px" }}>{val.id}</strong>
              <span style={{ fontFamily: "normal", whiteSpace: "pre-wrap" }}>
                {/* {val.content} */}
                <span>{commenter}</span>
                {/* // 여기에 (짧은거나 원본) 글 내용이 들어가고  */}

                <span
                  style={{ color: "gray", cursor: "pointer" }}
                  onClick={() => setIsShowMore(!isShowMore)}
                >
                  {" "}
                  {/* //클릭시 토글로 상태를 변경해주자 */}
                  {val.content.length > textLimit.current && // 버튼명은 조건에 따라 달라진다
                    (isShowMore ? "닫기" : "...더보기")}
                </span>
              </span>
            </p>
            {val.tname === null
              ? ""
              : val.tname.map((tname, i) => {
                  return <span key={i}>{tname}</span>;
                })}
          </div>

          {<CommentList val={val} />}
          <div className="feeds-comment"></div>
          <span className="time">
            <p>{dayjs(new Date(val.postdate)).format("YYYY/MM/DD")}</p>
          </span>
        </div>
        <div className="comment">
          <div className="emoji">
            <i
              class="fa-regular fa-face-smile"
              style={{ fontSize: "24px", left: "5px" }}
              onClick={() => {
                setemoji(!emoji);
              }}
            />
          </div>
          {emoji && (
            <div className="emoji-all">
              <Picker
                onEmojiClick={onEmojiClick}
                onClick={() => {
                  setemoji(!emoji);
                }}
              />
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
              e.target.value.length > 0 //사용자가 키를 눌렀다 떼었을때 길이가 0을 넘는 값인지 유효성 검사 결과 값을 담는다
                ? setisValid(true)
                : setisValid(false);
              // console.log(replyRef.current.value.length>0?'true':'false');
            }}
            // value={comment}
          />

          <button
            className={
              //클래스명을 comment창의 글자 길에 따라서 다르게 주면서 버튼색에 css디자인을 줄 수 있음
              replyRef.current === undefined
                ? null
                : replyRef.current.value.length > 0
                ? "submitCommentActive"
                : "submitCommentInactive"
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
    </div>
  );
}
async function getChatRoom(val, dispatch, setPrevChats) {
  await axios
    .post("/aamurest/chat/room?fromid=" + sessionStorage.getItem("username") + "&toid=" + val.id)
    .then((resp) => {
      setPrevChats(resp.data.list);
      dispatch(addForChatInfo({ ...resp.data, id: val.id }));
    })
    .catch((err) => {
      console.log(err);
    });
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
  background-color: rgba(0, 0, 0, 0.6);
`;
export default FeedSetting;
