import React, { useState, useEffect } from "react";
import axios from "axios"; // 액시오스

const Test = () => {
  const [id, setId] = useState(0);

  function stateTest() {
    setId(id + 1);
  }
  useEffect(() => {
    alert("ok");
  }, []);

  const api = "http://192.168.0.19:8080";

  function testAxios() {
    axios
      .get("http://192.168.0.19:8080/rest/info2?areacode=1&contenttypeid=12")
      .then((response) => {
        console.log(response.data);
      })
      .catch((error) => {
        console.log(error);
      })
      .then(() => {
        console.log("get요청실행됨");
      });
  }

  function test() {
    axios
      .post("http://192.168.0.19:8080/rest/info2", {
        areacode: "1",
        contenttypeid: "12",
      })
      .then((resp) => {
        console.log("post 성공");
        console.log(resp.data);
      })
      .catch((error) => {
        console.log("error : ", error);
      })
      .then(() => {
        console.log("post요청됨");
      });
  }

  return (
    <div>
      test{id}
      <button
        style={{ background: "green", cursor: "pointer" }}
        onClick={() => testAxios()}>
        test
      </button>
      <button
        style={{ background: "yellow", cursor: "pointer" }}
        onClick={() => test()}>
        axiosTest
      </button>
    </div>
  );
};

export default Test;
