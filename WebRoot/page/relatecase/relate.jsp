<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta charset="utf-8">
  <!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"><![endif]-->
  <title>CheckList</title>
  <meta name="keywords" content="" />
  <meta name="description" content="" />
  <meta name="viewport" content="width=device-width">        
  <link rel="stylesheet" href="../../css/templatemo_main.css">
  <link rel="stylesheet" href="../../js/ztree/css/zTreeStyle/zTreeStyle.css">
  <script type="text/javascript" src="../../js/jquery.js"></script>
  <script type="text/javascript" src="../../js/utils.js"></script>
  <script type="text/javascript" src="../../js/json.js"></script>
  <script  type="text/javascript" src="../../js/layer/layer/layer.min.js"></script>
  <script type="text/javascript" src="../../js/bootstrap.min.js"></script>
  <script type="text/javascript" src="../../js/templatemo_script.js"></script>
  <script type="text/javascript" src="../../js/ztree/js/jquery.ztree.core-3.5.js"></script>
  <script type="text/javascript" >
  $(function(){
  	findList();
  });
  function findList(){
	   $.getJSON('<%=basePath%>selenium/findTestSuitTree.do',{'productId':$("#ztProductSelect", window.parent.document).val()},
				function(data) {
						var setting = {
							view: {
								selectedMulti: false
							},
							data: {
								simpleData: {
									enable: true
								}
							},
							callback: {
									onClick: zTreeOnClick
								}
						};
					//加载树
					$.fn.zTree.init($("#treeDemo"), setting, data);	
					//默认点击第一个节点
					var zTree = $.fn.zTree.getZTreeObj("treeDemo");
					var node = zTree.getNodes()[0];
					zTree.selectNode(node);//选择点  
               		zTree.setting.callback.onClick(null, zTree.setting.treeId, node);//调用事件
				}); 
	  }
function zTreeOnClick(event, treeId, treeNode) {
	if(!treeNode.isParent){
		 $.getJSON('<%=basePath%>selenium/findCKCaseBySuitId.do',{'suitId':treeNode.id},
				function(data) {
					$("#ckcaselist").empty();
					$.each(data,function(i,n){
						var temp = "<tr class=''><td>"+(i+1)+"</td><td>"+n.name+
						"</td>"+
						  "<td><a class='btn btn-link' onclick=relate("+n.id+",this)>选择</a></td></tr>";
						$("#ckcaselist").append(temp);
					});
				});
	}
};
function relate(id,dom){
	var ztcaseId = window.parent.ztcaseId;
	var relateId = window.parent.relateId;
	if(relateId==''||relateId==null){
		//新增关联
		$.getJSON('<%=basePath%>selenium/addRelate.do',{'ztcaseId':ztcaseId,'ckcaseId':id},
				function(data) {
					$(window.parent.cdom).parent().prev().text($(dom).parent().prev().text());
					$(window.parent.cdom).attr("onclick","relateFrame("+ztcaseId+","+data+",this)");
					$(window.parent.cdom).next().attr("onclick","clearRelate("+ztcaseId+","+data+",this)");
					parent.layer.close(window.parent.relateframe);
				});
	}else{
		//更新关联
		 $.getJSON('<%=basePath%>selenium/updateRelate.do',{'relateId':relateId,'ckcaseId':id},
				function(data) {
					$(window.parent.cdom).parent().prev().text($(dom).parent().prev().text());
					parent.layer.close(window.parent.relateframe);
				}); 
	}
	
}

function execSelenium(productId){
	$(".panel-body").htmlLoad("page/selenium/starttest.jsp?productId="+productId);
}
  </script>
</head>
<body style="background-color:#fff">

      <div class="templatemo-content-wrapper">
        <div class="">
       <div class="templatemo-panels">   
		<div class="">
              <div class="col-md-2" >
                <div class="panel panel-primary">
                  <div class="panel-heading">关联用例</div>
                  	<div style="width:20%;float:left;">
	                 	<div class="zTreeDemoBackground left">
							<ul id="treeDemo" class="ztree"></ul>
						</div>
					</div>
					<div style="width:80%;float:right;">
						<div class="panel panel-primary">
		                  <div class="">
		                    <table class="table table-striped table-hover table-bordered">
								<thead>
									<tr>
										<th >序号</th>
										<th >用例名称</th>
										<th>关联</th>
									</tr>
								</thead>
								<tbody id="ckcaselist">
					
								</tbody>
							</table>
		                  </div>
		                </div>
					</div>
                 </div>                       
              </div>
            </div>
         </div> 
        </div>
      </div>
     
     


</body>
</html>
