import React from "react";
import "./IntroPart.css";
import { useNavigate } from "react-router-dom";
const IntroPart = () => {
  let navigate = useNavigate();
  return (
    <div className="introPart-container">
      {/* <video src="/images/video/video2.mp4" autoPlay loop muted /> */}
      <h1>손끝에서 시작하는 당신의 여행</h1>
      <p>What are you waiting?</p>
      <div className="introPart-btn">
        <button
          onClick={() => {
            navigate("/WholeMap");
          }}
        >
          Come with us now!
        </button>
      </div>
    </div>
  );
};

export default IntroPart;
