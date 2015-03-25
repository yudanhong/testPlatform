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
		
		$(function() {
			findList();
		});
		
		
	</script>
  </head>
  
 <body>

   	<div class="panel-heading" >列表
   	</div>
	<div class="panel-body">
		<table class="table table-striped table-hover table-bordered">
			<thead>
				<tr>
					<th width="10%">选择</th>
					<th width="80%">功能</th>
					<!-- <th>备注</th> -->
					<th width="10%">操作</th>
				</tr>
			</thead>
			<tbody id="functionlist">

			</tbody>
		</table>
	</div>
	</div>
</body>
</html>
