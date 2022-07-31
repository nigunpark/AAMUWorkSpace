import React from "react";
import styled from "styled-components";

const Pagination = ({ postsPerPage, totalPosts, setCurrentPage }) => {
  const pageNumbers = [];
  for (let i = 1; i <= Math.ceil(totalPosts / postsPerPage); i++) {
    pageNumbers.push(i);
  }
  return (
    <div>
      <nav>
        <PageUl className="pagination">
          {pageNumbers.map((number, i) => (
            <PageLi key={i} className="page-item" onClick={() => setCurrentPage(number)}>
              <span className="page-link">{number}</span>
            </PageLi>
          ))}
        </PageUl>
      </nav>
    </div>
  );
};
const PageUl = styled.ul`
  // float: left;
  list-style: none;
  text-align: center;
  border-radius: 3px;
  color: black;
  padding: 1px;
  // border-top: 3px solid #186ead;
  // border-bottom: 3px solid #186ead;
  // background-color: rgba(0, 0, 0, 0.4);
  width: 100%;
  height: 30px;
`;

const PageLi = styled.li`
  display: inline-block;
  font-size: 17px;
  font-weight: 600;
  padding: 5px;
  width: 25px;
  &:hover {
    color: var(--orange);
    cursor: pointer;
  }
`;

export default Pagination;
