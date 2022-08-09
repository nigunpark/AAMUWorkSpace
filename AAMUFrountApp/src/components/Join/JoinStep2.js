import React, { useEffect, useRef, useState } from "react";
import {
  Container,
  ContentStep2,
  Title,
  Body,
  Footer,
} from "../Modal/ForJoin.js";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUser } from "@fortawesome/free-regular-svg-icons";
import "./Join.css";
import { Typography } from "@mui/material";
import { Link, useNavigate } from "react-router-dom";
import {
  fa1,
  fa2,
  fa3,
  faCheck,
  faHouse,
} from "@fortawesome/free-solid-svg-icons";
import DaumPostcode from "react-daum-postcode";
import emailjs from "@emailjs/browser";
import { addStepTwo } from "../../redux/store.js";
import { useSelector, useDispatch } from "react-redux";

const JoinStep2 = () => {
  let joominGender = useRef();
  let nameRef = useRef();
  let nameValidRef = useRef("");
  let joominValidRef = useRef();
  let phoneNumF = useRef();
  let phoneNumS = useRef();
  let phoneNumT = useRef();
  let phoneNumValidRef = useRef();
  let sJoominRef = useRef();
  let emailIdRef = useRef("");
  let emailAddrRef = useRef("");
  let emailValidRef = useRef();
  let photoRef = useRef("");
  let zoneCodeRef = useRef();
  let addrValidRef = useRef();
  let addrDetailRef = useRef();
  let addrRef = useRef();
  let introduceRef = useRef();
  let navigate = useNavigate();
  let imgRef = useRef();
  let reduxState = useSelector((state) => {
    return state;
  });
  let dispatch = useDispatch();
  const [address, setAddress] = useState("");
  const [zoneCode, setZoneCode] = useState("");
  const [isOpenPost, setIsOpenPost] = useState(false);
  const [emailCk, setEmailCk] = useState(false);
  const [eId, setEId] = useState("");
  const [eAddr, setEAddr] = useState("");
  const [imageFile, setImageFile] = useState(null);
  console.log("reduxState", reduxState.joinData);
  useEffect(() => {
    if (sessionStorage.getItem("usernickname") !== null) {
      if (sessionStorage.getItem("userimgkakao").length > 0) {
        const img = sessionStorage.getItem("userimgkakao");
        photoRef.current.src = img;
      } else {
        photoRef.current.src = "/images/no-image.jpg";
      }
      const email = sessionStorage.getItem("useremailkakao").split("@");
      setEId(email[0]);
      setEAddr(email[1]);
      const name = sessionStorage.getItem("usernickname");
      nameRef.current.value = name;
    }
  }, []);
  return (
    <div className="join__step-two">
      <Container>
        <div className="join__slide-in-right">
          <ContentStep2>
            <Link to="/">
              <FontAwesomeIcon className="home-btn" icon={faHouse} />
            </Link>
            <Title>
              <div className="loginTitle__container">
                <FontAwesomeIcon icon={faUser} className="joinIcon" />
                <h1>회원가입</h1>
              </div>
              <p style={{ color: "gray" }}>welcome to AAMU</p>
            </Title>
            <div className="join__progress-container">
              <FontAwesomeIcon icon={fa1} className="join__progress-icon " />
              -
              <FontAwesomeIcon
                icon={fa2}
                className="join__progress-icon join__progress-step"
              />
              -
              <FontAwesomeIcon icon={fa3} className="join__progress-icon" />
            </div>
            <Body>
              <div className="join__input-container">
                <div className="join__stepTwo-top">
                  <div className="join__profile-img-container">
                    <div className="join__profile-img-preview">
                      <label htmlFor="photo" className="join__stepTwo-label" />
                      <img
                        alt=""
                        className="join__stepTwo-img"
                        ref={photoRef}
                        src="/images/no-image.jpg"
                      />
                    </div>
                    <form encType="multipart/form-data">
                      <input
                        id="photo"
                        type="file"
                        ref={imgRef}
                        onChange={(e) => {
                          if (e.target.files && e.target.files[0]) {
                            let reader = new FileReader();
                            reader.onload = function (e) {
                              photoRef.current.src = e.target.result;
                            };
                            reader.readAsDataURL(e.target.files[0]);
                            setImageFile(addImage(e, imgRef));
                          } else {
                            photoRef.current.src = "";
                          }
                        }}
                        style={{ display: "none" }}
                      />
                    </form>
                  </div>
                  <div className="join__stepTwo-input-wrap">
                    <div>
                      <div style={{ display: "flex" }}>
                        <span style={{ fontSize: "13px" }}>이름</span>{" "}
                        <span
                          className="join__keyup-validSpan"
                          ref={nameValidRef}
                        >
                          (이름을 입력하세요)
                        </span>
                      </div>
                      <div
                        className="join__stepTwo-input-div"
                        style={{
                          display: "flex",
                          justifyContent: "space-between",
                        }}
                      >
                        <input
                          style={{ fontSize: "13px", marginLeft: "3px" }}
                          type="text"
                          size={20}
                          ref={nameRef}
                          onChange={() => {
                            nameValid(nameValidRef, nameRef);
                          }}
                        />
                      </div>
                    </div>
                    <div>
                      <div style={{ display: "flex" }}>
                        <span style={{ fontSize: "13px" }}>주민등록번호</span>{" "}
                        <span
                          className="join__keyup-validSpan"
                          ref={joominValidRef}
                        >
                          (형식에 맞게 입력해주세요)
                        </span>
                      </div>
                      <div
                        style={{
                          display: "flex",
                          gap: "0.5rem",
                        }}
                      >
                        <div className="join__stepTwo-joomin-input-div">
                          <input
                            style={{
                              fontSize: "13px",
                              marginLeft: "3px",
                              letterSpacing: "5px",
                            }}
                            type="text"
                            size={12}
                            maxLength={6}
                            placeholder=""
                            ref={sJoominRef}
                            onChange={() => {
                              joominValid(
                                joominValidRef,
                                sJoominRef,
                                joominGender
                              );
                            }}
                          />
                        </div>
                        <span>-</span>
                        <div style={{ display: "flex" }}>
                          <div className="join__stepTwo-joomin-input-back-div">
                            <input
                              style={{
                                fontSize: "13px",
                                width: "25px",
                                border: "2px solid rgb(128, 128, 128)",
                                borderRadius: "5px",
                                textAlign: "center",
                              }}
                              type="text"
                              maxLength={1}
                              size={1}
                              placeholder=""
                              ref={joominGender}
                              onChange={() => {
                                joominValid(
                                  joominValidRef,
                                  sJoominRef,
                                  joominGender
                                );
                              }}
                            />
                            <span
                              style={{
                                letterSpacing: "4px",
                                paddingTop: "5px",
                              }}
                            >
                              ******
                            </span>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div>
                      <div style={{ display: "flex" }}>
                        <span style={{ fontSize: "13px" }}>휴대폰번호</span>{" "}
                        <span
                          className="join__keyup-validSpan"
                          ref={phoneNumValidRef}
                        >
                          (휴대폰번호를 입력해주세요)
                        </span>
                      </div>
                      <div
                        style={{
                          display: "flex",
                          justifyContent: "space-between",
                        }}
                      >
                        <div
                          className="join__stepTwo-phonenum-input-div"
                          style={{
                            display: "flex",
                            justifyContent: "space-between",
                          }}
                        >
                          <input
                            type="text"
                            size={20}
                            ref={phoneNumF}
                            className="join__stepTwo-phonenum-input"
                            onChange={() => {
                              phoneNumValid(
                                phoneNumValidRef,
                                phoneNumF,
                                phoneNumS,
                                phoneNumT
                              );
                            }}
                          />
                        </div>
                        -
                        <div
                          className="join__stepTwo-phonenum-input-div"
                          style={{
                            display: "flex",
                            justifyContent: "space-between",
                          }}
                        >
                          <input
                            type="text"
                            size={10}
                            ref={phoneNumS}
                            className="join__stepTwo-phonenum-input"
                            onChange={() => {
                              phoneNumValid(
                                phoneNumValidRef,
                                phoneNumF,
                                phoneNumS,
                                phoneNumT
                              );
                            }}
                          />
                        </div>
                        -
                        <div
                          className="join__stepTwo-phonenum-input-div"
                          style={{
                            display: "flex",
                            justifyContent: "space-between",
                          }}
                        >
                          <input
                            type="text"
                            size={20}
                            ref={phoneNumT}
                            className="join__stepTwo-phonenum-input"
                            onChange={() => {
                              phoneNumValid(
                                phoneNumValidRef,
                                phoneNumF,
                                phoneNumS,
                                phoneNumT
                              );
                            }}
                          />
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div
                  style={{ marginTop: "20px" }}
                  className="join__stepTwo-content-container"
                >
                  <SendEmail
                    emailIdRef={emailIdRef}
                    emailAddrRef={emailAddrRef}
                    emailValidRef={emailValidRef}
                    emailCk={emailCk}
                    setEmailCk={setEmailCk}
                    eId={eId}
                    setEId={setEId}
                    eAddr={eAddr}
                    setEAddr={setEAddr}
                  />
                </div>
                <div className="join__stepTwo-content-container">
                  <div>
                    <div>
                      <span style={{ fontSize: "13px" }}> 주소</span>
                      <span
                        className="join__stepTwo-keyup-validSpan"
                        ref={addrValidRef}
                      >
                        (주소 및 상세주소를 입력해주세요)
                      </span>
                    </div>
                    <div style={{ display: "flex" }}>
                      <div
                        className="join__stepTwo-input-common"
                        style={{ width: "100px" }}
                      >
                        <input
                          style={{ marginLeft: "3px" }}
                          type="text"
                          size={14}
                          placeholder="우편번호"
                          ref={zoneCodeRef}
                          value={zoneCode}
                        />
                      </div>
                      <span
                        className="postCodeFind-btn"
                        onClick={() => {
                          setIsOpenPost(!isOpenPost);
                        }}
                      >
                        우편번호 찾기
                      </span>
                    </div>
                  </div>

                  <div>
                    <div
                      className="join__stepTwo-input-common"
                      style={{ width: "100%" }}
                    >
                      <input
                        style={{ marginLeft: "3px" }}
                        type="text"
                        size={52}
                        placeholder="기본주소"
                        ref={addrRef}
                        value={address}
                      />
                    </div>
                  </div>
                  <div>
                    <div
                      className="join__stepTwo-input-common"
                      style={{ width: "100%" }}
                    >
                      <input
                        style={{ marginLeft: "3px" }}
                        type="text"
                        size={52}
                        placeholder="상세주소"
                        ref={addrDetailRef}
                      />
                    </div>
                  </div>
                </div>
                <div className="join__stepTwo-introduce">
                  <span style={{ fontSize: "13px" }}> 자기소개</span>
                  <div
                    className="join__stepTwo-introduce-div"
                    style={{ width: "100%" }}
                  >
                    <textarea
                      ref={introduceRef}
                      style={{
                        position: "absolute",
                        width: "98%",
                        height: "46px",
                        resize: "none",
                        border: "none",
                        outline: "none",
                      }}
                    ></textarea>
                  </div>
                </div>
              </div>
            </Body>

            <div className="join__next-btn-container">
              <span
                className="join__next-btn"
                onClick={() => {
                  navigate(-1);
                }}
              >
                이전
              </span>
              <span
                className="join__next-btn"
                onClick={() => {
                  validation(
                    nameRef,
                    sJoominRef,
                    joominGender,
                    phoneNumF,
                    phoneNumS,
                    phoneNumT,
                    emailIdRef,
                    emailAddrRef,
                    zoneCodeRef,
                    addrRef,
                    addrDetailRef,
                    emailCk,
                    introduceRef,
                    dispatch,
                    imageFile,
                    imgRef,
                    navigate
                  );
                }}
              >
                다음
              </span>
            </div>
            {isOpenPost && (
              <AddresApi
                isOpenPost={isOpenPost}
                setIsOpenPost={setIsOpenPost}
                setAddress={setAddress}
                setZoneCode={setZoneCode}
              />
            )}
            <Footer>
              <Copyright />
            </Footer>
          </ContentStep2>
        </div>
      </Container>
    </div>
  );
};

const SendEmail = ({
  emailIdRef,
  emailAddrRef,
  emailValidRef,
  emailCk,
  setEmailCk,
  eId,
  setEId,
  eAddr,
  setEAddr,
}) => {
  const formRef = useRef();
  const userVNumRef = useRef();
  const sendEmail = (e) => {
    e.preventDefault();
    emailjs
      .sendForm(
        "service_17j8i9s",
        "template_pn5zcvu",
        formRef.current,
        "Zhz2yYsd_9ndmdpMr"
      )
      .then(
        (result) => {
          console.log(result.text);
        },
        (error) => {
          console.log(error.text);
        }
      );
  };
  let number = Math.floor(Math.random() * 1000000) + 100000;
  if (number > 1000000) number = number - 100000;

  return (
    <>
      <div>
        <div style={{ display: "flex" }}>
          <span style={{ fontSize: "13px" }}>이메일</span>{" "}
          <span className="join__stepTwo-keyup-validSpan" ref={emailValidRef}>
            (이메일을 입력해주세요)
          </span>
        </div>
        <div
          style={{
            display: "flex",
            gap: "0.5rem",
          }}
        >
          <div className="join__stepTwo-email-input-div">
            <input
              style={{ fontSize: "15px", marginLeft: "3px" }}
              type="text"
              size={12}
              placeholder=""
              ref={emailIdRef}
              value={eId}
              onChange={(e) => {
                emailValid(emailValidRef, emailIdRef, emailAddrRef);

                setEId(e.target.value);
              }}
            />
          </div>
          <span>@</span>
          <div className="join__stepTwo-email-input-div">
            <input
              style={{ fontSize: "15px", marginLeft: "3px" }}
              type="text"
              size={12}
              placeholder=""
              ref={emailAddrRef}
              value={eAddr}
              onChange={(e) => {
                emailValid(emailValidRef, emailIdRef, emailAddrRef);
                setEAddr(e.target.value);
              }}
            />
          </div>
          <div style={{ display: "flex" }}>
            <div className="join__stepTwo-email-selector">
              <select
                onChange={(e) => {
                  if (e.target.value === "직접입력") {
                    emailValidRef.current.style.visibility = "visible";
                    emailAddrRef.current.parentElement.style.borderColor =
                      "grey";
                    emailAddrRef.current.value = "";
                  } else {
                    emailValidRef.current.style.visibility = "hidden";
                    emailAddrRef.current.parentElement.style.borderColor =
                      "yellowGreen";
                  }
                  emailAddrRef.current.value = e.target.value;
                }}
              >
                <option>직접입력</option>
                <option>naver.com</option>
                <option>daum.net</option>
                <option>nate.com</option>
                <option>google.com</option>
              </select>
            </div>
          </div>
        </div>
      </div>
      <div style={{ display: "flex", gap: ".5rem" }}>
        <form ref={formRef} onSubmit={sendEmail} className="email__form">
          <input
            type="email"
            name="user_email"
            // value={`${emailIdRef.current.value}@${emailAddrRef.current.value}`}
            value={eId + "@" + eAddr}
            // value="kkm0938@naver.com"
            style={{ display: "none" }}
          />
          <textarea name="message" value={number} style={{ display: "none" }} />
          <button
            type="submit"
            className="emailValid-transfer-btn"
            onClick={(e) => {
              e.stopPropagation();
              if (emailIdRef.current.value.trim().length === 0) {
                e.preventDefault();
                emailIdRef.current.parentElement.classList.add("validation");
                setTimeout(() => {
                  emailIdRef.current.parentElement.classList.remove(
                    "validation"
                  );
                }, 1100);
              } else if (emailAddrRef.current.value.trim().length === 0) {
                e.preventDefault();
                emailAddrRef.current.parentElement.classList.add("validation");
                setTimeout(() => {
                  emailAddrRef.current.parentElement.classList.remove(
                    "validation"
                  );
                }, 1100);
              }
              // console.log(emailIdRef.current.value,emailAddrRef);
            }}
          >
            인증번호 전송
          </button>
        </form>
        <div
          className="join__stepTwo-input-div"
          style={{
            display: "flex",
            justifyContent: "space-between",
          }}
        >
          <input
            style={{ fontSize: "13px", marginLeft: "3px" }}
            type="text"
            size={20}
            ref={userVNumRef}
            onChange={(e) => {
              userVNumRef.current.value = e.target.value;
            }}
          />
          <span
            className="emailValid-confirm-btn"
            onClick={() => {
              if (parseInt(userVNumRef.current.value) === number) {
                setEmailCk(true);
              }
            }}
          >
            {emailCk ? <FontAwesomeIcon icon={faCheck} /> : "확인"}
          </span>
        </div>
      </div>
    </>
  );
};

const addImage = (e, imgRef) => {
  // console.log("e.target.files", e.target.files);
  // const nowImageURL = URL.createObjectURL(e.target.files[0]);
  let formData = new FormData();
  formData.append("userprofile", e.target.files[0]);
  return formData;
};

const AddresApi = ({ setIsOpenPost, setAddress, setZoneCode }) => {
  const onCompletePost = (data) => {
    let fullAddr = data.address;
    let extraAddr = "";
    if (data.addressType === "R") {
      if (data.bname !== "") {
        extraAddr += data.bname;
      }
      if (data.buildingName !== "") {
        extraAddr +=
          extraAddr !== "" ? `, ${data.buildingName}` : data.buildingName;
      }
      fullAddr += extraAddr !== "" ? ` (${extraAddr})` : "";
    }
    setIsOpenPost(false);
    setAddress(fullAddr);
    setZoneCode(data.zonecode);
  };
  const postCodeStyle = {
    display: "block",
    position: "absolute",
    top: "0",
    right: "-420px",
    width: "400px",
    height: "500px",
    borderRadius: "8px",
    border: "1px solid grey",
    padding: "3px",
  };

  return (
    <div>
      <DaumPostcode
        style={postCodeStyle}
        className="daumPostCode"
        autoClose
        onComplete={onCompletePost}
      />
    </div>
  );
};

function nameValid(nameValidRef, nameRef) {
  if (nameRef.current.value.trim().length === 0) {
    nameValidRef.current.style.visibility = "visible";
    nameRef.current.parentElement.style.borderColor = "grey";
  } else if (nameRef.current.value.trim().length >= 2) {
    nameValidRef.current.style.visibility = "hidden";
    nameRef.current.parentElement.style.borderColor = "yellowGreen";
  }
}

function joominValid(joominValidRef, sJoominRef, joominGender) {
  if (sJoominRef.current.value.trim().length === 0) {
    joominValidRef.current.style.visibility = "visible";
    sJoominRef.current.parentElement.style.borderColor = "grey";
  } else if (sJoominRef.current.value.trim().length >= 6) {
    joominValidRef.current.style.visibility = "hidden";
    sJoominRef.current.parentElement.style.borderColor = "yellowGreen";
  }
  if (joominGender.current.value.trim().length === 0) {
    joominValidRef.current.style.visibility = "visible";
    joominGender.current.parentElement.style.borderColor = "grey";
  } else {
    joominValidRef.current.style.visibility = "hidden";
    joominGender.current.style.borderColor = "yellowGreen";
  }
}

function phoneNumValid(phoneNumValidRef, phoneNumF, phoneNumS, phoneNumT) {
  if (
    phoneNumF.current.value.trim().length === 0 ||
    phoneNumF.current.value.trim().length < 3
  ) {
    phoneNumValidRef.current.style.visibility = "visible";
    phoneNumF.current.parentElement.style.borderColor = "grey";
  } else {
    phoneNumValidRef.current.style.visibility = "hidden";
    phoneNumF.current.parentElement.style.borderColor = "yellowGreen";
  }
  if (
    phoneNumS.current.value.trim().length === 0 ||
    phoneNumS.current.value.trim().length < 4
  ) {
    phoneNumValidRef.current.style.visibility = "visible";
    phoneNumS.current.parentElement.style.borderColor = "grey";
  } else {
    phoneNumValidRef.current.style.visibility = "hidden";
    phoneNumS.current.parentElement.style.borderColor = "yellowGreen";
  }
  if (
    phoneNumT.current.value.trim().length === 0 ||
    phoneNumT.current.value.trim().length < 4
  ) {
    phoneNumValidRef.current.style.visibility = "visible";
    phoneNumT.current.parentElement.style.borderColor = "grey";
  } else {
    phoneNumValidRef.current.style.visibility = "hidden";
    phoneNumT.current.parentElement.style.borderColor = "yellowGreen";
  }
}

function emailValid(emailValidRef, emailIdRef, emailAddrRef) {
  if (emailIdRef.current.value.trim().length === 0) {
    emailValidRef.current.style.visibility = "visible";
    emailIdRef.current.parentElement.style.borderColor = "grey";
  } else {
    emailValidRef.current.style.visibility = "hidden";
    emailIdRef.current.parentElement.style.borderColor = "yellowGreen";
  }
  if (emailAddrRef.current.value.trim().length === 0) {
    emailValidRef.current.style.visibility = "visible";
    emailAddrRef.current.parentElement.style.borderColor = "grey";
  } else {
    emailValidRef.current.style.visibility = "hidden";
    emailAddrRef.current.parentElement.style.borderColor = "yellowGreen";
  }
}

function validation(
  nameRef,
  sJoominRef,
  joominGender,
  phoneNumF,
  phoneNumS,
  phoneNumT,
  emailIdRef,
  emailAddrRef,
  zoneCodeRef,
  addrRef,
  addrDetailRef,
  emailCk,
  introduceRef,
  dispatch,
  imageFile,
  imgRef,
  navigate
) {
  if (nameRef.current.value.trim().length === 0) {
    nameRef.current.parentElement.classList.add("validation");
    setTimeout(() => {
      nameRef.current.parentElement.classList.remove("validation");
    }, 1100);
  } else if (sJoominRef.current.value.trim().length === 0) {
    sJoominRef.current.parentElement.classList.add("validation");
    setTimeout(() => {
      sJoominRef.current.parentElement.classList.remove("validation");
    }, 1100);
  } else if (joominGender.current.value.trim().length === 0) {
    joominGender.current.parentElement.classList.add("validation");
    setTimeout(() => {
      sJoominRef.current.parentElement.classList.remove("validation");
    }, 1100);
  } else if (phoneNumF.current.value.trim().length === 0) {
    phoneNumF.current.parentElement.classList.add("validation");
    setTimeout(() => {
      phoneNumF.current.parentElement.classList.remove("validation");
    }, 1100);
  } else if (phoneNumS.current.value.trim().length === 0) {
    phoneNumS.current.parentElement.classList.add("validation");
    setTimeout(() => {
      phoneNumS.current.parentElement.classList.remove("validation");
    }, 1100);
  } else if (phoneNumT.current.value.trim().length === 0) {
    phoneNumT.current.parentElement.classList.add("validation");
    setTimeout(() => {
      phoneNumT.current.parentElement.classList.remove("validation");
    }, 1100);
  } else if (emailIdRef.current.value.trim().length === 0) {
    emailIdRef.current.parentElement.classList.add("validation");
    setTimeout(() => {
      emailIdRef.current.parentElement.classList.remove("validation");
    }, 1100);
  } else if (emailAddrRef.current.value.trim().length === 0) {
    emailAddrRef.current.parentElement.classList.add("validation");
    setTimeout(() => {
      emailAddrRef.current.parentElement.classList.remove("validation");
    }, 1100);
  } else if (
    zoneCodeRef.current.value.trim().length === 0 ||
    addrRef.current.value.trim().length === 0
  ) {
    zoneCodeRef.current.parentElement.classList.add("validation");
    addrRef.current.parentElement.classList.add("validation");
    setTimeout(() => {
      zoneCodeRef.current.parentElement.classList.remove("validation");
      addrRef.current.parentElement.classList.remove("validation");
    }, 1100);
  } else if (addrDetailRef.current.value.trim().length === 0) {
    addrDetailRef.current.parentElement.classList.add("validation");
    setTimeout(() => {
      addrDetailRef.current.parentElement.classList.remove("validation");
    }, 1100);
  } else {
    let regRex = new RegExp("[a-z]*.[a-z]+", "g");
    if (sJoominRef.current.value.trim().length < 6) {
      alert("주민등록번호를 형식에 맞게 입력해주세요");
      return;
    } else if (!regRex.test(emailAddrRef)) {
      // alert("이메일 형식에 맞게 입력해주세요");
    } else if (!emailCk) {
      alert("이메일 인증을 해주세요");
    } else {
      //////////////
      let gender;
      switch (joominGender.current.value) {
        case 1:
          gender = "남자";
          break;
        default:
          gender = "여자";
          break;
      }
      let phoneNum = `${phoneNumF.current.value}-${phoneNumS.current.value}-${phoneNumT.current.value}`;
      let email = `${emailIdRef.current.value}@${emailAddrRef.current.value}`;
      let addr = `${zoneCodeRef.current.value}`;
      console.log("imageFile", imageFile);
      dispatch(
        addStepTwo([
          nameRef.current.value,
          sJoominRef.current.value,
          gender,
          phoneNum,
          email,
          addr,
          introduceRef.current.value,
          imgRef.current.files[0],
        ])
      );

      navigate("/joinThree");
    }
  }
}

function Copyright(props) {
  return (
    <Typography variant="body2" color="text.warning" align="center" {...props}>
      {"Copyright © "}
      <Link
        color="inherit"
        to="https://localhost:3000/"
        style={{ color: "var(--orange)" }}
      >
        AAMU
      </Link>{" "}
      {new Date().getFullYear()}
      {"."}
    </Typography>
  );
}

export default JoinStep2;
