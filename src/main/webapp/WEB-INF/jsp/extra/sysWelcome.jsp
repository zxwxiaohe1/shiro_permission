<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/extra/basepath.jsp"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>loginsuccess</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Pragma" content="no-cache"> 
<meta http-equiv="Cache-Control" content="no-cache,must-revalidate"> 
<meta http-equiv="Expires" content="0">
<!-- css -->
<link rel="stylesheet" type="text/css"
	href="${ctxStatic}/js/easyUI/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${ctxStatic}/js/easyUI/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${ctxStatic}/js/easyUI/demo.css">
<link rel="stylesheet" type="text/css"
	href="${ctxStatic}/css/style/user/backsys.css">
<!-- js -->
<script type="text/javascript"
	src="${ctxStatic}/js/easyUI/jquery-1.7.1.min.js"></script>
<script type="text/javascript"
	src="${ctxStatic}/js/easyUI/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${ctxStatic}/js/verify/jquery.validate.js"></script>
<script type="text/javascript"
	src="${ctxStatic}/js/easyUI/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${ctxStatic}/js/verify/validate_redact.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/orgnal_js/sysWelocme.js"></script>
</head>
<script type="text/javascript">
	$(function() {//调userback.js
		userBack("${ctx}");

	});
</script>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
	<!-- north -->
	<div class="north" region="north" split="true">
		<div class="northd">
			<div class="nleft">
				<ul>
					<li><img src="${ctxStatic}/css/images/back/logo.jpg"width="80px" height="50px"></li>
					<li class="sysfont">sinux信息管理系统</li>
				</ul>
			</div>
			<div class="nright">
				<ul>
					<li class="sysfont">|</li>
					<li class="sysfonts">OA办公系统</li>
				</ul>
				<ul id="lginfo">
				<shiro:hasPermission name="user:update"></shiro:hasPermission>
					<li>登录名:${sessionInfo.username}</li>
					<li>|</li>
					<li><a href="${ctx}/logout.action">退出</a></li>
				</ul>
			</div>

		</div>

	</div>
	<!-- west -->
	<div class="west" region="west" split="true">
		<div class="easyui-accordion" fit="true" border="false"
			style="background-color: rgb(237, 244, 254)">
			<div title="系统管理" class="mnuebar">
				<ul id="sysmenu">

				</ul>
				<shiro:hasPermission name="source:show">
					<ul id="author">
					</ul>
				</shiro:hasPermission>
			</div>
			<div title="系统管理" class="asda"></div>
			<div title="系统管理" class="mnuebar"></div>
			<div title="系统管理" class="mnuebar"></div>
		</div>
	</div>
	<!-- center -->
	<div region="center" style="overflow: hidden;">
		<div id="usertabs" class="easyui-tabs" fit="true"
			style="background-color: rgb(237, 244, 254)">
			<div title="欢迎使用" class="backhome">
				<p>欢迎使用</p>
			</div>
		</div>
	</div>
</body>
</html>