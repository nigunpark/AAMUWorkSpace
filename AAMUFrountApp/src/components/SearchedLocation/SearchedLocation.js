import { faCircleInfo, faPlus, faStar } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { useEffect, useRef, useState } from "react";
import "./SearchedLocation.css";
import {
  ContainerLim,
  ContentsLim,
  OverlayLim,
  CloseLim,
  BodyLim,
  ImgLim,
  Review,
} from "../Modal/localInfoModalParts.js";
import { useDispatch } from "react-redux";
//redux에서 localNameForMarker(마커찍기위한 장소이름) 변경함수
import {
  addPickJangso,
  changeLnfM,
  changeShowWhichModal,
  deleteArrInJangso,
  timeSetter,
} from "../../redux/store.js";
import axios from "axios";

const SearchedLocation = ({ local }) => {
  const [locaInfoModal, setLocaInfoModal] = useState(false);
  const [showReview, setShowReview] = useState(false);
  let dispatch = useDispatch();
  let localContainer = useRef();
  const [commentData, setCommentData] = useState({});
  return (
    <>
      <div
        className="SearchedLocation"
        onClick={(e) => {
          e.stopPropagation();
          //지도에 마커찍어줄 redux 변경 함수
          dispatch(changeLnfM(local));
        }}
      >
        <div className="searchedLocation__container" ref={localContainer}>
          <div className="searchedLocation__img-container">
            <img
              src={local.smallimage ?? "/images/no-image.jpg"}
              onError={(e) => {
                e.target.src = "/images/no-image.jpg";
              }}
            />
          </div>
          <div className="searchedLocation__info">
            <h5 className="searchedLocation__info__title">{local.title}</h5>
            <div className="searchedLocation__info__content">
              <FontAwesomeIcon
                icon={faCircleInfo}
                className="searchedLocation__i"
                onClick={(e) => {
                  e.stopPropagation();
                  getReview(local, setCommentData);
                  setLocaInfoModal(true);
                }}
              />
              <FontAwesomeIcon
                icon={faPlus}
                className="searchedLocation__i"
                onClick={(e) => {
                  e.stopPropagation();
                  // localContainer.current.classList.add(
                  //   "searchedLocation__fadeOut"
                  // );
                  dispatch(timeSetter(2));
                  dispatch(addPickJangso(local));
                  dispatch(deleteArrInJangso(local));
                  dispatch(changeShowWhichModal(3));
                }}
              />
            </div>
          </div>
        </div>
      </div>
      {locaInfoModal && (
        <LocalInfoModal
          locaInfoModal={locaInfoModal}
          setLocaInfoModal={setLocaInfoModal}
          local={local}
          localContainer={localContainer}
          setShowReview={setShowReview}
          showReview={showReview}
          commentData={commentData}
          setCommentData={setCommentData}
        />
      )}
    </>
  );
};

function LocalInfoModal({
  locaInfoModal,
  setLocaInfoModal,
  local,
  localContainer,
  setShowReview,
  showReview,
  commentData,
}) {
  let dispatch = useDispatch();
  let reviewData;
  return (
    <ContainerLim>
      <OverlayLim onClick={() => setLocaInfoModal(!locaInfoModal)} />
      <ContentsLim>
        <CloseLim onClick={() => setLocaInfoModal(!locaInfoModal)}>X</CloseLim>
        <ImgLim>
          <img
            className="localInfoModal__img"
            src={local.bigimage ?? "/images/no-image.jpg"}
            alt="이미지"
            onError={(e) => {
              e.target.src = "/images/no-image.jpg";
            }}
          />
        </ImgLim>
        <BodyLim>
          <div
            style={{
              display: "flex",
              justifyContent: "flex-start",
              alignItems: "center",
            }}
          >
            <h4>{`${local.title}`}</h4>
            <h4>
              (
              <FontAwesomeIcon icon={faStar} style={{ marginRight: "8px", color: "gold" }} />
              {commentData.basic_info !== undefined && `(${commentData.basic_info.star})`})
            </h4>
          </div>
          <div className="localInfo__container">
            <div className="localInfo">
              <ul className="localInfo-ul">
                <li>영업시간</li>
                <li>홈페이지</li>
                <li>주소</li>
                <li>전화</li>
                <li>입장료</li>
              </ul>
            </div>
            <div className="localInfo">
              <ul className="localInfo-ul">
                <li>{local.playtime ?? "-"}</li>
                <li>{local.url ?? "-"}</li>
                <li>{local.addr !== null ? local.addr : "-"}</li>
                <li>{local.tel !== null ? local.tel : "-"}</li>
                <li>-</li>
              </ul>
            </div>
          </div>
        </BodyLim>
        <div className="localInfo__snsBtn__container">
          <ul className="localInfo__snsBtn-ul">
            <a href={`https://www.instagram.com/explore/tags/${local.title}/`} target="_blank">
              <li>
                <img src={process.env.PUBLIC_URL + "/images/sns/instagram.png"} />
              </li>
            </a>
            <a href={`https://twitter.com/search?q=${local.title}&src=typed_query`} target="_blank">
              <li>
                <img src={process.env.PUBLIC_URL + "/images/sns/twitter.png"} />
              </li>
            </a>
            <a
              href={`https://map.kakao.com/link/search/${local.title}
              `}
              target="_blank"
            >
              <li>
                <img src={process.env.PUBLIC_URL + "/images/sns/kakaoMap.png"} />
              </li>
            </a>
            <a href={`https://search.naver.com/search.naver?query=${local.title}`} target="_blank">
              <li>
                <img src={process.env.PUBLIC_URL + "/images/sns/naver.png"} />
              </li>
            </a>
          </ul>
        </div>
        <div className="localInfo-btn">
          <span
            onClick={(e) => {
              e.stopPropagation();
              setShowReview(!showReview);
            }}
          >
            리뷰보기
            {commentData.basic_info !== undefined && `(${commentData.basic_info.feedback})`}
          </span>
          <span
            onClick={(e) => {
              e.stopPropagation();
              localContainer.current.classList.add("searchedLocation__fadeOut");
              dispatch(timeSetter(2));
              dispatch(addPickJangso(local));
              dispatch(deleteArrInJangso(local));
              setLocaInfoModal(false);
            }}
          >
            목록에 추가
          </span>
        </div>
        {showReview && (
          <Review>
            {commentData.comment_info !== undefined &&
              commentData.comment_info.map((obj, i) => {
                return (
                  <div className="review__container">
                    <div className="review__info">
                      <div>
                        <FontAwesomeIcon
                          icon={faStar}
                          style={{ marginRight: "8px", color: "gold" }}
                        />
                        <span>{`(${obj.point})`}</span>
                      </div>
                      <p style={{ fontWeight: "bold" }}>{obj.review}</p>
                      <span
                        style={{
                          fontSize: "13px",
                          display: "flex",
                          justifyContent: "center",
                          fontWeight: "bold",
                        }}
                      >
                        {obj.username}
                      </span>
                    </div>
                  </div>
                );
              })}
          </Review>
        )}
      </ContentsLim>
    </ContainerLim>
  );
}

const getReview = async (local, setCommentData, reviewData) => {
  let token = sessionStorage.getItem("token");
  await axios
    .get("/aamurest/info/review", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
      params: { kakaoKey: local.kakaokey },
    })
    .then((resp) => {
      console.log(resp.data);
      setCommentData(resp.data);
    })
    .catch((err) => {
      console.log(err);
    });
};
export default SearchedLocation;
