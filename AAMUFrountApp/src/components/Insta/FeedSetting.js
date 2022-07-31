import styled from "styled-components";
import React, { useEffect, useRef, useState } from "react";
import Profile from "./ModalGroup/Profile";
import MenuModal from "./ModalGroup/MenuModal";
import Comment from "./ModalGroup/Comment/Comment";
import Slider from "react-slick";
import "./ModalGroup/Slider/slick.css";
import "./ModalGroup/Slider/slick-theme.css";
import dayjs from "dayjs";
import "react-confirm-alert/src/react-confirm-alert.css"; // Import css
import axios from "axios";
import Edit from "./ModalGroup/Edit/Edit";
import { confirmAlert } from "react-confirm-alert";
import { useDispatch } from "react-redux";
import { addForChatInfo } from "../../redux/store";
function FeedSetting({
  val,
  setlist,
  forReRender,
  setForReRender,
  setloading,
  showChat,
  setShowChat,
}) {
  let profileRef = useRef();

  let replyRef = useRef();
  let editRef = useRef();
  const [editModal, seteditModal] = useState(false);
  const [comeditModal, setcomeditModal] = useState(false);
  const [modalShow, setModalShow] = useState(false);
  const [profileModal, setprofileModal] = useState(false);
  const [commentModal, setcommentModal] = useState(false);
  let [userName] = useState("hacker");
  let [comment, setComment] = useState("");
  let [feedComments, setfeedComments] = useState([]);
  let [isValid, setisValid] = useState(false);
  // function uploadReply(replyUp){//이미지 업로드
  //     let formData = new FormData(); // formData 객체를 생성한다.
  //       formData.append("reply", replyUp[0]); // 반복문을 활용하여 파일들을 formData 객체에 추가한다

  //     return formData;
  //   }
  let dispatch = useDispatch();
  function post(comment, setfeedComments) {
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
          reply: comment,
          lno: val.lno,
        },
        {
          id: sessionStorage.getItem("username"),
          reply: comment,
          lno: val.lno,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      )
      .then((resp) => {
        console.log("resp.data", resp.data);
        setfeedComments(resp.data);
        val.commuComment.reply = resp.data.reply;
        val.commuComment.id = resp.data.id;
      })
      .catch((error) => {
        console.log(error);
      });

    setComment(""); //사용자 댓글창을 빈 댓글 창으로 초기화
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
        <span className="id">{val.commuComment == null ? null : val.commuComment.id}</span>
        <span>{val.commuComment == null ? null : val.commuComment.reply}</span>
      </div>
    );
  };

  let heartRef = useRef();
  let [fHeart, setfHeart] = useState([]);

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
            src="/images/user.jpg"
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
                onMouseOver={() => {
                  setprofileModal(true);
                }}
                onMouseOut={() => {
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
              <MenuModal
                setlist={setlist}
                setModalShow={setModalShow}
                seteditModal={seteditModal}
                val={val}
              />
            )}
            {editModal && <Edit val={val} setlist={setlist} seteditModal={seteditModal} />}
          </div>
        </div>
        <div className="location">
          <p>{val.title}</p>
        </div>
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
                getChatRoom(val, dispatch);
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
            <span className="userName">제목 </span>
            <span>{val.ctitle}</span>
          </div>
          <div className="feeds-writing">
            <span className="id">{val.id}</span>
            <span>{val.content}</span>
          </div>
          {<CommentList val={val} />}
          <div className="feeds-comment"></div>
          <span className="time">
            <p>{dayjs(new Date(val.postdate)).format("YYYY/MM/DD")}</p>
          </span>
        </div>
        <div className="comment">
          <input
            type="text"
            ref={replyRef}
            className="inputComment"
            placeholder="댓글 달기..."
            onChange={(e) => {
              e.stopPropagation();
              setComment(e.target.value); //댓글 창의 상태가 변할때마다 setComment를 통해 comment값을 바꿔준다
            }}
            onKeyUp={(e) => {
              e.stopPropagation();
              e.target.value.length > 0 //사용자가 키를 눌렀다 떼었을때 길이가 0을 넘는 값인지 유효성 검사 결과 값을 담는다
                ? setisValid(true)
                : setisValid(false);
            }}
            value={comment}
          />

          <button
            className={
              //클래스명을 comment창의 글자 길에 따라서 다르게 주면서 버튼색에 css디자인을 줄 수 있음
              comment.length > 0 ? "submitCommentActive" : "submitCommentInactive"
            }
            onClick={() => {
              post(comment, setfeedComments);
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
async function getChatRoom(val, dispatch) {
  await axios
    .post("/aamurest/chat/room?fromid=" + sessionStorage.getItem("username") + "&toid=" + val.id)
    .then((resp) => {
      console.log("resp.data", resp.data);
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
