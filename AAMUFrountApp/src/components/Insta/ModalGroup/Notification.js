
import React, { useEffect, useRef, useState } from 'react'
import styled from 'styled-components'

const Notification = ({setIsOpen}) => {

    return (
        <div className="notifi-all disappear">
            <div className="search-squre"></div>
            <div className="search-engine">
                <div className="parent">
                    <div className="search-contents">
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
                </div>
            </div>
      </div>
          )
        }
        
   

export default Notification
