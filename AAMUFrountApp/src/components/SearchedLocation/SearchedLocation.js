import { faCircleInfo, faPlus } from "@fortawesome/free-solid-svg-icons";
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
} from "../Modal/localInfoModal.js";
import { useDispatch } from "react-redux";
//redux에서 localNameForMarker(마커찍기위한 장소이름) 변경함수
import {
  addPickJangso,
  changeLnfM,
  changeShowWhichModal,
  deleteArrInJangso,
  timeSetter,
} from "../../redux/store.js";

const SearchedLocation = ({ local }) => {
  const [locaInfoModal, setLocaInfoModal] = useState(false);
  let dispatch = useDispatch();
  let localContainer = useRef();
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
                  setLocaInfoModal(true);
                }}
              />
              <FontAwesomeIcon
                icon={faPlus}
                className="searchedLocation__i"
                onClick={(e) => {
                  e.stopPropagation();
                  localContainer.current.classList.add(
                    "searchedLocation__fadeOut"
                  );
                  dispatch(timeSetter(2));
                  dispatch(addPickJangso(local));
                  dispatch(deleteArrInJangso(local));
                  dispatch(changeShowWhichModal(false));
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
          <h4>{local.title}</h4>
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
            <a
              href={`https://www.instagram.com/explore/tags/${local.title}/`}
              target="_blank"
            >
              <li>
                <img
                  src={process.env.PUBLIC_URL + "/images/sns/instagram.png"}
                />
              </li>
            </a>
            <a
              href={`https://twitter.com/search?q=${local.title}&src=typed_query`}
              target="_blank"
            >
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
                <img
                  src={process.env.PUBLIC_URL + "/images/sns/kakaoMap.png"}
                />
              </li>
            </a>
            <a
              href={`https://search.naver.com/search.naver?query=${local.title}`}
              target="_blank"
            >
              <li>
                <img src={process.env.PUBLIC_URL + "/images/sns/naver.png"} />
              </li>
            </a>
          </ul>
        </div>
        <div className="localInfo-btn">
          <span>리뷰보기</span>
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
      </ContentsLim>
    </ContainerLim>
  );
}

export default SearchedLocation;
