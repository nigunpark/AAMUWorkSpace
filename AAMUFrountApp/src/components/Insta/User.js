import React, { useEffect, useRef, useState } from "react";
import styled from "styled-components";
import NotificationModal from "./ModalGroup/Notification";
import SearchModal from "./ModalGroup/Search/SearchUser";
import WriteModal from "./ModalGroup/Upload/Upload";

import { confirmAlert } from "react-confirm-alert";
import axios from "axios";
import { Link } from "@mui/material";
import { useNavigate } from "react-router-dom";

function User({ setlist ,setloading}) {

  const modalRef = useRef();
  const notimodalRef = useRef();
  const outside = useRef();
  const SearchModalRef = useRef();
  const [heart, setHeart] = useState(false);
  const [follow, setFollowing] = useState(false);
  const [search, setsearch] = useState(false);
  const [square, setsquare] = useState(false);

  const [userModal, setUserModal] = useState(false);

  const [title, settitle] = useState([]);
  const [searchb, setSearchb] = useState([]);
  const [searchText, setsearchText] = useState(false);
  const [inputValue, setinputValue] = useState("");

  const [searchbar,setsearchbar] = useState([]);
  const [forReRender, setForReRender] = useState(false)
  let navigater = useNavigate()

  function searchBar(e){//백이랑 인스타 리스드를 뿌려주기 위한 axios
    
    let val = e.target.value;
    if (e.keyCode != 13) return;
    let token = sessionStorage.getItem("token");
    axios.get('/aamurest/gram/selectList',{
      headers: {
            Authorization: `Bearer ${token}`,
          },
          params:{
            searchColumn : searchb,
            searchWord :val,
          }
    })
    .then((resp) => {
      console.log(resp.data)
      setsearchbar(resp.data);
      navigater('/Insta/searchList')
      })
      .catch((error) => {
        console.log(error);
      });
  }

  function searchBarModal(e,modalRef){//백이랑 인스타 리스드를 뿌려주기 위한 axios
     let val = e.target.value;
    //  if (e.keyCode != 13) return;
    let token = sessionStorage.getItem("token");
    axios.get('/aamurest/gram/search/selectList',{
      headers: {
            Authorization: `Bearer ${token}`,
          },
          params:{
            searchColumn : searchb,
            searchWord :val,
          }
    })
    .then((resp) => {
      console.log(resp.data)
      settitle(resp.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }

  function handleModal(e) {
    e.stopPropagation();
    if (e.target !== modalRef.current) setsearch(false);
    if (e.target !== notimodalRef.current) setHeart(false);
    if (e.target !== SearchModalRef.current) setsearchText(false);
  }
  window.addEventListener("click", handleModal);

  const handleChange = (e) => {
    console.log(e.target.value);
  };

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
      <div className="userSearch">
        <select name="select" className="select" onChange={(e)=>{e.stopPropagation(); setSearchb(e.target.value);}}>
            <option defaultValue={'DEFAULT'}>선택</option>
            <option value="ctitle">제목</option>
            <option value="id">아이디</option>
            <option value="tag">태그</option>
        </select>
        <div
          className="search"
          onClick={() => {
            setsearch(!search);
          }}
        >
          <input 
            onKeyUp={(e)=>{e.stopPropagation();searchBarModal(e,modalRef);searchBar(e)}}
            value={inputValue}
            onChange={(e)=>{
              e.stopPropagation();
              setinputValue(e.target.value)
              setsearchText(true) 
            }}
            placeholder="검색" 
            type="text"
            className="search-bar"
            ref={modalRef}/>
            {searchText && 
            <SearchModal 
            ref={SearchModalRef}
            title={title} 
            setsearchText={setsearchText}
            setinputValue={setinputValue}
            />}
          
        </div>
      </div>
      <div className="user">
        <img src="/images/user.jpg" alt="프사" onError={(e)=>{e.stopPropagation(); e.target.src='/images/user.jpg'}}/>
        <div>
          <p className="user-id">0hyun0hyun</p>
          <p className="user-name">김영현</p>
        </div>

        <div
          className="notifi"
          onClick={() => {
            setHeart(!heart);
          }}
        >
          <div className="heart">
            {heart ? (
              <i
                className=" fa-solid fa-heart fa-2x"
                ref={notimodalRef}
                style={{ color: "black" }}
              >
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
        <div className="recommend-down">
          <div className="recommend-contents">
            <img src="./img/bk.jpg" alt="추사" />
            <div>
              <p className="user-id">psg</p>
              <p className="user-name">0hyun0hyun님 외 2명이...</p>
            </div>
            <div className="follow">
              {follow ? (
                <span
                  className="following"
                  onClick={() => {
                    setFollowing(!follow);
                  }}
                >
                  팔로잉
                </span>
              ) : (
                <span
                  onClick={() => {
                    setFollowing(!follow);
                  }}
                >
                  팔로우
                </span>
              )}
            </div>
          </div>
          <div className="recommend-contents">
            <img src="./img/bk.jpg" alt="추사" />
            <div>
              <p className="user-id">manchesterun</p>
              <p className="user-name">0hyun0hyun님 외 2명이...</p>
            </div>
            <div>
              <span className="follow">팔로우</span>
            </div>
          </div>
          <div className="recommend-contents">
            <img src="./img/bk.jpg" alt="추사" />
            <div>
              <p className="user-id">jeenny</p>
              <p className="user-name">0hyun0hyun님 외 2명이...</p>
            </div>
            <div>
              <span className="follow">팔로우</span>
            </div>
          </div>
          <div className="recommend-contents">
            <img src="./img/bk.jpg" alt="추사" />
            <div>
              <p className="user-id">0hyun0hyun</p>
              <p className="user-name">0hyun0hyun님 외 2명이...</p>
            </div>
            <div>
              <span className="follow">팔로우</span>
            </div>
          </div>
          <div className="recommend-contents">
            <img src="./img/bk.jpg" alt="추사" />
            <div>
              <p className="user-id">0hyun0hyun</p>
              <p className="user-name">0hyun0hyun님 외 2명이...</p>
            </div>
            <div>
              <span className="follow">팔로우</span>
            </div>
          </div>
        </div>
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

export default User;
