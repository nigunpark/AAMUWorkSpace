<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/template/top.jsp"/>
 
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
                        <h4 class="card-title">게시글 전체 리스트</h4>
                        
                        <div class="card-numberOfBoard">
                          총 게시글 수: 0개
                          <a href="#" class="btn btn-primary text-white me-0 " style="float:right"> 삭제</a>
                        </div>
                        <div class="table-responsive text-center">
                          <table class="table">
                            <thead>
                              <tr>
                                <th class="col-1 ">
                                    <input type="checkbox" class="form-check-input" aria-checked="false" value="all"><i class="input-helper"></i>
                                </th>
                                <th class="col-1">글번호</th>
                                <th class="col-4">제목</th>
                                <th class="col-1">ID</th>
                                <th class="col-1">등록일</th>
                                <th class="col-1">댓글수</th>
                                <th class="col-1">좋아요수</th>
                              </tr>
                            </thead>
                            <tbody>
                              <tr>
                                <td>
                                  <input type="checkbox" class="form-check-input" aria-checked="false"><i class="input-helper"></i>
                                </td>
                                <td>1</td>
                                <td >제목입니다</td>
                                <td>김경희</td>
                                <td>2022.06.29</td>
                                <td>1</td>
                                <td>1</td>
                              </tr>
                              
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
    var selectLength=$(':checkbox').slice(1).length;
    $(':checkbox').click(function(){
      if($(this).val()==='all'){
        if($(this).prop('checked')) $(':checkbox').slice(1).prop('checked',true);
        else $(':checkbox').slice(1).prop('checked',false);
      }
      else{
        if($(this).prop('checked')){
          if(selectLength==$(':checkbox:checked').length){
            $(':checkbox:first').prop('checked',true)
          }             
        }
        else $(':checkbox:first').prop('checked',false)
      }
    });
  </script>
</body>
</html>

