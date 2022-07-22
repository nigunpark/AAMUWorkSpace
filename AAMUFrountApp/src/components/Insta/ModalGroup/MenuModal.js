import { red } from '@mui/material/colors';
import React, { useEffect, useRef, useState } from 'react'
import styled from 'styled-components'
import Edit from './Edit/Edit'

const Modal = ({setIsOpen}) => {
    let menuRef = useRef();
    const [goEdit, setgoEdit] = useState(false);
    const [commentModal, setcommentModal] = useState(false);

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

    function menuModalRef(e){//메뉴 모달 나왔을때 주변 눌러도 꺼지게 만들기
        e.stopPropagation();
        if (e.target != menuRef.current) setgoEdit(false);
    }
    
    window.addEventListener("click", menuModalRef);
    
  return (
    <Container >
        <Overlay
        ref={menuRef} 
        onClick={ (e) => { if(e.target == menuRef.current) setcommentModal(false)  }}>
            <ModalWrap >
                <Contents>
                        <Button type="button" className='edit' onClick={()=>{setgoEdit(!goEdit)}}>수정하기</Button>
                        {
                            goEdit ? <Edit/> :null
                        }
                        <Button type="button" className='delete'>삭제하기</Button>
                        <Button type="button" className='cancel'>취소하기</Button>    
                                  
                </Contents>
            </ModalWrap>
        </Overlay>
    </Container>
  )
}

const Container = styled.div`
    cursor: default;
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
    z-index:100;
    background-color: rgba(0, 0, 0, 0.6);
`

const ModalWrap = styled.div`
    width: 400px;
    overflow: hidden;
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
    justify-content: center;
    align-items: center;
    
`
const Button = styled.div`
    padding: 30px;
    width: 100%;
    height: 100%;
    cursor: pointer;
    text-align: center;
    font-size: medium;
    border-bottom: 0.5px solid rgb(220, 220, 220);
`

export default Modal