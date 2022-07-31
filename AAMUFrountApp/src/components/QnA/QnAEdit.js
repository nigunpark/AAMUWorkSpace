import axios from "axios";
import React, { useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import "./QnAWrite.css";
const QnAEdit = () => {
  let titleRef = useRef();
  let contentRef = useRef();
  let navigate = useNavigate();
  const editQnA = () => {
    console.log(contentRef.current.value);
    console.log(titleRef.current.value);
    let token = sessionStorage.getItem("token");
    axios
      .post(
        "",
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      )
      .then(() => {
        navigate(-1);
      });
  };
  async function getEdit() {
    let token = sessionStorage.getItem("token");
    await axios
      .get("", {
        params: {},
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        console.log(resp);
      })
      .catch((err) => console.log(err));
  }
  useEffect(() => {
    getEdit();
  }, []);

  return (
    <div className="qnaWrite__container">
      <div className="qna__header">
        <div className="qna__header__title">궁금한점을 알려주세요</div>
        <div className="qna__header__subTitle">AAMU는 여러분의 목소리를 듣고싶어요</div>
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
              style={{ resize: "none" }}
            ></textarea>
          </div>
        </div>
        <div className="qnaWrite__content-btn">
          <span className="qnaWrite__content-btnSpan" onClick={editQnA}>
            수정완료
          </span>
        </div>
      </div>
    </div>
  );
};

export default QnAEdit;
