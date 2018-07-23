<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/extra/basepath.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<form id="redactForm" method="post"
		action="${ctx}/user/updateUser.action">
		<input type="hidden" name="id" id="id" value="${user.id}" />
		<table width="400px" border="0" style="margin-top:20px;">
			<tr height="30px">

				<td align="center">
				<input name="text" id="text" class="easyui-validatebox" value="${user.text}"  validType="length[2,12]" /> 
				</td>
				<td align="center">
				<input name="number" id="number" class="easyui-validatebox" value="${user.number}" validType="length[2,12]" />
				</td>
			</tr>
			<tr  height="30px">
				<td align="center">
				<input name="loginname" id="loginname" value="${user.loginname}" class="easyui-validatebox" required="true" validType="length[2,12]" />
				 </td>
				 <td align="center">  
			   <input name="age" id="age" value="${user.age}" class="easyui-validatebox" validType="length[1,3]" />
				 </td>
			</tr>
			<tr  height="30px">
				<td align="center">
				<input name="serialnumber" id="serialnumber" value="${user.serialnumber}" class="easyui-validatebox" validType="length[5,12]" /></td>
			    <td align="center"><input name="ustatus" id="ustatus" value="${user.ustatus}" class="easyui-validatebox" validType="length[1,2]" /></td>
			    </tr>
		</table>
		
	</form>



</body>
</html>