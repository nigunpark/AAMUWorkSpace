import React from "react";
import "./Search.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";
const Search = () => {
  return (
    <>
      <div className="search__container">
        <form action="#" method="post" className="search__container__form">
          <input type="text" placeholder="검색어를 입력하세요" />
          <button type="submit">
            <FontAwesomeIcon icon={faMagnifyingGlass} className="search__i" />
          </button>
        </form>
      </div>
    </>
  );
};

export default Search;
