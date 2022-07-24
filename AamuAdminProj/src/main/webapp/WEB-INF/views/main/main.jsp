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
                            <h3 class="rate-percentage">${todayUsers}</h3>
                          </div>
                          <div>
                            <p class="statistics-title">회원</p>
                            <h3 class="rate-percentage">${users}</h3>
                          </div>
                          <div>
                            <p class="statistics-title">등록된 여행지</p>
                            <h3 class="rate-percentage">${places}</h3>
                          </div>
                        <div>
                            <p class="statistics-title">관광지</p>
                            <h3 class="rate-percentage">${attraction}</h3>
                        </div>
                        <div>
                            <p class="statistics-title">호텔</p>
                            <h3 class="rate-percentage">${hotel}</h3>
                        </div>
                        <div>
                            <p class="statistics-title">진행중인 행사</p>
                            <h3 class="rate-percentage">${event}</h3>
                        </div>
                         <div>
                            <p class="statistics-title">식당</p>
                            <h3 class="rate-percentage">${diner}</h3>
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
                          <a href="<c:url value="userstatstart.do"/>"><h4 class="card-title">총 회원 수</h4></a>
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
                  <div class="row" >
                 	<div class="col-6 grid-margin stretch-card">
                      <div class="card">
                        <div class="card-body">
                          <h4 class="card-title">사용자 활동(<a href="<c:url value="CommuStatistics.do"/>" class="text-dark">커뮤니티</a>,<a href="<c:url value="bbsStat.do"/>" class="text-dark">게시판</a>)</h4>
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
	  var joinCount = ${join};
	  var date =[];
	  var bbs =${bbs};
	  var commu =${commu};
	  var planner =${planner};
	  date=${date}
	  var userCount=${usersWeek};
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
  		  var multiAreaOptions = {
  				    plugins: {
  				      filler: {
  				        propagate: true
  				      }
  				    },
  				    elements: {
  				      point: {
  				        radius: 0
  				      }
  				    },
  				    scales: {
  				      xAxes: [{
  				        gridLines: {
  				          display: false
  				        }
  				      }],
  				      yAxes: [{
  				        gridLines: {
  				          display: false
  				        }
  				      }]
  				    }
  				  }
	  var start = $('#datepicker-popup').datepicker({
	    	format: "yyyy-mm-dd",
			endDate: '+0d',
	    	clearBtn : true,
	    	autoclose : true,
	        enableOnReadonly: true,
	        todayHighlight: true,
	      });

      var end = $('#datepicker-popup2').datepicker({
    	format: "yyyy-mm-dd",
		endDate: '+0d',
		autoclose : true,
    	clearBtn : true,
        enableOnReadonly: true,
        todayHighlight: true,
      	  });
      var startDate = null;
     $("#datepicker-popup").on("changeDate",function(e){
    	 startDate = new Date(e.date.valueOf());
    	 console.log("첫번째 startDate",startDate);
         var end = $("#datepicker-popup2").datepicker("setStartDate", startDate);
         
     });
     $("#datepicker-popup2").on("changeDate",function(e){
    	 var endDate = new Date(e.date.valueOf());
    	 console.log("두번째 startDate",startDate);
    	 console.log(endDate); 
         if(startDate!=null){
        	 var dateData = JSON.stringify({start : startDate,end : endDate})
        	 console.log("dateData:",dateData);
        	 $.ajax({
     			url:"<c:url value="userstatend.do"/>",
     			type:"post",
     			data: dateData,
     			contentType:"application/json", //데이타 보낼 때
     			dataType: "json" //데이타 받을 때 
     		}).done(data=>{
     			date=data.dateList;
     			joinCount=data.countList;
     			commu=data.commuList;
     			bbs=data.bbsList;
     			plan=data.planList;
     			barchart(date,'가입자 수',joinCount,options2,"#barChart");
     			linechart(date,data.usersList,options);
    			multilinechart(date,bbs,commu,planner,options);
     		}).fail(error=>{
     			console.log('삭제에러:',error);
     		});
         }
     });

	  'use strict';
	  barchart(date,'가입자 수',joinCount,options2,"#barChart");
	  linechart(date,userCount,options);
	  multilinechart(date,bbs,commu,planner,multiAreaOptions);
	  barchart(["커뮤니티","게시판","플래너"],'등록된 글수',[${commuCount},${bbsCount},${plannerCount}],options2,"#barChart2");
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
	  if ($("#areachart-multi").length) {
		    var multiAreaCanvas = $("#areachart-multi").get(0).getContext("2d");
		    var multiAreaChart = new Chart(multiAreaCanvas, {
		      type: 'line',
		      data: multiAreaData,
		      options: options
		    });
		  }
	});
	function barchart(date,label,count,options,barchart){
		  var data = {
				    labels: date,
				    datasets: [{
				      label: label,
				      data: count,
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
		 if ($(barchart).length) {
		    var barChartCanvas = $(barchart).get(0).getContext("2d");

		    var barChart = new Chart(barChartCanvas, {
		      type: 'bar',
		      data: data,
		      options: options
		    });
		  }
	}
	////////////////////////////////////////
	function linechart(date,count,options){
		var data = {
			    labels: date,
			    datasets: [{
			      label: '총 회원수',
			      data: count,
			      borderWidth: 1,
			      fill: true
			    }]
			  };
		  if ($("#lineChart").length) {
			    var lineChartCanvas = $("#lineChart").get(0).getContext("2d");
			    var lineChart = new Chart(lineChartCanvas, {
			      type: 'line',
			      data: data,
			      options: options
			    });
			  }
		  }
	////////////////////////////////////////////////////////
	function multilinechart(date,count1,count2,count3,options){
		  var multiLineData = {
				    labels: date,
				    datasets: [{
				        label: '게시판',
				        data: count1,
				        borderColor: [
				          '#587ce4'
				        ],
				        borderWidth: 2,
				        fill: false
				      },
				      {
				        label: '커뮤니티',
				        data: count2,
				        borderColor: [
				          '#ede190'
				        ],
				        borderWidth: 2,
				        fill: false
				      },
				      {
				        label: '플래너',
				        data: count3,
				        borderColor: [
				          '#ede190'
				        ],
				        borderWidth: 2,
				        fill: false
					  }
				    ]
				  };
		  if ($("#linechart-multi").length) {
			    var multiLineCanvas = $("#linechart-multi").get(0).getContext("2d");
			    var lineChart = new Chart(multiLineCanvas, {
			      type: 'line',
			      data: multiLineData,
			      options: options
			    });
			  }
	}
</script>
</body>
</html>

