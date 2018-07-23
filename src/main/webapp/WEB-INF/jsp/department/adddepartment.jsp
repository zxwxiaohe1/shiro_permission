<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
  <!-- 部门输入框 -->
	<div id="combox">
		<!-- 自定义列表框 -->
		<input id="dpComboBox" name="dpComboBox" class="cominput" style="width:150px;display: none"/>
		<!-- 自定义列表框 -->
		<input id="dpComboBoxchild" name="dpComboBoxchild"class="cominput"style="width:150px;display: none"/>
		<!-- 自定义列表框 -->
		<input id="dpComboBoxson" name="dpComboBoxson" class="cominput"style="width:150px;display: none"/>
	</div>
	<!-- 一级部门输入框 -->
	<div id="combox2" style="width:200px;display: none">
		<!-- 自定义列表框 -->
		<label>请输入部门类型:</label>
		<input id="dptype" name="dptype" style="width:150px;"/><br>
		<!-- 自定义列表框 -->
		<label>请输入部门等级:</label>
		<input id="dprank" name="dprank" value="1"style="width:150px;"/><br>
		<!-- 自定义列表框 -->
		<label>是否开启部门(0/1):</label>
		<input id="dpdstatus" name="dpdstatus"value="1" style="width:150px;"/><br>
	</div>
</body>
</html>