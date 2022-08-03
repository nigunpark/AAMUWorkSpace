import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom';


const Profile = ({val}) =>  {
  let [followw, setfolloww] = useState(false);
  
  let [userPro, setuserPro] = useState(false);
  let [photo, setphoto] = useState('');
  let [photo1, setphoto1] = useState('');
  let [photo2, setphoto2] = useState('');
  let navigater = useNavigate();
 
  let [isFollower, setisFollower] = useState();
  function followfolliwing() {
    //유효성 검사를 통과하고 게시버튼 클릭시 발생하는 함수

    let token = sessionStorage.getItem("token");
    axios
      .post(
        "/aamurest/gram/follower",
        {
          follower: val.id,
          id:  sessionStorage.getItem("username"),
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      )
      .then((resp) => {
        console.log(resp.data);
        setisFollower(resp.data);
        userprofile()
      })
      .catch((error) => {
        console.log(error);
      });
  }

  function userprofile() {
    let token = sessionStorage.getItem("token");
    axios
      .get("/aamurest/gram/mypage", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        params: {
          id: sessionStorage.getItem("username"),
          follower:val.id,
        },
      })
      .then((resp) => {
        console.log(resp.data[0].totalcount);
        console.log(resp.data[0].followercount);
        console.log(resp.data[0].followingcount);
        setisFollower(resp.data[0].isFollower);
        setuserPro(resp.data[0].totalcount);
        val.followercount=resp.data[0].followercount;
        val.followingcount=resp.data[0].followingcount;
        setphoto(resp.data[0].photo[0]);
        setphoto1(resp.data[1].photo[0]);
        setphoto2(resp.data[2].photo[0]);
      })
      .catch((error) => {
        console.log(error);
      });
  }

  useEffect(()=>{
    userprofile()
  },[])
  return (
    <div className="profile-all disappear">
        <div className="profile-engine">
            <div className="parent">
              <div className="profile-contents">
                <div>
                  <div className='rowfirst'>
                    <div className="gradient">
                        <img className='imgprofile' src={val.userprofile} alt="스토리 프로필 사진" />
                    </div>
                    <div>
                        <p className="user-id">{val.id}</p>
                        {/* <p className="user-name">hi im jenny</p> */}
                    </div>
                  </div>
                  <div className='row'>
                    <div className='profileSecond'>
                        <p className="user-id">게시물</p>
                        <p className="user-name">{userPro}</p>
                    </div>
                    <div className='profileSecond'>
                        <p className="user-id">팔로워</p>
                        <p className="user-name">{val.followercount}</p>
                    </div>
                    <div className='profileSecond'>
                        <p className="user-id">팔로우</p>
                        <p className="user-name">{val.followingcount}</p>
                    </div>
                  </div>
                  <div className='rowpic'>
                    <div>
                      <img className='profilePic' src={photo} alt="스토리 프로필 사진" 
                      onError={(e) => {
                        e.stopPropagation();
                        e.target.src = "/img/image.jpg";
                      }}/>
                    </div>
                    <div>
                      <img className='profilePic' src={photo1} alt="스토리 프로필 사진" 
                      onError={(e) => {
                        e.stopPropagation();
                        e.target.src =  "/img/image.jpg";
                      }}/>
                    </div>
                    <div>
                      <img className='profilePic' src={photo2} alt="스토리 프로필 사진" 
                      onError={(e) => {
                        e.stopPropagation();
                        e.target.src =  "/img/image.jpg";
                      }}/>
                    </div>
                  </div>
                  <div className='rowbutton'>
                    <button className='message'>메세지 보내기</button>
                    <button className='following'
                     onClick={(e)=>{ 
                       e.stopPropagation();
                       followfolliwing();setfolloww(!followw)}}>{
                      val.id===sessionStorage.getItem('username') 
                      ? 
                      (followw ? navigater("/myPage") : 'Mypage')
                      : (isFollower ? '팔로잉' : '팔로우')
                    }</button>
                  </div>
                </div>
              </div>
            </div>
        </div>
    </div>
  )
}

export default Profile
