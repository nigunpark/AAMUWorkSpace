import React, { useState, useEffect, useRef } from "react";
import { Link, useNavigate, useLocation, NavLink } from "react-router-dom";
import "./Navbar.css";
import { Outlet } from "react-router-dom";
import styled, { keyframes } from "styled-components";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMessage, faRobot } from "@fortawesome/free-solid-svg-icons";
import ChatBot from "../ChatBot/ChatBot";
import axios from "axios";
let chatArr = [];
const Navbar = ({ scrollNav, whereUrl, noti, setNoti }) => {
  const [click, setClick] = useState(false);
  const handleClick = () => setClick(!click);
  const closeMoblieMenu = () => setClick(false);
  const [showChatBot, setShowChatBot] = useState(false);
  const activeStyle = {
    color: "var(--orange)",
    fontWeight: "bold",
  };
  let navigate = useNavigate();
  let location = useLocation();
  useEffect(() => {
    if (showChatBot === false) chatArr = [];
  }, [showChatBot]);
  return (
    <div className="navbar__fragment">
      <nav
        className={
          scrollNav ? "navbar scrollActive" : whereUrl ? "navbar whereUrlActive " : "navbar"
        }
      >
        <div className="navbar-container">
          <Link to="/">
            <div>
              <img className="logoImg" src="/images/aamuLogo.png" />
            </div>
            {/* </div> */}
          </Link>
          <ul className={click ? "nav-menu active" : "nav-menu"}>
            <li>
              <NavLink
                to="/WholeMap"
                className="nav-links"
                onClick={closeMoblieMenu}
                style={({ isActive }) => (isActive ? activeStyle : undefined)}
              >
                여행떠나기
              </NavLink>
            </li>
            <li>
              <NavLink
                to="/Insta"
                className="nav-links"
                onClick={closeMoblieMenu}
                style={({ isActive }) => (isActive ? activeStyle : undefined)}
              >
                이런곳은 어때
              </NavLink>
            </li>
            <li>
              <NavLink
                to="/forum"
                className="nav-links"
                onClick={closeMoblieMenu}
                style={({ isActive }) => (isActive ? activeStyle : undefined)}
              >
                이런여행 어때
              </NavLink>
            </li>
            <li>
              <NavLink
                to="/qna"
                className="nav-links"
                onClick={closeMoblieMenu}
                style={({ isActive }) => (isActive ? activeStyle : undefined)}
              >
                QnA
              </NavLink>
            </li>
            {sessionStorage.getItem("role") === "ROLE_ADMIN" && (
              <li>
                <a
                  href="http://192.168.0.22:9090/admin/"
                  className="nav-links"
                  onClick={closeMoblieMenu}
                  style={{ color: "red" }}
                  // style={({ isActive }) => (isActive ? activeStyle : undefined)}
                >
                  ADMIN
                </a>
              </li>
            )}
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
              <UserBadge noti={noti} setNoti={setNoti} />
            )}
          </div>
          <div className="navbar-menu-icon" onClick={handleClick}>
            <i className={click ? "fas fa-times" : "fas fa-bars"} />
          </div>
        </div>
      </nav>
      <Outlet />
      {location.pathname.indexOf("mainPage") !== 1 && location.pathname.indexOf("forum") !== 1 && (
        <ChatBotBtn setShowChatBot={setShowChatBot} showChatBot={showChatBot} />
      )}
    </div>
  );
};

function ChatBotBtn({ setShowChatBot, showChatBot }) {
  return (
    <div className="chatBotBtn__container">
      <div
        className="chatBotBtn__wrap"
        onClick={() => {
          setShowChatBot(!showChatBot);
        }}
      >
        <div className="chatBotBtn">
          <FontAwesomeIcon icon={faRobot} className="chatBotBtn__icon" />
        </div>
      </div>
      {showChatBot && <ChatBot showChatBot={showChatBot} chatArr={chatArr} />}
    </div>
  );
}

function UserBadge({ noti, setNoti }) {
  const [showUserModal, setShowUserModal] = useState(false);
  const [showNotiModal, setShowNotiModal] = useState(false);
  let navigate = useNavigate();
  let userRef = useRef();
  let modalRef = useRef();
  const handleUserModal = (e) => {
    if (e.target !== userRef.current) setShowUserModal(false);
  };
  window.addEventListener("click", handleUserModal);
  useEffect(() => {
    userRef.current.src = sessionStorage.getItem("userimg");
  }, [sessionStorage]);
  function getNotiNum() {
    if (
      noti.filter((val, i) => {
        return val.readknow === 0;
      }).length === 0
    )
      return null;
    if (
      noti.filter((val, i) => {
        return val.readknow === 0;
      }).length !== 0
    ) {
      return noti.filter((val, i) => {
        return val.readknow === 0;
      }).length;
    }
  }
  return (
    <>
      <Container>
        <ImgContainer>
          <img
            style={{ boxShadow: "var(--shadow)" }}
            ref={userRef}
            className="userImg"
            src={sessionStorage.getItem("userimg") ?? "/images/user.jpg"}
            alt="유저이미지"
            onError={(e) => {
              e.target.src = "/images/user.jpg";
            }}
            onClick={(e) => {
              setShowUserModal(!showUserModal);
              setShowNotiModal(false);
            }}
          />
          {noti.filter((val, i) => {
            return val.readknow === 0;
          }).length !== 0 && (
            <Badge>
              {
                noti.filter((val, i) => {
                  return val.readknow === 0;
                }).length
              }
            </Badge>
          )}
        </ImgContainer>
        <div className="modal_container" ref={modalRef}>
          {showUserModal && (
            <div style={{ position: "relative" }}>
              <Modal>
                <Content
                  onClick={(e) => {
                    e.stopPropagation();
                    setShowUserModal(false);
                    navigate("/myPage");
                  }}
                >
                  My Page
                </Content>
                <Content
                  onClick={(e) => {
                    e.stopPropagation();
                    setShowUserModal(false);
                    sessionStorage.clear();
                    navigate("/");
                  }}
                >
                  Log out
                </Content>
                <Content
                  onClick={(e) => {
                    e.stopPropagation();
                    setShowNotiModal(!showNotiModal);
                  }}
                >
                  {noti.filter((val, i) => {
                    return val.readknow === 0;
                  }).length !== 0 && (
                    <BadgeNotice>
                      {
                        noti.filter((val, i) => {
                          return val.readknow === 0;
                        }).length
                      }
                    </BadgeNotice>
                  )}
                  Notice
                </Content>
              </Modal>
              {showNotiModal && (
                <NoticeModal>
                  {noti.map((val) => {
                    return (
                      <div
                        className={val.readknow === 1 ? "noti_message read_noti" : "noti_message"}
                        onClick={(e) => {
                          e.stopPropagation();
                          val.readknow = 1;
                          readNoti(val);
                          setNoti((curr) => {
                            return curr.splice(
                              curr.findIndex((value) => {
                                return value.nano === val.nano;
                              }),
                              1,
                              val
                            );
                          });
                        }}
                      >
                        {val.amessage}
                      </div>
                    );
                  })}
                </NoticeModal>
              )}
            </div>
          )}
        </div>
      </Container>
    </>
  );
}
function readNoti(val) {
  let token = sessionStorage.getItem("token");
  axios
    .put(
      "/aamurest/notification/edit",
      {
        nano: val.nano,
      },
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )
    .then((resp) => {
      console.log(resp);
    })
    .catch((err) => {
      console.log(err);
    });
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

const slide_noti = keyframes`
0% {
  transform: translateX(30px);
  opacity: 0;
}
100% {
  transform: translateX(0);
  opacity: 1;
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
  top: 5px;
  right: 0px;
  padding: 10px;
  transition: 0.3s;
  // transform: translateY(-15px);
  animation: ${slide_bottom} 0.5s cubic-bezier(0.25, 0.46, 0.45, 0.94) both;
`;
const NoticeModal = styled.div`
  position: absolute;
  width: 300px;
  height: auto;
  background: white;
  border-radius: 5px;
  // box-shadow: var(--shadow);
  // z-index: 200;
  top: 75px;
  right: 103px;
  padding: 5px;
  transition: 0.3s;
  // transform: translateY(-15px);

  animation: ${slide_noti} 0.5s cubic-bezier(0.25, 0.46, 0.45, 0.94) both;
`;
const Content = styled.div`
  position: relative;
  padding: 3px 0;
  width: 100%;
  height: auto;
  border-bottom: 1px solid rgba(0, 0, 0, 0.3);
  font-weight: bold;
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
