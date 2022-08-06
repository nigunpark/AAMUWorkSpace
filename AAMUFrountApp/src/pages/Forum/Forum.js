import React from "react";
import Notice from "./Notice/Notice";
import Content from "./Content/Content";
import { useLocation } from "react-router-dom";

const Forum = () => {
  return (
    <div>
      {/* <Notice/> */}
      <Content />
    </div>
  );
};

export default Forum;
