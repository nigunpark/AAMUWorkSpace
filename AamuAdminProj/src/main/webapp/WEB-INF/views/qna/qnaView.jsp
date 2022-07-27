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
						<table class="table table-bordered">
							<tbody class="table-sm">
								<tr>
									<th class="w-25 bg-dark text-white text-center">번호</th>
									<td>${record.qno}</td>
								</tr>
								<tr>
									<th class="w-25 bg-dark text-white text-center">글쓴이</th>
									<td>${record.name}</td>
								</tr>
								<tr>
									<th class="w-25 bg-dark text-white text-center">작성일</th>
									<td>${record.qdate}</td>
								</tr>

								<tr>
									<th class="w-25 bg-dark text-white text-center">조회수</th>
									<td>${record.qcount}</td>
								</tr>

								<tr>
									<th class="w-25 bg-dark text-white text-center">제목</th>
									<td>${record.title}</td>
								</tr>
								<tr>
									<th class="bg-dark text-white text-center" colspan="2">내 용</th>
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



						<!-- 한줄 코멘트 입력 폼 -->
						<form id="form"
							class="form-inline col-sm-12 d-flex justify-content-center mt-3">
							<input type="hidden" name="qno" value="${record.qno}" />
							<!-- 댓글 수정용 -->
							<input type="hidden" name="ano" /> <input type="text"
								id="answer" name="answer" class="form-control mx-2 w-50"
								placeholder="한줄 댓글을 입력하세요" /> <input type="button"
								class="btn btn-danger mx-2" value="등록" id="submit" />
						</form>


						<!-- 한줄 코멘트 목록 -->
						<div class="row d-flex justify-content-center mt-3">
							<div class="col-sm-8">
								<table class="table table-hover text-center">
									<thead>
										<tr>
											<th class="col-2">작성자</th>
											<th>코멘트</th>
											<th class="col-2">작성일</th>
											<th class="col-2">삭제</th>
										</tr>
									</thead>
									<tbody class="table-sm down-file-body" id="answer-list">
										<c:if test="${empty record.answer}" var="isEmpty">
											<tr>
												<td colspan="4">등록된 한줄 댓글이 없습니다.</td>
											</tr>
										</c:if>


										<c:if test="${not isEmpty }">
											<c:forEach items="${record.answer}" var="item">

												<tr>

													<td>${item.name }</td>
													<td class="text-left ${titleClass}" title="${item.ano}">${item.answer }</td>
													<td>${item.adate }</td>
													<td><button class="btn btn-info btn-sm delete">삭제</button></td>

												</tr>
											</c:forEach>
										</c:if>
									</tbody>
								</table>
							</div>
						</div>

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
.col-sm-8 {
	width: 100% !important;
}
</style>

<script>

//코멘트 등록 및 수정처리
$('#submit').click(function(){
 console.log('등록버튼 클릭!!!:',$(this).val());
 console.log($("#form").serialize());
 var action;
 if($(this).val()==="등록"){
    action="<c:url value="/qna/answer/Write.do"/>";
 }
 else{//수정
    action="<c:url value="/qna/answer/Edit.do"/>";
 }

 //ajax로 요청
 $.ajax({
    url:action,
    data:$("#form").serialize(),
    dataType:'json',//{lno:입력된댓글번호,name:댓글 작성한 이름}
    type:"post"         
 }).done(function(data){
    console.log('서버에서 받은 데이타:',data);
    //입력후 입력된 댓글 뿌려주기위한 함수 호출:함수안에서 다시 모든 댓글 목록을 AJAX요청이 이루어짐
    //showComments();
    //성능개선 - 댓글목록 요청 하지 않고 초기데이타에 입력한 댓글을 추가한다
    console.log("입력한 댓글:",{id: data.id,answer:$("#answer").val(),adate:new Date().getTime(),name: data.name,no:${record.qno}});

    if($('#submit').val()==="등록"){
       var tr="<tr><td>"+data.name+"</td><td title=\""+data.ano+"\" class=\"text-left answer\">"+$("#answer").val()+"</td><td>"+(getDate(new Date().getTime()))+"</td><td><span class=\"btn btn-info btn-sm delete\">삭qwe제</span></td></tr>";
       $('#answer-list').before(tr);
    }
    else{//수정
       $('#submit').val("등록");
       $('td[title='+data.ano+']').html(data.answer);
       console.log("수정하려는 댓글 번호:",data.ano);
       
    }
    
    
    
   
    
    //입력값 클리어 및  포커스 주기
    $("#answer").val("");
    $("#answer").focus();
    
 }).fail(function(error){console.log('에러:',error)});
});///////// 등록 및 수정버튼 클릭

//※댓글 목록의 제목 클릭시-click이벤트걸때 반드시  $(document).on('이벤트명','셀렉터',콜백함수)으로
//그래야 동적으로 추가된 요소에도 이벤트가 발생한다
    
    $(document).on('click','.answer',function(){
 console.log($(this).html());
 //입력상자값을 클릭한 제목을고 변경
 $('#answer').val($(this).html());      
 //버튼의 텍스트를 수정으로 변경
 $('#submit').val("수정");
 //폼의 hidden인 lno의 value를 클릭한 제목의 lno값으로 설정
 $('input[name=qno]').val($(this).attr("title"));
 console.log("ano히든값 설정 확인:",$('input[name=ano]').val());
});
    

    $(document).on('click','.delete',function(){
     if(confirm("정말로 삭제하시겠습니까?")){
        console.log($(this).parent().prev().prev().attr('title'))
        var ano = $(this).parent().prev().prev().attr('title');
        var this_=$(this);
        $.ajax({
           url:"<c:url value="/qna/answer/Delete.do"/>",
           type:"post",
           data:"ano="+ano,
           dataType:'json'
        }).done(data=>{
           console.log('삭제성공:',data);
           //tr태그 제거
           this_.parent().parent().remove();
        }).fail(error=>{
           console.log('삭제에러:',error);
        });
     }
    });
    

    function getDate(dateNumber){
     var date = new Date(dateNumber);
     return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
     
    }////////////////
    
    

//메모글 삭제
function qnaDelete(key){
	if(confirm("삭제 할래요?")){
		location.replace("<c:url value="QNAViewDelete.do?qno="/>"+key);
	}
}

</script>

</body>
</html>