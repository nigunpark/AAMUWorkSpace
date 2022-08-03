import React, { useState, useEffect, useRef } from "react";
import ContentItem from "./ContentItem";
import "./Content.css";
import { Link, useNavigate } from "react-router-dom";
import FSearch from "../FSearch/FSearch";
import axios from "axios";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";
import dummy from "../DB/contentdata.json";
import { useSelector } from "react-redux";
import DetailModal from "../DetailModal/DetailModal";
import Spinner from "../../../components/Insta/Spinner";
import { FormControl, InputLabel, MenuItem, Select } from "@mui/material";
const Content = () => {
  let reduxState = useSelector((state) => state);
  //let navigate = useNavigate();
  const [listData, setListData] = useState([]);
  const [isOpen, setIsOpen] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  let token = sessionStorage.getItem("token");
  let [list, setList] = useState("");
  const [showCBModal, setShowCBModal] = useState(false);
  const [detailOne, setDetailOne] = useState({});
  const [kindOfSearch, setKindOfSearch] = useState("");
  let searchOne = useRef();
  function chatbotModal() {
    if (reduxState.forChatBotData.bool === true) {
      setShowCBModal(true);
    }
  }
  // console.log("detailOne", detailOne);
  // console.log("?forChatBotData?", reduxState.forChatBotData);
  useEffect(() => {
    axios
      .get("/aamurest/bbs/SelectList", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        // setList(resp.data);
        setListData(resp.data);
      })
      .catch((error) => {
        console.log((error) =>
          console.log("글 목록 가져오기 실패(Content.js) :", error)
        );
      });
    chatbotModal();
  }, [isOpen]);

  const listSearch = (inSelectText) => {
    if (kindOfSearch == "" || inSelectText == "") {
      return alert("검색어와 검색할 주제를 선택해주세요.");
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
        searchOne.current.value = null;
      })
      .catch((error) => {
        console.log((error) =>
          console.log("검색 목록 가져오기 실패(Content.js) :", error)
        );
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
            {/* <FSearch /> */}
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
              {/* {console.log("kindOfSearch :", kindOfSearch)} */}
            </FormControl>
            <div className="search__warpper__minCon">
              <input
                type="text"
                placeholder="검색어를 입력하세요"
                ref={searchOne}
              />
              <span>
                <FontAwesomeIcon
                  icon={faMagnifyingGlass}
                  className="search__i__minCon"
                  onClick={() => {
                    let inSelectText = searchOne.current.value;
                    console.log("searchOne :", inSelectText);
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
      {/* {isLoading && <Spinner />} */}
    </div>
  );
};

export default Content;
