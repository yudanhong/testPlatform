<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'addmodel.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/jquery.js"></script>
  	<script type="text/javascript" src="js/utils.js"></script>
	<script type="text/javascript">
		$(function(){
		    /***************************************************/
		    //添加
			$("#addpoint").click(function(){
				var title = $("#title").val();
				var remark = $("#remark").val();
				var modelId = $("#modelSelect").val();
				if(title==""){
					alert("请输入检查点标题");
					return;
				}
				$.getJSON('main/savePoint.do',{'title':title,'remark':remark,'modelId':modelId}, function(data) {
				   if(data.result==0){
						alert("添加失败！");
					 }else{
						 alert("添加成功！");
						 	$(".tab.active").removeClass("active");
							$("#list").prev().addClass("active");
							$(".panel-primary").htmlLoad("page/point/listpoint.jsp");
						}
			    });
			});
			/***************************************************/
		});
	</script>
  </head>
  
 <body>
  <div id="main-wrapper">
    <div class="template-page-wrapper">
      <div class="templatemo-content-wrapper">
        <div class="yudh_templatemo-content">
          <ol class="breadcrumb" >
          </ol>
          <h1></h1>
          <p class="margin-bottom-15"></p>
          <div class="row">
            <div class="col-md-12">
              <table id="templatemo-preferences-form">
                
                <div class="has-success has-feedback">
                  <div class="row">
                   <div class="col-md-12 margin-bottom-15">
                    <label class="control-label" for="inputSuccess">检查点</label>
                    <input type="text" class="form-control" id="title">
                    <span class="fa fa-check form-control-feedback"></span>
                  </div> 
                </div>
              </div>
				<div class="row">
                <div class="col-md-12 margin-bottom-15">
                  <label for="notes">备注</label>
                  <textarea class="form-control" rows="3" id="remark"></textarea>
                </div>
              </div>
                            
              <div class="row templatemo-form-buttons">
                <div class="col-md-12">
                  <button type="button" class="btn btn-primary" id="addpoint">提交</button>
                  <!-- <button type="reset" class="btn btn-default">取消</button>   -->  
                </div>
              </div>
            </table>
          </div>
        </div>
      </div>
    </div>

   
  </div>
</div>
</body>
</html>