import axios from 'axios';
import React, { useEffect, useState } from 'react'
import styled from 'styled-components';

function SearchList({searchb, setSearchb}) {
    const [searchbar, setsearchbar] = useState([]);
    function searchBar(e,searchb){//백이랑 인스타 리스드를 뿌려주기 위한 axios
    
        let val = e.target.value;
        let token = sessionStorage.getItem("token");
        axios.get('/aamurest/gram/selectList',{
          headers: {
                Authorization: `Bearer ${token}`,
              },
              params:{
                searchColumn : searchb,
                searchWord :val,
              }
        })
        .then((resp) => {
          console.log(resp.data)
          setsearchbar(resp.data);
          })
          .catch((error) => {
            console.log(error);
          });
      }
      useEffect((e)=>{
        searchBar(e,searchb)
      },[])
    
    return (
        <MyInstaContainer>
            <Grid>
            <User>
                <img src="/img/mm.jpg" alt="프사" onError={(e)=>{e.stopPropagation(); e.target.src='/images/user.jpg'}}/>
                <div style={{paddingLeft:'20px'}}>
                <p className="user-id">@토토</p>
                <p className="user-name">총 게시물</p>
                </div>

                
            </User>
            <MyInstar>
            
                {
                searchbar.map((val, idx)=>{
                    return(
                    <InstarBox
                        onClick={(e)=>{
                        }}>
                        <InstaImg
                        src={val === undefined ? null : val.photo}/>
                    </InstarBox>
                    )
                })
                }
            </MyInstar>
            </Grid>
        </MyInstaContainer>
 
      );
}

const MyInstaContainer = styled.div`

display: grid;
grid-template-columns: auto 900px auto;
gap:5px;
width: 100%;
height: 100%;
padding-top : 80px;
 
    
 `

 const Grid = styled.div`

 display: grid;
 grid-column: 2/3;
 position:relative;
 gap:5px;
 overflow: auto;
 padding: 5px;

 &::-webkit-scrollbar{
   display:none;
 }
 // border: 1px solid red;
`

const User = styled.div`
    display: flex;
    flex-direction: row;
    align-items: center;
    margin-bottom: 10px;
    height:200px;
    > img {
      width: 150px;
      height: 150px;
      border: 1px solid #e6e6e6;
      border-radius: 50%;
      
    }

    .user-id {
      font-size: 25px;
      font-weight: bold;
      margin-left: 10px;
    }

    .user-name {
      font-size: 15px;
      color: #8e8e8e;
      margin-top: 3px;
      margin-left: 10px;
    }
`
 const SearchProfile = styled.div`

 display: grid;
 position:absolute;
 gap:5px;
 overflow: auto;
 padding: 5px;

 &::-webkit-scrollbar{
   display:none;
 }
 // border: 1px solid red;
`
 const MyInstar = styled.div`

 display: grid;
 grid-template-columns: repeat(3, 1fr);
 gap:15px;
 overflow: auto;
 padding: 5px;

 &::-webkit-scrollbar{
   display:none;
 }
    // border: 1px solid red;
  `
  const InstarBox = styled.div`

  height: 225px;
  border-radius: 10px;
  cursor: pointer;
  box-shadow: 0 0 0 2px #e9ebec, 0 0 0 1px #fcfdfe;
  &:hover{
    transition: 0.3s;
    transform: scale(1.03);
  }
  `
const InstaImg = styled.div`
    width: 100%;
    height: 100%;
    border-radius: 10px;
    object-fit: cover;
 ` 



 export default SearchList
