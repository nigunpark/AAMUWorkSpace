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
                <img src="img/v.jpg" alt="오류나면 나오는 메시지"/>

            </div>
            <div className="contents">
                <div className="feeds-settingCom">  
                    <div className="search-contents">
                        <div className="gradient">
                            <img src="img/b.jpg" alt="스토리 프로필 사진" />
                        </div>
                        <div>
                            <p className="user-id"><strong>jenny0305</strong></p>
                        </div>
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
                                <p className="userName">내용</p>
                                <p className="userName">태그</p>
                                <p className="userName">등록일</p>
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
                                <p className="userName"><big>0hyun0hyun</big>님이 댓글을 달았습니다</p>
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
                                <p className="userName"><big>0hyun0hyun</big>님이 댓글을 달았습니다</p>
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
                                <p className="userName"><big>0hyun0hyun</big>님이 댓글을 달았습니다</p>
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
                                <p className="userName"><big>0hyun0hyun</big>님이 댓글을 달았습니다</p>
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
                                <p className="userName"><big>0hyun0hyun</big>님이 댓글을 달았습니다</p>
                            </div>
                        </div>
                    </div>    
                </div>
                <div className="contentIcon">
                    <div className="feeds-icons">
                        <div className='heart-icon'>
                            {heart ?<i class="fa-solid fa-heart fa-2x"onClick={()=>{setHeart(!heart)}} style={{color:'red'}} />
                            :<i className="fa-regular fa-heart fa-2x"  onClick={()=>{setHeart(!heart)}}></i>}
                        </div>
                        <div className="talk-icon">
                            <i className=" fa-regular fa-comment fa-2x"></i>
                        </div>
                        <div className="share-icon">
                        <i className="fa-regular fa-paper-plane fa-2x"></i>
                        </div >
                    </div>
                    <div className="likeCount">
                        <h3><strong>좋아요 1,222개</strong></h3>
                    </div>
                    <div className="postDate">
                        <h3>17시간 전</h3>
                    </div>
                </div>
                <div className="comment">
                    <input type="text" className="typing-comment" placeholder="댓글 달기..."/>
                    <button className="comment-button" type="button">게시</button>
                </div>
            </div>             
        </Contents>
    </ModalWrap>
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
    margin-right:30px;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.6);
`

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