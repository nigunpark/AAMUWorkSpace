<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/WEB-INF/views/template/top.jsp" />

<div class="main-panel">
	<div class="content-wrapper" style="background-color: white;">
		<div class="row">
			<div class="col-sm-12">
				<div class="home-tab">
					<div class="tab-content tab-content-basic">
						<!--여기부터 내용을 넣으시오-->
						<!--커뮤니티 게시글 관리-->
						<div class="col-lg-12 grid-margin stretch-card">
							<div class="card">
								<div class="card-body">
								<div class="card-numberOfBoard">
										<button class="btn btn-primary text-white me-0" style="float: right">데이터 백업</button>
									</div>
									<h4 class="card-title">게시글 전체 리스트</h4>
									<div class="table-responsive text-center">
										<table class="table text-center">
											<thead>
												<tr>
													<th class="col-2">ID</th>
													<th>장소명</th>
													<th class="col-4">주소</th>
													<th class="col-1">장소 타입</th>
													<th class="col-1">지역 코드</th>										
												</tr>
											</thead>
											<tbody>
												<c:if test="${empty listPagingData.lists }" var="isEmpty">
													<tr>
														<td colspan="8">등록된 글이 없습니다.</td>
													</tr>
												</c:if>
												<c:if test="${not isEmpty }">
													<c:forEach var="record" items="${listPagingData.lists}" varStatus="loop">
														<tr>
															<td>
																<div class="form-check form-check-flat mt-0">
																	<label class="form-check-label"> 
																		<input name="RowCheck" type="checkbox" class="form-check-input" aria-checked="false" value="${record.lno}"> <i class="input-helper"></i>
																	</label>
																</div>
															</td>
															<td>${record.lno}</td>
															<td>${record.ctitle}</td>
															<td>${record.id}</td>
															<td>${record.postdate}</td>
															<td>${record.rcount}</td>
															<td>${record.likecount}</td>
														</tr>
													</c:forEach>
												</c:if>
											</tbody>
										</table>

									</div>
								</div>
							</div>
						</div>
						<!--예시 용 테이블-->
						<!-- 검색 -->
						<div class="row">
							<form class="col-md-12 d-flex justify-content-center align-items-center" method="post" action="<c:url value="Commu.do"/>">
								<div class="form-group row">
									<div class="col-sm-12">
										<select class="form-control background-color-secondary text-black" name="searchColumn">
											<option value="c.id">id</option>
											<option value="ctitle">제목</option>
											<option value="content">내용</option>
										</select>
									</div>
								</div>
								<div class="form-group row">
									<div class="col-sm-12">
										<input type="text" class="form-control mx-2 my-2" placeholder="검색어를 입력하세요" name="searchWord" />
									</div>
								</div>
								<div class="form-group row">
									<div class="col-sm-12">
										<input type="submit" class="btn btn-primary mx-3 my-2 text-white" value="검색" id="submit"/>
									</div>
								</div>
							</form>
						</div>
						<!-- 검색 끝 -->
						<!--------------------- 내용의 끝 부분입니다------------------------------------>
					</div>
				</div>
			</div>
		</div>
		<!--content-wrapper-->
	</div>
	<!--main-panel-->
</div>


<script>
 
    
  </script>
</body>
</html>

