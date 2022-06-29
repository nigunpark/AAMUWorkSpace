import React from 'react'

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
            {/* <h1 className="swiper__title_Notice">
            당신의 최애 여행지는 어디인가요 <span>?</span>
            </h1> */}
            <p className="swiper__desc_Notice">국내 인기 여행지를 알고리즘으로 추천</p>
            <div className="swiper__wrapper_Notice">
                <ul className="swiper__items_Notice">
                    <Swiper
                        className="swiper-container_Notice2"
                        modules={[Navigation, Pagination, Scrollbar, A11y, Autoplay]}
                        spaceBetween={50}
                        slidesPerView={3} //한 슬라이드에 보여줄 개수
                        // navigation
                        autoplay={{ delay: 3000 }}
                        loop={true}
                        pagination={{ clickable: true }}
                        scrollbar={{ draggable: true }}
                        >
                        <SwiperSlide>
                            <NoticeItem
                            src="/images/img-9.jpg"
                            text="Explore the hidden waterfall deep inside the Amazon Jungle"
                            label="Adventure"
                            path="/"
                            />
                        </SwiperSlide>
                        <SwiperSlide>
                            <NoticeItem
                            src="/images/img-2.jpg"
                            text="Explore the hidden waterfall deep inside the Amazon Jungle"
                            label="Adventure"
                            path="/"
                            />
                        </SwiperSlide>
                        <SwiperSlide>
                            <NoticeItem
                            src="/images/img-3.jpg"
                            text="Explore the hidden waterfall deep inside the Amazon Jungle"
                            label="Adventure"
                            path="/"
                            />
                        </SwiperSlide>
                        <SwiperSlide>
                            <NoticeItem
                            src="/images/img-4.jpg"
                            text="Explore the hidden waterfall deep inside the Amazon Jungle"
                            label="Adventure"
                            path="/"
                            />
                        </SwiperSlide>
                    </Swiper>
                </ul>
            </div>
        </div>
    </div>
  )
}

export default Notice