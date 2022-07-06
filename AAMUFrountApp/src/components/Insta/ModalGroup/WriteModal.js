import { faPaperPlane, faTimesCircle } from '@fortawesome/free-regular-svg-icons';
import { faImage } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect, useRef, useState } from 'react'
import styled from 'styled-components'

const Modal = ({setIsOpen}) => {

    const [attachment, setClearAttachment] = useState(false);

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
                <form className="form_rweets" >{/*onSubmit={onSubmit}*/}
            <div className="preview_container">
                <>
                    <label className="rweet_file_btn" htmlFor="input-file">
                        <FontAwesomeIcon icon={faImage} size="4x" />
                    </label>
                    <input
                        type="file"
                        id="input-file"
                        accept="image/*"
                        //onChange={onFileChange}
                    />
                </>
                {attachment && (
                    <div>
                        <img
                            className="real_preview"
                            src={attachment}
                            alt="attachment"
                            width="235.6px"
                            height="220px"
                        />
                        <button className="preview_cancel" onClick={setClearAttachment}>
                            <FontAwesomeIcon icon={faTimesCircle} size="1x" />
                        </button>
                    </div>
                )}
            </div>
            <textarea
                className="form_rweets_text"
                //value={rweet}
                //onChange={onChange}
                placeholder="What's on your mind?"
                maxLength={120}
            ></textarea>
            <div className="form_rweet_controller">
                <label className="rweet_submit_btn" htmlFor="input-submit">
                    <FontAwesomeIcon icon={faPaperPlane} size="2x" />
                </label>
                <input type="submit" id="input-submit" value="Rweet" />
            </div>
        </form>
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
    width: fit-content;
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