import { faImage, faTimesCircle } from '@fortawesome/free-regular-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect, useRef, useState } from 'react'
import styled from 'styled-components'

const Upload = ({setIsOpen}) => {
    const countRef = useRef();
    const [hide, setHide] = useState(false);

    const [fileImage, setFileImage] = useState("");


    const saveFileImage = (event) =>{
      // @ts-ignore
      console.log('Ref', countRef.current.value.length)

    
    //    if(countRef.current.value.length !== 0){
    //     return setHide
    //    }

      {countRef.current.value.length != 0 &&
          setFileImage(URL.createObjectURL(event.target.files[0]));}
    };


    const deleteFileImage = () =>{
      URL.revokeObjectURL(fileImage);
      setFileImage("");
      setHide(false)
      
    console.log('Ref', countRef.current.value)
    };

    useEffect(() => {
        const $body = document.querySelector("body");
        $body.style.overflow= "hidden";

        return () => (
            $body.style.overflow = "auto"
            );
    }, []);
    // window.addEventListener("change",saveFileImage);
  return (
    <ModalWrap>
        <Contents>   
            <div className='UploadEntire'>
            <FirstLine>
                <Deletebtn 
                        onClick={() => deleteFileImage()} > 
                        <i class="fa-solid fa-arrow-left"></i>
                        </Deletebtn> 
                <div className='newPosting'>
                    <h2>새 게시물 만들기</h2>
                </div>
                <Nextbtn>다음</Nextbtn>
            </FirstLine>
                <div 
                    className='picfileframe' >
                    <label 
                    className="rweet_file_btn" 
                    onClick={ ()=>{setHide(!hide)} }
                    for="input-file"                
                    >
                        {hide ?
                       null
                        :
                        <FontAwesomeIcon icon={faImage} size="4x"/>
                        
                        }
                    </label>
                    <input
                        ref={countRef}
                        id="input-file"
                        type="file"
                        accept="image/*" 
                        onChange={saveFileImage}
                        
                        
                        />
                    <div  className='imageDiv'  >
                        <div style={{position: "absolute",width:"100%",height:"100%" }}>
                            {fileImage && ( <img alt="sample" src={fileImage}
                            style={{ height:"100%",width:"100%" }} /> )}
                        </div>  
                    </div>
                </div>
            </div>
        </Contents>
    </ModalWrap>
  )
}

const ModalWrap = styled.div`
    width: 500px;
    overflow: hidden;
    height:600px;
    border-radius: 15px;
    background-color: #fff;
    position: relative;
    justify-content: center;
    align-items: center;
    top: 55%;
    left: 50%;
    transform: translate(-50%, -50%);
    //border: solid 5px black;
`

const Contents = styled.div`
    position: absolute;
    display: flex;
    flex-direction: column;
    
    right:50px;
    width: 100%;
    height: 100%;
    align-items: center;
    right:2px;
`

const FirstLine = styled.div`
    display: flex;
    border-bottom : 0.1px solid #d3d3d3;
    width: auto;
    height: 10%;
`
const Deletebtn = styled.button`
      float: left;
      display:flex;
      width: 58px;
      padding-left:10px;
      height:fit-content;
      margin-top: 10px;
      font-size: 30px;
      color: #000;
`
const Nextbtn = styled.button`
    float: right;
    display:flex;
    width: 58px;
    height:fit-content;
    margin-top: 15px;
    font-size: 15px;
    font-weight:bold;
    color: #0095f6;
`

export default Upload