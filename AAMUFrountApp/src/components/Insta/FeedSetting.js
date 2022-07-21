import styled from "styled-components";
import React, {  useEffect, useRef, useState } from 'react'
import Profile from './ModalGroup/Profile';
import MenuModal from './ModalGroup/MenuModal';
import Comment from './ModalGroup/Comment/Comment';
import Slider from "react-slick";
import "./ModalGroup/Slider/slick.css";
import "./ModalGroup/Slider/slick-theme.css";
import dayjs from 'dayjs';
import { confirmAlert } from 'react-confirm-alert'; // Import
import 'react-confirm-alert/src/react-confirm-alert.css'; // Import css

function FeedSetting({val,setMyImage}) {
    let menuRef = useRef();
    let profileRef = useRef();
    let commentRef = useRef();
    const [heart,setHeart] = useState(false);
   
    const [modalShow, setModalShow] = useState(false);
    const [profileModal, setprofileModal] = useState(false);
    const [commentModal, setcommentModal] = useState(false);

    let [userName] = useState('hacker');
    let [comment, setComment] = useState('');
    let [feedComments, setfeedComments] = useState([]); 
    let [isValid, setisValid] = useState(false); 

    let post = e => {//유효성 검사를 통과하고 게시버튼 클릭시 발생하는 함수
        const copyFeedComments = [...feedComments];//feedComments에 담겨있던 댓글 받아옴
        copyFeedComments.push(comment);//copyFeedComments에 있는 기존 댓글에 push하기 위함 
        setfeedComments(copyFeedComments);//copyFeedComments 담겨있을 comment를 setfeedComments로 변경
        setComment('');//사용자 댓글창을 빈 댓글 창으로 초기화
    };


    const settings = {//이미지 슬라이드
        dots: true,
        infinite: false,
        speed: 500,
        slidesToShow: 1,
        slidesToScroll: 1
      };
      

    function menuModalRef(e){//메뉴 모달 나왔을때 주변 눌러도 꺼지게 만들기
        e.stopPropagation();
        if (e.target != menuRef.current) setModalShow(false);
    }
    
    window.addEventListener("click", menuModalRef);
    
    const CommentList = props => {
        return(
            <div className = 'userCommentBox'>
                <p className="userName">{props.userName}</p>
                <div className="userCommnet">{props.userComment}</div>
                <p className="userHeart">
                    {/* <FaHeart/> */}
                </p>
            </div>
        )
    }
   
  return (
<div>
    <div className="feeds-setting">  
    <div className="title-profile">
        <img src="./img/bk.jpg" alt="프사" />                
        <span
            ref={profileRef}
            className="profileSpan"
            onMouseOver={()=>{setprofileModal(true)}}
            >
                {val.id}
        </span> 
        <div className="profile" style={{position : 'relative'}}>          
            {profileModal ? 
                <div 
                ref={profileRef}
                onMouseOver={()=>{setprofileModal(true)}}
                onMouseOut={()=>{setprofileModal(false)}}>
                    <Profile 
                    val={val}
                    ></Profile>
                </div>
            : null
            }         
        </div>
        <div className="dot">
            <i className="fa-solid fa-ellipsis fa-2x"
            ref={menuRef}
            onClick={() => setModalShow(!modalShow)}></i>
            {modalShow 
                ?<MenuModal></MenuModal>
                :null}
                
        </div>  
    </div>
    <div className="location">
        <p>{val.title}</p>
    </div>
        <Slider {...settings}>
            {val.photo.map((image,i)=>(
                <div className="container">
                    <img src={image}
                    // setMyImage={setMyImage}
                        className="main-image" 
                        alt="메인" />
                </div> 
            ))}
        </Slider>

    <div className="feeds-contents">
        <div className="feeds-icons">
            <div className='heart-icon'>
                {val.islike ?<i className="fa-solid fa-heart fa-2x"onClick={()=>{}} style={{color:'red'}} />
                :<i className="fa-regular fa-heart fa-2x"  onClick={()=>{}}></i>}
            </div>
            <div>
                <div className="talk-icon">
                    <i className="fa-regular fa-comment fa-2x"
                    onClick={()=>{setcommentModal(!commentModal)}}></i>
                </div>
                {commentModal ?
                <Container1>
                    <Overlay
                    ref={commentRef} 
                    onClick={ (e) => { if(e.target == commentRef.current) setcommentModal(false)  }}
                    >
                        <Comment onClick={ () => setcommentModal(false)} val={val}></Comment>
                    </Overlay>
                </Container1>
                :null}
            </div>
            <div className="share-icon">
            <i className="fa-regular fa-paper-plane fa-2x"></i>
            </div >
            {/* <svg className="save-icon" viewBox="0 0 48.065 48.065">
                <path d="M40.908,0H7.158c-0.553,0-1,0.448-1,1v46.065c0,0.401,0.239,0.763,0.608,0.92c0.369,0.157,0.797,0.078,1.085-0.2
                l16.182-15.582l16.182,15.582c0.189,0.183,0.439,0.28,0.693,0.28c0.132,0,0.266-0.026,0.392-0.08c0.369-0.157,0.608-0.52,0.608-0.92
                V1C41.908,0.448,41.46,0,40.908,0z M39.908,44.714L24.726,30.095c-0.193-0.187-0.443-0.28-0.693-0.28s-0.5,0.093-0.693,0.28
                L8.158,44.714V2h31.75V44.714z"/>
            </svg> */}
        </div>
        <div className="feeds-like">
            <span><span className="like-bold"><strong>좋아요</strong></span>
            <span className="like-bold"> {val.likecount}명</span>이 좋아합니다</span>
        </div>
        <div className="feeds-title">
            <span className="comment-id">제목 : </span>
            <span>
                {val.ctitle}
                </span>
        </div>
        <div className="feeds-writing">
            <span className="comment-id">
                {val.id} 
                </span>
                <span> 
                    {val.content}
                </span>
        </div>
        <div className="feeds-writing">
            <span className="comment-id">
                {val.commuComment==null?"":val.commuComment.id}
                </span>
            <span>
                {val.commuComment==null?"":val.commuComment.reply}
                </span>
        </div>
        {
            feedComments.map((commentArr,i)=>{//feedComments에 담겨있을 댓글 값을 CommentList 컴포넌트에 담아서 가져온다
                return(
                    <CommentList//CommentList 컴포넌트는 반복적으로 추가되는 사용자 댓글 하나하나를 담고있는 박스
                        userName={userName}//userName에는 위에서 'hacker'를 담은 값을 userComment에는 feedComments에 담겨있는 댓글 배열을 담는다
                        userComment={commentArr}
                        key={i}
                    />
                );
            })
        }
        <div className="feeds-comment">
        </div>
        <span className="time">
           <p>{dayjs(new Date(val.postdate)).format('YYYY/MM/DD')}</p>
        </span>    
    </div>
    <div className="comment">
        <input type="text"
                className="inputComment" 
                placeholder="댓글 달기..."
                onChange={(e)=>{
                    setComment(e.target.value);//댓글 창의 상태가 변할때마다 setComment를 통해 comment값을 바꿔준다
                }}
                onKeyUp={(e)=>{
                    e.target.value.length > 0//사용자가 키를 눌렀다 떼었을때 길이가 0을 넘는 값인지 유효성 검사 결과 값을 담는다
                    ? setisValid(true)
                    : setisValid(false);
                }}
                value={comment}
                />

        <button 
                className={//클래스명을 comment창의 글자 길에 따라서 다르게 주면서 버튼색에 css디자인을 줄 수 있음
                    comment.length > 0
                    ? 'submitCommentActive'
                    : 'submitCommentInactive'
                }
                onClick={post}//클릭하면 위서 선언한 post함수를 실행하여 feedComments에 담겨서 re-rendering 된 댓글창을 확인할 수 있다
                disabled={isValid ? false : true}//사용자가 아무것도 입력하지 않았을 경우 게시를 할 수 없도록
                type="button" >
                    게시
        </button>
       
    </div>
    </div> 

    </div> 
  )
  
}

const Container1 = styled.div`
position: fixed;
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
`
const Overlay = styled.div`
position: relative;
width: 100%;
height: 100%;
background-color: rgba(0, 0, 0, 0.6);
`
export default FeedSetting
