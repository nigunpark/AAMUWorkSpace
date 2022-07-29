import { style } from "@mui/system";
import React, { useEffect, useRef, useState } from "react";
import styled from "styled-components";
import DaumPostcode from "react-daum-postcode";
import axios from "axios";
import { Avatar } from "antd";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPaperPlane } from "@fortawesome/free-regular-svg-icons";
import { SwiperSlide, Swiper } from "swiper/react";

const MyInstaBox = () => {
  const [showImages, setShowImages] = useState([]); //이미지

  //이미지 등록
  const handleAddImages = (e) => {
    const imageLists = e.target.files;
    let imageUrlLists = [...showImages];

    for (let i = 0; i < imageLists.length; i++) {
      const currentImageUrl = URL.createObjectURL(imageLists[i]);
      imageUrlLists.push(currentImageUrl);
    }

    if (imageUrlLists.length > 1) {
      //사진 1개 제한
      imageUrlLists = imageUrlLists.slice(0, 1);
    }
    setShowImages(imageUrlLists);

    let formData = new FormData();
    formData.append("userprofile", e.target.files[0]);
    return formData;
  };

  const handleDeleteImage = () => {
    //등록한 사진 삭제
    setShowImages([]);
  };

  useEffect(() => {
    let token = sessionStorage.getItem("token");

    axios
      .get("", {
        params: {
          // id:sessionStorage.getItem('username')
        },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        console.log("insta 데이터 확인 : ", resp.data);
      })
      .catch((error) => {
        console.log((error) =>
          console.log("insta 데이터 가져오기 실패", error)
        );
      });
  }, []);

  return (
    <MyProfileContainer>
      <FirstLine>
        <div className="newPosting" style={{ marginLeft: "12%" }}>
          <h2>내 게시물</h2>
        </div>
        {/* {showNext ?  */}
        <Nextbtn
          onClick={() => {
            // edit(val,setlist,setShowWrite,titleRef,textareaRef,searchRef,search)
            // seteditModal(false)
            // feedList(setlist)
          }}
        >
          <FontAwesomeIcon icon={faPaperPlane} size="2x" />
        </Nextbtn>
      </FirstLine>

      {/* <Body>
          <form className='picfileframe'  encType='multipart/form-data'>
            <input  
              id="input-file"
              type="file" 
              multiple
              accept="image/*"
              // 클릭할 때 마다 file input의 value를 초기화 하지 않으면 버그가 발생할 수 있다
              // 사진 등록을 두개 띄우고 첫번째에 사진을 올리고 지우고 두번째에 같은 사진을 올리면 그 값이 남아있음!
              onClick={(e) => e.target.value = null}
              // ref={refParam => inputRef = refParam}
              style={{display: "none" , width:'100%',height:'100%'}}
            />
            <div className="previewPic1">
              <ul>
                <Swiper
                  className="swiperContainer1"
                  // modules={[Navigation, Pagination, Scrollbar, A11y, Autoplay]}
                  spaceBetween={10}
                  slidesPerView={1}
                  // navigation
                  autoplay={{ delay: 2500 }}
                  loop={true}
                  pagination={{ clickable: true }}
                  scrollbar={{ draggable: true }}
                  >
                  {
                    val.photo.map((image,i)=>{
                      return(
                          <SwiperSlide>
                            <li>
                              <img className='divimage1' alt="sample" src={image}/>
                            </li>
                          </SwiperSlide>
                        )
                      })
                  }
                </Swiper>
              </ul>
            </div>
            <label 
              className="rweet_file_btn" 
              onClick={()=>{
                // setHide(!hide)
              }}
              htmlFor="input-file"          
              >
            </label>  
          </form>
        </Body> */}
    </MyProfileContainer>
  );
};

function uploadFile(showImages) {
  //이미지 업로드
  let formData = new FormData(); // formData 객체를 생성한다.
  for (let i = 0; i < showImages.length; i++) {
    formData.append("userprofile", showImages[i]); // 반복문을 활용하여 파일들을 formData 객체에 추가한다
  }
  return formData;
}

function profileUpdate(profiles) {
  // profiles.append('addrid', addr);

  let token = sessionStorage.getItem("token");
  axios
    .post("/aamurest/users/upload", profiles, {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-type": "multipart/form-data",
      },
    })
    .then((resp) => {
      console.log(resp.data);
    })
    .catch((error) => {
      console.log(error);
    });
}

const MyProfileContainer = styled.div`
  display: grid;
  width: 100%;
  // grid-template-columns: 520px 500px;
  grid-template-rows: 50px 600px;
  // gap: 10px;
  // border: solid 1px black;
  font-size: 18px;
  margin: auto;
`;
const FirstLine = styled.div`
  height: auto;
  display: flex;
  justify-content: space-around;
  align-items: center;
  border-bottom: 0.1px solid rgb(211, 211, 211);

  border: 1px solid red;
`;
const Nextbtn = styled.button`
  font-size: 13px;
  font-weight: bold;
`;
const Body = styled.div`
  display: flex;
  height: 100%;
`;

const MyUpdateImg = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: var(--app-container);
  border-radius: 0.5em;
  border: solid 1px black;
`;
const ImgBox = styled.div`
  width: 250px;
  height: 250px;
  border-radius: 70%;
  overflow: hidden;
  // position: absolute;
  position: relative;
`;
const Imgs = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
`;
const ImgUploadLabel = styled.label`
  position: absolute;
  border-radius: 50%;
  top: 0px;
  left: 0px;
  width: 250px;
  height: 250px;
  // border: 1px red solid;
`;
const MyUpdateProfile = styled.div`
  display: flex;
  flex-direction: column;
  border: solid 1px black;
`;
const Name = styled.div`
  width: 100px;
  margin-bottom: 10px;
  margin-left: 10px;
`;
const Id = styled.div`
  width: 100px;
  margin-bottom: 10px;
  margin-left: 130px;
`;
const RRNContainer = styled.div`
  // display: flex;
  // flex-direction: row;
  // margin-bottom: 10px;
  // margin-left: 10px;
  // gap: 2px;

  // div{
  // }

  display: flex;
  flexdirection: row;
  gap: 3px;
  margin-left: 10px;
  margin-bottom: 10px;

  input {
    font-size: 18px;
    width: 100px;
    border: solid 2px gray;
    border-radius: 0.3em;
  }
`;
const PhoneNum = styled.div`
  display: flex;
  flex-direction: row;
  gap: 2px;
  margin-bottom: 10px;
  margin-left: 10px;

  input {
    width: 70px;
    border: solid 2px gray;
    border-radius: 0.3em;
  }
`;
const EmailContainer = styled.div`
  display: flex;
  flexdirection: row;
  gap: 3px;
  margin-left: 10px;
  margin-bottom: 10px;

  input {
    font-size: 18px;
    width: 100px;
    border: solid 2px gray;
    border-radius: 0.3em;
  }
`;
const AddrBtn = styled.button`
  padding: 3px 10px;
  background-color: var(--orange);
  color: white;
  border-radius: 5px;
  font-weight: bold;
`;
const UpdateBtn = styled.button`
  margin-top: 5px;
  width: 100px;
  padding: 3px 10px;
  background-color: var(--orange);
  color: white;
  border-radius: 5px;
  font-weight: bold;
`;

export default MyInstaBox;
