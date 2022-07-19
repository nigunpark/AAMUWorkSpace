import React from 'react'
import { Link } from "react-router-dom";

const NoticeItem = ({imgdata}) => {
  // console.log('props.src : ',props.src);

  console.log('imgdata :', imgdata);

  
  return (
    <>
      <li className="swiper__item_Notice">
        <Link to='/' className="swiper__item__link_Notice">
          <figure
            className="swiper__item__pic-wrap_Notice"
            // data-category={props.label}
          >
            <img
              // src={props.src}
              alt="travel image"
              className="swiper__item__img_Notice"
            />
          </figure>
          {/* <div className="swiper__item__info_Notice">
            <h5 className="swiper__item__text_Notice">{props.text}</h5>
          </div> */}
        </Link>
      </li>
    </>
  )
}

export default NoticeItem