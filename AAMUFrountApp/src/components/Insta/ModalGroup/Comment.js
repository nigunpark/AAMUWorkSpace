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
    const [commentHeart, setCommentHeart] = useState(false);
    
    function menuModalRef(e){
        e.stopPropagation();
        if (e.target != menuRef.current) setModalShow(false);
    }
    window.addEventListener("click", menuModalRef);


  return (
    // <ModalWrap>
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
                            <img className='userimg' src="./img/bk.jpg" alt="추사" />
                            <div style={{display:'flex',flexDirection:'column',marginTop:'10px',marginLeft:'10px'}}>
                                <div style={{display:'flex',flexDirection:'row',paddingRight:'15px'}}>
                                    <p className="userName"><strong>0hyun0hyun</strong>
                                                            퍼먹다 순삭한다는☕아포카토 맛집총정리16☕
                                                            달달 쌉싸름 조합인데 말모말모ㅠ
                                                            @@나랑 카페투어 갈 너 소환😚
                                                            .
                                                            .
                                                            #미니마이즈 #그레이트커피 #몰또 #트라인커피
                                                             #이치서울 #디퍼카페테리아 #쿰베오 #드로우에스프레소바 
                                                             #타우니에스프레소바 #에스프레소부티크 #파이오니어커피 
                                                             #롤링브루잉 #커피매터스 #어커성수 #카페코인 #아워레스프 
                                                             #아포카토맛집 #아포카토 #커피맛집 #에스프레소바 #에스프레소 
                                                             #오먹_맛집총정리 #일반 #포</p>
                                    
                                </div>
                                <div style={{fontSize:'10px',color:'#a5a5a5',marginTop:'8px'}}>
                                    <p className="postDate">등록일</p>
                                </div>
                            </div>
                        </div>

                        <div className="recommend-contents">
                            <img className='likeimg' src="./img/bk.jpg" alt="추사" />
                            <div style={{width:'100%',display:'flex',flexDirection:'column',marginTop:'10px',marginLeft:'10px'}}>
                                <div style={{display:'flex',flexDirection:'row'}}>
                                    <p className="userName"><strong>0hyun0hyun</strong></p>
                                    <p className="userName">풍경사진 잘 보고 갑니다</p>
                                </div>
                                <div className="comment-heart">
                                    {commentHeart ?<i className="fa-solid fa-heart"onClick={()=>{setCommentHeart(!commentHeart)}} style={{color:'red'}} />
                                    :<i className="fa-regular fa-heart"  onClick={()=>{setCommentHeart(!commentHeart)}}></i>}
                                    <i class="fa-regular fa-trash-can"></i>
                                </div>
                                <div style={{fontSize:'10px',color:'#a5a5a5',marginTop:'8px'}}>
                                    <p className="postDate">등록일</p>
                                </div>
                            </div>
                        </div>
                       
                    </div>    
                </div>
                <div className="contentIcon">
                    <div className="feeds-icons">
                        <div className='heart-icon'>
                            {heart ?<i className="fa-solid fa-heart fa-2x"onClick={()=>{setHeart(!heart)}} style={{color:'red'}} />
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
     //</ModalWrap>
  )
}


// const ModalWrap = styled.div`
//     width: 78%;
//     overflow: hidden;
//     height: 90%;
//     border-radius: 5px;
//     background-color: #fff;
//     position: absolute;
//     justify-content: center;
//     align-items: center;
//     top: 50%;
//     left: 50%;
//     transform: translate(-50%, -50%);
//     //border: solid 5px black;
// `

const Contents = styled.div`
    position: absolute;
    width: 70%;
    height: 800px;
    left: 50%;
    top: 50%;
    background :white;
    transform: translate(-50%,-50%);
    display:flex;
    flex-direction:row;
    border-radius:7px;
`


export default Comment