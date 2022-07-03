import React from 'react'
import { Link } from 'react-router-dom'
import './DetailButton.scss'

const DetailButton = () => {
  return (
    <div className='detail-button'>
        {/* <button className="learn-more">edit</button>
        <button className="learn-more">delete</button> */}

        <Link to='/forum'>
            <button className="learn-more">list</button>
        </Link>
    </div>
  )
}

export default DetailButton