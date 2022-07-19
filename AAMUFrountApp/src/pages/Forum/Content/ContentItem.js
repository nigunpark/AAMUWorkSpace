import { Rating, Typography } from "@mui/material";
import { Link } from "react-router-dom";
import React, { useState } from "react";
import DetailModal from "../DetailModal/DetailModal";


const ContentItem = ({dummy, keys }) => {

    // console.log("ContentItem dummy :",dummy.reviewdata);
    // console.log("keys : ",keys);

    const [isOpen, setIsOpen] = useState(false);

    const onClickModal = () =>{
        setIsOpen(true);
    };

    //목록에서 보여줄 평균 별점
    let add=0;
    dummy.reviewdata.forEach((obj,i)=>{
            // console.log('11',typeof Number(obj.star))
            add+=Number(obj.star)
    });
    add = Math.round((add/dummy.reviewdata.length)*10)/10;

    return (
        <>
        <li className="card__item_minCon" onClick={onClickModal}>
            <div className="card__item__link_minCon">
                <figure className="card__item__info_minCon">
                    <div className="card__item__img-container_minCon">
                        <img
                            src={dummy.imgsdata[0].imgs}
                            alt="카드이미지"
                            className="card__item__img_minCon"
                        />
                    </div>
                    <div className="card__item__rating_minCon">
                        <img src='/images/star.jpg' style={{width:'30px'}}/>{add}
                        <span className="idSpan_minCon" style={{marginLeft: 'auto'}}>{dummy.id}님의 plan</span>
                    </div>
                    <div className="card__item__content_minCon">
                        <h3>{dummy.title}</h3> {/* 제목 */}

                        <p style={{fontSize: '12.5px', color: '#8e8e8e'}}>#tag</p>
                    </div>
                </figure>
            </div>
            
        </li>
        {isOpen == true ? (
            <DetailModal
            setIsOpen={setIsOpen}
            dummy={dummy}
            />
        ) : null}
        
    </>
    
    )
}

export default ContentItem