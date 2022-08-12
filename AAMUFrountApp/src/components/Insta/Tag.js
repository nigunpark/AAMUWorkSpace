import React, { useRef, useState } from "react";
import ReactDOM from "react-dom";
import ReactWordcloud from "react-wordcloud";

import "tippy.js/dist/tippy.css";
import "tippy.js/animations/scale.css";

import styled from "styled-components";
import axios from "axios";
import { useNavigate } from "react-router-dom";
// import words from "./Words";

const options = {
  colors: ["#1f77b4", "#ff7f0e", "#2ca02c", "#d62728", "#9467bd", "#8c564b"],
  enableTooltip: true,
  deterministic: false,
  fontFamily: "impact",
  fontSizes: [5, 60],
  fontStyle: "normal",
  fontWeight: "normal",
  padding: 1,
  rotations: 3,
  rotationAngles: [0, 90],
  scale: "sqrt",
  spiral: "archimedean",
  transitionDuration: 1000,
};

export function Tag({  tagpop, setSearchb, setinputValue }) {
  // const [tagcl, settagcl] = useState("");
  const searchRef = useRef();
  const navigate = useNavigate();

  function searchBarTag(e) {
    const tag = 'tname';
    const tagcl =e.target.innerHTML
    //백이랑 인스타 리스드를 뿌려주기 위한 axios
    let token = sessionStorage.getItem("token");
    axios
      .get("/aamurest/gram/selectList", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        params: {
          searchColumn: tag,
          searchWord: tagcl,
        },
      })
      .then((resp) => {
        console.log(resp.data);
        setSearchb(resp.data)
        navigate('/Insta/searchList')
      })
      .catch((error) => {
        console.log(error);
      });
  }

  return (
    <Searchengine>
      <Searchcontents>
        <div style={{ height: 400, width: 600 }}>
          <ReactWordcloud
            options={options}
            words={tagpop}
            onClick={(e) => {
              e.stopPropagation();              
              setinputValue(e.target.innerHTML);
              searchBarTag(e)
              console.log(e.target.innerHTML);
            }}
          />
        </div>
      </Searchcontents>
    </Searchengine>
  );
}

const Searchengine = styled.div`
  position: absolute;
  top: 100px;
  width: 600px;
  height: 400px;
  z-index: 9;
  overflow: auto;
  box-shadow: 0 0 5px 2px rgba(var(--jb7, 0, 0, 0), 0.09);
  background-color: #fff;
`;
const Searchcontents = styled.div`
  position: absolute;
  width: 100%;
  display: flex;
  flex-direction: column;

  align-items: center;
`;
