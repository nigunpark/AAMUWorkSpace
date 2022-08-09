import React, { useRef } from "react";
import "./App.css";
import Navbar from "./components/Navbar/Navbar";
import { Routes, Route, useLocation, useNavigate } from "react-router-dom";
import Home from "./pages/Home.js";
import { useEffect, useState } from "react";
import WholeMap from "./pages/WholeMap.js";
import MainPage from "./pages/MainPage/MainPage";
import Test from "./Test";
import Main from "./components/Insta/Main";
import LoginTest from "./components/Login/LoginTest";
import Forum from "./pages/Forum/Forum";
import MyPage from "./pages/MyPage/MyPage";
import DetailModal from "./pages/Forum/DetailModal/DetailModal";
import KakaoRedirectHandler from "./components/Login/Kakao/KakaoRedirectHandler";
import JoinStep1 from "./components/Join/JoinStep1.js";
import JoinStep2 from "./components/Join/JoinStep2.js";
import SearchList from "./components/Insta/SearchList";
import Chat from "./components/Chat/Chat";
import QnA from "./components/QnA/QnA";
import CommentSearch from "./components/Insta/ModalGroup/Comment/CommentSearch";
import QnADetail from "./components/QnA/QnADetail";
import QnAWrite from "./components/QnA/QnAWrite";
import QnAEdit from "./components/QnA/QnAEdit";
import SockJS from "sockjs-client";
import * as StompJs from "@stomp/stompjs";
import { useDispatch } from "react-redux";
import { addForChatInfo } from "./redux/store";
import JoinStep3 from "./components/Join/JoinStep3";
import TokenUser from "./components/Login/Kakao/TokenUser";
import LoginFunc from "./components/Login/Kakao/LoginFunc";
import Content from "./pages/Forum/Content/Content";
function App() {
  let dispatch = useDispatch();
  let location = useLocation();
  const client = useRef({});
  useEffect(() => {
    changeLocation(location, setWhereUrl);
  }, [location]);
  useEffect(() => {
    if (sessionStorage.getItem("username") !== null) {
      connect();
    }
  }, []);
  const [scrollNav, setScrollNav] = useState(false);
  const [whereUrl, setWhereUrl] = useState(false);
  const [searchb, setSearchb] = useState([]);
  const [inputValue, setinputValue] = useState("");
  const [list, setlist] = useState([]);
  const [forReRender, setForReRender] = useState(false);
  const navigate = useNavigate();
  const [accountEmail, setaccountEmail] = useState("");
  const [noti, setNoti] = useState([]);
  const handleScroll = () => {
    if (window.scrollY > 950 && window.scrollY < 2500) setScrollNav(true);
    else setScrollNav(false);
  };
  window.addEventListener("scroll", handleScroll);

  const subscribe = () => {
    client.current.subscribe(
      `/queue/notification/${sessionStorage.getItem("username")}`,
      ({ body }) => {
        // setChatMessages((_chatMessages) => [..._chatMessages, JSON.parse(body)]);
        console.log("body", body);
        setNoti((curr) => {
          return [...curr, JSON.parse(body)];
        });
        console.log("noti", noti);
        // console.log([...noti, JSON.parse(body)]);
      }
    );
  };
  //채팅연결
  const connect = () => {
    // client.current = new StompJs.Client();
    client.current = new StompJs.Client({
      brokerURL: "ws://192.168.0.22:8080/aamurest/ws/chat/websocket",
      // connectHeaders: {
      //   "auth-token": "spring-chat-auth-token",
      // },
      debug: function (str) {
        console.log(str);
      },
      // reconnectDelay: 5000,
      // heartbeatIncoming: 4000,
      // heartbeatOutgoing: 4000,
      onConnect: () => {
        console.log("connect됨");
        subscribe();
      },
      onStompError: (frame) => {
        console.error("error", frame);
      },
    });
    dispatch(addForChatInfo({ client: client.current }));
    console.log("curr", client.current);
    client.current.activate();
  };
  return (
    <div>
      <Routes>
        <Route
          element={
            <Navbar scrollNav={scrollNav} whereUrl={whereUrl} noti={noti} setNoti={setNoti} />
          }
        >
          <Route path="/" element={<Home />} />
          <Route path="/WholeMap" element={<WholeMap />} />
          <Route path="/mainPage/:currPosition" element={<MainPage />} />
          <Route path="/forum" element={<Content />} />
          <Route path="/qna" element={<QnA />} />
          <Route path="/qnaDetail" element={<QnADetail />} />
          <Route path="/qnaWrite" element={<QnAWrite />} />
          {/* <Route path="/admin" element="http://192.168.0.22:9090/admin/" /> */}

          {/* <Route path="/review" element={<Board />} /> */}
          {/* <Route path="/Insta" element={<Main />} /> */}
          {/* <Route
            path="/Insta/searchList"
            element={<SearchList searchb={searchb} setSearchb={setSearchb} />}
          /> */}
          <Route
            path="/Insta"
            element={
              <Main
                inputValue={inputValue}
                setinputValue={setinputValue}
                searchb={searchb}
                setSearchb={setSearchb}
                list={list}
                setlist={setlist}
                forReRender={forReRender}
                setForReRender={setForReRender}
              />
            }
          />
          <Route
            path="/Insta/searchList"
            element={
              <SearchList
                inputValue={inputValue}
                searchb={searchb}
                forReRender={forReRender}
                setForReRender={setForReRender}
              />
            }
          />
          <Route path="/myPage" element={<MyPage />} />
        </Route>
        {/* <Route path="/chat" element={<Chat />} /> */}
        <Route path="/login" element={<LoginTest />}></Route>
        <Route path="/join" element={<JoinStep1 />} />
        <Route path="/joinTwo" element={<JoinStep2 />} />
        <Route path="/joinThree" element={<JoinStep3 />} />
        <Route path="/login" element={<LoginTest />} />
        <Route path="/test" element={<Test />} />
        <Route path="/Detailmodal" element={<DetailModal />} />
        <Route
          path="/tokenuser"
          element={
            <TokenUser
              navigate={navigate}
              accountEmail={accountEmail}
              setaccountEmail={setaccountEmail}
            />
          }
        />
        <Route
          path="/loginFunc"
          element={<LoginFunc navigate={navigate} accountEmail={accountEmail} />}
        />
        <Route
          path="/oauth/callback/kakao"
          element={<KakaoRedirectHandler navigate={navigate} />}
        />
      </Routes>
    </div>
  );
}
function changeLocation(location, setWhereUrl) {
  if (
    location.pathname.indexOf("mainPage") === 1 ||
    location.pathname.indexOf("forum") === 1 ||
    location.pathname.indexOf("login") === 1 ||
    location.pathname.indexOf("myPage") === 1 ||
    location.pathname.indexOf("Insta") === 1 ||
    location.pathname.indexOf("qna") === 1
    // location.pathname.indexOf("WholeMap") === 1
  )
    setWhereUrl(true);
  else setWhereUrl(false);
}
export default App;
