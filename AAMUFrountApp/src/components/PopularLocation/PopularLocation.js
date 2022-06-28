import React, { useRef } from "react";
import { Link, useNavigate } from "react-router-dom";
import "./PopularLocation.css";
import { changeLnfM } from "../../redux/store";
import { useDispatch } from "react-redux";
const PopularLocation = () => {
  let localRef = useRef("");
  let navigate = useNavigate();
  let dispatch = useDispatch();
  return (
    <li
      className="PopularLocation__li"
      onClick={() => {
        dispatch(changeLnfM(localRef.current.textContent));
        navigate("/mainPage/" + localRef.current.textContent);
      }}>
      <div>
        <span className="PopularLocation__rank">1</span>
        <span className="title" ref={localRef}>
          춘천
        </span>
      </div>
      <div>
        <i className="fa-solid fa-sort-up" />
        <span className="count">2</span>
      </div>
    </li>
  );
};

export default PopularLocation;
