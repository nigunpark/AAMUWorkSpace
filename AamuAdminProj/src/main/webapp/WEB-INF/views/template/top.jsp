<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>Aamu</title>
  <!-- navbar 아이콘 -->
  <link rel="stylesheet" href="<c:url value="/resources/vendors/feather/feather.css"/>">
   <!--sidebar 아이콘 -->
  <link rel="stylesheet" href="<c:url value="/resources/vendors/mdi/css/materialdesignicons.min.css"/>">
  <!-- inject:css -->
  <link rel="stylesheet" href="<c:url value="/resources/css/vertical-layout-light/style.css"/>">
  
  <script src="<c:url value="/resources/vendors/js/vendor.bundle.base.js"/>"></script>
  
  
  <!--축소 페이지용 js-->
  <script src="<c:url value="/resources/js/off-canvas.js"/>"></script>
  <!--datepicker용 js-->
  <script src="<c:url value="/resources/vendors/bootstrap-datepicker/bootstrap-datepicker.min.js"/>"></script>
  <!-- commu_plugins:css _체크박스v모양 나오게 -->
  <link rel="stylesheet" href="<c:url value="/resources/vendors/ti-icons/css/themify-icons.css"/>">
  
  <style>
  
  .navbar .navbar-brand-wrapper .navbar-brand img {height: auto;}
  
  </style>

</head>

<body class="sidebar-dark">
  <div class="container-scroller">
    <!-- navbar입니다-->
    <nav class="navbar default-layout col-lg-12 col-12 p-0 fixed-top d-flex align-items-top flex-row">
      <div class="text-center navbar-brand-wrapper d-flex align-items-center justify-content-start">
        <div class="me-3">
          <button class="navbar-toggler navbar-toggler align-self-center" type="button" data-bs-toggle="minimize">
            <span class="icon-menu"></span>
          </button>
        </div>
        <div>
          <a class="navbar-brand brand-logo" href="<c:url value="main.do"/>">
            <img src="<c:url value="/resources/images/AAMU_100_TEXT.png"/>" alt="mapping=/resources/**" />
          </a>
          <a class="navbar-brand brand-logo-mini" href="<c:url value="main.do"/>">
            <img src="<c:url value="/resources/images/AAMU_100_Circle.png"/>" alt="mapping=/resources/**" />
          </a>
        </div>
      </div>
      <div class="navbar-menu-wrapper d-flex align-items-top">
        <ul class="navbar-nav">
          <li class="nav-item font-weight-semibold d-none d-lg-block ms-0">
            <h1 class="welcome-text"><span class="text-black fw-bold">Admin Page</span></h1>
            <h3 class="welcome-sub-text">AAMU 관리자 게시판</h3>
          </li>
        </ul>
        <ul class="navbar-nav ms-auto">
          <li class="nav-item d-none d-lg-block">
            <div id="datepicker-popup" class="input-group date datepicker navbar-date-picker">
              <span class="input-group-addon input-group-prepend border-right">
                <span class="icon-calendar input-group-text calendar-icon"></span>
              </span>
              <input type="text" class="form-control" >
            </div>
          </li>
          <li class="nav-item d-none d-lg-block">
            <div id="datepicker-popup2" class="input-group date datepicker navbar-date-picker">
              <span class="input-group-addon input-group-prepend border-right">
                <span class="icon-calendar input-group-text calendar-icon"></span>
              </span>
              <input type="text" class="form-control" >
            </div>
          </li>
          <li class="nav-item">
            <form class="search-form" action="#">
              <i class="icon-search"></i>
              <input type="search" class="form-control" placeholder="Search Here" title="Search here">
            </form>
          </li>
          <li class="nav-item dropdown">
            <a class="nav-link count-indicator" id="notificationDropdown" href="#" data-bs-toggle="dropdown">
              <i class="icon-mail icon-lg"></i>
            </a>
          </li>
          <li class="nav-item dropdown">
            <a class="nav-link count-indicator" id="countDropdown" href="#" data-bs-toggle="dropdown"
              aria-expanded="false">
              <i class="icon-bell"></i>
              <span class="count"></span>
            </a>
          </li>
        </ul>
        <button class="navbar-toggler navbar-toggler-right d-lg-none align-self-center" type="button"
          data-bs-toggle="offcanvas">
          <span class="mdi mdi-menu"></span>
        </button>
      </div>
    </nav>
    <!--navbar와 사이드바 구분 없애기 용-->
    <div class="container-fluid page-body-wrapper">
      <!-- 여기서부터 사이드바 입니다 -->
      <nav class="sidebar sidebar-offcanvas" id="sidebar">
        <ul class="nav">
          <li class="nav-item">
            <a class="nav-link" href="<c:url value="main.do"/>">
              <i class="mdi mdi-grid-large menu-icon"></i>
              <span class="menu-title">메인 페이지</span>
            </a>
          </li>
          <li class="nav-item nav-category">회원 관리</li>
          <li class="nav-item">
            <a class="nav-link" data-bs-toggle="collapse" href="#ui-basic" aria-expanded="false" aria-controls="ui-basic">
              <i class="menu-icon mdi mdi-floor-plan"></i>
              <span class="menu-title">회원 관리</span>
              <i class="menu-arrow"></i>
            </a>
            <div class="collapse" id="ui-basic">
              <ul class="nav flex-column sub-menu">
                <li class="nav-item"> <a class="nav-link" href="Users.do">회원 관리</a></li>
              </ul>
            </div>
          </li>
          <li class="nav-item nav-category">게시판 및 커뮤니티</li>
          <li class="nav-item">
            <a class="nav-link" data-bs-toggle="collapse" href="#form-elements" aria-expanded="false"
              aria-controls="form-elements">
              <i class="menu-icon mdi mdi-card-text-outline"></i>
              <span class="menu-title">게시판 관리</span>
              <i class="menu-arrow"></i>
            </a>
            <div class="collapse" id="form-elements">
              <ul class="nav flex-column sub-menu">
                <li class="nav-item"><a class="nav-link" href="<c:url value="bbs.do"/>">전체 게시글 관리</a></li>
                <li class="nav-item"> <a class="nav-link" href="#">리뷰 관리</a></li>
              </ul>
            </div>
          </li>
          <li class="nav-item">
            <a class="nav-link" data-bs-toggle="collapse" href="#tables" aria-expanded="false" aria-controls="tables">
              <i class="menu-icon mdi mdi-table"></i>
              <span class="menu-title">커뮤니티 관리</span>
              <i class="menu-arrow"></i>
            </a>
            <div class="collapse" id="tables">
              <ul class="nav flex-column sub-menu">
                <li class="nav-item"><a class="nav-link" href="<c:url value="Commu.do"/>">전체 커뮤니티 관리</a></li>
                <li class="nav-item"> <a class="nav-link" href="CommuComment.do">댓글 관리</a></li>
              </ul>
            </div>
          </li>
          <li class="nav-item nav-category">관리자 게시판</li>
          <li class="nav-item">
             <a class="nav-link" data-bs-toggle="collapse" href="#auth" aria-expanded="false" aria-controls="auth">
              <i class="menu-icon mdi mdi-account-circle-outline"></i>
              <span class="menu-title">공지사항 및 Q&A</span>
              <i class="menu-arrow"></i>
            </a>
            <div class="collapse" id="auth">
              <ul class="nav flex-column sub-menu">
                <li class="nav-item"> <a class="nav-link" href="<c:url value="Notice.do"/>">공지사항</a></li>
                <li class="nav-item"> <a class="nav-link" href="<c:url value="QNA.do"/>">Q&A</a></li>
              </ul>
            </div>
          </li>
          <li class="nav-item nav-category">여행지 관리</li>
           <li class="nav-item">
           <a class="nav-link" data-bs-toggle="collapse" href="#icons" aria-expanded="false" aria-controls="icons">
              <i class="menu-icon mdi mdi-layers-outline"></i>
              <span class="menu-title">여행지 관리</span>
              <i class="menu-arrow"></i>
            </a>
            <div class="collapse" id="icons">
              <ul class="nav flex-column sub-menu">
                <li class="nav-item"> <a class="nav-link" href="<c:url value="adminbackup.do"/>">데이터 백업</a></li>
                <li class="nav-item"> <a class="nav-link" href="<c:url value="Theme.do"/>">테마 관리</a></li>
              </ul>
            </div>
          </li>
        </ul>
      </nav>
      <!--사이드바 끝------------------------------------------------------------------------->
     	</body>
</html>