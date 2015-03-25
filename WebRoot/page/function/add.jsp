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
			//装载project
			$.getJSON('main/findPoint.do',null, function(data) {
				$.each(data,function(i,n){
					var temp = "<tr ><td><input  name='checkbox' value="+n.id+" type='checkbox'></td><td>"+n.modelName+"</td><td title='"+n.remark+"'>"+n.title+"</td></tr>";
					$("#pointList").append(temp);
				});
		    });
		    /***************************************************/
		    //添加
			$("#submit").click(function(){
				var checkarray = new Array();
				$("[name='checkbox']:checked").each(function(){    
					checkarray.push($(this).val());    
				}); 
				var name = $("#name").val();
				var remark = $("#remark").val();
				var versionId = $("#versionSelect").val();
				if(name==""){
					alert("请输入功能名");
					return;
				}
				$.getJSON('main/saveFunction.do',{'name':name,'remark':remark,'versionId':versionId,'checkarray':checkarray.toString()}, function(data) {
				   if(data.result==0){
						alert("添加失败！");
					 }else if(data.result==2){
						 alert("添加成功，但是设置检查点时出错！");
							$(".tab.active").removeClass("active");
							$("#list").prev().addClass("active");
							$(".panel-primary").htmlLoad("page/function/list.jsp");
						}else{
							alert("添加成功");
							$(".tab.active").removeClass("active");
							$("#list").prev().addClass("active");
							$(".panel-primary").htmlLoad("page/function/list.jsp");
							}
				   
			    });
			});
			/***************************************************/
		});
		/***************************************************/
		function obj2str(o){
			   var r = [];
			   if(typeof o == "string" || o == null) {
			     return o;
			   }
			   if(typeof o == "object"){
			     if(!o.sort){
			       r[0]="{"
			       for(var i in o){
			         r[r.length]=i;
			         r[r.length]=":";
			         r[r.length]=obj2str(o[i]);
			         r[r.length]=",";
			       }
			       r[r.length-1]="}"
			     }else{
			       r[0]="["
			       for(var i =0;i<o.length;i++){
			         r[r.length]=obj2str(o[i]);
			         r[r.length]=",";
			       }
			       r[r.length-1]="]"
			     }
			     return r.join("");
			   }
			   return o.toString();
			}
	</script>
  </head>
  
 <body>
  <div id="main-wrapper">
    <div class="template-page-wrapper">
      <div class="templatemo-content-wrapper">
        <div class="yudh_templatemo-content">
          <ol class="breadcrumb" >新增
          </ol>
          <h1></h1>
          <p class="margin-bottom-15"></p>
          <div class="row">
            <div class="col-md-12">
              <table id="templatemo-preferences-form">
                
                <div class="has-success has-feedback">
                  <div class="row">
                   <div class="col-md-8 ">
                    <label for="firstName" class="control-label">功能名称</label>
                    <input type="text"  class="form-control" id="name" value="">                  
                  </div>
                  <!-- <div class="col-md-7 margin-bottom-15">
                    <label for="lastName" class="control-label">备注</label>
                    <input type="text" style="width:400px;" class="yudh_form-control" id="remark" value="">                 
                  </div> -->
                </div>
              </div>
				<div class="row">
				<div class="table-responsive">
                <!-- <h4 style="margin-left:25px;" class="margin-bottom-15">选择检查点</h4> -->
                <table class="table table-striped table-hover table-bordered">
                  <thead>
                    <tr>
                      <th>选择</th>
                      <th>分类</th>
                      <th>检查点</th>
                    </tr>
                  </thead>
                  <tbody id="pointList">
                    
                                        
                  </tbody>
                </table>
              </div>
              </div>
                            
              <div class="row templatemo-form-buttons">
                <div class="col-md-12">
                  <button type="button" class="btn btn-primary" id="submit">提交</button>
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
