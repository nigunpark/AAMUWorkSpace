import Cards from "../components/Cards/Cards.js";
import IntroPart from "../components/IntroPart/IntroPart.js";
import Swipers from "../components/Swipers/Swipers.js";
import React from "react";
import HowToUse from "../components/HowToUse/HowToUse.js";
const Home = () => {
  return (
    <>
      <IntroPart />
      <Swipers />
      <Cards />
      <HowToUse />
    </>
  );
};

export default Home;
