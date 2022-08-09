import React, {useEffect, useState } from "react";
import "./Main.scss";
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
  const [recommendUser, setrecommendUser] = useState([]);
  const [prevChats, setPrevChats] = useState([]);
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
        setrecommendUser(resp.data[0].recommenduser);
        console.log(resp.data[0].recommenduser);
        setForReRender(!forReRender);
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
        <div className="main-left" style={{ display: "flex", flexDirection: "column" }}>
          {loading && <Spinner />}
          {list.map((val, i) => {
            return (
              <FeedSetting
                key={i}
                val={val}
                setlist={setlist}
                forReRender={forReRender}
                setForReRender={setForReRender}
                showChat={showChat}
                setShowChat={setShowChat}
                setPrevChats={setPrevChats}
                inputValue={inputValue} 
                setinputValue={setinputValue}
              />
            );
          })}
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
            recommendUser={recommendUser}
            setrecommendUser={setrecommendUser}
            prevChats={prevChats}
          ></User>
        </div>
      </div>
    </div>
  );
}

export default Main;
