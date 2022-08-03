import React, { useCallback, useEffect, useRef, useState } from "react";
import "./MyPage.scss";
import MyPostBox from "./MyPageBox/MyPostBox";
import MyEditBox from "./MyPageBox/MyEditBox";
import MyHomeBox from "./MyPageBox/MyHomeBox";
import MyProfileBox from "./MyPageBox/MyProfileBox";
import MyInstaBox from "./MyPageBox/MyInstaBox";
import MyMessageBar from "./MyMessageBar/MyMessageBar";
import styled from "styled-components";
import {
  faBookmark,
  faImage,
  faMessage,
  faStar,
  faThumbsUp,
} from "@fortawesome/free-regular-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import axios from "axios";
import CommentSearch from "../../components/Insta/ModalGroup/Comment/CommentSearch";
import MyEdit from "../../components/Insta/ModalGroup/Edit/MyEdit";
const MyPage = () => {
  let [clickTab, setClickTab] = useState(0);

  let home = useRef();
  let two = useRef();
  let three = useRef();
  let setting = useRef();
  let homeBox = useRef();

  // /aamurest/planner/selectList
  // /aamurest/planner/selectOne

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
        setPlanList(resp.data);
        // console.log("데이터 확인 : ", resp.data);

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
        console.log("인스타 내 글 가져오기", resp.data);
        setMyInstar(resp.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }

  useEffect(() => {
    selectList();
    searchBar();
  }, [clickTab]);

  useEffect(() => {
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

          {/* <button
            ref={three}
            className="app-sidebar-link"
            onClick={() => {
              setClickTab(2);
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
              className="feather feather-calendar"
            >
              <FontAwesomeIcon icon={faStar} />
            </svg>
          </button> */}

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
            />
          </div>
        </div>

        <div className="messages-section">
          <div className="projects-section-header">
            <p>공지사항</p>
          </div>
          <div className="messages">
            <MyMessageBar />
          </div>
        </div>
      </div>
    </div>
  );
};

function Title({ clickTab }) {
  //타이틀
  if (clickTab === 0) {
    return <div className="projects-title">이런여행 어때 게시판 정보</div>;
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
}) {
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

  // console.log("TabContent selectRbn :", selectRbn);

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
                      <img className="instaImg" src={val.photo[0]} />
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

            {modalOpen == true ? (
              <MyBoxList
                setModalOpen={setModalOpen}
                setClickTab={setClickTab}
                seteditModal={seteditModal}
                setMyLno={setMyLno}
                myLno={myLno}
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

            {editModal && (
              <MyEdit
                val={val}
                setlist={setMyInstar}
                seteditModal={seteditModal}
                searchBar={searchBar}
              />
            )}
          </div>
        </div>
      </>
    );
  } else if (clickTab === 2) {
    //즐겨찾기
    return null;
  } else if (clickTab === 3) {
    //----------------------프로필------------------------
    return <MyProfileBox setClickTab={setClickTab} />;
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
  // console.log("uploadCount :", uploadCount[1] == undefined);
  useEffect(() => {
    myInstar.map((val) => {
      return setTotalLike((curr) => curr + parseInt(val.likecount));
    });
  }, [clickTab]);
  if (planList.length != 0) {
    if (uploadCount[1] != undefined) count = uploadCount[1].length;
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
        {/* <div className="item-status">
          <span className="status-number">{totalLike}</span>
          <span className="status-type">Like</span>
        </div> */}
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
  setClickTab,
  seteditModal,
  setMyLno,
  myLno,
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
        // setdeleteOnee(resp.data); //성공 여부가 온다 true false
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
