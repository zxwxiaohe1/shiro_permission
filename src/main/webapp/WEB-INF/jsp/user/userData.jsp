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
<script type="text/javascript" src="${ctxStatic}/js/orgnal_js/userData.js"></script>
<script type="text/javascript"
	src="${ctxStatic}/js/verify/jquery.validate.js"></script>
<script type="text/javascript"
	src="${ctxStatic}/js/verify/validate_expand.js"></script>
<script type="text/javascript"
	src="${ctxStatic}/js/verify/validate_redact.js"></script>
<script type="text/javascript">
	$(function() {//调user.js
		var pers = '<%=session.getAttribute("permissions")%>';
		var resultpers = pers.replace('[', '').replace(']', '').split(',');
		datagrid("${ctx}", resultpers);
	});
</script>
</head>
<body>
	<!-- 用户信息datagrid -->
	<div id="test" style="overflow: hidden;">
		<!-- userBar -->
		<div id="userBar" style="padding: 5px;">
			<div style="margin-bottom: 5px;">
				<shiro:hasPermission name="user:update">
					<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="user.addUser();">添加用户</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="user.deleteUser();">删除用户</a>
				</shiro:hasPermission>
				<a href="#" id="btsave"class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="user.savaUser();">保存</a> 
				<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="user.refresh();">刷新</a> 
			</div>
		</div>
	</div>
</body>
</html>