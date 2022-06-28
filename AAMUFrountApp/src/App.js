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
          <Route path="/" element={<Home />}></Route>
          <Route path="/WholeMap" element={<WholeMap />}></Route>
          <Route path="/mainPage/:currPosition" element={<MainPage />}></Route>
        </Route>
        <Route path="/login" element={<Login />}></Route>
        <Route path="/test" element={<Test />}></Route>
      </Routes>
    </div>
  );
}

function changeLocation(location, setWhereUrl) {
  if (location.pathname.indexOf("mainPage") === 1) setWhereUrl(true);
  else setWhereUrl(false);
}
export default App;
