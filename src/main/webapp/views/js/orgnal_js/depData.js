
function departmentdg(ctx) {
	$('#departmentdg').treegrid({
						url : '/sinuxproject/department/departmentTree.action?ranks=1',
						width : 1150,
						idField : 'id',
						treeField : 'text',
						toolbar : '#departmentBar',
						columns : [ [
								{
									id : "did",
									field : 'id',
									title : '菜单名称',
									hidden : true
								},
								{
									id : "menu",
									field : 'text',
									title : '菜单名称',
									width : 400,
								},
								{
									id : "pname",
									field : 'personname',
									title : '人员名单',
									width : 400,
									formatter : function(value, rowData, index) {
										var powers = '';
										powers = '<img src="'
												+ ctx
												+ '/views/js/easyUI/themes/icons/department.png"/>名单';
										return powers;
									},
								}, {
									id : 3,
									field : 'textd',
									title : '菜单名称',
									width : 200,
								} ] ],
						onDblClickRow : function(row) {
							clickName(ctx, row);// 根据did查询部门下的所有用户
						},

					});
	// 定义点击事件函数
	department = {
		addDepartment : function() {
			var num = 0;
			 adddpDialog = $('<div id="adddp"></div>').dialog({
								id : 'departmentDialog',
								title : '添加部门',
								width : 500,
								height :350,
								closable : false,
								href : ctx
										+ '/department/redirct.action?address=department/adddepartment',
								toolbar : [ {
									id : 'addinput',
									text : '添加职位',
									iconCls : 'icon-add',
									handler : function() {
										if (num == 0) {
											$('#dpComboBoxchild').combobox({});
											num += 1;
										} else if (num == 1) {
											$('#dpComboBoxson').combobox({});
										}
									}
								} ],
								buttons : [ {
									text : '确定',
									handler : function(data) {
										saveDpartment(ctx);
									}
								}, {
									text : '关闭',
									handler : function() {
										adddpDialog.dialog("destroy");
									}
								} ],
								onLoad : function(data) {
									$('#dpComboBox').css("display", "block");
									$('#dpComboBox').combobox({
														url : ctx
																+ '/department/findDepartmentByRank.action?ranks=1& math='
																+ Math.random(),
														valueField : 'id',
														textField : 'text',
														onSelect : function(record) {
															$('#combox2').css("display", "none");
															$('#dpComboBoxchild').combobox({
																				url : ctx
																						+ '/department/findDepartmentByDtype.action?did='
																						+ record.id
																						+ '&ranks=2& math='
																						+ Math
																								.random(),
																				method : 'post',
																				valueField : 'id',
																				textField : 'text',
																				onSelect : function(record) {
																					$('#dpComboBoxson').combobox({
																										url : ctx
																												+ '/department/findDepartmentByDtype.action?did='
																												+ record.id
																												+ '&ranks=3& math='
																												+ Math
																														.random(),
																										method : 'post',
																										valueField : 'id',
																										textField : 'text',
																									});
																				}

																			});

														}
													});

								}
							});
		}
	};
}
function saveDpartment(ctx) {
	var level = 1;
	// 获取一级部门的ID
	firstValue = $('#dpComboBox').combobox('getValue');
	$.ajax({
		url : ctx + '/department/findDepartmentByDid.action?math=' + Math.random(),// 提交的地址
		type : "post",// 提交的方式
		data : {
			did: firstValue
		},// 提交的参数
		success : function(data) {
			if(data!=""){
				if($('input').size() == 8){
				// 获取一级部门的值
				Text = $('#dpComboBox').combobox('getText');
				level = 1;
			}else if ($('input').size() == 10) {
				// 获取二级部门的值
				Text = $('#dpComboBoxchild').combobox('getText');
				level = 2;
			} else if ($('input').size() == 12) {
				// 获取三级部门的值
				Text = $('#dpComboBoxson').combobox('getText');
				level = 3;
			}
			$.ajax({
				url : ctx + '/department/register.action?math=' + Math.random(),// 提交的地址
				type : "post",// 提交的方式
				data : {
					prentId : firstValue,
					text : Text,
					level : level
				},// 提交的参数
				success : function(data) {
					if(data.trim()=="success"){
						$.messager.alert('提示', '数据添加成功！', 'info',function () {
							adddpDialog.dialog("destroy");
						});	
					}
				}
			});
			}else{
				$('#dpComboBoxchild').hide();
				$('#dpComboBoxson').hide();
				$('#combox2').css("display", "block");
				$('#dptype').combobox({});
				$('#dprank').combobox({});
				$('#dpdstatus').combobox({});
				if($('#dptype').combobox('getText')!=""){
					$.ajax({
						url : ctx + '/department/registDepartment.action?math=' + Math.random(),// 提交的地址
						type : "post",// 提交的方式
						data : {
							text:$('#dpComboBox').combobox('getText'),
							dtype :$('#dptype').combobox('getText'),
							rank : $('#dprank').combobox('getText'),
							dstatus : $('#dpdstatus').combobox('getText'),
						},// 提交的参数
						success : function(data) {
							if(data.trim()=="success"){
								$.messager.alert('提示', '数据添加成功！', 'info',function () {
									adddpDialog.dialog("destroy");
								});	
							}
							
						}
					});
				}
				
			}
		}
	});

}
function clickName(ctx, row) {
	var child = $('#departmentdg').treegrid("getChildren", row.id);
	var childId = [];
	if (child.length != 0) {
		$.each(child, function(n, value) {
			childId.push(value.id);
		});
	} else {
		childId.push(row.id);
	}
	createUserData(ctx, childId);
}
function createUserData(ctx, childId) {
	var dpDialog = $('<div id="dperson"></div>').dialog({
						id : 'departmentDialog',
						title : '部门用户',
						width : 600,
						height : 400,
						closable : false,
						href : ctx
								+ '/department/redirct.action?address=department/depUnderUsers',
						buttons : [ {
							text : '确定',
							handler : function(data) {
								dpDialog.dialog("destroy");
							}
						}, {
							text : '关闭',
							handler : function() {
								dpDialog.dialog("destroy");
							}
						} ],
						onLoad : function(data) {
							$('#personList')
									.datagrid(
											{
												width : 580,
												url : ctx
														+ '/department/finduUsersByDid.action?math='
														+ Math.random(),
												queryParams : {
													dids : childId.join(","),
												},
												rownumbers : true,
												striped : true,// 是否显示斑马线效果
												collapsible : true,
												toolbar : [ {
													id : 'btnadd',
													text : '添加用户',
													iconCls : 'icon-add',
													handler : function() {
														addUser();// 调用添加函数
													}
												}, {
													id : 'btndelete',
													text : '删除用户',
													iconCls : 'icon-cancel',
													handler : function() {
														deleteUser(ctx);// 调用删除用户
													}
												} ],
												columns : [ [ {
													title : 'ID',
													width : 50,
													sortable : true,
													checkbox : true,
												}, {
													field : 'id',
													title : '工号',
													width : 50,
													hidden : true,
												}, {
													field : 'text',
													title : '用户名',
													width : 100,

												}, {
													field : 'number',
													title : '工号',
													width : 100,

												}, {
													field : 'loginname',
													title : '登录名',
													width : 100,

												}, {
													field : 'age',
													title : '年龄',
													width : 100,

												}, {
													field : 'serialnumber',
													title : '工号',
													width : 100,
												} ] ],
											});
						},

					});
}
// 获得相关职位角色的人员名册
function getPersonName(ctx, field, rowIndex) {
	$('#persondg').dialog({
						id : 'newDialog',
						title : '用户编辑',
						width : 450,
						height : 250,
						inline : false,
						shadow : false,
						modal : true,
						href : ctx
								+ '/user/redactUser.action?address=user/redactuser&uid='
								+ row['uid'],
						buttons : [
								{
									text : '保存',
									handler : function(data) {
										$.ajax({url : ctx+ '/user/savaUDrelations.action',// 提交的地址
													data : {
														did : dids.join(','),
														uid : uid
													},
													type : "post",// 提交的方式
													success : function(data) {
														$.messager.confirm(
																'确认',
																'已成功为该用户分配部门!');
													}
												});

									}

								}, {
									text : '关闭',
									handler : function() {
										$("#redact").dialog("destroy");
									}
								} ],
					});

}