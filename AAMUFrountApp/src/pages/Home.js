import Cards from "../components/Cards/Cards.js";
import IntroPart from "../components/IntroPart/IntroPart.js";
import Swipers from "../components/Swipers/Swipers.js";
import React, { useEffect, useState } from "react";
import HowToUse from "../components/HowToUse/HowToUse.js";
import axios from "axios";
const Home = () => {
  const [forSwiper, setForSwiper] = useState([]);
  const [forCards, setForCards] = useState([]);
  function get() {
    // let token = sessionStorage.getItem("token");
    axios
      .get("/aamurest/main/mainelement", {
        params: {
          id: sessionStorage.getItem("username"),
        },
      })
      .then((resp) => {
        // console.log("home", resp.data);
        setForSwiper(resp.data.placeInfo);
        setForCards(resp.data.bbsInfo);
      })
      .catch((err) => {
        console.log(err);
      });
  }
  useEffect(() => {
    get();
  }, []);
  return (
    <>
      <IntroPart />
      <Swipers forSwiper={forSwiper} />
      <Cards forCards={forCards} />
      <HowToUse />
    </>
  );
};

export default Home;
