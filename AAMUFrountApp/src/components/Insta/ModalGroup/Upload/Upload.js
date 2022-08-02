import React, { useState, useRef, useEffect } from "react";
import { Button } from "@mui/material";
import axios from "axios";
import styled from "styled-components";
import $, { escapeSelector } from "jquery";
import SearchModal from "./SearchModal";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPaperPlane } from "@fortawesome/free-regular-svg-icons";
import { Link, useNavigate } from "react-router-dom";
import Hashtag from "../../Hashtag";
import "../Slider/slick-theme.css";
import "../Slider/slick.css";
import { SwiperSlide, Swiper } from "swiper/react";
import SwipersItem from "../../Swipers/SwipersItem";
import { A11y, Autoplay, Navigation, Pagination, Scrollbar } from "swiper";
import "swiper/css"; //basic
import "swiper/css/navigation";
import "swiper/css/pagination";
import "../Upload/UploadSwiper.css";

const Uploader = ({ setsquare, setlist, setloading }) => {
  let searchRef = useRef();
  let titleRef = useRef();
  let textareaRef = useRef();
  let navigate = useNavigate();
  const [hide, setHide] = useState(false);
  const [upload, setupload] = useState(false);
  const [search, setSearch] = useState([]);
  const [showSearch, setshowSearch] = useState(false);
  const [showWrite, setShowWrite] = useState([]);
  const [show] = useState(false);
  const [hasText, setHasText] = useState(false);
  const [inputValue, setinputValue] = useState("");

  //이미지 하나 업로드시
  // const [image, setImage] = useState({//초기 이미지 세팅 및 변수
  //   image_file: "",
  //   preview_URL: "img/image.jpg",
  // });

  // let inputRef;
  // const saveImage = (e) => {
  //   e.preventDefault();
  //   if(e.target.files[0]){
  //     // 새로운 이미지를 올리면 createObjectURL()을 통해 생성한 기존 URL을 폐기
  //     //URL.revokeObjectURL(image.preview_URL);
  //     const preview_URL = URL.createObjectURL(e.target.files[0]);
  //     setImage(() => (
  //       {
  //         image_file: e.target.files[0],
  //         preview_URL: preview_URL
  //       }
  //     ))
  //   }
  // }

  //이미지 다중 업로드 시
  const [myImage, setMyImage] = useState([]);
  const [myImagefile, setMyImageFile] = useState([]);

  const addImage = (e) => {
    const nowSelectImageList = e.target.files; //한번에 받은 파일 리스트(object)
    const nowImageURLList = [...myImage]; //현재 myImage복사하고
    for (let i = 0; i < nowSelectImageList.length; i += 1) {
      //nowSelectImageList object를 i를 이용해서 돌리면서
      const nowImageURL = URL.createObjectURL(nowSelectImageList[i]);
      //미리보기 가능하게 변수화
      nowImageURLList.push(nowImageURL);
      //복사한 myImage에 추가
    }
    if (nowImageURLList.length > 5) {
      alert("사진은 5장 이하로만 가능합니다!");
      setHide(false);
      nowImageURLList = nowImageURLList.slice(0, 5);
    }
    setMyImage(nowImageURLList);
    setMyImageFile(nowSelectImageList);

    //myImage원본에 덮어씌우기
  };

  //이미지 삭제
  // const deleteFileImage = () =>{
  //   URL.revokeObjectURL(fileImage);
  //   setFileImage("");
  //   setHide(false)

  // console.log('Ref', countRef.current.value)
  // };
  {
    /* <button style={{
                    width: "50px",
                    height: "50px",
                    cursor: "pointer",
                    marginBottom:'-50px'}}
                    onClick={() => deleteFileImage()} > 삭제 </button>  */
  }

  const deleteImage = () => {
    // 이미지 삭제를 위해
    // createObjectURL()을 통해 생성한 기존 URL을 폐기
    setMyImage([]);
    URL.revokeObjectURL(myImage);
    setHide(false);
  };

  useEffect(() => {
    // 컴포넌트가 언마운트되면 createObjectURL()을 통해 생성한 기존 URL을 폐기
    return () => {
      URL.revokeObjectURL(myImage);
    };
  }, []);
  // const sendImageToServer = async () => {
  //   if (image.image_file) {
  //     const formData = new FormData()
  //     formData.append('file', image.image_file);
  //     await axios.get('/aamurest//gram/edit', formData,);
  //     alert("서버에 등록이 완료되었습니다!");
  //     setImage({
  //       image_file: "",
  //       preview_URL: "img/image.jpg",
  //     });
  //   } else {
  //     alert("사진을 등록하세요!")
  //   }
  // }

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
        // console.log(resp.data);
        setSearch(resp.data);
      })
      .catch((error) => {
        console.log(error);
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

  return (
    <Contents>
      <FirstLine>
        <Deletebtn variant="contained" onClick={deleteImage}>
          <i className="fa-solid fa-arrow-left"></i>
        </Deletebtn>
        <div className="newPosting">
          <h2>새 게시물 만들기</h2>
        </div>
        {/* {showNext ?  */}
        <Nextbtn
          onClick={(e) => {
            e.stopPropagation();
            let temp = uploadFile(myImagefile);
            console.log("temp", temp);
            gramEdit(
              temp,
              setloading,
              setlist,
              setupload,
              titleRef,
              textareaRef,
              searchRef,
              search
            );
            setsquare(false);
            setloading(true);
          }}
        >
          <FontAwesomeIcon icon={faPaperPlane} size="2x" />
        </Nextbtn>
        {/* {
                showNext && navigate('/Insta')
                //  window.location.reload(window.location.href)
              } */}
        {/* :
              <Nextbtn  onClick={()=>setshowNext(!showNext)}>다음</Nextbtn>
            }     */}
      </FirstLine>
      <Body>
        <form className="picfileframe" encType="multipart/form-data">
          <input
            id="input-file"
            type="file"
            multiple
            accept="image/*"
            onChange={addImage}
            // 클릭할 때 마다 file input의 value를 초기화 하지 않으면 버그가 발생할 수 있다
            // 사진 등록을 두개 띄우고 첫번째에 사진을 올리고 지우고 두번째에 같은 사진을 올리면 그 값이 남아있음!
            onClick={(e) => {
              e.stopPropagation();
              e.target.value = null;
            }}
            // ref={refParam => inputRef = refParam}
            style={{ display: "none", width: "100%", height: "100%" }}
          />
          {/* {myImage.map((images,i)=>(
                                   <img className='divimage' alt="sample" src={images}/>
                             
                          ))}  */}
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
                {myImage.map((image, i) => {
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
            onChange={addImage}
          >
            {hide ? null : (
              <Button type="primary" variant="contained">
                {/* onClick={() => inputRef.click()} */}
                컴퓨터에서 선택
              </Button>
            )}
          </label>
        </form>

        {/* {showNext ?  */}
        <div className="side">
          <div className="title-profile">
            <img
              src="'/img/bk.jpg ' ?? '/images/user.jpg'"
              alt="프사"
              onError={(e) => {
                e.stopPropagation();
                e.target.src = "/images/user.jpg";
              }}
            />
            <span className="uploadname">
              {sessionStorage.getItem("username")}
            </span>
          </div>
          <div>
            <span style={{ fontWeight: "bold", marginLeft: "10px" }}>
              제목 :{" "}
            </span>
            <input ref={titleRef} type="text" placeholder="제목을 입력하세요" />
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
            <Hashtag/>
          </div>
          <div
            style={{
              borderBottom: "0.1px solid #c0c0c0",
              width: "97%",
              height: "27px",
            }}
          >
            <sup
              style={{ float: "right", paddingRight: "15px", color: "#c0c0c0" }}
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
              value={inputValue}
              onChange={(e) => {
                e.stopPropagation();
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
                setHasText={setHasText}
                setinputValue={setinputValue}
              />
            ) : null}
            <i className="fa-solid fa-location-dot"></i>
          </div>
        </div>
        {/* // :null} */}
      </Body>
    </Contents>
  );
};

function feedList(setlist, setloading) {
  //업로드 버튼 누르고 화면 새로고침
  let token = sessionStorage.getItem("token");
  axios
    .get("/aamurest/gram/selectList", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
    .then((resp) => {
      console.log(resp.data);
      setlist(resp.data);
      setloading(false);
    })
    .catch((error) => {
      console.log(error);
    });
}

function uploadFile(myImagefile) {
  //이미지 업로드
  let formData = new FormData(); // formData 객체를 생성한다.
  for (let i = 0; i < myImagefile.length; i++) {
    formData.append("multifiles", myImagefile[i]); // 반복문을 활용하여 파일들을 formData 객체에 추가한다
  }
  return formData;
}

function gramEdit(
  temp,
  setloading,
  setlist,
  setupload,
  titleRef,
  textareaRef,
  searchRef,
  search
) {
  //새 게시물 업로드를 위한 axios
  let searched = search.find((val, i) => {
    return val.TITLE === searchRef.current.value;
  });
  console.log("searched:", searched);

  temp.append("id", sessionStorage.getItem("username"));
  temp.append("ctitle", titleRef.current.value);
  temp.append("content", textareaRef.current.value);
  temp.append("contentid", searched.CONTENTID);

  let token = sessionStorage.getItem("token");
  axios
    .post(
      "/aamurest/gram/edit",
      temp,
      //  { temp,
      //   id: sessionStorage.getItem('username'),
      //   ctitle: titleRef.current.value,
      //   content: textareaRef.current.value,
      //   contentid:searched.CONTENTID
      // },
      {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "multipart/form-data",
        },
      }
    )
    .then((resp) => {
      console.log(resp.data);
      setupload(resp.data);
      feedList(setlist, setloading);
    })
    .catch((error) => {
      console.log(error);
    });
}

const SliderContainer = styled.div`
  margin: 0 auto;
  margin-bottom: 2em;
  display: flex; // 이미지들을 가로로 나열합니다.
`;

const Contents = styled.div`
  position: relative;
  top: 30px;
  padding: 0 auto;
  width: 60%;
  // min-width:30%;
  // max-width:60%;
  height: 700px;
  background: white;
  display: flex;
  flex-direction: column;
  border-radius: 7px;
  z-index: 11;
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
const Center = styled.div`
  text-align: center;
`;
export default Uploader;
