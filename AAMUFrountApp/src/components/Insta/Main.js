import React, { Component, useEffect, useRef, useState } from "react";
import "./Main.scss";
import { Routes, Route, Link } from "react-router-dom";
import FeedSetting from "./FeedSetting.js";
import User from "./User.js";
import axios from "axios";
import Spinner from "./Spinner";
function Main({
  searchb,
  setSearchb,
  inputValue,
  setinputValue,
  list,
  setlist,
  forReRender,
  setForReRender,
}) {
  const [showChat, setShowChat] = useState(false);
  const [val, setval] = useState([]);
  function feedList() {
    //백이랑 인스타 리스드를 뿌려주기 위한 axios
    let token = sessionStorage.getItem("token");
    axios
      .get("/aamurest/gram/selectList", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        params: {
          id: sessionStorage.getItem("username"),
        },
      })
      .then((resp) => {
        console.log(resp.data);
        setlist(resp.data);
        setForReRender(!forReRender)
      })
      .catch((error) => {
        console.log(error);
      });
  }

  useEffect(() => {
    feedList();
    setinputValue("");
  }, []);

  const spinner = document.querySelector(".spinner");
  const [loading, setloading] = useState(false);

  return (
    <div className="main">
      {/* <div className="margin-top">  
          <Story></Story>   
        </div> */}
      <div className="margin-value">
        <div
          className="main-left"
          style={{ display: "flex", flexDirection: "column" }}
        >
          {loading && <Spinner />}
          {list.map((val, i) => {
            return (
              <FeedSetting
                val={val}
                setlist={setlist}
                forReRender={forReRender}
                setForReRender={setForReRender}
                setloading={setloading}
                setShowChat={setShowChat}
                showChat={showChat}
                
              />
            );
          })}
          {/* <FeedSetting></FeedSetting> */}
        </div>
        <div className="main-right">
          <User
          list={list}
            inputValue={inputValue}
            setinputValue={setinputValue}
            setlist={setlist}
            setloading={setloading}
            searchb={searchb}
            setSearchb={setSearchb}
            showChat={showChat}
          ></User>
        </div>
      </div>
    </div>
  );
}

export default Main;
