import React from 'react'

import NoticeItem from "./NoticeItem";
import './Notice.css';

import { Navigation, Pagination, Scrollbar, A11y, Autoplay } from "swiper";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css"; //basic
import "swiper/css/navigation";
import "swiper/css/pagination";
import { Link } from 'react-router-dom';



const Notice = ({dummy}) => {

    console.log('이미지 파일', dummy.imgsdata.length);

  return (
    <div className='Notice'>
        <div className="swiper__container_Notice">
            {/* <h1 className="swiper__title_Notice">
            당신의 최애 여행지는 어디인가요 <span>?</span>
            </h1> */}
            {/* <p className="swiper__desc_Notice">국내 인기 여행지를 알고리즘으로 추천</p> */}
            <div className="swiper__wrapper_Notice">
                <ul className="swiper__items_Notice">
                    <Swiper
                        className="swiper-container_Notice2"
                        modules={[Navigation, Pagination, Scrollbar, A11y, Autoplay]}
                        spaceBetween={50} //슬라이드 사이 여백
                        slidesPerView={1} //한 슬라이드에 보여줄 개수
                        autoplay={{ delay: 4000 }}
                        loop={true}
                        // navigation={true}
                        pagination={{ clickable: true }}
                        scrollbar={{ draggable: true }}
                        >
                        {
                            dummy.imgsdata.map((val, idx)=>{
                                console.log('value : ',(val.imgs));

                                <SwiperSlide>
                                    <li className="swiper__item_Notice">
                                        <Link to='/' className="swiper__item__link_Notice">
                                            <figure
                                                className="swiper__item__pic-wrap_Notice"
                                                // data-category={props.label}
                                                >
                                                <img
                                                    src={val.imgs}
                                                    alt="travel image"
                                                    className="swiper__item__img_Notice"
                                                    />
                                            </figure>
                                        </Link>
                                    </li>
                                </SwiperSlide>
                            })
                        }
{/* 
                                <SwiperSlide>
                                    <li className="swiper__item_Notice">
                                        <Link to='/' className="swiper__item__link_Notice">
                                            <figure
                                                className="swiper__item__pic-wrap_Notice"
                                                // data-category={props.label}
                                                >
                                                <img
                                                    src='/images/img-2.jpg'
                                                    alt="travel image"
                                                    className="swiper__item__img_Notice"
                                                    />
                                            </figure>
                                        </Link>
                                    </li>
                                </SwiperSlide>
                         */}
                        {/* <SwiperSlide>
                            <NoticeItem
                            src="/images/imageMap.png"
                            //text="Explore the hidden waterfall deep inside the Amazon Jungle"
                            // label="Adventure"
                            path="/"
                            />
                        </SwiperSlide>

                        <SwiperSlide>
                            <NoticeItem
                            src="/images/img-2.jpg"
                            //text="Explore the hidden waterfall deep inside the Amazon Jungle"
                            // label="Adventure"
                            path="/"
                            />
                        </SwiperSlide>
                        
                        <SwiperSlide>
                            <NoticeItem
                            src="/images/img-3.jpg"
                            //text="Explore the hidden waterfall deep inside the Amazon Jungle"
                            // label="Adventure"
                            path="/"
                            />
                        </SwiperSlide>

                        <SwiperSlide>
                            <NoticeItem
                            src="/images/woah.jpg"
                            //text="Explore the hidden waterfall deep inside the Amazon Jungle"
                            // label="Adventure"
                            path="/"
                            />
                        </SwiperSlide> */}
                        
                    </Swiper>
                </ul>
            </div>
        </div>
    </div>
  )
}

export default Notice