import React from 'react'

const MyHomeTopLine = () => {
  return (
        <div className="projects-status">

            <div className="item-status">
                <span className="status-number">4</span>
                <span className="status-type">Total</span>
            </div>

            <div className="item-status">
                <span className="status-number">0</span>
                <span className="status-type">Upload</span>
            </div>

            {/* <div className="item-status">
                <span className="status-number">62</span>
                <span className="status-type">Total Projects</span>
            </div> */}

            
            
            {/* <div className="view-actions">
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

            </div> */}
        </div>
  )
}

export default MyHomeTopLine