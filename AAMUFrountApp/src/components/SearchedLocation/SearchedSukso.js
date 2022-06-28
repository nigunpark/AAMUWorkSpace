import { faCircleInfo, faPlus } from "@fortawesome/free-solid-svg-icons";
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
} from "../Modal/localInfoModal.js";
import { useDispatch, useSelector } from "react-redux";
//redux에서 localNameForMarker(마커찍기위한 장소이름) 변경함수
import {
  addOneSaveDaysRedux,
  changeLnfM,
  changeShowWhichModal,
} from "../../redux/store.js";

const SearchedSukso = ({ local, index }) => {
  const [locaInfoModal, setLocaInfoModal] = useState(false);
  const [showBadge, setShowBadge] = useState(false);
  let dispatch = useDispatch();
  let localNameRef = useRef();
  let reduxState = useSelector((state) => {
    return state;
  });
  return (
    <div
      className="SearchedLocation"
      onClick={() => {
        //redux 변경 함수
        dispatch(changeLnfM(localNameRef.current.textContent));
      }}>
      {locaInfoModal && (
        <LocalInfoModal
          locaInfoModal={locaInfoModal}
          setLocaInfoModal={setLocaInfoModal}
        />
      )}
      <div className="searchedLocation__container">
        <div className="searchedLocation__img-container">
          {reduxState.saveDaysNPickedSuksoRedux.map((state, index) => {
            if (state === local) {
              console.log(reduxState.saveDaysNPickedSuksoRedux);
              console.log(index);
              return (
                <PickedSuksoBadge index={index} local={local} key={index} />
              );
              // return reduxState.saveDaysNPickedSuksoRedux.map((sukso, i) => {
              //   if (sukso !== 0)
              //     return <PickedSuksoBadge index={i} local={local} key={i} />;
              // });
            }
          })}

          <img src="/images/img-8.jpg" />
        </div>
        <div className="searchedLocation__info">
          <h4 className="searchedLocation__info__title" ref={localNameRef}>
            {local}
          </h4>
          <div className="searchedLocation__info__content">
            <FontAwesomeIcon
              icon={faCircleInfo}
              className="searchedLocation__i"
              onClick={() => setLocaInfoModal(true)}
            />
            <FontAwesomeIcon
              icon={faPlus}
              className="searchedLocation__i"
              onClick={(e) => {
                e.stopPropagation();
                dispatch(addOneSaveDaysRedux(local));
                dispatch(changeShowWhichModal(true));
              }}
            />
          </div>
        </div>
      </div>
    </div>
  );
};

function LocalInfoModal({ locaInfoModal, setLocaInfoModal }) {
  return (
    <ContainerLim>
      <OverlayLim onClick={() => setLocaInfoModal(!locaInfoModal)} />
      <ContentsLim>
        <CloseLim onClick={() => setLocaInfoModal(!locaInfoModal)}>X</CloseLim>
        <ImgLim></ImgLim>
        <BodyLim>
          <h4>장소이름</h4>
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
                <li>보기</li>
                <li>www.naver.com</li>
                <li>서울특별시 금천구 가산동</li>
                <li>02-000-0000</li>
                <li>-</li>
              </ul>
            </div>
          </div>
        </BodyLim>
        <div className="localInfo__snsBtn__container">
          <ul className="localInfo__snsBtn-ul">
            <a
              href="https://www.instagram.com/explore/tags/동문재래시장/"
              target="_blank">
              <li>
                <img
                  src={process.env.PUBLIC_URL + "/images/sns/instagram.png"}
                />
              </li>
            </a>
            <a
              href="https://twitter.com/search?q=동문재래시장&src=typed_query"
              target="_blank">
              <li>
                <img src={process.env.PUBLIC_URL + "/images/sns/twitter.png"} />
              </li>
            </a>
            <a href="https://www.google.com/maps/place/영종도" target="_blank">
              <li>
                <img
                  src={process.env.PUBLIC_URL + "/images/sns/googlemap.png"}
                />
              </li>
            </a>
            <a
              href="https://search.naver.com/search.naver?query=동문재래시장"
              target="_blank">
              <li>
                <img src={process.env.PUBLIC_URL + "/images/sns/naver.png"} />
              </li>
            </a>
          </ul>
        </div>
        <div className="localInfo-btn">
          <span>리뷰보기</span>
          <span>목록에 추가</span>
        </div>
      </ContentsLim>
    </ContainerLim>
  );
}

function PickedSuksoBadge({ sukso, index }) {
  return (
    <div>
      <span className="badge">Day{index + 1}</span>{" "}
    </div>
  );
}
export default SearchedSukso;
