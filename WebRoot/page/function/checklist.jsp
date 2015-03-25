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
			findCheckPointList($("#id").val());
		});

		//根据功能id查询检查点
		function findCheckPointList(functionId){
			$.getJSON('main/findcheckPointByFunctionId.do',{'functionId':functionId}, function(data) {
				$("#checklist").empty();
				if(data!=null&&data.length!=0){
					$.each(data,function(i,n){
						var temp = "<tr class="+(n.flag==1?'success':'danger')+">"
							//+"<td><input value="+n.id+" type='checkbox'></td>"
							+"<td title='"+n.remark+"'>"+n.title+"</td>"
							//+"<td>"+n.remark+"</td>"
							+"<td><a class='btn btn-default' flag="+(n.flag==0?1:0)+" onclick='setChecked(this,"+n.id+")'>"+(n.flag==0?'通过':'未通过')+"</a></td>"
							+"</tr>";
							//(n.flag==0?"<a class='btn btn-default' onclick='setChecked("+n.id+")'>checked</a>":'checked')
						$("#checklist").append(temp);
					});
	  				
		  		}
			});	
		}
		//设置为通过
		function setChecked(dom,functionPointId){
			var flag = $(dom).attr("flag");
			$.getJSON('main/setChecked.do',{'functionPointId':functionPointId,'flag':flag}, function(data) {
				if(data.result==0){
					alert("更新失败！");
				}else{
					var className = (flag=='1'?'success':'danger');
					$(dom).parent().parent().attr("class",className);
					$(dom).text(flag=='1'?'未通过':'通过');
					$(dom).attr("flag",flag=='1'?'0':'1')
					//alert("更新成功！");
				}
			});	
		}
	//返回至功能页面
	function goBack(){
		$(".panel-primary").htmlLoad("page/function/list.jsp");
	}	
		
	</script>
  </head>
  
 <body>
<input type="hidden" id="id" value="<%=request.getParameter("functionId") %>">
   	<div class="panel-heading" >功能检查点列表<a style="margin-left:850px;cursor:pointer;" onclick="goBack();"><font color="#EEE">返回</font></a>
   	</div>
	<div class="panel-body">
		<table class="table table-striped table-hover table-bordered">
			<thead>
				<tr>
					<!-- <th >选择</th> -->
					<th >检查点</th>
					<th >操作</th>
				</tr>
			</thead>
			<tbody id="checklist">

			</tbody>
		</table>
	</div>
	</div>
</body>
</html>
