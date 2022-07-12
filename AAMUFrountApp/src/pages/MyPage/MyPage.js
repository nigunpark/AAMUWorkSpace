import React, { useCallback, useEffect, useRef, useState } from "react";
import "./MyPage.scss";
import MyHomeBox from "./MyPageBox/MyHomeBox";
import MyHomeTopLine from "./MyPageBox/MyHomeTopLine";
import MyProfileBox from "./MyPageBox/MyProfileBox";
import MyProfileTopLine from "./MyPageBox/MyProfileTopLine";
import MyMessageBar from "./MyMessageBar/MyMessageBar";
import styled from "styled-components";
import { faStar } from "@fortawesome/free-regular-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
const MyPage = () => {

  

  // state = {
  //   title: '',
  //   content: '',
  // };

  let [clickTab, setClickTab] = useState(0);

  let home = useRef();
  let two = useRef();
  let three = useRef();
  let setting = useRef();

  useEffect(() => {
    if (clickTab === 0) {
      home.current.classList.add("active");
      // two.current.classList.remove("active");
      three.current.classList.remove("active");
      setting.current.classList.remove("active");
    }
    else if (clickTab === 1) {
      home.current.classList.remove("active");
      // two.current.classList.add("active");
      three.current.classList.remove("active");
      setting.current.classList.remove("active");
    }
    else if (clickTab === 2) {
      home.current.classList.remove("active");
      // two.current.classList.remove("active");
      three.current.classList.add("active");
      setting.current.classList.remove("active");
    }
    else if (clickTab === 3)  {
      home.current.classList.remove("active");
      // two.current.classList.remove("active");
      three.current.classList.remove("active");
      setting.current.classList.add("active");
    }
  }, [clickTab]);
  return (
    <div className="app-container">
      <div className="app-header">
        {/* <div className="app-header-left">
        <span className="app-icon"></span>
        <p className="app-name">Portfolio</p>
          <svg 
            xmlns="http://www.w3.org/2000/svg" 
            width="20" height="20" fill="none" 
            stroke="currentColor" strokeLinecap="round" 
            strokeLinejoin="round" strokeWidth="2" 
            className="feather feather-search" 
            viewBox="0 0 24 24">
            <defs></defs>
             <svg
              className="moon" 
              fill="none" 
              stroke="currentColor" strokeLinecap="round" 
              strokeLinejoin="round" strokeWidth="2" 
              width="24" height="24" viewBox="0 0 24 24">
              <defs></defs>
              <path d="M21 12.79A9 9 0 1111.21 3 7 7 0 0021 12.79z"></path>
              width="16" height="16" 
              viewBox="0 0 24 24" fill="none" 
              stroke="currentColor" 
              strokeWidth="3" strokeLinecap="round" 
              strokeLinejoin="round" class="feather feather-plus">
              <line x1="12" y1="5" x2="12" y2="19" />
              <line x1="5" y1="12" x2="19" y2="12" />
            </svg>
              xmlns="http://www.w3.org/2000/svg" 
              width="24" height="24" 
              viewBox="0 0 24 24" fill="none" 
              stroke="currentColor" strokeWidth="2" 
              strokeLinecap="round" strokeLinejoin="round" 
              className="feather feather-bell">
              <path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9" />
              <path d="M13.73 21a2 2 0 0 1-3.46 0" /></svg>

               <button className="profile-btn">
            <img src="/images/profile.jpg" />
            <span>뚱이</span>
          </button>
        </div>
            xmlns="http://www.w3.org/2000/svg"
            width="20" height="20" 
            viewBox="0 0 24 24" fill="none" 
            stroke="currentColor" strokeWidth="2" 
            strokeLinecap="round" strokeLinejoin="round" 
            className="feather feather-message-circle">
            <path d="M21 11.5a8.38 8.38 0 0 1-.9 3.8 8.5 8.5 0 0 1-7.6 4.7 8.38 8.38 0 0 1-3.8-.9L3 21l1.9-5.7a8.38 8.38 0 0 1-.9-3.8 8.5 8.5 0 0 1 4.7-7.6 8.38 8.38 0 0 1 3.8-.9h.5a8.48 8.48 0 0 1 8 8v.5z" />
          </svg>
        </button> */}
      </div>
      {/* let [clickTab, setClickTab] = useState(0); */}
      <div className="app-content">
        {/* <MySideBar/> 왼쪽 사이드바 */}
        <div className="app-sidebar">
          <button
            ref={home}
            className="app-sidebar-link "
            onClick={() => {
              setClickTab(0);
            }}
            >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="24"
              height="24"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              strokeWidth="2"
              strokeLinecap="round"
              strokeLinejoin="round"
              className="feather feather-home"
              >
              <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z" />
              <polyline points="9 22 9 12 15 12 15 22app-content" />
            </svg>
          </button>
          
          {/* <button
            ref={two}
            className="app-sidebar-link"
            onClick={() => {
              setClickTab(1);
            }}
            >
            <svg
              className="link-icon feather feather-pie-chart"
              xmlns="http://www.w3.org/2000/svg"
              width="24"
              height="24"
              fill="none"
              stroke="currentColor"
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              viewBox="0 0 24 24"
              >
              <defs />
              <path d="M21.21 15.89A10 10 0 118 2.83M22 12A10 10 0 0012 2v10z" />
            </svg>
          </button> */}

          <button
            ref={three}
            className="app-sidebar-link"
            onClick={() => {
              setClickTab(2);
            }}
            >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="24"
              height="24"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              strokeWidth="2"
              strokeLinecap="round"
              strokeLinejoin="round"
              className="feather feather-calendar"
              >
              {/* <rect x="3" y="4" width="18" height="18" rx="2" ry="2" />
              <line x1="16" y1="2" x2="16" y2="6" />
              <line x1="8" y1="2" x2="8" y2="6" />
              <line x1="3" y1="10" x2="21" y2="10" /> */}
              <FontAwesomeIcon icon={faStar} />
            </svg>
          </button>

          <button
            ref={setting}
            className="app-sidebar-link"
            onClick={() => {
              setClickTab(3);
            }}
            >
            <svg
              className="link-icon feather feather-settings"
              xmlns="http://www.w3.org/2000/svg"
              width="24"
              height="24"
              fill="none"
              stroke="currentColor"
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              viewBox="0 0 24 24"
              >
              <defs />
              <circle cx="12" cy="12" r="3" />
              <path d="M19.4 15a1.65 1.65 0 00.33 1.82l.06.06a2 2 0 010 2.83 2 2 0 01-2.83 0l-.06-.06a1.65 1.65 0 00-1.82-.33 1.65 1.65 0 00-1 1.51V21a2 2 0 01-2 2 2 2 0 01-2-2v-.09A1.65 1.65 0 009 19.4a1.65 1.65 0 00-1.82.33l-.06.06a2 2 0 01-2.83 0 2 2 0 010-2.83l.06-.06a1.65 1.65 0 00.33-1.82 1.65 1.65 0 00-1.51-1H3a2 2 0 01-2-2 2 2 0 012-2h.09A1.65 1.65 0 004.6 9a1.65 1.65 0 00-.33-1.82l-.06-.06a2 2 0 010-2.83 2 2 0 012.83 0l.06.06a1.65 1.65 0 001.82.33H9a1.65 1.65 0 001-1.51V3a2 2 0 012-2 2 2 0 012 2v.09a1.65 1.65 0 001 1.51 1.65 1.65 0 001.82-.33l.06-.06a2 2 0 012.83 0 2 2 0 010 2.83l-.06.06a1.65 1.65 0 00-.33 1.82V9a1.65 1.65 0 001.51 1H21a2 2 0 012 2 2 2 0 01-2 2h-.09a1.65 1.65 0 00-1.51 1z" />
            </svg>
          </button>
        </div>

        <div className="projects-section">
          <div className="projects-section-header">
            <Title clickTab={clickTab}/>
            <p className="time">December, 12</p>
          </div>
          <div className="projects-section-line">
            {/* <MyHomeTopLine/> */}
            <TabTopLine clickTab={clickTab} />
          </div>
          <div className="project-boxes jsListView"> {/* jsGridView */}
            {/* <MyHomeBox/> */}
            <TabContent
            clickTab={clickTab} setClickTab={setClickTab}/>
          </div>
        </div>

        <div className="messages-section">
          {/* <button className="messages-close">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="24"
              height="24"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="2"
              stroke-linecap="round"
              stroke-linejoin="round"
              className="feather feather-x-circle"
            >
              <circle cx="12" cy="12" r="10" />
              <line x1="15" y1="9" x2="9" y2="15" />
              <line x1="9" y1="9" x2="15" y2="15" />
            </svg>
          </button> */}
          <div className="projects-section-header">
            <p>알림</p>
          </div>
          <div className="messages">
            <MyMessageBar />
          </div>
        </div>
      </div>
    </div>
  );
};



function Title({clickTab}){
  if (clickTab === 0) {
    return <div className="projects-title">MyPage</div>;
  }
  else if (clickTab === 1) {
    return <div className="projects-title">MyPage</div>;
  }
  else if (clickTab === 2) {
    return <div className="projects-title">즐겨찾기</div>;
  }
  else if (clickTab === 3) {
    return <div className="projects-title">프로필 수정</div>;
  }

}



//-----------------------------------------------------------------------
function TabContent({clickTab,setClickTab}) {
  
  //--------------------------------이미지 시작--------------------------------
  const [showImages, setShowImages] = useState([]);

  console.log('등록한 이미지:',showImages);

  //이미지 등록
  const handleAddImages = (e) => {
    const imageLists = e.target.files;
    let imageUrlLists = [...showImages];

    for (let i = 0; i < imageLists.length; i++) {
      const currentImageUrl = URL.createObjectURL(imageLists[i]);
      imageUrlLists.push(currentImageUrl);
    }

    if (imageUrlLists.length > 5) { //사진 5개 제한
      imageUrlLists = imageUrlLists.slice(0, 5);
    }

    setShowImages(imageUrlLists);
  };

  const handleDeleteImage = (id) => { //등록한 사진 삭제
    setShowImages(showImages.filter((_, index) => index !== id));
  };
  //--------------------------------이미지 끝--------------------------------



  
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [tag, setTag] = useState("");

  console.log('입력한 제목:',title);
  console.log('입력한 내용:',content);
  console.log('입력한 태그:',tag);
  // const [image, setImage] = useState({
  //   image_file: "",
  //   // preview_URL: "image/default_image.png",
  // });

  const canSubmit = useCallback(() => {
    return content !== "" && title !== "";
  }, [title, content]); //이 코드 필요없을수도있음

  // const handleSubmit = () => {
  //   console.log('입력한 제목 handleSubmit :',title);
  //   console.log('등록한 사진 handleSubmit :',showImages);

  //   if (content.length===0 || title.length===0 ) {
  //     alert("제목과 내용을 입력하세요");
  //     return;
  //   }

  //   // try{
  //     const formData = new FormData();
  //     formData.append("title", title);
  //     formData.append("content", content);
  //     formData.append("file", showImages);
  //     // formData.append("user_id", jwtUtils.getId(token));

  //     // await api.post("/api/board", formData);
  //     // window.alert("😎등록이 완료되었습니다😎");
  //     console.log('입력한 제목 formData :',formData.title);
  //   //   return formData;
      
  //   //   // navigate("/board-list");
  //   // } catch (e) {
  //   //   window.alert("등록을 실패했습니다");
  //   // }
  // };


  

  let totalEdit = [1, 2, 3, 4];

  if (clickTab === 0) {
    return totalEdit.map(() => {
      return <MyHomeBox setClickTab={setClickTab}/>;
    });
  }
  else if (clickTab === 1) {
    return <div>Tab 2 내용입니다.</div>;
  }
  else if (clickTab === 2) {
    return (
      <div className="project-box-wrapper">
        <div className="project-box">
            <div className="project-box-header">
                <span>December 10, 2020 저장일</span>

                <div className="more-wrapper">
                    <button className="project-btn-more">
                        <svg 
                        xmlns="http://www.w3.org/2000/svg" 
                        width="24" height="24" 
                        viewBox="0 0 24 24" fill="none" 
                        stroke="currentColor" stroke-width="2" 
                        stroke-linecap="round" stroke-linejoin="round"
                        className="feather feather-more-vertical">
                            <circle cx="12" cy="12" r="1" />
                            <circle cx="12" cy="5" r="1" />
                            <circle cx="12" cy="19" r="1" />
                        </svg>
                    </button>
                </div>
            </div>

            <div className="project-box-content-header">
                <img className='MapImgSize' src='/images/imageMap.png'/>
            </div>

            <div className="box-progress-wrapper">
                <p className="box-progress-header">Progress</p>
                <div className="box-progress-bar">
                    <span className="box-progress"></span>
                </div>
            </div>

            <div className="project-box-footer">
                <div className="participants">

                </div>

                <div className='detail-button'>
                    <button className="learn-more" type="button">삭제</button>
                </div>
            </div>
        </div>
    </div>
    );
  }
  else if (clickTab === 3) {
    return <MyProfileBox />;
  }
  else if (clickTab === 10) { //-----------------------Write------------------------
    // const imgFileUpload = (fileBlob) => {
    //   const reader = new FileReader();
  
    //   reader.readAsDataURL(fileBlob);
  
    //   return new Promise((resolve) => {
    //     reader.onload = () => {
    //       setImageSrc(reader.result);
    //       resolve();
    //     };
    //   });
    // };

    return (
    <div className="MyWrite-container">
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
          onChange={handleAddImages}/>
        <label className="write-picture-label" for='upload'>
          사진 고르기
        </label>
      </div>

      <div className="write-box">
        <Imgs src='/images/imageMap.png'/>
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
      
      <div className="write-box add-delete">
        {showImages.map((image, id) => (
          <Imgs
            src={image}
            alt={`${image}-${id}`}
            onClick={() => handleDeleteImage(id)}/>
        ))}
        
      </div>

      <div className="write-box">
        <div className='detail-button'>
        {canSubmit() ? (
          <button className="learn-more" type="button">공유하기</button>
          ) : (
            <button  type="button" disabled>사진과 내용을 모두 입력하세요😭</button>
          )}
        </div>
      </div>

    </div>
    );
  }
}





// function Comp (){
//   useEffect(()=>{
//     axios.get(sdfsd,sdf)
//   },[])
//   return(sdf)
// }

function TabTopLine({clickTab}) {

  if (clickTab === 0) {
    return <MyHomeTopLine />;
  }
  else if (clickTab === 1) {
    return <div>Tab 2 TopLine</div>;
  }
  else if (clickTab === 2) {
    return (
      <div className="projects-status">
        <div className="item-status">
          <span className="status-number">4</span>
          <span className="status-type">Total</span>
        </div>
      </div>
    );
  }
  else if (clickTab === 3) {
    // return <MyProfileTopLine />;
  }
  else if (clickTab === 10) {
    return <div></div>;
  }
}

const Imgs = styled.img`
  width: 100%;
  height: 100%;
  overflow: hidden;
  object-fit: contain;
  position: relative;
`


export default MyPage;