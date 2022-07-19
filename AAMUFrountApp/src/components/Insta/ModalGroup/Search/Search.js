import React,{useState,useEffect,useCallback,useMemo} from "react";
import styled from "styled-components";
import axios from 'axios';


const search = () => {


  return (
    <div className="search-all disappear">
      <div className="search-engine">
        <div className="parent">
          <div className="search-contents">
            <div className="gradient">
              <img src="img/bk.jpg" alt="스토리 프로필 사진" />
            </div>
            <div>
              <p className="user-id">jenny0305</p>
              <p className="user-name">hi im jenny💙</p>
            </div>
          </div>
        </div>
        <div className="search-square"></div>
      </div>
    </div>
  );
};

export default search;
