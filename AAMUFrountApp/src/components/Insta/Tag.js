import React, { useRef } from "react";
import ReactDOM from "react-dom";
import ReactWordcloud from "react-wordcloud";

import "tippy.js/dist/tippy.css";
import "tippy.js/animations/scale.css";

import styled from "styled-components";

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

export function Tag({tagpop}) {
    
  return (
    <Searchengine >
      <Searchcontents>
        <div style={{ height: 400, width: 600 }}>
          <ReactWordcloud options={options} words={tagpop} />
        </div>
      </Searchcontents>
    </Searchengine>
  );
}

// {tagpop.map((val, i) => {
//     return (
    // <div key={i} style={{ height: 400, width: 600 }}></div>
//       <ReactWordcloud
// options={options} words={tagpop}
//         onClick={(e) => {
//           e.stopPropagation();
//           setHasText(false);
//         }}
//       />
// </div>
//     );
//   })}


const Searchengine = styled.div`
    position: absolute;
    top: 100px;
    width: 600px;
    height: 400px;
    z-index: 9;
    overflow: auto;
    box-shadow: 0 0 5px 2px rgba(var(--jb7, 0, 0, 0), 0.09);
    background-color: #fff;
 `
 const Searchcontents = styled.div`
    position: absolute;
    width: 100%;
    display: flex;
    flex-direction: column;
    
    align-items: center;
    
 `