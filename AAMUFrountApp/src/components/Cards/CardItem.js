import { Rating, Typography } from "@mui/material";
import { Link } from "react-router-dom";
import React, { useState } from "react";

const CardItem = ({ val }) => {
  const [value, setValue] = useState(2.5);

  return (
    <>
      <li className="card__item">
        <Link to="/" className="card__item__link">
          <figure className="card__item__info">
            <div className="card__item__img-container">
              <img src={`${val.photo[0]}`} alt="카드이미지" className="card__item__img" />
            </div>
            <div className="card__item__rating">
              <Rating
                name="simple-controlled"
                value={val.rateavg}
                precision={0.5}
                onChange={(event, newValue) => {
                  setValue(newValue);
                }}
              />
              <span className="idSpan">{`${val.id}님의 plan`}</span>
            </div>
            <div className="card__item__content">
              <h3>{val.title}</h3>
              <p>{val.content}</p>
            </div>
          </figure>
        </Link>
      </li>
    </>
  );
};

export default CardItem;
