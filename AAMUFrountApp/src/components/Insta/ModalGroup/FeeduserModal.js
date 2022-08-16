import { red } from "@mui/material/colors";
import axios from "axios";
import React, { useEffect, useRef, useState } from "react";
import styled from "styled-components";
import Spinner from "../Spinner";

const FeeduserModal = ({
  val,
  setModalShow,
  seteditModal,
  setlist
}) => {
  let menuRef = useRef();
  let editRef = useRef();

  function menuModalRef(e) {
    //메뉴 모달 나왔을때 주변 눌러도 꺼지게 만들기
    e.stopPropagation();
    if (e.target === menuRef.current) setModalShow(false);
  }

  window.addEventListener("click", menuModalRef);

  const feedList = async ( setlist, page, list) => {
    //백이랑 인스타 리스드를 뿌려주기 위한 axios
    let token = sessionStorage.getItem("token");
    const temp = await axios.get(`/aamurest/gram/selectList?page=${page}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
      params: {
        id: sessionStorage.getItem("username"),
      },
    });
    let removelist = list;
    for (let k = 0; k < temp.data.length; k += 1) {
      removelist = removelist.filter((item) => item.lno != temp.data[k].lno);
    }
    const tempComments = removelist.concat(temp.data);
    setlist([...tempComments]);
  };

  let [deleteOnee, setdeleteOnee] = useState(false);
  function deleteOne() {
    //업로드 버튼 누르고 화면 새로고침
    let token = sessionStorage.getItem("token");
    axios
      .delete(`/aamurest/gram/edit/${val.lno}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        console.log(resp.data);
        setdeleteOnee(resp.data); //성공 여부가 온다 true false
        // feedList( setlist, page, list);
        setlist((curr) => {
          return curr.filter((item) => {
            return item.lno != val.lno;
          });
        });
        alert("삭제가 완료되었습니다!");
      })
      .catch((error) => {});
  }

  return (
    <Container>
      <Overlay ref={menuRef}>
        {val.id === sessionStorage.getItem("username") ? (
          <ModalWrap>
            <Contents>
              <Button
                type="button"
                className="edit"
                ref={editRef}
                onClick={(e) => {
                  e.stopPropagation();
                  setModalShow(false);
                  seteditModal(true);
                }}
              >
                수정하기
              </Button>
              <Button
                type="button"
                className="delete"
                onClick={(e) => {
                  e.stopPropagation();
                  deleteOne();
                  setModalShow(false);
                }}
              >
                삭제하기
              </Button>
              <Button
                type="button"
                className="cancel"
                onClick={() => {
                  setModalShow(false);
                }}
              >
                취소하기
              </Button>
            </Contents>
          </ModalWrap>
        ) : (
          <ModalWrap>
            <Contents>
              <Button
                type="button"
                className="cancel"
                onClick={() => {
                  setModalShow(false);
                }}
              >
                취소하기
              </Button>
            </Contents>
          </ModalWrap>
        )}
      </Overlay>
    </Container>
  );
};

const Container = styled.div`
  cursor: default;
  position: fixed;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  z-index: 1;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const Overlay = styled.div`
  position: absolute;
  width: 100%;
  height: 100%;
  z-index: 11;
  background-color: rgba(0, 0, 0, 0.6);
`;

const ModalWrap = styled.div`
  width: 400px;
  overflow: hidden;
  height: fit-content;
  border-radius: 15px;
  background-color: #fff;
  position: absolute;
  top: 50%;
  left: 50%;
  z-index: 12;
  transform: translate(-50%, -50%);
  //border: solid 5px black;
`;

const Contents = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
  justify-content: center;
  align-items: center;
`;
const Button = styled.div`
  padding: 30px;
  width: 100%;
  height: 100%;
  cursor: pointer;
  text-align: center;
  font-size: medium;
  border-bottom: 0.5px solid rgb(220, 220, 220);
`;

export default FeeduserModal;
