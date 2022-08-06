import React, { useState, useEffect, useRef } from "react";
import ContentItem from "./ContentItem";
import "./Content.css";
import { Link } from "react-router-dom";
import axios from "axios";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";
import { useSelector } from "react-redux";
import DetailModal from "../DetailModal/DetailModal";
import { FormControl, InputLabel, MenuItem, Select } from "@mui/material";
const Content = () => {
  let reduxState = useSelector((state) => state);
  const [listData, setListData] = useState([]);
  const [isOpen, setIsOpen] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  let token = sessionStorage.getItem("token");
  const [showCBModal, setShowCBModal] = useState(false);
  const [detailOne, setDetailOne] = useState({});
  const [kindOfSearch, setKindOfSearch] = useState("");

  let searchOne = useRef();
  function chatbotModal() {
    if (reduxState.forChatBotData.bool === true) {
      setShowCBModal(true);
    }
  }

  const selectList = async () => {
    await axios
      .get("/aamurest/bbs/SelectList", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        console.log("글 목록 가져오기 Content.js) :", resp.data);
        setListData(resp.data);
      })
      .catch((error) => {
        console.log((error) =>
          console.log("글 목록 가져오기 실패(Content.js) :", error)
        );
      });
  };
  useEffect(() => {
    selectList();
    bookMarkList();
    chatbotModal();
  }, [isOpen]);

  const listSearch = (inSelectText) => {
    if (kindOfSearch == "" || inSelectText == "") {
      return alert("검색어와 주제를 선택해주세요.");
    }
    let token = sessionStorage.getItem("token");
    axios
      .get("", {
        params: {
          검색어key: inSelectText,
          선택한거key: kindOfSearch,
        },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        console.log("검색한 글 목록(Content.js) :", resp.data);
        setListData(resp.data);

        // setKindOfSearch([]);
        // searchOne.current.value = null;
      })
      .catch((error) => {
        console.log("검색 목록 가져오기 실패(Content.js) :", error);
      });
  };

  const [myBookMark, setMyBookMark] = useState([]);
  const bookMarkList = async () => {
    let token = sessionStorage.getItem("token");

    await axios
      .get("/aamurest/bbs/bookmark/list", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        console.log("북마크 목록 데이터 (Content.js) :", resp.data);
        setMyBookMark(resp.data);
      })
      .catch((error) => {
        console.log("북마크 목록 실패 (Content.js) :", error);
      });
  };

  function isBook(e) {
    if (e.rbn === detailOne.rbn) {
      return true;
    }
  }
  function isBookBool(e) {
    if (e.bookmark === false) {
      return true;
    }
  }
  const bookTF = myBookMark.find(isBook);
  const bookBool = myBookMark.find(isBookBool);

  let book;
  let bookbol;
  if (bookTF != undefined) {
    book = bookTF.rbn;
  }
  if (bookTF != undefined) {
    bookbol = bookBool.bookmark;
  }
  console.log("bookbol find:", bookbol);

  return (
    <div className="Cards_minCon">
      <div className="card__container_minCon">
        <div className="card__wrapper_minCon">
          <div className="card__top_minCon">
            <div>
              <h2 className="card__title_minCon">여행플랜 공유 게시판</h2>
              <p className="card__desc_minCon">
                자신만의 여행계획을
                <span className="myPage-link">
                  <Link to="/myPage"> 공유해보자</Link>
                </span>
              </p>
            </div>
          </div>
          <div className="search__container__minCon">
            <FormControl sx={{ m: 1, minWidth: 120 }} size="small">
              <InputLabel id="demo-select-small">검색</InputLabel>
              <Select
                labelId="demo-select-small"
                id="demo-select-small"
                value={kindOfSearch}
                label="검색"
                onChange={(e) => {
                  setKindOfSearch(e.target.value);
                }}
              >
                <MenuItem value="">
                  <em>없음</em>
                </MenuItem>
                <MenuItem value="id">아이디</MenuItem>
                <MenuItem value="theme">테마</MenuItem>
                <MenuItem value="title">제목</MenuItem>
              </Select>
            </FormControl>
            <div className="search__warpper__minCon">
              <input
                type="text"
                placeholder="검색어를 입력하세요"
                ref={searchOne}
                onKeyDown={(e) => {
                  let inSelectText = searchOne.current.value;
                  if (e.key === "Enter") listSearch(inSelectText);
                }}
              />
              <span>
                <FontAwesomeIcon
                  icon={faMagnifyingGlass}
                  className="search__i__minCon"
                  onClick={() => {
                    let inSelectText = searchOne.current.value;
                    listSearch(inSelectText);
                  }}
                />
              </span>
            </div>
          </div>
          <div className="card__items_minCon">
            {listData.map((val, idx) => {
              return (
                <ContentItem
                  setDetailOne={setDetailOne}
                  detail={val}
                  setIsOpen={setIsOpen}
                />
              );
            })}
          </div>
        </div>
      </div>
      {showCBModal && (
        <DetailModal
          setShowCBModal={setShowCBModal}
          detailRbn={reduxState.forChatBotData.rbn}
          setIsOpen={setIsOpen}
          setIsLoading={setIsLoading}
          bookbol={bookbol}
        />
      )}
      {isOpen && (
        <DetailModal
          detailOne={detailOne}
          setShowCBModal={setShowCBModal}
          setIsOpen={setIsOpen}
          setIsLoading={setIsLoading}
          bookbol={bookbol}
        />
      )}
    </div>
  );
};

export default Content;
