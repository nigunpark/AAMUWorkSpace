<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container">
	<div class="jumbotron">
		<h1>
			한줄 댓글 게시판 <small>상세보기 페이지</small>
		</h1>
		<p>
			<!-- 씨큐리티 사용시:사용자 권한 출력  -->			
			<c:forEach var="authority" items="${authorities}">
				${authority }<br/>			
			</c:forEach>
		</p>
	</div>
	<table class="table table-bordered">
		<tbody class="table-sm">
			<tr>
				<th class="w-25 bg-dark text-white text-center">번호</th>
				<td>${record.no}</td>
			</tr>
			<tr>
				<th class="w-25 bg-dark text-white text-center">글쓴이</th>
				<td>${record.name }</td>
			</tr>
			<tr>
				<th class="w-25 bg-dark text-white text-center">작성일</th>
				<td>${record.postDate}</td>
			</tr>

			<tr>
				<th class="w-25 bg-dark text-white text-center">제목</th>
				<td>${record.title}</td>
			</tr>
			<tr>
				<th class="bg-dark text-white text-center" colspan="2">내 용</th>
			</tr>
			<tr>
				<td colspan="2">${record.content }</td>
			</tr>
		</tbody>
	</table>


	<!-- 수정/삭제/목록 컨트롤 버튼 -->
	<div class="text-center">
		<%-- 
		<c:if test="${sessionScope.id==record.id}">
			<a href="<c:url value="/onememo/bbs/Edit.do?no=${record.no}"/>" class="btn btn-success">수정</a> 
			<a href="javascript:isDelete(${record.no})" class="btn btn-success">삭제</a> 
		</c:if>
		--%>
		<!-- 씨큐리티 적용시 -->
		<c:set var="session_id"><sec:authentication property="principal.username"/></c:set>		
		<c:if test="${session_id ==record.id }">		
			<a href="<c:url value="/onememo/bbs/Edit.do?no=${record.no}"/>" class="btn btn-success">수정</a> 
			<a href="javascript:isDelete(${record.no})" class="btn btn-success">삭제</a> 
		</c:if>
		
		<a href="<c:url value="/onememo/bbs/List.do?nowPage=${param.nowPage}"/>" class="btn btn-success">목록</a>
	</div>
	<!-- 한줄 코멘트 입력 폼 -->
	<form id="form" class="form-inline col-sm-12 d-flex justify-content-center mt-3">
		<input type="hidden" name="no" value="${record.no}"/>
		<!-- 댓글 수정용 -->
		<input type="hidden" name="lno"/>
		<input type="text" id="linecomment" name="linecomment" class="form-control mx-2 w-50"
			placeholder="한줄 댓글을 입력하세요" /> 
			<input type="button" class="btn btn-danger mx-2" value="등록" id="submit"/>
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
				<tbody class="table-sm down-file-body" id="comments-list">
						<c:if test="${empty record.comments }" var="isEmpty">
							<tr>
								<td colspan="4">등록된 한줄 댓글이 없습니다.</td>
							</tr>
						</c:if>	
						
						
						<c:if test="${not isEmpty }">	
							<c:forEach items="${record.comments }" var="item">
								<%-- 
								씨큐리티 미 적용시
								<c:set var="titleClass" value="${sessionScope.id==item.id ? 'linecomment' : ''}"/>						
								<c:set var="inactive" value="${sessionScope.id==item.id ? '' : 'disabled'}"/>
								--%>
								<!--  씨큐리티 적용시 -->
								<c:set var="titleClass" value="${session_id==item.id ? 'linecomment' : ''}"/>						
								<c:set var="inactive" value="${session_id==item.id ? '' : 'disabled'}"/>
								<tr>
									<td>${item.name }</td>
									<td  class="text-left ${titleClass}" title="${item.lno}">${item.lineComment }</td>
									<td>${item.lpostDate }</td>
									<td><button class="btn btn-info btn-sm delete" ${inactive}>삭제</button></td>
								</tr>
							</c:forEach>		
						</c:if>							
				</tbody>
			</table>
		</div>
	</div>
</div>
<!-- container -->

<script>
	//ajax로 서버에 데이터를 요청하는 함수]
	/*
	※AJAX에서의 요청방식
	 -GET, POST요청 :
		 data:key1=value1&key2=value2&...
		 혹은 data:{key1:value1,key2:value2,...}
		 contentType:"application/x-www-form-urlencoded"(디폴트)			 
		 스프링에서는 @RequestParam으로 데이타를 받는다
	 -POST,PUT, DELETE요청
		data:JSON.stringify({key1:value1,key2:value2,...})
	    contentType:"application/json"
	    스프링에서는 @RequestBody 로 데이터를 받는다.
	*/
	//코멘트 등록 및 수정처리
	$('#submit').click(function(){
		console.log('등록버튼 클릭!!!:',$(this).val());
		console.log($("#form").serialize());
		var action;
		if($(this).val()==="등록"){
			action="<c:url value="/onememo/comments/Write.do"/>";
		}
		else{//수정
			action="<c:url value="/onememo/comments/Edit.do"/>";
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
			console.log("입력한 댓글:",{id:"${sessionScope.id}",lineComment:$("#linecomment").val(),lpostDate:new Date().getTime(),name:"코스모",no:${record.no}});
			
			//아래 두 줄은 방법1 : 마이바티스의 <resultMap>태그의 하위태그인 <collection>태그 미 사용시일때
			//초기 데이타 배열에 입력한 댓글 맨 앞에 추가
			//initialComments.unshift({lno:data.lno,id:"${sessionScope.id}",lineComment:$("#linecomment").val(),lpostDate:new Date().getTime(),name:data.name,no:${record.no}});
			//초기 데이타로 다시 뿌리기
			//displayComments(initialComments);
			//방법2 : 마이바티스 <collection>태그 사용시
			if($('#submit').val()==="등록"){
				var tr="<tr><td>"+data.name+"</td><td title=\""+data.lno+"\" class=\"text-left linecomment\">"+$("#linecomment").val()+"</td><td>"+(getDate(new Date().getTime()))+"</td><td><span class=\"btn btn-info btn-sm delete\">삭제</span></td></tr>";
				$('#comments-list').before(tr);
			}
			else{//수정
				$('#submit').val("등록");
				$('td[title='+data.lno+']').html(data.linecomment);
				console.log("수정하려는 댓글 번호:",data.lno);
				
			}
			
			
			//입력값 클리어 및  포커스 주기
			$("#linecomment").val("");
			$("#linecomment").focus();
			
		}).fail(function(error){console.log('에러:',error)});
	});///////// 등록 및 수정버튼 클릭
	
	//※댓글 목록의 제목 클릭시-click이벤트걸때 반드시  $(document).on('이벤트명','셀렉터',콜백함수)으로
	//그래야 동적으로 추가된 요소에도 이벤트가 발생한다
	$(document).on('click','.linecomment',function(){
		console.log($(this).html());
		//입력상자값을 클릭한 제목을고 변경
		$('#linecomment').val($(this).html());		
		//버튼의 텍스트를 수정으로 변경
		$('#submit').val("수정");
		//폼의 hidden인 lno의 value를 클릭한 제목의 lno값으로 설정
		$('input[name=lno]').val($(this).attr("title"));
		console.log("lno히든값 설정 확인:",$('input[name=lno]').val());
	});
	$(document).on('click','.delete',function(){
		if(confirm("정말로 삭제하시겠습니까?")){
			console.log($(this).parent().prev().prev().attr('title'))
			var lno = $(this).parent().prev().prev().attr('title');
			var this_=$(this);
			$.ajax({
				url:"<c:url value="/onememo/comments/Delete.do"/>",
				type:"post",
				data:"lno="+lno,
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
	/*
	//방법1 : 마이바티스의 <resultMap>태그의 하위태그인 <collection>태그 미 사용시
	function showComments(){
		$.ajax({
			url:"<c:url value="/onememo/comments/List.do"/>",
			data:{"no":"${record.no}"},
			dataType:"json",
			type:"post",
			success:displayComments,
			error:function(e){console.log(e);}
			
		});
	}
	//페이지 로드시 댓글 목록을 뿌려주기위한 함수 호출	
	//showComments();
	var initialComments;//페이지로드시 서버로부터 받은 초기 코멘트 목록 데이타
	
	
	
	function displayComments(data){
		console.log('댓글 목록:',data);
		//댓글 목록 초기 코멘트 목록변수에 저장
		initialComments = data;
		var comments="<tr><td colspan=\"4\">등록된 한줄 댓글이 없습니다.</td></tr>";
		if(data.length!=0){
			comments="";
			//jQuery함수 사용
			
			//$.each(data,function(index,item){
			//	comments+="<tr>";
			//	comments+="<td>"+item.name+"</td>";
			//	comments+="<td>"+item.lineComment+"</td>";
			//	comments+="<td>"+getDate(item.lpostDate)+"</td>";
			//	comments+="<td><span class=\"btn btn-info btn-sm\">삭제</span></td>";
			//	comments+="</tr>";				
			//});
			//Vanilla js사용
			data.map(function(item){
				comments+="<tr>";
				comments+="<td>"+item.name+"</td>";
				comments+="<td>"+item.lineComment+"</td>";
				comments+="<td>"+getDate(item.lpostDate)+"</td>";
				comments+="<td><span class=\"btn btn-info btn-sm\">삭제</span></td>";
				comments+="</tr>";	
			});				
		}		
		$('#comments-list').html(comments);
	}
	*/
	//메모글 삭제
	function isDelete(key){
		if(confirm("삭제 할래요?")){
			location.replace("<c:url value="/onememo/bbs/Delete.do?no="/>"+key);
		}
	}

</script>


