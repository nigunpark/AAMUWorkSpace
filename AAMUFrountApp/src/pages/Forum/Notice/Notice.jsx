import React, { useEffect, useState } from 'react'

import NoticeItem from "./NoticeItem";
import './Notice.css';

import { Navigation, Pagination, Scrollbar, A11y, Autoplay } from "swiper";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css"; //basic
import "swiper/css/navigation";
import "swiper/css/pagination";

const Notice = () => {
    
  return (
    <div className='Notice'>
        <div className="swiper__container_Notice">
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
                        {/* {
                            dummy.map((val, idx)=>{
                                return (
                                    <SwiperSlide>
                                        <NoticeItem picture={val.imgs}/>
                                    </SwiperSlide>
                                )
                            })
                        } */}
                        <SwiperSlide>
                            <NoticeItem/>
                        </SwiperSlide>
                    </Swiper>
                </ul>
            </div>
        </div>
    </div>
  )
}


export default Notice