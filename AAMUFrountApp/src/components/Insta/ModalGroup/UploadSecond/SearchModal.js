import { style } from '@mui/system';
import React from 'react'
import styled from 'styled-components';


function SearchModal({search}){
    

  return (
    
    <SearchAll>
        <Searchengine>
            <Searchcontents>
              {
              search.map((val)=>{
                return(<P>
                  {val.TITLE}
              </P>)
              })}
                
            </Searchcontents>
        </Searchengine>
    </SearchAll>
                 
   
  )
}

const SearchAll = styled.div`
    position: absolute;
    bottom: 20px;
    right: -124px;
    width: 30%;
    height: 300px;
    transform: translate(-50%);
    background-color:transparent;
    border-radius: 5px;
    z-index: 202;
 `
 const Searchengine = styled.div`
    position: relative;
    width: 100%;
    height: 100%;
    background-color: transparent;
    right:10px;
    z-index: 9;
    overflow: auto;
 `
 const Searchcontents = styled.div`
    position: absolute;
    display: flex;
    background-color: #fff;
    flex-direction: column;
    box-shadow: 0 0 5px 1px rgba(var(--jb7, 0, 0, 0), 0.0975);
    align-items: center;
    margin-left: 10px;
 `
 const P = styled.p`  
    display: flex;
    position: absolute;
    width: 100%;
    height: 100%;
 `
    
export default SearchModal
