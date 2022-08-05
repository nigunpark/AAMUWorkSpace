import React from "react";

const MyThemeLists = ({ theme }) => {
  return (
    <>
      {theme.map((val, idx) => {
        return (
          <div key={idx} className="projects-theme-item">
            {val.THEMENAME}
          </div>
        );
      })}
    </>
  );
};

export default MyThemeLists;
