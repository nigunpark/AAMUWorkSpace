import React, { useEffect, useRef, useState } from "react";
import "./MyPage.scss";
import MyHomeBox from "./MyPageBox/MyHomeBox";
import MyHomeTopLine from "./MyPageBox/MyHomeTopLine";
import MyProfileBox from "./MyPageBox/MyProfileBox";
import MyProfileTopLine from "./MyPageBox/MyProfileTopLine";
import MyMessageBar from "./MyMessageBar/MyMessageBar";
import styled from "styled-components";
const MyPage = () => {

  let [clickTab, setClickTab] = useState(0);

  let home = useRef();
  let two = useRef();
  let three = useRef();
  let setting = useRef();

  useEffect(() => {
    if (clickTab === 0) {
      home.current.classList.add("active");
      two.current.classList.remove("active");
      three.current.classList.remove("active");
      setting.current.classList.remove("active");
    }
    else if (clickTab === 1) {
      home.current.classList.remove("active");
      two.current.classList.add("active");
      three.current.classList.remove("active");
      setting.current.classList.remove("active");
    }
    else if (clickTab === 2) {
      home.current.classList.remove("active");
      two.current.classList.remove("active");
      three.current.classList.add("active");
      setting.current.classList.remove("active");
    }
    else if (clickTab === 3)  {
      home.current.classList.remove("active");
      two.current.classList.remove("active");
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
          <button
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
          </button>
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
              <rect x="3" y="4" width="18" height="18" rx="2" ry="2" />
              <line x1="16" y1="2" x2="16" y2="6" />
              <line x1="8" y1="2" x2="8" y2="6" />
              <line x1="3" y1="10" x2="21" y2="10" />
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
            <p>MyPage</p>
            <p className="time">December, 12</p>
          </div>
          <div className="projects-section-line">
            {/* <MyHomeTopLine/> */}
            <TabTopLine clickTab={clickTab} />
          </div>
          <div className="project-boxes jsGridView">
            {/* <MyHomeBox/> */}
            <TabContent clickTab={clickTab} setClickTab={setClickTab}/>
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
            <MyMessageBar />
            <MyMessageBar />
            <MyMessageBar />
            <MyMessageBar />
            <MyMessageBar />
          </div>
        </div>
      </div>
    </div>
  );
};

function TabContent({clickTab,setClickTab}) {

  let totalEdit = [1, 2, 3, 4];

  if (clickTab === 0) {
    return totalEdit.map(() => {
      return <MyHomeBox  clickTab={clickTab} setClickTab={setClickTab}/>;
    });
  }
  else if (clickTab === 1) {
    return <div>Tab 2 내용입니다.</div>;
  }
  else if (clickTab === 2) {
    return <div>Tab 3 내용입니다.</div>;
  }
  else if (clickTab === 3) {
    return <MyProfileBox />;
  }
  else if (clickTab === 10) {
    return (
    <div className="MyWrite-container">
      <div className="write-box">
        <input type='text' className="wirte-title"  placeholder="제목을 입력하세요">
          
        </input>
      </div>

      <div className="write-box">
        <input className="write-picture-input" type='file' id='abcd'></input>
        <label className="write-picture-label" for='abcd'>사진 등록</label>
      </div>

      <div className="write-box">
        <Imgs src='/images/imageMap.png'/>
      </div>

      <div className="write-box writer">
        <textarea className="write-section" placeholder="글 쓰기">
          
        </textarea>
        <div className="box-gab"></div>
        <textarea className="tag-section" placeholder="#tag">
            
        </textarea>
      </div>

      <div className="write-box add-delete">
        <Imgs src='/images/imageMap.png'/>
        <Imgs src='/images/bg1.png'/>
        <Imgs src='/images/bg5.png'/>
        <Imgs src='/images/imageMap.png'/>
        <Imgs src='/images/imageMap.png'/>
        {/*
        첫 사진 - 여행 경로(지도)

        그 외 4개 사진은 직접 찍은거로
        단, 최대 4개까지 업로드 가능한거로
        */}
      </div>

      <div className="write-box">
        <div className='detail-button'>
          <button className="learn-more" type="button">공유하기</button>
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
    return <div>Tab 3 TopLine</div>;
  }
  else if (clickTab === 3) {
    return <MyProfileTopLine />;
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
`


export default MyPage;