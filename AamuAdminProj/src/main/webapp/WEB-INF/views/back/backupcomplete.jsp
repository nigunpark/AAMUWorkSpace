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
										<button class="btn btn-primary text-white me-0" style="float: right" id="save">데이터 저장</button>
									</div>
									<h4 class="card-title">여행지 리스트</h4>
									<div class="card-numberOfBoard">
										데이터 수: ${totalCount}개
									</div>
									<div class="table-responsive text-center">
										<table class="table text-center">
											<thead>
												<tr>
													<th class="col-1">장소 번호</th>
													<th class="col-1">지역 번호</th>
													<th class="col-1">타입 번호</th>
													<th>주소</th>
													<th class="col-4">장소명</th>
													<th class="col-1">좋아요수</th>								
												</tr>
											</thead>
											<tbody>
												<c:if test="${empty placelist.lists }" var="isEmpty">
													<tr>
														<td colspan="8">받은 데이터가 없습니다.</td>
													</tr>
												</c:if>
												<c:if test="${not isEmpty }">
													<c:forEach var="record" items="${placelist.lists}" varStatus="loop">
														<tr>
															<td>${record.areacode}</td>
															<td>${record.contenttype}</td>
															<td>${record.count}</td>
															<td>${record.contenttype}</td>
															<td>${record.count}</td>
															<td>${record.contenttype}</td>
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

