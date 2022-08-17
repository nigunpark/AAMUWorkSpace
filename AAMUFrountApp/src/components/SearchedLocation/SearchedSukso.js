import { faCircleInfo, faPlus, faStar } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { useRef, useState } from "react";
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
import { useDispatch, useSelector } from "react-redux";
//redux에서 localNameForMarker(마커찍기위한 장소이름) 변경함수
import { addOneSaveDaysRedux, changeLnfM, changeShowWhichModal } from "../../redux/store.js";
import axios from "axios";
const SearchedSukso = ({ local, index }) => {
  const [locaInfoModal, setLocaInfoModal] = useState(false);
  let dispatch = useDispatch();
  let localNameRef = useRef();
  let reduxState = useSelector((state) => {
    return state;
  });
  const [commentData, setCommentData] = useState({});
  const [showReview, setShowReview] = useState(false);
  return (
    <div
      className="SearchedLocation"
      onClick={(e) => {
        e.stopPropagation();
        //redux 변경 함수
        dispatch(changeLnfM(local));
      }}
    >
      {locaInfoModal && (
        <LocalInfoModal
          locaInfoModal={locaInfoModal}
          setLocaInfoModal={setLocaInfoModal}
          local={local}
          commentData={commentData}
          showReview={showReview}
          setShowReview={setShowReview}
        />
      )}
      <div
        className="searchedLocation__container"
        onClick={() => {
          //redux 변경 함수
          dispatch(changeLnfM(local.addr));
        }}
      >
        <div className="searchedLocation__img-container">
          {reduxState.saveDaysNPickedSuksoRedux.map((state, index) => {
            if (state === local) {
              return <PickedSuksoBadge index={index} local={local} key={index} />;
            }
          })}

          <img
            src={local.smallimage ?? "/images/no-image.jpg"}
            onError={(e) => {
              e.target.src = "/images/no-image.jpg";
            }}
          />
        </div>
        <div className="searchedLocation__info">
          <h5 className="searchedLocation__info__title" ref={localNameRef}>
            {local.title}
          </h5>
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
                dispatch(addOneSaveDaysRedux(local));
                dispatch(changeShowWhichModal(1));
              }}
            />
          </div>
        </div>
      </div>
    </div>
  );
};

function LocalInfoModal({
  locaInfoModal,
  setLocaInfoModal,
  local,
  commentData,
  showReview,
  setShowReview,
}) {
  let dispatch = useDispatch();
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
            <h4>{local.title}</h4>
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
                <li>-</li>
                <li>{local.url}</li>
                <li>{local.addr}</li>
                <li>{local.tel}</li>
                <li>-</li>
              </ul>
            </div>
          </div>
        </BodyLim>
        <div className="localInfo__snsBtn__container">
          <ul className="localInfo__snsBtn-ul">
            <a
              href={`https://www.instagram.com/explore/tags/${local.title.split(" ").join("")}/`}
              target="_blank"
            >
              <li>
                <img src={process.env.PUBLIC_URL + "/images/sns/instagram.png"} />
              </li>
            </a>
            <a href={`https://twitter.com/search?q=${local.title}&src=typed_query`} target="_blank">
              <li>
                <img src={process.env.PUBLIC_URL + "/images/sns/twitter.png"} />
              </li>
            </a>
            <a href={`https://www.google.com/maps/place/${local.title}`} target="_blank">
              <li>
                <img src={process.env.PUBLIC_URL + "/images/sns/googlemap.png"} />
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
            onClick={() => {
              setShowReview(!showReview);
            }}
          >
            리뷰보기
            {commentData.basic_info !== undefined && `(${commentData.basic_info.feedback})`}
          </span>
          <span
            onClick={(e) => {
              e.stopPropagation();
              dispatch(addOneSaveDaysRedux(local));
              dispatch(changeShowWhichModal(1));
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
const getReview = async (local, setCommentData) => {
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

function PickedSuksoBadge({ sukso, index }) {
  return (
    <div>
      <span className="badge">Day{index + 1}</span>{" "}
    </div>
  );
}
export default SearchedSukso;
