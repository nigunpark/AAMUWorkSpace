import React, { useEffect, useState } from "react";
import "../components/WholeMap/WholeMap.css";
import WholeMapCoords from "../components/WholeMap/WholeMapCoords.js";
import WholeMapLocalData from "../components/WholeMap/WholeMapLocalData.js";
import { Container, Contents, Overlay, Title, Close, Body } from "../components/Modal/Modal.js";
import PopularLocation from "../components/PopularLocation/PopularLocation";
import { Link, useNavigate } from "react-router-dom";
import { changeLnfM } from "../redux/store";
import { useSelector, useDispatch } from "react-redux";
import axios from "axios";

const WholeMap = () => {
  const [modalState, setModalState] = useState(false);
  const [localName, setLocalName] = useState("");
  const [localId, setLocalId] = useState("");
  let reduxState = useSelector((state) => {
    return state;
  });
  let dispatch = useDispatch();
  console.log("localId", localId);
  console.log("localName", localName);
  return (
    <div className="WholeMap">
      {/* <video src="/images/video/video.mp4" muted loop autoPlay /> */}
      <div className="test__container">
        <div className="wholeMap__img__container">
          <img
            className="wholeMap__img"
            src={process.env.PUBLIC_URL + "/images/koreaMap.png"}
            alt="지도이미지"
            useMap="#koreaMap"
          />
          {WholeMapLocalData.map((data, i) => {
            return (
              <WholeMapLocalName
                data={data}
                modalState={modalState}
                setModalState={setModalState}
                setLocalName={setLocalName}
                setLocalId={setLocalId}
                dispatch={dispatch}
                key={i}
              />
            );
          })}
        </div>

        <map name="koreaMap">
          <WholeMapCoords />
        </map>
      </div>

      <div className="wholeMap__modal">
        {modalState ? (
          <Modal
            setModalState={setModalState}
            localName={localName}
            localId={localId}
            reduxState={reduxState}
          />
        ) : null}
      </div>
    </div>
  );
};

//모달창
function Modal({ setModalState, localName, localId }) {
  const [recomLocals, setRecomLocals] = useState([]);
  async function getRecommendLocal(localName) {
    let token = sessionStorage.getItem("token");
    let areacode;
    switch (localName) {
      case "서울":
        areacode = 1;
        break;
      case "인천":
        areacode = 2;
        break;
      case "대전":
        areacode = 3;
        break;
      case "대구":
        areacode = 4;
        break;
      case "광주광역시":
        areacode = 5;
        break;
      case "부산":
        areacode = 6;
        break;
      case "울산":
        areacode = 7;
        break;
      case "세종":
        areacode = 8;
        break;
      case "경기도":
        areacode = 31;
        break;
      case "강원도":
        areacode = 32;
        break;
      case "충청북도":
        areacode = 33;
        break;
      case "충청남도":
        areacode = 34;
        break;
      case "경상북도":
        areacode = 35;
        break;
      case "경상남도":
        areacode = 36;
        break;
      case "전라북도":
        areacode = 37;
        break;
      case "전라남도":
        areacode = 38;
        break;
      case "제주도":
        areacode = 39;
        break;
    }
    await axios
      .get("/aamurest/main/area", {
        params: {
          areacode: areacode,
        },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((resp) => {
        console.log(resp);
        setRecomLocals(resp.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }
  useEffect(() => {
    getRecommendLocal(localName);
  }, []);
  return (
    <Container>
      <Overlay
        onClick={() => {
          setModalState(false);
        }}
      />
      <Contents>
        <Title>
          <span className="local__modal__span">{localName}</span>의 추천 여행지
          <Close onClick={() => setModalState(false)}>X</Close>
        </Title>
        <Body>
          <div className="local__modal__img">
            <Link to={`/mainPage/` + localName}>
              <img src={process.env.PUBLIC_URL + "/images/localImages/" + localId + ".png"} />
            </Link>
          </div>
          <ul className="local__modal__ul">
            {recomLocals.map((val, i) => {
              return <PopularLocation val={val} key={i} i={i} />;
            })}
            {/* <PopularLocation />
            <PopularLocation />
            <PopularLocation /> */}
          </ul>
        </Body>
      </Contents>
    </Container>
  );
}

function WholeMapLocalName({
  modalState,
  setModalState,
  setLocalName,
  setLocalId,
  dispatch,
  data,
}) {
  return (
    <span
      className={"local " + data.id}
      id={data.id}
      onClick={() => {
        setLocalName(data.localName);
        setModalState(!modalState);
        setLocalId(data.id);
        dispatch(changeLnfM(data.localName));
      }}
    >
      {data.localName}
    </span>
  );
}

export default WholeMap;
