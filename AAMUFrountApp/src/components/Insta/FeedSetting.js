import styled from "styled-components";
import React, {  useEffect, useRef, useState } from 'react'
import Profile from './ModalGroup/Profile';
import MenuModal from './ModalGroup/MenuModal';
import Comment from './ModalGroup/Comment';
import Slider from "react-slick";
import "./ModalGroup/slick-theme.css";
import "./ModalGroup/slick.css";
import axios from "axios";

function FeedSetting({val}) {
    let menuRef = useRef();
    let profileRef = useRef();
    let commentRef = useRef();
    const [heart,setHeart] = useState(false);
   
    const [modalShow, setModalShow] = useState(false);
    const [profileModal, setprofileModal] = useState(false);
    const [commentModal, setcommentModal] = useState(false);

    const settings = {
        dots: true,
        infinite: false,
        speed: 500,
        slidesToShow: 1,
        slidesToScroll: 1
      };
      

    function menuModalRef(e){
        e.stopPropagation();
        if (e.target != menuRef.current) setModalShow(false);
    }
    
    window.addEventListener("click", menuModalRef);
    


  return (
<div>
    <div className="feeds-setting">  
    <div className="title-profile">
        <img src="./img/bk.jpg" alt="프사" />                
        <span
        ref={profileRef}
        className="profileSpan"
        onMouseOver={()=>{setprofileModal(true)}}
        >{val.id}</span> 
        <div className="profile" style={{position : 'relative'}}>          
            {profileModal ? 
                <div 
                ref={profileRef}
                onMouseOver={()=>{setprofileModal(true)}}
                onMouseOut={()=>{setprofileModal(false)}}>
                    <Profile val={val}></Profile>
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
            <div className="container">
            <img src={val.photo[0]} className="main-image" alt="메인" />
            </div>
            
        </Slider>

    <div className="feeds-contents">
        <div className="feeds-icons">
            <div className='heart-icon'>
                {heart ?<i className="fa-solid fa-heart fa-2x"onClick={()=>{setHeart(false)}} style={{color:'red'}} />
                :<i className="fa-regular fa-heart fa-2x"  onClick={()=>{setHeart(true)}}></i>}
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
                    onClick={ (e) => { if(e.target == commentRef.current) setcommentModal(false) } }
                    >
                        <Comment onClick={ () => setcommentModal(false) }></Comment>
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
            <img src="./img/bk.jpg" className="feeds-commentimage" alt="프사" />
            <span><span className="like-bold">0hyun0hyun</span>님<span className="like-bold">외 {val.likecount}명</span>이 좋아합니다</span>
        </div>
        <div className="feeds-title">
            <span className="comment-id">제목 : </span><span>{val.ctitle}</span>
        </div>
        <div className="feeds-writing">
            <span className="comment-id">{val.id} </span><span> {val.content}</span>
        </div>
        <div className="feeds-writing">
            <span className="comment-id">{val.commuComment==null?"":val.commuComment.id}</span>
            <span>{val.commuComment==null?"":val.commuComment.reply}</span>
        </div>
        <div className="feeds-comment">
        </div>
        <span className="time">{val.postdate}</span>    
    </div>
    <div className="comment">
        <input type="text" className="typing-comment" placeholder="댓글 달기..."/>
        <button className="comment-button" type="button">게시</button>
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
    flex-direction: column;
    justify-content: center;
    align-items: center;
`
const Overlay = styled.div`
    position: absolute;
    width: 100%;
    height: 100%;
    overflow:hidden;
    background-color: rgba(0, 0, 0, 0.6);
`
export default FeedSetting
