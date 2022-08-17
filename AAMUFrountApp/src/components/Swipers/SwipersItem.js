import React from "react";
import { Link } from "react-router-dom";

const SwipersItem = (props) => {
  console.log("props", props);
  return (
    <>
      <li className="swiper__item">
        <Link to="/" className="swiper__item__link">
          <figure className="swiper__item__pic-wrap" data-category={props.label}>
            <img
              src={props.src ?? `images/no-image.jpg`}
              alt="travel image"
              className="swiper__item__img"
            />
          </figure>
          <div className="swiper__item__info">
            <h5 className="swiper__item__text">{props.text}</h5>
          </div>
        </Link>
      </li>
    </>
  );
};
//  <a
// // href={`https://place.map.kakao.com/${val.kakaokey}`}
// href={`https://map.kakao.com/link/search/${props.val.kakaokey}`}
// target="_blank"
// />

export default SwipersItem;
