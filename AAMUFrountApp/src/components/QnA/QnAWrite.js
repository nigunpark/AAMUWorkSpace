import axios from "axios";
import React, { useRef } from "react";
import { useNavigate } from "react-router-dom";
import "./QnAWrite.css";
const QnAWrite = () => {
  let titleRef = useRef();
  let contentRef = useRef();
  let navigate = useNavigate();
  const sendQnA = () => {
    console.log(titleRef.current.value);
    console.log(contentRef.current.value);
    let token = sessionStorage.getItem("token");
    axios
      .post(
        "/aamurest/qna/write",
        {
          id: sessionStorage.getItem("username"),
          title: titleRef.current.value,
          content: contentRef.current.value,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      )
      .then((resp) => {
        console.log("QnA 등록 :", resp.data);
        // if (resp.data === 1) navigate("/qna");
        alert("QnA 등록이 완료되었어요.");
        navigate("/qna");
      })
      .catch((err) => {
        console.log(err);
      });
  };
  return (
    <div className="qnaWrite__container">
      <div className="qna__header">
        <div className="qna__header__title">궁금한점을 알려주세요</div>
        <div className="qna__header__subTitle">
          AAMU는 여러분의 목소리를 듣고싶어요
        </div>
      </div>
      <div className="qnaWrite">
        <div className="qnaWrite__title">
          <h3>제목</h3>
          <div className="qnaWrite__title-input">
            <input type="text" placeholder="제목을 입력하세요" ref={titleRef} />
          </div>
        </div>
        <div className="qnaWrite__content">
          <h3>내용</h3>
          <div className="qnaWrite__content-input">
            <textarea
              ref={contentRef}
              placeholder="궁금한점을 알려주세요"
              style={{ width: "980px", height: "410px", resize: "none" }}
            ></textarea>
          </div>
        </div>
        <div className="qnaWrite__content-btn">
          <span className="qnaWrite__content-btnSpan" onClick={sendQnA}>
            게시하기
          </span>
        </div>
      </div>
    </div>
  );
};

export default QnAWrite;
