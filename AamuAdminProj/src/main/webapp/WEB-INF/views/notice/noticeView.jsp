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
									<td>${record.nno}</td>
								</tr>
								<tr>
									<th class="w-25 bg-dark text-white text-center">글쓴이</th>
									<td>${record.id }</td>
								</tr>
								<tr>
									<th class="w-25 bg-dark text-white text-center">작성일</th>
									<td>${record.noticedate}</td>
								</tr>

								<tr>
									<th class="w-25 bg-dark text-white text-center">조회수</th>
									<td>${record.ncount}</td>
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
						<!--------------------- 내용의 끝 부분입니다------------------------------------>
					</div>
				</div>
			</div>
		</div>
		<!--content-wrapper-->
	</div>
	<!--main-panel-->
</div>


</body>
</html>