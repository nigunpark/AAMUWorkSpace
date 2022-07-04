import { faSquarePlus } from '@fortawesome/free-regular-svg-icons'
import { faHeart } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { red } from '@mui/material/colors'
import React, { useState } from 'react'


function User() {
    const [heart,setHeart] = useState(false);
    const [follow,setFollowing] = useState(false);
    
  return (
    <div >
        <div className="search">
            <input type="text" className="search-bar" placeholder="검색" />
           
             {/* <div className="search-all disappear">
                <div className="search-engine">
                    <div className="parent">
                    <div className="search-contents">
                        <div className="gradient">
                            <img src="img/jenny.jpeg" alt="스토리 프로필 사진" />
                        </div>
                        <div>
                            <p className="user-id">jenny0305</p>
                            <p className="user-name">hi im jenny💙</p>
                        </div>
                    </div>
                    </div>
                </div>
                <div className="search-squre"></div>
  </div>  */}
        </div>
        <div className="user">
            <img src="./img/bk.jpg" alt="사프" />
            <div>
                <p className="user-id">0hyun0hyun</p>
                <p className="user-name">김영현</p>
            </div>
            <div className='heart'>
                {heart ?<i class="fa-solid fa-heart fa-2x"onClick={()=>{setHeart(!heart)}} style={{color:'black'}} />:<i class="fa-regular fa-heart fa-2x"  onClick={()=>{setHeart(!heart)}}></i>}
            </div>
            <div>
                <FontAwesomeIcon icon={faSquarePlus} className="post-icon" />
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

