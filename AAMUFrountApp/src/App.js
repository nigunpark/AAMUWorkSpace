import React from "react";
import "./App.css";
import Navbar from "./components/Navbar/Navbar";
import { Routes, Route, useLocation } from "react-router-dom";
import Home from "./pages/Home.js";
import { useEffect, useState } from "react";
import WholeMap from "./pages/WholeMap.js";
import MainPage from "./pages/MainPage/MainPage";
import Login from "./components/Login/Login";
import Test from "./Test";
import Main from "./components/Insta/Main";
import LoginTest from "./components/Login/LoginTest";
import Forum from "./pages/Forum/Forum";
import MyPage from "./pages/MyPage/MyPage";
import DetailModal from "./pages/Forum/DetailModal/DetailModal";
import KakaoRedirectHandler from "./components/Login/Kakao/KakaoRedirectHandler";
import Join from "./Join/Join";

function App() {
  let location = useLocation();
  useEffect(() => {
    changeLocation(location, setWhereUrl);
  }, [location]);
  const [scrollNav, setScrollNav] = useState(false);
  const [whereUrl, setWhereUrl] = useState(false);

  const handleScroll = () => {
    if (window.scrollY > 990 && window.scrollY < 1780) setScrollNav(true);
    else setScrollNav(false);
  };

  window.addEventListener("scroll", handleScroll);

  return (
    <div>
      <Routes>
        <Route element={<Navbar scrollNav={scrollNav} whereUrl={whereUrl} />}>
          <Route path="/" element={<Home />} />
          <Route path="/WholeMap" element={<WholeMap />} />
          <Route path="/mainPage/:currPosition" element={<MainPage />} />
          <Route path="/forum" element={<Forum />} />
          {/* <Route path="/review" element={<Board />} /> */}
          <Route path="/Insta" element={<Main />} />
          <Route path="/Insta" element={<Main />}></Route>
          <Route path="/login" element={<LoginTest />}></Route>
          <Route path="/myPage" element={<MyPage />} />
        </Route>
        <Route path="/join" element={<Join />} />
        <Route path="/login" element={<LoginTest />} />
        <Route path="/test" element={<Test />} />
        <Route path="/Detailmodal" element={<DetailModal />} />

        <Route
          path="/oauth/callback/kakao"
          element={<KakaoRedirectHandler />}
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
    location.pathname.indexOf("Insta") === 1
  )
    setWhereUrl(true);
  else setWhereUrl(false);
}
export default App;
