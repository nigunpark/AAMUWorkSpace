import React, { useEffect, useRef, useState } from "react";
import styled from "styled-components";
import NotificationModal from "./ModalGroup/Notification";
import SearchModal from "./ModalGroup/Search/SearchUser";
import WriteModal from "./ModalGroup/Upload/Upload";
import $ from "jquery";
import { confirmAlert } from "react-confirm-alert";
import axios from "axios";
import { Link } from "@mui/material";
import { useNavigate } from "react-router-dom";
import SearchSelect from "./ModalGroup/Search/SearchSelect";
import Chat from "../Chat/Chat";
import { Tag } from "./Tag";
function User({
  list,
  setlist,
  setloading,
  searchb,
  setSearchb,
  inputValue,
  setinputValue,
  showChat,
  recommendUser,
  prevChats,
  setpage,
  page,
  setShowChat,
}) {
  const modalRef = useRef();
  const notimodalRef = useRef();
  const outside = useRef();
  const SearchModalRef = useRef();
  const [heart, setHeart] = useState(false);
  const [ffollower, setfollower] = useState([]);
  const [search, setsearch] = useState(false);
  const [square, setsquare] = useState(false);
  const [usefollow, setusefollow] = useState(false);
  const [tag, settag] = useState(false);
  const [tagsearch, settagsearch] = useState("");
  const [title, settitle] = useState([]);
  const [tagpop, settagpop] = useState([]);

  const [searchText, setsearchText] = useState(false);
  let navigater = useNavigate();

  function searchBar(e) {
    //백이랑 인스타 리스드를 뿌려주기 위한 axios
    setinputValue(e.target.value);
    if (e.keyCode != 13) return;
    let token = sessionStorage.getItem("token");
    axios
      .get("/aamurest/gram/selectList", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        params: {
          searchColumn: tagsearch,
          searchWord: inputValue,
        },
      })
      .then((resp) => {
        setSearchb(resp.data);

        navigater("/Insta/searchList");
      })
      .catch((error) => {
      });
  }

  function follower(id) {
    //유효성 검사를 통과하고 게시버튼 클릭시 발생하는 함수
    let token = sessionStorage.getItem("token");
    axios
      .post(
        "/aamurest/gram/follower",
        {
          follower: id,
          id: sessionStorage.getItem("username"),
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      )
      .then((resp) => {
        setusefollow(resp.data);
      })
      .catch((error) => {
      });
  }

  function searchBarModal() {
    //백이랑 인스타 리스드를 뿌려주기 위한 axios
    let token = sessionStorage.getItem("token");
    axios
      .get("/aamurest/gram/search/selectList", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        params: {
          searchColumn: tagsearch,
          searchWord: inputValue,
        },
      })
      .then((resp) => {
        settitle(resp.data);
      })
      .catch((error) => {
      });
  }

  useEffect(() => {}, [tag]); 

  function popTag() {
    //백이랑 인스타 리스드를 뿌려주기 위한 axios
    let token = sessionStorage.getItem("token");
    axios
      .get("/aamurest/gram/word", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        settagpop(resp.data);
      })
      .catch((error) => {
      });
  }

  function handleModal(e) {
    e.stopPropagation();
    if (e.target !== modalRef.current) setsearch(false);
    if (e.target !== notimodalRef.current) setHeart(false);
    if (e.target !== SearchModalRef.current) setsearchText(false);
  }
  window.addEventListener("click", handleModal);

  const submit = () => {
    confirmAlert({
      title: "새 글 작성을 삭제하시겠습니까?",
      message: "지금 나가면 내용이 저장되지 않습니다.",
      buttons: [
        {
          label: "삭제",
          onClick: () => {
            setsquare(false);
          },
        },
        {
          label: "취소",
        },
      ],
    });
  };
  return (
    <div>
      {showChat && <Chat showChat={showChat} setShowChat={setShowChat} prevChats={prevChats} />}
      <div className="userSearch">
        <SearchSelect settagsearch={settagsearch} />
        <div
          className="search"
          onClick={() => {
            setsearch(!search);
          }}
        >
          <input
            onKeyUp={(e) => {
              searchBarModal(e, modalRef);
              searchBar(e);
            }}
            value={inputValue}
            onChange={(e) => {
              setinputValue(e.target.value);
              setsearchText(true);
            }}
            placeholder="검색"
            type="text"
            className="search-bar"
            ref={modalRef}
          />
          {searchText && (
            <SearchModal
              ref={SearchModalRef}
              title={title}
              setsearchText={setsearchText}
              setinputValue={setinputValue}
            />
          )}
        </div>
      </div>
      <BUTTON
        onClick={() => {
          popTag();
          settag(!tag);
          // $(".select").val("tname");
        }}
      >
        실시간 인기 태그
      </BUTTON>
      {tag && (
        <Tag
          tagpop={tagpop}
          setinputValue={setinputValue}
          inputValue={inputValue}
          setSearchb={setSearchb}
        />
      )}
      <div className="user">
        <div className="userps">
          <img
            src={sessionStorage.getItem("userimg")}
            alt="프사"
            onError={(e) => {
              e.stopPropagation();
              e.target.src = "/images/user.jpg";
            }}
          />
        </div>
        <div className="userpsid">
          <p className="user-id">{sessionStorage.getItem("username")}</p>
        </div>

        <div
          className="notifi"
          onClick={() => {
            setHeart(!heart);
          }}
        >
          <div className="heart">
            {heart ? (
              <i className=" fa-solid fa-heart fa-2x" ref={notimodalRef} style={{ color: "black" }}>
                <NotificationModal></NotificationModal>
              </i>
            ) : (
              <i className="fa-regular fa-heart fa-2x"></i>
            )}
          </div>
        </div>
        {/* <div className="post-icon">
        </div>  */}
        {square ? (
          <>
            <div className="post-icon">
              <i
                className=" fa-solid fa-square-plus fa-2x"
                onClick={() => {
                  setsquare(!square);
                }}
                style={{ color: "black" }}
              ></i>
            </div>
            <Container>
              <Overlay
                ref={outside}
                onClick={() => {
                  submit();
                }}
              />
              <WriteModal
                onClick={() => setsquare(false)}
                list={list}
                setpage={setpage}
                page={page}
                setsquare={setsquare}
                setlist={setlist}
                setloading={setloading}
              />
            </Container>
          </>
        ) : (
          <div className="post-icon">
            <i
              className=" fa-regular fa-square-plus fa-2x"
              onClick={() => {
                setsquare(!square);
              }}
            ></i>
          </div>
        )}
      </div>

      <div className="recommend">
        <div className="recommend-title">
          <span>회원님을 위한 추천</span>
          <span>모두 보기</span>
        </div>
        {recommendUser.slice(0, 5).map((val, i) => {
          return (
            <div className="recommend-down" key={i}>
              <div className="recommend-contents">
                <img
                  src={val.userprofile}
                  alt="추사"
                  onError={(e) => {
                    e.stopPropagation();
                    e.target.src = "/images/user.jpg";
                  }}
                />
                <div className="user-id">
                  <p>{val.id}</p>
                </div>
                <div
                  className="follow"
                  onClick={(e) => {
                    e.stopPropagation();
                    if (
                      //팔로워 배열안에 들어가 있는 팔로워들
                      ffollower.includes(e.target.parentElement.previousElementSibling.textContent)
                    ) {
                      setfollower(
                        ffollower.filter((folval) => {
                          //언팔로우 하기 위한 로직
                          return (
                            e.target.parentElement.previousElementSibling.textContent !== folval
                          );
                        })
                      );
                    } else {
                      //팔로우하기 위한 팔로워들
                      setfollower([
                        ...ffollower, //기존에 있는 팔로워들 +
                        e.target.parentElement.previousElementSibling.textContent, //클릭이 생기는 팔로워들
                      ]);
                    }
                    follower(val.id);
                  }}
                >
                  {ffollower.includes(val.id) ? ( //ffollower 배열에 val.id가 포함되어 있다면
                    <span className="following">팔로잉</span>
                  ) : (
                    <span>팔로우</span>
                  )}
                </div>
              </div>
            </div>
          );
        })}
      </div>
      <div className="information">
        <p> &nbsp;&nbsp;About ・ Help ・ Press ・ API ・ Jobs ・ Privacy </p>
        <p> &nbsp;・ Terms ・ Locations ・ Language</p>
      </div>
      <div className="information2">
        <p>ⓒ 2022 INSTAGRAM</p>
      </div>
    </div>
  );
}

const Container = styled.div`
  position: fixed;
  width: 100%;
  height: 100%;

  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  justify-content: center;
  align-items: center;
`;
const Overlay = styled.div`
  position: absolute;
  width: 100%;
  height: 100%;
  z-index: 10;
  background-color: rgba(0, 0, 0, 0.6);
`;

const BUTTON = styled.div`
  position: relative;
  width: 100%;
  top: 10px;
  background-color: white;
  cursor: pointer;
  border-radius: 5px;
  color: orange;
  text-align: center;
  height: 30px;
  margin-bottom: 5px;
`;

export default User;
