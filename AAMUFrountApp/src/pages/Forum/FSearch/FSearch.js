import React from 'react'
import "./FSearch.scss";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";
import FSelect from '../FSelect/FSelect';

const FSearch = () => {
  return (
    <>
      <div className="FS_search__container">
          <form action="#" method="post" className="FS_search__container__form">
            {/* 큼지막한 기능 다 만들면 정렬버튼 */}
            <p></p>
            {/* <FSelect/> */}
            <input type="text" placeholder="검색어를 입력하세요" />
            <button type="submit">
              <FontAwesomeIcon icon={faMagnifyingGlass} className="FS_search__i" />
            </button>
          </form>
      </div>
    </>
  )
}

export default FSearch