import React from 'react'
import styled from 'styled-components'

const MyWrite = () => {
  return (
    <div className="app-container">
      <div className="app-header">
      </div>
      <div className="app-content">
        <div className="app-sidebar">
          
        </div>

        <div className="projects-section">
          <div className="projects-section-header">
            <p>d</p>
            <p className="time">
              <input type='file' id='abcd'></input>
              <label for='abcd'>사진 등록</label>
            </p>
          </div>
          <div className="projects-section-line">
            
          </div>
          <div className="project-boxes jsGridView">
            <Imgs src='/images/imageMap.png'/>
          </div>
        </div>

        <div className="messages-section">
          
          <div className="projects-section-header">
            <p>여기에 글을 쓸까요</p>
          </div>
          <div className="messages">
            아니면 여기에 글을 쓸까요 아래로 내려서
            tag 칸 만드는것도 ㄱㅊ을지도
          </div>
        </div>
      </div>
    </div>
  )
}

const Imgs = styled.img`
  width: 100%;
  height: 100%;
  overflow: hidden;
  object-fit: contain;
`

export default MyWrite