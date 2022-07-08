import { Rating, Typography } from "@mui/material";
import { Link } from "react-router-dom";
import React, { useState } from "react";
import DetailModal from "../DetailModal/DetailModal";


const ContentItem = ({ i }) => {

    const [value, setValue] = useState(2.5);
    const [isOpen, setIsOpen] = useState(false);
    const onClickModal = () =>{
        setIsOpen(true);
    };

//<Link to="/review" className="card__item__link_minCon">
    return (
        <>
        <li className="card__item_minCon" onClick={onClickModal}>
            <div className="card__item__link_minCon">
                <figure className="card__item__info_minCon">
                    <div className="card__item__img-container_minCon">
                        <img
                            src={"/images/img-" + i + ".jpg"}
                            alt="카드이미지"
                            className="card__item__img_minCon"
                        />
                    </div>
                    <div className="card__item__rating_minCon">
                        <Rating
                            name="simple-controlled"
                            value={value}
                            precision={0.5}
                            onChange={(event, newValue) => {
                            setValue(newValue);
                            }}
                        />
                        <span className="idSpan_minCon">kim**58님의 plan</span>
                    </div>
                    <div className="card__item__content_minCon">
                        <h3>제주도 3박4일</h3>
                        <p>국시치고 가족과 함께 제주도 여행다녀왔어요</p>
                    </div>
                </figure>
            </div>
            
        </li>
        {isOpen == true ? (
            <DetailModal
            setIsOpen={setIsOpen}
            />
        ) : null}
    </>
    
    )
}

export default ContentItem