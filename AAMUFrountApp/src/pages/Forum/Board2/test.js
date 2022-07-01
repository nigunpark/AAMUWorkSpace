import React from 'react'
import './test.css'

const test = () => {
    const forMapTestArr = [1, 2, 3, 5, 6, 7];
  return (
    <div className="test_Cards_minCon">
            <div className="test_card__container_minCon">
                <div className="test_card__wrapper_minCon">
                    <h1 className="test_card__title_minCon">
                        테스트<span>!</span>
                    </h1>
                    
                    <p className="test_card__desc_minCon">
                        ㅇㅅㅇ
                        <span className="test_content-btn">
                            ㅇㅅㅇ2
                        </span>
                    </p>
                    
                    <div className="test_card__items_minCon">
                        <figure className="test_card__item__info_minCon">
                            <div className="test_card__item__img-container_minCon">
                                <img
                                    src='/images/imageMap.png'
                                    alt="카드이미지"
                                    className="test_card__item__img_minCon"
                                    />
                            </div>
                        </figure>
                    </div>

                </div>
            </div>
        </div>
  )
}

export default test