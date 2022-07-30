import { style } from "@mui/system";
import React from "react";
import styled from "styled-components";

function SearchModal({ search, setinputValue, setHasText }) {
  return (
    // <SearchAll>
    <Searchengine>
      <div>
        {search.map((val, i) => {
          return (
            <P
              onClick={(e) => {
                e.stopPropagation();
                setinputValue(e.target.textContent);
                setHasText(false);
              }}
            >
              {val.TITLE}
            </P>
          );
        })}
        {/* // val.indexOf(inputValue)!==-1?<P onClick={()=>onClick(i)}>{val.TITLE}</P>  */}
      </div>
    </Searchengine>
    /* </SearchAll> */
  );
}

const Searchengine = styled.div`
  // position: absolute;
  // width: 35%;
  // height: 300px;
  // background-color: transparent;
  // right: 10px;
  // z-index: 100;
  // overflow: auto;
  // border: 1px solid balck;

  position: fixed;
  display: auto;
  width: 150px;
  height: 300px;
  z-index: 1000;
  top: 35%;
  left: 70%;
  right: 0;
  bottom: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: auto;
  border-radius: 10px;
  box-shadow: 0 0 0 2px #ffffff, 0 0 0 5px #e9ecef;
  background-color: #ffffff;
  animation: slide-in-right 0.5s cubic-bezier(0.25, 0.46, 0.45, 0.94) both;
`;
const Searchcontents = styled.div`
  position: absolute;
  // width: 95%;
  display: flex;
  background-color: #fff;
  flex-direction: column;
  box-shadow: 0 0 5px 1px rgba(var(--jb7, 0, 0, 0), 0.0975);
  align-items: center;
  margin-left: 10px;
  border: 1px solid balck;
`;
const P = styled.div`
  display: flex;
  position: relative;
  // width: 100%;
  height: 50px;
  padding-left: 10px;
  align-items: center;
  cursor: pointer;
  border: 1px solid balck;

  &:hover {
    background-color: #e5e5e5;
  }
`;

export default SearchModal;
