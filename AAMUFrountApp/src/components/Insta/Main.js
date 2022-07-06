import React, { Component } from "react";
import "./Main.scss";
import { Routes, Route, Link } from "react-router-dom";
import FeedSetting from "./FeedSetting.js";
import User from "./User.js";
import Story from "./Story.js";

function Main() {
  return (
    <div className="main">
        {/* <div className="margin-top"> 
          <Story></Story>   
        </div> */}
        <div className="margin-value"> 
          <div className="main-left">
            <FeedSetting></FeedSetting>
          </div>
          <div className='main-right'>
            <User></User>   
          </div>
        </div>
    </div>
  );
}

export default Main;
