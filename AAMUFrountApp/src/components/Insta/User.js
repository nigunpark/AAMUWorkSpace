import styled from 'styled-components'
import React, { useState, useRef } from 'react'
import WriteModal from './ModalGroup/WriteModal';
import SearchModal from './ModalGroup/Search';
import Notification from './ModalGroup/Notification';


function User() {
    const [heart,setHeart] = useState(false);
    const [follow,setFollowing] = useState(false);
    const [square,setsquare] = useState(false);

    const [menuBtnClick, setMenuBtnClick] = useState(false);
    const outSection = useRef();
    
  return (
    <div>        
        <div  className="search">        
            <div onClick={()=>{setMenuBtnClick(!menuBtnClick)}} className='entre'>
                <input type="text" className="search-bar" placeholder="검색" />  
                {menuBtnClick ? (
                     <Container ref={outSection} onClick={(e)=>{
                        if(outSection.current === e.target) {
                            setMenuBtnClick(false)
                        }
                        }}>  
                        <SearchModal/>
                        </Container>):null}    
            </div>  
        </div>
        <div className="user">
            <img src="./img/bk.jpg" alt="사프" />
            <div>
                <p className="user-id">0hyun0hyun</p>
                <p className="user-name">김영현</p>
            </div>
            <div className='notifi'>
                <div className='heart'>
                    {heart ?<i class="fa-solid fa-heart fa-2x" onClick={()=>{setHeart(!heart)}} style={{color:'black'}} ></i>
                    :<i class="fa-regular fa-heart fa-2x" onClick={()=>{setHeart(!heart)}}></i>}
                    {heart ? <Notification></Notification> : null}
                </div>
            </div>
            <div className="post-icon">
            {square ?<i class="fa-solid fa-square-plus fa-2x" onClick={()=>{setsquare(!square)}} style={{color:'black'}} />
                :<i class="fa-regular fa-square-plus fa-2x"  onClick={()=>{setsquare(!square)}}/>}
                 
            </div>
            {square ?
                <WriteModal></WriteModal>
                :null}
                
        </div>
        
        <div className="recommend">
            <div className="recommend-title">
                <span>회원님을 위한 추천</span>
                <span>모두 보기</span>
            </div>
            <div className="recommend-down">
                <div className="recommend-contents">
                    <img src="./img/bk.jpg" alt="추사" />
                    <div>
                        <p className="user-id">psg</p>
                        <p className="user-name">0hyun0hyun님 외 2명이...</p>
                    </div>
                    <div className="follow">
                        {follow ?  <span className='following' onClick={()=>{setFollowing(!follow)}}>팔로잉</span> : <span onClick={()=>{setFollowing(!follow)}}>팔로우</span>}
                    </div>
                </div>
                <div className="recommend-contents">
                    <img src="./img/bk.jpg" alt="추사" />
                    <div>
                        <p className="user-id">manchesterun</p>
                        <p className="user-name">0hyun0hyun님 외 2명이...</p>
                    </div>
                    <div>
                        <span className="follow">팔로우</span>
                    </div>
                </div>
                <div className="recommend-contents">
                    <img src="./img/bk.jpg" alt="추사" />
                    <div>
                        <p className="user-id">jeenny</p>
                        <p className="user-name">0hyun0hyun님 외 2명이...</p>
                    </div>
                    <div>
                        <span className="follow">팔로우</span>
                    </div>
                </div>
                <div className="recommend-contents">
                    <img src="./img/bk.jpg" alt="추사" />
                    <div>
                        <p className="user-id">0hyun0hyun</p>
                        <p className="user-name">0hyun0hyun님 외 2명이...</p>
                    </div>
                    <div>
                        <span className="follow">팔로우</span>
                    </div>
                </div>
                <div className="recommend-contents">
                    <img src="./img/bk.jpg" alt="추사" />
                    <div>
                        <p className="user-id">0hyun0hyun</p>
                        <p className="user-name">0hyun0hyun님 외 2명이...</p>
                    </div>
                    <div>
                        <span className="follow">팔로우</span>
                    </div>
                </div>
                <div className="recommend-contents">
                    <img src="./img/bk.jpg" alt="추사" />
                    <div>
                        <p className="user-id">0hyun0hyun</p>
                        <p className="user-name">0hyun0hyun님 외 2명이...</p>
                    </div>
                    <div>
                        <span className="follow">팔로우</span>
                    </div>
                </div>
            </div>    
        </div>
        <div className="information">
            <p> &nbsp;&nbsp;About ・ Help ・ Press ・ API ・ Jobs ・ Privacy </p>     
            <p>   &nbsp;・ Terms ・ Locations ・ Language</p>   
        </div>
        <div className="information2">
            <p>ⓒ 2022 INSTAGRAM</p>
        </div>
    </div> 
  )
}

const Container = styled.div`
    position: fixed;
    width: 100%;
    height: 100%;
    z-index: 10;
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
    display: grid;
`

export default User

