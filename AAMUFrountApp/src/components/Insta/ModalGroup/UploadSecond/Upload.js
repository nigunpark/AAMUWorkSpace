import {useEffect, useRef, useState} from 'react';
import {Button} from "@mui/material";
import axios from 'axios';
import styled from 'styled-components';
import $, { escapeSelector } from 'jquery';
import SearchModal from './SearchModal'

const Uploader = () => {
let searchRef = useRef();
const [hide, setHide] = useState(false);
const [showNext, setshowNext] = useState(false);
const [search, setSearch] = useState([]);
const [showSearch, setshowSearch] = useState(false);
const [show] = useState(false);

  const [image, setImage] = useState({
    image_file: "",
    preview_URL: "img/image.jpg",
  });

  function handleModal(e) {
    e.stopPropagation();
    if (e.target !== searchRef.current) setshowSearch(false);
  }
  window.addEventListener("click", handleModal);

  

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
  // const deleteFileImage = () =>{
  //   URL.revokeObjectURL(fileImage);
  //   setFileImage("");
  //   setHide(false)
    
  // console.log('Ref', countRef.current.value)
  // };
  {/* <button style={{
                    width: "50px",
                    height: "50px",
                    cursor: "pointer",
                    marginBottom:'-50px'}}
                    onClick={() => deleteFileImage()} > 삭제 </button>  */}

  const deleteImage = () => {
    // createObjectURL()을 통해 생성한 기존 URL을 폐기
    URL.revokeObjectURL(image.preview_URL);
    setImage({
      image_file: "",
      preview_URL: "img/image.jpg",
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
      await axios.get('/aamurest/gram/place/selectList', formData,);
      alert("서버에 등록이 완료되었습니다!");
      setImage({
        image_file: "",
        preview_URL: "img/image.jpg",
      });
    } else {
      alert("사진을 등록하세요!")
    }
  }

 

  function searchWord(val,setSearch){
    console.log(val);
    let token = sessionStorage.getItem("token");
    axios.get('/aamurest/gram/place/selectList',{
      headers: {
            Authorization: `Bearer ${token}`,
          },
          params: {
            searchWord: val,
          },
    })
    .then((resp) => {
      console.log(resp.data);
      setSearch(resp.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }


  function gramEdit(val){
    let token = sessionStorage.getItem("token");
    axios.post('/aamurest/gram/edit',{
      headers: {
            Authorization: `Bearer ${token}`,
          },
          id:'id',
          ctitle:'ctitle',
          content :'content',
          photo :'photo',
          contentid :'contentid',

        })
    .then((resp) => {
      console.log(resp.data);
      
      })
      .catch((error) => {
        console.log(error);
      });
    }


  function fn_checkByte(obj){
    const maxByte = 1000; //최대 100바이트
    const text_val = obj.target.value; //입력한 문자
    const text_len = text_val.length; //입력한 문자수
    
    let totalByte=0;
    for(let i=0; i<text_len; i++){
    	const each_char = text_val.charAt(i);
        const uni_char = escapeSelector(each_char); //유니코드 형식으로 변환
        if(uni_char.length>4){
        	// 한글 : 2Byte
            totalByte += 2;
        }else{
        	// 영문,숫자,특수문자 : 1Byte
            totalByte += 1;
        }
    }
    
    if(totalByte>maxByte){
    	alert('최대 1000Byte까지만 입력가능합니다.');
        	document.getElementById("nowByte").innerText = totalByte;
            document.getElementById("nowByte").style.color = "red";
        }else{
        	document.getElementById("nowByte").innerText = totalByte;
            document.getElementById("nowByte").style.color = "green";
        }
    }
  return (
  
    <Contents>   
        <FirstLine>
            <Deletebtn 
                variant="contained" onClick={deleteImage} > 
                <i className="fa-solid fa-arrow-left"></i>
            </Deletebtn> 
            <div className='newPosting'>
                <h2>새 게시물 만들기</h2>
            </div>
            {showNext ? 
              <Nextbtn  onClick={sendImageToServer}>업로드</Nextbtn>
            :
              <Nextbtn  onClick={()=>setshowNext(!showNext)}>다음</Nextbtn>
            }    
        </FirstLine>
            <Body>           
                <div 
                    className='picfileframe' >
                  <label 
                    className="rweet_file_btn" 
                    onClick={ ()=>{setHide(!hide)} }
                    htmlFor="input-file"                
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
                    {/* {showNext ?  */}
                    <div className='side'>
                      <div className="title-profile">
                          <img src="./img/bk.jpg" alt="프사" />                
                          <span>eyesmag</span> 
                      </div>
                      <div>
                        <span style={{fontWeight:'bold', marginLeft:'10px'}}>제목 : </span><input type="text" placeholder="제목을 입력하세요"/>
                      </div>
                      <div>
                        <textarea 
                          className="form-control" 
                          id="textArea_byteLimit" 
                          name="textArea_byteLimit" 
                          onKeyUp={(e)=>fn_checkByte(e)}
                          rows="8" 
                          placeholder="문구입력..." 
                          style={{border:'none',resize: 'none',
                              fontSize: '16px',fontFamily:'normal',
                              outline: 'none',paddingTop:'5px' ,
                              marginLeft:'10px',width:'90%',position:'relative'}}>
                        </textarea>    
                      </div>
                      <div style={{borderBottom:'0.1px solid #c0c0c0',width:'97%',height:'27px'}}>
                        <sup style={{float:'right',paddingRight:'15px',color:'#c0c0c0'}}>(<span id="nowByte">0</span>/1000bytes)</sup>
                      </div>                
                      <div className='uploadLocation' onClick={()=>{setshowSearch(!showSearch);}}>
                        <input 
                            onKeyUp={(e)=>searchWord(e.target.value,setSearch)}
                            placeholder="위치 추가" 
                            type="text"
                            ref={searchRef}/>
                            {showSearch ? <SearchModal search={search}></SearchModal> : null}
                        <i class="fa-solid fa-location-dot"></i>
                      </div>                
                  </div>
                  {/* // :null} */}
                  </Body>
                  {/* {show?<SearchModal search={search}/>:null} */}
        </Contents>

 
  );
}


const Contents = styled.div`
  position: relative;
  top:30px;
  padding : 0 auto;
  width:60%;
  // min-width:30%;
  // max-width:60%;
  height: 700px;
  background :white;
  display:flex;
  flex-direction:column;
  border-radius:7px;
`

const FirstLine = styled.div`
  height:auto;
  display:flex;
  justify-content:space-around;
  align-items:center;
  border-bottom:0.1px solid rgb(211, 211, 211) ;

`
const Body = styled.div`
  display: flex;
  height: 100%;
`

const Deletebtn = styled.button`
  font-size : 20px;
`
const Nextbtn = styled.button`
  font-size:19px;
  color:blue;
  font-weight:bold;
`
export default Uploader;