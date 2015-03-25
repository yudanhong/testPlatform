<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<script type="text/javascript">
	$.getJSON('<%=basePath%>selenium/findTestCaseById.do',{'caseId':$('#caseId').val()}, function(data) {
		if(data!=null){
			$("#name").val(data.name);
			$("#expect").val(data.expect);
			$("#isIgnore").val(data.isIgnore);
			$("#ord").val(data.ord);
		}			   
	});
	$("#tit").text(suitName+" >> 编辑用例");
	$("#addCase").click(function(){
		var id = $("#caseId").val();
	  	var name = $("#name").val();
	  	var expect = $("#expect").val();
		var isIgnore = $("#isIgnore").val();
		var ord = $("#ord").val();
		if(name==""){
			alert("请输入用例名称");
			return;
		}
		if(expect==""){
			alert("请输入期望值");
			return;
		}
  		var cas = {'id':id,'name':name,'isIgnore':isIgnore,'expect':expect,'suitId':suitId,'ord':ord} ;
  		$.getJSON('<%=basePath%>selenium/editTestSCase.do',cas, function(data) {
				   if(data==0){
						alert("编辑失败！");
					 }else{
						 alert("编辑成功！");
							$(".panel-body").htmlLoad("page/selenium/actions/case.jsp?suitId="+suitId+"&suitName="+suitName+"&productId="+$("#productId").val());
						}
			    });
  	});
  	function gobacktoCaseList(){
  		$(".panel-body").htmlLoad("page/selenium/actions/case.jsp?suitId="+suitId+"&suitName="+suitName+"&productId="+$("#productId").val());
  	}
</script>
<html>
<input type="hidden" id="productId" value="<%=request.getParameter("productId") %>"/>
<input type="hidden" id="caseId" value="<%=request.getParameter("caseId") %>"/>
<div class="panel panel-primary">
  <div id="tit" class="panel-heading"></div>
</div>
	<div class="row">
            <div class="col-md-6">
              <div class="has-success has-feedback">
                  <div class="row">
                   <div class="col-md-12 margin-bottom-15">
                    <label class="control-label" for="inputSuccess">用例名称</label>
                    <input type="text" class="form-control" id="name" maxlength="12">
                    <span class="fa fa-check form-control-feedback"></span>
                  </div> 
                </div>
              </div>
                </br>
                <div class="has-success has-feedback">
                  <div class="row">
                   <div class="col-md-12 margin-bottom-15">
                    <label class="control-label" for="inputSuccess">期望值</label>
                    <input type="text" class="form-control" id="expect" maxlength="12">
                    <span class="fa fa-check form-control-feedback"></span>
                  </div> 
                </div>
              </div>
                </br>
              <div class="row">
                <div class="col-md-12 margin-bottom-15">
                  <label for="notes">是否忽略</label>
                  <select id="isIgnore"><option value="0">否</option><option value="1">是</option></select>
                </div>
              </div>
              </br>
              <div class="has-success has-feedback">
                  <div class="row">
                   <div class="col-md-12 margin-bottom-15">
                    <label class="control-label" for="inputSuccess">执行顺序</label>
                    <input onkeyup='$(this).clearNoNum(1)' type="text" class="form-control" id="ord" maxlength="8">
                    <span class="fa fa-check form-control-feedback"></span>
                  </div> 
                </div>
              </div>
              </br>
              </br>
              <div class="row templatemo-form-buttons">
                <div class="col-md-12">
                  <button type="button" class="btn btn-primary" id="addCase">提交</button>
                  <button type="reset" class="btn btn-default" style="margin-left:30px;" onclick="gobacktoCaseList()">返回</button>
                </div>
              </div><table id="templatemo-preferences-form">
            </table>
          </div>
        </div>

</html>
