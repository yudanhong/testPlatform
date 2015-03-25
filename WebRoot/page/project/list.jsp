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
			findList();
		});
		function findList(){
			$.getJSON('main/findProject.do',null, function(data) {
				$("#projectlist").empty();
				$.each(data,function(i,n){
					var temp = "<tr><td ><input value="+n.id+" type='checkbox'></td><td>"+n.name+"</td><td>"+n.begin+"</td></tr>";
					$("#projectlist").append(temp);
				});
		    });
		}
	</script>
  </head>
  
 <body>

   	<div class="panel-heading">列表</div>
	<div class="panel-body">
		<table class="table table-striped table-hover table-bordered">
			<thead>
				<tr>
					<th >选择</th>
					<th >项目名</th>
					<th>开始日期</th>
					<!-- <th>备注</th> -->
				</tr>
			</thead>
			<tbody id="projectlist">

			</tbody>
		</table>
	</div>
	</div>
</body>
</html>
