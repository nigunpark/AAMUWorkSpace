import styled from "styled-components";
import React, {  useEffect, useRef, useState } from 'react'
import Profile from '../Profile';
import MenuModal from '../MenuModal';
import axios from "axios";
import "../Slider/slick.css";
import "../Slider/slick-theme.css";
import { SwiperSlide,Swiper } from 'swiper/react';
import SwipersItem from '../../Swipers/SwipersItem';
import  { A11y, Autoplay, Navigation, Pagination, Scrollbar } from 'swiper';
import "swiper/css"; //basic
import "swiper/css/navigation";
import "swiper/css/pagination";
import '../Upload/UploadSwiper.css';
import dayjs from 'dayjs';

function Comment({val,forReRender, setForReRender}) {
    let menuRef = useRef();
    let profileRef = useRef();
    let commentRef = useRef();
    const [commentHeart,setCommentHeart] = useState(false);
    const [modalShow, setModalShow] = useState(false);
    let [comment, setComment] = useState('');
    const [idReply, setidReply] = useState([{
        id: '',
        reply:''
    }]);
    
    function menuModalRef(e){
        e.stopPropagation();
        if (e.target != menuRef.current) setModalShow(false);
    }
    window.addEventListener("click", menuModalRef);

    function uploadFile(idReply){//이미지 업로드
        let formData = new FormData(); // formData 객체를 생성한다.
        for (let i = 0; i < comments.length; i++) { 
          formData.append("", comments[i]); // 반복문을 활용하여 파일들을 formData 객체에 추가한다
        }
        return formData;
      }

    const [comments, setcomments] = useState([]);
    function commentModal(setcomments){
        const copyFeedComments = [...comments];//feedComments에 담겨있던 댓글 받아옴
        copyFeedComments.push(comment);//copyFeedComments에 있는 기존 댓글에 push하기 위함 
        setcomments(copyFeedComments);//copyFeedComments 담겨있을 comment를 setfeedComments로 변경
        let token = sessionStorage.getItem("token");
        axios.get(`/aamurest/gram/SelectOne/${val.lno}`,{
        headers: {
                Authorization: `Bearer ${token}`,
            },
        })
        .then((resp) => {
        console.log(resp.data);
        setcomments(resp.data.commuCommentList);
        })
        .catch((error) => {
            console.log(error);
        });
        setComment('');
    }
    useEffect(()=>{
        commentModal(setcomments)
      },[])
    const settings = {//이미지 슬라이드
        dots: true,
        infinite: false,
        speed: 500,
        slidesToShow: 1,
        slidesToScroll: 1
      };
      function fillLike(setForReRender,forReRender){//백이랑 인스타 리스드를 뿌려주기 위한 axios
        
        let token = sessionStorage.getItem("token");
        axios.get('/aamurest/gram/like',{
          headers: {    
                Authorization: `Bearer ${token}`,
              },
              params:{
                lno:parseInt(val.lno),
                id:sessionStorage.getItem('username')
              }
        })
        .then((resp) => {
          console.log(resp.data)
          val.islike=resp.data.isLike
          val.likecount=resp.data.likecount
          setForReRender(!forReRender)
          })
          .catch((error) => {
            console.log(error);
          });
      }
      
      let CommentList = ({val}) => {
        return(
            // <div className = 'writing'>
            //     <span className="id">{val.commuComment==null?null:val.commuComment.id}</span>
            //     <span>{val.commuComment==null?null:val.commuComment.reply}</span>
            // </div>
            <div className="recommend-contents">
                <img className='likeimg' src="./img/bk.jpg" alt="추사" />
                <div style={{width:'100%',display:'flex',flexDirection:'column',marginTop:'10px',marginLeft:'10px'}}>
                    <div style={{display:'flex',flexDirection:'row'}}>
                        <p className="userName"><strong>{val.id}</strong></p>
                        <p className="userName">{val.reply}</p>
                    </div>
                    <div className="comment-heart">
                        {commentHeart ?<i className="fa-solid fa-heart"onClick={()=>{setCommentHeart(!commentHeart)}} style={{color:'red'}} />
                        :<i className="fa-regular fa-heart"  onClick={()=>{setCommentHeart(!commentHeart)}}></i>}
                        <i class="fa-regular fa-trash-can"></i>
                    </div>
                    <div style={{fontSize:'10px',color:'#a5a5a5',marginTop:'8px'}}>
                        <p className="postDate">{dayjs(val.postdate).format('YYYY/MM/DD')}</p>
                    </div>
                </div>
            </div>
        )
    }

  return (
    // <ModalWrap>
        <Contents> 
            <div className="swiperUi">
                <ul>
                 <Swiper
                    className="swiperContainer"
                    modules={[Navigation, Pagination, Scrollbar, A11y]}
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
                                    <p className="postDate">{dayjs(new Date(val.postdate)).format('YYYY/MM/DD')}</p>
                                </div>
                            </div>
                        </div>

                        {/* <div className="recommend-contents">
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
                                    <p className="postDate">{dayjs(new Date(val.postdate)).format('YYYY/MM/DD')}</p>
                                </div>
                            </div>
                        </div> */}
                       
                                {
                                    comments.map((val,i)=>{//feedComments에 담겨있을 댓글 값을 CommentList 컴포넌트에 담아서 가져온다
                                        return(
                                            <CommentList//CommentList 컴포넌트는 반복적으로 추가되는 사용자 댓글 하나하나를 담고있는 박스
                                            val={val}
                                            />
                                        );
                                    })
                                }
                                

                       
                       
                    </div>    
                </div>
                <div className="contentIcon">
                    <div className="feeds-icons">
                        <div className='heart-icon' 
                            onClick={(e)=>{
                                e.stopPropagation();
                                fillLike(setForReRender,forReRender)}}>
                            {val.islike?
                            <i className="fa-solid fa-heart fa-2x"  style={{color:'red'}}></i>
                            :<i className="fa-regular fa-heart fa-2x"></i>}
                            
                        </div>
                        <div className="talk-icon">
                            <i className=" fa-regular fa-comment fa-2x"></i>
                        </div>
                        <div className="share-icon">
                        <i className="fa-regular fa-paper-plane fa-2x"></i>
                        </div >
                    </div>
                    <div className="likeCount">
                        <h3><strong>좋아요 {val.likecount}개</strong></h3>
                    </div>
                    <div className="postDate">
                        <h3>{dayjs(new Date(val.postdate)).format('YYYY/MM/DD')}</h3>
                    </div>
                </div>
                <div className="comment">
                    <input type="text" className="typing-comment" placeholder="댓글 달기..."/>
                    <button className="comment-button" type="button" onClick={()=>{commentModal(comment,setcomments)}} >게시</button>
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
    height: 750px;
    left: 50%;
    top: 54%;
    background :white;
    transform: translate(-50%,-50%);
    display:flex;
    flex-direction:row;
    border-radius:7px;
`


export default Comment