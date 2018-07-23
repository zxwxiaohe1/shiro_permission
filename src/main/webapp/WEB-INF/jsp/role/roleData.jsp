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
	src="${ctxStatic}/js/orgnal_js/roleData.js"></script>
</head>
<script type="text/javascript">
	$(function() {//调user.js
		roleTreeGrid("${ctx}");
	});
</script>
<body>
	<!-- 用户信息treegrid -->
	<div id="roleTreeGrid" style="overflow: hidden;"></div>
	<!-- toolbar -->
	<div id="roleBar" style="padding: 5px;">
		<div style="margin-bottom: 5px;width='300px'">
			<shiro:hasPermission name="role:add">
				<a href="#" class="easyui-linkbutton" iconCls="icon-add"plain="true" onclick="role.addRole();">添加</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="role:update">
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit"plain="true" onclick="obj.edit();">修改</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="role:delete">
				<a href="#" class="easyui-linkbutton" iconCls="icon-remove"plain="true" onclick="obj.remove();">删除</a>
			</shiro:hasPermission>
		    	<a href="#" class="easyui-linkbutton" iconCls="icon-save"plain="true" style="display: none;" id="save" onclick="obj.save();">保存</a>
		    	<a href="#" class="easyui-linkbutton" iconCls="icon-redo"plain="true" style="display: none;" id="redo" onclick="obj.redo();">取消编辑</a>
		</div>
		<div id="orangalNames">
		     <a href="#" onclick="role.openDialog();" class="easyui-linkbutton"plain="true" iconCls="icon-edit">组织人员</a>
	</div>
	</div>
	
</body>
</html>