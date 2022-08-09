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
									<div
										class="d-sm-flex justify-content-between align-items-start">
										<div>
											<h4 class="card-title">테마 관리</h4>
											<p class="card-description">총 테마 개수: ${totalCount}개</p>
										</div>
										<div>
											<button
												class="btn btn-primary btn-sm text-white mb-0 me-0 my-4"
												type="button" data-toggle="modal" data-target="#myModal">테마
												등록</button>

										</div>
									</div>
									<div class="table-responsive text-center">
										<table class="table table-bordered">
											<thead>
												<tr>
													<th class="col-1">번호</th>
													<th class="col-1">이미지</th>
													<th>테마 이름</th>
													<th class="col-3">비고</th>
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
															<td>${record.themeid}</td>
															<td>
																<img src="${record.themeimg}" alt="테마 이미지">
															</td>
															<td>${record.themename}</td>
															<td>
																<button
																	class="btn btn-primary btn-sm text-white mb-0 me-2"
																	type="button">수정</button>
																<button
																	class="btn btn-primary btn-sm text-white mb-0 me-0"
																	type="button">삭제</button>
															</td>
														</tr>
													</c:forEach>
												</c:if>
											</tbody>
										</table>
										<!-- 페이징 출력 -->
										<div>${listPagingData.pagingString}</div>
									</div>
								</div>
							</div>
						</div>
						<!--예시 용 테이블-->
						<!-- 모달 시작 -->
						<div class="modal" id="myModal">
							<div class="modal-dialog">
								<div class="modal-content">
									<!-- Modal -->
									<div class="col-12 grid-margin stretch-card">
										<div class="card">
											<div class="card-body">
												<h4 class="card-title">새로운 테마 추가</h4>
												<form class="forms-sample" method="post" encType="multipart/form-data" action="<c:url value="ThemeInsert.do"/>">
													<div class="form-group">
														<label for="exampleInputName1">테마 이름</label> <input
															type="text" class="form-control" id="exampleInputName1" name="themename" value="${param.themename}"
															placeholder="테마 이름">
													</div>
													<!-- 파일업로드 -->
													<div class="form-group">
														<label>테마 이미지</label>
														<div>
															<input type="file" size="50" name="multifiles" />
														</div>
													</div>
													<button type="submit" class="btn btn-primary me-2">등록</button>
													<button class="btn btn-light">취소</button>
												</form>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!--모달 끝-->
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
  	
    
    //삭제 click
    $('div.card-numberOfBoard > button').click(function(){
    	console.log("버튼이벤트 발생");
    	var lnoArr = new Array();
        $('input[name="RowCheck"]:checked').each(function(i){//체크된 리스트 저장
        	lnoArr.push($(this).val());
        	console.log($(this).val()); //lnoArr:63,62
        });
    	if(lnoArr.length ==0){
    		alert("선택된 글이 없습니다.");
    	}
    	else{
    		if(confirm("정말 삭제하시겠습니까?")){
    			var jsonString = JSON.stringify({lno : lnoArr})
        		$.ajax({
           			url:"<c:url value="CommuDelete.do"/>",
           			type:"post",
           			data: jsonString,
           			contentType:"application/json", //데이타 보낼 때
           			dataType: "json" //데이타 받을 때 
           		}).done(data=>{
           			console.log('삭제성공:',data);
           			location.replace("Commu.do");
           			
           		}).fail(error=>{
           			console.log('삭제에러:',error);
           		});
    		}///////if 삭제하시겠습니까?
    		
    	}//////else
    });
    
  </script>
</body>
</html>

