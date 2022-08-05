import axios from "axios";
import React, { useEffect, useState } from "react";
const TokenUser = ({navigate,accountEmail, setaccountEmail}) => {
  const [user_id, setUserId] = useState();
  const [birthday, setbirthday] = useState();
  const [profileImage, setProfileImage] = useState();
//   const [openid, setProfileImage] = useState();
  const getProfile =  () => {
      // Kakao SDK API를 이용해 사용자 정보 획득
       window.Kakao.API.request({
        url: '/v2/user/me',
        data: {
            property_keys: ["kakao_account.email","kakao_account.birthday",
            "kakao_account.id","kakao_account.profile","kakao_account.nickName"]
        },
        success: function(response) {
            console.log(response);
            console.log('response.data.kakao_account.email',response.kakao_account.email);
            setaccountEmail(response.kakao_account);
            navigate("./loginFunc",  { replace: true});
        },
        fail: function(error) {
            console.log(error);
        }
    });
  }

 
  useEffect(() => {
    getProfile();
  }, []);

  return (
    <div>
    </div>
  );
};
export default TokenUser;
