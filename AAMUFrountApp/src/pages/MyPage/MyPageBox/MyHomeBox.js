import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect, useState } from 'react'
import styled from 'styled-components';
import { faRectangleXmark } from "@fortawesome/free-solid-svg-icons";
import CreatePlanLeft from '../../../components/CreatePlan/CreatePlanLeft';
import CreatePlanMap from '../../../components/CreatePlan/CreatePlanMap';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import MyPlanMap from './MyPlanMap';

const MyHomeBox = ({setClickTab, planList, rbn, setSelectRbn}) => {

    const [showCreatePlan, setShowCratePlan] = useState(false);
    const [fromWooJaeData, setFromWooJaeData] = useState([]);
    
    let [currPosition, setCurrPosition] = useState();

    // console.log('잘 넘어왔나 :',rbn);

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
                    {planList.plannerdate} (rbn :{rbn})
                </p>
            </div>

            <div className="project-box-footer" style={{marginTop:'5px'}}>
                <div className='detail-button'>
                    <button
                        className="learn-more"
                        type="button"
                        onClick={()=>{
                            setClickTab(10);
                            setSelectRbn(rbn);
                            }}>공유하기</button>
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
            setCurrPosition={setCurrPosition}
            fromWooJaeData={fromWooJaeData}
            setFromWooJaeData={setFromWooJaeData}
            rbn={rbn}
            />
        ) : null}
    </div>
  )
}

function MyDetailPlan({
        setIsOpen,
        setShowCratePlan,
        currPosition,
        setCurrPosition,
        fromWooJaeData,
        setFromWooJaeData,
        rbn}){

    const [auSure, setAuSure] = useState(false);
    const [savePlan, setSavePlan] = useState(false);
    const [forDayLine, setForDayLine] = useState(0);
    const [routeMap, setRouteMap] = useState([]);
    const [planDate, setPlanDate] = useState();


    useEffect(()=>{
        let token = sessionStorage.getItem("token");
//   /planner/selectonemap
        axios.get('/aamurest/planner/selectonemap',{
            params:{
                // id:sessionStorage.getItem('username'),
                rbn:rbn
            },
            headers: {
                Authorization: `Bearer ${token}`,
            }
        }).then((resp)=>{
            console.log('상세경로 데이터 확인 : ',resp.data);

            setCurrPosition(resp.data.title.split(' ')[0]);
            setRouteMap(resp.data.routeMap);
            
            setPlanDate(resp.data.plannerdate);

        }).catch((error)=>{
            console.log((error) => console.log("상세경로 가져오기 실패", error));
        });
        
    },[]);
    // console.log('currPosition 잘 짤렷나요 :',currPosition);
    console.log('planDate 가져왔나 :',planDate);

    console.log('자르기 1 :', planDate.substring(planDate.indexOf('월')-1,planDate.indexOf('월')));
    console.log('자르기 2 :', planDate.substring(planDate.indexOf('일')-2,planDate.indexOf('일')));
    console.log('자르기 3 :', planDate.substring(planDate.indexOf('수'),planDate.indexOf('수')+1));

    let keys = Object.keys(routeMap);
    let values = Object.values(routeMap);

    let bbc = Object.entries(routeMap).map((val, idx)=>{
        return {[keys[idx]] : values[idx]};
    });
    
    console.log('bbc :',bbc);
    
    //Object.entries(routeMap)
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
                    currPosition={currPosition}//지역명 넣기
                    fromWooJaeData={fromWooJaeData} //routeMap [{},{},{}] 형식으로 가공해야함
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
                    <MyPlanMap
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