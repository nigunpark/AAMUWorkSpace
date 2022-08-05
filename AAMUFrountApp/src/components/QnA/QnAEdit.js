import axios from "axios";
import React, { useEffect, useRef } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import "./QnAWrite.css";
const QnAEdit = () => {
  const location = useLocation();
  const editData = location.state.qnaDetail;
  console.log("QnA 수정할 데이터 : ", editData);
  let titleRef = useRef();
  let contentRef = useRef();
  let navigate = useNavigate();
  useEffect(() => {
    titleRef.current.value = editData.title;
    contentRef.current.value = editData.content;
  }, []);
  const editQnA = () => {
    console.log(contentRef.current.value);
    console.log(titleRef.current.value);
    let token = sessionStorage.getItem("token");
    axios
      .put(
        "/aamurest/qna/edit",
        {
          qno: editData.qno,
          title: titleRef.current.value,
          content: contentRef.current.value,
          id: sessionStorage.getItem("username"),
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      )
      .then(() => {
        alert("수정이 완료되었어요!");
        navigate(-1);
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
              style={{ width: "100%", height: "100%", resize: "none" }}
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
