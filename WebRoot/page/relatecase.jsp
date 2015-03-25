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
  <link rel="stylesheet" href="../css/templatemo_main.css">
  <link rel="stylesheet" href="../js/ztree/css/zTreeStyle/zTreeStyle.css">
  <script type="text/javascript" src="../js/jquery.js"></script>
  <script type="text/javascript" src="../js/utils.js"></script>
  <script type="text/javascript" src="../js/json.js"></script>
  <script  type="text/javascript" src="../js/layer/layer/layer.min.js"></script>
  <script type="text/javascript" src="../js/bootstrap.min.js"></script>
  <script type="text/javascript" src="../js/templatemo_script.js"></script>
  <script type="text/javascript" src="../js/ztree/js/jquery.ztree.core-3.5.js"></script>
  <script type="text/javascript" >
  var ztcaseId,relateId,relateframe,cdom;
  $(function(){
  	findZTProduct();
  });
  function findZTProduct(){
  	$.getJSON('<%=basePath%>selenium/findZTProduct.do',{},
				function(data) {
					$("#ztProductSelect").empty();
					$.each(data,function(i,n){
						var temp = "<option value="+n.id+">"+n.name+"</option>"
						$("#ztProductSelect").append(temp);
					});
					//默认查询第一个产品的module信息
					findList();
				}); 
  }
  function findList(){
	   $.getJSON('<%=basePath%>selenium/findZTModuleByProductId.do',{'productId':$("#ztProductSelect").val()},
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
					$("#relatecaselist").empty();
				}); 
	  }
function zTreeOnClick(event, treeId, treeNode) {
	if(!treeNode.isParent){
		 $.getJSON('<%=basePath%>selenium/findZTCasebyModuleId.do',{'moduleId':treeNode.id},
				function(data) {
					$("#relatecaselist").empty();
					$.each(data,function(i,n){
						var color= n.lastRunResult=='pass'?'success':n.lastRunResult=='fail'?'danger':'';
						var temp = "<tr class='"+color+"'><td>"+(i+1)+"</td><td><a class='btn btn-link'>"+n.title+
						"</a></td>"+
						"<td >"+(n.lastRunResult==null?'':n.lastRunResult)+"</td>"+
						"<td>"+(n.relatecaseName==null?'':n.relatecaseName)+"</td>"+
						  "<td><a class='btn btn-link' onclick=relateFrame("+n.id+","+n.relatecaseId+",this)>设置关联</a>"+
						  "<a class='btn btn-link' onclick=clearRelate("+n.id+","+n.relatecaseId+",this)>清除关联</a></td></tr>";
						$("#relatecaselist").append(temp);
					});
				}); 
	}
};
function relateFrame(id,relatecaseId,dom){
	cdom = dom;
	ztcaseId = id;
	relateId = relatecaseId;
	relateframe = $.layer({
	    type: 2,
	    title: false,
	    offset: ['200px',''],
	    area: ['880px', '500px'],
	    shade : [0.3, '#000'],
	    iframe: {src: 'relatecase/relate.jsp'}
	});
}
function clearRelate(id,relatecaseId,dom){
	if(relatecaseId==null)return;
	$.getJSON('<%=basePath%>selenium/clearRelate.do',{'relateId':relatecaseId},
				function(data) {
					$(dom).parent().prev().text("");
					$(dom).prev().attr("onclick","relateFrame("+id+","+null+",this)");
					$(dom).attr("onclick","clearRelate("+id+","+null+",this)");
				}); 
	
}
function gotoTestSuit(productId,productName){
	window.location = "selenium/scriptmanager.jsp?productId="+productId+"&productName="+productName;
}
function execSelenium(productId){
	$(".panel-body").htmlLoad("page/selenium/starttest.jsp?productId="+productId);
}
  </script>
</head>
<body>
  <div class="navbar navbar-inverse" role="navigation">
      <div class="navbar-header">
        <div class="logo"><h1>CheckList-keepsoft</h1></div>
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button> 
      </div>   
    </div>
    <div class="template-page-wrapper">
      <div class="navbar-collapse collapse templatemo-sidebar">
        <ul class="templatemo-sidebar-menu">
          <li>
            <form class="navbar-form">
              <input type="text" class="form-control" id="templatemo_search_box" placeholder="Search...">
              <span class="btn btn-default">Go</span>
            </form>
          </li>
          <li class=""><a href="../index.jsp"><i class="fa fa-home"></i>分类</a></li>
           <li class=""><a href="selenium.jsp"><i class="fa fa-home"></i>selenium自动检查</a></li>
           <li class="active"><a href="relatecase.jsp"><i class="fa fa-home"></i>关联用例</a></li>
        </ul>
      </div><!--/.navbar-collapse -->

      <div class="templatemo-content-wrapper">
        <div class="templatemo-content">
          <ol class="breadcrumb">
            <li>当前页：关联用例</li>
          </ol>
          <label for="singleSelect" style="margin-left:300px;">选择产品：</label>
          <select onchange="findList()" class="yudh_form-control  margin-bottom-15" id="ztProductSelect">
          	
          </select>
       <div class="templatemo-panels">   
		<div class="row">
              <div class="col-md-2 col-sm-6" >
                <div class="panel panel-success">
                  <div class="panel-heading">禅道用例</div>
                 	<div class="zTreeDemoBackground left" style="min-height:700px;">
						<ul id="treeDemo" class="ztree"></ul>
					</div>
                 </div>                       
              </div>
              <div class="col-md-9 col-sm-6">
                <div class="panel panel-primary">
                  <div class="panel-heading">关联</div>
                  <div class="panel-body">
                    <table class="table table-striped table-hover table-bordered">
						<thead>
							<tr>
								<th >序号</th>
								<th >用例标题</th>
								<th >结果</th>
								<th >关联</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="relatecaselist">
			
						</tbody>
					</table>
                  </div>
                </div>
              </div> 
            </div>
         </div> 
        </div>
      </div>
      <!-- Modal -->
      <div class="modal fade" id="confirmModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
              <h4 class="modal-title" id="myModalLabel">Are you sure you want to sign out?</h4>
            </div>
            <div class="modal-footer">
              <a href="sign-in.html" class="btn btn-primary">Yes</a>
              <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
            </div>
          </div>
        </div>
      </div>
      <footer class="templatemo-footer">
        <div class="templatemo-copyright">
          <p>Copyright &copy; 2014 keepsoft，create by yudanhong</p>
        </div>
      </footer>
    </div>

</body>
</html>
