import Cards from "../components/Cards/Cards.js";
import IntroPart from "../components/IntroPart/IntroPart.js";
import Swipers from "../components/Swipers/Swipers.js";
import React from "react";
const Home = () => {
  return (
    <>
      <IntroPart />
      <Swipers />
      <Cards />
    </>
  );
};

export default Home;
