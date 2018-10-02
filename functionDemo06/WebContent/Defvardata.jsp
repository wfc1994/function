<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>定义变量数据</title>
<link href="http://fonts.googleapis.com/css?family=Oxygen|Marck+Script" rel="stylesheet" type="text/css">
<link href="assets/css/bootstrap.css" rel="stylesheet">
<link href="assets/css/font-awesome.css" rel="stylesheet">
<link href="assets/css/admin.css" rel="stylesheet">
<link href="//cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css" rel="stylesheet">

<link href="assets/jqueryui-editable/css/jqueryui-editable.css" rel="stylesheet"/>

<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
$(function () {
    
    $("#myTable td").click(function(){

    	$("#myTable td").editable({
            type: "text",                //编辑框的类型。支持text|textarea|select|date|checklist等
            title: "输入修改值",              //编辑框的标题
            disabled: false,             //是否禁用编辑
            emptytext: "空文本",          //空值的默认文本
            mode: "inline",              //编辑框的模式：支持popup和inline两种模式，默认是popup
            validate: function (value) { //字段验证
                if (!$.trim(value)) {
                    return '不能为空';
                }
            }
        });

   	});

});

	var idTmr;
	function  getExplorer() {
	    var explorer = window.navigator.userAgent ;
	    //ie
	    if (explorer.indexOf("MSIE") >= 0) {
	        return 'ie';
	    }
	    //firefox
	    else if (explorer.indexOf("Firefox") >= 0) {
	        return 'Firefox';
	    }
	    //Chrome
	    else if(explorer.indexOf("Chrome") >= 0){
	        return 'Chrome';
	    }
	    //Opera
	    else if(explorer.indexOf("Opera") >= 0){
	        return 'Opera';
	    }
	    //Safari
	    else if(explorer.indexOf("Safari") >= 0){
	        return 'Safari';
	    }
	}
	function method1(tableid) {//整个表格拷贝到EXCEL中
	    if(getExplorer()=='ie') {
	        var curTbl = document.getElementById(tableid);
	        var oXL = new ActiveXObject("Excel.Application");
	
	        //创建AX对象excel
	        var oWB = oXL.Workbooks.Add();
	        //获取workbook对象
	        var xlsheet = oWB.Worksheets(1);
	        //激活当前sheet
	        var sel = document.body.createTextRange();
	        sel.moveToElementText(curTbl);
	        //把表格中的内容移到TextRange中
	        sel.select;
	        //全选TextRange中内容
	        sel.execCommand("Copy");
	        //复制TextRange中内容
	        xlsheet.Paste();
	        //粘贴到活动的EXCEL中
	        oXL.Visible = true;
	        //设置excel可见属性
	
	        try {
	            var fname = oXL.Application.GetSaveAsFilename("Excel.xls", "Excel Spreadsheets (*.xls), *.xls");
	        } catch (e) {
	            print("Nested catch caught " + e);
	        } finally {
	            oWB.SaveAs(fname);
	
	            oWB.Close(savechanges = false);
	            //xls.visible = false;
	            oXL.Quit();
	            oXL = null;
	            //结束excel进程，退出完成
	            //window.setInterval("Cleanup();",1);
	            idTmr = window.setInterval("Cleanup();", 1);
	        }
	    } else {
	        tableToExcel('myTable')
	    }
	}
	function Cleanup() {
	    window.clearInterval(idTmr);
	    CollectGarbage();
	}
	
	/*
	    template ： 定义文档的类型，相当于html页面中顶部的<!DOCTYPE> 声明。（个人理解，不确定）
	    encodeURIComponent:解码
	    unescape() 函数：对通过 escape() 编码的字符串进行解码。
	    window.btoa(window.encodeURIComponent(str)):支持汉字进行解码。
	    \w ：匹配包括下划线的任何单词字符。等价于’[A-Za-z0-9_]’
	    replace()方法：用于在字符串中用一些字符替换另一些字符，或替换一个与正则表达式匹配的子串。
	    {(\w+)}：匹配所有 {1个或更多字符} 形式的字符串；此处匹配输出内容是 “worksheet”
	    正则中的() ：是为了提取匹配的字符串。表达式中有几个()就有几个相应的匹配字符串。
	    讲解(/{(\w+)}/g, function(m, p) { return c[p]; } ：
	        /{(\w+)}/g 匹配出所有形式为“{worksheet}”的字符串；
	        function参数：  m  正则所匹配到的内容，即“worksheet”；
	                        p  正则表达式中分组的内容,即“(\w+)”分组中匹配到的内容，为“worksheet”；
	        c ：为object，见下图3
	        c[p] : 为“worksheet”
	
	*/
	var tableToExcel = (function() {
	    var uri = 'data:application/vnd.ms-excel;base64,',
	    template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--><meta charset="UTF-8"></head><body><table>{table}</table></body></html>',
	    base64 = function(s) {
	        return window.btoa(unescape(encodeURIComponent(s)))
	    },
	    // 下面这段函数作用是：将template中的变量替换为页面内容ctx获取到的值
	    format = function(s, c) {
	            return s.replace(/{(\w+)}/g,
	                            function(m, p) {
	                                return c[p];
	                            }
	            )
	    };
	    return function(table, name) {
	        if (!table.nodeType) {
	            table = document.getElementById(table)
	        }
	        // 获取表单的名字和表单查询的内容
	        var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML};
	        // format()函数：通过格式操作使任意类型的数据转换成一个字符串
	        // base64()：进行编码
	        window.location.href = uri + base64(format(template, ctx))
	    }
	})()
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
					<li class="dropdown">
						<a class="dropdown-toggle" data-toggle="collapse" data-target="#website-dropdown" href="Defvarrelation.jsp"><i class="icon-sitemap"></i> 定义变量关系 </a>
					</li>
					<li class="active">
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
				<form method="post" action="ImportVariableDataServlet" enctype="multipart/form-data" class="form-inline">
				   	 选择一个文件:  <input type="file" name="uploadFile" /> <input type="submit" value="导入"  class="btn btn-info"/>
				   	 <button class="btn dropdown-toggle" data-toggle="dropdown" onclick="method1('myTable')">
									<i class="icon-download-alt"></i> 导出Excel
									<span class="caret"></span>
					</button>
				</form>
				<h2>${message}</h2>			
			</div>	
		</div>

		 <table border="1" width="700" id="myTable">
		 	<thead>
		 		${templist[0] } 
			</thead>
		 	<c:forEach items="${templist }" begin="1" var="templist">
				 <tbody>
				 	${templist }
				 </tbody>
			 </c:forEach>
		 </table>
		 
	</div> <!-- end span10 -->
		
	</div> <!-- end row -->
</div> <!-- end container -->

<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/bootstrap.js"></script>
<script src="assets/js/excanvas.min.js"></script>
<script src="assets/js/jquery.flot.min.js"></script>
<script src="assets/js/jquery.flot.resize.js"></script>
<script src="assets/jqueryui-editable/js/jqueryui-editable.js"></script>
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