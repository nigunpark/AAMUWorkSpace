import React from 'react'

const MyProfileBox = () => {
  return (
    <div className="profile_project-box-wrapper">
        <div className="profile-project-box">
            <div className="project-box-header">
                <span>가입일. December 10, 2020</span>

                <div className="more-wrapper">
                    ㅇㅅㅇ
                </div>
            </div>
            
            <div className="project-box-content-header">
                <img className='myProfiles' src='/images/profile.jpg'/>
                <div className='asdqwe'>
                <p className="profile_box-content-header">
                    <h5>이름: <input type='text' value='뚱이' disabled/></h5>
                    <h5>아이디: <input type='text' value='kim9898' disabled/></h5>
                    <h5>비밀번호: <input type='password' placeholder='기존 비밀번호'/></h5>
                </p>
                </div>
            </div>

            <div className="box-progress-wrapper">
                <div className="box-progress-bar">
                    <span className="box-progress"></span>
                </div>
            </div>

            <div className="project-box-footer">
                <div className="participants">
                    <div className='detail-button'>
                        <button className="learn-more" type="button">회원탈퇴</button>
                    </div>
                </div>
                <div className='detail-button'>
                    <button className="learn-more" type="button">저장</button>
                </div>
            </div>
        </div>
    </div>
  )
}

export default MyProfileBox