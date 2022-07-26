import React, { useState } from 'react';
import styled from 'styled-components';

function Dropdown({title, settitle, id, setid}) {
    

    return (
      <select name="select" className="select">
          <option defaultValue={'DEFAULT'}>선택</option>
          <option value="title" onClick={()=>{settitle(!title)}}>제목</option>
          <option value="id" onClick={()=>{setid(!id)}}>아이디</option>
          <option value="tag">태그</option>
      </select>
    )
}
 


export default Dropdown;