import axios from 'axios';
import React, { useEffect } from 'react'

export default function LoginFunc({navigate,accountEmail}) {

    const  gramEdit =async(accountEmail) =>{
        // console.log(accountEmail);
        // let token = sessionStorage.getItem("token");
        try{
        const res = await axios.post("/aamurest/authenticate/email",  {
              email : accountEmail
          });
          console.log(res);
          sessionStorage.setItem("token", res.data.token);
          sessionStorage.setItem("userimg", res.data.userprofile);
          sessionStorage.setItem("username", res.data.member.username);
          navigate("/");
        }
        catch(e){
          navigate("/join");
        }
      }
    
      useEffect(() => {
        gramEdit(accountEmail);
      }, []);
    
  return (
    <div>
      
    </div>
  )
}
