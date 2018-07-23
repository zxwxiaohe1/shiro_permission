<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/extra/basepath.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<!-- css -->
<link rel="stylesheet" type="text/css"
	href="${ctxStatic}/js/easyUI/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${ctxStatic}/js/easyUI/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${ctxStatic}/js/easyUI/demo.css">
<link rel="stylesheet" type="text/css"
	href="${ctxStatic}/css/style/login.css">
<!-- js -->
<script type="text/javascript"
	src="${ctxStatic}/js/easyUI/jquery-1.7.1.min.js"></script>
<script type="text/javascript"
	src="${ctxStatic}/js/easyUI/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/orgnal_js/juriData.js"></script>
<script type="text/javascript">
	$(function() {//调user.js
		 authoritygrid("${ctx}");
	});
</script>
</head>
<body>
	<!-- 权限分配datagrid -->
	<div id="authority" style="overflow: hidden;"></div>
	<!-- 添加权限 -->
	<div id="adddialog" style="overflow: hidden;"></div>
</body>
</html>