import {
  faBed,
  faLocation,
  faLocationDot,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { useEffect, useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import Search from "../Search/Search";
import SearchedLocation from "../SearchedLocation/SearchedLocation";
import "./RightRecommandSide.css";
import { changeInfo } from "../../redux/store";
import SearchedSukso from "../SearchedLocation/SearchedSukso";
const RightRecommandSide = ({ titleName, setTitleName, conWhichModal }) => {
  let reduxState = useSelector((state) => {
    return state;
  });
  let dispatch = useDispatch();
  //추천숙소선택시 숙소버튼선택용
  let suksoRef = useRef();
  //추천장소선택시 장소버튼선택용
  let jangsoRef = useRef();
  useEffect(() => {
    if (conWhichModal === true) {
      suksoRef.current.classList.add("rps__type-btn-picked");
      jangsoRef.current.classList.remove("rps__type-btn-picked");
    } else {
      suksoRef.current.classList.remove("rps__type-btn-picked");
      jangsoRef.current.classList.add("rps__type-btn-picked");
    }
  }, [conWhichModal]);
  return (
    <div className="RightRecommandSide">
      <div>
        <Search />
      </div>
      <div className="rightRecommandSide__span__container">
        <span
          ref={suksoRef}
          className="rightRecommandSide__span sukbak"
          onClick={(e) => {
            toggleBtn(e);
            setTitleName("숙소");
            dispatch(changeInfo("숙소"));
          }}
        >
          <FontAwesomeIcon
            icon={faBed}
            className="rightRecommandSide__span-i"
            style={{ marginRight: "3px" }}
          />
          숙소
        </span>
        <span
          ref={jangsoRef}
          className="rightRecommandSide__span jangso rps__type-btn-picked"
          onClick={(e) => {
            toggleBtn(e);
            setTitleName("장소");
            dispatch(changeInfo("장소"));
          }}
        >
          <FontAwesomeIcon
            icon={faLocationDot}
            className="rightRecommandSide__span-i"
            style={{ marginRight: "3px" }}
          />
          장소
        </span>
      </div>

      <div className="rightRecommandSide__searchedLocation__container">
        <div className="rightSideTitle">
          <RightSideTitle titleName={titleName} />
        </div>
        <div className="rightSideInfo">
          <RightSideInfo
            kindOfInfo={reduxState.kindOfInfo}
            arrForJangso={reduxState.arrForJangso}
            arrForSukso={reduxState.arrForSukso}
          />
        </div>
      </div>
    </div>
  );
};
function RightSideTitle({ titleName }) {
  switch (titleName) {
    case "추천장소":
      return <h3>추천장소</h3>;
    case "추천숙소":
      return <h3>추천숙소</h3>;
    case "숙소":
      return <h3>숙소를 검색하세요</h3>;
    default:
      return <h3>장소를 검색하세요</h3>;
  }
}

function RightSideInfo({ kindOfInfo, arrForJangso, arrForSukso }) {
  switch (kindOfInfo) {
    case "추천장소":
      return (
        <>
          {arrForJangso.map((local, index) => {
            return <SearchedLocation key={index} local={local} />;
          })}
        </>
      );

    case "추천숙소":
      return (
        <>
          {arrForSukso.map((local, index) => {
            return <SearchedSukso key={index} local={local} index={index} />;
          })}
        </>
      );
    case "숙소":
      return <RightSideModal />;
    case "장소":
      return <RightSideModal />;
  }
}

function RightSideModal() {
  return (
    <div className="rightSideModal">
      <FontAwesomeIcon icon={faLocation} className="rightSideModal_icon" />

      <div className="rightSideModal__span">장소명을 검색하세요.</div>
      <div className="rightSideModal__span">
        검색어는 두 글자 이상 입력해주세요.
      </div>
    </div>
  );
}

function toggleBtn(e) {
  if (e.target.nodeName === "SPAN") {
    if (e.target.classList.contains("sukbak")) {
      e.target.nextElementSibling.classList.remove("rps__type-btn-picked");
      e.target.classList.add("rps__type-btn-picked");
    } else {
      e.target.previousElementSibling.classList.remove("rps__type-btn-picked");
      e.target.classList.add("rps__type-btn-picked");
    }
  }
}
export default RightRecommandSide;
