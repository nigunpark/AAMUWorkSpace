import { style } from '@mui/system'
import React, { useEffect, useRef, useState } from 'react'
import styled from 'styled-components'
import DaumPostcode from "react-daum-postcode";
import axios from 'axios';
import { Avatar } from 'antd';

const MyProfileBox = () => {

    const [userProfile, setUserProfile] = useState();

    // const {gender} = userProfile;
    // 이렇게 데이터 뽑아서 저장가능
    // console.log('gender :', gender); 

    const [name, setName] = useState();
    const [gender, setGender] = useState();
    const [userId, setUserId] = useState();

    const [addrIsValid, setAddrIsValid] = useState(false);

    const [phoneFNum, setPhoneFNum] = useState("");
    const [phoneSNum, setPhoneSNum] = useState("");
    const [phoneTNum, setPhoneTNum] = useState("");
    let phoneNum = phoneFNum + '-' + phoneSNum + '-' + phoneTNum;

    const [introduce, setIntroduce] = useState(""); //self

    const [zoneCode, setZoneCode] = useState(""); //우편번호
    const [address, setAddress] = useState(""); //기본주소
    const [detailAddr, setDetailAddr] = useState(""); //상세주소
    let addr = (zoneCode + '/' + address + '/' + detailAddr).split();
    
    const [isOpenPost, setIsOpenPost] = useState(false);

    const [showImages, setShowImages] = useState([]); //이미지
    

    //이메일
    let [emailFrist, setEmailFrist] = useState("");
    let [emailSecond, setEmailSecond] = useState("");
    let email = emailFrist + '@' + emailSecond;

    let zoneCodeRef = useRef();
    let addrRef = useRef();
    let addrDetailRef = useRef();

    const [pwd, setPwd] = useState();

    // console.log('email :',email);
    // console.log('addr :',addr);
    // console.log('phoneNum :', phoneNum);
    // console.log('zoneCode :',zoneCode);
    // console.log('address :',address);
    // console.log('setDetailAddr :',detailAddr);
    
    //이미지 등록
    const handleAddImages = (e) => {
        const imageLists = e.target.files;
        let imageUrlLists = [...showImages];

        
        for (let i = 0; i < imageLists.length; i++) {
            const currentImageUrl = URL.createObjectURL(imageLists[i]);
            imageUrlLists.push(currentImageUrl);
        }

        if (imageUrlLists.length > 1) { //사진 1개 제한
            imageUrlLists = imageUrlLists.slice(0, 1);
        }
        setShowImages(imageUrlLists);

        let formData = new FormData();
        formData.append('userprofile',e.target.files[0]);
        return formData;
    };

    const handleDeleteImage = () => { //등록한 사진 삭제
        setShowImages([]);
    };

    useEffect(()=>{
        let token = sessionStorage.getItem("token");
  
        axios.get('/aamurest/users/selectone',{
            params:{
                id:sessionStorage.getItem('username')
            },
            headers: {
                Authorization: `Bearer ${token}`,
            }
        }).then((resp)=>{
            console.log('데이터 확인 : ',resp.data);
            
            setUserProfile(resp.data);
            setPwd(resp.data.pwd);

            setPhoneFNum(resp.data.phonenum.split('-')[0]);
            setPhoneSNum(resp.data.phonenum.split('-')[1]);
            setPhoneTNum(resp.data.phonenum.split('-')[2]);

            setEmailFrist(resp.data.email.split('@')[0]);
            setEmailSecond(resp.data.email.split('@')[1]);

            setZoneCode(resp.data.addrid.split('/')[0]);
            setAddress(resp.data.addrid.split('/')[1]);
            setDetailAddr(resp.data.addrid.split('/')[2]);

            setIntroduce(resp.data.self);
            setShowImages(resp.data.userprofile.split());
            setName(resp.data.name);
            setGender(resp.data.gender);
            setUserId(resp.data.id);
        }).catch((error)=>{
            console.log((error) => console.log("프로필 가져오기 실패", error));
        });
        
    },[]);

    
    // console.log('userProfile :',userProfile.userprofile.split());
    // console.log('showImages :',showImages);
    let [profiles, setProfile] = useState([]);
  return (
    <MyProfileContainer>
        <MyUpdateImg>
            <ImgBox>
                <Imgs src='images/no-image.jpg'/>
                {/* <Imgs src={userProfile.userprofile}/> */}
                <form encType='multipart/form-data'>
                    <input
                        className="write-picture-input"
                        type='file' id='upload'
                        onChange={(e)=>{
                            setProfile(handleAddImages(e));
                        }}
                        onClick={(e)=>e.target.value = null}
                        />
                    <ImgUploadLabel for='upload'>
                        {showImages.map((image, id) => (
                        <Imgs src={showImages}
                        // onClick={() => handleDeleteImage(id)}/>
                            alt={`${image}-${id}`}
                            onClick={() => handleDeleteImage()}/>
                        ))}
                    </ImgUploadLabel>
                </form>
            </ImgBox>
            <div
                className="join__stepTwo-introduce-div"
                style={{ width: "70%", height:'100px', marginTop:'10px' }}
                >
                <textarea
                    // ref={introduceRef}
                    style={{
                    position: "absolute",
                    width: "345px",
                    height: "95px",
                    resize: "none",
                    border: "none",
                    outline: "none",
                    }}
                    onChange={(e)=>{setIntroduce(e.target.value)}} value={introduce}></textarea>
            </div>
        </MyUpdateImg>

        <MyUpdateProfile>

            <div style={{fontSize:'14px', display: 'flex', flexDirection: 'row'}}>
                <div>이름</div>
                <div style={{marginLeft:'200px'}}>아이디</div>
            </div>
            <div style={{fontSize:'14px', display: 'flex', flexDirection: 'row'}}>
                <Name>{name}</Name>
                {/* {userProfile.name} */}
                <Id>{userId}</Id>
                {/* {userProfile.id} */}
            </div>
            
            <div style={{fontSize:'14px'}}>비밀번호</div>
            <RRNContainer>
                <input type='password'
                    onChange={(e)=>{
                        setPwd(e.target.value);
                    }}
                    value={pwd}/>
            </RRNContainer>

            <div style={{fontSize:'14px'}}>휴대폰 번호</div>
            <PhoneNum>
                <input type='text'
                    onChange={(e)=>{setPhoneFNum(e.target.value)}} value={phoneFNum}/>
                -
                <input type='text'
                    onChange={(e)=>{setPhoneSNum(e.target.value)}} value={phoneSNum}/>
                -
                <input type='text'
                    onChange={(e)=>{setPhoneTNum(e.target.value)}} value={phoneTNum}/>
            </PhoneNum>

            <div style={{fontSize:'14px'}}>이메일</div>
            <EmailContainer>
                <input type='text'
                    onChange={(e) => {
                        setEmailFrist(e.target.value);
                    }}
                    value={emailFrist}/>
                @
                <input type='text'
                    onChange={(e) => {
                        setEmailSecond(e.target.value);
                    }}
                    value={emailSecond}/>
            </EmailContainer>

            <div style={{fontSize:'14px', display:'flex', flexDirection:'row'}}>
                주소
                <div style={{marginLeft: 'auto'}}>
                    <AddrBtn
                        type='button'
                        onClick={()=>{
                        setAddrIsValid(!addrIsValid)}
                        }>
                        주소 변경하기
                    </AddrBtn>
                </div>
            </div>
            <div style={{marginLeft: '10px'}}>
            {
                addrIsValid ?
                <EditAddr
                    zoneCodeRef={zoneCodeRef}
                    zoneCode={zoneCode}
                    setIsOpenPost={setIsOpenPost}
                    isOpenPost={isOpenPost}
                    addrRef={addrRef}
                    address={address}
                    detailAddr={detailAddr}
                    addrDetailRef={addrDetailRef}
                    setAddress={setAddress}
                    setZoneCode={setZoneCode}
                    setDetailAddr={setDetailAddr}
                    /> :
                <Addr
                    zoneCode={zoneCode}
                    address={address}
                    detailAddr={detailAddr}/>
            }
            </div>
            <div style={{textAlign:'end', marginTop:'50px'}}>
                <UpdateBtn type='button'
                    onClick={()=>{
                        // let profile = uploadFile(showImages);
                        profileUpdate(profiles, phoneNum, email, addr, introduce, pwd, gender, name);
                    }}>
                    저장
                </UpdateBtn>
            </div>
        </MyUpdateProfile>
    </MyProfileContainer>
  )
}

function uploadFile(showImages){//이미지 업로드
    let formData = new FormData(); // formData 객체를 생성한다.
    for (let i = 0; i < showImages.length; i++) { 
      formData.append("userprofile", showImages[i]); // 반복문을 활용하여 파일들을 formData 객체에 추가한다
    }
    return formData;
}

function profileUpdate(profiles, phoneNum, email, addr, introduce, pwd, gender, name){

    profiles.append('addrid', addr);
    profiles.append('email', email);
    profiles.append('gender', gender);
    profiles.append('id', sessionStorage.getItem('username'));
    profiles.append('name', name);
    profiles.append('phonenum', phoneNum);
    profiles.append('pwd', pwd);
    profiles.append('self', introduce);

    let token = sessionStorage.getItem("token");
    axios.post('/aamurest/users/upload',profiles, {
        headers: {
                Authorization: `Bearer ${token}`,
                'Content-type': 'multipart/form-data',
            }
        }
    )
    .then((resp) => {
        console.log(resp.data);
    })
    .catch((error) => {
        console.log(error);
    });
}

function EditAddr({zoneCodeRef,zoneCode,
                setIsOpenPost, isOpenPost,
                addrRef, address, detailAddr, addrDetailRef,
                setAddress, setZoneCode, setDetailAddr}){

    return(
        <div style={{position:'relative'}}>
            <div style={{
                display: 'flex',
                flexDirection: 'row',
                marginBottom:'10px'
                }}>
                <div className="join__stepTwo-input-common">
                    <input
                    style={{ marginLeft: "3px" }}
                    type="text"
                    size={14}
                    placeholder="우편번호"
                    ref={zoneCodeRef}
                    value={zoneCode}
                    />
                </div>
                <span
                    className="postCodeFind-btn"
                    onClick={() => {
                        setIsOpenPost(!isOpenPost);
                    }}
                    >
                    우편번호 찾기
                </span>
            </div>
            <div>
                <div
                    className="join__stepTwo-input-common"
                    style={{ width: "100%", marginBottom:'10px' }}
                    >
                    <input
                        style={{ marginLeft: "3px" }}
                        type="text"
                        size={52}
                        placeholder="기본주소"
                        ref={addrRef}
                        value={address}
                        />
                </div>
            </div>
            <div>
                <div
                    className="join__stepTwo-input-common"
                    style={{ width: "100%" }}
                    >
                    <input
                        style={{ marginLeft: "3px" }}
                        type="text"
                        size={52}
                        placeholder="상세주소"
                        ref={addrDetailRef}
                        onChange={(e)=>{
                            setDetailAddr(e.target.value);
                        }}
                        value={detailAddr}
                    />
                </div>
            </div>
            {
            isOpenPost && (
                <AddresApi
                    isOpenPost={isOpenPost}
                    setIsOpenPost={setIsOpenPost}
                    setAddress={setAddress}
                    setZoneCode={setZoneCode}
                />
            )}
                
        </div>
    )
};

const AddresApi = ({ setIsOpenPost, setAddress, setZoneCode }) => {
    const onCompletePost = (data) => {
        let fullAddr = data.address;
        let extraAddr = "";

        setZoneCode(data.zonecode);
        setAddress(data.address);

        if (data.addressType === "R") {
            if (data.bname !== "") {
            extraAddr += data.bname;
            }
            if (data.buildingName !== "") {
            extraAddr +=
                extraAddr !== "" ? `, ${data.buildingName}` : data.buildingName;
            }
            fullAddr += extraAddr !== "" ? ` (${extraAddr})` : "";
        }
  
        setIsOpenPost(false);
    };
    const postCodeStyle = {
        display: "block",
        position: "absolute",
        top: "-255px",
        right: "200px",
        width: "400px",
        height: "400px",
        borderRadius: "8px",
        padding: "3px",
    };
  
    return (
      <div>
        <DaumPostcode
          style={postCodeStyle}
          className="daumPostCode"
          autoClose
          onComplete={onCompletePost}
        />
      </div>
    );
};

function Addr({zoneCode, address, detailAddr}){
return(
    <>
        <div style={{
            display: 'flex',
            flexDirection: 'row',
            marginBottom:'10px'
            }}>
            <div className="join__stepTwo-input-common">
                <input
                style={{ marginLeft: "3px" }}
                type="text"
                size={14}
                value={zoneCode}
                // ref={zoneCodeRef}
                // value={zoneCode}
                disabled
                />
            </div>
        </div>
        <div>
            <div
                className="join__stepTwo-input-common"
                style={{ width: "100%", marginBottom:'10px' }}
                >
                <input
                    style={{ marginLeft: "3px" }}
                    type="text"
                    size={52}
                    value={address}
                    // ref={addrRef}
                    // value={address}
                    disabled
                    />
            </div>
        </div>
        <div>
            <div
                className="join__stepTwo-input-common"
                style={{ width: "100%" }}
                >
                <input
                style={{ marginLeft: "3px" }}
                type="text"
                size={52}
                value={detailAddr}
                // ref={addrDetailRef}
                disabled
                />
            </div>
        </div>
    </>
    )
};

const MyProfileContainer = styled.div`
    display: grid;
    grid-template-columns: 500px 520px;
    grid-template-rows: 450px;
    gap: 10px;
    // border: solid 1px black;
    font-size: 18px;
    
`

const MyUpdateImg = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    background-color: var(--app-container);
    border-radius: 0.5em;
`
const ImgBox = styled.div`
    width: 250px;
    height: 250px;
    border-radius: 70%;
    overflow: hidden;
    // position: absolute;
    position: relative;
`
const Imgs = styled.img`
    width: 100%;
    height: 100%;
    object-fit: cover;
`
const ImgUploadLabel = styled.label`
    position: absolute;
    border-radius: 50%;
    top: 0px;
    left : 0px;
    width: 250px;
    height: 250px;
    // border: 1px red solid;
`
const MyUpdateProfile = styled.div`
    display: flex;
    flex-direction: column;
`
const Name = styled.div`
    width: 100px;
    margin-bottom: 10px;
    margin-left: 10px;
`
const Id = styled.div`
    width: 100px;
    margin-bottom: 10px;
    margin-left: 130px;
`
const RRNContainer = styled.div`
    // display: flex;
    // flex-direction: row;
    // margin-bottom: 10px;
    // margin-left: 10px;
    // gap: 2px;
    
    // div{
    // }

    display: flex;
    flexDirection: row;
    gap: 3px;
    margin-left: 10px;
    margin-bottom: 10px;

    input{
        font-size: 18px;
        width: 100px;
        border: solid 2px gray;
        border-radius: 0.3em;
    }
`
const PhoneNum = styled.div`
    display: flex;
    flex-direction: row;
    gap: 2px;
    margin-bottom: 10px;
    margin-left: 10px;

    input{
        width: 70px;
        border: solid 2px gray;
        border-radius: 0.3em;
    }
`
const EmailContainer = styled.div`
    display: flex;
    flexDirection: row;
    gap: 3px;
    margin-left: 10px;
    margin-bottom: 10px;

    input{
        font-size: 18px;
        width: 100px;
        border: solid 2px gray;
        border-radius: 0.3em;
    }
`
const AddrBtn = styled.button`
    padding: 3px 10px;
    background-color: var(--orange);
    color: white;
    border-radius: 5px;
    font-weight: bold;
`
const UpdateBtn = styled.button`
    margin-top: 5px;
    width: 100px;
    padding: 3px 10px;
    background-color: var(--orange);
    color: white;
    border-radius: 5px;
    font-weight: bold;
`

export default MyProfileBox