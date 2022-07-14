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
                        {/* <Rating
                            name="simple-controlled"
                            value={value}
                            precision={0.5}
                            onChange={(event, newValue) => {
                            setValue(newValue);
                            }}
                        /> */}
                        <img src='/images/star.jpg' style={{width:'30px'}}/>4.8
                        <span className="idSpan_minCon" style={{marginLeft: 'auto'}}>kim**58님의 plan</span>
                    </div>
                    <div className="card__item__content_minCon">
                        <h3>제주도 3박4일</h3> {/* 제목 */}
                        <p style={{fontSize: '12.5px', color: '#8e8e8e'}}>#tag</p>
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