import React, { useState } from 'react';
import styled from 'styled-components';

function Dropdown() {
    

    return (
      <select name="select" className="select">
          <option defaultValue={'DEFAULT'}>선택</option>
          <option value="title">제목</option>
          <option value="id">아이디</option>
          <option value="tag">태그</option>
      </select>
    )
}
 


export default Dropdown;