import React,{useState,useEffect,useCallback,useMemo} from "react";
import styled from "styled-components";
import axios from 'axios';


const search = ({ setsearchText, setinputValue,title}) => {


  return (
    <Searchengine>
            <Searchcontents>
            {title.length > 1 &&
            title.map((val,i)=>{return <P onClick={(e)=>{
              e.stopPropagation();
                setinputValue(e.target.textContent)
                setsearchText(false)}              
              }>{val}</P>}
            )}
          </Searchcontents>
        </Searchengine>
  );
};
const Searchengine = styled.div`
    position: absolute;
    width: 100%;
    height: 300px;
    background-color: transparent;
    z-index: 9;
    overflow: auto;
 `
 const Searchcontents = styled.div`
    position: absolute;
    width: 100%;
    display: flex;
    background-color: #fff;
    flex-direction: column;
    
    box-shadow: 0 0 5px 1px rgba(var(--jb7, 0, 0, 0), 0.7);  
    align-items: center;
    
 `
 const P = styled.div`  
    display: flex;
    position: relative;
    width: 100%;
    height: 50PX;
    padding-left: 10px;
    align-items: center;
    cursor:pointer;
    &:hover {
      background-color: #e5e5e5;
    }
 `
export default search;
