<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<script type="text/javascript">
 suitId = $("#suitId").val();
 suitName = $("#suitName").val();
 productId = $("#productId").val();
 $("#casetitle").text(suitName+" >> 用例列表");
 $(function(){
	findTestCaseList();
});
//----------------------------获取action中所有的用例集--------------------------------------------------
function findTestCaseList(){
  $.getJSON('<%=basePath%>selenium/findTestCaseBySuitId.do',{'suitId':suitId},
			function(data) {
				$("#testCaseList").empty();
				$.each(data,function(i,n){
					var temp = "<tr class="+(n.result==1?'success':(n.result==null?'':'danger'))+"><td>"+(i+1)+
						"</td><td><a class='btn btn-link' onclick=managerStep("+n.id+",'"+n.name+"')>"+n.name+"</a></td><td>"+
						n.expect+"</td><td>"+n.ord+"</td><td>"+(n.isIgnore==1?'是':'否')+"</td>"+
						"<td><a class="+(n.result==1?'yudh_success-link':(n.result==null?'yudh':'yudh_fail-link'))+" photos='"+(n.photos==null?'':n.photos)+"' onclick=lookSnapshot(this)>"+(n.result==1?'成功':(n.result==null?'':'失败'))+"</a></td>"+
						"<td>"+(n.exetime=null?'':n.exetime)+"</td>"+
					  "<td><div class='btn-group'>"+
                         	"<button type='button' class='btn btn-info'>操作</button>"+
                         	"<button type='button' style='height:34px;' class='btn btn-info dropdown-toggle' data-toggle='dropdown'>"+
                          		" <span class='caret'></span>"+
                          		" <span class='sr-only'>Toggle Dropdown</span></button>"+
                          		"<div style='margin-bottom:-50px;margin-left:80px;'><a class='btn btn-link' onclick=managerStep("+n.id+",'"+n.name+"')>测试步骤</a></div>"+
                          "<ul class='dropdown-menu' role='menu'>"+
                          	"<li><a href=javascript:editCase("+n.id+")>编辑用例</a></li>"+
                          	"<li><a href=javascript:deleteCase("+n.id+")>删除用例</a></li>"+
                          	"<li><a href=javascript:exeCase("+n.id+")>执行用例</a></li>"+
                          //	"<li><a href=javascript:managerCase("+n.id+",'"+n.name+"')>step管理</a></li>"+
                        " </ul>"+
                       "</div></td>"+
                       "<td><a class='btn btn-default' onclick=correctResult("+n.id+",this)>"+(n.result==1?'未通过':'通过')+"</a></td>"+
                       "</tr>";
					$("#testCaseList").append(temp);
				});
			});
  }
  function correctResult(id,dom){
  	$.getJSON('<%=basePath%>selenium/correctResult.do',{'result':$(dom).text()=='通过'?'1':'0','id':id},
			function(data) {
				if(data==0){
						alert("更新失败！");
					 }else{
							$(".panel-body").htmlLoad("page/selenium/actions/case.jsp?suitId="+suitId+"&suitName="+suitName+"&productId="+productId);
						}
			});
  }

//----------------------------删除用例--------------------------------------------------
function deleteCase(id){
  	$.getJSON('<%=basePath%>selenium/deleteCase.do',{'id':id},
			function(data) {
				if(data==0){
						alert("删除失败！");
					 }else{
						 alert("删除成功！");
							$(".panel-body").htmlLoad("page/selenium/actions/case.jsp?suitId="+suitId+"&suitName="+suitName+"&productId="+productId);
						}
			});
  }
  //----------------------------执行用例--------------------------------------------------
function exeCase(id){
	var selenium = {'caseId':id,'productId':productId} ;
	var j = layer.load('后台执行中...',20);
  		$.getJSON('<%=basePath%>selenium/executeCase.do',selenium, function(data) {
  				layer.close(j);
				  $(".panel-body").htmlLoad("page/selenium/actions/case.jsp?suitId="+suitId+"&suitName="+suitName+"&productId="+productId);
			    });
}
//----------------------------编辑用例--------------------------------------------------
function editCase(id){
	$(".panel-body").htmlLoad("page/selenium/actions/editcase.jsp?productId="+$("#productId").val()+"&caseId="+id);
}
//----------------------------管理用例--------------------------------------------------
function managerStep(id,name){
	$(".panel-body").htmlLoad("page/selenium/actions/step.jsp?caseId="+id+"&caseName="+name);
} 
//----------------------------至添加页面--------------------------------------------------
function toAddCase(){
	$(".panel-body").htmlLoad("page/selenium/actions/addcase.jsp?productId="+$("#productId").val());
}
function gobacktoSuitList(){
  		window.location = "scriptmanager.jsp?productId="+$("#productId").val();
  	}
function lookSnapshot(dom){
	var photos = $(dom).attr("photos").split(";");
	var  datas = new Array();
	var n = 0;
	var data;
	var path;
	for(i=0;i<photos.length;i++){
		if(photos[i]!=''){
			n = n+1;
			path ='<%=basePath%>'+photos[i];
			data = {"name":n,"pid": n,"src": path,"thumb": "", "area": [638, 851] };
			datas.push(data);
		}
		
	}
	 var phtotJson = { 
		"status": 1,
  		"msg": "", 
  		"title": "JSON图片查看", 
  		"id": 8, 
		"start": 0, 
		"data": datas
		}; 
	      layer.photos({
	        html: '',
	        json: phtotJson
	    });  
}
  
</script>
<html>
<input type="hidden" id="suitId" value="<%=request.getParameter("suitId") %>"/>
<input type="hidden" id="suitName" value="<%=request.getParameter("suitName") %>"/>
<input type="hidden" id="productId" value="<%=request.getParameter("productId") %>"/>
<div style="margin-bottom:10px;" >
	<div onclick="toAddCase()" style="width:70px;">
		<b class="yudh_operator-icon yudh_up-icon" title="新增用例"></b>
		<div class="yudh_operat-name" style="width:70px;">新增用例</div>
	</div>
	<div  style="width:70px;">
		<b class="yudh_operator-icon yudh_back-icon" ></b>
		<div class="yudh_operat-name" style="margin-top: -20px;margin-left:145px;width:100px;" onclick="gobacktoSuitList()">返回用例集</div> 
	</div>
</div>
<div class="panel panel-primary" style="margin-bottom:0px;">
  <div id="casetitle" class="panel-heading"></div>
</div>

<table class="table table-striped table-hover table-bordered">
	<thead>
		<tr>
			<th>序号</th>
			<th>用例名称</th>
			<th>期望值</th>
			<th>执行顺序</th>
			<th>是否忽略</th>
			<th>执行结果</th>
			<th>执行时间</th>
			<th>操作</th>
			<th>更正结果</th>
		</tr>
	</thead>
	<tbody id="testCaseList">
	</tbody>
</table>

</html>
