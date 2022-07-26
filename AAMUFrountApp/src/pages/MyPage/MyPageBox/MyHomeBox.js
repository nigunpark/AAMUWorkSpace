import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useState } from 'react'
import styled from 'styled-components';
import { faRectangleXmark } from "@fortawesome/free-solid-svg-icons";
import CreatePlanLeft from '../../../components/CreatePlan/CreatePlanLeft';
import CreatePlanMap from '../../../components/CreatePlan/CreatePlanMap';
import { useParams } from 'react-router-dom';

const MyHomeBox = ({setClickTab, planList}) => {

    const [showCreatePlan, setShowCratePlan] = useState(false);
    const [fromWooJaeData, setFromWooJaeData] = useState([]);
    let { currPosition } = useParams();

    // console.log('잘 넘어왔나 :',planList);

    const [isOpen, setIsOpen] = useState(false);

    const postDate = new Date(planList.routeDate);

    function dateFormat(date) {
        let month = date.getMonth() + 1;
        let day = date.getDate();

        month = month >= 10 ? month : '0' + month;
        day = day >= 10 ? day : '0' + day;

        return date.getFullYear() + '-' + month + '-' + day;
    };

    const onClickModal = () =>{
        setIsOpen(true);
    };

    
    // fee4cb ffd6ff d6f6dd
    
    // transform: scale(1.07);
    // project-box-wrapper 이거 css 703번 줄에 있음 &-wrapper 으로 검색
  return (
    <div className="project-box-wrapper" >{/* style={{backgroundColor: '#fee4cb'}} */}
        <div className="project-box">
            {/* <div className="project-box-header">
                <div className="more-wrapper">
                    <button className="project-btn-more">
                        <svg 
                        xmlns="http://www.w3.org/2000/svg" 
                        width="24" height="24" 
                        viewBox="0 0 24 24" fill="none" 
                        stroke="currentColor" stroke-width="2" 
                        stroke-linecap="round" stroke-linejoin="round"
                        className="feather feather-more-vertical">
                            <circle cx="12" cy="12" r="1" />
                            <circle cx="12" cy="5" r="1" />
                            <circle cx="12" cy="19" r="1" />
                        </svg>
                    </button>
                </div>
            </div> */}

            <div className="project-box-content-header">
                {/* '/images/img-'+imgNum+'.jpg' */}
                <img className='MapImgSize' src={planList.smallImage} style={{marginTop:'10px'}}/>
                {/* 저장한 경로 */}
            </div>

            <div className="box-progress-wrapper">
                <p className="box-progress-header" style={{marginTop:'10px',marginBottom:'10px'}}>
                    여행 제목 : {planList.title}
                </p>
                <p className="box-progress-header" style={{marginBottom:'10px'}}>
                    저장 날자 : {dateFormat(postDate)}
                </p>
                <p className="box-progress-header">
                    {planList.plannerdate} (rbn :{planList.rbn})
                </p>
            </div>

            <div className="project-box-footer" style={{marginTop:'5px'}}>
                <div className='detail-button'>
                    <button className="learn-more" type="button" onClick={()=>{setClickTab(10)}}>공유하기</button>
                </div>
                <div className='detail-button'>
                    <button className="learn-more" type="button" style={{marginTop:'20px'}} onClick={onClickModal}>일정보기</button>
                </div>
            </div>
        </div>
        {isOpen == true ? (
            <MyDetailPlan
            setIsOpen={setIsOpen}
            setShowCratePlan={setShowCratePlan}
            currPosition={currPosition}
            fromWooJaeData={fromWooJaeData}
            setFromWooJaeData={setFromWooJaeData}
            />
        ) : null}
    </div>
  )
}

function MyDetailPlan({
        setIsOpen,
        setShowCratePlan,
        currPosition,
        fromWooJaeData,
        setFromWooJaeData}){

    const [auSure, setAuSure] = useState(false);
    const [savePlan, setSavePlan] = useState(false);
    const [forDayLine, setForDayLine] = useState(0);
    
    
    return(
        <DimmedContainer>
            <Modal>
                <TitleBar>
                    AAMU
                    <CloseBtn
                        onClick={() => {
                            setAuSure(true);
                        }}
                        >
                        <FontAwesomeIcon icon={faRectangleXmark} />
                    </CloseBtn>
                </TitleBar>
                <Contents>
                    <CreatePlanLeft
                    currPosition={currPosition}
                    fromWooJaeData={fromWooJaeData}
                    setFromWooJaeData={setFromWooJaeData}
                    setForDayLine={setForDayLine}
                    />
                    {auSure && (
                    <AuSureModal
                        setIsOpen={setIsOpen}
                        // setShowCratePlan={setShowCratePlan}
                        setAuSure={setAuSure}
                    />
                    )}
                    <CreatePlanMap
                    setSavePlan={setSavePlan}
                    currPosition={currPosition}
                    fromWooJaeData={fromWooJaeData}
                    forDayLine={forDayLine}
                    />
                </Contents>
            </Modal>
            {/* {savePlan && (
            <SavePlan
                auSure={auSure}
                setAuSure={setAuSure}
                setSavePlan={setSavePlan}
                fromWooJaeData={fromWooJaeData}
                currPosition={currPosition}
            />
            )} */}
        </DimmedContainer>
    )
}

const AuSureModal = ({ setIsOpen, setAuSure }) => {
    return (
      <DimmedSavePlanContainer>
        <AuSModal2>
          <h4>현재 창을 닫으시면 일정이 저장되지 않습니다.</h4>
          <h4> 창을 닫으시겠습니까?</h4>
          <AusBtnContainer>
            <AuSBtn
              onClick={() => {
                setIsOpen(false);
              }}
            >
              확인
            </AuSBtn>
            <AuSBtn
              onClick={() => {
                setAuSure(false);
              }}
            >
              취소
            </AuSBtn>
          </AusBtnContainer>
        </AuSModal2>
      </DimmedSavePlanContainer>
    );
  };

const DimmedContainer = styled.div`
    position: fixed;
    width: 100%;
    height: 100%;
    z-index: 1000;
    top: 0;
    left: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: rgba(0, 0, 0, 0.6);
`
const Modal = styled.div`
    position: relative;
    background: white;
    width: 98%;
    height: 97%;
`
const TitleBar = styled.div`
    text-align: center;
    background: inherit;
    box-shadow: var(--shadow);
    height: 3%;
    width: 100%;
`
const CloseBtn = styled.div`
    position: absolute;
    margin-right: 10px;
    right: 0;
    top: 0;
    padding: 0 3px;
    width: auto;
    height: auto;
    cursor: pointer;
`
const Contents = styled.div`
  position: absolute;
  display: grid;
  grid-template-columns: 350px auto;
  padding: 3px;
  width: 100%;
  height: 97%;
`
const DimmedSavePlanContainer = styled.div`
  position: fixed;
  width: 100%;
  height: 100%;
  z-index: 1227;
  top: 0;
  left: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.6);
`
const AuSModal2 = styled.div`
  position: absolute;
  background: white;
  width: 400px;
  height: 155px;
  padding: 30px 30px;
  border-radius: 5px;
`
const AusBtnContainer = styled.div`
  position: absolute;
  display: flex;
  justify-self: center;
  gap: 1rem;
  margin-top: 15px;
  width: auto;
  height: auto;
  left: 70px;
  right: auto;
`
const AuSBtn = styled.div`
  padding: 8px 40px;
  background: var(--orange);
  color: white;
  cursor: pointer;
  border-radius: 5px;
`

export default MyHomeBox