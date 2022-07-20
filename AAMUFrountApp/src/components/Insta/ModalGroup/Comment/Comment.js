import styled from "styled-components";
import React, {  useRef, useState } from 'react'
import Profile from '../Profile';
import MenuModal from '../MenuModal';
import axios from "axios";
import "../Slider/slick.css";
import "../Slider/slick-theme.css";
import Swiper, { A11y, Autoplay, Navigation, Pagination, Scrollbar } from "swiper";
import { SwiperSlide } from "swiper/react";

function Comment({val}) {
    let menuRef = useRef();
    let profileRef = useRef();
    let commentRef = useRef();
    const [heart,setHeart] = useState(false);
    const [modalShow, setModalShow] = useState(false);
    
    const [commentHeart, setCommentHeart] = useState(false);
    
    function menuModalRef(e){
        e.stopPropagation();
        if (e.target != menuRef.current) setModalShow(false);
    }
    window.addEventListener("click", menuModalRef);

    const [comment, setcomment] = useState([]);
    function commentModal(){
       
        let token = sessionStorage.getItem("token");
        axios.get(`/aamurest/gram/SelectOne/${val.lno}`,{
        headers: {
                Authorization: `Bearer ${token}`,
            },
        })
        .then((resp) => {
        console.log(resp.data);
        setcomment(resp.data);
        })
        .catch((error) => {
            console.log(error);
        });
    }

    const settings = {//이미지 슬라이드
        dots: true,
        infinite: false,
        speed: 500,
        slidesToShow: 1,
        slidesToScroll: 1
      };
      
console.log('photo',val.photo)
  return (
    // <ModalWrap>
        <Contents> 
            <div className="swiperUi">
                <ul>
                 <Swiper
                    className="swiperContainer"
                    modules={[Navigation, Pagination, Scrollbar, A11y, Autoplay]}
                    spaceBetween={10}
                    slidesPerView={1}
                    // navigation
                    autoplay={{ delay: 2500 }}
                    loop={true}
                    pagination={{ clickable: true }}
                    scrollbar={{ draggable: true }}
                  >
                    {val.photo.map((image,i)=>{
                      return(
                      <SwiperSlide>
                        <li>
                        <img className='divimage' alt="sample" src={image}/>
                        {/* <img className='divimage' alt="sample" src='/images/bg1.png'/> */}
                        </li>
                    </SwiperSlide>
                      )
                      
                    })
                    }      
                  </Swiper>
                  
                </ul>
            </div>
            <div className="contents">
                <div className="feeds-settingCom">  
                    <div className="search-contents">
                        <div className="gradient">
                        <img src="'/img/bk.jpg ' ?? '/images/user.jpg'" alt="프사" onError={(e)=>{e.target.src='/images/user.jpg'}}/> 
                        </div>
                        <div>
                            <p className="user-id"><strong>{sessionStorage.getItem('username')}</strong></p>
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
                        <img className="userimg" src="'/img/bk.jpg ' ?? '/images/user.jpg'" alt="프사" onError={(e)=>{e.target.src='/images/user.jpg'}}/> 
                            <div style={{display:'flex',flexDirection:'column',marginTop:'10px',marginLeft:'10px'}}>
                                <div style={{display:'flex',flexDirection:'row',paddingRight:'15px'}}>
                                    <p className="userName" ><strong style={{fontSize:'13px',marginRight:'5px'}}>{sessionStorage.getItem('username')}</strong>
                                                             {val.content}</p>
                                    
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
                    <button className="comment-button" type="button" >게시</button>
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
    width: 75%;
    height: 780px;
    left: 50%;
    top: 50%;
    background :white;
    transform: translate(-50%,-50%);
    display:flex;
    flex-direction:row;
    border-radius:7px;
`


export default Comment