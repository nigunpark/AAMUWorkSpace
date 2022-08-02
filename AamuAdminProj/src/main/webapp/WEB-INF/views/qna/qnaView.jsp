<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- textarea 개행을 위해 추가 1 -->
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- textarea 개행을 위해 추가 2 -->
<%
pageContext.setAttribute("newLineChar", "\n");
%>

<jsp:include page="/WEB-INF/views/template/top.jsp" />

<div class="main-panel">
	<div class="content-wrapper" style="background-color: white;">
		<div class="row">
			<div class="col-sm-12">
				<div class="home-tab">
					<div class="tab-content tab-content-basic">

						<!-- 템플릿 시작 -->
						<div class="tab-content-top">

							<table class="table table-bordered">
								<tbody class="table-sm">
									<tr>
										<th class="w-25 bg-dark text-white text-center">번호</th>
										<td>${record.qno}</td>
									</tr>
									<tr>
										<th class="w-25 bg-dark text-white text-center">제목</th>
										<td>${record.title}</td>
									</tr>
									<tr>
										<th class="w-25 bg-dark text-white text-center">문의</th>
										<td>${record.name}(${record.id})</td>
									</tr>
									<!-- 댓글 수에 따라서 답변 대기 및 완료로 전환 -->
									<tr>
										<th class="w-25 bg-dark text-white text-center">답변</th>
										<td><span class="answer-count"><c:set var="ac"
													value="${record.answerCount}" /> <c:choose>
													<c:when test="${ac == 0}">
														<label class="badge badge-danger">대기</label>
													</c:when>
													<c:otherwise>
														<label class="badge badge-success">완료</label>
													</c:otherwise>
												</c:choose></span></td>
									</tr>
									<tr>
										<th class="w-25 bg-dark text-white text-center">날짜</th>
										<td>${record.qdate}</td>
									</tr>
									<tr>
										<th class="w-25 bg-dark text-white text-center">조회</th>
										<td>${record.qcount}</td>
									</tr>
									<tr>
										<th class="bg-dark text-white text-center" colspan="2">내용</th>
									</tr>
									<tr>
										<td colspan="2">${record.content}</td>
									</tr>
								</tbody>
							</table>

							<!-- 수정/삭제/목록 컨트롤 버튼 -->
							<div class="text-center mt-4">
								<a href="<c:url value="QNAEdit.do?qno=${record.qno}"/>"
									class="btn btn-success">수정</a> <a
									href="javascript:qnaDelete(${record.qno})"
									class="qna-delete btn btn-success">삭제</a> <a
									href="<c:url value="QNA.do?nowPage="/><c:out value="${param.nowPage}" default="1"/>"
									class="btn btn-success">목록</a>
							</div>

							<!-- 댓글 입력 -->
							<form id="form" class="col-sm-12  justify-content-center mt-3">
								<input type="hidden" name="qno" value="${record.qno}" />

								<!-- 댓글 수정 -->
								<input type="hidden" name="ano" />
								<textarea id="answer" name="answer"
									class="form-control w-20 h-25" rows="10"
									placeholder="내용을 입력하세요."></textarea>
								<div class="d-flex justify-content-center">
									<input type="button" class="btn btn-info mt-3" value="댓글 등록"
										id="submit" />
								</div>
							</form>
						</div>
						<div class="tab-content-bottom">
							<!-- 댓글 목록 -->
							<div class="answer-index row d-flex justify-content-center">
								<div class="col-sm-8">
									<table class="table table-hover text-center">
										<thead>
											<tr>
												<th class="col-2">작성</th>
												<th>내용</th>
												<th class="col-2">날짜</th>
												<th class="col-2">액션</th>
											</tr>
										</thead>
										<tbody class="table-sm down-file-body" id="answer-list">
											<c:if test="${empty record.answer}" var="isEmpty">
												<tr>
													<td colspan="4" id="answer-list"><span
														class="no-answer">등록된 댓글이 없습니다.</span></td>
												</tr>
											</c:if>
											<c:if test="${not isEmpty }">
												<c:forEach items="${record.answer}" var="item">
													<tr>
														<c:set var="titleClass"
															value="${\"ADMIN2\"==item.id ? 'answer' : ''}" />
														<c:set var="inactive"
															value="${\"ADMIN2\"==item.id ? '' : 'disabled'}" />
														<td>${item.name}(${item.id})</td>
														<td class="text-left ${titleClass}" title="${item.ano}">
															${fn:replace(item.answer, newLineChar, "<br/>")}</td>
														<td>${item.adate }</td>
														<td>
															<button class="btn btn-sm btn-warning edit-answer"
																title="${item.ano}" value="${item.answer }">수정</button>
															<button class="btn btn-sm btn-danger delete">삭제</button>
														</td>
													</tr>
												</c:forEach>
											</c:if>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<!-- 템플릿 종료 -->

					</div>
				</div>
			</div>
		</div>
		<!--content-wrapper-->
	</div>
	<!--main-panel-->
</div>

<style>
.tab-content-top {
	padding: 1.5rem 2.187rem 1.5rem 3.5rem;
	border-radius: 20px;
	-webkit-box-shadow: 0 0 0 0 rgb(90 113 208/ 11%), 0 4px 16px 0
		rgb(167 175 183/ 33%);
}

.tab-content-bottom {
	margin-top: 30px;
	border-radius: 20px;
	-webkit-box-shadow: 0 0 0 0 rgb(90 113 208/ 11%), 0 4px 16px 0
		rgb(167 175 183/ 33%);
	padding: 1.5rem 2.187rem 1.5rem 3.5rem;
}

.col-sm-8 {
	width: 100% !important;
}

.answer-index {
	
}

.table-hover>tbody>tr:hover { -
	-bs-table-accent-bg: transparent !important;
}

.btn-warning {
	background: #ffaf00 !important;
}

.btn-danger {
	background: #F95F53 !important;
}

.answer-index .table>:not(caption)>*>* {
	border-bottom-width: 0px;
}

.answer-index tr:hover {
	background-color: transparent !important;
}

.answer-index thead th {
	border-bottom: 2px solid #ccc !important;
	padding-bottom: 20px;
}

.answer-index tr {
	border-bottom: 1px solid #dee2e6 !important;
}

.answer-index tr:last-child {
	border-bottom: 0px solid #dee2e6 !important;
}
</style>

<script>

// 댓글 등록 및 수정
$('#submit').click(function(){
 console.log('등록 요청:',$(this).val());
 console.log($("#form").serialize());
 var action;
 if($(this).val()==="댓글 등록"){
    action="<c:url value="/qna/answer/Write.do"/>";
 }
 else{ // 수정
    action="<c:url value="/qna/answer/Edit.do"/>";
 }

 $.ajax({
    url:action,
    data:$("#form").serialize(),
    dataType:'json',
    type:"post"         
 }).done(function(data){
   
    console.log('서버에서 받은 데이터:',data);
    //입력후 입력된 댓글 뿌려주기위한 함수 호출:함수안에서 다시 모든 댓글 목록을 AJAX요청이 이루어짐
    //showComments();
    //성능개선 - 댓글목록 요청 하지 않고 초기데이타에 입력한 댓글을 추가한다
    console.log("입력한 댓글:",{id: data.id,answer:$("#answer").val(),adate:new Date().getTime(),name: data.name,no:${record.qno}});

    if($('#submit').val()==="댓글 등록"){
       var tr="<tr><td>"+data.name+" ("+data.id+")</td><td title=\""+data.ano+"\" class=\"text-left answer\"><pre>"+$("#answer").val()+"</pre></td><td>"+(getDate(new Date().getTime()))+"</td><td><button class=\"btn btn-sm btn-warning edit-answer\" title=\""+data.ano+"\" value=\""+data.answer+"\">수정</button><button class=\"btn btn-danger btn-sm delete\">삭제</button></td></tr>";
       $('#answer-list').before(tr);
       answerLoad();
    }
    else{//수정
       $('#submit').val("댓글 등록");
       $('td[title='+data.ano+']').html(data.answer);
       console.log("수정하려는 댓글 번호:",data.ano);
    }
     
    // 입력값 클리어 및 포커스 주기
    $("#answer").val("");
    $("#answer").focus();
    
 }).fail(function(error){console.log('에러:',error)});
}); // 등록 및 수정 버튼 클릭
    
    $(document).on('click','.edit-answer',function(){
 console.log($(this).html());
 // 입력 상자 값을 클릭한 제목으로 변경
 $('#answer').val($(this).attr("value"));      
 // 버튼의 텍스트를 수정으로 변경
 $('#submit').val("수정 완료");
 // 폼의 hidden인 ano의 value를 클릭한 제목의 ano값으로 설정
 $('input[name=ano]').val($(this).attr("title"));
 console.log("ano히든값 설정 확인:",$('input[name=ano]').val());
});

    $(document).on('click','.delete',function(){
     if(confirm("삭제하시겠습니까?")){
        console.log($(this).parent().prev().prev().attr('title'))
        var ano = $(this).parent().prev().prev().attr('title');
        var this_=$(this);
        $.ajax({
           url:"<c:url value="/qna/answer/Delete.do"/>",
           type:"post",
           data:"ano="+ano,
           dataType:'json'
        }).done(data=>{
        	
           console.log('삭제 성공:',data);
           // tr 태그 제거
           this_.parent().parent().remove();
           answerLoad();
        }).fail(error=>{
           console.log('삭제 에러:',error);
        });
     }
    });
    
    function getDate(dateNumber){
     var date = new Date(dateNumber);
     return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
    }
    
    function answerLoad() {
    	$('.answer-count').load(location.href+' .answer-count');
    	$('.no-answer').load(location.href+' .no-answer');
    }

    
    // 게시물 삭제
    function qnaDelete(key){
    	if(confirm("삭제하시겠습니까?")){
    		location.replace("<c:url value="QNAViewDelete.do?qno="/>"+key);
    		}
    	}

</script>

</body>
</html>