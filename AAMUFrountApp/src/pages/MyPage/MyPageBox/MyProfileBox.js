import React, { useEffect, useRef, useState } from "react";
import styled from "styled-components";
import DaumPostcode from "react-daum-postcode";
import axios from "axios";

import MyTheme from "./MyTheme";

const MyProfileBox = ({ setClickTab }) => {
  const [name, setName] = useState();
  const [gender, setGender] = useState();
  const [userId, setUserId] = useState();
  const [addrIsValid, setAddrIsValid] = useState(false);
  const [isOpenPost, setIsOpenPost] = useState(false);
  const [showImages, setShowImages] = useState([]); //이미지
  const [showImagesFile, setShowImagesFile] = useState([]);
  const [checkeds, setCheckeds] = useState([]);
  let pwdRef = useRef();
  let fPhoneNum = useRef();
  let sPhoneNum = useRef();
  let tPhoneNum = useRef();
  let eIdRef = useRef();
  let eAddrdRef = useRef();
  let zCodeRef = useRef();
  let addrRef = useRef();
  let addrDetailRef = useRef();
  let introduceRef = useRef();
  let imgRef = useRef();
  //이미지 등록
  console.log("checkeds", checkeds);
  const handleAddImages = (e) => {
    const imageLists = e.target.files;
    let imageUrlLists = [...showImages];
    let imgs = [...showImagesFile];

    if (imageLists.length !== 0) {
      for (let i = 0; i < imageLists.length; i++) {
        const currentImageUrl = URL.createObjectURL(imageLists[i]);
        imageUrlLists.push(currentImageUrl);

        imgs.push(imageLists[i]);
      }
    }

    if (imageUrlLists.length > 1) {
      //사진 1개 제한
      imageUrlLists = imageUrlLists.slice(0, 1);
    }
    setShowImages(imageUrlLists);
    setShowImagesFile(imgs);

    let formData = new FormData();
    formData.append("userprofile", e.target.files[0]);
    return formData;
  };

  const handleDeleteImage = () => {
    //등록한 사진 삭제
    setShowImages([]);
  };
  async function getDatas() {
    try {
      let token = sessionStorage.getItem("token");
      let resp = await axios.get("/aamurest/users/selectone", {
        params: {
          id: sessionStorage.getItem("username"),
        },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      console.log("resp", resp.data);
      pwdRef.current.value = resp.data.pwd;
      fPhoneNum.current.value = resp.data.phonenum.split("-")[0];
      sPhoneNum.current.value = resp.data.phonenum.split("-")[1];
      tPhoneNum.current.value = resp.data.phonenum.split("-")[2];
      eIdRef.current.value = resp.data.email.split("@")[0];
      eAddrdRef.current.value = resp.data.email.split("@")[1];
      zCodeRef.current.value = resp.data.addrid.split("/")[0];
      addrRef.current.value = resp.data.addrid.split("/")[1];
      addrDetailRef.current.value = resp.data.addrid.split("/")[2];
      introduceRef.current.value = resp.data.self;
      setCheckeds(resp.data.theme);
      setShowImages(resp.data.userprofile.split());
      setName(resp.data.name);
      setGender(resp.data.gender);
      setUserId(resp.data.id);
    } catch (err) {
      console.log("프로필 가져오기 실패", err);
    }
  }
  useEffect(() => {
    getDatas();
  }, []);
  let [profiles, setProfile] = useState([]);
  // console.log("profiles", profiles);
  function profileUpdate() {
    let phoneNum = `${fPhoneNum.current.value}-${sPhoneNum.current.value}-${tPhoneNum.current.value}`;
    let addr = `${zCodeRef.current.value}/${addrRef.current.value}/${addrDetailRef.current.value}`;
    let email = `${eIdRef.current.value}@${eAddrdRef.current.value}`;
    if (profiles.length == 0) {
      profiles = new FormData();
      console.log("profileUpdate 클릭 후 호출 함수:", profiles);
    }
    profiles.append("addrid", addr);
    profiles.append("email", email);
    profiles.append("gender", gender);
    profiles.append("id", sessionStorage.getItem("username"));
    profiles.append("name", name);
    profiles.append("phonenum", phoneNum);
    profiles.append("pwd", pwdRef.current.value);
    profiles.append("self", introduceRef.current.value);
    profiles.append("theme", checkeds.join());
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
        if (resp.data.result === 1) {
          sessionStorage.setItem("userimg", resp.data.Dto.userprofile);
          alert("프로필 수정이 완료되었습니다.");
        } else {
          alert("프로필 수정이 실패했습니다");
        }
      })
      .catch((error) => {
        console.log(error);
      });
  }
  return (
    <MyProfileContainer>
      <MyUpdateImg>
        <ImgBox>
          <Imgs src="images/no-image.jpg" />
          {/* <Imgs src={userProfile.userprofile}/> */}
          <form encType="multipart/form-data">
            <input
              className="write-picture-input"
              ref={imgRef}
              type="file"
              id="upload"
              onChange={(e) => {
                setProfile(handleAddImages(e));
              }}
              onClick={(e) => (e.target.value = null)}
            />
            <ImgUploadLabel for="upload">
              {showImages.map((image, id) => (
                <Imgs src={showImages} alt={`${image}-${id}`} onClick={() => handleDeleteImage()} />
              ))}
            </ImgUploadLabel>
          </form>
        </ImgBox>
      </MyUpdateImg>
      {/* ---------------------------------------------- */}
      <MyUpdateProfile>
        <div className="myProfile__left">
          <div className="profile-title-name-id">
            <div>
              <span>이름</span>
              <Name>
                <input value={name} readOnly />
              </Name>
            </div>
          </div>
          <div style={{ display: "flex", gap: "1rem" }}>
            <RRNContainer>
              <div className="profile-title">아이디</div>
              <input type="text" value={userId} readOnly />
            </RRNContainer>
            <RRNContainer>
              <div className="profile-title">비밀번호</div>
              <input type="password" ref={pwdRef} />
            </RRNContainer>
          </div>
          <PhoneNum>
            <div className="profile-title">휴대폰 번호</div>
            <div>
              <input type="text" ref={fPhoneNum} />
              &nbsp;-&nbsp;
              <input type="text" ref={sPhoneNum} />
              &nbsp;-&nbsp;
              <input type="text" ref={tPhoneNum} />
            </div>
          </PhoneNum>

          <EmailContainer>
            <div className="profile-title">이메일</div>
            <div>
              <input type="text" ref={eIdRef} />
              &nbsp;@&nbsp;
              <input type="text" ref={eAddrdRef} />
            </div>
          </EmailContainer>
        </div>
        {/* ---------------------------------------------- */}
        <div className="myProfile__right">
          <div className="profile-title-addr">
            <span>주소</span>

            <AddrBtn
              type="button"
              onClick={() => {
                setAddrIsValid(!addrIsValid);
                setIsOpenPost(!isOpenPost);
              }}
            >
              주소 변경하기
            </AddrBtn>
          </div>
          <div className="myProfile__addr">
            <div className="myProfile__zCode">
              <input type="text" size={14} placeholder="우편번호" ref={zCodeRef} />
            </div>

            <div className="myProfile__addrs">
              <input type="text" size={52} placeholder="기본주소" ref={addrRef} />
            </div>

            <div className="myProfile__addrs">
              <input
                style={{ marginLeft: "3px" }}
                type="text"
                size={52}
                placeholder="상세주소"
                ref={addrDetailRef}
              />
            </div>
          </div>
          {isOpenPost && (
            <AddresApi
              isOpenPost={isOpenPost}
              setIsOpenPost={setIsOpenPost}
              zCodeRef={zCodeRef}
              addrRef={addrRef}
            />
          )}

          <div className="join__stepTwo-introduce-div" style={{ width: "100%", height: "50%" }}>
            <textarea
              ref={introduceRef}
              style={{
                // position: "absolute",
                width: "100%",
                height: "100%",
                resize: "none",
                border: "none",
                outline: "none",
              }}
            ></textarea>
          </div>
        </div>
        {/* ---------------------------------------------- */}
      </MyUpdateProfile>

      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          gap: "10px",
        }}
      >
        <div className="myProfile__theme-container">
          <span className="profile-title-theme">나의 테마 &nbsp;:&nbsp;</span>
          <MyTheme checkeds={checkeds} setCheckeds={setCheckeds} />
        </div>

        <UpdateBtn
          type="button"
          onClick={(e) => {
            profileUpdate();
          }}
        >
          저장
        </UpdateBtn>
      </div>
    </MyProfileContainer>
  );
};

function uploadFile(showImages) {
  //이미지 업로드
  let formData = new FormData();
  formData.append("userprofile", showImages);

  return formData;
}

const AddresApi = ({ setIsOpenPost, zCodeRef, addrRef }) => {
  function profileUpdate(profiles, phoneNum, email, addr, introduce, pwd, gender, name) {
    if (profiles.length === 0) {
      profiles = new FormData();
      console.log("profileUpdate 클릭 후 호출 함수:", profiles);
    }

    profiles.append("addrid", addr);
    profiles.append("email", email);
    profiles.append("gender", gender);
    profiles.append("id", sessionStorage.getItem("username"));
    profiles.append("name", name);
    profiles.append("phonenum", phoneNum);
    profiles.append("pwd", pwd);
    profiles.append("self", introduce);
    let token = sessionStorage.getItem("token");
    axios
      .post("/aamurest/users/upload", profiles, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-type": "multipart/form-data",
        },
      })
      .then((resp) => {
        if (resp.data === 1) {
          alert("프로필 수정이 완료되었습니다.");
        }
      })
      .catch((error) => {
        console.log(error);
      });
  }
  const onCompletePost = (data) => {
    let fullAddr = data.address;
    let extraAddr = "";

    zCodeRef.current.value = data.zonecode;
    addrRef.current.value = data.address;

    if (data.addressType === "R") {
      if (data.bname !== "") {
        extraAddr += data.bname;
      }
      if (data.buildingName !== "") {
        extraAddr += extraAddr !== "" ? `, ${data.buildingName}` : data.buildingName;
      }
      fullAddr += extraAddr !== "" ? ` (${extraAddr})` : "";
    }

    setIsOpenPost(false);
  };
  const postCodeStyle = {
    display: "block",
    position: "absolute",
    top: "140px",
    right: "680px",
    width: "400px",
    height: "500px",
    borderRadius: "8px",
    border: "1px solid grey",
    padding: "1px",
    zIndex: "10",
  };

  return (
    <div>
      <DaumPostcode
        style={postCodeStyle}
        className="daumPostCode_myProfile"
        autoClose
        onComplete={onCompletePost}
      />
    </div>
  );
};

const MyProfileContainer = styled.div`
  display: flex;
  // grid-template-columns: 500px 520px;
  // grid-template-rows: 500px;
  flex-direction: column;
  gap: 0.5rem;
  font-size: 18px;
  margin: auto;
  width: 100%;
  height: 100%;
  max-width: 1200px;
`;

const MyUpdateImg = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 20px 0;
  width: 100%;
  height: 100%;
  border-radius: 5px;
  box-shadow: var(--shadow);
`;
const ImgBox = styled.div`
  width: 250px;
  height: 250px;
  border-radius: 70%;
  overflow: hidden;
  // position: absolute;
  position: relative;
  box-shadow: var(--shadow);
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
  display: grid;
  // flex-direction: column;
  grid-template-columns: 50% 50%;
  // padding: 5px;
  width: 100%;
  height: 100%;
  gap: 10px;
`;
const Name = styled.div`
  input {
    width: 573px;
    border: 2px solid grey;
    padding: 3px;
    font-size: 18px;
    border-radius: 5px;
  }
`;

const RRNContainer = styled.div`
  input {
    font-size: 18px;
    width: 277px;
    border: solid 2px gray;
    border-radius: 0.3em;
    padding: 3px;
  }
`;
const PhoneNum = styled.div`
  display: flex;
  gap: 2px;
  flex-direction: column;

  input {
    width: 177px;
    border: solid 2px gray;
    font-size: 18px;
    border-radius: 0.3em;
    padding: 3px;
  }
`;
const EmailContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 3px;

  input {
    font-size: 18px;
    width: 271px;
    border: solid 2px gray;
    border-radius: 0.3em;
    padding: 3px;
  }
`;
const AddrBtn = styled.button`
  padding: 3px 10px;
  background-color: var(--orange);
  color: white;
  border-radius: 5px;
  font-weight: bold;
`;
const UpdateBtn = styled.span`
  width: 65px;
  padding: 5px 10px;
  background-color: var(--orange);
  color: white;
  border-radius: 5px;
  font-weight: bold;
  margin-top: 10px;
  text-align: center;
  border: 2px solid var(--orange);
  &:hover {
    color: black;
    cursor: pointer;
    background: white;
  }
`;

export default MyProfileBox;
