import { style } from '@mui/system';
import React from 'react'
import styled from 'styled-components';

function SearchModal(){
    

  return (
    
    <SearchAll>
        <Searchengine>
            <Searchcontents>
                <Button>jenny0305</Button>
            </Searchcontents>
        </Searchengine>
    </SearchAll>
                 
   
  )
}

const SearchAll = styled.div`
    position: absolute;
    bottom: 230px;
    right: -184px;
    width: 350px;
    height: fit-content;
    transform: translate(-50%);
    background-color: transparent;
    box-shadow: 0 0 5px 1px rgba(var(--jb7, 0, 0, 0), 0.0975);
    border: 1px solid #fff;
    border-radius: 5px;
    z-index: 20;
 `
 const Searchengine = styled.div`
    position: relative;
    width: 340px;
    height: fit-content;
    background-color: red;
    z-index: 9;
    overflow: auto;
 `
 const Searchcontents = styled.div`
    display: flex;
    flex-direction: row;
    align-items: center;
    margin-left: 10px;
    height: 100%;

 `
 const Button = styled.button`
    font-size: 18px;
    height:60px;
    background-color: #000;
 `
    
export default SearchModal
