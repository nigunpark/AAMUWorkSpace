import React, { useEffect, useRef, useState } from 'react'
import styled from 'styled-components'
import Notice from '../Notice/Notice'
import useOutSideClick from './hook/useOutSideClick'
import '../Board2/DetailButton.scss'
import { Link } from 'react-router-dom'
import { Rating } from '@mui/material'

const DetailModal = ({setIsOpen}) => {

    const [value, setValue] = useState(2.5);

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

// ref={modalRef}
  return (
    <DetailContainer >
        <DetailOverlay>
            <DetailModalWrap>

                <DetailTitle>
                    <span>제목</span>
                    {/* <Button onClick={handleClose}>Close</Button> */}
                    <div className='detail-button'>
                        {/* DetailButton.scss */}
                        <button className="learn-more_exit" type="button" onClick={(e)=>{
                             setIsOpen(false);                            
                        }}>exit</button>
                    </div>
                </DetailTitle>

                <DetailContents>
                    {/* <img src='/images/imageMap.png' alt='smile'/> */}

                    <Notice/>
                    <Textarea>
                        내용 들어갈 자리
                    </Textarea>
                    <div>
                        <Rating
                            name="simple-controlled"
                            value={value}
                            precision={0.5}
                            onChange={(event, newValue) => {
                            setValue(newValue);
                            }}
                        />
                    </div>
                    <Tag>
                        <Date>2022-07-03</Date>
                        <Link to='/'>
                            #tag
                        </Link>
                    </Tag>
                    <input type="text" placeholder="리뷰 달기..."/>
                    <div className='detail-button'>
                        {/* DetailButton.scss */}
                        <button className="learn-more" type="button">게시</button>
                    </div>
                </DetailContents>

                {/* <DetailReview>
                    <div>
                        리뷰인듯 1. 달린 리뷰의 개수가 늘어나면 위 아래가 잘림
                                    모달창 스크롤 기능 혹은 안잘리게 하는거 찾아봐야함
                                    리뷰인듯 1. 달린 리뷰의 개수가 늘어나면 위 아래가 잘림
                                    모달창 스크롤 기능 혹은 안잘리게 하는거 찾아봐야함리뷰인듯 1. 달린 리뷰의 개수가 늘어나면 위 아래가 잘림
                                    모달창 스크롤 기능 혹은 안잘리게 하는거 찾아봐야함
                    </div>
                    <button>
                        <img src='/images/content-delete.png'/>
                    </button>
                    
                </DetailReview> */}
                
            </DetailModalWrap>
        </DetailOverlay>
    </DetailContainer>
  )
}
const Textarea = styled.div`
    position: relative;
    width: 450px;
    height: 350px;
    overflow: auto;
`
// textarea{
//     position: absolute;
//     width: 100%;
//     height: 100%;
//     top: 0;
//     left: 0;
//     resize: none;
// }

const DetailContainer = styled.div`
    position: fixed;
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
`

const DetailOverlay = styled.div`
    position: absolute;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.6);
`

const DetailModalWrap = styled.div`
    width: 1000px;
    height: fit-content;
    border-radius: 15px;
    background-color: #fff;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    //border: solid 5px black;
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
    margin-left: 40px;
    margin-right: 40px;
    
    font-size: 1em;
    
    div{
        
        display: inline-block;
        text-align: center;
        margin: 5px;
        border: solid 2px black;
        height: 50%;
    }

    border: solid 2px black;
`

const DetailContents = styled.div`
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    margin: 40px 30px;
    
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
        font-size: 20px;
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