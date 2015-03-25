<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"><![endif]-->
  <title>CheckList</title>
  <meta name="keywords" content="" />
  <meta name="description" content="" />
  <meta name="viewport" content="width=device-width">  
  <meta charset="utf-8">      
  <link rel="stylesheet" href="../../css/templatemo_main.css">
  <script type="text/javascript" src="../../js/jquery.js"></script>
  <script type="text/javascript" src="../../js/utils.js"></script>
  <script type="text/javascript" src="../../js/json.js"></script>
  <script type="text/javascript" src="../../js/bootstrap.min.js"></script>
  <script type="text/javascript" src="../../js/templatemo_script.js"></script>
  <script  type="text/javascript" src="../../js/layer/layer/layer.min.js"></script>
  <script type="text/javascript" src="../../js/layer/layer/extend/layer.ext.js"></script>
  <script type="text/javascript" >
  var suitId,suitName,caseId,caseName,productName,productId;
  productId = $("#id").val();
  productName = $("#curProductName").val();
  $(function(){
  	$(".panel-body").htmlLoad("page/selenium/actions/actions.jsp");
  });
  

function editInit(dom){
	$(".yudh").removeClass("active");
	$(dom).parent().addClass("active");
	/* $("#currentpage").text("init"); */
	$(".panel-body").htmlLoad("page/selenium/init/initaction.jsp");
}
function editAction(dom){
	$(".yudh").removeClass("active");
	$(dom).parent().addClass("active");
	/* $("#currentpage").text("action >> 用例集管理"); */
	$(".panel-body").htmlLoad("page/selenium/actions/actions.jsp");
}
function editEnd(dom){
	$(".yudh").removeClass("active");
	$(dom).parent().addClass("active");
	/* $("#currentpage").text("end"); */
	$(".panel-body").htmlLoad("page/selenium/end/endaction.jsp");
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
           <li class="active"><a href="../selenium.jsp"><i class="fa fa-home"></i>selenium自动检查</a></li>
           <li class=""><a href="../relatecase.jsp"><i class="fa fa-home"></i>关联用例</a></li>
        </ul>
      </div><!--/.navbar-collapse -->
		<input type="hidden" id="id" value="<%=request.getParameter("productId") %>">
      <div class="templatemo-content-wrapper">
        <div class="templatemo-content">
          <ol class="breadcrumb">
            <li ><font id="curProductName">产品：<%=request.getParameter("productName") %></font> </li>
          </ol>
          

          
          <div class="templatemo-panels">
            
            <div class="row">
              <div tyle="width:100%;" class="yudh_col-md-6 col-sm-6">
                <!-- Tab panes -->
                <div class="tab-content">
                <div class="row">
              <div class="yudh_col-md-6 col-sm-6 margin-bottom-30">
                <div class="panel panel-primary">
					<div class="row margin-bottom-10">
			            <div class="col-md-12">
			              <ul class="nav nav-pills ">
			                <li class="yudh"><a href="#" onclick="editInit(this)">init <span class="badge">42</span></a></li>
			                <li class="yudh active"><a href="#" onclick="editAction(this);">action <span class="badge">107</span></a></li>
			                <li class="yudh"><a href="#" onclick="editEnd(this);">end <span class="badge">3</span></a></li>
			              </ul>          
			            </div>
			          </div>
					<div class="panel-body">
						
					</div>
                </div>
              </div>       
            </div>
                  
                  
                  
                </div> <!-- tab-content --> 
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
