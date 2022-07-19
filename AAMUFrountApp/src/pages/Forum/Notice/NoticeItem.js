import React from 'react'
import { Link } from "react-router-dom";

const NoticeItem = ({picture}) => {
  // console.log('props.src : ',props.src);

  // console.log('picture :', picture);

  
  return (
    <>
      <li className="swiper__item_Notice">
        <div className="swiper__item__link_Notice">
          <figure className="swiper__item__pic-wrap_Notice">
            <img
              src={picture}
              alt="travel image"
              className="swiper__item__img_Notice"
            />
          </figure>
        </div>
      </li>
    </>
  )
}

export default NoticeItem