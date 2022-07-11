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

      {countRef.current.value.length != 0 &&
          setFileImage(URL.createObjectURL(event.target.files[0]));}
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
            <>
            <FirstLine>
                <Deletebtn 
                        onClick={() => deleteFileImage()} > 삭제 </Deletebtn> 
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
                        {hide != true?
                        (<FontAwesomeIcon icon={faImage} size="4x"/>)
                        :
                        (
                            countRef.current.value.length != 0 ?
                            null
                            :<FontAwesomeIcon icon={faImage} size="4x"/>
                        )
                        
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
            </>
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
    width: 100%;
    height: 100%;
    align-items: center;
    right:2px;
    padding:10px;
`

const FirstLine = styled.div`
    display: flex;
    
    width: 100%;
    height: 10%;
`
const Deletebtn = styled.button`
    float: left;
      padding: 5px;
      margin-top: 15px;
      margin-left: 10px;
      font-size: 15px;
      border-radius: 5px;
      color: #ebebeb;
      background-color: #0095f6;
      border: 1px solid #0095f6;
`
const Nextbtn = styled.button`
    float: right;
    padding: 5px;
    margin-top: 15px;
    margin-right: 15px;
    font-size: 10px;
    border-radius: 5px;
    color: #ebebeb;
    background-color: #0095f6;
    border: 1px solid #0095f6;
`

export default Upload