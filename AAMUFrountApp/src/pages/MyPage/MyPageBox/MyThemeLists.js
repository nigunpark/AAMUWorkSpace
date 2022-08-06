import React from "react";

const MyThemeLists = ({ theme }) => {
  let arrTemp = [];
  return (
    <>
      {theme.map((val, idx) => {
        return (
          <span key={idx} className="projects-theme-item">
            {val}
          </span>
        );
      })}
    </>
  );
};

export default MyThemeLists;
