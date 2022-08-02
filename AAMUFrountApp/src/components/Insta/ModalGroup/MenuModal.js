import { red } from '@mui/material/colors';
import axios from 'axios';
import React, { useEffect, useRef, useState } from 'react'
import styled from 'styled-components'
import Spinner from "../Spinner";


const Modal = ({val,setModalShow,seteditModal , setlist,setcommentModal}) => {
    let menuRef = useRef();
    let editRef = useRef();
    const [goEdit, setgoEdit] = useState(false);
    const [loading, setloading] = useState(false);
    const [godown, setgodown] = useState(false);
    

    function menuModalRef(e){//메뉴 모달 나왔을때 주변 눌러도 꺼지게 만들기
        e.stopPropagation();
        if (e.target === menuRef.current) setModalShow(false);
        
    }
    
    window.addEventListener("click", menuModalRef);

 
    
    let [deleteOnee, setdeleteOnee] = useState(false); 
    function deleteOne(){//업로드 버튼 누르고 화면 새로고침
        let token = sessionStorage.getItem("token");
        axios.delete(`/aamurest/gram/edit/${val.lno}`,{
          headers: {
                Authorization: `Bearer ${token}`,
              },
        })
        .then((resp) => {
            setdeleteOnee(resp.data);//성공 여부가 온다 true false
            // alert('삭제되었습니다!')
            setcommentModal(false);
            setlist((curr) => {
              return curr.filter((item) => {
                return item.lno != val.lno;
              });
            });
          })
          .catch((error) => {
            console.log(error);
          });
      }
 
      
  return (
    <Container >
        <Overlay ref={menuRef} >
            <ModalWrap >
                <Contents  >
                        <Button type="button" className='edit' ref={editRef} onClick={(e)=>{e.stopPropagation(); setModalShow(false); seteditModal(true) }}>수정하기</Button>
                       
                        <Button type="button" className='delete' onClick={(e)=>{e.stopPropagation(); setModalShow(false);   deleteOne() }}>삭제하기</Button>
                        <Button type="button" className='cancel' onClick={()=>{setModalShow(false)}}>취소하기</Button>    
                                  
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
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    display: flex;
    z-index:1;
    flex-direction: column;
    justify-content: center;
    align-items: center;
`

const Overlay = styled.div`
    position: absolute;
    width: 100%;
    height: 100%;
    z-index:11;
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
    z-index:12;
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