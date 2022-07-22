import React from "react";
import CardItem from "./CardItem";
import "./Cards.css";
const Cards = () => {
  const forMapTestArr = [1, 2, 3, 5];
  return (
    <div className="Cards">
      <div className="card__container">
        <div className="card__wrapper">
          <h1 className="card__title">
            당신도 이제는 여행 Creator<span> !</span>
          </h1>
          <p className="card__desc">
            자신만의 여행계획을 세우고 남들과 공유해봐
          </p>

          <ul className="card__items">
            {forMapTestArr.map((val, index) => {
              return <CardItem i={val} key={index} />;
            })}
          </ul>
        </div>
      </div>
    </div>
  );
};

export default Cards;
