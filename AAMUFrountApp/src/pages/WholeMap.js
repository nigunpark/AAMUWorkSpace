import React, { useState } from "react";
import "../components/WholeMap/WholeMap.css";
import WholeMapCoords from "../components/WholeMap/WholeMapCoords.js";
import WholeMapLocalData from "../components/WholeMap/WholeMapLocalData.js";
import {
  Container,
  Contents,
  Overlay,
  Title,
  Close,
  Body,
} from "../components/Modal/Modal.js";
import PopularLocation from "../components/PopularLocation/PopularLocation";
import { Link } from "react-router-dom";
import { changeLnfM } from "../redux/store";
import { useSelector, useDispatch } from "react-redux";

const WholeMap = () => {
  const [modalState, setModalState] = useState(false);
  const [localName, setLocalName] = useState("");
  const [localId, setLocalId] = useState("");
  let reduxState = useSelector((state) => {
    return state;
  });
  let dispatch = useDispatch();

  return (
    <div className="WholeMap">
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
  let dispatch = useDispatch();
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
              <img
                src={
                  process.env.PUBLIC_URL +
                  "/images/localImages/" +
                  localId +
                  ".png"
                }
              />
            </Link>
          </div>
          <ul className="local__modal__ul">
            <PopularLocation />
            <PopularLocation />
            <PopularLocation />
            <PopularLocation />
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
