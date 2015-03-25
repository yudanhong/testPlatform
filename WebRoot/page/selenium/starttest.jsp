<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<script type="text/javascript">
//默认加载上次配置
	$.getJSON('<%=basePath%>selenium/findSelenium.do?productId='+$("#productId").val(), function(data) {
				   if(data!=null){
						$("#address").val(data.address);
						$("#driver").val(data.browser);
					}
			    });
	$("#saveSelenium").click(function(){
	  	var address = $("#address").val();
		var driver = $("#driver").val();
		if(address==""){
			alert("请输入测试地址");
			return;
		}
  		var selenium = {'address':address,'driver':driver,'productId':$("#productId").val()} ;
  		$.getJSON('<%=basePath%>selenium/saveSelenium.do',selenium, function(data) {
				  window.location="<%=basePath%>page/selenium.jsp";
			    });
		//alert("后台测试程序已启动");
  	});
  	function goback(){
  		window.location="selenium.jsp";
  	}
</script>
<html>
<div class="panel panel-info">
  <div class="panel-heading">执行selenium自动化</div>
  <input type="hidden" id="productId" value="<%=request.getParameter("productId") %>">
</div>
	<div class="row">
            <div class="col-md-6">
              <div class="has-success has-feedback">
                  <div class="row">
                   <div class="col-md-12 margin-bottom-15">
                    <label class="control-label" for="inputSuccess">测试地址</label>
                    <input type="text" class="form-control" id="address">
                    <span class="fa fa-check form-control-feedback"></span>
                  </div> 
                </div>
              </div>
                </br>
                <div class="has-success has-feedback">
                  <div class="row">
                   <div class="col-md-12 margin-bottom-15">
                    <label class="control-label" for="inputSuccess">浏览器</label>
                    <select id="driver">
                    	<option value="ie">IE</option>
                    	<option value="chrome">chrome</option>
                    	<option value="firefox">火狐</option>
                    </select>
               		<span class="fa fa-check form-control-feedback"></span>
                  </div> 
                </div>
              </div>
                </br>
              </br>
              </br>
              <div class="row templatemo-form-buttons">
                <div class="col-md-12">
                  <button type="button" class="btn btn-primary" id="saveSelenium">保存</button>
                   <button type="reset" class="btn btn-default" style="margin-left:30px;" onclick="goback()">返回</button>  
                </div>
              </div><table id="templatemo-preferences-form">
            </table>
          </div>
        </div>
</html>
