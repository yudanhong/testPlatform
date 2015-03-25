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
  <script type="text/javascript" src="../js/jquery.js"></script>
  <script type="text/javascript" src="../js/utils.js"></script>
  <script type="text/javascript" src="../js/json.js"></script>
   <script type="text/javascript" src="../js/bootstrap.min.js"></script>
   <script type="text/javascript" src="../js/templatemo_script.js"></script>
  <script type="text/javascript" >
  $(function(){
  //产品列表
	  findList();
  });
  function findList(){
	  $.getJSON('<%=basePath%>selenium/findZTProduct.do',{},
				function(data) {
					$("#ztProductlist").empty();
					$.each(data,function(i,n){
						var temp = "<tr><td>"+(i+1)+"</td><td><a class='btn btn-link' onclick=gotoTestSuit("+n.id+",'"+n.name+"')>"+n.name+"</a></td><td>"+n.createdDate+"</td><td>"
						+n.createdBy+"</td>"+
						  "<td><div class='btn-group'>"+
                          	"<button type='button' class='btn btn-info'>操作</button>"+
                          	"<button type='button' style='height:34px;' class='btn btn-info dropdown-toggle' data-toggle='dropdown'>"+
                           		" <span class='caret'></span>"+
                           		" <span class='sr-only'>Toggle Dropdown</span></button>"+
                           	"<div style='margin-bottom:-50px;margin-left:80px;'><a class='btn btn-link' onclick=gotoTestSuit("+n.id+",'"+n.name+"')>用例集</a></div>"+
                           "<ul class='dropdown-menu' role='menu'>"+
                           	"<li><a href='javascript:execSelenium("+n.id+")'>编辑测试环境</a></li>"+
                          	 "<li><a href='gotoTestResult'>测试结果</a></li>"+
                         " </ul>"+
                        "</div></td></tr>";
						$("#ztProductlist").append(temp);
					});
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
          <li class="active"><a href="selenium.jsp"><i class="fa fa-home"></i>selenium自动检查</a></li>
          <li class=""><a href="relatecase.jsp"><i class="fa fa-home"></i>关联用例</a></li>
        </ul>
      </div><!--/.navbar-collapse -->

      <div class="templatemo-content-wrapper">
        <div class="templatemo-content">
          <ol class="breadcrumb">
            <li>当前页：selenium自动检查</li>
          </ol>
          

          
          <div class="templatemo-panels">
            
            <div class="row">
              <div tyle="width:100%;" class="yudh_col-md-6 col-sm-6">
                <!-- Tab panes -->
                <div class="tab-content">
                <div class="row">
              <div class="yudh_col-md-6 col-sm-6 margin-bottom-30">
                <div class="panel panel-primary">
                  <div class="panel-heading">产品列表
                  </div>
                  <div class="panel-body">
                    <table class="table table-striped table-hover table-bordered">
						<thead>
							<tr>
								<th >序号</th>
								<th >产品名称</th>
								<th >创建时间</th>
								<th>创建人</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="ztProductlist">
			
						</tbody>
					</table>
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
