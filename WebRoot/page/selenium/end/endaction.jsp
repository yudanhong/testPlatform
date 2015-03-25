<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<script type="text/javascript" src="../../js/selenium/end/endaction.js"></script>
<html>
<div class="panel panel-primary" style="margin-bottom:0px;margin-top:30px;">
  <div id="steptitle" class="panel-heading" ></div>
</div>
<table class="table table-striped table-hover table-bordered">
	<thead>
		<tr>
			<th >序号</th>
			<th >step名称</th>
			<th >type</th>
			<th>dom</th>
			<th>value</th>
			<th>sleep</th>
			<th>截图</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody id="initSteplist">
		<tr>
					<td ></td>
					<td ><input type="text"></td>
					<td >
						<select>
							<option value='click'>click</option>
							<option value='type'>type</option>
							<option value='select'>select</option>
							<option value='selectFrame'>selectFrame</option>
							<option value='keyUp'>keyUp</option>
						</select>
					</td>
					<td><input type="text"></td>
					<td><input type="text"></td>
					<td><input  onkeyup="$(this).clearNoNum(1)" type="text"></td>
					<td><select><option value='0'>否</option><option value='1'>是</option></select></td>
					<td><a class='btn btn-link' onclick="addBefore(this)">之前添加</a><a class='btn btn-link' onclick="addAfter(this)">之后添加</a><a onclick="deleteStep(this)" class='btn btn-link' >删除</a></td>
			</tr>
	</tbody>
</table>
<div class="row templatemo-form-buttons">
                <div class="col-md-12">
                  <button type="button" class="btn btn-primary" onclick="addSteps()" id="addSteps">保存</button>
                  <!-- <button type="reset" class="btn btn-default">取消</button>   -->  
                </div>
</div>
</html>
