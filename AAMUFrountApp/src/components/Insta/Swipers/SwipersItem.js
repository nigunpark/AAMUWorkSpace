import React from "react";
import { Link } from "react-router-dom";

const SwipersItem = (props) => {
  return (
    <>
      <li className="swiper__item">
        <Link to={props.path} className="swiper__item__link">
          <figure
            className="swiper__item__pic-wrap"
            data-category={props.label}
          >
            <img
              src={props.src}
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

export default SwipersItem;
