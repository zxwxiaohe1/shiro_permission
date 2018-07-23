<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/extra/basepath.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<style>
#addauthor {
	margin-top: 50px;
}
#removeauthor {
	margin-top: 50px;
}

</style>
<body>
	<div class="easyui-layout" style="width: 500px; height: 300px;">
		<div region="east" icon="icon-reload" style="width: 200px;">
			<table id="addauthorTable">
				<tr>
					<td>具有权限</td>

				</tr>
			</table>
		</div>
		<div region="west" style="width: 200px;">
			<table id="authorTable">
				<tr>
					<td>权限列表</td>

				</tr>
			</table>
		</div>
		<div region="center" style="background: #fafafa; overflow: hidden">
			<a id="addauthor" href="#" style="margin-top: 50px;">添加</a>
			<p>
				<a id="removeauthor" href="#" style="margin-top: 50px;">移出</a>
		</div>
	</div>
	<script>
		$(function() {
			$('#addauthor').bind('click', function() {
				addAuthor();

			});
			$('#removeauthor').bind('click', function() {
				removeTrueAuthor();

			});
		});
	</script>
</body>
</html>