import styled from "styled-components";
import React, {  useRef, useState } from 'react'
import Profile from './Profile';
import MenuModal from './MenuModal';

function Comment() {
    let menuRef = useRef();
    let profileRef = useRef();
    let commentRef = useRef();
    const [heart,setHeart] = useState(false);
    const [modalShow, setModalShow] = useState(false);
    const [profileModal, setprofileModal] = useState(false);
    const [commentModal, setcommentModal] = useState(false);
    
    function menuModalRef(e){
        e.stopPropagation();
        if (e.target != menuRef.current) setModalShow(false);
    }
    window.addEventListener("click", menuModalRef);


  return (
    <ModalWrap>
        <Contents> 
            <div className="previewPic">


            </div>
            <div className="contents">
                <div className="feeds-settingCom">  
                    <div className="gradient">
                        <img className='imgprofile' src="img/b.jpg" alt="스토리 프로필 사진" />
                    </div>
                    <div className="dot1">
                        <i className="fa-solid fa-ellipsis fa-2x"
                        ref={menuRef}
                        onClick={() => setModalShow(!modalShow)}></i>
                        {modalShow 
                            ?<MenuModal></MenuModal>
                            :null}
                    </div> 
                </div>
                <div className="recommend">                    
                            <div className="recommend-down">
                                <div className="recommend-contents">
                                    <img className='likeimg' src="./img/bk.jpg" alt="추사" />
                                    <div>
                                        <p className="userName"><big>0hyun0hyun</big>님이 좋아요를 눌렀습니다</p>
                                    </div>
                                </div>
                                <div className="recommend-contents">
                                    <img className='likeimg' src="./img/bk.jpg" alt="추사" />
                                    <div>
                                        <p className="userName"><big>0hyun0hyun</big>님이 댓글을 달았습니다</p>
                                    </div>
                                </div>
                                <div className="recommend-contents">
                                <img className='likeimg' src="./img/bk.jpg" alt="추사" />
                                    <div>
                                        <p className="userName"><big>0hyun0hyun</big>님이 좋아요를 눌렀습니다</p>
                                    </div>
                                </div>
                                <div className="recommend-contents">
                                <img className='likeimg' src="./img/bk.jpg" alt="추사" />
                                    <div>
                                        <p className="userName"><big>0hyun0hyun</big>님이 댓글을 달았습니다</p>
                                    </div>
                                </div>
                                <div className="recommend-contents">
                                <img className='likeimg' src="./img/bk.jpg" alt="추사" />
                                    <div>
                                        <p className="userName"><big>0hyun0hyun</big>님이 좋아요를 눌렀습니다</p>
                                    </div>
                                </div>
                                <div className="recommend-contents">
                                <img className='likeimg' src="./img/bk.jpg" alt="추사" />
                                    <div>
                                        <p className="userName"><big>0hyun0hyun</big>님이 댓글을 달았습니다</p>
                                    </div>
                                </div>
                            </div>    
                        </div>
            </div>             
        </Contents>
    </ModalWrap>
  )
}

const ModalWrap = styled.div`
    width: 78%;
    overflow: hidden;
    height: 90%;
    border-radius: 5px;
    background-color: #fff;
    position: absolute;
    justify-content: center;
    align-items: center;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    //border: solid 5px black;
`

const Contents = styled.div`
    display: flex;
    flex-direction: row;
    width: 100%;
    height: 100%;
    align-items: center;
    
    h1{
        font-size: 30px;
        font-weight: 600;
    }
    img{
        margin-top: 10px;
        width: 100%;
    }
    input{
        width: 100%;
        font-size: 20px;
    }
`


export default Comment