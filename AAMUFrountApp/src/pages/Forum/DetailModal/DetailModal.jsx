import React, { useEffect, useRef, useState } from 'react'
import styled from 'styled-components'
import Notice from '../Notice/Notice'
import '../Board2/DetailButton.scss'
import { Link } from 'react-router-dom'
import { Rating } from '@mui/material'
import axios from 'axios'
import './BookMark.css';

const DetailModal = ({setIsOpen, detailRbn}) => {

    let [tempObj, setTempObj] = useState({review: "",
                                        reviewId: "",
                                        star: ""});

    // console.log('tempObj 인듯 : ', tempObj);

    let [userName] = useState('ADMIN');

    let [star, setStar] = useState(0); //사용자가 입력하는 별점
    let [commentStar, setCommentStar] = useState([]); //commentStar에 별점 저장

    let [comment, setComment] = useState(''); // comment 사용자가 입력하는 댓글
    let [feedComments, setFeedComments] = useState([]); // feedComments 댓글 리스트 저장
    let [isValid, setIsValid] = useState(false); // 댓글 게시가능여부 (유효성 검사)

    // console.log('저장됬는지 : ',feedComments);

    useEffect(() => {
       
       const $body = document.querySelector("body");
       $body.style.overflow= "hidden";

       return () => (
           $body.style.overflow = "auto"
           );
    }, []);

    // 더미 데이터
    // useEffect(()=>{
    //     let temp = [...dummy.reviewdata]
    //     setFeedComments(temp)
    // },[])

    // console.log('detailRbn 글번호 넘어왔나 :',detailRbn);
    useEffect(()=>{
        let token = sessionStorage.getItem("token");
        axios.get('/aamurest/bbs/SelectOne',{
            params:{
                rbn:detailRbn
            },
            headers: {
                Authorization: `Bearer ${token}`,
            }
        }).then((resp)=>{
            console.log('게시판 상세보기 : ',resp.data);
            
        }).catch((error)=>{
            console.log((error) => console.log("게시판 상세보기 실패", error));
        });
        
    },[]);
    

    let reviewPost = (e) => {
        
        // setTempObj((curr)=>{return {...curr, star:star, review:comment, reviewId:sessionStorage.getItem('token')}});
        // setFeedComments([...feedComments, tempObj]);

        // 전개연산자를 사용해서 feedComments에 담겨있는 댓글과
        // commentStar에 담겨있는 별점 가져오기
        // const copyFeedComments = [...feedComments];
        // const copyStar = [...commentStar];

        // console.log('comment 추가됬나 확인 :',comment);
        // console.log('star 값 저장됬나 확인 :',star);

        let token = sessionStorage.getItem("token");

        axios.post("",{
        comment: `${comment}`,
        star: `${star}`,
        id: sessionStorage.getItem('token')

        },{
        headers: {
            Authorization: `Bearer ${token}`,
            }
        }).then((res)=>{
            setFeedComments(res.data);
        });

        

        //기존 댓글 배열이 담긴 copyFeedComments에 사용자가 입력한 comment 를 push
        // copyFeedComments.push(comment);

        //기존 별점 배열이 담긴 copyStar에 사용자가 입력한 star 를 push
        // copyStar.push(star);

        //사용자가 입력한 댓글을 포함시켜서 setFeedComments을 변경
        // setFeedComments(copyFeedComments);
        //사용자가 입력한 별점을 포함시켜서 setCommentStar을 변경
        // setCommentStar(copyStar);

        //사용자가 입력한 댓글창과 별점 초기화
        setComment('');
        setStar(0);
        setIsValid(false);
    };
    
    // console.log('입력한 댓글 저장 확인 :', feedComments);
    // console.log('입력한 별점 저장 확인 :', star);

    const reviewDelete = (no) => {
        // console.log('프롭스 잘 넘어오나~', no);
        // console.log('feedComments : ', feedComments);

        setFeedComments(feedComments.filter((e, index)=>index !== no));
        setCommentStar(commentStar.filter((e, index)=>index !== no));

        // filter 이거가지고 검색기능 가능할듯
    }

    const CommentList = (props) =>{ //리뷰댓글 컴포넌트
        return(
            <div>
                <Stars>
                    <img src='/images/star.jpg' style={{width:'30px'}}/>
                    {props.commnetArr.star}
                </Stars>

                <UserName>{props.commnetArr.reviewId}<Name>님 (2022-07-10)</Name></UserName>

                <p>{props.commnetArr.review}</p>

                <EditDelte type='button'>
                    <Name>수정</Name>
                </EditDelte>
                <EditDelte type='button' onClick={()=>{reviewDelete(props.index)}}>
                    <Name>삭제</Name>
                </EditDelte>
                {/* {
                    console.log('인덱스 넘어온거 : ', props.index)
                } */}
            </div>
        );
    };

    // console.log('더미데이터 :', dummy.imgsdata);
    const [bookMark, setBookMark] = useState(false);

    let test = [1,2,3,4];
// ref={modalRef}
  return (
    <DetailContainer >
        <DetailOverlay>
            <DetailModalWrap>
                <Plan>
                    <PlanTitle>여행경로 제목 자리</PlanTitle>
                    {/* {
                        dummyPlanner.route.map((val, idx)=>{
                            console.log('더미 플래너 :',val, '인덱스:',idx);
                        })
                    } */}
                    <div style={{border:'1.5px solid #edf2f4'}}>
                    {/*
                    게시글 클릭시 게시글 작성자의 id (혹은 다른 값)를 키로 보내서
                    해당 글에 대한 여행 경로 데이터를 가져와서 표시해줘야함
                    */}
                    {
                        test.map((val, idx)=>{
                            return (
                            <DetailPlan>
                                <PlanDate>{val} 일차 20xx-xx-xx x요일</PlanDate>
                                <PlanTime>00:00 ~ 00:00</PlanTime>
                                <PlanRegion>제주공항</PlanRegion>

                                <PlanTime>00:00 ~ 00:00</PlanTime>
                                <PlanRegion>교원스위트호텔 제주</PlanRegion>
                            </DetailPlan>
                            )
                        })
                    }
                    
                        
                    </div>
                </Plan>

                <DetailTitle>
                    <span>
                        <div className="anim-icon star">
                            <input
                                type="checkbox"
                                id="star"
                                onClick={(e)=>{
                                    console.log('북마크 체크 여부 :',e.currentTarget.checked);
                                }}/>
                            <label for="star"></label>
                        </div>
                        
                        {/* 여기가 글 작성할때 쓴 제목 */}
                        {/* {dummy.title} */}

                        <div className='detail-button'>
                            <button className="learn-more_exit" type="button" onClick={(e)=>{
                                setIsOpen(false);                            
                            }}>exit</button>
                        </div>
                    </span>
                </DetailTitle>

                <DetailContents>
                    <Notice />
                    {/* dummy={dummy.imgsdata} */}
                    
                    <Textarea>
                        {/* {dummy.content} */}
                        {/* 여기가 공유한 글 내용 */}
                    </Textarea>
                    <div>
                        <Rating
                            name="simple-controlled"
                            precision={0.5}
                            onChange={(e, newValue) => {
                                setStar(newValue);
                            }}// 사용자가 선택한만큼 setStar를 통해 star의 값 변경
                            value={star}
                        />
                    </div>
                    <Tag>
                        <Date>작성일. </Date>
                        <Link to='/'>
                            #tag
                        </Link>
                    </Tag>
                    <input
                        type="text"
                        placeholder="리뷰 달기..."
                        onChange={(e)=>{
                            setComment(e.target.value);
                        }} //리뷰창 변할때마다 setComment를 통해 comment의 값 변경
                        onKeyUp={(e)=>{
                            e.target.value.length > 0 ? setIsValid(true) : setIsValid(false);
                            // console.log(isValid);
                        }} //사용자가 리뷰를 작성했을 때 빈공간인지 확인하여 유효성 검사
                        value={comment}
                        />

                    {/* DetailButton.scss */}
                    <div className='detail-button'>
                        {
                            isValid ? 
                            <button className="learn-more" type="button" onClick={reviewPost}>리뷰 등록</button>
                            : 
                            <button className="learn-more" type="button" disabled>리뷰를 작성하세요</button>
                        }
                    </div>
                </DetailContents>

                {/* <div style={{border:'1px blue solid'}}>사용목적 미정 공간</div> */}

                <DetailReview>
                    {//feedComments 에 담겨있을 댓글 값을 CommentList 컴포넌트에 담아서 가져오기
                    //CommentList 컴포넌트는 반복적으로 추가되는 사용자 댓글을 하나하나 담고있음
                    //userName 은 위에서 ADMIN 을 담은 값을,
                    //userComment 는 feedComments 에 담겨있는 배열 담는다
                    //stars 는 commentStar의 인덱스 번호에 맞는 별점 배열을 담는다
                        feedComments.map((commnetArr, idx)=>{
                            return(
                                <CommentList
                                    commnetArr={commnetArr}
                                    // stars={commentStar[idx]}
                                    // userName={userName}
                                    // userComment={commnetArr}
                                    // key={idx}
                                    // index={idx}
                                />
                            );
                        })
                    }
                </DetailReview>
                
            </DetailModalWrap>
        </DetailOverlay>
    </DetailContainer>

  )
}
const Plan = styled.div`
    display: flex;
    flex-direction: column;
    overflow: auto;
    grid-row: 1 / 4;
    &::-webkit-scrollbar{
        display:none;
    }
`
const PlanTitle = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    
`
const PlanDate = styled.span`
    height: 50px;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #edf2f4;
    // border-radius: 8px;
`
const DetailPlan = styled.div`
    width: 100%;
    font-size: 20px;
    margin-top: 3px;
    // border: 1px red solid;
    
`
const PlanTime = styled.div`

`
const PlanRegion = styled.div`
    margin-top: 5px;
    margin-bottom: 10px;
`

const Textarea = styled.div`
    position: relative;
    width: 99%;
    height: 320px;
    overflow: auto;
    font-size: 20px;
    box-shadow: 0 0 0 2px #e9ebec, 0 0 0 11px #fcfdfe;
`
// position: fixed; 모달창 열리면 외부 스크롤바 안되게
const DetailContainer = styled.div`
    position: fixed;
    display:auto;
    width: 100%;
    height: 100%;
    z-index: 1000;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    overflow: auto;
`

const DetailOverlay = styled.div`
    position: absolute;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.6);
`

const DetailModalWrap = styled.div`
    display: grid;
    grid-template-columns:300px 1000px;
    border: 1px white solid;
    height: 90%;
    // height: 650px;
    overflow: auto;

    border-radius: 15px;
    background-color: #fff;

    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    box-shadow: 0 0 0 10px #e9ebec, 0 0 0 11px #fcfdfe;

    &::-webkit-scrollbar{
        display:none;
    }
`

const DetailTitle = styled.div`
    margin-top: 15px;
    margin-bottom: 10px;
    
    span{
        float: center;
        font-size: 1.5em;
        margin-left: 40px;
    }
    div{
        margin-right: 25px;
    }
`

const DetailReview = styled.div`
    margin: 0px 20px;
    font-size: 18px;

    div{
        display: grid;
        grid-template-columns:70px 200px 620px 30px 30px;
        margin-top: 2px;
        border-bottom: solid 1px #c3cff4;
    }
`
const Stars = styled.span`
    // border: 1px solid red;
    // width: 80px;
`
const UserName = styled.span`
    // border: 1px solid blue;
    // width: 100px;
`
const Name = styled.span`
    font-size: 13px;
    color: #6d6875;
`

const EditDelte = styled.button`
    // border: 1px solid black;
    height: 30px;
    margin-left: 3px;
`

const DetailContents = styled.div`
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    grid-template-rows: 400px 50px 50px;
    gap: 5px;

    // overflow: auto;

    margin: 10px 10px;
    
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
        margin-left: 10px;
        font-size: 18px;
    }
`
const Tag = styled.span`
    font-size: 12.5px;
    color: #8e8e8e;
`
const Date = styled.div`
    float: right;
    justify-content: center;
    align-items: center;
    font-size: 12.5px;
    color: #8e8e8e;
    margin-right: 5px;
`

const Button = styled.button`
    float: right;

    font-size: 14px;
    padding: 10px 20px;
    border: none;
    background-color: #ababab;
    border-radius: 10px;
    color: white;
    font-style: italic;
    font-weight: 200;
    cursor: pointer;

    &:hover{
        background-color: #898989;
    }
`

export default DetailModal