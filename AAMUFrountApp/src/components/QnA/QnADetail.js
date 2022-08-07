import axios from "axios";
import React, { useEffect, useRef, useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";

const QnADetail = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [qnaDetail, setQnADetail] = useState([]);
  const titleRef = useRef();

  async function getDetail() {
    const qno = location.state.qno;
    let token = sessionStorage.getItem("token");
    await axios
      .get("/aamurest/qna/view", {
        params: { qno },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        // console.log("QnA 게시글 상세보기 :", resp.data);
        setQnADetail(resp.data);
      })
      .catch((err) => {
        console.log("QnA 게시글 상세보기 실패:", err);
      });
  }
  const fnDelete = () => {
    let token = sessionStorage.getItem("token");
    axios
      .delete("/aamurest/qna/delete", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        params: {
          qno: qnaDetail.qno,
        },
      })
      .then((resp) => {
        // console.log("QnA 게시글 삭제 성공 :", resp);
        alert("게시글이 삭제되었어요.");
        navigate("/qna");
      })
      .catch((err) => {
        console.log("QnA 게시글 삭제 실패 :", err);
      });
  };
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
            <input type="text" value={qnaDetail.title} readOnly />
          </div>
        </div>
        <div className="qnaWrite__content">
          <h3>질문주신 내용</h3>
          <div className="qnaWrite__content-input">
            <textarea
              style={{ width: "100%", height: "100%", resize: "none" }}
              value={qnaDetail.content}
              readOnly
            ></textarea>
          </div>
        </div>
        <hr style={{ marginTop: "20px" }} />
        <div className="qnaWrite__content">
          <h3>AAMU의 답변</h3>
          <div className="qnaWrite__content-input">
            <textarea readOnly style={{ width: "100%", height: "100%", resize: "none" }}></textarea>
          </div>
        </div>
        <div className="qnaAnswer__content-btns">
          <Link to="/qna" className="qnaWrite__content-btnSpan">
            목록으로
          </Link>

          {qnaDetail.id == sessionStorage.getItem("username") ? (
            <>
              <div
                className="qnaWrite__content-btnSpan"
                onClick={() => {
                  navigate("/qnaEdit", { state: { qnaDetail: qnaDetail } });
                }}
              >
                수정하기
              </div>
              <span className="qnaWrite__content-btnSpan" onClick={fnDelete}>
                삭제하기
              </span>
            </>
          ) : null}
        </div>
      </div>
    </div>
  );
};

export default QnADetail;
