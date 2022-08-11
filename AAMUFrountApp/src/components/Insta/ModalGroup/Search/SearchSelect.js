import React from "react";


function SearchSelect({ setSearchb}) {
  return (
    <>
      <select
        name="select"
        className="select"
        onChange={(e) => {
          e.stopPropagation();
          setSearchb(e.target.value);
        }}
      >
        <option defaultValue={"DEFAULT"} value="choice">
          선택
        </option>
        <option value="ctitle">제목</option>
        <option value="id">아이디</option>
        <option value="tname">태그</option>
      </select>
    </>
  );
}

export default SearchSelect;
