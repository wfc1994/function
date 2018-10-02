<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>定义变量关系</title>
<link href="http://fonts.googleapis.com/css?family=Oxygen|Marck+Script" rel="stylesheet" type="text/css">
<link href="assets/css/bootstrap.css" rel="stylesheet">
<link href="assets/css/font-awesome.css" rel="stylesheet">
<link href="assets/css/admin.css" rel="stylesheet">
<link href="//cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css" rel="stylesheet">
<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
	function importVariable() {
	    var objFile = document.getElementById("fileId");
	    if(objFile.value == "") {
	        alert("不能空")
	    }
	    console.log(objFile.files[0].size); // 文件字节数
	    var files = $('#fileId').prop('files');//获取到文件列表
	    if(files.length == 0){
	        alert('请选择文件');
	    }else{
	        var reader = new FileReader();//新建一个FileReader
	        reader.readAsText(files[0], "UTF-8");//读取文件 
	        reader.onload = function(evt){ //读取完文件之后会回来这里
	            var fileString = evt.target.result; // 读取文件内容\
	            console.log(fileString);
	            $.ajax({
	                type: "POST",
	                url: "ImportVariableRelationServlet",
	                contentType: "application/json; charset=utf-8",
	                data: fileString,
	                success: function (data) {
	                	location.reload(true);
	                },
	                error: function (data) {
	                	location.reload(true);
	                }
	            });
	            
	    	}
		}
	 }

	function doDelete(varrelid,independentvar,dependentvar,functionname){
		var flag = confirm("是否确定删除？");
		if(flag){
			//	表明点了确定，访问servlet。在当前标签页上打开超链接。
			location.href = "DeleteVariableRelationServlet?varrelid="+varrelid+"&independentvar="+encodeURI(encodeURI(independentvar))+"&dependentvar="+encodeURI(encodeURI(dependentvar))+"&functionname="+encodeURI(encodeURI(functionname));
		}
	}
	
	function doEdit(varrelid,independentvar,dependentvar,functionname){
		var vrid = varrelid;
		var indepvar = prompt("请修改自变量",independentvar);
		var depvar = prompt("请修改因变量",dependentvar);
		var funname = prompt("请修改函数名",functionname);
		if (indepvar!=null && indepvar!="" && depvar!=null && depvar!="" && funname!=null && funname!="")
		{
			location.href = "EditVariableRelationServlet?varrelid="+vrid+"&independentvar="+encodeURI(encodeURI(indepvar))+"&dependentvar="+encodeURI(encodeURI(depvar))+"&functionname="+encodeURI(encodeURI(funname));
		}
	 }
	
	function fore(){
	    var temp = "";
	    var tabLen = document.getElementById("myTable");
	    var jsonT = "[";
	    var pun = "";
	    var punn = "";
	    for(var i = 1; i < tabLen.rows.length;i++){
	    	var jsontemp = "{";
	        for(var j = 0;j<tabLen.rows[i].cells.length-1;j++){
	        	if(j==tabLen.rows[i].cells.length-2)
	        	{
	        		pun = "}";
	        	}else{
	        		pun = "',"
	        	}
	        	jsontemp += tabLen.rows[0].cells[j].innerHTML + ":" + "'" + tabLen.rows[i].cells[j].innerHTML + pun     	
	        }
	    	
	    	if(i == tabLen.rows.length-1)
	    	{
	    		punn="]";
	    	}else{
	    		punn=",";
	    	}
	        
	    	jsonT += jsontemp+punn;
	    }
	    
	    
		console.log(jsonT);
	    downFile(jsonT, "test.json");
	}

	function downFile(content, filename) {    // 创建隐藏的可下载链接
	    var eleLink = document.createElement('a');
	    eleLink.download = filename;
	    eleLink.style.display = 'none';    // 字符内容转变成blob地址
	    var blob = new Blob([content]);
	    eleLink.href = URL.createObjectURL(blob);    // 触发点击    document.body.appendChild(eleLink);
	    eleLink.click();    // 然后移除    document.body.removeChild(eleLink);
	};
</script>
</head>
<body>

<div class="container">	
	<div class="row">
		
		<div class="span2">
			<div class="main-left-col">
				<h1><div style="width:10px;height:23px;"></div></i> Function</h1>
				<ul class="side-nav">
	
					<li class="dropdown">
						<a href="Variable.jsp"><i class="icon-home"></i> 定义变量</a>
					</li>
					<li class="active">
						<a class="dropdown-toggle" data-toggle="collapse" data-target="#website-dropdown" href="Defvarrelation.jsp"><i class="icon-sitemap"></i> 定义变量关系 </a>
					</li>
					<li class="dropdown">
						<a class="dropdown-toggle" data-toggle="collapse" data-target="#reports-dropdown" href="Defvardata.jsp"><i class="icon-signal"></i> 定义变量数据 </a>
					</li>
					<li class="dropdown">
						<a class="dropdown-toggle" data-toggle="collapse" data-target="#settings-dropdown" href="Execute.jsp"><i class="icon-cogs"></i> 运行结果 </a>					
					</li>
					<li class="dropdown">
						<a class="dropdown-toggle" data-toggle="collapse" data-target="#help-dropdown" href="Help.jsp"><i class="icon-info-sign"></i> 查看帮助文档 </a>
					</li>
				</ul>
			
			</div> <!-- end main-left-col -->
		</div> <!-- end span2 -->
	
		<div class="span10">
			<div class="main-area dashboard">
				<div class="slate">
					<form method="post" action="DefunctionServlet" class="form-inline">
						<input type="text" class="input-large" placeholder="请输入自变量..."  name="independentvar">
						<input type="text" class="input-large" placeholder="请输入因变量..."  name="dependentvar">
						<input type="text" class="input-large" placeholder="请输入函数名..."  name="functionname">
						<button type="submit"  class="btn btn-info">生成</button>
					</form>
				</div>
				
				上传文件 ： <input type="file" name = "file" id = "fileId" /> 
				<button type="submit" onclick="importVariable()" class="btn btn-info">导入</button>
				<button class="btn dropdown-toggle" data-toggle="dropdown" onclick="fore()">
					<i class="icon-download-alt"></i>导出Json
				<span class="caret"></span>
			</div>
				
			
			
			<table border="1" width="700" id="myTable">
					
				<thead>
					<td>变量关系编号</td>
					<td>自变量</td>
					<td>因变量</td>
					<td>函数名</td>
					<td>变量关系</td>
					<td>操作</td>
				</thead>
					
				<c:forEach items="${varrelationlist }" var="varrel">
					<tbody>
						<td>${varrel.varrelid }</td>
						<td>${varrel.independentvar }</td>
						<td>${varrel.dependentvar }</td>
						<td>${varrel.functionname }</td>
						<td>${varrel.varrelation }</td>
						<td>
							<button href="#" onclick="doEdit('${varrel.varrelid}','${varrel.independentvar}','${varrel.dependentvar}','${varrel.functionname}')" class="btn btn-small btn-primary">修改</button>  
							<button href="#" onclick="doDelete('${varrel.varrelid}','${varrel.independentvar}','${varrel.dependentvar}','${varrel.functionname}')" class="btn btn-small btn-danger" data-toggle="modal">删除</button>
						</td>
					</tbody>
				</c:forEach>
			</table>	
		</div> <!-- end span10 -->
		
	</div> <!-- end row -->
</div> <!-- end container -->

<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/bootstrap.js"></script>
<script src="assets/js/excanvas.min.js"></script>
<script src="assets/js/jquery.flot.min.js"></script>
<script src="assets/js/jquery.flot.resize.js"></script>
<script type="text/javascript" src="//cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
    $('#myTable').DataTable( {
        "paging":   false,
        "ordering": false,
        "info":     false,
        "searching": false
    } );
} );
</script>

</body>
</html>