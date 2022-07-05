import React, {  useState } from 'react'
import Dotsvg from './Dotsvg';
import { Button } from 'antd';
import MenuModal from './ModalGroup/MenuModal';


function FeedSetting() {
    const [heart,setHeart] = useState(false);
    const [notification,notNoti] = useState(false);
    const [modalShow, setModalShow] = useState(false);
    
  return (
<div>
    <div className="feeds-setting">  
            <div className="title-profile">
                <img src="./img/bk.jpg" alt="프사" />
                <span>eyesmag</span>                
                <Button onClick={() => setModalShow(!modalShow)}>
                    <Dotsvg></Dotsvg>
                    {modalShow ? <MenuModal></MenuModal> : null}
                </Button>               
               
            </div>
            <div className="location">
                <p>어딘가에서</p>
            </div>
            <div className="container">
            <img src="img/v.jpg" className="main-image" alt="메인" />
            </div>
            <div className="feeds-contents">
                <div className="feeds-icons">
                    <div className='heart-icon'>
                        {heart ?<i class="fa-solid fa-heart fa-2x"onClick={()=>{setHeart(!heart)}} style={{color:'red'}} />
                        :<i class="fa-regular fa-heart fa-2x"  onClick={()=>{setHeart(!heart)}}></i>}
                        
                    </div>
                    <svg className="talk-icon" fill="#262626" viewBox="0 0 512 512" >
                        <path d="M256,0C114.848,0,0,114.848,0,256c0,49.216,13.792,96.48,39.936,137.216L1.152,490.048
                        c-2.368,5.952-0.992,12.736,3.552,17.28C7.744,510.368,11.84,512,16,512c2.016,0,4-0.384,5.952-1.152l96.832-38.784
                        C159.52,498.208,206.784,512,256,512c141.152,0,256-114.848,256-256S397.152,0,256,0z M256,480
                        c-45.632,0-89.312-13.504-126.272-39.072c-2.688-1.888-5.888-2.848-9.088-2.848c-2.016,0-4.032,0.384-5.952,1.152l-69.952,28.032
                        l28.032-69.952c1.984-4.992,1.344-10.656-1.696-15.04C45.504,345.312,32,301.632,32,256C32,132.48,132.48,32,256,32
                        s224,100.48,224,224S379.52,480,256,480z"/>
                    </svg>
                    <svg className="share-icon" viewBox="0 0 551.13 551.13">
                        <path d="m465.016 172.228h-51.668v34.446h34.446v310.011h-344.457v-310.011h34.446v-34.446h-51.669c-9.52 0-17.223 7.703-17.223 17.223v344.456c0 9.52 7.703 17.223 17.223 17.223h378.902c9.52 0 17.223-7.703 17.223-17.223v-344.456c0-9.52-7.703-17.223-17.223-17.223z"/><path d="m258.342 65.931v244.08h34.446v-244.08l73.937 73.937 24.354-24.354-115.514-115.514-115.514 115.514 24.354 24.354z"/>
                    </svg>
                    {/* <svg className="save-icon" viewBox="0 0 48.065 48.065">
                        <path d="M40.908,0H7.158c-0.553,0-1,0.448-1,1v46.065c0,0.401,0.239,0.763,0.608,0.92c0.369,0.157,0.797,0.078,1.085-0.2
                        l16.182-15.582l16.182,15.582c0.189,0.183,0.439,0.28,0.693,0.28c0.132,0,0.266-0.026,0.392-0.08c0.369-0.157,0.608-0.52,0.608-0.92
                        V1C41.908,0.448,41.46,0,40.908,0z M39.908,44.714L24.726,30.095c-0.193-0.187-0.443-0.28-0.693-0.28s-0.5,0.093-0.693,0.28
                        L8.158,44.714V2h31.75V44.714z"/>
                    </svg> */}
                </div>
                <div className="feeds-like">
                    <img src="./img/bk.jpg" className="feeds-commentimage" alt="프사" />
                    <span><span className="like-bold">0hyun0hyun</span>님<span className="like-bold">외 10명</span>이 좋아합니다</span>
                </div>
                <div className="feeds-writing">
                    <span className="comment-id">eyesmag </span><span> 송원아트센터에서 진행하는 뉴 랜덤 다이버시티 전시 📷</span>
                </div>
                <div className="feeds-comment">
                </div>
                <span className="time">42분 전</span>    
            </div>
            <div className="comment">
                <input type="text" className="typing-comment" placeholder="댓글 달기..."/>
                <button className="comment-button" type="button">게시</button>
            </div>
    </div>       
    <div className="feeds-setting">    
            <div className="title-profile">
                <img src="./img/bk.jpg" alt="프사" />
                <span>eyesmag</span>
                <svg className="dot" fill="#262626" version="1.1" id="Capa_1" 
                viewBox="0 0 426.667 426.667">
                    <circle cx="42.667" cy="213.333" r="42.667"/>
                    <circle cx="213.333" cy="213.333" r="42.667"/>
                    <circle cx="384" cy="213.333" r="42.667"/>
                </svg>
            </div>
            <div className="location">
                <p>어딘가에서</p>
            </div>
            <div>
            <div className="container">
            <img src="img/b.jpg" className="main-image" alt="메인" />
            </div>
            </div>
            <div className="feeds-contents">
                <div className="feeds-icons">
                    <div className='heart-icon'>
                        {heart ?<i class="fa-solid fa-heart fa-2x"onClick={()=>{setHeart(!heart)}} style={{color:'red'}} />:<i class="fa-regular fa-heart fa-2x"  onClick={()=>{setHeart(!heart)}}></i>}
                    </div>
                    <svg className="talk-icon" fill="#262626" viewBox="0 0 512 512" >
                        <path d="M256,0C114.848,0,0,114.848,0,256c0,49.216,13.792,96.48,39.936,137.216L1.152,490.048
                        c-2.368,5.952-0.992,12.736,3.552,17.28C7.744,510.368,11.84,512,16,512c2.016,0,4-0.384,5.952-1.152l96.832-38.784
                        C159.52,498.208,206.784,512,256,512c141.152,0,256-114.848,256-256S397.152,0,256,0z M256,480
                        c-45.632,0-89.312-13.504-126.272-39.072c-2.688-1.888-5.888-2.848-9.088-2.848c-2.016,0-4.032,0.384-5.952,1.152l-69.952,28.032
                        l28.032-69.952c1.984-4.992,1.344-10.656-1.696-15.04C45.504,345.312,32,301.632,32,256C32,132.48,132.48,32,256,32
                        s224,100.48,224,224S379.52,480,256,480z"/>
                    </svg>
                    <svg className="share-icon" viewBox="0 0 551.13 551.13">
                        <path d="m465.016 172.228h-51.668v34.446h34.446v310.011h-344.457v-310.011h34.446v-34.446h-51.669c-9.52 0-17.223 7.703-17.223 17.223v344.456c0 9.52 7.703 17.223 17.223 17.223h378.902c9.52 0 17.223-7.703 17.223-17.223v-344.456c0-9.52-7.703-17.223-17.223-17.223z"/><path d="m258.342 65.931v244.08h34.446v-244.08l73.937 73.937 24.354-24.354-115.514-115.514-115.514 115.514 24.354 24.354z"/>
                    </svg>
                    {/*
                    <svg className="save-icon" viewBox="0 0 48.065 48.065">
                        <path d="M40.908,0H7.158c-0.553,0-1,0.448-1,1v46.065c0,0.401,0.239,0.763,0.608,0.92c0.369,0.157,0.797,0.078,1.085-0.2
                        l16.182-15.582l16.182,15.582c0.189,0.183,0.439,0.28,0.693,0.28c0.132,0,0.266-0.026,0.392-0.08c0.369-0.157,0.608-0.52,0.608-0.92
                        V1C41.908,0.448,41.46,0,40.908,0z M39.908,44.714L24.726,30.095c-0.193-0.187-0.443-0.28-0.693-0.28s-0.5,0.093-0.693,0.28
                        L8.158,44.714V2h31.75V44.714z"/>
                    </svg>
                    */}
                </div>
                <div className="feeds-like">
                    <img src="./img/bk.jpg" className="feeds-commentimage" alt="프사" />
                    <span><span className="like-bold">0hyun0hyun</span>님<span className="like-bold">외 10명</span>이 좋아합니다</span>
                </div>
                <div className="feeds-writing">
                    <span className="comment-id">eyesmag </span><span> 송원아트센터에서 진행하는 뉴 랜덤 다이버시티 전시 📷</span>
                </div>
                <div className="feeds-comment">
                </div>
                <span className="time">42분 전</span>    
            </div>
            <div className="comment">
                <input type="text" className="typing-comment" placeholder="댓글 달기..."/>
                {}
                <button className="comment-button" type="button" >게시</button>
            </div>
        </div>
    </div>       
    
   
  )
}

export default FeedSetting
