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
												<h4 class="card-title card-title-dash">Pending Requests</h4>
												<p class="card-subtitle card-subtitle-dash">You have 50+
													new requests</p>
											</div>
											<div>
												<button class="btn btn-primary btn-lg text-white mb-0 me-0"
													type="button">
													<i class="mdi mdi-account-plus"></i>Add new member
												</button>
											</div>
										</div>
										<div class="table-responsive  mt-1">
											<table class="table select-table">
												<thead>
													<tr>
														<th>
															<div class="form-check form-check-flat mt-0">
																<label class="form-check-label"> <input
																	type="checkbox" class="form-check-input"
																	aria-checked="false"><i class="input-helper"></i></label>
															</div>
														</th>
														<th>Customer</th>
														<th>Company</th>
														<th>Progress</th>
														<th>Status</th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td>
															<div class="form-check form-check-flat mt-0">
																<label class="form-check-label"> <input
																	type="checkbox" class="form-check-input"
																	aria-checked="false"><i class="input-helper"></i></label>
															</div>
														</td>
														<td>
															<div class="d-flex ">
																<img src="images/faces/face1.jpg" alt="">
																<div>
																	<h6>Brandon Washington</h6>
																	<p>Head admin</p>
																</div>
															</div>
														</td>
														<td>
															<h6>Company name 1</h6>
															<p>company type</p>
														</td>
														<td>
															<div>
																<div
																	class="d-flex justify-content-between align-items-center mb-1 max-width-progress-wrap">
																	<p class="text-success">79%</p>
																	<p>85/162</p>
																</div>
																<div class="progress progress-md">
																	<div class="progress-bar bg-success" role="progressbar"
																		style="width: 85%" aria-valuenow="25"
																		aria-valuemin="0" aria-valuemax="100"></div>
																</div>
															</div>
														</td>
														<td><div class="badge badge-opacity-warning">In
																progress</div></td>
													</tr>
													<tr>
														<td>
															<div class="form-check form-check-flat mt-0">
																<label class="form-check-label"> <input
																	type="checkbox" class="form-check-input"
																	aria-checked="false"><i class="input-helper"></i></label>
															</div>
														</td>
														<td>
															<div class="d-flex">
																<img src="images/faces/face3.jpg" alt="">
																<div>
																	<h6>Wayne Murphy</h6>
																	<p>Head admin</p>
																</div>
															</div>
														</td>
														<td>
															<h6>Company name 1</h6>
															<p>company type</p>
														</td>
														<td>
															<div>
																<div
																	class="d-flex justify-content-between align-items-center mb-1 max-width-progress-wrap">
																	<p class="text-success">65%</p>
																	<p>85/162</p>
																</div>
																<div class="progress progress-md">
																	<div class="progress-bar bg-warning" role="progressbar"
																		style="width: 38%" aria-valuenow="38"
																		aria-valuemin="0" aria-valuemax="100"></div>
																</div>
															</div>
														</td>
														<td><div class="badge badge-opacity-warning">In
																progress</div></td>
													</tr>
													<tr>
														<td>
															<div class="form-check form-check-flat mt-0">
																<label class="form-check-label"> <input
																	type="checkbox" class="form-check-input"
																	aria-checked="false"><i class="input-helper"></i></label>
															</div>
														</td>
														<td>
															<div class="d-flex">
																<img src="images/faces/face4.jpg" alt="">
																<div>
																	<h6>Matthew Bailey</h6>
																	<p>Head admin</p>
																</div>
															</div>
														</td>
														<td>
															<h6>Company name 1</h6>
															<p>company type</p>
														</td>
														<td>
															<div>
																<div
																	class="d-flex justify-content-between align-items-center mb-1 max-width-progress-wrap">
																	<p class="text-success">65%</p>
																	<p>85/162</p>
																</div>
																<div class="progress progress-md">
																	<div class="progress-bar bg-danger" role="progressbar"
																		style="width: 15%" aria-valuenow="15"
																		aria-valuemin="0" aria-valuemax="100"></div>
																</div>
															</div>
														</td>
														<td><div class="badge badge-opacity-danger">Pending</div></td>
													</tr>
													<tr>
														<td>
															<div class="form-check form-check-flat mt-0">
																<label class="form-check-label"> <input
																	type="checkbox" class="form-check-input"
																	aria-checked="false"><i class="input-helper"></i></label>
															</div>
														</td>
														<td>
															<div class="d-flex">
																<img src="images/faces/face5.jpg" alt="">
																<div>
																	<h6>Katherine Butler</h6>
																	<p>Head admin</p>
																</div>
															</div>
														</td>
														<td>
															<h6>Company name 1</h6>
															<p>company type</p>
														</td>
														<td>
															<div>
																<div
																	class="d-flex justify-content-between align-items-center mb-1 max-width-progress-wrap">
																	<p class="text-success">65%</p>
																	<p>85/162</p>
																</div>
																<div class="progress progress-md">
																	<div class="progress-bar bg-success" role="progressbar"
																		style="width: 65%" aria-valuenow="65"
																		aria-valuemin="0" aria-valuemax="100"></div>
																</div>
															</div>
														</td>
														<td><div class="badge badge-opacity-success">Completed</div></td>
													</tr>
												</tbody>
											</table>
										</div>
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

