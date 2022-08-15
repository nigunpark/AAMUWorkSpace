import React, { useState, useRef, useEffect } from "react";
import { Button } from "@mui/material";
import axios from "axios";
import styled from "styled-components";
import $, { escapeSelector } from "jquery";
import SearchModal from "../Upload/SearchModal";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPaperPlane } from "@fortawesome/free-regular-svg-icons";
import { Link, useNavigate } from "react-router-dom";
import Slider from "react-slick";
import "../Slider/slick-theme.css";
import "../Slider/slick.css";
import { SwiperSlide, Swiper } from "swiper/react";
import SwipersItem from "../../Swipers/SwipersItem";
import { A11y, Autoplay, Navigation, Pagination, Scrollbar } from "swiper";
import "swiper/css"; //basic
import "swiper/css/navigation";
import "swiper/css/pagination";
import "../Upload/UploadSwiper.css";
import { confirmAlert } from "react-confirm-alert";
import { faX } from "@fortawesome/free-solid-svg-icons";

const MyEdit = ({ val, seteditModal, searchBar }) => {
  let menuRef = useRef();
  let searchRef = useRef();
  let titleRef = useRef();
  let textareaRef = useRef();
  let navigate = useNavigate();
  const [hide, setHide] = useState(false);
  const [close, setClose] = useState(false);
  const [search, setSearch] = useState([]);
  const [showSearch, setshowSearch] = useState(false);
  const [showWrite, setShowWrite] = useState(false);
  const [show] = useState(false);
  const [hasText, setHasText] = useState(false);
  const [inputValue, setinputValue] = useState("");

  //이미지 다중 업로드 시
  const [back, setback] = useState(false);
  const [myImagefile, setMyImageFile] = useState([]);

  const [modalShow, setModalShow] = useState(false);
  const [title, settitle] = useState("");
  const [content, setcontent] = useState("");

  useEffect(() => {
    settitle(val.ctitle);
    setcontent(val.content);
  }, []);

  function searchWord(e, setSearch) {
    //위치 지정을 위한 백에게 받는 axios
    let val = e.target.value;
    if (e.keyCode != 13) return;
    let token = sessionStorage.getItem("token");
    axios
      .get("/aamurest/gram/place/selectList", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        params: {
          searchWord: val,
        },
      })
      .then((resp) => {
        setSearch(resp.data);
      })
      .catch((error) => {
      });
  }

  function fn_checkByte(obj) {
    //textarea입력한 글자 count 및 글자 수 제한
    const maxByte = 1000; //최대 100바이트
    const text_val = obj.target.value; //입력한 문자
    const text_len = text_val.length; //입력한 문자수

    let totalByte = 0;
    for (let i = 0; i < text_len; i++) {
      const each_char = text_val.charAt(i);
      const uni_char = escapeSelector(each_char); //유니코드 형식으로 변환
      if (uni_char.length > 4) {
        // 한글 : 2Byte
        totalByte += 2;
      } else {
        // 영문,숫자,특수문자 : 1Byte
        totalByte += 1;
      }
    }

    if (totalByte > maxByte) {
      alert("최대 1000Byte까지만 입력가능합니다.");
      document.getElementById("nowByte").innerText = totalByte;
      document.getElementById("nowByte").style.color = "red";
    } else {
      document.getElementById("nowByte").innerText = totalByte;
      document.getElementById("nowByte").style.color = "green";
    }
  }

  const submit = () => {
    confirmAlert({
      title: "수정완료 하셨나요?",
      message: "지금 나가면 수정 내용이 저장되지 않습니다.",
      buttons: [
        {
          label: "확인",
          onClick: () => {
            seteditModal(false);
          },
        },
        {
          label: "취소",
        },
      ],
    });
  };

  return (
    <Container>
      <Overlay />
      <Contents>
        <FirstLine>
          <div
            className="newPosting"
            style={{ marginLeft: "45%", width: "20%" }}
          >
            <h2>수정하기</h2>
          </div>
          {/* {showNext ?  */}
          <Nextbtn style={{ marginRight: "10px" }}>
            <FontAwesomeIcon
              icon={faPaperPlane}
              size="2x"
              className="myEdit__editBtn"
              onClick={(e) => {
                e.stopPropagation();
                edit(
                  val,
                  seteditModal,
                  setShowWrite,
                  titleRef,
                  textareaRef,
                  searchRef,
                  search,
                  searchBar
                );

                // feedList(setlist)
              }}
            />
            <FontAwesomeIcon
              className="myEdit__editBtn"
              icon={faX}
              size="2x"
              style={{ marginLeft: "14px" }}
              onClick={(e) => {
                e.stopPropagation();
                submit();
              }}
            />
          </Nextbtn>
        </FirstLine>
        <Body>
          <form
            className="picfileframe"
            style={{ width: "65%" }}
            encType="multipart/form-data"
          >
            <input
              id="input-file"
              type="file"
              multiple
              accept="image/*"
              // 클릭할 때 마다 file input의 value를 초기화 하지 않으면 버그가 발생할 수 있다
              // 사진 등록을 두개 띄우고 첫번째에 사진을 올리고 지우고 두번째에 같은 사진을 올리면 그 값이 남아있음!
              onClick={(e) => (e.target.value = null)}
              // ref={refParam => inputRef = refParam}
              style={{ display: "none", width: "100%", height: "100%" }}
            />
            <div className="previewPic1">
              <ul>
                <Swiper
                  className="swiperContainer1_My"
                  modules={[Navigation, Pagination, Scrollbar, A11y, Autoplay]}
                  spaceBetween={10}
                  slidesPerView={1}
                  // navigation
                  autoplay={{ delay: 2500 }}
                  loop={true}
                  pagination={{ clickable: true }}
                  scrollbar={{ draggable: true }}
                >
                  {val.photo.map((image, i) => {
                    return (
                      <SwiperSlide>
                        <li>
                          <img className="divimage1" alt="sample" src={image} />
                        </li>
                        {/* <img className='divimage' alt="sample" src='/images/bg1.png'/> */}
                      </SwiperSlide>
                    );
                  })}
                </Swiper>
              </ul>
            </div>
            <label
              className="rweet_file_btn"
              onClick={() => {
                setHide(!hide);
              }}
              htmlFor="input-file"
            ></label>
          </form>

          {/* {showNext ?  */}
          <div className="side" style={{ width: "35%", height: "100%" }}>
            <div
              className="title-profile"
              style={{
                diplay: "flex",
                flexDirection: "row",
                justifyContent: "flex-start",
                alignItems: "center",
                margin: "10px",
              }}
            >
              <img
                style={{
                  width: "35px",
                  border: "solid 1px #dbdbdb",
                  borderRadius: "50%",
                }}
                src="'/img/bk.jpg ' ?? '/images/user.jpg'"
                alt="프사"
                onError={(e) => {
                  e.target.src = "/images/user.jpg";
                }}
              />
              <MyUsername>{sessionStorage.getItem("username")}</MyUsername>
            </div>
            <div>
              <span style={{ fontWeight: "bold", marginLeft: "10px" }}>
                제목 :{" "}
              </span>
              <input
                ref={titleRef}
                type="text"
                placeholder="제목을 입력하세요"
                onChange={(e) => {
                  settitle(e.target.value);
                }}
                value={title}
                style={{ width: "70%", textOverflow: "ellipsis" }}
              />
            </div>
            <div>
              <textarea
                ref={textareaRef}
                className="form-control"
                id="textArea_byteLimit"
                name="textArea_byteLimit"
                onKeyUp={(e) => fn_checkByte(e)}
                rows="8"
                placeholder="문구입력..."
                onChange={(e) => {
                  setcontent(e.target.value);
                }}
                value={content}
                style={{
                  border: "none",
                  resize: "none",
                  fontSize: "16px",
                  fontFamily: "normal",
                  outline: "none",
                  paddingTop: "5px",
                  marginLeft: "10px",
                  width: "90%",
                  position: "relative",
                }}
              ></textarea>
            </div>
            <div
              style={{
                borderBottom: "0.1px solid #c0c0c0",
                width: "97%",
                height: "27px",
              }}
            >
              <sup
                style={{
                  float: "right",
                  paddingRight: "15px",
                  color: "#c0c0c0",
                }}
              >
                (<span id="nowByte">0</span>/1000bytes)
              </sup>
            </div>
            <div
              className="uploadLocation"
              onClick={() => {
                setshowSearch(!showSearch);
              }}
            >
              <input
                onKeyUp={(e) => searchWord(e, setSearch)}
                value={close === false ? val.title : inputValue}
                style={{ width: "70%" }}
                onChange={(e) => {
                  setinputValue(e.target.value);
                  setHasText(true);
                }}
                placeholder="위치 추가"
                type="text"
                ref={searchRef}
              />
              {hasText ? (
                <SearchModal
                  search={search}
                  inputValue={inputValue}
                  setHasText={setHasText}
                  setinputValue={setinputValue}
                />
              ) : null}

              <i className="fa-solid fa-location-dot" />
              {close ? null : (
                <i
                  className="fa-regular fa-circle-xmark"
                  style={{ marginRight: "-10px" }}
                  onClick={() => {
                    setClose(!close);
                  }}
                />
              )}
            </div>
          </div>
          {/* // :null} */}
        </Body>
        {/* {show?<SearchModal search={search}/>:null} */}
      </Contents>
    </Container>
  );
};

function feedList(setlist) {
  let token = sessionStorage.getItem("token");
  axios
    .get("/aamurest/gram/selectList", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
    .then((resp) => {
      setlist(resp.data);
    })
    .catch((error) => {
    });
}

function edit(
  val,
  seteditModal,
  setShowWrite,
  titleRef,
  textareaRef,
  searchRef,
  search,
  searchBar
) {
  //새 게시물 업로드를 위한 axios
  let searched = search.find((val, i) => {
    return val.TITLE === searchRef.current.value;
  });

  let token = sessionStorage.getItem("token");
  axios
    .put(
      "/aamurest/gram/edit",
      {
        lno: val.lno,
        ctitle: titleRef.current.value,
        content: textareaRef.current.value,
        contentid: searched.CONTENTID,
      },
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )
    .then((resp) => {
      setShowWrite(resp.data);
      searchBar();
      if (resp.data.isSuccess === true) {
        alert("수정되었습니다");
        seteditModal(false);
      }
      // feedList(setlist);
    })
    .catch((error) => {
      alert("수정에 실패했습니다. 관리자에게 문의하세요");
    });
}
const MyUsername = styled.span`
  margin-top: -5px;
  font-size: 16px;
  font-weight: bold;
  margin-left: 10px;

  &:hover {
    text-decoration: underline;
    cursor: pointer;
  }
`;
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
  z-index: 2;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const Overlay = styled.div`
  position: absolute;
  width: 100%;
  height: 100%;
  z-index: 13;
  background-color: rgba(0, 0, 0, 0.6);
`;

const Contents = styled.div`
  position: relative;
  padding: 0 auto;
  width: 60%;
  top: 30px;
  // align-item:center;
  height: 700px;
  background: white;
  display: flex;
  flex-direction: column;
  border-radius: 7px;
  z-index: 14;
`;

const FirstLine = styled.div`
  height: auto;
  display: flex;
  justify-content: space-around;
  align-items: center;
  border-bottom: 0.1px solid rgb(211, 211, 211);
`;
const Body = styled.div`
  display: flex;
  height: 100%;
`;

const Deletebtn = styled.button`
  font-size: 20px;
`;
const Nextbtn = styled.button`
  font-size: 13px;
  font-weight: bold;
  margin-left: auto;
  margin-right: 40px;
`;
const Center = styled.div`
  text-align: center;
`;
export default MyEdit;
