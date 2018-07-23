/**
 * @author Administrator
 */
function userBack(ctx) {
	// 导航栏点击事件
	$.ajax({
		url : ctx + '/jurisdiction/findMenu.action',// 提交的地址
		type : "post",// 提交的方式
		async : false,
	}).done(
			function(data) {
				$.each(data, function(n, value) {
					if (value.text.trim() == "资源列表") {
						$("#author").append('<li><a href="#" class="menu' + n + '">'+ value.text + '</a></li>');
					} else {
						$("#sysmenu").append('<li><a href="#" class="menu' + n + '">'+ value.text + '</a></li>');
					}
					$('.menu' + n + '').click(function() {
						addtabs(value.text);
					});
				});
			});
	// 添加tab页面
	function addtabs(tittle) {
		var address = " ";
		if (tittle == $('.menu1').text()) {
			address = '<iframe scrolling="yes" framebirder="0" src="/sinuxproject/user/redirct.action?address=user/userData" style="width:100%;height:100%;"/>';
		} else if (tittle == $('.menu2').text()) {
			address = '<iframe scrolling="yes" framebirder="0" src="/sinuxproject/user/redirct.action?address=department/departmentdata" style="width:100%;height:100%;"/>';
		} else if (tittle == $('.menu3').text()) {
			address = '<iframe scrolling="yes" framebirder="0" src="/sinuxproject/user/redirct.action?address=role/roleData" style="width:100%;height:100%;"/>';
		} else if (tittle == $('.menu0').text()) {
			address = '<iframe scrolling="yes" framebirder="0" src="/sinuxproject/user/redirct.action?address=jurisdiction/juriData" style="width:100%;height:100%;"/>';
		}
		if (!$('#usertabs').tabs('exists', tittle)) {
			$('#usertabs').tabs('add', {
				title : tittle,
				content : address,
				iconCls : 'icon-save',
				closable : true
			});
		} else {
			$('#usertabs').tabs('select', tittle);
		}
	}
}