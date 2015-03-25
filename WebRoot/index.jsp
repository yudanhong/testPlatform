<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE9">
  <title>CheckList</title>
  <meta name="keywords" content="" />
  <meta name="description" content="" />
  <meta name="viewport" content="width=device-width">        
  <link rel="stylesheet" href="css/templatemo_main.css">
  <script type="text/javascript" src="js/jquery.js"></script>
  <script type="text/javascript" src="js/utils.js"></script>
  <script type="text/javascript" >
  $(function(){
	  loadList();
	});
  function loadList() {
	    $.getJSON('main/findModel.do',null, function(data) {
		    $.each(data,function(i,n){
				var temp = "<tr><td ><input value="+n.id+" type='checkbox'></td><td>"+n.name+"</td><td>"+n.remark+"</td></tr>";
				$("#modelList").append(temp);
			});
	    });
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
          <li class="active"><a href="index.jsp"><i class="fa fa-home"></i>分类</a></li>
         
           <li class=""><a href="page/selenium.jsp"><i class="fa fa-home"></i>selenium自动检查</a></li>
           <li class=""><a href="page/relatecase.jsp"><i class="fa fa-home"></i>关联用例</a></li>
        </ul>
      </div><!--/.navbar-collapse -->

      <div class="templatemo-content-wrapper">
        <div class="templatemo-content">
          <ol class="breadcrumb">
            <li><a >当前页：分类</a></li>
          </ol>
          

          
          <div class="templatemo-panels">
            <div class="row">
              <div class="col-md-12">
                  <span class="btn btn-primary"><a href="#">新增</a></span>
                  <span class="btn btn-primary"><a href="#">编辑</a></span>
                  <span class="btn btn-primary"><a href="#">查看</a></span>
                  <span class="btn btn-primary"><a href="#">删除</a></span>
              </div>
              
            </div>
            &nbsp;
            <div class="row">
              <div tyle="width:100%;" class="yudh_col-md-6 col-sm-6">
                <div class="tab-content">
                <div class="row">
              <div class="yudh_col-md-6 col-sm-6 margin-bottom-30">
                <div class="panel panel-primary">
                  <div class="panel-heading">分类列表</div>
                  <div class="panel-body">
                    <table class="table table-striped table-hover table-bordered">
                      <thead>
                        <tr>
                       		<th>选择</th>
                          <th>分类</th>
                          <th>备注</th>
                        </tr>
                      </thead>
                      <tbody id="modelList">
                        
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>       
            </div>
                  <div class="tab-pane fade" id="profile">
                    <ul class="list-group">
                      <li class="list-group-item">
                        <span class="badge">33</span>
                        Vivamus dictum posuere odio
                      </li>
                      <li class="list-group-item">
                        <span class="badge">9</span>
                        Dapibus ac facilisis in
                      </li>
                      <li class="list-group-item">
                        <span class="badge">0</span>
                        Morbi convallis sed nisi suscipit
                      </li>
                      <li class="list-group-item">
                        <span class="badge">14</span>
                        Cras justo odio
                      </li>
                      <li class="list-group-item">
                        <span class="badge">2</span>
                        Vestibulum at eros
                      </li>
                    </ul>
                  </div>
                  <div class="tab-pane fade" id="messages">
                    <div class="list-group">
                      <a href="#" class="list-group-item active">
                        Morbi convallis sed nisi suscipit
                      </a>
                      <a href="#" class="list-group-item">Dapibus ac facilisis in</a>
                      <a href="#" class="list-group-item">Morbi leo risus</a>
                      <a href="#" class="list-group-item">Porta ac consectetur ac</a>
                      <a href="#" class="list-group-item">Vestibulum at eros</a>
                    </div>
                  </div>
                  <div class="tab-pane fade" id="settings">
                    <div class="list-group">
                      <a href="#" class="list-group-item disabled">
                        Vivamus dictum posuere odio
                      </a>
                      <a href="#" class="list-group-item">Porta ac consectetur ac</a>
                      <a href="#" class="list-group-item">Vestibulum at eros</a>
                      <a href="#" class="list-group-item">Dapibus ac facilisis in</a>
                      <a href="#" class="list-group-item">Morbi leo risus</a>
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
          <p>Copyright &copy; 2014 keepsoft，create by yudanhong </p>
        </div>
      </footer>
    </div>

</body>
</html>
