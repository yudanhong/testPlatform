 caseId = $("#caseId").val();
 caseName = $("#caseName").val();
 $("#steptitle").text(caseName+" >> step编辑");
 $(function(){
	findTestStepList();
});
//----------------------------返回用例列表---------------------------------------
 function gobacktoCaseList(){
	 $(".panel-body").htmlLoad("page/selenium/actions/case.jsp?suitId="+suitId+"&suitName="+suitName+"&productId="+productId);
 }
//----------------------------获取action中所有的用例集--------------------------------------------------
function findTestStepList(){
  $.getJSON('../../selenium/findTestStepByCaseId.do',{'caseId':caseId},
			function(data) {
			var lastIndex = (data==null)?1:(data.length+1);
			$("tr:last td:first").text(lastIndex);
			var lastTr = $("tr:last").html();
			$("#testStepList").empty();
			if(data!=null&&data.length!=0){
				$.each(data,function(i,n){
					var temp = $("<tr>"+
						"<td>"+(i+1)+"</td>"+
						"<td><input type='text' value='"+n.name+"'/></td>"+
						"<td><select><option value='click'>click</option><option value='type'>type</option><option value='select'>select</option><option value='selectFrame'>selectFrame</option><option value='keyUp'>keyUp</option></select></td>"+
						"<td><input type='text' value='"+n.dom+"'/></td>"+
						"<td><input type='text' value='"+n.value+"'/></td>"+
						"<td><input class='x' onkeyup='$(this).clearNoNum(1)' type='text' value='"+(n.sleep==0?'':n.sleep)+"'/></td>"+
						"<td><select ><option value='0'>否</option><option value='1'>是</option></select></td>"+
						"<td><select><option value='0'>否</option><option value='1'>是</option></select></td>"+
						"<td><select><option value='0'>否</option><option value='1'>是</option></select></td>"+
						"<td><a class='btn btn-link' onclick='addBefore(this)'>之前添加</a><a class='btn btn-link' onclick='addAfter(this)'>之后添加</a><a onclick='deleteStep(this)' class='btn btn-link' >删除</a></td>"+
					"</tr>");
					$(temp).children("td:eq(2)").children().val(n.type);
					$(temp).children("td:eq(6)").children().val(n.isIgnore);
					$(temp).children("td:eq(7)").children().val(n.ischeck);
					$(temp).children("td:eq(8)").children().val(n.snapshot);
					$("#testStepList").append(temp);
				});
			}
			$("#testStepList").append("<tr>"+lastTr+"</tr>");
			});
  }
function addAfter(dom){
	var currentRow = $(dom).parent().parent();
	var currentIndex = currentRow.children("td:first").text();
	var after = getEmptyRow();
	currentRow.after("<tr>"+after.html()+"</tr>");
	currentRow.next().children("td:first").text(parseInt(currentIndex)+1);
	currentRow.nextAll("tr:gt(0)").each(function(){
		var temp = $(this).children("td:first").text();
		$(this).children("td:first").text(parseInt(temp)+1);
	});
}
function addBefore(dom){
	var currentRow = $(dom).parent().parent();
	var currentIndex = currentRow.children("td:first").text();
	var before = getEmptyRow();
	currentRow.before("<tr>"+before.html()+"</tr>");
	currentRow.prev().children("td:first").text(parseInt(currentIndex));
	currentRow.prev().nextAll("tr").each(function(){
		var temp = $(this).children("td:first").text();
		$(this).children("td:first").text(parseInt(temp)+1);
	});
}
function getEmptyRow(){
	var dom = $("<tr>"+
			"<td>"+$("tr").length+"</td>"+
			"<td><input type='text' value=''/></td>"+
			"<td><select><option value='click'>click</option><option value='type'>type</option><option value='select'>select</option><option value='selectFrame'>selectFrame</option><option value='keyUp'>keyUp</option></select></td>"+
			"<td><input type='text' value=''/></td>"+
			"<td><input type='text' value=''/></td>"+
			"<td><input onkeyup='$(this).clearNoNum(1)' type='text' value=''/></td>"+
			"<td><select ><option value='0'>否</option><option value='1'>是</option></select></td>"+
			"<td><select ><option value='0'>否</option><option value='1'>是</option></select></td>"+
			"<td><select ><option value='0'>否</option><option value='1'>是</option></select></td>"+
			"<td><a class='btn btn-link' onclick='addBefore(this)'>之前添加</a><a class='btn btn-link' onclick='addAfter(this)'>之后添加</a><a onclick='deleteStep(this)' class='btn btn-link' >删除</a></td>"+
		"</tr>");
	return dom;
}
//----------------------------删除用例集--------------------------------------------------
function deleteStep(dom){
	$(dom).parent().parent().nextAll("tr").each(function(){
		var temp = $(this).children("td:first").text();
		$(this).children("td:first").text(parseInt(temp)-1);
	});
	
  	$(dom).parent().parent().remove();
  	
  }
 
//----------------------------至添加页面--------------------------------------------------
function addSteps(){
	var steps = new Array();
	var step;
	var flag =1;
	$("tr:gt(0)").each(function(i,n){
		step = {};
		step.name = $(n).find("td:eq(1)").children().val();
		step.type = $(n).find("td:eq(2)").children().val();
		step.dom = $(n).find("td:eq(3)").children().val();
		step.value = $(n).find("td:eq(4)").children().val();
		step.sleep = $(n).find("td:eq(5)").children().val();
		step.isIgnore = $(n).find("td:eq(6)").children().val();
		step.ischeck = $(n).find("td:eq(7)").children().val();
		step.snapshot = $(n).find("td:eq(8)").children().val();
		if(step.name==""&&step.dom==""){
		}else{
			if(step.name==""){
				alert("请输入step名称");
				flag = "0";
				return false;
			}if(step.dom==""){
				alert("请输入dom名称");
				flag = "0";
				return false;
			}
			steps.push(step);
		}
	});
	
	if(flag == "0"){ return;}
	$.post('../../selenium/saveStepsByCaseId.do',{'steps':JSON.stringify(steps),'caseId':caseId},
			function(data) {
				if(data==0){
						alert("保存失败！");
					 }else{
						 alert("保存成功！");
							$(".panel-body").htmlLoad("page/selenium/actions/case.jsp?suitId="+suitId+"&suitName="+suitName+"&productId="+productId);
						}
			});
}