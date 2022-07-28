import React from 'react'
import { Link } from "react-router-dom";
//더미로 {picture} 를 넘겨받았었음
const NoticeItem = () => {
  // console.log('props.src : ',props.src);

  // console.log('picture :', picture);

  
  return (
    <>
      <li className="swiper__item_Notice">
        <div className="swiper__item__link_Notice">
          <figure className="swiper__item__pic-wrap_Notice">
            <img
              src='/images/img-1.jpg'
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