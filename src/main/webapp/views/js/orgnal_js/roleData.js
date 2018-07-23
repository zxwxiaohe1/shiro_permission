function roleTreeGrid(ctx) {
	role = {
		// 添加职位函数
		addRole : function() {
			var node = $('#roleTreeGrid').treegrid('getSelected');
			if (node){
				alert(node.id);
				$('#roleTreeGrid').treegrid('append', {
					after: node.id,
					data: {}
				});
			}

		},
		//打开dialog窗体函数
		openDialog : function() {
			$('<div id="orangalDialog"></div>').dialog({
								id : 'orangalDialogId',
								title : '用户编辑',
								width : 600,
								height : 400,
								inline : false,
								shadow : false,
								modal : true,
								closable : false,
								href : ctx
										+ '/role/redirct.action?address=role/rolesinfo',
								buttons : [ {
									id : 'saveUserbut',
									text : '保存',
									handler : function(data) {
										$('#redactForm').submit();

									}

								}, {
									text : '关闭',
									handler : function() {
										$("#orangalDialog").dialog("destroy");
									}
								} ],
								onLoad : function(data) {
									$("#rolesDatagrid").datagrid({
														iconCls : 'icon-save',
														url : ctx
																+ '/role/getAllUserByRid.action?rid='
																+ $('#roleTreeGrid').treegrid('getSelections')[0].id
																+ '&math='
																+ Math.random(),
														method : 'POST',
														width : 600,
														rownumbers : true,
														striped : true,// 是否显示斑马线效果
														collapsible : true,
														sortName : 'id',// 定义哪些列可以进行排序
														sortOrder : 'desc',// 定义列的排序顺序
														remoteSort : false,// 定义从服务器对数据进行排序
														idField : 'id',// 指明哪个字段为标识字段
														columns : [ [
																{
																	// field :
																	// 'id',
																	title : '编号',
																	sortable : true,
																	width : 50,
																	checkbox : true,
																},
																{
																	field : 'id',
																	title : '用户ID',
																	hidden : true,
																},
																{
																	field : 'text',
																	title : '用户名',
																	width : 100,

																},
																{
																	field : 'number',
																	title : '编号',
																	width : 100,

																},
																{
																	field : 'loginname',
																	title : '登录名',
																	width : 100,

																},
																{
																	field : 'age',
																	title : '年龄',
																	width : 100,

																},
																{
																	field : 'serialnumber',
																	title : '工号',
																	width : 100,
																} ] ],
													});
								}
							});
		}
	};
	$('#roleTreeGrid').treegrid({
						url : ctx + '/role/roleTree.action?ranks=1',
						width : 1150,
						idField : 'id',
						treeField : 'text',
						toolbar : '#roleBar',
						columns : [ [
								{
									field : 'id',
									title : '用户ID',
									hidden : true,
								},
								{
									id : "menu",
									field : 'text',
									title : '菜单名称',
									width : 400,
								},
								{
									id : "oraganName",
									field : 'personname',
									title : '组织人员',
									width : 400,
									formatter : function(value, rowData, index) {
										// alert(value+rowData+index);
										return $('#orangalNames').html();
									},

								},
								{
									id : "",
									field : 'textd',
									title : '角色职务',
									width : 200,
									formatter : function(value, rowData, index) {
										return '<img src="'
												+ ctx
												+ '/views/js/easyUI/themes/icons/department.png"/>角色职务';
									},
								} ] ],
						onClickRow : function(row) {
							$('#roleTreeGrid')
							.treegrid('beginEdit',row.id);
						}
					});
}