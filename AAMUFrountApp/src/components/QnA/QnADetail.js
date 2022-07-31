import axios from "axios";
import React, { useEffect } from "react";
import { Link } from "react-router-dom";

const QnADetail = () => {
  async function getDetail() {
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
      .catch((err) => {
        console.log(err);
      });
  }
  useEffect(() => {
    getDetail();
  }, []);
  return (
    <div className="qnaWrite__container">
      <div className="qna__header">
        <div className="qna__header__title">Q &amp; A 답변</div>
        <div className="qna__header__subTitle">궁금한 점이 해결되었으면 좋겠어요</div>
      </div>
      <div className="qnaWrite">
        <div className="qnaWrite__title">
          <h3>제목</h3>
          <div className="qnaWrite__title-input">
            <input type="text" readOnly />
          </div>
        </div>
        <div className="qnaWrite__content">
          <h3>질문주신 내용</h3>
          <div className="qnaWrite__content-input">
            <textarea style={{ resize: "none" }} readOnly></textarea>
          </div>
        </div>
        <hr style={{ marginTop: "20px" }} />
        <div className="qnaWrite__content">
          <h3>AAMU의 답변</h3>
          <div className="qnaWrite__content-input">
            <textarea style={{ resize: "none" }}></textarea>
          </div>
        </div>
        <div className="qnaAnswer__content-btns">
          <Link to="/qna" className="qnaWrite__content-btnSpan">
            목록으로
          </Link>
          <Link to="/qnaEdit" className="qnaWrite__content-btnSpan">
            수정하기
          </Link>

          <span className="qnaWrite__content-btnSpan">삭제하기</span>
        </div>
      </div>
    </div>
  );
};

export default QnADetail;
