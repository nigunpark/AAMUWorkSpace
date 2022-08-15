import React, { useState, useEffect, useRef } from "react";
import ContentItem from "./ContentItem";
import "./Content.css";
import { useLocation } from "react-router-dom";
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

  let location = useLocation();
  let searchOne = useRef();
  function chatbotModal() {
    let dto;
    if (location.state !== null) {
      dto = location.state.dto;
      setDetailOne(dto);
      // setTimeout(setShowCBModal(true), 200);
      // setShowCBModal(true);
      setIsOpen(true);
    }
  }

  function sortData(data, rateavg, type) {
    if (type == undefined) {
      type = "asc";
    }
    return data.sort(function (a, b) {
      let x = a[rateavg];
      let y = b[rateavg];
      if (type == "desc") {
        return x > y ? -1 : x < y ? 1 : 0;
      } else if (type == "asc") {
        return x < y ? -1 : x > y ? 1 : 0;
      }
    });
  }

  // console.log("별점순으로 내림차순", sortData(listData, "rateavg", "desc"));

  const selectList = async () => {
    await axios
      .get("/aamurest/bbs/SelectList", {
        params: {
          id: sessionStorage.getItem("username"),
        },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        // console.log("글 목록 가져오기 Content.js) :", resp.data);

        setListData(resp.data);
      })
      .catch((error) => {
        // console.log((error) => console.log("글 목록 가져오기 실패(Content.js) :", error));
      });
  };

  useEffect(() => {
    selectList();
  }, [isOpen]);
  useEffect(() => {
    chatbotModal();
  }, []);

  const listSearch = (inSelectText) => {
    if (kindOfSearch == "" || inSelectText == "") {
      return alert("검색어와 주제를 선택해주세요.");
    }
    let token = sessionStorage.getItem("token");
    console.log("searchWord :", inSelectText);
    console.log("searchColumn :", kindOfSearch);
    axios
      .get("/aamurest/bbs/search/list", {
        params: {
          searchWord: inSelectText,
          searchColumn: kindOfSearch,
        },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        // console.log("검색한 글 목록(Content.js) :", resp.data);
        setListData(resp.data);

        setKindOfSearch([]);
        searchOne.current.value = null;
      })
      .catch((error) => {
        // console.log("검색 목록 가져오기 실패(Content.js) :", error);
      });
  };

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
                <MenuItem value="themename">테마</MenuItem>
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
          detailRbn={detailOne}
          setShowCBModal={setShowCBModal}
          setIsOpen={setIsOpen}
          setIsLoading={setIsLoading}
        />
      )}
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
};

export default Content;
