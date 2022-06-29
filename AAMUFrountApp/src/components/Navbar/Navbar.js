import React, { useState, useEffect } from "react";
import { Link, useNavigate, useLocation } from "react-router-dom";
import "./Navbar.css";
import { Outlet } from "react-router-dom";
const Navbar = ({ scrollNav, whereUrl }) => {
  const [click, setClick] = useState(false);
  const handleClick = () => setClick(!click);
  const closeMoblieMenu = () => setClick(false);
  let navigate = useNavigate();

  return (
    <div className="navbar__fragment">
      <nav
        className={
          scrollNav
            ? "navbar scrollActive"
            : whereUrl
            ? "navbar whereUrlActive "
            : "navbar"
        }>
        <div className="navbar-container">
          <div className="navbar-logo">
            <Link to="/">
              <h1>AAMU</h1>
            </Link>
          </div>
          <ul className={click ? "nav-menu active" : "nav-menu"}>
            <li>
              <Link to="/" className="nav-links" onClick={closeMoblieMenu}>
                Home
              </Link>
            </li>
            <li>
              <Link
                to="/services"
                className="nav-links"
                onClick={closeMoblieMenu}>
                Services
              </Link>
            </li>
            <li>
              <Link
                to="/Insta"
                className="nav-links"
                onClick={closeMoblieMenu}>
                Insta
              </Link>
            </li>
            <li>
              <Link
                to="/others"
                className="nav-links"
                onClick={closeMoblieMenu}>
                others
              </Link>
            </li>
            <li>
              <Link
                to="/forum"
                className="nav-links"
                onClick={closeMoblieMenu}>
                forum
              </Link>
            </li>
          </ul>
          <div className="navbar-btn-container">
            <button
              className="navbar-btn"
              onClick={() => {
                navigate("/login");
              }}>
              로그인
            </button>
            <button className="navbar-btn">회원가입</button>
          </div>
          <div className="navbar-menu-icon" onClick={handleClick}>
            <i className={click ? "fas fa-times" : "fas fa-bars"} />
          </div>
        </div>
      </nav>
      <Outlet />
    </div>
  );
};

export default Navbar;
