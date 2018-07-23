<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/extra/basepath.jsp"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
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
<script type="text/javascript"
	src="${ctxStatic}/js/orgnal_js/depData.js"></script>
<script type="text/javascript"
	src="${ctxStatic}/js/verify/jquery.validate.js"></script>
<script type="text/javascript"
	src="${ctxStatic}/js/verify/validate_expand.js"></script>
<script type="text/javascript"
	src="${ctxStatic}/js/verify/validate_redact.js"></script>
<script type="text/javascript">
	$(function() {//调user.js
		departmentdg("${ctx}");
	});
</script>
</head>
<body>
	<!-- 用户信息datagrid -->
	<div id="departmentdg" style="overflow: hidden;"></div>
	<!-- toolbar -->
	<div id="departmentBar" style="padding: 5px;">
		<div style="margin-bottom: 5px;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"onclick="department.addDepartment();">添加</a> 
			<a href="#"class="easyui-linkbutton" iconCls="icon-edit" plain="true"onclick="obj.edit();">修改</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="obj.remove();">删除</a>
		</div>
	</div>
	<!-- 用户信息dialog -->
	<div id="persondg"></div>

</body>
</html>