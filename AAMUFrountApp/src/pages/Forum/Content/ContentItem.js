import { Rating, Typography } from "@mui/material";
import { Link } from "react-router-dom";
import React, { useState } from "react";
import DetailModal from "../DetailModal/DetailModal";
import { faStar } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const ContentItem = ({ detail, setDetailOne, setIsOpen }) => {
  let day = new Date(detail.postdate);
  const postDay = dateFormat(day);
  const detailRbn = detail.rbn; // rbn 값 저장 잘 됨
  function dateFormat(date) {
    let month = date.getMonth() + 1;
    let day = date.getDate();
    month = month >= 10 ? month : "0" + month;
    day = day >= 10 ? day : "0" + day;
    return date.getFullYear() + "-" + month + "-" + day;
  }
  //   목록에서 보여줄 평균 별점
  let star = 0;
  detail.reviewList.forEach((obj, i) => {
    star += Number(obj.rate);
  });
  star = Math.round((star / detail.reviewList.length) * 10) / 10;
  return (
    <>
      <div
        className="card__item_minCon"
        onClick={() => {
          setDetailOne(detail);
          setIsOpen(true);
        }}
      >
        <div className="card__item__link_minCon">
          <figure className="card__item__info_minCon">
            <div className="card__item__img-container_minCon">
              <img
                src={detail.photo[0] == undefined ? "/images/no-image.jpg" : detail.photo[0]}
                alt="카드이미지"
                className="card__item__img_minCon"
              />
            </div>

            <div className="card__item__bottom">
              <div className="card__item__rating_minCon">
                {/* <img src="/images/star.png" style={{ width: "30px" }} /> */}
                <div>
                  <FontAwesomeIcon icon={faStar} style={{ marginRight: "3px", color: "gold" }} />
                  <span style={{ fontWeight: "bold" }}>
                    {detail.reviewList.length === 0 ? 0 : star}
                  </span>
                </div>
                <span className="idSpan_minCon">{detail.id}님의 plan</span>
              </div>
              {/* style={{ display: "flex", flexDirection: "row" }} */}
              <div>
                <h4
                  style={{
                    width: "100%",
                    overflow: "hidden",
                  }}
                >
                  {detail.title}
                </h4>
              </div>
              <div
                style={{
                  display: "flex",
                  color: "#8e8e8e",
                  justifyContent: "space-between",
                  alignItems: "center",
                  fontSize: "11px",
                }}
              >
                <p style={{ fontWeight: "bold" }}>{detail.themename}</p>
                <div>{postDay}</div>
              </div>
            </div>
          </figure>
        </div>
      </div>
      {/* {isOpen == true ? (
        <DetailModal
          postDay={postDay}
          detailRbn={detailRbn}
          setShowCBModal={setShowCBModal}
          setIsOpen={setIsOpen}
        />
      ) : null} */}
    </>
  );
};

export default ContentItem;
