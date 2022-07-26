import React, { useState, useEffect, useRef } from "react";
import { Link, useNavigate, useLocation } from "react-router-dom";
import "./Navbar.css";
import { Outlet } from "react-router-dom";
import styled, { keyframes } from "styled-components";
import { useSelector } from "react-redux";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMessage, faRobot } from "@fortawesome/free-solid-svg-icons";
import ChatBot from "../ChatBot/ChatBot";
const Navbar = ({ scrollNav, whereUrl }) => {
  const [click, setClick] = useState(false);
  const handleClick = () => setClick(!click);
  const closeMoblieMenu = () => setClick(false);
  const [showChatBot, setShowChatBot] = useState(false);
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
        }
      >
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
                onClick={closeMoblieMenu}
              >
                Services
              </Link>
            </li>
            <li>
              <Link to="/Insta" className="nav-links" onClick={closeMoblieMenu}>
                Insta
              </Link>
            </li>
            <li>
              <Link
                to="/others"
                className="nav-links"
                onClick={closeMoblieMenu}
              >
                others
              </Link>
            </li>
            <li>
              <Link to="/forum" className="nav-links" onClick={closeMoblieMenu}>
                forum
              </Link>
            </li>
          </ul>
          <div className="navbar-btn-container">
            {sessionStorage.getItem("token") === null ? (
              <>
                <button
                  className="navbar-btn"
                  onClick={() => {
                    navigate("/login");
                  }}
                >
                  로그인
                </button>
                <Link to="/join">
                  <button className="navbar-btn">회원가입</button>
                </Link>
              </>
            ) : (
              <UserBadge />
            )}
          </div>
          <div className="navbar-menu-icon" onClick={handleClick}>
            <i className={click ? "fas fa-times" : "fas fa-bars"} />
          </div>
        </div>
      </nav>
      <Outlet />
      {showChatBot && <ChatBot />}
      <ChatBotBtn setShowChatBot={setShowChatBot} showChatBot={showChatBot} />
    </div>
  );
};

function ChatBotBtn({ setShowChatBot, showChatBot }) {
  return (
    <div
      className="chatBotBtn__container"
      onClick={() => {
        setShowChatBot(!showChatBot);
      }}
    >
      <div className="chatBotBtn">
        <FontAwesomeIcon icon={faRobot} className="chatBotBtn__icon" />
      </div>
    </div>
  );
}

function UserBadge() {
  const [showUserModal, setShowUserModal] = useState(false);
  let navigate = useNavigate();
  let userRef = useRef();
  let modalRef = useRef();
  const handleUserModal = (e) => {
    if (e.target !== userRef.current) setShowUserModal(false);
  };
  window.addEventListener("click", handleUserModal);
  let reduxState = useSelector((state) => state);

  return (
    <>
      <Container>
        <ImgContainer>
          <img
            style={{ boxShadow: "var(--shadow)" }}
            ref={userRef}
            className="userImg"
            src={reduxState.profileImg ?? "/images/user.jpg"}
            alt="유저이미지"
            onError={(e) => {
              e.target.src = "/images/user.jpg";
            }}
            onClick={(e) => {
              setShowUserModal(!showUserModal);
            }}
          />
          <Badge>3</Badge>
        </ImgContainer>
        <div className="modal_container" ref={modalRef}>
          {showUserModal && (
            <Modal>
              <Content
                onClick={() => {
                  navigate("/myPage");
                }}
              >
                My Page
              </Content>
              <Content
                onClick={() => {
                  sessionStorage.clear();
                  navigate("/");
                }}
              >
                Log out
              </Content>
              <Content>
                <BadgeNotice>3</BadgeNotice>Notice
              </Content>
            </Modal>
          )}
        </div>
      </Container>
    </>
  );
}

const slide_bottom = keyframes`
0% {
  transform: translateY(-15px);
}
100% {
  transform: translateY(0px);
}
`;

const float = keyframes`

0%{
  transform: translateY(2px);
}
50%{
  transform : translateY(-3px);
}
100%{
  transform: translateY(2px);
}
`;

const wobble = keyframes`
0%{
  transform : scale(1)
}
50%{
  transform : scale(1.1)
}
100%{
  transform : scale(1)
}
`;

const Container = styled.div`
  position: relative;
  transition: 0.3s;
  cursor: pointer;
  &:hover {
  }
`;
const Modal = styled.div`
  position: absolute;
  width: 100px;
  height: auto;
  background: white;
  border-radius: 5px;
  box-shadow: var(--shadow);
  // z-index: 200;
  top: 37px;
  right: 0px;
  padding: 10px;
  transition: 0.3s;
  // transform: translateY(-15px);
  animation: ${slide_bottom} 0.5s cubic-bezier(0.25, 0.46, 0.45, 0.94) both;
`;
const Content = styled.div`
  position: relative;
  padding: 3px 0;
  width: 100%;
  height: auto;
  border-bottom: 1px solid rgba(0, 0, 0, 0.3);
  &:hover {
    color: var(--orange);
  }
`;
const ImgContainer = styled.div`
  position: relative;
  width: 35px;
  height: 35px;
  border-radius: 50%;
`;
const Badge = styled.span`
  text-align: center;
  position: absolute;
  width: 15px;
  height: 15px;
  font-size: 10px;
  border-radius: 50%;
  background: red;
  color: white;
  right: -7px;
  top: -5px;
  animation: ${float} 2s infinite;
`;

const BadgeNotice = styled.span`
  text-align: center;
  position: absolute;
  width: 15px;
  height: 15px;
  font-size: 10px;
  border-radius: 50%;
  background: red;
  color: white;
  right: 9px;
  top: 8px;
  animation: ${wobble} 1s ease-in-out infinite;
`;

export default Navbar;
