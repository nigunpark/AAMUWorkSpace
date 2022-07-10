<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/template/top.jsp"/>
   <!--차트 데이터용 자바 스크립트-->

  <script src="<c:url value="/resources/vendors/chart.js/Chart.min.js"/>"></script>
      <div class="main-panel">
        <div class="content-wrapper">
          <div class="row">
            <div class="col-sm-12">
              <div class="home-tab">
                <div class="tab-content tab-content-basic">


                  <!--대시보드 정보 페이지-->
                  <div class="tab-pane fade show active" id="overview" role="tabpanel" aria-labelledby="overview">
                    <div class="row">
                      <div class="col-sm-11">
                        <div class="statistics-details d-flex align-items-center justify-content-between">
                          <!--정보 페이지-->
                          <div>
                            <p class="statistics-title">가입자</p>
                            <h3 class="rate-percentage">102</h3>
                          </div>
                          <div>
                            <p class="statistics-title">회원</p>
                            <h3 class="rate-percentage">102</h3>
                          </div>
                          <div>
                            <p class="statistics-title">게시판</p>
                            <h3 class="rate-percentage">200</h3>
                          </div>
                          <div>
                            <p class="statistics-title">커뮤니티</p>
                            <h3 class="rate-percentage">300</h3>
                        </div>
                        <div>
                            <p class="statistics-title">여행지</p>
                            <h3 class="rate-percentage">300</h3>
                        </div>
                        <div>
                            <p class="statistics-title">호텔</p>
                            <h3 class="rate-percentage">300</h3>
                        </div>
                        <div>
                            <p class="statistics-title">진행중인 행사</p>
                            <h3 class="rate-percentage">300</h3>
                        </div>
                         <div>
                            <p class="statistics-title">식당</p>
                            <h3 class="rate-percentage">300</h3>
                        </div>
                      </div>
                    </div>
                  </div>
                  <!--대시보드 정보 끝-->
                  <!--차트 정보 시작-->
                  <div class="row">
                    <div class="col-lg-6 grid-margin stretch-card">
                      <div class="card">
                        <div class="card-body">
                          <h4 class="card-title">총 회원 수</h4>
                          <canvas id="lineChart"></canvas>
                        </div>
                      </div>
                    </div>
                    <div class="col-lg-6 grid-margin stretch-card">
                      <div class="card">
                        <div class="card-body">
                          <h4 class="card-title">가입자 수</h4>
                          <canvas id="barChart"></canvas>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="row">
                  <div class="col-lg-12 grid-margin stretch-card">
                      <div class="card">
                        <div class="card-body">
                          <h4 class="card-title">게시물과 커뮤니티</h4>
                          <canvas id="linechart-multi"></canvas>
                        </div>
                      </div>
                    </div>
                  </div>
                  <!--차트 정보 끝-->
                  <div class="col-lg-12 grid-margin stretch-card">
                    <div class="card">
                      <div class="card-body">
                        <h4 class="card-title">Striped Table</h4>
                        <p class="card-description">
                          Add class <code>.table-striped</code>
                        </p>
                        <div class="table-responsive">
                          <table class="table table-striped">
                            <thead>
                              <tr>
                                <th>
                                  User
                                </th>
                                <th>
                                  First name
                                </th>
                                <th>
                                  Progress
                                </th>
                                <th>
                                  Amount
                                </th>
                                <th>
                                  Deadline
                                </th>
                              </tr>
                            </thead>
                            <tbody>
                              <tr>
                                <td class="py-1">
                                  <img src="../../images/faces/face1.jpg" alt="image"/>
                                </td>
                                <td>
                                  Herman Beck
                                </td>
                                <td>
                                  <div class="progress">
                                    <div class="progress-bar bg-success" role="progressbar" style="width: 25%" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
                                  </div>
                                </td>
                                <td>
                                  $ 77.99
                                </td>
                                <td>
                                  May 15, 2015
                                </td>
                              </tr>
                              <tr>
                                <td class="py-1">
                                  <img src="../../images/faces/face2.jpg" alt="image"/>
                                </td>
                                <td>
                                  Messsy Adam
                                </td>
                                <td>
                                  <div class="progress">
                                    <div class="progress-bar bg-danger" role="progressbar" style="width: 75%" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100"></div>
                                  </div>
                                </td>
                                <td>
                                  $245.30
                                </td>
                                <td>
                                  July 1, 2015
                                </td>
                              </tr>
                              <tr>
                                <td class="py-1">
                                  <img src="../../images/faces/face3.jpg" alt="image"/>
                                </td>
                                <td>
                                  John Richards
                                </td>
                                <td>
                                  <div class="progress">
                                    <div class="progress-bar bg-warning" role="progressbar" style="width: 90%" aria-valuenow="90" aria-valuemin="0" aria-valuemax="100"></div>
                                  </div>
                                </td>
                                <td>
                                  $138.00
                                </td>
                                <td>
                                  Apr 12, 2015
                                </td>
                              </tr>
                              <tr>
                                <td class="py-1">
                                  <img src="../../images/faces/face4.jpg" alt="image"/>
                                </td>
                                <td>
                                  Peter Meggik
                                </td>
                                <td>
                                  <div class="progress">
                                    <div class="progress-bar bg-primary" role="progressbar" style="width: 50%" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100"></div>
                                  </div>
                                </td>
                                <td>
                                  $ 77.99
                                </td>
                                <td>
                                  May 15, 2015
                                </td>
                              </tr>
                              <tr>
                                <td class="py-1">
                                  <img src="../../images/faces/face5.jpg" alt="image"/>
                                </td>
                                <td>
                                  Edward
                                </td>
                                <td>
                                  <div class="progress">
                                    <div class="progress-bar bg-danger" role="progressbar" style="width: 35%" aria-valuenow="35" aria-valuemin="0" aria-valuemax="100"></div>
                                  </div>
                                </td>
                                <td>
                                  $ 160.25
                                </td>
                                <td>
                                  May 03, 2015
                                </td>
                              </tr>
                              <tr>
                                <td class="py-1">
                                  <img src="../../images/faces/face6.jpg" alt="image"/>
                                </td>
                                <td>
                                  John Doe
                                </td>
                                <td>
                                  <div class="progress">
                                    <div class="progress-bar bg-info" role="progressbar" style="width: 65%" aria-valuenow="65" aria-valuemin="0" aria-valuemax="100"></div>
                                  </div>
                                </td>
                                <td>
                                  $ 123.21
                                </td>
                                <td>
                                  April 05, 2015
                                </td>
                              </tr>
                              <tr>
                                <td class="py-1">
                                  <img src="../../images/faces/face7.jpg" alt="image"/>
                                </td>
                                <td>
                                  Henry Tom
                                </td>
                                <td>
                                  <div class="progress">
                                    <div class="progress-bar bg-warning" role="progressbar" style="width: 20%" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"></div>
                                  </div>
                                </td>
                                <td>
                                  $ 150.00
                                </td>
                                <td>
                                  June 16, 2015
                                </td>
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
<script>
$(function() {
	  /* ChartJS
	   * -------
	   * Data and config for chartjs
	   */
	  'use strict';
	  var data = {
	    labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
	    datasets: [{
	      label: '# of Votes',
	      data: [10, 19, 3, 5, 2, 3, 10, 20, 30],
	      backgroundColor: [
	        'rgba(255, 99, 132, 0.2)',
	        'rgba(54, 162, 235, 0.2)',
	        'rgba(255, 206, 86, 0.2)',
	        'rgba(75, 192, 192, 0.2)',
	        'rgba(153, 102, 255, 0.2)',
	        'rgba(255, 159, 64, 0.2)'
	      ],
	      borderColor: [
	        'rgba(255,99,132,1)',
	        'rgba(54, 162, 235, 1)',
	        'rgba(255, 206, 86, 1)',
	        'rgba(75, 192, 192, 1)',
	        'rgba(153, 102, 255, 1)',
	        'rgba(255, 159, 64, 1)'
	      ],
	      borderWidth: 1,
	      fill: true
	    }]
	  };
	  var multiLineData = {
	    labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
	    datasets: [{
	        label: 'Dataset 1',
	        data: [12, 19, 3, 5, 2, 3],
	        borderColor: [
	          '#587ce4'
	        ],
	        borderWidth: 2,
	        fill: false
	      },
	      {
	        label: 'Dataset 2',
	        data: [5, 23, 7, 12, 42, 23],
	        borderColor: [
	          '#ede190'
	        ],
	        borderWidth: 2,
	        fill: false
	      },
	      {
	        label: 'Dataset 3',
	        data: [15, 10, 21, 32, 12, 33],
	        borderColor: [
	          '#f44252'
	        ],
	        borderWidth: 2,
	        fill: false
	      }
	    ]
	  };
	  var options = {
	    scales: {
	      yAxes: [{
	        ticks: {
	          beginAtZero: true
	        }
	      }]
	    },
	    legend: {
	      display: false
	    },
	    elements: {
	      point: {
	        radius: 0
	      }
	    }

	  };
	  var doughnutPieData = {
	    datasets: [{
	      data: [30, 40, 30],
	      backgroundColor: [
	        'rgba(255, 99, 132, 0.5)',
	        'rgba(54, 162, 235, 0.5)',
	        'rgba(255, 206, 86, 0.5)',
	        'rgba(75, 192, 192, 0.5)',
	        'rgba(153, 102, 255, 0.5)',
	        'rgba(255, 159, 64, 0.5)'
	      ],
	      borderColor: [
	        'rgba(255,99,132,1)',
	        'rgba(54, 162, 235, 1)',
	        'rgba(255, 206, 86, 1)',
	        'rgba(75, 192, 192, 1)',
	        'rgba(153, 102, 255, 1)',
	        'rgba(255, 159, 64, 1)'
	      ],
	    }],

	    // These labels appear in the legend and in the tooltips when hovering different arcs
	    labels: [
	      'Pink',
	      'Blue',
	      'Yellow',
	    ]
	  };
	  var doughnutPieOptions = {
	    responsive: true,
	    animation: {
	      animateScale: true,
	      animateRotate: true
	    }
	  };

	  
	  // Get context with jQuery - using jQuery's .get() method.
	  if ($("#barChart").length) {
	    var barChartCanvas = $("#barChart").get(0).getContext("2d");
	    // This will get the first returned node in the jQuery collection.
	    var barChart = new Chart(barChartCanvas, {
	      type: 'bar',
	      data: data,
	      options: options
	    });
	  }

	  if ($("#lineChart").length) {
	    var lineChartCanvas = $("#lineChart").get(0).getContext("2d");
	    var lineChart = new Chart(lineChartCanvas, {
	      type: 'line',
	      data: data,
	      options: options
	    });
	  }

	  if ($("#linechart-multi").length) {
	    var multiLineCanvas = $("#linechart-multi").get(0).getContext("2d");
	    var lineChart = new Chart(multiLineCanvas, {
	      type: 'line',
	      data: multiLineData,
	      options: options
	    });
	  }

	  if ($("#areachart-multi").length) {
	    var multiAreaCanvas = $("#areachart-multi").get(0).getContext("2d");
	    var multiAreaChart = new Chart(multiAreaCanvas, {
	      type: 'line',
	      data: multiAreaData,
	      options: multiAreaOptions
	    });
	  }

	  if ($("#pieChart").length) {
	    var pieChartCanvas = $("#pieChart").get(0).getContext("2d");
	    var pieChart = new Chart(pieChartCanvas, {
	      type: 'pie',
	      data: doughnutPieData,
	      options: doughnutPieOptions
	    });
	  }

	  if ($("#areaChart").length) {
	    var areaChartCanvas = $("#areaChart").get(0).getContext("2d");
	    var areaChart = new Chart(areaChartCanvas, {
	      type: 'line',
	      data: areaData,
	      options: areaOptions
	    });
	  }

	  if ($("#browserTrafficChart").length) {
	    var doughnutChartCanvas = $("#browserTrafficChart").get(0).getContext("2d");
	    var doughnutChart = new Chart(doughnutChartCanvas, {
	      type: 'doughnut',
	      data: browserTrafficData,
	      options: doughnutPieOptions
	    });
	  }
	});
</script>
</body>
</html>

