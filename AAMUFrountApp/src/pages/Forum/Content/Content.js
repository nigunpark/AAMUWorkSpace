import React, { useState, useEffect } from "react";
import ContentItem from "./ContentItem";
import "./Content.css";
import { Link, useNavigate } from "react-router-dom";
import FSearch from "../FSearch/FSearch";
import axios from "axios";

import dummy from "../DB/contentdata.json";
import { useSelector } from "react-redux";
import DetailModal from "../DetailModal/DetailModal";
const Content = () => {
  let reduxState = useSelector((state) => state);
  //let navigate = useNavigate();
  const [listData, setListData] = useState([]);
  const [isOpen, setIsOpen] = useState(false);
  let token = sessionStorage.getItem("token");
  let [list, setList] = useState("");
  const [showCBModal, setShowCBModal] = useState(false);
  const [detailOne, setDetail] = useState({});
  function chatbotModal() {
    if (reduxState.forChatBotData.bool === true) {
      setShowCBModal(true);
    }
  }
  useEffect(() => {
    console.log("useEffect", reduxState.forChatBotData.length);
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
        console.log((error) => console.log("글 목록 가져오기 실패", error));
      });
    console.log("useEffect밑", reduxState.forChatBotData);
    chatbotModal();
  }, []);

  return (
    <div className="Cards_minCon">
      <div className="card__container_minCon">
        <div className="card__wrapper_minCon">
          <h1 className="card__title_minCon">
            당신도 이제는 여행 Creator<span>!</span>
          </h1>

          <p className="card__desc_minCon">
            자신만의 여행계획을 세우고 남들과
            <span className="myPage-link">
              <Link to="/myPage"> 공유해봐</Link>
            </span>
          </p>
          <FSearch />

          <ul className="card__items_minCon">
            {listData.map((val, idx) => {
              return (
                <ContentItem
                  // setDetail={setDetail}
                  detail={val}
                  index={idx}
                  setShowCBModal={setShowCBModal}
                  isOpen={isOpen}
                  setIsOpen={setIsOpen}
                />
              );
            })}
          </ul>
        </div>
      </div>
      {showCBModal && (
        <DetailModal
          setShowCBModal={setShowCBModal}
          detailRbn={reduxState.forChatBotData.rbn}
          setIsOpen={setIsOpen}
          // postDay={new Date(reduxState.forChatBotData.planner.routeDate)}
        />
      )}
      {/* {isOpen == true ? (
        <DetailModal
          // detailOne={detailOne}
          postDay={postDay}
          setShowCBModal={setShowCBModal}
          setIsOpen={setIsOpen}
          detailRbn={detailRbn}
        />
      ) : null} */}
    </div>
  );
};

export default Content;
