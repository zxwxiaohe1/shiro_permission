/**
 * @author Administrator
 */
$(function() {
//	alert('#name validatebox');
	$("#name").validatebox({
		required : true,
		validType : 'minLength[5,10]',
		tipPosition : 'right',
	});
	$.extend($.fn.validatebox.defaults.rules, {
		minLength : {
			validator : function(value, param) {
				return value.length >= param[0];
			},
			message : '请输入不小于{0}的字符',
		},
	});
});
