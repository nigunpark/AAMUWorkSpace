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
										<button class="btn btn-primary text-white me-0" style="float: right" id="backup">저장</button>
									</div>
									<h4 class="card-title">여행지 리스트</h4>
									<div class="table-responsive text-center">
										<table class="table text-center">
											<thead>
												<tr>
													<th>지역</th>
													<th>장소 타입</th>
													<th>카운트</th>
													<th class="col-1">데이터 백업</th>								
												</tr>
											</thead>
											<tbody>
												<c:if test="${empty areaCount.lists }" var="isEmpty">
													<tr>
														<td colspan="8">등록된 여행지가 없습니다.</td>
													</tr>
												</c:if>
												<c:if test="${not isEmpty }">
													<c:forEach var="record" items="${areaCount.lists}" varStatus="loop">
														<tr>
															<td>${record.area}</td>
															<td>${record.contenttype}</td>
															<c:if test="${record.count eq '0' }" var="isZero">
																	<td class="text-danger font-weight-bold"><h6>${record.count}</h6></td>
															</c:if>
															<c:if test="${not isZero }">
															<td class="font-weight-bold">${record.count}</td>
															</c:if>
															<td><button class="btn btn-primary text-white me-0" id="backup">데이터 백업</button></td>
														</tr>
													</c:forEach>
												</c:if>
											</tbody>
										</table>
										<div>${areaCount.pagingString}</div>
									</div>
								</div>
							</div>
						</div>
						<!--예시 용 테이블-->
						<!-- 검색 -->
						<div class="row">
							<form class="col-md-12 d-flex justify-content-center align-items-center" method="post" action="<c:url value="adminbackup.do"/>">
								<div class="form-group row">
									<div class="col-sm-12">
										<select class="form-control background-color-secondary text-black" name="searchColumn">
											<option value="c.id">지역</option>
											<option value="ctitle">장소 타입</option>
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
$('#backup').click(function(){
	$.ajax({
		url:"<c:url value="placesbackup.do"/>",
		type:"get",
		dataType: "json"
	})
});
$('#save').click(function(){
	
});
    
  </script>
</body>
</html>

