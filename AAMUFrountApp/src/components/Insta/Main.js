import React, { Component, useEffect, useRef, useState } from "react";
import "./Main.scss";
import { Routes, Route, Link } from "react-router-dom";
import FeedSetting from "./FeedSetting.js";
import User from "./User.js";
import axios from "axios";
function Main() {

  const [list,setlist] = useState([]);
  function feedList(){
    let token = sessionStorage.getItem("token");
    // axios.get('/aamurest/gram/selectList',{
    //   headers: {
    //         Authorization: `Bearer ${token}`,
    //       },
    // })
    // .then((resp) => {
    //   setlist(resp.data);
    //   })
    //   .catch((error) => {
    //     console.log(error);
    //   });
  }
  useEffect(()=>{
    feedList()
  },[])
  // console.log('list',list)
  return (
    <div className="main">
      {/* <div className="margin-top"> 
          <Story></Story>   
        </div> */}
      <div className="margin-value">
        <div className="main-left" style={{display:'flex',flexDirection:'column'}}>
       {/* {
         list.map((val,i)=>{
           return <FeedSetting val={val}/>
         })
       } */}
        <FeedSetting></FeedSetting>
        </div>
        <div className="main-right">
          <User></User>
        </div>
      </div>
    </div>
  );
}

export default Main;
