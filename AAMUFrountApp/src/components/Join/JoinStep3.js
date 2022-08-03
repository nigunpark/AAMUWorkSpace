import React, { useEffect, useRef, useState } from "react";
import { Container, Content, Title, Body, Footer, ContentStep3 } from "../Modal/ForJoin.js";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faFolderOpen } from "@fortawesome/free-regular-svg-icons";
import "./Join.css";
import { Typography } from "@mui/material";
import { Link, useNavigate } from "react-router-dom";
import { fa1, fa2, fa3, faCheck, faFolderPlus, faHouse } from "@fortawesome/free-solid-svg-icons";
import axios from "axios";
import { useDispatch, useSelector } from "react-redux";
import { addStepOne } from "../../redux/store.js";
import styled from "styled-components";
const JoinStep3 = () => {
  const forSlideRef = useRef();
  let navigate = useNavigate();
  let dispatch = useDispatch();
  let reduxState = useSelector((state) => {
    return state;
  });
  console.log("reduxState.joinData", reduxState.joinData);
  let checkBoxRef = useRef();
  const [themes, setThemes] = useState([]);
  const [checkeds, setCheckeds] = useState([]);
  const getThemes = async () => {
    let token = sessionStorage.getItem("token");
    await axios
      .get("/aamurest/users/theme", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        console.log(resp.data);
        setThemes(resp.data);
      })
      .catch((err) => {
        console.log("err", err);
      });
  };

  const getCheckedBoxes = (checked, val) => {
    if (checked) setCheckeds([...checkeds, val]);
    else setCheckeds(checkeds.filter((one) => one !== val));
  };
  const doCheck = (e) => {
    let checkbox = e.target.parentElement.nextSibling.lastChild;
    if (checkbox.checked) {
      checkbox.checked = false;
      e.target.parentElement.parentElement.style.background = "white";
      e.target.parentElement.parentElement.style.color = "black";
    } else {
      checkbox.checked = true;
      e.target.parentElement.parentElement.style.background = "var(--skyblue)";
      e.target.parentElement.parentElement.style.color = "white";
    }
  };
  const joinDone = () => {
    let temp = new FormData();
    temp.append("id", reduxState.joinData.id);
    temp.append("email", reduxState.joinData.email);
    temp.append("pwd", reduxState.joinData.pwd);
    temp.append("name", reduxState.joinData.name);
    temp.append("gender", reduxState.joinData.gender);
    temp.append("socialnum", parseInt(reduxState.joinData.socialnum));
    temp.append("phonenum", reduxState.joinData.phonenum);
    temp.append("addrid", reduxState.joinData.addrid);
    temp.append("self", reduxState.joinData.self);
    temp.append("userprofile", reduxState.joinData.userprofile);
    temp.append("theme", checkeds.join());
    let token = sessionStorage.getItem("token");
    axios
      .post("/aamurest/users/edit", temp, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "multipart/form-data",
        },
      })
      .then((resp) => {
        console.log(resp.data);
        if (resp.data === 1) {
          alert("가입이 완료되었습니다");
          navigate("/login");
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };
  useEffect(() => {
    getThemes();
  }, []);
  return (
    <div className="join__step-one">
      <Container>
        <div ref={forSlideRef} className="join__slide-in-left">
          <ContentStep3>
            <Link to="/">
              <FontAwesomeIcon className="home-btn" icon={faHouse} />
            </Link>
            <Title>
              <div className="loginTitle__container">
                <FontAwesomeIcon icon={faFolderOpen} className="joinStep3_title-i" />
                <h1>테마선택</h1>
              </div>
              <p style={{ color: "gray" }}>welcome to AAMU</p>
            </Title>
            <div className="join__progress-container">
              <FontAwesomeIcon icon={fa1} className="join__progress-icon " />
              -
              <FontAwesomeIcon icon={fa2} className="join__progress-icon" />-
              <FontAwesomeIcon icon={fa3} className="join__progress-icon join__progress-step" />
            </div>
            <CheckBoxBody>
              {themes.map((val, i) => {
                return (
                  <CheckBox
                    key={val.THEMEID}
                    onClick={(e) => {
                      e.stopPropagation();
                      doCheck(e);
                      getCheckedBoxes(
                        e.target.parentElement.nextSibling.lastChild.checked,
                        e.target.parentElement.nextSibling.lastChild.value
                      );
                    }}
                  >
                    <ImgCon>
                      <img
                        src={val.themeimg}
                        style={{
                          width: "100%",
                          height: "100%",
                          objectFit: "cover",
                          borderRadius: "5px",
                        }}
                      />
                    </ImgCon>
                    <div style={{ textAlign: "center", margin: "2px 0", fontSize: "14px" }}>
                      <label for={val.THEMENAME}>{val.THEMENAME}</label>
                      <input
                        type="checkbox"
                        id={val.THEMENAME}
                        value={val.THEMENAME}
                        onChange={(e) => {
                          e.stopPropagation();
                          getCheckedBoxes(e.target.checked, e.target.value);
                        }}
                        checked={checkeds.includes(val.THEMENAME) ? true : false}
                        hidden
                      />
                    </div>
                  </CheckBox>
                );
              })}
            </CheckBoxBody>
            {checkeds}
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
                  joinDone();
                }}
              >
                가입
              </span>
            </div>
            <Footer>
              <Copyright />
            </Footer>
          </ContentStep3>
        </div>
      </Container>
    </div>
  );
};

const CheckBoxBody = styled.div`
  position: relative;
  width: 100%;
  height: 500px;
  //   display: flex;
  //   flex-direction: column;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 5px;
  overflow-y: auto;
  &::-webkit-scrollbar {
    display: none;
  }
`;
const CheckBox = styled.div`
  //   border: 1px solid rgb(128, 128, 128);
  box-shadow: var(--lightShadow);
  border-radius: 5px;
  width: 100%;
  height: 190px;
  padding: 5px;
  transition: 0.1s;
  border: 2px solid white;
  font-weight: bold;
  &:hover {
    border: 2px solid var(--skyblue);
  }
`;
const ImgCon = styled.div`
  //   border: 1px solid red;
  width: 100%;
  height: 90%;
`;
function Copyright(props) {
  return (
    <Typography variant="body2" color="text.warning" align="center" {...props}>
      {"Copyright © "}
      <Link color="inherit" to="https://localhost:3000/" style={{ color: "var(--orange)" }}>
        AAMU
      </Link>{" "}
      {new Date().getFullYear()}
      {"."}
    </Typography>
  );
}
export default JoinStep3;
