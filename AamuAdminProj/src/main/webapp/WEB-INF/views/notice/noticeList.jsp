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
									<h4 class="card-title">공지사항</h4>

									<div class="card-numberOfBoard">
										총 게시글 수: ${totalCount}개

										<button class="delete btn btn-primary text-white me-0 mx-2"
											style="float: right">삭제</button>
										<a href="<c:url value="NoticeWrite.do"/>"><button
												class="write btn btn-primary text-white me-0"
												style="float: right">등록</button></a>
									</div>

									<div class="notice-table table-responsive text-center">
										<table class="table text-center ">
											<thead>
												<tr>
													<th class="col-1 ">
														<div class="form-check form-check-flat mt-0">
															<label class="form-check-label"> <input
																type="checkbox" class="form-check-input"
																aria-checked="false" id="chkAll"><i
																class="input-helper"></i>
															</label>
														</div>
													</th>
													<th class="col-1">번호</th>
													<th>제목</th>
													<th class="col-1">공지</th>
													<th class="col-1">날짜</th>
													<th class="col-1">조회</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${empty listPagingData.lists }" var="isEmpty">
													<tr>
														<td colspan="8">등록된 글이 없습니다.</td>
													</tr>
												</c:if>
												<c:if test="${not isEmpty }">
													<c:forEach var="record" items="${listPagingData.lists}"
														varStatus="loop">
														<tr>
															<td>
																<div class="form-check form-check-flat mt-0">
																	<label class="form-check-label"> <input
																		name="RowCheck" type="checkbox"
																		class="form-check-input" aria-checked="false"
																		value="${record.nno}"> <i class="input-helper"></i>
																	</label>
																</div>
															</td>
															<td>${record.nno}</td>
															<td class="admin-title"><a
																href="<c:url value="/NoticeView.do?nno=${record.nno}&nowPage="/><c:out value="${param.nowPage}" default="1"/>">${record.title}</a></td>
															<td>${record.name}(${record.id})</td>

															<td>${record.noticedate}</td>
															<td>${record.ncount}</td>
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
						<!-- 검색 -->
						<div class="row">
							<form
								class="col-md-12 d-flex justify-content-center align-items-center"
								method="post" action="<c:url value="Notice.do"/>">
								<div class="form-group row">
									<div class="col-sm-12">
										<select
											class="form-control background-color-secondary text-black"
											name="searchColumn">
											<option value="n.id">id</option>
											<option value="title">제목</option>
											<option value="content">내용</option>
										</select>
									</div>
								</div>
								<div class="form-group row">
									<div class="col-sm-12">
										<input type="text" class="form-control mx-2 my-2"
											placeholder="검색어를 입력하세요" name="searchWord" />
									</div>
								</div>
								<div class="form-group row">
									<div class="col-sm-12">
										<input type="submit"
											class="btn btn-primary mx-3 my-2 text-white" value="검색"
											id="submit" />
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

<style>
.notice-table {
	width: 100%;
}

td.admin-title a {
	text-decoration: none;
	color: black;
	font-weight: bold;
}
</style>

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
    
    //삭제 click
    $('div.card-numberOfBoard > button.delete').click(function(){
    	console.log("버튼이벤트 발생");
    	var nnoArr = new Array();
        $('input[name="RowCheck"]:checked').each(function(i){//체크된 리스트 저장
        	nnoArr.push($(this).val());
        	console.log($(this).val()); //nnoArr:63,62
        });
    	if(nnoArr.length ==0){
    		alert("선택된 글이 없습니다.");
    	}
    	else{
    		if (confirm("삭제하시겠습니까?")){
    		
    		var jsonString = JSON.stringify({nno : nnoArr})
    		$.ajax({
       			url:"<c:url value="NoticeDelete.do"/>",
       			type:"post",
       			data: jsonString,
       			contentType:"application/json", //데이타 보낼 때
       			dataType: "json" //데이타 받을 때 
       		}).done(data=>{
       			console.log('삭제 성공:',data);
       			location.replace("Notice.do");
       			
       		}).fail(error=>{
       			console.log('삭제 에러:',error);
       		});
    		
    		} else return false;
    		
    	}//////else
    });
    
  </script>
</body>
</html>

