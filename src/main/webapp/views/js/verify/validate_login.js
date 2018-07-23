/**
 * @author Administrator
 */
$(function() {
	initValidator();
});
function initValidator(base) {
	$("#loginForm").validate({
		onkeyup : false,
		// 设置验证规则
		rules : {
			"loginname" : {
				required : true,
			},
			"password" : {
				required : true,
				rangelength : [ 6, 12 ],
			},
		},
		// 设置错误信息
		messages : {
			"loginname" : {
				required : "请输入用户名",
			},

			"password" : {
				required : "请输入密码",
				rangelength : "密码长度为6-12位",
			},
		},
		errorElement : "font",
		errorPlacement : function(error, element) {
			error.appendTo(element.parent().find(".tipinfo"));
		},
		success : "valid"
	});
}
