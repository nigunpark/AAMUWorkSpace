import { faSquarePlus } from '@fortawesome/free-regular-svg-icons'
import { faHeart } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import React, { useState } from 'react'
import NotificationModal from './ModalGroup/NotificationModal';
import SearchModal from './Search';


function User() {
    const [heart,setHeart] = useState(false);
    const [follow,setFollowing] = useState(false);
    const [search,setsearch] = useState(false);
    
  return (
    <div >
        <div className="search" onClick={()=>{setsearch(!search)}}>
            <input type="text" className="search-bar" placeholder="검색" />            
            {search ? <SearchModal></SearchModal> : null}              
        </div>
        <div className="user">
            <img src="./img/bk.jpg" alt="사프" />
            <div>
                <p className="user-id">0hyun0hyun</p>
                <p className="user-name">김영현</p>
            </div>
            <div className='heart'>
                {heart ?<i class="fa-solid fa-heart fa-2x"onClick={()=>{setHeart(!heart)}} style={{color:'black'}} >
                <NotificationModal></NotificationModal></i>
                :<i class="fa-regular fa-heart fa-2x"  onClick={()=>{setHeart(!heart)}}></i>}
                {/* {heart ? <NotificationModal></NotificationModal>:null} */}
            </div>
            <div className="post-icon">
            {square ?<i class="fa-solid fa-square-plus fa-2x" onClick={()=>{setsquare(!square)}} style={{color:'black'}} >
                <WriteModal></WriteModal></i>
                :<i class="fa-regular fa-square-plus fa-2x"  onClick={()=>{setsquare(!square)}}></i>}
                
                
            </div>
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

export default User

