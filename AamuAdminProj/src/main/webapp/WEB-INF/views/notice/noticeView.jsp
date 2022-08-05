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
						<div class="tab-content-top">
						<table class="table table-bordered">
							<tbody class="table-sm">
								<tr>
									<th class="w-25 bg-dark text-white text-center">번호</th>
									<td>${record.nno}</td>
								</tr>
								<tr>
									<th class="w-25 bg-dark text-white text-center">제목</th>
									<td><span class="text-overflow">${record.title}</span></td>
								</tr>
								<tr>
									<th class="w-25 bg-dark text-white text-center">공지</th>
									<td>${record.name } (${record.id })</td>
								</tr>
								<tr>
									<th class="w-25 bg-dark text-white text-center">날짜</th>
									<td>${record.noticedate}</td>
								</tr>

								<tr>
									<th class="w-25 bg-dark text-white text-center">조회</th>
									<td>${record.ncount}</td>
								</tr>


								<tr>
									<th class="bg-dark text-white text-center" colspan="2">내용</th>
								</tr>
								<tr>
									<td colspan="2"><p>${record.content }</p></td>
								</tr>
							</tbody>
						</table>

						<!-- 수정/삭제/목록 컨트롤 버튼 -->
						<div class="text-center mt-4">

							<a href="<c:url value="NoticeEdit.do?nno=${record.nno}"/>"
								class="btn btn-success">수정</a> <a
								href="javascript:noticeDelete(${record.nno})"
								class="delete btn btn-success">삭제</a> <a
								href="<c:url value="Notice.do?nowPage="/><c:out value="${param.nowPage}" default="1"/>"
								class="btn btn-success">목록</a>

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

table {
	table-layout: fixed;
}

table td .text-overflow {
	line-height: 1.3rem;
	padding: 0px;
	text-align: left;
	white-space: pre-wrap; /* CSS3*/
	white-space: -moz-pre-wrap; /* Mozilla, since 1999 */
	white-space: -pre-wrap; /* Opera 4-6 */
	white-space: -o-pre-wrap; /* Opera 7 */
	word-wrap: break-all; /* Internet Explorer 5.5+ */
}

textarea, table td p {
	line-height: 1.5rem !important;
	text-align: left;
	white-space: pre-wrap; /* CSS3*/
	white-space: -moz-pre-wrap; /* Mozilla, since 1999 */
	white-space: -pre-wrap; /* Opera 4-6 */
	white-space: -o-pre-wrap; /* Opera 7 */
	word-wrap: break-all; /* Internet Explorer 5.5+ */
}

.tab-content-top {
padding: 1.5rem 1.5rem 1.5rem 1.5rem;
	border-radius: 20px;
	-webkit-box-shadow: 0 0 0 0 rgb(90 113 208/ 11%), 0 4px 16px 0
		rgb(167 175 183/ 33%);
}


</style>


<script>

//메모글 삭제
function noticeDelete(key){
	if(confirm("삭제하시겠습니까?")){
		location.replace("<c:url value="NoticeViewDelete.do?nno="/>"+key);
	}
}

</script>

</body>
</html>