import React from 'react'

const MyHomeBox = ({setClickTab, planList}) => {
    // console.log('잘 넘어왔나 :',planList);

    const postDate = new Date(planList.routeDate);
    // console.log('postDate :', postDate);

    function dateFormat(date) {
        let month = date.getMonth() + 1;
        let day = date.getDate();

        month = month >= 10 ? month : '0' + month;
        day = day >= 10 ? day : '0' + day;

        return date.getFullYear() + '-' + month + '-' + day;
    };
    // console.log('dateFormat(postDate)',dateFormat(postDate));

    const randomNum = [1,2,3,4,5,6,7,8,9].length;
    const imgNum = Math.floor(Math.random() * randomNum)+1;

    // console.log('Math.floor(Math.random() * randomNum) :',Math.floor(Math.random() * randomNum)+1);

    const myTravel = () => {
        
    };
    // transform: scale(1.07);
    // project-box-wrapper 이거 css 703번 줄에 있음 &-wrapper 으로 검색
  return (
    <div className="project-box-wrapper">{/* style={{backgroundColor: '#fee4cb'}} */}
        <div className="project-box" >
            {/* <div className="project-box-header">
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
            </div> */}

            <div className="project-box-content-header">
                <img className='MapImgSize' src={'/images/img-'+imgNum+'.jpg'} style={{marginTop:'10px'}}/>
                {/* 저장한 경로 */}
            </div>

            <div className="box-progress-wrapper">
                <p className="box-progress-header" style={{marginTop:'10px',marginBottom:'10px'}}>
                    여행 제목 : {planList.title}
                </p>
                <p className="box-progress-header" style={{marginBottom:'10px'}}>
                    저장 날자 : {dateFormat(postDate)}
                </p>
                <p className="box-progress-header">{planList.plannerdate} (rbn :{planList.rbn})</p>
            </div>

            <div className="project-box-footer" style={{marginTop:'5px'}}>
                <div className='detail-button'>
                    <button className="learn-more" type="button" onClick={()=>{setClickTab(10)}}>공유하기</button>
                </div>
                <div className='detail-button'>
                    <button className="learn-more" type="button" style={{marginTop:'20px'}} onClick={myTravel}>일정보기</button>
                </div>
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

export default MyHomeBox