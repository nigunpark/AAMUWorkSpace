import React, { useRef, useState } from "react";
import "./Search.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";
import axios from "axios";
const Search = ({
  setSearchedData,
  areaCode,
  forSearchTypeId,
  showSearchedData,
  setShowSearchedData,
}) => {
  const [searchWord, setSearchWord] = useState("");

  let searchRef = useRef();
  return (
    <>
      <div className="search__container">
        <input
          ref={searchRef}
          type="text"
          placeholder="검색어를 입력하세요"
          onClick={() => {
            setShowSearchedData(true);
          }}
          onKeyUp={(e) => {
            e.stopPropagation();

            setSearchWord(e.target.value);
            searching(
              e,
              areaCode,
              forSearchTypeId,
              searchWord,
              setSearchedData
            );
            if (e.target.value.length === 0) setShowSearchedData(false);
          }}
        />
        <span>
          <FontAwesomeIcon icon={faMagnifyingGlass} className="search__i" />
        </span>
      </div>
    </>
  );
};

function searching(e, areaCode, forSearchTypeId, searchWord, setSearchedData) {
  // if (e.key === "Enter") {
  let token = sessionStorage.getItem("token");
  axios
    .get("/aamurest/info/search", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
      params: {
        areacode: areaCode,
        contenttypeid: forSearchTypeId,
        searchword: searchWord,
      },
    })
    .then((resp) => {
      // console.log(resp.data);
      setSearchedData(resp.data);
    })
    .catch((err) => {
      console.log(err);
    });
  // }
}
export default Search;
