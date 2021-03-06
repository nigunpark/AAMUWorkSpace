import React, { useState, useEffect } from "react";
import ContentItem from "./ContentItem";
import "./Content.css";
import { Link, useNavigate } from "react-router-dom";
import FSearch from "../FSearch/FSearch";
import axios from "axios";

import dummy from "../DB/contentdata.json";

const Content = () => {
  //let navigate = useNavigate();

  const [listData, setListData] = useState([]);

  let token = sessionStorage.getItem("token");

  let [list, setList] = useState("");

  useEffect(() => {
    axios
      .get("/aamurest/bbs/SelectList", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        // setList(resp.data);
        console.log("글 목록이 왔는지 : ", resp.data);
        setListData(resp.data);
      })
      .catch((error) => {
        console.log((error) => console.log("글 목록 가져오기 실패", error));
      });
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
            <span className="content-btn">
              <Link to="/myPage">공유해봐</Link>
            </span>
          </p>
          <FSearch />

          <ul className="card__items_minCon">
            {listData.map((val, idx) => {
              return <ContentItem detail={val} index={idx} />;
            })}
          </ul>
        </div>
      </div>
    </div>
  );
};

export default Content;
