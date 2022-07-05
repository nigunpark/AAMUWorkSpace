import React, { useEffect, useRef, useState } from 'react'
import styled from 'styled-components'

const Modal = ({setIsOpen}) => {

    const [value, setValue] = useState(2.5);

    const handleClose = () => {
        setIsOpen(false);
    };

    useEffect(() => {
        const $body = document.querySelector("body");
        $body.style.overflow= "hidden";

        return () => (
            $body.style.overflow = "auto"
            );
    }, []);

  return (
    <Container >
        <Overlay>
            <ModalWrap >
                <Contents>
                    <div className="recommend">                    
                        <div className="recommend-down">
                            <div className="recommend-contents">
                                <img className='likeimg' src="./img/bk.jpg" alt="추사" />
                                <div>
                                    <p className="userName">0hyun0hyun님이 좋아요를 눌렀습니다</p>
                                </div>
                            </div>
                            <div className="recommend-contents">
                                <img src="./img/bk.jpg" alt="추사" />
                                <div>
                                    <p className="userName">0hyun0hyun님이 댓글을 달았습니다</p>
                                </div>
                            </div>
                            <div className="recommend-contents">
                                <img src="./img/bk.jpg" alt="추사" />
                                <div>
                                    <p className="userName">0hyun0hyun님이 좋아요를 눌렀습니다</p>
                                </div>
                            </div>
                            <div className="recommend-contents">
                                <img src="./img/bk.jpg" alt="추사" />
                                <div>
                                    <p className="userName">0hyun0hyun님이 댓글을 달았습니다</p>
                                </div>
                            </div>
                            <div className="recommend-contents">
                                <img src="./img/bk.jpg" alt="추사" />
                                <div>
                                    <p className="userName">0hyun0hyun님이 좋아요를 눌렀습니다</p>
                                </div>
                            </div>
                            <div className="recommend-contents">
                                <img src="./img/bk.jpg" alt="추사" />
                                <div>
                                    <p className="userName">0hyun0hyun님이 댓글을 달았습니다</p>
                                </div>
                            </div>
                        </div>    
                    </div>
                </Contents>
            </ModalWrap>
        </Overlay>
    </Container>
  )
}

const Container = styled.div`
    position: fixed;
    width: 100%;
    height: 100%;
    z-index: 1000;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
`

const Overlay = styled.div`
    position: absolute;
    width: 100%;
    height: 100%;
    //background-color: rgba(0, 0, 0, 0.6);
`

const ModalWrap = styled.div`
    width: 500px;
    height: fit-content;
    border-radius: 15px;
    background-color: #fff;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    //border: solid 5px black;
`

const Contents = styled.div`
    display: flex;
    flex-direction: column;
    width: 100%;
    height: 100%;
    
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


export default Modal