import React, { useEffect, useRef, useState } from 'react'
import styled from 'styled-components'
import Notice from '../Notice/Notice'
import useOutSideClick from './hook/useOutSideClick'
import '../Board2/DetailButton.scss'
import { Link } from 'react-router-dom'
import { Rating } from '@mui/material'

const DetailModal = ({setIsOpen}) => {
    //const modalRef = useRef(null);

    const handleClose = () => {
        setIsOpen(false);
    };

    //useOutSideClick(modalRef, handleClose);

    useEffect(() => {
        const $body = document.querySelector("body");
        $body.style.overflow= "hidden";

        return () => (
            $body.style.overflow = "auto"
            );
    }, []);

    const [star, setStar] = useState(0); //사용자가 입력하는 별점
    let [commentStar, setCommentStar] = useState([]); //commentStar에 별점 저장

    // console.log('맨 위 별점:',star);

    let [userName] = useState('ADMIN');
    let [comment, setComment] = useState(''); // comment 사용자가 입력하는 댓글
    let [feedComments, setFeedComments] = useState([]); // feedComments 댓글 리스트 저장
    let [isValid, setIsValid] = useState(false); // 댓글 게시가능여부 (유효성 검사)

    let post = (e) => {
         // 전개연산자를 사용해서 feedComments에 담겨있는 댓글과
         // commentStar에 담겨있는 별점 가져오기
        const copyFeedComments = [...feedComments];
        const copyStar = [...commentStar];

        //기존 댓글 배열이 담긴 copyFeedComments에 사용자가 입력한 comment 를 push
        copyFeedComments.push(comment);
        //기존 별점 배열이 담긴 copyStar에 사용자가 입력한 star 를 push
        copyStar.push(star);

        //사용자가 입력한 댓글을 포함시켜서 setFeedComments을 변경
        setFeedComments(copyFeedComments);
        //사용자가 입력한 별점을 포함시켜서 setCommentStar을 변경
        setCommentStar(copyStar);

        //사용자가 입력한 댓글창과 별점 초기화
        setComment('');
        setStar(0);
    };

    const reviewDelete = (no) => {
        // console.log('프롭스 잘 넘어오나~', no);
        // console.log('feedComments : ', feedComments);

        setFeedComments(feedComments.filter((e, index)=>index !== no));
        // filter 이거가지고 검색기능 가능할듯
    }

    const CommentList = (props) =>{ //리뷰댓글 컴포넌트
        return(
            <div>
                <Stars>
                    <img src='/images/star.jpg' style={{width:'30px'}}/>
                    {props.stars}
                </Stars>

                <UserName>{props.userName}<Name>님 (2022-07-10)</Name></UserName>

                <p>{props.userComment}</p>

                <EditDelte type='button'>
                    <Name>수정</Name>
                </EditDelte>
                <EditDelte type='button' onClick={()=>{reviewDelete(props.index)}}>
                    <Name>삭제</Name>
                </EditDelte>
                {/* {
                    console.log('인덱스(키값) : ', props.index)
                } */}
            </div>
        );
    };

// ref={modalRef}
  return (
    <DetailContainer >
        <DetailOverlay>
            <DetailModalWrap>

                <DetailTitle>
                    <span>제주도 3박 4일 여행</span>
                    {/* <Button onClick={handleClose}>Close</Button> 주석 */}
                    <div className='detail-button'>
                        {/* DetailButton.scss 주석 */}
                        <button className="learn-more_exit" type="button" onClick={(e)=>{
                             setIsOpen(false);                            
                        }}>exit</button>
                    </div>
                </DetailTitle>

                <DetailContents>
                    {/* <img src='/images/imageMap.png' alt='smile'/> 주석 */}

                    <Notice/>
                    <Textarea>
                        내용 들어갈 자리
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
                        <Date>작성일. 2022-07-03</Date>
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
                        }} //사용자가 리뷰를 작성했을 때 빈공간인지 확인하여 유효성 검사
                        value={comment}
                        />
                    <div className='detail-button'>
                        {/* DetailButton.scss 주석 */}
                        <button
                            className="learn-more"
                            type="button"
                            onClick={post}
                            disabled={isValid ? false : true}
                            >
                            리뷰 등록
                        </button>
                    </div>
                </DetailContents>
                <DetailReview>
                    {//feedComments 에 담겨있을 댓글 값을 CommentList 컴포넌트에 담아서 가져오기
                     //CommentList 컴포넌트는 반복적으로 추가되는 사용자 댓글을 하나하나 담고있음
                     //userName 은 위에서 ADMIN 을 담은 값을,
                     //userComment 는 feedComments 에 담겨있는 배열 담는다
                     //stars 는 commentStar의 인덱스 번호에 맞는 별점 배열을 담는다
                        feedComments.map((commnetArr, idx)=>{
                            return(
                                <CommentList
                                    stars={commentStar[idx]}
                                    userName={userName}
                                    userComment={commnetArr}
                                    key={idx}
                                    index={idx}
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
const Textarea = styled.div`
    position: relative;
    width: 99%;
    height: 320px;
    overflow: auto;
    box-shadow: 0 0 0 2px #e9ebec, 0 0 0 11px #fcfdfe;
    // border: solid 1px black
`
// textarea{
//     position: absolute;
//     width: 100%;
//     height: 100%;
//     top: 0;
//     left: 0;
//     resize: none;
// }


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
    width: 1050px;
    height: 90%;

    overflow: auto;

    border-radius: 15px;
    background-color: #fff;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    box-shadow: 0 0 0 10px #e9ebec, 0 0 0 11px #fcfdfe;
`

const DetailTitle = styled.div`
    margin-top: 15px;

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
    margin-left: 20px;
    margin-right: 20px;
    
    font-size: 18px;

    div{
        display: grid;
        grid-template-columns:70px 200px 670px 30px 30px;
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
    gap: 5px;

    // overflow: auto;

    margin: 20px 10px;
    
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

// const DetailCloseButton = styled.div`
//     float: right;
//     width: 40px;
//     height: 40px;
//     margin: 20px;
//     cursor: pointer;
//     i{
//         color: #5d5d5d;
//         font-size: 30px;
//     }
// `

export default DetailModal