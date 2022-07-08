import { faPaperPlane, faTimesCircle } from '@fortawesome/free-regular-svg-icons';
import { faImage } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect, useRef, useState } from 'react'
import styled from 'styled-components'

const Modal = ({setIsOpen}) => {

    const [attachment, setClearAttachment] = useState(false);

    const [fileImage, setFileImage] = useState("");
    const saveFileImage = (event) =>{
      // @ts-ignore
      setFileImage(URL.createObjectURL(event.target.files[0]));
    };
    const deleteFileImage = () =>{
      URL.revokeObjectURL(fileImage);
      setFileImage("");
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
                  
                    <button style={{
                            width: "50px",
                            height: "50px",
                            cursor: "pointer",}}
                            onClick={() => deleteFileImage()} > 삭제 </button> 
                    <div style={{
                      alignItems: "center",
                      justifyContent: "center", }} >
                      <input
                          name="imggeUpload"
                          type="file"
                          accept="image/*"
                          onChange={saveFileImage} />
                    </div>
                    <div  style={{ marginRight:"30px",width:"100%",height:"100%" }}>
                      {fileImage && ( <img alt="sample" src={fileImage}
                                            style={{ height:"100%",width:"100%" }} /> )}
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
    background-color: rgba(0, 0, 0, 0.6);
`

const ModalWrap = styled.div`
    width: 600px;
    overflow: hidden;
    height: 700px;
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
    align-items: center;
    
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
        font-size: 20px;
    }
`


export default Modal