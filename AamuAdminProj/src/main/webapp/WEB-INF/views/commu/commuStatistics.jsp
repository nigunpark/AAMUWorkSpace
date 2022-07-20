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
						<!--차트 정보 시작-->
						<!-- 월별 게시물 수 -->
						<div class="row">
							<div class="col-lg-6 grid-margin stretch-card">
								<div class="card">
									<div class="card-body">
										<h4 class="card-title">월별 게시물 수</h4>
										<canvas id="lineChart"></canvas>
									</div>
								</div>
							</div>
							<!-- 성별 게시물 비율 -->
							<div class="col-lg-6 grid-margin stretch-card">
								<div class="card">
									<div class="card-body">
										<h4 class="card-title">성별 게시물 비율</h4>
										<canvas id="doughnutChart"></canvas>
									</div>
								</div>
							</div>
						</div>
						<!--차트 정보 끝-->
						<div class="col-lg-12 grid-margin stretch-card">
							<div class="card">
								<div class="card-body">
									<h4 class="card-title">베스트 글쓴이</h4>
									<p class="card-description">
										좋아요를 가장 많이 받은 순위
										<code>1위~5위</code>
									</p>
									<div class="table-responsive">
										<table class="table table-striped">
											<thead>
												<tr>
													<th>순위</th>
													<th>프로필사진</th>
													<th>id</th>
													<th>좋아요 수</th>
													<th>게시글 수</th>
													<th>가입일</th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td>1위</td>
													<td class="py-1"><img
														src="../../images/faces/face1.jpg" alt="image" /></td>
													<td>Herman Beck</td>

													<td>100</td>
													<td>1234</td>
													<td>2022-07-12</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
							</div>
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
	$(function() {
		'use strict';
		//월별 게시물 수
		var data = {
			labels : [ "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" ],
			datasets : [ {
				label : '# of Votes',
				data : [ 0,0,0,0,0,0,${commuMonthTotal},0,0,0,0,0 ],
				backgroundColor : [ 'rgba(255, 99, 132, 0.2)',
						'rgba(54, 162, 235, 0.2)', 'rgba(255, 206, 86, 0.2)',
						'rgba(75, 192, 192, 0.2)', 'rgba(153, 102, 255, 0.2)',
						'rgba(255, 159, 64, 0.2)' ],
				borderColor : [ 'rgba(255,99,132,1)', 'rgba(54, 162, 235, 1)',
						'rgba(255, 206, 86, 1)', 'rgba(75, 192, 192, 1)',
						'rgba(153, 102, 255, 1)', 'rgba(255, 159, 64, 1)' ],
				borderWidth : 1,
				fill : false
			} ]
		};

		//월별 게시물 수
		var options = {
			scales : {
				yAxes : [ {
					ticks : {
						beginAtZero : true
					}
				} ]
			},
			legend : {
				display : false
			},
			elements : {
				point : {
					radius : 0
				}
			}

		};

		//성별 게시물 비율
		var doughnutPieData = {
			datasets : [ {
				data : [ ${femaleRecordCount}, ${maleRecordCount} ],
				backgroundColor : [ 'rgba(153, 102, 255, 0.5)', 'rgba(255, 206, 86, 0.5)'],
				borderColor : [ 'rgba(153, 102, 255, 1)', 'rgba(255, 206, 86, 1)'],
			} ],
			labels : [ '여성', '남성',]
		};

		//성별 게시물 비율
		var doughnutPieOptions = {
			responsive : true,
			animation : {
				animateScale : true,
				animateRotate : true
			}
		};

		//월별 게시물 수
		if ($("#lineChart").length) {
			var lineChartCanvas = $("#lineChart").get(0).getContext("2d");
			var lineChart = new Chart(lineChartCanvas, {
				type : 'line',
				data : data,
				options : options
			});
		}

		//성별 게시물 비율
		if ($("#doughnutChart").length) {
			var doughnutChartCanvas = $("#doughnutChart").get(0).getContext(
					"2d");
			var doughnutChart = new Chart(doughnutChartCanvas, {
				type : 'doughnut',
				data : doughnutPieData,
				options : doughnutPieOptions
			});
		}

	});
</script>
</body>
</html>

