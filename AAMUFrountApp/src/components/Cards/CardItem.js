import { Rating, Typography } from "@mui/material";
import { Link } from "react-router-dom";
import React, { useState } from "react";
import { faStar } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

import { useDispatch } from "react-redux";
import { addChatBotData } from "../../redux/store";
import { useLocation, useNavigate } from "react-router-dom";

const CardItem = ({ val }) => {
  const [value, setValue] = useState(2.5);
  let dispatch = useDispatch();
  let navigate = useNavigate();
  let location = useLocation();
  function goRecommend(val) {
    if (val.rbn !== null) {
      console.log(val);
      navigate("/forum", { state: { dto: val } });
      dispatch(addChatBotData(val));
    }
  }
  return (
    <>
      <li
        className="card__item"
        onClick={() => {
          goRecommend(val);
        }}
      >
        <Link to="/" className="card__item__link">
          <figure className="card__item__info">
            <div className="card__item__img-container">
              <img src={`${val.photo[0]}`} alt="카드이미지" className="card__item__img" />
            </div>
            <div className="card__item__rating">
              <div>
                <FontAwesomeIcon
                  icon={faStar}
                  style={{
                    marginRight: "5px",
                    color: "gold",
                    filter: "drop-shadow(1px 1px 1px black)",
                    fontSize: "16px",
                  }}
                />
                <span style={{ fontWeight: "bold" }}>{val.rateavg}</span>
              </div>
              {/* <Rating
                name="simple-controlled"
                value={val.rateavg}
                precision={0.5}
                onChange={(event, newValue) => {
                  setValue(newValue);
                }}
              /> */}
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
