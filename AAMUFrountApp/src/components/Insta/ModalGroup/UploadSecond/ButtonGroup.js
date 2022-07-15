import React, { useState } from 'react';
import styled from 'styled-components';

function Dropdown() {
    

    return (
      <Select name="select" class="select">
          <option disabled selected>선택</option>
          <option value="title">제목</option>
          <option value="id">아이디</option>
          <option value="tag">태그</option>
      </Select>
    )
}
 
const Select = styled.select`
    background-color:#fff;
    padding:5px;
    margin-top:28px;
    border-radius:3px;

    .items {
      position: absolute;
      background-color: DodgerBlue;
      top: 100%;
      left: 0;
      right: 0;
      z-index: 99;
    }
`

export default Dropdown;