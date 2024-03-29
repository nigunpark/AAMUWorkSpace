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
										<button class="btn btn-primary text-white me-0" style="float: right" id="crawll">이미지 크롤링</button>
										<button class="btn btn-primary text-white me-0" style="float: right" id="save">데이터 저장</button>
									</div>
									<h4 class="card-title">여행지 리스트</h4>
									<div class="card-numberOfBoard">
										데이터 수: ${totalcount}개
									</div>
									<div class="table-responsive text-center">
										<table class="table text-center">
											<thead>
												<tr>
													<th class="col-1">장소 번호</th>
													<th class="col-1">이미지</th>
													<th>주소</th>
													<th class="col-4">장소명</th>		
													<th class="col-1">지역 번호</th>
													<th class="col-1">타입 번호</th>
					
												</tr>
											</thead>
											<tbody>
												<c:if test="${empty placelist}" var="isEmpty">
													<tr>
														<td colspan="8">데이터가 없습니다.</td>
													</tr>
												</c:if>
												<c:if test="${not isEmpty }">
													<c:forEach var="record" items="${placelist}" varStatus="loop">
														<tr>
															<td>${record.contentid}</td>
															<td class=""><img src="${record.smallimage}" alt="No Img"></td>
															<td>${record.addr}</td>
															<td>${record.title}</td>
															<td>${record.areacode}</td>
															<td>${record.contenttypeid}</td>
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

$('#save').click(function(){
	var placelist =new Array();
	<c:forEach items="${placelist}" var="item">
		placelist.push({
			areacode:"${item.areacode}",
			sigungucode:"${item.sigungucode}",
			contentid:"${item.contentid}",
			contenttypeid:"${item.contenttypeid}",
			mapx:"${item.mapx}",
			mapy:"${item.mapy}",
			title:"${item.title}",
			addr:"${item.addr}",
			image:"${item.bigimage}"
			
		});
	</c:forEach>
	var placejson = JSON.stringify({"placelist":placelist});
	$.ajax({
		url:"http://192.168.0.22:5020/saveplaces",
		type:"post",
		data: placejson,
		contentType:"application/json", //데이타 보낼 때
		dataType: "json" //데이타 받을 때 
	}).done(data=>{(console.log('저장 성공'),data)}).fail(e=>{console.log('실패',e)})
});
$('#crawll').click(function(){
	console.log('클릭이벤트 발생')
	var placelist =new Array();
	<c:forEach items="${placelist}" var="item">
		<c:if test="${item.smallimage eq null}">
			placelist.push({
				title:"${item.title}",
				contenttypeid:"${item.contenttypeid}",
				contentid:"${item.contentid}"
			});
		</c:if>
	</c:forEach>
	var placejson = JSON.stringify({"placelist":placelist});
	console.log(placejson);
	$.ajax({
		url:"http://192.168.0.22:5020/imgcrawll",
		type:"post",
		data: placejson,
		contentType:"application/json", //데이타 보낼 때
		dataType: "json" //데이타 받을 때 
	    ,beforeSend:function(){ 
			var width = 0;
			var height = 0;
			var left = 0; 
			var top = 0;
			width = 50;
			height = 50;
			top = ( $(window).height() - height ) / 2 + $(window).scrollTop();
			left = ( $(window).width() - width ) / 2 + $(window).scrollLeft();
			if($("#div_ajax_load_image").length != 0) {
				$("#div_ajax_load_image").css({"top": top+"px","left": left+"px"});
				$("#div_ajax_load_image").show();             
			}
			else {
				$('body').append('<div id="div_ajax_load_image" style="position:absolute; top:' + top + 'px; left:' + left + 'px; width:' + width + 'px; height:' + height + 'px; z-index:9999; background:#f0f0f0; filter:alpha(opacity=50); opacity:alpha*0.5; margin:auto; padding:0; "><img src="<c:url value="/resources/images/imgloading.gif"/>" style="width:50px; height:50px;"></div>');
			}
	    } 
	    ,complete:function(){ 
		 		$("#div_ajax_load_image").hide();
	    }
		,success:function(res){ 
		    console.log('저장 성공',res);
			location.reload();
		         
	    } 
	    ,error:function(e){ 
			console.log('실패',e)
	    } 
	});
});
  </script>
</body>
</html>

