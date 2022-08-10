import React, { useEffect, useRef, useState } from "react";
import "./MyPage.scss";
import MyPostBox from "./MyPageBox/MyPostBox";
import MyEditBox from "./MyPageBox/MyEditBox";
import MyHomeBox from "./MyPageBox/MyHomeBox";
import MyProfileBox from "./MyPageBox/MyProfileBox";
import MyMessageBar from "./MyMessageBar/MyMessageBar";
import { faBookmark, faMessage, faThumbsUp } from "@fortawesome/free-regular-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import axios from "axios";
import CommentSearch from "../../components/Insta/ModalGroup/Comment/CommentSearch";
import MyEdit from "../../components/Insta/ModalGroup/Edit/MyEdit";
import MyBookMarkBox from "./MyPageBox/MyBookMarkBox";
import DetailModal from "../Forum/DetailModal/DetailModal";
import MyThemeLists from "./MyPageBox/MyThemeLists";

import Spinner from "../../components/Insta/Spinner";
import { addForChatInfo } from "../../redux/store";
import { useDispatch } from "react-redux";
import Chat from "../../components/Chat/Chat";

const MyPage = () => {
  let [clickTab, setClickTab] = useState(0);
  let home = useRef();
  let two = useRef();
  let three = useRef();
  let setting = useRef();
  let homeBox = useRef();

  const [planList, setPlanList] = useState([]);
  // 업로드한 게시글 개수 카운트
  const [upload, setUpload] = useState({});

  async function selectList() {
    let token = sessionStorage.getItem("token");
    await axios
      .get("/aamurest/planner/selectList", {
        params: {
          id: sessionStorage.getItem("username"),
        },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        console.log("resp.data(selectList)", resp.data);
        setPlanList(resp.data);
        setIsLoading(false);

        setUpload(
          resp.data.reduce((acc, obj) => {
            const { isBBS } = obj;
            acc[isBBS] = acc[isBBS] ?? [];
            acc[isBBS].push(obj);
            return acc;
          }, {})
        );
      })
      .catch((error) => {
        console.log((error) => console.log("여행경로 가져오기 실패", error));
      });
  }
  const [myInstar, setMyInstar] = useState([]);
  const [myLno, setMyLno] = useState(0);
  let dispatch = useDispatch();
  async function searchBar() {
    let token = sessionStorage.getItem("token");
    let serchId = "id";
    await axios
      .get("/aamurest/gram/selectList", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        params: {
          searchColumn: serchId,
          searchWord: sessionStorage.getItem("username"),
        },
      })
      .then((resp) => {
        console.log("인스타 내 글 가져오기 (MyPage.js) :", resp.data);
        setMyInstar(resp.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }

  const [myBookList, setMyBookList] = useState([]);
  const [detailOne, setDetailOne] = useState({});
  const [isOpen, setIsOpen] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const [chatRooms, setChatRooms] = useState([]);
  const [followings, setFollowing] = useState({});
  const [prevChats, setPrevChats] = useState([]);
  const [showChat, setShowChat] = useState(false);
  const bookMarkList = async () => {
    let token = sessionStorage.getItem("token");

    await axios
      .get("/aamurest/bbs/bookmark/list", {
        params: {
          id: sessionStorage.getItem("username"),
        },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        console.log("북마크 목록 데이터 (DetailModal.js) :", resp.data);
        setMyBookList(resp.data);
      })
      .catch((error) => {
        console.log("북마크 목록 실패 (DetailModal.js) :", error);
      });
  };

  const getFollowing = () => {
    let token = sessionStorage.getItem("token");
    axios
      .get("/aamurest/gram/mypage/follower", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        params: {
          id: sessionStorage.getItem("username"),
        },
      })
      .then((resp) => {
        console.log("팔로잉", resp.data);
        setFollowing(resp.data);
      })
      .catch((err) => {
        console.log(err);
      });
  };
  async function getChatRoom(val, dispatch, setPrevChats) {
    await axios
      .post("/aamurest/chat/room?fromid=" + sessionStorage.getItem("username") + "&toid=" + val)
      .then((resp) => {
        console.log("resp", resp);
        setPrevChats([]);
        setTimeout(() => {
          setPrevChats(resp.data.list);
          dispatch(addForChatInfo({ ...resp.data, id: val }));
          setShowChat(!showChat);
        }, 100);
      })
      .catch((err) => {
        console.log(err);
      });
  }
  useEffect(() => {
    selectList();
    searchBar();
    bookMarkList();
    getChatRooms(setChatRooms);
    getFollowing();
    if (clickTab === 0) {
      home.current.classList.add("active");
      two.current.classList.remove("active");
      three.current.classList.remove("active");
      setting.current.classList.remove("active");

      homeBox.current.classList.add("jsListView");
      homeBox.current.classList.remove("jsGridView");
    } else if (clickTab === 1) {
      home.current.classList.remove("active");
      two.current.classList.add("active");
      three.current.classList.remove("active");
      setting.current.classList.remove("active");

      homeBox.current.classList.remove("jsListView");
      homeBox.current.classList.add("jsGridView");
    } else if (clickTab === 2) {
      home.current.classList.remove("active");
      two.current.classList.remove("active");
      three.current.classList.add("active");
      setting.current.classList.remove("active");

      homeBox.current.classList.remove("jsListView");
      homeBox.current.classList.add("jsGridView");
    } else if (clickTab === 3) {
      home.current.classList.remove("active");
      two.current.classList.remove("active");
      three.current.classList.remove("active");
      setting.current.classList.add("active");

      homeBox.current.classList.remove("jsListView");
      homeBox.current.classList.remove("jsGridView");
    }
  }, [clickTab]);

  const [val, setList] = useState([]);
  const [editModal, seteditModal] = useState(false);
  return (
    <div className="app-container">
      <div className="app-header"></div>
      {isLoading && <Spinner />}
      <div className="app-content">
        {/* <MySideBar/> 왼쪽 사이드바 */}
        <div className="app-sidebar">
          <button
            ref={home}
            className="app-sidebar-link "
            onClick={(e) => {
              e.stopPropagation();
              setClickTab(0);
            }}
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="24"
              height="24"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              strokeWidth="2"
              strokeLinecap="round"
              strokeLinejoin="round"
              className="feather feather-home"
            >
              <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z" />
              <polyline points="9 22 9 12 15 12 15 22app-content" />
            </svg>
          </button>

          <button
            ref={two}
            className="app-sidebar-link"
            onClick={(e) => {
              e.stopPropagation();
              setClickTab(1);
            }}
          >
            <i className="fa-brands fa-instagram fa-2x"></i>
          </button>

          <button
            ref={three}
            className="app-sidebar-link"
            onClick={(e) => {
              e.stopPropagation();
              setClickTab(2);
            }}
          >
            <FontAwesomeIcon
              icon={faBookmark}
              style={{ fontSize: "23px" }}
              className="detail__plan-bookMark"
            />
          </button>
          <button
            ref={setting}
            className="app-sidebar-link"
            onClick={(e) => {
              e.stopPropagation();
              setClickTab(3);
            }}
          >
            <svg
              className="link-icon feather feather-settings"
              xmlns="http://www.w3.org/2000/svg"
              width="24"
              height="24"
              fill="none"
              stroke="currentColor"
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              viewBox="0 0 24 24"
            >
              <defs />
              <circle cx="12" cy="12" r="3" />
              <path d="M19.4 15a1.65 1.65 0 00.33 1.82l.06.06a2 2 0 010 2.83 2 2 0 01-2.83 0l-.06-.06a1.65 1.65 0 00-1.82-.33 1.65 1.65 0 00-1 1.51V21a2 2 0 01-2 2 2 2 0 01-2-2v-.09A1.65 1.65 0 009 19.4a1.65 1.65 0 00-1.82.33l-.06.06a2 2 0 01-2.83 0 2 2 0 010-2.83l.06-.06a1.65 1.65 0 00.33-1.82 1.65 1.65 0 00-1.51-1H3a2 2 0 01-2-2 2 2 0 012-2h.09A1.65 1.65 0 004.6 9a1.65 1.65 0 00-.33-1.82l-.06-.06a2 2 0 010-2.83 2 2 0 012.83 0l.06.06a1.65 1.65 0 001.82.33H9a1.65 1.65 0 001-1.51V3a2 2 0 012-2 2 2 0 012 2v.09a1.65 1.65 0 001 1.51 1.65 1.65 0 001.82-.33l.06-.06a2 2 0 012.83 0 2 2 0 010 2.83l-.06.06a1.65 1.65 0 00-.33 1.82V9a1.65 1.65 0 001.51 1H21a2 2 0 012 2 2 2 0 01-2 2h-.09a1.65 1.65 0 00-1.51 1z" />
            </svg>
          </button>
        </div>

        <div className="projects-section">
          <div className="projects-section-header">
            <Title clickTab={clickTab} />
          </div>
          <div className="projects-section-line">
            {/* <MyHomeTopLine/> */}
            <TabTopLine
              clickTab={clickTab}
              planList={planList}
              uploadCount={upload}
              myInstar={myInstar}
            />
          </div>
          <div ref={homeBox} className="project-boxes">
            <TabContent
              clickTab={clickTab}
              setClickTab={setClickTab}
              planList={planList}
              setPlanList={setPlanList}
              setUpload={setUpload}
              myInstar={myInstar}
              setMyLno={setMyLno}
              myLno={myLno}
              setMyInstar={setMyInstar}
              val={val}
              setList={setList}
              editModal={editModal}
              seteditModal={seteditModal}
              searchBar={searchBar}
              myBookList={myBookList}
              detailOne={detailOne}
              setDetailOne={setDetailOne}
              isOpen={isOpen}
              setIsOpen={setIsOpen}
              setIsLoading={setIsLoading}
            />
          </div>
          <div style={{ position: "absolute", right: "285px", top: "-90px" }}>
            {showChat && (
              <Chat showChat={showChat} prevChats={prevChats} setPrevChats={setPrevChats} />
            )}
          </div>
        </div>

        <div className="messages-section">
          {/* <div style={{ border: "1px solid grey", borderRadius: "5px" }}>
            <div className="projects-section-header">
              <p>공지사항</p>
            </div>
            <div className="messages">
              <MyMessageBar />
            </div>
          </div> */}
          {/* <div style={{ border: "1px solid grey", borderRadius: "5px" }}> */}
          <div className="projects-section-header">
            <p>팔로잉/팔로워</p>
          </div>
          <div className="myPage__follow__container">
            <div
              style={{
                display: "flex",
                flexDirection: "column",
                gap: "5px",
              }}
            >
              <div style={{ fontWeight: "bold", fontSize: "17px", textAlign: "center" }}>
                팔로잉
              </div>
              <div className="myPage__following">
                {followings.following !== undefined &&
                  followings.following.map((val, i) => {
                    return (
                      <div
                        className="myPage__following_one"
                        onClick={() => {
                          getChatRoom(val.id, dispatch, setPrevChats);
                          setTimeout(() => {}, 100);
                        }}
                      >
                        <div className="myPage__following__img-container">
                          <img src={val.userprofile} />
                        </div>
                        <span>{val.id}</span>
                      </div>
                    );
                  })}
              </div>
            </div>
            <div style={{ display: "flex", flexDirection: "column", gap: "5px" }}>
              <div style={{ fontWeight: "bold", fontSize: "17px", textAlign: "center" }}>
                팔로워
              </div>
              <div className="myPage__follower">
                {followings.follower !== undefined &&
                  followings.follower.map((val, i) => {
                    return (
                      <div className="myPage__follower_one">
                        <div className="myPage__following__img-container">
                          <img
                            src={val.userprofile ?? "/images/no-image.jpg"}
                            onError={(e) => {
                              e.target.src = "/images/no-image.jpg";
                            }}
                          />
                        </div>
                        <span>{val.id}</span>
                      </div>
                    );
                  })}
              </div>
            </div>
          </div>
          {/* </div> */}
          <div className="projects-section-header">
            <p>채팅방 목록</p>
          </div>
          <div className="chatRooms__container">
            {chatRooms.map((val, i) => {
              return (
                <div
                  className="chatRooms"
                  onClick={(e) => {
                    e.stopPropagation();
                    getChatRoom(
                      sessionStorage.getItem("username") === val.fromid ? val.toid : val.fromid,
                      dispatch,
                      setPrevChats
                    );
                    setTimeout(() => {
                      setShowChat(!showChat);
                    }, 100);
                  }}
                >
                  <div className="chatRooms__profile-top">
                    <div style={{ display: "flex", alignItems: "center", gap: "8px" }}>
                      <div className="chatRooms__profile-img-container">
                        <img
                          src={
                            sessionStorage.getItem("username") === val.fromid
                              ? val.topro
                              : val.frompro
                          }
                          className="chatRooms__profile-img"
                        />
                      </div>
                      <span style={{ fontSize: "13px", fontWeight: "bold" }}>
                        {sessionStorage.getItem("username") === val.fromid ? val.toid : val.fromid}
                      </span>
                    </div>
                    <span style={{ fontSize: "12px", color: "grey" }}>
                      {displayedAt(val.senddate)}
                    </span>
                  </div>
                  <div className="chatRooms__profile-bottom">
                    <p style={{ fontSize: "14px" }}>{val.lastmessage}</p>
                  </div>
                </div>
              );
            })}
          </div>
        </div>
      </div>
    </div>
  );
};

function displayedAt(createdAt) {
  const milliSeconds = new Date() - createdAt;
  const seconds = milliSeconds / 1000;
  if (seconds < 60) return `방금 전`;
  const minutes = seconds / 60;
  if (minutes < 60) return `${Math.floor(minutes)}분 전`;
  const hours = minutes / 60;
  if (hours < 24) return `${Math.floor(hours)}시간 전`;
  const days = hours / 24;
  if (days < 7) return `${Math.floor(days)}일 전`;
  const weeks = days / 7;
  if (weeks < 5) return `${Math.floor(weeks)}주 전`;
  const months = days / 30;
  if (months < 12) return `${Math.floor(months)}개월 전`;
  const years = days / 365;
  return `${Math.floor(years)}년 전`;
}
function getChatRooms(setChatRooms) {
  let token = sessionStorage.getItem("token");
  axios
    .get("/aamurest/chat/rooms", {
      params: {
        id: sessionStorage.getItem("username"),
      },
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
    .then((resp) => {
      console.log("채팅방목록", resp.data);
      setChatRooms(resp.data);
    })
    .catch((err) => {
      console.log(err);
    });
}

function Title({ clickTab }) {
  const [theme, setTheme] = useState();

  const getMyThemes = async () => {
    let token = sessionStorage.getItem("token");

    await axios
      .get("/aamurest/user/theme", {
        params: {
          id: sessionStorage.getItem("username"),
        },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        console.log("나의 테마 : ", resp.data);
        setTheme(resp.data);
      })
      .catch((error) => {
        console.log("나의 테마 가져오기 실패", error);
      });
  };

  //타이틀
  if (clickTab === 0) {
    return (
      <>
        <div className="projects-title">이런여행 어때 게시판 정보</div>
        {/* <div style={{ display: "flex", flexDirection: "row" }}>
          {theme === undefined ? null : <MyThemeLists theme={theme} />}
        </div> */}
      </>
    );
  } else if (clickTab === 1) {
    return <div className="projects-title">이런곳은 어때 게시판 정보</div>;
  } else if (clickTab === 2) {
    return <div className="projects-title">즐겨찾기</div>;
  } else if (clickTab === 3) {
    return <div className="projects-title">프로필 수정</div>;
  }
}

//-----------------------------------------------------------------------
function TabContent({
  clickTab,
  setClickTab,
  planList,
  setPlanList,
  setUpload,
  myInstar,
  setMyLno,
  myLno,
  setMyInstar,
  val,
  setList,
  editModal,
  seteditModal,
  searchBar,
  myBookList,
  detailOne,
  setDetailOne,
  isOpen,
  setIsOpen,
  setIsLoading,
}) {
  const [showCBModal, setShowCBModal] = useState(false);

  const [selectRbn, setSelectRbn] = useState();
  const [modalOpen, setModalOpen] = useState(false);

  const [commentModal, setcommentModal] = useState(false);
  let [comment, setComment] = useState("");
  const [comments, setcomments] = useState([]);

  function commentModal1(setcomments, lno) {
    let token = sessionStorage.getItem("token");
    axios
      .get("/aamurest/gram/SelectOne", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        params: {
          id: sessionStorage.getItem("username"),
          lno: lno,
        },
      })
      .then((resp) => {
        console.log("여긴가요:", resp.data);
        setcomments(resp.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }

  if (clickTab === 0) {
    // 홈
    return (
      <div className="myInstaContainer">
        <div className="myInstar">
          {planList.map((val, idx) => {
            return (
              <MyHomeBox
                setClickTab={setClickTab}
                planList={val}
                rbn={val.rbn}
                setSelectRbn={setSelectRbn}
                setPlanList={setPlanList}
                setUpload={setUpload}
                setIsLoading={setIsLoading}
              />
            );
          })}
        </div>
      </div>
    );
  } else if (clickTab === 1) {
    //인스타

    return (
      <>
        <div className="myInstaContainer">
          <div className="myInstar">
            {myInstar.map((val, idx) => {
              return (
                <>
                  <div className="instarBox__container">
                    <div
                      className="instarBox"
                      onClick={(e) => {
                        e.stopPropagation();
                        setModalOpen(true);
                        setMyLno(val.lno);
                        setList(val);
                      }}
                    >
                      <img alt="" className="instaImg" src={val.photo[0]} />
                      <div>
                        <div className="instaTitle__container">
                          <input type="text" value={val.ctitle} className="instaTitle" />
                        </div>
                        <div className="insta__info">
                          <div>
                            <FontAwesomeIcon icon={faMessage} className="insta__info-icon" />{" "}
                            <span className="insta__info-content">{val.rcount}</span>
                          </div>
                          <div>
                            <FontAwesomeIcon icon={faThumbsUp} className="insta__info-icon" />
                            <span className="insta__info-content">{val.likecount}</span>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </>
              );
            })}

            {modalOpen === true ? (
              <MyBoxList
                setModalOpen={setModalOpen}
                seteditModal={seteditModal}
                setMyLno={setMyLno}
                val={val}
                setList={setList}
                setcomments={setcomments}
                commentModal1={commentModal1}
                setcommentModal={setcommentModal}
                searchBar={searchBar}
              />
            ) : null}

            {commentModal && (
              <CommentSearch
                val={val}
                commentModal={commentModal}
                comment={comment}
                setComment={setComment}
                comments={comments}
                setcomments={setcomments}
                setcommentModal={setcommentModal}
                commentModal1={commentModal1}
              />
            )}

            {editModal && <MyEdit val={val} seteditModal={seteditModal} searchBar={searchBar} />}
          </div>
        </div>
      </>
    );
  } else if (clickTab === 2) {
    //----------------------북마크------------------------
    return (
      <div className="myInstaContainer">
        <div className="myInstar">
          {myBookList.map((val, idx) => {
            return <MyBookMarkBox setDetailOne={setDetailOne} detail={val} setIsOpen={setIsOpen} />;
          })}
        </div>

        {isOpen && (
          <DetailModal
            detailOne={detailOne}
            setShowCBModal={setShowCBModal}
            setIsOpen={setIsOpen}
            setIsLoading={setIsLoading}
          />
        )}
      </div>
    );
  } else if (clickTab === 3) {
    //----------------------프로필------------------------
    return <MyProfileBox />;
  } else if (clickTab === 10) {
    //-----------------------글작성------------------------
    return <MyPostBox selectRbn={selectRbn} setClickTab={setClickTab} />;
  } else if (clickTab === 11) {
    //------------글수정-------------
    return <MyEditBox selectRbn={selectRbn} />;
  }
}

function TabTopLine({ clickTab, planList, uploadCount, myInstar }) {
  const [totalLike, setTotalLike] = useState(0);
  let count = 0;

  useEffect(() => {
    myInstar.map((val) => {
      return setTotalLike((curr) => curr + parseInt(val.likecount));
    });
  }, [clickTab]);
  if (planList.length !== 0) {
    if (uploadCount[1] !== undefined) count = uploadCount[1].length;
  }

  //서브 타이틀
  if (clickTab === 0) {
    return (
      <div className="projects-status">
        <div className="item-status">
          <span className="status-number">{planList.length}</span>
          <span className="status-type">Total</span>
        </div>

        <div className="item-status">
          <span className="status-number">{count}</span>
          <span className="status-type">Upload</span>
        </div>
      </div>
    );
  } else if (clickTab === 1) {
    return (
      <div className="projects-status">
        <div className="item-status">
          <span className="status-number">{myInstar.length}</span>
          <span className="status-type">Total</span>
        </div>
      </div>
    );
  } else if (clickTab === 2) {
    return (
      <div className="projects-status">
        <div className="item-status">
          <span className="status-number">0</span>
          <span className="status-type">Total</span>
        </div>
      </div>
    );
  } else if (clickTab === 3) {
    // return <MyProfileTopLine />;
  } else if (clickTab === 10) {
    return <div></div>;
  }
}

function MyBoxList({
  setModalOpen,
  seteditModal,
  setMyLno,
  val,
  setList,
  setcomments,
  commentModal1,
  setcommentModal,
  searchBar,
}) {
  function deleteOne() {
    let token = sessionStorage.getItem("token");
    axios
      .delete(`/aamurest/gram/edit/${val.lno}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        alert("삭제되었습니다!");
        // feedList(setlist);
        searchBar();
      })
      .catch((error) => {
        console.log(error);
      });
  }

  return (
    <>
      <div className="myBox-List-two">
        <div className="myBox-List-overlay">
          <div
            className="myBox-List-Plan"
            onClick={(e) => {
              e.stopPropagation();
              commentModal1(setcomments, val.lno);
              setList(val);
              setcommentModal(true);
              setModalOpen(false);
            }}
          >
            내 글 보기
          </div>
          <div
            className="myBox-List-Post"
            onClick={(e) => {
              e.stopPropagation();
              seteditModal(true);
              setModalOpen(false);
            }}
          >
            수정 하기
          </div>
          <div
            className="myBox-List-Delete"
            onClick={(e) => {
              e.stopPropagation();
              deleteOne();
              setModalOpen(false);
            }}
          >
            삭제하기
          </div>
          <div
            className="myBox-List-back"
            onClick={(e) => {
              setModalOpen(false);
              setMyLno(0);
            }}
          >
            취소
          </div>
        </div>
      </div>
    </>
  );
}

export default MyPage;
