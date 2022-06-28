import { Rating, Typography } from "@mui/material";
import { Link } from "react-router-dom";
import React, { useState } from "react";

const CardItem = ({ i }) => {
  const [value, setValue] = useState(2.5);

  return (
    <>
      <li className="card__item">
        <Link to="/" className="card__item__link">
          <figure className="card__item__info">
            <div className="card__item__img-container">
              <img
                src={"/images/img-" + i + ".jpg"}
                alt="카드이미지"
                className="card__item__img"
              />
            </div>
            <div className="card__item__rating">
              <Rating
                name="simple-controlled"
                value={value}
                precision={0.5}
                onChange={(event, newValue) => {
                  setValue(newValue);
                }}
              />
              <span className="idSpan">kim**58님의 plan</span>
            </div>
            <div className="card__item__content">
              <h3>제주도 3박4일</h3>
              <p>국시치고 가족과 함께 제주도 여행다녀왔어요</p>
            </div>
          </figure>
        </Link>
      </li>
    </>
  );
};

export default CardItem;
