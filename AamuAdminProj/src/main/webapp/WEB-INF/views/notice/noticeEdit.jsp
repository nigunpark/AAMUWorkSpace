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
						<form method="post"
							action="<c:url value="NoticeEdit.do?nno=${record.nno}"/>">

							<div class="form-group">
								<label><kbd class="lead">제목</kbd></label> <input type="text"
									class="form-control" placeholder="제목을 입력하세요." name="title"
									value="${record.title }">
							</div>

							<input type="hidden" name="id" value="${record.id }">

							<div class="form-group">
								<label><kbd class="lead">내용</kbd></label>
								<textarea class="form-control" rows="10" name="content"
									placeholder="내용을 입력하세요." onkeydown="resize(this)"
									onkeyup="resize(this)">${record.content }</textarea>
							</div>
							<div class="text-center justify-content-center">
							<button type="submit" class="btn btn-info text-white">수정 완료</button>
							</div>
						</form>
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

textarea.form-control {
	min-height: 10rem;
	overflow-y: hidden;
	resize: none;
}

.tab-content-top {
padding: 1.5rem 1.5rem 1.5rem 1.5rem;
	border-radius: 20px;
	-webkit-box-shadow: 0 0 0 0 rgb(90 113 208/ 11%), 0 4px 16px 0
		rgb(167 175 183/ 33%);
}

</style>

<script>

function resize(obj) {
    obj.style.height = '1px';
    obj.style.height = (12 + obj.scrollHeight) + 'px';
}

</script>

</body>
</html>

