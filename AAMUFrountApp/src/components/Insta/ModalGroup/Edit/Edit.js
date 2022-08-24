import React, { useState, useRef, useEffect } from "react";
import ReactTooltip from "react-tooltip";
import axios from "axios";
import styled from "styled-components";
import $, { escapeSelector } from "jquery";
import SearchModal from "../Upload/SearchModal";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPaperPlane } from "@fortawesome/free-regular-svg-icons";
import { useNavigate } from "react-router-dom";
import "../Slider/slick-theme.css";
import "../Slider/slick.css";
import { SwiperSlide, Swiper } from "swiper/react";
import { A11y, Autoplay, Navigation, Pagination, Scrollbar } from "swiper";
import "swiper/css"; //basic
import "swiper/css/navigation";
import "swiper/css/pagination";
import "../Upload/UploadSwiper.css";
import { confirmAlert } from "react-confirm-alert";
import HashTagModal from "../Upload/HashTagModal";

const Edit = ({ setlist, val, seteditModal, setloading, page, list }) => {
  let searchRef = useRef();
  let titleRef = useRef();
  let textareaRef = useRef();
  const [hide, setHide] = useState(false);
  const [search, setSearch] = useState([]);
  const [showSearch, setshowSearch] = useState(false);
  const [showWrite, setShowWrite] = useState(false);
  const [hasText, setHasText] = useState(false);
  const [tagItem, setTagItem] = useState([]);
  const [tagList, setTagList] = useState([]);
  const [tagModal, settagModal] = useState([]);
  const [editzz, setEdit] = useState([]);
  const [edithash, setedithash] = useState([]);
  const [listid, setlistid] = useState({
    TITLE: "",
    CONTENTID: "",
  });

  useEffect(() => {
    setedithash(val.tname);
    titleRef.current.value = val.ctitle;
    textareaRef.current.value = val.content;
    searchRef.current.value = val.title;
    setlistid({ TITLE: val.title, CONTENTID: val.contentid });
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
      .catch((error) => {});
  }

  const onKeyPress = (e) => {
    if (
      !e.target.value.includes("#") &&
      e.keyCode === 32 &&
      e.target.value.length !== 0
    ) {
      alert("#을 입력해주세요~!");
    }
    if (
      e.target.value.length !== 0 &&
      e.keyCode === 32 &&
      e.target.value.includes("#")
    ) {
      submitTagItem();
    }
  };

  const submitTagItem = () => {
    let updatedTagList = [...tagList];
    updatedTagList.push(tagItem);
    setTagList(updatedTagList);
    setTagItem("");
  };

  const deleteTagItem = (e) => {
    let delOne = e.target.parentElement.firstChild.textContent;
    setTagList((curr) => {
      return curr.filter((val) => {
        return val !== delOne;
      });
    });
  };

  const deleteTagedit = (e) => {
    let delOne = e.target.parentElement.firstChild.textContent;
    setedithash((curr) => {
      return curr.filter((val) => {
        return val !== delOne;
      });
    });
  };

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
      title: "수정내용을 삭제하시겠습니까?",
      message: "지금 나가면 수정 내용이 저장되지 않습니다.",
      buttons: [
        {
          label: "삭제",
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
      <Overlay
        onClick={() => {
          submit();
        }}
      />
      <Contents>
        <FirstLine>
          <div className="newPosting" style={{ marginLeft: "12%" }}>
            <h2>수정하기</h2>
          </div>
          {/* {showNext ?  */}
          <Nextbtn
            onClick={() => {
              edit(
                val,
                setlist,
                setShowWrite,
                titleRef,
                textareaRef,
                tagList,
                edithash,
                listid,
                setEdit,
                setloading,
                page,
                list
              );
              seteditModal(false);
              // feedList(setlist)
            }}
          >
            <FontAwesomeIcon icon={faPaperPlane} size="2x" />
          </Nextbtn>
        </FirstLine>
        <Body>
          <form className="picfileframe" encType="multipart/form-data">
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
                  className="swiperContainer1"
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
                      <SwiperSlide key={i}>
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
          <div className="side">
            <div className="title-profile">
              <img
                src={sessionStorage.getItem("userimg")}
                alt="프사"
                onError={(e) => {
                  e.target.src = "/images/user.jpg";
                }}
              />
              <span>{sessionStorage.getItem("username")}</span>
            </div>
            <div>
              <span style={{ fontWeight: "bold", marginLeft: "10px" }}>
                제목 :{" "}
              </span>
              <input
                ref={titleRef}
                type="text"
                placeholder="제목을 입력하세요"
                // onChange={(e) => {
                //   settitle(e.target.value);
                // }}
                // value={title}
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
                // onChange={(e) => {
                //   setcontent(e.target.value);
                // }}
                // value={content}
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
              className="uploadLocation2"

              onClick={() => {
                setshowSearch(!showSearch);
              }}
            >
              <input
                onKeyUp={(e) => {
                  searchWord(e, setSearch);
                  setHasText(true);
                }}
                style={{ width: "80%" }}
                // onChange={(e) => {
                //   setinputValue(e.target.value);

                // }}
                data-tip="위치 입력"
                placeholder="위치 추가"
                type="text"
                ref={searchRef}
              />  
              {hasText ? (
                <Searchengine>
                <Searchcontents>
                  {search.map((val, i) => {
                    return (
                      <P
                        key={i}
                        onClick={(e) => {
                          e.stopPropagation();
                          setlistid({ TITLE: val.TITLE, CONTENTID: val.CONTENTID });
                          searchRef.current.value = val.TITLE
                          setHasText(false);
                        }}
                      >
                        {val.TITLE}
                      </P>
                    );
                  })}
                  {/* // val.indexOf(inputValue)!==-1?<P onClick={()=>onClick(i)}>{val.TITLE}</P>  */}
                </Searchcontents>
              </Searchengine>
              ) : null}

              <i className="fa-solid fa-location-dot" />
              {/* {close ? null : (
                <i
                  className="fa-regular fa-circle-xmark"
                  style={{ marginRight: "-10px" }}
                  onClick={() => {
                    setClose(!close);
                  }}
                />
              )} */}
            </div>

            <div>
              {edithash === null
                ? ""
                : edithash.map((tagItem, index) => {
                    return (
                      <TagItem key={index}>
                        <p>{tagItem}</p>
                        <DDButton
                          onClick={(e) => {
                            deleteTagedit(e);
                          }}
                        >
                          X
                        </DDButton>
                      </TagItem>
                    );
                  })}
              {tagList.map((tagItem, index) => {
                return (
                  <TagItem key={index}>
                    <p>{tagItem}</p>
                    <DDButton
                      onClick={(e) => {
                        deleteTagItem(e);
                      }}
                    >
                      X
                    </DDButton>
                  </TagItem>
                );
              })}

              <div className="uploadLocation">
                <input
                  type="text"
                  data-tip="입력후 스페이스바를 눌러주세요"
                  placeholder="해시태그 추가"
                  tabIndex={2}
                  onChange={(e) => {
                    e.stopPropagation();
                    setTagItem(e.target.value);
                    setShowWrite(true);
                  }}
                  value={tagItem}
                  onKeyUp={(e) => {
                    hashTag(e, settagModal);
                    onKeyPress(e);
                    setShowWrite(true);
                  }}
                />
                {showWrite && (
                  <Searchengine>
                    <Searchcontents>
                      {tagModal.length > 1 &&
                        tagModal.map((val, i) => {
                          return (
                            <P
                              key={i}
                              onClick={(e) => {
                                e.stopPropagation();
                                setTagItem(e.target.value);
                                setShowWrite(false);
                              }}
                            >
                              {val}
                            </P>
                          );
                        })}
                    </Searchcontents>
                  </Searchengine>
                )}

                <i className="fa-solid fa-hashtag"></i>
              </div>
            </div>
            <ReactTooltip />
          </div>
          {/* // :null} */}
        </Body>
        {/* {show?<SearchModal search={search}/>:null} */}
      </Contents>
    </Container>
  );
};

const feedList = async (setloading, setlist, page, list) => {
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
  console.log(temp.data);
  setloading(false);
};

function hashTag(e, settagModal) {
  let val = e.target.value;
  // submitTagItem()
  //업로드 버튼 누르고 화면 새로고침
  let token = sessionStorage.getItem("token");
  axios
    .get("/aamurest/gram/tag", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
      params: {
        tname: val,
      },
    })
    .then((resp) => {
      settagModal(resp.data);
    })
    .catch((error) => {});
}

function edit(
  val,
  setlist,
  setShowWrite,
  titleRef,
  textareaRef,
  tagList,
  edithash,
  listid,
  setEdit,
  setloading,
  page,
  list
) {
  //새 게시물 업로드를 위한 axios

  let updatedTagList1;
  updatedTagList1 = [ ...tagList,...edithash];
  console.log("updatedTagList1 : ", updatedTagList1);
  setEdit(updatedTagList1);
  let token = sessionStorage.getItem("token");
  axios
    .put(
      "/aamurest/gram/edit",
      {
        lno: val.lno,
        ctitle: titleRef.current.value,
        content: textareaRef.current.value,
        contentid: parseInt(listid.CONTENTID),
        tname: updatedTagList1,
      },
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )
    .then((resp) => {
      console.log(resp.data);
      setShowWrite(resp.data);
      feedList(setloading, setlist, page, list);
      alert("수정이 완료되었습니다!");
    })
    .catch((error) => {
      console.log(error);
    });
}

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
  top: 30px;
  width: 60%;
  // min-width:30%;
  // max-width:60%;
  height: 750px;
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
`;

const TagItem = styled.div`
  display: inline-flex;
  align-items: center;
  justify-content: space-between;
  margin: 5px;
  padding: 5px;
  width: fit-content;
  background-color: orange;
  border-radius: 5px;
  color: white;
  font-size: 13px;
`;

const DDButton = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 15px;
  height: 15px;
  margin-left: 5px;
  background-color: white;
  border-radius: 50%;
  color: tomato;
`;
const Searchengine = styled.div`
  position: absolute;
  width: 35%;
  height: 250px;
  background-color: transparent;
  right: 10px;
  z-index: 9;
  overflow: auto;
`;
const Searchcontents = styled.div`
  position: absolute;
  width: 95%;
  display: flex;
  background-color: #fff;
  flex-direction: column;
  box-shadow: 0 0 5px 1px rgba(var(--jb7, 0, 0, 0), 0.0975);
  align-items: center;
  margin-left: 10px;
`;
const P = styled.div`
  display: flex;
  position: relative;
  width: 100%;
  height: 50px;
  padding-left: 10px;
  align-items: center;
  cursor: pointer;
  font-size: 15px;

  &:hover {
    background-color: #e5e5e5;
  }
`;
export default Edit;
