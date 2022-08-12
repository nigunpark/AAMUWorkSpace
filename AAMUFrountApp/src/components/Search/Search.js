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
  let selectRef = useRef();
  return (
    <>
      <div className="search__container">
        <select
          ref={selectRef}
          onChange={() => {
            console.log(selectRef.current.value);
          }}
        >
          <option>장소</option>
          <option>주소</option>
        </select>
        <input
          ref={searchRef}
          type="text"
          placeholder="검색어를 입력하세요"
          onKeyDown={(e) => {
            e.stopPropagation();
            if (e.key === "Enter") {
              setShowSearchedData(true);
              setSearchWord(e.target.value);
              searching(selectRef, areaCode, forSearchTypeId, searchRef, setSearchedData);
            }
          }}
          onChange={(e) => {
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

function searching(selectRef, areaCode, forSearchTypeId, searchRef, setSearchedData) {
  // if (e.key === "Enter") {
  let token = sessionStorage.getItem("token");
  axios
    .get("/aamurest/info/search", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
      params: {
        searchColumn: selectRef.current.value,
        areacode: areaCode,
        contenttypeid: forSearchTypeId,
        searchword: searchRef.current.value,
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
