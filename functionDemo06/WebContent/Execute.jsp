<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>运行结果页面</title>
<link href="http://fonts.googleapis.com/css?family=Oxygen|Marck+Script" rel="stylesheet" type="text/css">
<link href="assets/css/bootstrap.css" rel="stylesheet">
<link href="assets/css/font-awesome.css" rel="stylesheet">
<link href="assets/css/admin.css" rel="stylesheet">
<link href="//cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css" rel="stylesheet">
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
					<li class="dropdown">
						<a class="dropdown-toggle" data-toggle="collapse" data-target="#reports-dropdown" href="Defvardata.jsp"><i class="icon-signal"></i> 定义变量数据 </a>
					</li>
					<li class="active">
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
				校验变量、变量关系、变量数据是否合&nbsp;&nbsp;<button type="submit" class="btn btn-info">校验</button></br>
				点击运行，查看推导结果&nbsp;&nbsp;<button class="btn btn-small btn-danger">运行</button>
			</div>	
		</div>

	</div> <!-- end span10 -->
		
	</div> <!-- end row -->
</div> <!-- end container -->

<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/bootstrap.js"></script>
<script src="assets/js/excanvas.min.js"></script>
<script src="assets/js/jquery.flot.min.js"></script>
<script src="assets/js/jquery.flot.resize.js"></script>
<script type="text/javascript" src="//cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>

</body>
</html>