import React, { useEffect, useState } from "react";
import SwipersItem from "./SwipersItem";
import "./Swipers.css";
import { Navigation, Pagination, Scrollbar, A11y, Autoplay } from "swiper";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css"; //basic
import "swiper/css/navigation";
import "swiper/css/pagination";
import axios from "axios";
const Swipers = () => {
  const [forSwiper, setForSwiper] = useState([]);
  useEffect(() => {
    get(setForSwiper);
  }, []);
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
              {forSwiper.map((val, i) => {
                return (
                  <SwiperSlide key={val.kakaokey}>
                    <SwipersItem
                      style={{ fontSize: "13px" }}
                      src={val.bigimage}
                      text={val.title}
                      label={`${getLocalName(val.areacode)}`}
                      path="/"
                      val={val}
                    />
                  </SwiperSlide>
                );
              })}
            </Swiper>
          </ul>
        </div>
      </div>
    </div>
  );
};

function get(setForSwiper) {
  // let token = sessionStorage.getItem("token");
  axios
    .get("/aamurest/main/mainelement")
    .then((resp) => {
      setForSwiper(Object.values(resp.data)[0].places);
    })
    .catch((err) => {
      console.log(err);
    });
}

function getLocalName(areacode) {
  switch (areacode) {
    case 1:
      return "서울";

    case 2:
      return "인천";

    case 3:
      return "대전";

    case 4:
      return "대구";

    case 5:
      return "광주";

    case 6:
      return "부산";

    case 7:
      return "부산";

    case 8:
      return "세종";

    case 31:
      return "경기도";

    case 32:
      return "강원도";

    case 33:
      return "충청북도";

    case 34:
      return "충청남도";

    case 35:
      return "경상북도";

    case 36:
      return "경상남도";

    case "37":
      return "전라북도";

    case 38:
      return "전라남도";

    case 39:
      return "제주도";
  }
}
export default Swipers;
