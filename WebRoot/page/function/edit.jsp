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
	var notchecked = new Array();
	var add = new Array();
	var del = new Array();
		$(function(){
			//装载project
			$.getJSON('main/findPointCheckFlag.do',{'functionId':$("#id").val()}, function(data) {
				if(data!=null&&data.length!=0){
					$("#name").val(data[0].functionName);
					$.each(data,function(i,n){
						if(n.flag==0){
							notchecked.push(n.id);
							var temp = "<tr ><td title='"+n.remark+"'>"+n.title+"</td>"
							+"<td><a class='btn btn-link' onclick=addCheckPoint(this,'"+n.id+"')>添加</a></td></tr>";
							$("#pointaddList").append(temp);
						}else{
							var temp = "<tr ><td title='"+n.remark+"'>"+n.title+"</td>"
							+"<td><a class='btn btn-link' onclick=deleteCheckPoint(this,'"+n.id+"')>删除</a></td></tr>";
							$("#pointList").append(temp);
						}
						
					});
				}
				
		    });
		    /***************************************************/
		    //添加
			$("#submit").click(function(){
				var checkarray = new Array();
				$("[name='checkbox']:checked").each(function(){    
					checkarray.push($(this).val());    
				}); 
				var name = $("#name").val();
				var functionId = $("#id").val();
				if(name==""){
					alert("请输入功能名");
					return;
				}
				//alert("add:"+add.toString()+"  del:"+del.toString());
				$.getJSON('main/updateFunction.do',{'name':name,'remark':'','functionId':functionId,'add':add.toString(),'del':del.toString()}, function(data) {
				   if(data.result==0){
						alert("编辑失败！");
					 }else if(data.result==2){
						 alert("编辑成功，但是设置检查点时出错！");
							window.location="function.jsp";
						}else{
							alert("编辑成功");
							window.location="function.jsp";
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
		function deleteCheckPoint(dom,pointId){
			var temp = "<tr ><td title='"+$(dom).parent().prev().attr("title")+"'>"+$(dom).parent().prev().text()+"</td>"
			+"<td><a class='btn btn-link' onclick=addCheckPoint(this,'"+pointId+"')>添加</a></td></tr>";
			$("#pointaddList").append(temp);
			$(dom).parent().parent().remove();
			var index = notchecked.indexOf(pointId);
			if(index>=0){
				alert(index);
				add = add.del(add.indexOf(pointId));
			}else{
				del.push(pointId);
			} 
		//	alert("add:"+add.toString()+"  del:"+del.toString()+"  nochecked:"+notchecked.toString());
			
		}
		function addCheckPoint(dom,pointId){
			var temp = "<tr ><td title='"+$(dom).parent().prev().attr("title")+"'>"+$(dom).parent().prev().text()+"</td>"
			+"<td><a class='btn btn-link' onclick=deleteCheckPoint(this,'"+pointId+"')>删除</a></td></tr>";
			$("#pointList").append(temp);
			$(dom).parent().parent().remove();
			var index = del.indexOf(pointId);
			if(index>=0){
				del = del.del(index);
			}else{
				add.push(pointId);
			}
			//alert("add:"+add.toString()+"  del:"+del.toString()+"  nochecked:"+notchecked.toString());
		}
	</script>
  </head>
  
 <body>
  <input type="hidden" id="id" value="<%=request.getParameter("id") %>">
  <div id="main-wrapper">
    <div class="template-page-wrapper">
      <div class="templatemo-content-wrapper">
        <div class="yudh_templatemo-content">
          <ol class="breadcrumb" >编辑
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
                </div>
              </div>
				<div class="row">
				<div class="table-responsive">
                <table class="table table-striped table-hover table-bordered">
                  <thead><label for="" style="color:#3c763d" class="control-label">已添加</label>
                    <tr>
                      <th>检查点</th>
                       <th>操作</th>
                    </tr>
                  </thead>
                  <tbody id="pointList">
                    
                    <!-- <tr >
                      <td>2</td>
                      <td>Bill</td>
                      <td>Digital</td>
                    </tr> -->
                                        
                  </tbody>
                </table>
                <table class="table table-striped table-hover table-bordered">
                  <thead><label for="" style="color:#3c763d" class="control-label">可添加</label>
                    <tr>
                      <th>检查点</th>
                       <th>操作</th>
                    </tr>
                  </thead>
                  <tbody id="pointaddList">
                    
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
