import { style } from "@mui/system";
import React from "react";
import styled from "styled-components";

function SearchModal({ search, setlistid, setHasText }) {
  return (
    // <SearchAll>
    <Searchengine>
      <Searchcontents>
        {search.map((val, i) => {
          return (
            <P
              key={i}
              onClick={(e) => {
                e.stopPropagation();
                setlistid({ TITLE: val.TITLE, CONTENTID: val.CONTENTID });
                setHasText(false);
              }}
            >
              {val.TITLE}
            </P>
          );
        })}
        {/* // val.indexOf(inputValue)!==-1?<P onClick={()=>onClick(i)}>{val.TITLE}</P>  */}
      </Searchcontents>
    </Searchengine>
    /* </SearchAll> */
  );
}

const Searchengine = styled.div`
  position: absolute;
  width: 35%;
  height: 300px;
  background-color: transparent;
  right: 10px;
  z-index: 9;
  overflow: auto;
`;
const Searchcontents = styled.div`
  position: absolute;
  width: 95%;
  display: flex;
  background-color: #fff;
  flex-direction: column;
  box-shadow: 0 0 5px 1px rgba(var(--jb7, 0, 0, 0), 0.0975);
  align-items: center;
  margin-left: 10px;
`;
const P = styled.div`
  display: flex;
  position: relative;
  width: 100%;
  height: 50px;
  padding-left: 10px;
  align-items: center;
  cursor: pointer;
  font-size: 15px;

  &:hover {
    background-color: #e5e5e5;
  }
`;

export default SearchModal;
