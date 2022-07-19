import React, { useEffect, useRef, useState } from 'react';
import styled from 'styled-components';

const TOTAL_SLIDES = 2; // 전체 슬라이드 개수(총3개. 배열로 계산)

export default function Slider() {
  const [currentSlide, setCurrentSlide] = useState(0);
  const slideRef = useRef(null);

  // Next 버튼 클릭 시
  const NextSlide = () => {
    if (currentSlide >= TOTAL_SLIDES) {
      // 더 이상 넘어갈 슬라이드가 없으면
      setCurrentSlide(0); // 1번째 사진으로 넘어갑니다.
      // return;  // 클릭이 작동하지 않습니다.
    } else {
      setCurrentSlide(currentSlide + 1);
    }
  };
  // Prev 버튼 클릭 시
  const PrevSlide = () => {
    if (currentSlide === 0) {
      setCurrentSlide(TOTAL_SLIDES); // 마지막 사진으로 넘어갑니다.
      // return;  // 클릭이 작동하지 않습니다.
    } else {
      setCurrentSlide(currentSlide - 1);
    }
  };
  const [myImage,setMyImage] = useState([]);
  const [myImagefile,setMyImageFile] = useState([]);

  const addImage = e => {
    const nowSelectImageList = e.target.files;//한번에 받은 파일 리스트(object)
    const nowImageURLList = [...myImage];//현재 myImage복사하고
    for (let i = 0; i < nowSelectImageList.length; i += 1){
      //nowSelectImageList object를 i를 이용해서 돌리면서
      const nowImageURL = URL.createObjectURL(nowSelectImageList[i]);
      //미리보기 가능하게 변수화
      nowImageURLList.push(nowImageURL);
      //복사한 myImage에 추가
    }
    setMyImage(nowImageURLList);
    setMyImageFile(nowSelectImageList);

    //myImage원본에 덮어씌우기
  }
  useEffect(() => {
    slideRef.current.style.transition = 'all 0.5s ease-in-out';
    slideRef.current.style.transform = `translateX(-${currentSlide}00%)`; // 백틱을 사용하여 슬라이드로 이동하는 에니메이션을 만듭니다.
  }, [currentSlide]);

  return (
    <Container>
      <SliderContainer ref={slideRef} onChange={addImage}>
        <div>
        {myImage.map((images,i)=>(
            <div className='divimage'>
            <IMG  alt="sample" src={images}/>
            </div>
        ))}
        </div>
      </SliderContainer>
      <Center>
        <Button onClick={PrevSlide}>Prev</Button>
        <Button onClick={NextSlide}>Next</Button>
      </Center>
    </Container>
  );
}
const Container = styled.div`
  width: 100%;
  height: 100%;
  overflow: hidden; // 선을 넘어간 이미지들은 숨겨줍니다.
`;
const Button = styled.div`
  all: unset;
  padding: 1em 2em;
  margin: 2em 2em;
  color: burlywood;
  border-radius: 10px;
  border: 1px solid burlywood;
  cursor: pointer;
  &:hover {
    background-color: burlywood;
    color: #fff;
  }
`;
const SliderContainer = styled.div`
  margin: 0 auto;
  margin-bottom: 2em;
  display: flex; // 이미지들을 가로로 나열합니다.
`;
const Text = styled.div`
  text-align: center;
  color: burlywood;
  p {
    color: #fff;
    font-size: 20px;
    background-color: burlywood;
    display: inline-block;
    border-radius: 50px;
    padding: 0.5em 1em;
  }
`;
const Center = styled.div`
  text-align: center;
`;
const IMG = styled.img`
  width: 500px;
  height: 500px;
`;