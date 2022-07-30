import { Rating, Typography } from "@mui/material";
import { Link } from "react-router-dom";
import React, { useState } from "react";
import DetailModal from "../DetailModal/DetailModal";

const ContentItem = ({ detail, index }) => {
  // console.log("ContentItem dummy :",dummy.reviewdata);
  // console.log("keys : ",keys);

  //   console.log("detail :", detail);
  //   console.log("리뷰 데이터 :", detail.reviewList);

  const detailPostDate = new Date(detail.postdate);

  function dateFormat(date) {
    let month = date.getMonth() + 1;
    let day = date.getDate();
    month = month >= 10 ? month : "0" + month;
    day = day >= 10 ? day : "0" + day;
    return date.getFullYear() + "-" + month + "-" + day;
  }
  const postDay = dateFormat(detailPostDate);

  const [isOpen, setIsOpen] = useState(false);
  const detailRbn = detail.rbn; // rbn 값 저장 잘 됨

  const onClickModal = () => {
    setIsOpen(true);
  };

  //   목록에서 보여줄 평균 별점
  let star = 0;
  detail.reviewList.forEach((obj, i) => {
    // console.log('11',typeof Number(obj.star))
    star += Number(obj.rate);
  });
  star = Math.round((star / detail.reviewList.length) * 10) / 10;

  return (
    <>
      <li className="card__item_minCon" onClick={onClickModal}>
        <div className="card__item__link_minCon">
          <figure className="card__item__info_minCon">
            <div className="card__item__img-container_minCon">
              <img
                src={detail.photo[0]}
                alt="카드이미지"
                className="card__item__img_minCon"
              />
            </div>
            <div className="card__item__rating_minCon">
              <img src="/images/star.jpg" style={{ width: "30px" }} />
              {detail.reviewList.length == 0 ? 0 : star}

              <span className="idSpan_minCon" style={{ marginLeft: "auto" }}>
                {detail.id}님의 plan
              </span>
            </div>
            {/* style={{ display: "flex", flexDirection: "row" }} */}
            <div>
              <h3
                style={{
                  width: "100%",
                  overflow: "hidden",
                }}
              >
                {detail.title}
              </h3>
            </div>
            <div
              style={{
                display: "flex",
                flexDirection: "row",
                fontSize: "12.5px",
                color: "#8e8e8e",
              }}
            >
              <p>{detail.themename}</p>
              <div style={{ marginLeft: "auto" }}>{postDay}</div>
            </div>
          </figure>
        </div>
      </li>
      {isOpen == true ? (
        <DetailModal
          postDay={postDay}
          setIsOpen={setIsOpen}
          detailRbn={detailRbn}
        />
      ) : null}
    </>
  );
};

export default ContentItem;
