import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './DetailContent.css'
import DetailButton from './DetailButton';
import Notice from '../Notice/Notice';
import { Rating } from '@mui/material';

const test = () => {
    const forMapTestArr = [1, 2, 3, 5, 6, 7];
    
    
  return (
    <div style={{
        width: "100%",
        display: "inline-block"
        }}>
        <div className="minCon_Cards">
            <div className="minCon_card__container">
                <div className="minCon_card__wrapper">

                    <div className='min_Contents'>
                        <h1 className="minCon_card__title">
                            제목
                            <DetailButton/>
                        </h1>
                    </div>

                    <div className='aasds'>
                        <Rating
                            name="simple-controlled"
                            value={3.5}
                            precision={0.5}
                        />
                        해당 게시글 별점 표시?
                    </div>

                    <div className="minCon_card__items">{/* 여행 경로 자리 */}
                    
                        <figure className="minCon_card__item__info">
                            <div className="minCon_card__item__img-container">
                                <img
                                    src='/images/imageMap.png'
                                    alt="카드이미지"
                                    className="minCon_card__item__img"
                                    />
                            </div>
                        </figure>
                        
                        <div>
                            <Notice/>
                            <p className="minCon_card__desc">
                                내용 적힐 공간 그리고 여행경로 지도 사진 저장될 때
                                사이즈 고정으로 저장되는지 여쭤봐야함
                                이 공간 스크롤 기능 해야됨
                                아니면 내용 적힐 공간 위치 변경
                            </p>
                        </div>
                        <span className="content_time">
                            2022-07-03
                            
                        </span>
                        <div className="minContent">
                            <Link to='/'>
                            #tag
                            </Link>
                        </div>
                    </div>
                    
                    
                    
                    <form className="minCon_comment">
                        <Rating
                            name="simple-controlled"
                            value={0}
                            precision={0.5}
                            onChange={(event, newValue) => {
                                // setValues(newValue);
                            }}
                        />
                        <input type="text" className="minCon_typing-comment" placeholder="리뷰 달기..."/>
                        <div className='detail-button'>
                            {/* DetailButton.scss */}
                            <button className="learn-more" type="button">게시</button>
                        </div>
                    </form>

                    <div>
                        리뷰 적히는 곳
                    </div>
                </div>
            </div>
        </div>
    </div>
  )
}
export default test