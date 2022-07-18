import axios from 'axios';
import React, { useEffect } from 'react'

const MyBox = ({setClickTab}) => {

    const myTravel = () => {
        let token = sessionStorage.getItem("token");
    
        axios.get('/planner/selectList',{
            params:{
                id:sessionStorage.getItem('token')
            },
            headers: {
                Authorization: `Bearer ${token}`,
            }
        }).then((resp)=>{
            
            console.log('데이터 형태 : ',resp.data);
        }).catch((error)=>{
            console.log((error) => console.log("여행경로 가져오기 실패", error));
        });
    };

  return (
    <div className="project-box-wrapper">
        <div className="project-box"> {/*  style="background-color: #fee4cb;" */}
            <div className="project-box-header">
                {/* <span>December 10, 2020 저장일</span> */}

                {/* <div className="more-wrapper">
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
                </div> */}
            </div>

            <div className="project-box-content-header">
                <img className='MapImgSize' src='/images/img-3.jpg' style={{marginTop:'10px'}}/>
                {/* 저장한 경로 */}
            </div>

            <div className="box-progress-wrapper">
                <p className="box-progress-header" style={{marginTop:'10px',marginBottom:'10px'}}>
                    여행 이름 : 여행이름
                </p>
                <p className="box-progress-header" style={{marginBottom:'10px'}}>
                    최종 수정 : 2022-07-12
                </p>
                <p className="box-progress-header">여행 일시 : 2022-07-12 ~ 2022-07-16</p>
                {/* <div className="box-progress-bar">
                    <span className="box-progress"></span>
                </div> */}
                {/* <p className="box-progress-percentage">60%</p> */}
            </div>

            <div className="project-box-footer" style={{marginTop:'5px'}}>
                <div className='detail-button'>
                    <button className="learn-more" type="button" onClick={()=>{setClickTab(10)}}>공유하기</button>
                </div>
                <div className='detail-button'>
                    <button className="learn-more" type="button" style={{marginTop:'20px'}} onClick={myTravel}>일정보기</button>
                </div>
            </div>
            <div className="project-box-footer" style={{marginTop:'5px'}}>
                {/* <div className='detail-button'>
                    <button className="learn-more" type="button">수정하기</button>
                </div>
                <div className='detail-button' style={{marginTop:'20px'}}>
                    <button className="learn-more" type="button">삭제하기</button>
                </div> */}
            </div>
        </div>
    </div>
  )
}

{/* <div className="participants"> */}
        {/* <img src="https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=2550&q=80" alt="participant"/>
        <img src="https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?ixid=MXwxMjA3fDB8MHxzZWFyY2h8MTB8fG1hbnxlbnwwfHwwfA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=900&q=60" alt="participant"/>
    
        <button className="add-participant">
        버튼 태그에 있던거  style="color: #ff942e;"
            <svg 
                xmlns="http://www.w3.org/2000/svg" 
                width="12" height="12" 
                viewBox="0 0 24 24" fill="none" 
                stroke="currentColor" stroke-width="3" 
                stroke-linecap="round" stroke-linejoin="round"
                className="feather feather-plus">
                <path d="M12 5v14M5 12h14" />
            </svg>
        </button> */}
        
    {/* </div> */}

    {/* <div className="days-left">
        style="color: #ff942e;"
    2 Days Left
    </div> */}

export default MyBox