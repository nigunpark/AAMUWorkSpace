import axios from 'axios';
import React, { useCallback, useEffect, useState } from 'react'
import styled from 'styled-components';

const MyPostBox = ({selectRbn}) => {
    const [detailPostData, setDetailPostData] = useState();
    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");
    const [tag, setTag] = useState();
    let [writeTag, setWriteTag] = useState([]);

    //--------------------------------이미지 시작--------------------------------
    const [showImages, setShowImages] = useState([]);

    //이미지 등록
    const handleAddImages = (e) => {
        const imageLists = e.target.files;
        let imageUrlLists = [...showImages];

        for (let i = 0; i < imageLists.length; i++) {
        const currentImageUrl = URL.createObjectURL(imageLists[i]);
        imageUrlLists.push(currentImageUrl);
        }

        if (imageUrlLists.length > 6) { //사진 6개 제한
        imageUrlLists = imageUrlLists.slice(0, 6);
        }

        setShowImages(imageUrlLists);
    };

  const handleDeleteImage = (id) => { //등록한 사진 삭제
    setShowImages(showImages.filter((_, index) => index !== id));
  };
  //--------------------------------이미지 끝--------------------------------

    const canSubmit = useCallback(() => {
        return content !== "" && title !== "";
      }, [title, content]);

    let test = [1,2,3,4];


    useEffect(()=>{
        let token = sessionStorage.getItem("token");
        axios.get('/aamurest/planner/selectonemap',{
            params:{
                // id:sessionStorage.getItem('username'),
                rbn:selectRbn
            },
            headers: {
                Authorization: `Bearer ${token}`,
            }
        }).then((resp)=>{
            console.log('글 작성 페이지에서 상세경로 데이터 확인 : ',resp.data);
            setDetailPostData(resp.data);
    
        }).catch((error)=>{
            console.log((error) => console.log("상세경로 가져오기 실패", error));
        });
        
      },[]);


      console.log('detailPostData :', detailPostData);

  return (
    <div className="MyWrite-container">

      <div className="write-box plan">
        <div className="plan-title">여행경로</div>

        <div style={{border:'1.5px solid #edf2f4'}}>
          {
            test.map((val, idx)=>{
              return(
                <div className="detail-plan">
                  <span className="paln-date">{val} 일차 20xx-xx-xx x요일</span>
                  <div>00:00 ~ 00:00</div>
                  <div className="plan-region">제주공항</div>

                  <div>00:00 ~ 00:00</div>
                  <div className="plan-region">제주공항</div>
                </div>
              )
            })
          }
        </div>

      </div>

      <div className="write-box">
        <input
        onChange={(e)=>{
          setTitle(e.target.value);
        }}
        name="title"
        type="text"
        className="wirte-title"
        placeholder="제목을 입력하세요"
        value={title}/>
      </div>

      <div className="write-box">
          <input
            multiple
            className="write-picture-input"
            type='file' id='upload'
            onChange={handleAddImages}
            onClick={(e)=>e.target.value = null}/>
          <label className="write-picture-label" for='upload'>
            Img Upload
          </label>
      </div>

      {/* <div className="write-box">
        <Imgs src='/images/imageMap.png'/>
      </div> */}
        <div className="write-box add-delete">
          {showImages.map((image, id) => (
            <Imgs
              src={image}
              alt={`${image}-${id}`}
              onClick={() => handleDeleteImage(id)}/>
          ))}
        </div>

      <div className="write-box writer">
        <textarea
          onChange={(e) => {
            setContent(e.target.value);
          }}
          name="content"
          className="write-section"
          placeholder="글 쓰기"
          value={content}/>
        <div className="box-gab"></div>
        <input
          onChange={(e) => {
            setTag(e.target.value);
          }}
          type='text'
          className="tag-section"
          placeholder="#tag"
          value={tag}/>
      </div>

      <div className="write-box" style={{textAlign: 'end'}}>
        {/* <div className='detail-button'> */}
        {
        canSubmit() ? (
          <button
            style={{color:'black'}}
            className="navbar-btn"
            type="button"
            onClick={()=>{
              let write = uploadFile(showImages);
              bordWrite(write, title, content, tag, writeTag, setWriteTag);
            }}>공유하기</button>
          ) : (
            <button  type="button" disabled>제목과 내용을 모두 입력하세요</button>
          )
        }
        {/* </div> */}
      </div>

    </div>
  )
}

function uploadFile(showImages){//이미지 업로드
    let formData = new FormData(); // formData 객체를 생성한다.
    for (let i = 0; i < showImages.length; i++) { 
      formData.append("photo", showImages[i]); // 반복문을 활용하여 파일들을 formData 객체에 추가한다
    }
    return formData;
  };
  function bordWrite(write, title, content, tag, writeTag, setWriteTag){
  
    // setWriteTag(tag.split('#'));
    // writeTag.splice(0,1);
    // console.log('writeTag :', writeTag);
  
    write.append('id', sessionStorage.getItem('username'));
    write.append('title', title);
    write.append('content', content);
    // write.append('writeTag', writeTag);
  
    let token = sessionStorage.getItem("token");
    axios.post('/aamurest/bbs/edit',write, {
        headers: {
                Authorization: `Bearer ${token}`,
                'Content-Type': 'multipart/form-data',
            }
        }
    )
    .then((resp) => {
        console.log(resp.data);
    })
    .catch((error) => {
        console.log(error);
    });
  }

const Imgs = styled.img`
  width: 100%;
  height: 100%;
  overflow: hidden;
  object-fit: contain;
  position: relative;
`

export default MyPostBox