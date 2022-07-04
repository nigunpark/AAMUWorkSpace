import React, { Component } from "react";
import "./Main.scss";
import { Routes, Route, Link } from "react-router-dom";
import Nav from "./Nav.js";
import FeedSetting from "./FeedSetting.js";
import User from "./User.js";
import Story from "./Story.js";

function Main() {
  return (
    <div className="main">
      {/* <Nav></Nav> */}
      {/* <div className="margin-top"> 
            <Story></Story>   
          </div> */}
      <div className="margin-value">
        <FeedSetting></FeedSetting>
        <div className="main-right">
          <User></User>
        </div>
      </div>
    </div>
  );
}

export default Main;
