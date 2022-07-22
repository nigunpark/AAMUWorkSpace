import React, { Component, useEffect, useRef, useState } from "react";
import "./Main.scss";
import { Routes, Route, Link } from "react-router-dom";
import FeedSetting from "./FeedSetting.js";
import User from "./User.js";
import axios from "axios";
function Main() {

  const [list,setlist] = useState([]);
  const [forReRender, setForReRender] = useState(false)
  function feedList(){//백이랑 인스타 리스드를 뿌려주기 위한 axios
    let token = sessionStorage.getItem("token");
    axios.get('/aamurest/gram/selectList',{
      headers: {
            Authorization: `Bearer ${token}`,
          },
          params:{
            id:sessionStorage.getItem('username')
          }
    })
    .then((resp) => {
      console.log(resp.data)
      setlist(resp.data);
      })
      .catch((error) => {
        console.log(error);
      });
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
        {
          list.map((val,i)=>{
            return <FeedSetting val={val} setlist={setlist} forReRender={forReRender} setForReRender={setForReRender} />
          })
        }
        {/* <FeedSetting></FeedSetting> */}
        </div>
        <div className="main-right">
          <User setlist={setlist}></User>
        </div>
      </div>
    </div>
  );
}

export default Main;
