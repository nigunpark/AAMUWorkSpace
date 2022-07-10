import { faImage, faTimesCircle } from '@fortawesome/free-regular-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect, useState } from 'react'
import styled from 'styled-components'

const Modal = ({setIsOpen}) => {

    const [hide, setHide] = useState(false);
   

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
    <ModalWrap>
        <Contents>   
            {/* <button style={{
                    width: "50px",
                    height: "50px",
                    cursor: "pointer",
                    marginBottom:'-50px'}}
                    onClick={() => deleteFileImage()} > 삭제 </button>  */}
            <h2>새 글 작성하기</h2>
            <div className="preview_container">
                {hide ?(
                <>
                <div 
                    className='picfileframe' >
                    <label 
                    className="rweet_file_btn" 
                    onClick={()=>{setHide(!hide)}}
                    for="input-file"                
                    >
                        <FontAwesomeIcon icon={faImage} size="4x"/>
                    </label>
                    <input
                        id="input-file"
                        type="file"
                        accept="image/*" 
                        onChange={saveFileImage} />  
                </div>
                <div className='imageDiv'  
                style={{marginRight:'30px',width:"100%",height:"100%" }}>
                    {fileImage && ( <img alt="sample" src={fileImage}
                                        style={{ height:"100%",width:"100%" }} /> )}
                </div>  
                </>
                ):(
<>
                <div 
                    className='picfileframe' >
                    <label 
                    className="rweet_file_btn" 
                    onClick={()=>{setHide(!hide)}}
                    for="input-file"                
                    >
                        <FontAwesomeIcon icon={faImage} size="4x"/>
                    </label>
                    <input
                        id="input-file"
                        type="file"
                        accept="image/*" 
                        onChange={saveFileImage} />  
                </div>
                </>
                )}
            </div>
        </Contents>
    </ModalWrap>
  )
}

const ModalWrap = styled.div`
    width: 30%;
    overflow: hidden;
    height: 65%;
    border-radius: 15px;
    background-color: #fff;
    position: absolute;
    justify-content: center;
    align-items: center;
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