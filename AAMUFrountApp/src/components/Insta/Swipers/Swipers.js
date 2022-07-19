import React from "react";
import SwipersItem from "./SwipersItem";
import "./Swipers.css";
import { Navigation, Pagination, Scrollbar, A11y, Autoplay } from "swiper";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css"; //basic
import "swiper/css/navigation";
import "swiper/css/pagination";

const Swipers = () => {
  return (
    <div className="Swipers">
      <div className="swiper__container">
        <h1 className="swiper__title">
          당신의 최애 여행지는 어디인가요 <span>?</span>
        </h1>
        <p className="swiper__desc">국내 인기 여행지를 알고리즘으로 추천</p>
        <div className="swiper__wrapper">
          <ul className="swiper__items">
            <Swiper
              className="swiper-container"
              modules={[Navigation, Pagination, Scrollbar, A11y, Autoplay]}
              spaceBetween={50}
              slidesPerView={3}
              // navigation
              autoplay={{ delay: 2500 }}
              loop={true}
              pagination={{ clickable: true }}
              scrollbar={{ draggable: true }}
            >
              <SwiperSlide>
                <SwipersItem
                  src="/images/img-9.jpg"
                  text="Explore the hidden waterfall deep inside the Amazon Jungle"
                  label="Adventure"
                  path="/"
                />
              </SwiperSlide>
              <SwiperSlide>
                <SwipersItem
                  src="/images/img-2.jpg"
                  text="Explore the hidden waterfall deep inside the Amazon Jungle"
                  label="Adventure"
                  path="/"
                />
              </SwiperSlide>
              <SwiperSlide>
                <SwipersItem
                  src="/images/img-3.jpg"
                  text="Explore the hidden waterfall deep inside the Amazon Jungle"
                  label="Adventure"
                  path="/"
                />
              </SwiperSlide>
              <SwiperSlide>
                <SwipersItem
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
  );
};

export default Swipers;
