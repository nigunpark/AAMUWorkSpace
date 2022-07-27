<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/WEB-INF/views/template/top.jsp" />
<!--차트 데이터용 자바 스크립트-->

<script src="<c:url value="/resources/vendors/chart.js/Chart.min.js"/>"></script>
<div class="main-panel">
	<div class="content-wrapper">
		<div class="row">
			<div class="col-sm-12">
				<div class="home-tab">
					<div class="tab-content tab-content-basic">
						<!-- 회원 정보 -->
						<div class="row flex-grow">
							<div class="col-12 grid-margin stretch-card">
								<div class="card card-rounded">
									<div class="card-body">
										<div
											class="d-sm-flex justify-content-between align-items-start">
											<div>
												<h4 class="card-title card-title-dash">전체 회원 관리</h4>
												<p class="card-subtitle card-subtitle-dash">총 회원 수: ${totalCount}명</p>
											</div>
											<div>
												<button class="btn btn-primary btn-lg text-white mb-0 me-0"
													type="button">
													<i class="mdi mdi-account-remove"></i>회원정지
												</button>
											</div>
										</div>
										<div class="table-responsive  mt-1">
											<table class="table select-table">
												<thead>
													<tr>
														<th>
															<div class="form-check form-check-flat mt-0">
																<label class="form-check-label"> 
																	<input type="checkbox" class="form-check-input" aria-checked="false" id="chkAll"><i class="input-helper"></i>
																</label>
															</div>
														</th>
														<th>id</th>
														<th>이름</th>
														<th>가입일</th>
														<th>상태</th>
													</tr>
												</thead>
												<tbody>
													<c:if test="${empty listPagingData.lists }" var="isEmpty">
														<tr>
															<td colspan="8">가입된 회원이 없습니다.</td>
														</tr>
													</c:if>
													<c:if test="${not isEmpty }">
														<c:forEach var="record" items="${listPagingData.lists}" varStatus="loop">
															<tr>
																<td>
																	<div class="form-check form-check-flat mt-0">
																		<label class="form-check-label"> 
																			<input name="RowCheck" type="checkbox" class="form-check-input" aria-checked="false" value="${record.id}"><i class="input-helper"></i>
																		</label>
																	</div>
																</td>
																<td>
																	<div class="d-flex ">
																		<img src="${record.userprofile}" alt="">
																		<div>
																			<h6>${record.id}</h6>
																			<p>${record.gender}</p>
																		</div>
																	</div>
																</td>
																<td>${record.name}</td>
																<td>${record.joindate}</td>
																<c:choose>
																	<c:when test="${record.enabled eq 1}">
																		<td><div class="badge badge-opacity-success">활동중</div></td>
																	</c:when>
																	<c:otherwise>
																		<td><div class="badge badge-opacity-warning">활동정지</div></td>
																	</c:otherwise>
																</c:choose>
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
						</div>
						<!-- 검색 -->
						<div class="row">
							<form class="col-md-12 d-flex justify-content-center align-items-center" method="post" action="<c:url value="Users.do"/>">
								<div class="form-group row">
									<div class="col-sm-12">
										<select class="form-control background-color-secondary text-black" name="searchColumn">
											<option value="u.id">id</option>
											<option value="name">이름</option>
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
						<!--------------------- 내용의 끝 부분입니다------------------------------------>
					</div>
				</div>
			</div>
		</div>

	</div>
	<!--content-wrapper-->
</div>
<!--main-panel-->
</div>
</div>
</div>
<!-- container-scroller -->
<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>
<script>
	//체크박스 
	var selectLength=$(':checkbox').slice(1).length;
	$(':checkbox').click(function(){
	  if($(this).val()==='all'){
	    if($(this).prop('checked')) $(':checkbox').slice(1).prop('checked',true);
	    else $(':checkbox').slice(1).prop('checked',false);
	  }
	  else{
	    if($(this).prop('checked')){
	      if(selectLength==$(':checkbox:checked').length){
	        $(':checkbox:first').prop('checked',true)
	      }             
	    }
	    else $(':checkbox:first').prop('checked',false)
	  }
	});
	
	//체크박스all버튼 눌렀을 때 전체 선택
	$('#chkAll').click(function(){
		if($('#chkAll').is(":checked")) $(':checkbox').prop("checked",true)
		else $(':checkbox').prop("checked",false)
	});  
	
	//회원정지 click
	$('div:nth-child(2) > button').click(function(){
		console.log("버튼이벤트 발생");
		var idArr = new Array();
	    $('input[name="RowCheck"]:checked').each(function(i){//체크된 리스트 저장
	    	idArr.push($(this).val());
	    	console.log($(this).val()); 
	    });
		if(idArr.length ==0){
			alert("선택된 글이 없습니다.");
		}
		else{
			if(confirm("회원정지를 진행하시겠습니까?")){
				var jsonString = JSON.stringify({id : idArr})
	    		$.ajax({
	       			url:"<c:url value="UsersStop.do"/>",
	       			type:"post",
	       			data: jsonString,
	       			contentType:"application/json", //데이타 보낼 때
	       			dataType: "json" //데이타 받을 때 
	       		}).done(data=>{
	       			console.log('삭제성공:',data);
	       			location.replace("Users.do");
	       			
	       		}).fail(error=>{
	       			console.log('삭제에러:',error);
	       		});
			}///////if 삭제하시겠습니까?
			
		}//////else
	});
</script>
</body>
</html>

