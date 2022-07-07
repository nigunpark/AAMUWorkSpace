import React, { useState } from 'react'
import ContentItem from './ContentItem';
import './Content.css'
import { Link, useNavigate } from 'react-router-dom';
import FSearch from '../FSearch/FSearch';

const Content = () => {
    //let navigate = useNavigate();
    const forMapTestArr = [1, 2, 3, 5, 6, 7];

    return (
        <div className="Cards_minCon">
            <div className="card__container_minCon">
                <div className="card__wrapper_minCon">
                    <h1 className="card__title_minCon">
                        당신도 이제는 여행 Creator<span>!</span>
                    </h1>
                    
                    <p className="card__desc_minCon">
                        자신만의 여행계획을 세우고 남들과
                        <span className="content-btn">
                            <Link to='/myPage'>공유해봐</Link>
                        </span>
                    </p>
                    <FSearch/>
                    
                    <ul className="card__items_minCon">
                        {forMapTestArr.map((val, index) => {
                        return <ContentItem i={val} key={index} />;
                        })}
                    </ul>
                </div>
            </div>
        </div>
  )
}


export default Content