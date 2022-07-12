import {useEffect, useState} from 'react';
import {Button} from "@mui/material";
import axios from 'axios';
import styled from 'styled-components';

const Uploader = () => {
const [hide, setHide] = useState(false);
  const [image, setImage] = useState({
    image_file: "",
    preview_URL: "img/photo-film-solid.jpg",
  });

  let inputRef;

  const saveImage = (e) => {
    e.preventDefault();
    if(e.target.files[0]){
      // 새로운 이미지를 올리면 createObjectURL()을 통해 생성한 기존 URL을 폐기
      URL.revokeObjectURL(image.preview_URL);
      const preview_URL = URL.createObjectURL(e.target.files[0]);
      setImage(() => (
        {
          image_file: e.target.files[0],
          preview_URL: preview_URL
        }
      ))
    }
  }

  const deleteImage = () => {
    // createObjectURL()을 통해 생성한 기존 URL을 폐기
    URL.revokeObjectURL(image.preview_URL);
    setImage({
      image_file: "",
      preview_URL: "img/photo-film-solid.jpg",
    });
    setHide(false)
  }

  useEffect(()=> {
    // 컴포넌트가 언마운트되면 createObjectURL()을 통해 생성한 기존 URL을 폐기
    return () => {
      URL.revokeObjectURL(image.preview_URL)
    }
  }, [])

  const sendImageToServer = async () => {
    if (image.image_file) {
      const formData = new FormData()
      formData.append('file', image.image_file);
      await axios.post('/api/image/upload', formData);
      alert("서버에 등록이 완료되었습니다!");
      setImage({
        image_file: "",
        preview_URL: "img/photo-film-solid.jpg",
      });
    } else {
      alert("사진을 등록하세요!")
    }
  }

  $('#contentText').keyup(function (e){
    var content = $(this).val();       
    $('#counter').val(100-content.length);

    if(content.length > 100) {
    $(this).val($(this).val().substring(0, 100));
    }
    });

  return (
  <ModalWrap>
    <Contents>   
        <FirstLine>
            <Deletebtn 
                variant="contained" onClick={deleteImage} > 
                <i class="fa-solid fa-arrow-left"></i>
            </Deletebtn> 
            <div className='newPosting'>
                    <h2>새 게시물 만들기</h2>
                </div>
                <Nextbtn  onClick={sendImageToServer}>다음</Nextbtn>
        </FirstLine>
            <div className='UploadEntire'>           
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
                                <Button 
                                    type="primary" 
                                    variant="contained" 
                                    onClick={() => inputRef.click()}>
                                    컴퓨터에서 선택
                                </Button>}
                        </label>
                        <input  
                                id="input-file"
                                type="file" 
                                accept="image/*"
                                onChange={saveImage}
                            // 클릭할 때 마다 file input의 value를 초기화 하지 않으면 버그가 발생할 수 있다
                            // 사진 등록을 두개 띄우고 첫번째에 사진을 올리고 지우고 두번째에 같은 사진을 올리면 그 값이 남아있음!
                                onClick={(e) => e.target.value = null}
                                ref={refParam => inputRef = refParam}
                                style={{display: "none" , width:'100%',height:'100%'}}
                        />
                        <img className='divimage' alt="sample" src={image.preview_URL}/>
                    </div>
                <div className='side'>
                    <div className="title-profile">
                        <img src="./img/bk.jpg" alt="프사" />                
                        <span>eyesmag</span> 
                    </div>
                    <textarea 
                        cols="35" rows="10" 
                        placeholder="Input some text." 
                        style={{border:'none',resize: 'none',
                                fontSize: '20px',fontFamily:'normal',
                                outline: 'none',padding:'7px' }}>
                    </textarea>
                    <textarea name="contentText" id="contentText" cols="30" rows="10" ></textarea>
                     <span class="txsub">남은글자수 : <input type="text" readonly  value="100" name="counter" id="counter"/></span> 
                </div>
            </div>
        </Contents>
    </ModalWrap>
  );
}

const ModalWrap = styled.div`
    width: 900px;
    overflow: hidden;
    height:650px;
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
    
    right:50px;
    width: 100%;
    height: 100%;
    align-items: center;
    right:2px;
`

const FirstLine = styled.div`
    display: flex;
    border-bottom : 0.1px solid #d3d3d3;
    width: 100%;
    height: 8%;
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
    font-size: 20px;
    font-weight:bold;
    color: #0095f6;
`
export default Uploader;