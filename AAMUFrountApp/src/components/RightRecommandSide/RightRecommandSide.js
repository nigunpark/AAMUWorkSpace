import { faBed, faLocation, faLocationDot, faUtensils } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { useEffect, useRef, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import Search from "../Search/Search";
import SearchedLocation from "../SearchedLocation/SearchedLocation";
import "./RightRecommandSide.css";
import { addArrForSukso, addArrInForJangso, changeInfo } from "../../redux/store";
import SearchedSukso from "../SearchedLocation/SearchedSukso";
import { faX } from "@fortawesome/free-solid-svg-icons";
import SearchedMazzip from "../SearchedLocation/SearchedMazzip";
const RightRecommandSide = ({
  titleName,
  setTitleName,
  conWhichModal,
  areaCode,
  forSearchTypeId,
  setForSearchTypeId,
}) => {
  let reduxState = useSelector((state) => {
    return state;
  });
  let dispatch = useDispatch();
  //추천숙소선택시 숙소버튼선택용
  let suksoRef = useRef();
  //추천장소선택시 장소버튼선택용
  let jangsoRef = useRef();
  //추천맛집선택시 맛집버튼선택용
  let restaurantRef = useRef();
  useEffect(() => {
    if (conWhichModal === 1) {
      jangsoRef.current.classList.remove("rps__type-btn-picked");
      restaurantRef.current.classList.remove("rps__type-btn-picked");
      suksoRef.current.classList.add("rps__type-btn-picked");
    } else if (conWhichModal === 2) {
      suksoRef.current.classList.remove("rps__type-btn-picked");
      jangsoRef.current.classList.remove("rps__type-btn-picked");
      restaurantRef.current.classList.add("rps__type-btn-picked");
    } else {
      suksoRef.current.classList.remove("rps__type-btn-picked");
      restaurantRef.current.classList.remove("rps__type-btn-picked");
      jangsoRef.current.classList.add("rps__type-btn-picked");
    }
  }, [conWhichModal]);

  const [searchedData, setSearchedData] = useState([]);
  const [showSearchedData, setShowSearchedData] = useState(false);

  return (
    <div className="RightRecommandSide">
      <div style={{ position: "relative" }}>
        <Search
          setSearchedData={setSearchedData}
          areaCode={areaCode}
          forSearchTypeId={forSearchTypeId}
          showSearchedData={showSearchedData}
          setShowSearchedData={setShowSearchedData}
        />
        {showSearchedData && (
          <div
            className="searchedResult__container"
            style={{
              width: "100%",
              height: "auto",
              boxShadow: "var(--shadow)",
            }}
          >
            <div className="searchedResult">
              {searchedData.map((val, i) => {
                return (
                  <div
                    key={i}
                    className="searchedOne"
                    onClick={(e) => {
                      e.stopPropagation();
                      if (val.contenttypeid === 12) {
                        dispatch(addArrInForJangso(val));
                      } else {
                        dispatch(addArrForSukso(val));
                      }
                      setShowSearchedData(false);
                    }}
                  >
                    {val.title}
                  </div>
                );
              })}
            </div>
            <div
              className="searched__xIcon"
              onClick={(e) => {
                e.stopPropagation();
                setShowSearchedData(false);
              }}
            >
              <FontAwesomeIcon icon={faX} style={{ fontSize: "15px" }} />
            </div>
          </div>
        )}
      </div>

      <div className="rightRecommandSide__span__container">
        <span
          ref={suksoRef}
          className="rightRecommandSide__span sukbak"
          onClick={(e) => {
            e.stopPropagation();
            setForSearchTypeId(32);
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
          ref={restaurantRef}
          className="rightRecommandSide__span restaurant"
          onClick={(e) => {
            e.stopPropagation();
            setForSearchTypeId(12);
            toggleBtn(e);
            setTitleName("맛집");
            dispatch(changeInfo("맛집"));
          }}
        >
          <FontAwesomeIcon
            icon={faUtensils}
            className="rightRecommandSide__span-i"
            style={{ marginRight: "3px" }}
          />
          맛집
        </span>
        <span
          ref={jangsoRef}
          className="rightRecommandSide__span jangso rps__type-btn-picked"
          onClick={(e) => {
            e.stopPropagation();
            setForSearchTypeId(12);
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
            arrForRestaurant={reduxState.arrForRestaurant}
            searchedData={searchedData}
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
    case "추천맛집":
      return <h3>추천맛집</h3>;
    case "숙소":
      return <h3>숙소를 검색하세요</h3>;
    case "맛집":
      return <h3>맛집을 검색하세요</h3>;
    default:
      return <h3>장소를 검색하세요</h3>;
  }
}

function RightSideInfo({ kindOfInfo, arrForJangso, arrForSukso, searchedData, arrForRestaurant }) {
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
    case "추천맛집":
      return (
        <>
          {arrForRestaurant.map((local, index) => {
            return <SearchedMazzip key={index} local={local} index={index} />;
          })}
        </>
      );
    case "숙소":
    case "맛집":
    case "장소":
      return <RightSideModal searchedData={searchedData} />;
    // case "맛집":
    //   return <RightSideModal searchedData={searchedData} />;
    // case "장소":
    //   return <RightSideModal searchedData={searchedData} />;
  }
}

function RightSideModal({ searchedData }) {
  return (
    <div className="rightSideModal">
      <FontAwesomeIcon icon={faLocation} className="rightSideModal_icon" />

      <div className="rightSideModal__span">장소명을 검색하세요.</div>
      <div className="rightSideModal__span">검색어는 두 글자 이상 입력해주세요.</div>
    </div>
  );
}

function toggleBtn(e) {
  if (e.target.nodeName === "SPAN") {
    if (e.target.classList.contains("sukbak")) {
      [...e.target.parentElement.children]
        .filter((val) => {
          return !val.classList.contains("sukbak");
        })
        .forEach((val) => val.classList.remove("rps__type-btn-picked"));
      e.target.classList.add("rps__type-btn-picked");
    } else if (e.target.classList.contains("restaurant")) {
      [...e.target.parentElement.children]
        .filter((val) => {
          return !val.classList.contains("restaurant");
        })
        .forEach((val) => val.classList.remove("rps__type-btn-picked"));
      e.target.classList.add("rps__type-btn-picked");
    } else {
      [...e.target.parentElement.children]
        .filter((val) => {
          return !val.classList.contains("jangso");
        })
        .forEach((val) => val.classList.remove("rps__type-btn-picked"));
      e.target.classList.add("rps__type-btn-picked");
    }
  }
}
export default RightRecommandSide;
