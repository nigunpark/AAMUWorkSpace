import React, { useEffect, useState } from "react";
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
  const [page, setpage] = useState(1);
  const [loading, setloading] = useState(false);

  const apiUrl = "/aamurest/gram/selectList";

  // .create()를 이용해 axios 인스턴스 생성
  const instance = axios.create({
    baseURL: apiUrl,
  });

  const feedList = async (page) => {
    try {
      setloading(true);
      //백이랑 인스타 리스드를 뿌려주기 위한 axios
      let token = sessionStorage.getItem("token");
      const temp = await axios.get(`/aamurest/gram/selectList?page=${page}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        params: {
          id: sessionStorage.getItem("username"),
        },
      });
      const tempComments = list.concat(temp.data);
      if (page === 1) {
        setlist(temp.data);
      } else {
        setlist([...tempComments]);
      }
      setrecommendUser(temp.data[0].recommenduser);
      setForReRender(!forReRender);

      setloading(false);
    } catch {
      alert("마지막페이지입니다");
      setloading(false);
    }
  };

  useEffect(() => {
    console.log(page);
    feedList(page);
    setinputValue("");
  }, [page]);

  const scrollEvent = () => {
    const scrollHeight = document.documentElement.scrollHeight;
    const scrollTop = document.documentElement.scrollTop;
    const clientHeight = document.documentElement.clientHeight;

    if (scrollTop + clientHeight >= scrollHeight) {
      setpage(page + 1);
    }
  };

  useEffect(() => {
    window.addEventListener("scroll", scrollEvent);

    // unmount시 scrollEvent 제거
    return () => {
      window.removeEventListener("scroll", scrollEvent);
    };
  }, [scrollEvent]);

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
                list={list}
                setlist={setlist}
                forReRender={forReRender}
                setForReRender={setForReRender}
                showChat={showChat}
                setShowChat={setShowChat}
                setPrevChats={setPrevChats}
                inputValue={inputValue}
                setinputValue={setinputValue}
                setloading={setloading}
                page={page}
              />
            );
          })}
        </div>
        <div className="main-right">
          <User
            list={list}
            page={page}
            inputValue={inputValue}
            setinputValue={setinputValue}
            setlist={setlist}
            setloading={setloading}
            searchb={searchb}
            setSearchb={setSearchb}
            showChat={showChat}
            recommendUser={recommendUser}
            setpage={setpage}
            prevChats={prevChats}
            setShowChat={setShowChat}
            showChat={showChat}
          ></User>
        </div>
      </div>
    </div>
  );
}

export default Main;
