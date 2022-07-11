import { faImage, faTimesCircle } from '@fortawesome/free-regular-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect, useState } from 'react'
import styled from 'styled-components'

const Upload = ({setIsOpen}) => {

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
        <Contents>  
                <div className='imageDiv'  
                style={{marginRight:'30px',width:"100%",height:"100%" }}>
                    {fileImage && ( <img alt="sample" src={fileImage}
                                        style={{ height:"100%",width:"100%" }} /> )}
                </div>  
        </Contents>
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
    position: absolute;
    display: flex;
    flex-direction: column;
    width: 100%;
    height: 100%;
    justify-content: center;
    align-items: center;
    right:2px;
    z-index:100000px;
    
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


export default Upload