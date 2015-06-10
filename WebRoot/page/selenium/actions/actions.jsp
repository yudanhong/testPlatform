<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<script type="text/javascript">
$(function(){
	findSeleniumSuitList();
});
//----------------------------获取action中所有的用例集--------------------------------------------------
function findSeleniumSuitList(){
  $.getJSON('<%=basePath%>selenium/findSeleniumSuitByProductId.do',{'productId':$("#id").val()},
			function(data) {
				$("#seleniumSuitList").empty();
				$.each(data,function(i,n){
					var temp = "<tr class="+(n.result==1?'success':'')+"><td>"+(i+1)+"</td><td><a class='btn btn-link' onclick=managerCase("+n.id+",'"+n.name+"')>"+n.name+"</a></td><td>"+(n.isIgnore==1?'是':'否')+"</td>"+
					  "<td><div class='btn-group'>"+
                         	"<button type='button' class='btn btn-info'>操作</button>"+
                         	"<button type='button' style='height:34px;' class='btn btn-info dropdown-toggle' data-toggle='dropdown'>"+
                          		" <span class='caret'></span>"+
                          		" <span class='sr-only'>Toggle Dropdown</span></button>"+
                          		"<div style='margin-bottom:-50px;margin-left:80px;'><a class='btn btn-link' onclick=managerCase("+n.id+",'"+n.name+"')>用例管理</a></div>"+
                          "<ul class='dropdown-menu' role='menu'>"+
                          	"<li><a href=javascript:editSuit("+n.id+")>编辑用例集</a></li>"+
                          	"<li><a href=javascript:deleteSuit("+n.id+")>删除用例集</a></li>"+
                          	"<li><a href=javascript:execSuit("+n.id+")>执行用例集</a></li>"+
                          	"<li><a href=javascript:exportScript("+n.id+")>导出脚本</a></li>"+
                          	//"<li><a href=javascript:managerCase("+n.id+",'"+n.name+"')>case管理</a></li>"+
                        " </ul>"+
                       "</div></td></tr>";
					$("#seleniumSuitList").append(temp);
				});
			});
  }
//----------------------------至添加页面--------------------------------------------------
function toAddSuit(){
	$(".panel-body").htmlLoad("page/selenium/actions/addsuit.jsp");
}
//----------------------------删除用例集--------------------------------------------------
function deleteSuit(id){
  	$.getJSON('<%=basePath%>selenium/deleteSuit.do',{'id':id},
			function(data) {
				if(data==0){
						alert("删除失败！");
					 }else{
						 alert("删除成功！");
							$(".panel-body").htmlLoad("page/selenium/actions/actions.jsp");
						}
			});
  }
//----------------------------编辑用例集--------------------------------------------------
function editSuit(id){
	$(".panel-body").htmlLoad("page/selenium/actions/editaction.jsp?suitId="+id);
}
//----------------------------管理case--------------------------------------------------
function managerCase(id,name){
	$("#currentpage").text("action");
	$(".panel-body").htmlLoad("page/selenium/actions/case.jsp?suitId="+id+"&suitName="+name+"&productId="+$("#id").val());
}
function goback(){
	window.location="../selenium.jsp";
}
//----------------------------执行用例集（按顺序执行所有case）--------------------------------
function execSuit(id){
	var selenium = {'suitId':id,'productId':$("#id").val()} ;
	$.getJSON('<%=basePath%>selenium/executeSuit.do',selenium, function(data) {
				  
	});
}
function exportScript(id){
	$.getJSON('<%=basePath%>selenium/exportScript.do',{'productId':id}, function(data) {
				  
	});
}
</script>
<html>

<div style="margin-bottom:10px;" >
	<div onclick="toAddSuit()" style="width:70px;">
		<b class="yudh_operator-icon yudh_up-icon" title="新增action"></b>
		<div class="yudh_operat-name" style="width:70px;">新增用例集</div>
	</div>
	<div  style="width:70px;">
		<b class="yudh_operator-icon yudh_back-icon" ></b>
		<div class="yudh_operat-name" style="margin-top: -20px;margin-left:145px;width:100px;" onclick="goback()">返回产品列表</div> 
	</div>
</div>
<div class="panel panel-primary" style="margin-bottom:0px;">
  <div class="panel-heading">用例集列表</div>
</div>
<table class="table table-striped table-hover table-bordered">
	<thead>
		<tr>
			<th>序号</th>
			<th>用例集</th>
			<th>是否忽略</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody id="seleniumSuitList">
	</tbody>
</table>

</html>
