import axios from "axios";
import React, { useEffect } from "react";

export default function LoginFunc({ navigate, accountEmail }) {
  const gramEdit = async (accountEmail) => {
    console.log(accountEmail.profile.profile_image_url);
    console.log("accountEmail.gender", accountEmail);
    // let token = sessionStorage.getItem("token");
    try {
      const res = await axios.post("/aamurest/authenticate/email", {
        email: accountEmail.email,
      });
      console.log(res);
      sessionStorage.setItem("token", res.data.token);
      sessionStorage.setItem("userimg", res.data.userprofile);
      sessionStorage.setItem("username", res.data.member.username);
      navigate("/");
    } catch (e) {
      sessionStorage.setItem(
        "userimgkakao",
        accountEmail.profile.profile_image_url
      );
      sessionStorage.setItem("useremailkakao", accountEmail.email);
      sessionStorage.setItem("usergender", accountEmail.gender);
      sessionStorage.setItem("usernickname", accountEmail.profile.nickname);
      navigate("/join");
    }
  };

  useEffect(() => {
    gramEdit(accountEmail);
  }, []);

  return <div></div>;
}
