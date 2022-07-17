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
                 	<div class="col-6 grid-margin stretch-card">
                      <div class="card">
                        <div class="card-body">
                          <h4 class="card-title">사용자 활동</h4>
                          <canvas id="linechart-multi"></canvas>
                        </div>
                      </div>
                     </div>
                    <div class="col-6 grid-margin stretch-card">
                      <div class="card">
                        <div class="card-body">
                          <h4 class="card-title">사용자 활동</h4>
                          <canvas id="barChart2"></canvas>
                        </div>
                      </div>
                     </div>
	              </div>
                  <!--차트 정보 끝-->
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
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script>
$(function() {
	  /* ChartJS
	   * -------
	   * Data and config for chartjs
	   */
	  'use strict';
	  var date =[];
	  date=${date}
	  var data = {
	    labels: date,
	    datasets: [{
	      label: '가입자 수',
	      data: ${join},
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
	  var data2 = {
	    labels: date,
	    datasets: [{
	      label: '총 회원수',
	      data: ${usersWeek},
	      borderWidth: 1,
	      fill: true
	    }]
	  };
	  var data3 = {
			    labels: ["커뮤니티","게시판","플래너"],
			    datasets: [{
			      label: '등록된 글',
			      data: [${commuCount},${bbsCount},${plannerCount}],
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
	    labels: date,
	    datasets: [{
	        label: '게시판',
	        data: ${bbs},
	        borderColor: [
	          '#587ce4'
	        ],
	        borderWidth: 2,
	        fill: false
	      },
	      {
	        label: '커뮤니티',
	        data: ${commu},
	        borderColor: [
	          '#ede190'
	        ],
	        borderWidth: 2,
	        fill: false
	      },
	      {
	        label: '플래너',
	        data: ${planner},
	        borderColor: [
	          '#ede190'
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
	      display: true
	    },
	    elements: {
	      point: {
	        radius: 0
	      }
	    }

	  };
	  var options2 = {
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
	      options: options2
	    });
	  }
	  if ($("#barChart2").length) {
		    var barChartCanvas = $("#barChart2").get(0).getContext("2d");
		    // This will get the first returned node in the jQuery collection.
		    var barChart = new Chart(barChartCanvas, {
		      type: 'bar',
		      data: data3,
		      options: options2
		    });
		  }
	
	  if ($("#lineChart").length) {
	    var lineChartCanvas = $("#lineChart").get(0).getContext("2d");
	    var lineChart = new Chart(lineChartCanvas, {
	      type: 'line',
	      data: data2,
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

