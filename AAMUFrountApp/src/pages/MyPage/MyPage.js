import React from 'react';
import './MyPage.scss';
import MyBox from './MyPageBox/MyBox';

const MyPage = () => {
  return (
    // <div className='MyPage'>
    //     <div className='MyPage_Container'>
    //         <div className='MyPage_Wrapper'>
    //             <div className='MyPage_Profile'>
    //                 <img className='MyProfile_img' src='/images/profile.jpg'/>
                    
    //             </div>
    //             <div>
    //                 MyPage
    //                 </div>
    //         </div>
    //     </div>
    // </div>
    // style={{ marginRight: "3px" }}


    // <div className='app-container'>
    //   <div className="app-header">
    //     <div className="app-header-left">

    //       <span className="app-icon"></span>
    //       <p className="app-name">Portfolio</p>

    //       <div className="search-wrapper">
    //         <input className="search-input" type="text" placeholder="Search"/>
    //         <svg 
    //           xmlns="http://www.w3.org/2000/svg" 
    //           width="20" height="20" fill="none" 
    //           stroke="currentColor" 
    //           stroke-linecap="round" 
    //           stroke-linejoin="round" 
    //           stroke-width="2" 
    //           className="feather feather-search" 
    //           viewBox="0 0 24 24">
              
    //           <defs></defs>
    //           <circle cx="11" cy="11" r="8"></circle>
    //           <path d="M21 21l-4.35-4.35"></path>
    //         </svg>
    //       </div>

    //     </div>
    //   </div>
    // </div>

    <div className="app-container">
      <div className="app-header">
        <div className="app-header-left">

          <span className="app-icon"></span>
          <p className="app-name">Portfolio</p>

          <div className="search-wrapper">
            <input className="search-input" type="text" placeholder="Search"/>
            <svg 
              xmlns="http://www.w3.org/2000/svg" 
              width="20" height="20" fill="none" 
              stroke="currentColor" stroke-linecap="round" 
              stroke-linejoin="round" stroke-width="2" 
              className="feather feather-search" 
              viewBox="0 0 24 24">
              <defs></defs>
              <circle cx="11" cy="11" r="8"></circle>
              <path d="M21 21l-4.35-4.35"></path>
            </svg>
          </div>

        </div>

        <div className="app-header-right">
          <button className="mode-switch" title="Switch Theme">
            <svg
              className="moon" 
              fill="none" 
              stroke="currentColor" stroke-linecap="round" 
              stroke-linejoin="round" stroke-width="2" 
              width="24" height="24" viewBox="0 0 24 24">
              <defs></defs>
              <path d="M21 12.79A9 9 0 1111.21 3 7 7 0 0021 12.79z"></path>
            </svg>
          </button>

          <button className="add-btn" title="Add New Project">
            <svg 
              className="btn-icon" 
              xmlns="http://www.w3.org/2000/svg" 
              width="16" height="16" 
              viewBox="0 0 24 24" fill="none" 
              stroke="currentColor" 
              stroke-width="3" stroke-linecap="round" 
              stroke-linejoin="round" class="feather feather-plus">
              <line x1="12" y1="5" x2="12" y2="19" />
              <line x1="5" y1="12" x2="19" y2="12" />
            </svg>
          </button>

          <button className="notification-btn">
            <svg
              xmlns="http://www.w3.org/2000/svg" 
              width="24" height="24" 
              viewBox="0 0 24 24" fill="none" 
              stroke="currentColor" stroke-width="2" 
              stroke-linecap="round" stroke-linejoin="round" 
              className="feather feather-bell">
              <path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9" />
              <path d="M13.73 21a2 2 0 0 1-3.46 0" /></svg>
          </button>

          <button className="profile-btn">
            <img src="https://assets.codepen.io/3306515/IMG_2025.jpg" />
            <span>Aybüke C.</span>
          </button>

        </div>

        <button className="messages-btn">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="20" height="20" 
            viewBox="0 0 24 24" fill="none" 
            stroke="currentColor" stroke-width="2" 
            stroke-linecap="round" stroke-linejoin="round" 
            className="feather feather-message-circle">
            <path d="M21 11.5a8.38 8.38 0 0 1-.9 3.8 8.5 8.5 0 0 1-7.6 4.7 8.38 8.38 0 0 1-3.8-.9L3 21l1.9-5.7a8.38 8.38 0 0 1-.9-3.8 8.5 8.5 0 0 1 4.7-7.6 8.38 8.38 0 0 1 3.8-.9h.5a8.48 8.48 0 0 1 8 8v.5z" />
          </svg>
        </button>
      </div>

      <div className="app-content">
        <div className="app-sidebar">

          <a href="" className="app-sidebar-link active">
            <svg
              xmlns="http://www.w3.org/2000/svg" 
              width="24" height="24" 
              viewBox="0 0 24 24" fill="none" 
              stroke="currentColor" stroke-width="2" 
              stroke-linecap="round" stroke-linejoin="round"
              className="feather feather-home">
              <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z" />
              <polyline points="9 22 9 12 15 12 15 22app-content" />
            </svg>
          </a>

          <a href="" className="app-sidebar-link">
            <svg
              className="link-icon feather feather-pie-chart"
              xmlns="http://www.w3.org/2000/svg" 
              width="24" height="24" fill="none" 
              stroke="currentColor" stroke-linecap="round" 
              stroke-linejoin="round" stroke-width="2"
              viewBox="0 0 24 24">
              <defs />
              <path d="M21.21 15.89A10 10 0 118 2.83M22 12A10 10 0 0012 2v10z" />
            </svg>
          </a>

          <a href="" className="app-sidebar-link">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="24" height="24" 
              viewBox="0 0 24 24" fill="none" 
              stroke="currentColor" stroke-width="2"
              stroke-linecap="round" stroke-linejoin="round" 
              className="feather feather-calendar">
              <rect x="3" y="4" width="18" height="18" rx="2" ry="2" />
              <line x1="16" y1="2" x2="16" y2="6" />
              <line x1="8" y1="2" x2="8" y2="6" />
              <line x1="3" y1="10" x2="21" y2="10" />
            </svg>
          </a>

          <a href="" className="app-sidebar-link">
            <svg
              className="link-icon feather feather-settings" 
              xmlns="http://www.w3.org/2000/svg" 
              width="24" height="24" fill="none" 
              stroke="currentColor" stroke-linecap="round" 
              stroke-linejoin="round" stroke-width="2"
              viewBox="0 0 24 24">
              <defs />
              <circle cx="12" cy="12" r="3" />
              <path d="M19.4 15a1.65 1.65 0 00.33 1.82l.06.06a2 2 0 010 2.83 2 2 0 01-2.83 0l-.06-.06a1.65 1.65 0 00-1.82-.33 1.65 1.65 0 00-1 1.51V21a2 2 0 01-2 2 2 2 0 01-2-2v-.09A1.65 1.65 0 009 19.4a1.65 1.65 0 00-1.82.33l-.06.06a2 2 0 01-2.83 0 2 2 0 010-2.83l.06-.06a1.65 1.65 0 00.33-1.82 1.65 1.65 0 00-1.51-1H3a2 2 0 01-2-2 2 2 0 012-2h.09A1.65 1.65 0 004.6 9a1.65 1.65 0 00-.33-1.82l-.06-.06a2 2 0 010-2.83 2 2 0 012.83 0l.06.06a1.65 1.65 0 001.82.33H9a1.65 1.65 0 001-1.51V3a2 2 0 012-2 2 2 0 012 2v.09a1.65 1.65 0 001 1.51 1.65 1.65 0 001.82-.33l.06-.06a2 2 0 012.83 0 2 2 0 010 2.83l-.06.06a1.65 1.65 0 00-.33 1.82V9a1.65 1.65 0 001.51 1H21a2 2 0 012 2 2 2 0 01-2 2h-.09a1.65 1.65 0 00-1.51 1z" />
            </svg>
          </a>

        </div>

        <div className="projects-section">
          <div className="projects-section-header">
            <p>Projects</p>
            <p className="time">December, 12</p>
          </div>

          <div className="projects-section-line">
            <div className="projects-status">

              <div className="item-status">
                <span className="status-number">45</span>
                <span className="status-type">In Progress</span>
              </div>

              <div className="item-status">
                <span className="status-number">24</span>
                <span className="status-type">Upcoming</span>
              </div>

              <div className="item-status">
                <span className="status-number">62</span>
                <span className="status-type">Total Projects</span>
              </div>

            </div>
            
            <div className="view-actions">
              <button className="view-btn list-view" title="List View">
                <svg xmlns="http://www.w3.org/2000/svg" 
                  width="24" height="24" viewBox="0 0 24 24" 
                  fill="none" stroke="currentColor" 
                  stroke-width="2" stroke-linecap="round" 
                  stroke-linejoin="round" 
                  className="feather feather-list">
                  <line x1="8" y1="6" x2="21" y2="6" />
                  <line x1="8" y1="12" x2="21" y2="12" />
                  <line x1="8" y1="18" x2="21" y2="18" />
                  <line x1="3" y1="6" x2="3.01" y2="6" />
                  <line x1="3" y1="12" x2="3.01" y2="12" />
                  <line x1="3" y1="18" x2="3.01" y2="18" />
                </svg>
              </button>

              <button className="view-btn grid-view active" title="Grid View">
                <svg
                  xmlns="http://www.w3.org/2000/svg" 
                  width="20" height="20" 
                  viewBox="0 0 24 24" fill="none" 
                  stroke="currentColor" stroke-width="2" 
                  stroke-linecap="round" stroke-linejoin="round" 
                  className="feather feather-grid">
                  <rect x="3" y="3" width="7" height="7" />
                  <rect x="14" y="3" width="7" height="7" />
                  <rect x="14" y="14" width="7" height="7" />
                  <rect x="3" y="14" width="7" height="7" />
                </svg>
              </button>

            </div>
          </div>

          <div className="project-boxes jsGridView">
            <MyBox/>
            <MyBox/>
            <MyBox/>
            <MyBox/>
          </div>
          
          
        </div>

      <div className="messages-section">
        <button className="messages-close">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="24" height="24" 
            viewBox="0 0 24 24" fill="none" 
            stroke="currentColor" stroke-width="2"
            stroke-linecap="round" stroke-linejoin="round" 
            className="feather feather-x-circle">
            <circle cx="12" cy="12" r="10" />
            <line x1="15" y1="9" x2="9" y2="15" />
            <line x1="9" y1="9" x2="15" y2="15" />
          </svg>
        </button>

        <div className="projects-section-header">
          <p>알림</p>
        </div>

        <div className="messages">
          <div className="message-box">
            
            <img src="https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=2550&q=80" alt="profile image"/>
            
            <div className="message-content">
              <div className="message-header">
                <div className="name">Stephanie</div>
                
                <div className="star-checkbox">
                  <input type="checkbox" id="star-1"/>
                  <label for="star-1">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      width="20" height="20" 
                      viewBox="0 0 24 24" fill="none" 
                      stroke="currentColor" stroke-width="2" 
                      stroke-linecap="round" stroke-linejoin="round" 
                      className="feather feather-star">
                      
                      <polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2" />
                    </svg>
                  </label>
                </div>
              </div>

              <p className="message-line">
                I got your first assignment. It was quite good. 🥳 We can continue with the next assignment.
              </p>

              <p className="message-line time">
                Dec, 12
              </p>

            </div>
          </div>

          <div className="message-box">
            
            <img src="https://images.unsplash.com/photo-1600486913747-55e5470d6f40?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=2550&q=80" alt="profile image"/>
            
            <div className="message-content">

              <div className="message-header">
                <div className="name">Mark</div>
                
                <div className="star-checkbox">
                  <input type="checkbox" id="star-2"/>
                  <label for="star-2">
                    <svg
                      xmlns="http://www.w3.org/2000/svg" 
                      width="20" height="20" 
                      viewBox="0 0 24 24" fill="none" 
                      stroke="currentColor" stroke-width="2" 
                      stroke-linecap="round" stroke-linejoin="round" 
                      className="feather feather-star">
                      <polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2" />
                    </svg>
                  </label>
                </div>
              </div>

              <p className="message-line">
                Hey, can tell me about progress of project? I'm waiting for your response.
              </p>

              <p className="message-line time">
                Dec, 12
              </p>

            </div>
          </div>

          <div className="message-box">
            
            <img src="https://images.unsplash.com/photo-1543965170-4c01a586684e?ixid=MXwxMjA3fDB8MHxzZWFyY2h8NDZ8fG1hbnxlbnwwfDB8MHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=900&q=60" alt="profile image"/>
            
            <div className="message-content">
              <div className="message-header">
                <div className="name">David</div>

                <div className="star-checkbox">
                  <input type="checkbox" id="star-3"/>
                  <label for="star-3">
                    <svg 
                      xmlns="http://www.w3.org/2000/svg"
                      width="20" height="20" 
                      viewBox="0 0 24 24" fill="none" 
                      stroke="currentColor" stroke-width="2" 
                      stroke-linecap="round" stroke-linejoin="round" 
                      className="feather feather-star">
                      <polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2" />
                    </svg>
                  </label>
                </div>

              </div>

              <p className="message-line">
                Awesome! 🤩 I like it. We can schedule a meeting for the next one.
              </p>

              <p className="message-line time">
                Dec, 12
              </p>

            </div>
          </div>

          <div className="message-box">
            
            <img src="https://images.unsplash.com/photo-1533993192821-2cce3a8267d1?ixid=MXwxMjA3fDB8MHxzZWFyY2h8MTl8fHdvbWFuJTIwbW9kZXJufGVufDB8fDB8&ixlib=rb-1.2.1&auto=format&fit=crop&w=900&q=60" alt="profile image"/>
            
            <div className="message-content">
              <div className="message-header">
                <div className="name">Jessica</div>
                
                <div className="star-checkbox">
                  <input type="checkbox" id="star-4"/>
                  <label for="star-4">
                    <svg
                      xmlns="http://www.w3.org/2000/svg" 
                      width="20" height="20" 
                      viewBox="0 0 24 24" fill="none"
                      stroke="currentColor" stroke-width="2" 
                      stroke-linecap="round" stroke-linejoin="round" 
                      className="feather feather-star">
                      <polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2" />
                    </svg>
                  </label>
                </div>

              </div>

              <p className="message-line">
                I am really impressed! Can't wait to see the final result.
              </p>

              <p className="message-line time">
                Dec, 11
              </p>

            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  )
}

export default MyPage