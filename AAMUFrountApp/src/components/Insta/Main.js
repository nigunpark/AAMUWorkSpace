import React, { Component } from "react";
import "./Main.scss";
import { Routes, Route, Link } from "react-router-dom";
import Nav from "./Nav.js";
import FeedSetting from "./FeedSetting.js";
import User from "./User.js";
import Story from "./Story.js";
import Imgup from "./Imgup.js";

function Main() {
  return (
    <div className="main">
      <Nav></Nav>
      <div className="margin-top"> 
            <Story></Story>   
          </div>
      <div className="margin-value">
        <FeedSetting />
        <div className="main-right">
          <User />
        </div>
      </div>
    </div>
  );
}

export default Main;
