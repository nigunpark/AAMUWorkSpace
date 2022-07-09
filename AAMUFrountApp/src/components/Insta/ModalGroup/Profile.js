import React, { useEffect, useRef, useState } from 'react'
import styled from 'styled-components'

const profile = ({setIsOpen}) => {
    

  return (
    <div className="profile-all disappear">
        <div className="profile-engine">
            <div className="parent">
              <div className="profile-contents">
                <div>
                  <div className='rowfirst'>
                    <div className="gradient">
                        <img className='imgprofile' src="img/b.jpg" alt="스토리 프로필 사진" />
                    </div>
                    <div>
                        <p className="user-id">jenny0305</p>
                        <p className="user-name">hi im jenny</p>
                    </div>
                  </div>
                  <div className='row'>
                    <div className='profileSecond'>
                        <p className="user-id">게시물</p>
                        <p className="user-name">59</p>
                    </div>
                    <div className='profileSecond'>
                        <p className="user-id">팔로워</p>
                        <p className="user-name">59</p>
                    </div>
                    <div className='profileSecond'>
                        <p className="user-id">팔로우</p>
                        <p className="user-name">59</p>
                    </div>
                  </div>
                  <div className='rowpic'>
                    <div>
                      <img className='profilePic' src="img/b.jpg" alt="스토리 프로필 사진" />
                    </div>
                    <div>
                      <img className='profilePic' src="img/b.jpg" alt="스토리 프로필 사진" />
                    </div>
                    <div>
                      <img className='profilePic' src="img/b.jpg" alt="스토리 프로필 사진" />
                    </div>
                  </div>
                  <div className='rowbutton'>
                    <button className='message'>메세지 보내기</button>
                    <button className='following'>팔로잉</button>
                  </div>
                </div>
              </div>
            </div>
        </div>
    </div>
  )
}

export default profile
