import React, { useEffect, useState } from "react";
import "./QnA.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faAngleDown, faCircleQuestion } from "@fortawesome/free-solid-svg-icons";
import Pagination from "./Pagination";
import { Link } from "react-router-dom";
import { post } from "jquery";
import axios from "axios";
const QnA = () => {
  const [posts, setPosts] = useState([
    <QnABbsOne />,
    <QnABbsOne />,
    <QnABbsOne />,
    <QnABbsOne />,
    <QnABbsOne />,
    <QnABbsOne />,
    <QnABbsOne />,
    <QnABbsOne />,
  ]);
  const [loading, setLoading] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);
  const [postsPerPage, setPostsPerPage] = useState(5);
  const indexOfLast = currentPage * postsPerPage;
  const indexOfFirst = indexOfLast - postsPerPage;
  const getcurrentPosts = (posts) => {
    let currentPosts = 0;
    currentPosts = posts.slice(indexOfFirst, indexOfLast);
    return currentPosts;
  };
  function getQna() {
    let token = sessionStorage.getItem("token");
    axios
      .get("/aamurest/qna/list", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        console.log(resp.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }
  useEffect(() => {
    getQna();
  }, []);
  return (
    <div className="qna__container">
      <div className="qna__header">
        <div className="qna__header__title">AAMU Q &amp; A</div>
        <div className="qna__header__subTitle">AAMU에 궁금한점이 있으신가요?</div>
      </div>
      <div className="qna__body">
        <div className="qna__usual-questions">
          <div className="qna__usual-questions-title">
            <h3>&lt;&nbsp;자주 찾는 질문&nbsp;&gt;</h3>
          </div>
          <div>
            <QnAaccoridan />
            <QnAaccoridan />
            <QnAaccoridan />
          </div>
        </div>
        <div className="qna__qnaBoard">
          <h3>&lt;&nbsp;Q&amp;A 게시판&nbsp;&gt;</h3>
          <Link to="/qnaWrite">
            <div className="qna__Board-writeBtn">
              <span>글쓰기</span>
            </div>
          </Link>
          <div className="qna__qnaBoard-contents">
            {getcurrentPosts(posts).map((val, i) => {
              return val;
            })}
            {/* <QnABbsOne />
            <QnABbsOne />
            <QnABbsOne />
            <QnABbsOne />
            <QnABbsOne />
            <QnABbsOne />
            <QnABbsOne />
            <QnABbsOne /> */}
          </div>
          <div className="qna__pagination">
            <Pagination
              postsPerPage={postsPerPage}
              totalPosts={posts.length}
              setCurrentPage={setCurrentPage}
            />
          </div>
        </div>
      </div>
    </div>
  );
};
function QnABbsOne() {
  return (
    <div className="qna__one">
      <span style={{ textAlign: "center", height: "30px" }}>1</span>
      <span className="qna__one-title">
        질문드립니다.질문드립니다.질문드립니다.질문드립니다.질문드립니다.질문드립니다.질문드립니다.질문드립니다.
      </span>
      <span className="qna__one-id">KIM1111</span>
    </div>
  );
}

function QnAaccoridan() {
  const accordianItems = document.querySelectorAll(".value__accordion-item");
  accordianItems.forEach((item) => {
    const accordianHeader = item.querySelector(".value__accordion-header");
    accordianHeader.addEventListener("click", () => {
      const openItem = document.querySelector(".accordion-open");
      toggleItem(item);
      if (openItem && openItem !== item) {
        toggleItem(openItem);
      }
    });
  });
  const toggleItem = (item) => {
    const accordianContent = item.querySelector(".value__accordion-content");
    if (item.classList.contains("accordian-open")) {
      accordianContent.removeAttribute("style");
      item.classList.remove("accordian-open");
    } else {
      accordianContent.style.height = accordianContent.scrollHeight + "px";
      item.classList.add("accordian-open");
    }
  };
  return (
    <div className="value__accordion">
      <div className="value__accordion-item">
        <header className="value__accordion-header">
          <FontAwesomeIcon icon={faCircleQuestion} className="value__accordion-icon" />
          <h3 className="value__accordion-title">자주찾는 질문제목입니다</h3>
          <div className="value__accordion-arrow">
            <FontAwesomeIcon icon={faAngleDown} className="accordian-arrow-icon" />
          </div>
        </header>
        <div className="value__accordion-content">
          <p className="value__accordion-description">
            자주찾는 질문내용입니다.자주찾는 질문내용입니다.자주찾는 질문내용입니다.자주찾는
            질문내용입니다.
          </p>
        </div>
      </div>
    </div>
  );
}
export default QnA;
