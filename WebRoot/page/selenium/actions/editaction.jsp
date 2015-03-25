<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<script type="text/javascript">
	$.getJSON('<%=basePath%>selenium/getTestSuitById.do',{'id':$("#suitId").val()}, function(data) {
	   if(data!=null&&data.length!=0){
			$("#name").val(data.name);
			$("#ignore").val(data.isIgnore);
			$("#suitId").val(data.id);
		}
    });
	$("#editSuit").click(function(){
	  	var name = $("#name").val();
		var isIgnore = $("#ignore").val();
		if(name==""){
			alert("请输入用例集名称");
			return;
		}
  		var suit = {'name':name,'isIgnore':isIgnore,'id':$("#suitId").val()} ;
  		$.getJSON('<%=basePath%>selenium/editTestSuit.do',suit, function(data) {
				   if(data==0){
						alert("编辑失败！");
					 }else{
						 alert("编辑成功！");
							$(".panel-body").htmlLoad("page/selenium/actions/actions.jsp");
						}
				});
  	});
  	function gobacktoSuitList(){
  		window.location = "scriptmanager.jsp?productId="+$("#id").val();
  	}
</script>
<html>
<div class="panel panel-primary">
  <div class="panel-heading">编辑用例集</div>
</div>
<input type="hidden" id="id" value="<%=request.getParameter("productId") %>">
<input type="hidden" id="suitId" value="<%=request.getParameter("suitId")%>"/>
	<div class="row">
            <div class="col-md-6">
              <div class="has-success has-feedback">
                  <div class="row">
                   <div class="col-md-12 margin-bottom-15">
                    <label class="control-label" for="inputSuccess">用例集名称</label>
                    <input type="text" class="form-control" id="name" maxlength="12">
                    <span class="fa fa-check form-control-feedback"></span>
                  </div> 
                </div>
              </div>
                </br>
              <div class="row">
                <div class="col-md-12 margin-bottom-15">
                  <label for="notes">是否忽略</label>
                  <select id="ignore"><option value="1">是</option><option value="0">否</option></select>
                </div>
              </div>
              </br>
              </br>
              <div class="row templatemo-form-buttons">
                <div class="col-md-12">
                  <button type="button" class="btn btn-primary" id="editSuit">提交</button>
                  <button type="reset" class="btn btn-default" style="margin-left:30px;" onclick="gobacktoSuitList()">返回</button>
                </div>
              </div><table id="templatemo-preferences-form">
            </table>
          </div>
        </div>

</html>
