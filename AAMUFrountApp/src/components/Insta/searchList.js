import axios from "axios";
import React, { useEffect, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import CommentSearch from "./ModalGroup/Comment/CommentSearch";

function SearchList({ searchb, inputValue }) {
  let commentRef = useRef();
  const [list, setList] = useState([]);
  const [commentModal, setcommentModal] = useState(false);
  let [comment, setComment] = useState("");
  const [comments, setcomments] = useState([]);

  function commentModal1(setcomments, lno) {
    console.log("searchb.eelno", lno);
    // const copyFeedComments = [...comments];//feedComments에 담겨있던 댓글 받아옴
    // copyFeedComments.push(comment);//copyFeedComments에 있는 기존 댓글에 push하기 위함
    // setcomments(copyFeedComments);//copyFeedComments 담겨있을 comment를 setfeedComments로 변경
    let token = sessionStorage.getItem("token");
    axios
      .get(`/aamurest/gram/SelectOne/${lno}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        console.log(resp.data)
        setcomments(resp.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }

  return (
    <MyInstaContainer>
      <Grid>
        <User>
          <img
            src={searchb[0] === undefined ? null : searchb[0].photo[0]}
            onError={(e) => {
              e.stopPropagation();
              e.target.src = "/images/user.jpg";
            }}
          />
          <div style={{ paddingLeft: "20px" }}>
            <p className="user-id">{inputValue}</p>
            <p className="user-name">
              게시물{" "}
              <strong style={{ color: "black" }}>{searchb.length}</strong>
            </p>
          </div>
        </User>
        <MyInstar>
          {searchb.map((val, idx) => {
            return (
              <>
                <InstarBox>
                  <InstaImg
                    onClick={(e) => {
                      commentModal1(setcomments, val.lno);
                      setList(val)
                      setcommentModal(!commentModal);
                    }}
                    src={val.photo[0]}
                  />
                  
                </InstarBox>
              </>
            );
          })}
          {commentModal && (
                    <CommentSearch
                      list={list}
                      commentModal={commentModal}
                      comment={comment}
                      setComment={setComment}
                      comments={comments}
                      setcomments={setcomments}
                      setcommentModal={setcommentModal}
                    />
                  )}
        </MyInstar>
      </Grid>
    </MyInstaContainer>
  );
}

const MyInstaContainer = styled.div`
  background-color: #f3f3f3;
  display: grid;
  grid-template-columns: auto 900px auto;
  gap: 5px;
  width: 100%;
  height: 100%;
  padding-top: 80px;
`;

const Grid = styled.div`
  display: grid;
  grid-column: 2/3;
  position: relative;
  gap: 5px;
  overflow: auto;
  padding: 5px;

  &::-webkit-scrollbar {
    display: none;
  }
  // border: 1px solid red;
`;

const User = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  margin-bottom: 10px;
  height: 180px;
  > img {
    width: 150px;
    height: 150px;
    border: 1px solid #e6e6e6;
    border-radius: 50%;
  }

  .user-id {
    font-size: 30px;
    font-weight: bold;
    margin-left: 10px;
  }

  .user-name {
    font-size: 15px;
    color: #8e8e8e;
    margin-top: 3px;
    margin-left: 10px;
  }
`;
const SearchProfile = styled.div`
  display: grid;
  position: absolute;
  gap: 5px;
  overflow: auto;
  padding: 5px;

  &::-webkit-scrollbar {
    display: none;
  }
  // border: 1px solid red;
`;
const MyInstar = styled.div`
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 15px;
  overflow: auto;
  padding: 5px;

  &::-webkit-scrollbar {
    display: none;
  }
  // border: 1px solid red;
`;
const InstarBox = styled.div`
  height: 225px;
  border-radius: 10px;
  cursor: pointer;
  box-shadow: 0 0 0 2px #e9ebec, 0 0 0 1px #fcfdfe;
  &:hover {
    transition: 0.3s;
    transform: scale(1.03);
  }
`;
const InstaImg = styled.img`
  width: 100%;
  height: 100%;
  border-radius: 10px;
  object-fit: cover;
`;

const Container1 = styled.div`
  position: fixed;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  z-index: 1;
  justify-content: center;
  align-items: center;
`;
const Overlay = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
  z-index: 15;
  background-color: rgba(0, 0, 0, 0.6);
`;

export default SearchList;
