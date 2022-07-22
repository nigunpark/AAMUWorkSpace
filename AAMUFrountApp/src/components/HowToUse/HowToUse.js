import React from "react";
import "./HowToUse.css";
const HowToUse = () => {
  return (
    <div className="HowToUse">
      <div className="howToUse__container">
        <h1 className="howToUse__title">
          이렇게 사용하세요 <span>!</span>
        </h1>
        <p className="howToUse__desc">손가락 끝으로 여행계획을 세우자</p>
        <div className="howToUse__wrapper">
          <iframe
            src="/images/video/howTo.mp4"
            style={{ width: "100%", height: "100%" }}
            allow="autoplay; encrypted-media"
            allowFullScreen
          ></iframe>
        </div>
      </div>
    </div>
  );
};

export default HowToUse;
