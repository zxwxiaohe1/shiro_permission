
// data展示用户信息
function datagrid(ctx, resultpers) {
	addRows = true;
	global = resultpers;
	user = {
		// dialog添加一行用户表格
		addUser : function() {
			$('#btsave').show();// 显示保存按钮
			// 添加一行
			if (addRows) {
				$('#test').datagrid('insertRow', {
					index : 0,
					row : {}
				});
				// 将第一行设置为可编辑状态
				$('#test').datagrid('beginEdit', 0);
				addRows = false;
			}
		},
		// 保存新建用户
		savaUser : function() {
			$('#test').datagrid('endEdit', 0);
		},
		//刷新datagrid
		refresh:function(){
			$('#btsave').hide();
			$('#test').datagrid('reload');
			$('#test').datagrid('beginEdit', 0);
		},
		// 删除用户
		deleteUser : function() {
			var rows = $('#test').datagrid("getSelections");
			if (rows == 0) {
				$.messager.alert('提示', '请选择要删除的记录！', 'info');
			} else {
				var uids = [];
				var copyRows = [];
				for (var j = 0; j < rows.length; j++) {
					copyRows.push(rows[j]);
					uids.push(rows[j].id);
				}
				$.messager.confirm('提示', '确定要删除选中数据？', function(flag) {
					if (flag) {
						$.ajax({
							url : ctx + '/user/deleteUser.action?math='+ Math.random(),// 提交的地址
							type : "post",// 提交的方式
							data : {
								uids : uids.join(','),
							},// 提交的参数
							success : function(data) {
								$.messager.alert('提示', '删除数据成功！', 'info');
								$('#test').datagrid('load');
								$('#test').datagrid('unselectAll');
								addRows = true;
								$('#btsave').hide();
							}
						});
					}
				});
			}
		}
	}
	//构建datagrid表格
	var testdatagrid = $('#test').datagrid(
					{
						iconCls : 'icon-save',
						url : ctx + '/user/userlist.action',
						method : 'POST',
						width : 1150,
						rownumbers : true,
						striped : true,// 是否显示斑马线效果
						collapsible : true,
						sortName : 'id',// 定义哪些列可以进行排序
						sortOrder : 'desc',// 定义列的排序顺序
						remoteSort : false,// 定义从服务器对数据进行排序
						idField : 'id',// 指明哪个字段为标识字段
						toolbar : '#userBar',
						columns : [ [
								{
									field : 'num',
									title : '编号',
									sortable : true,
									width : 150,
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
									width : 180,
									editor : {
										type : 'validatebox',
										options : {
											required : true,
										},

									},
								},
								{
									field : 'number',
									title : '编号',
									width : 180,
									editor : {
										type : 'validatebox',
										options : {
											required : true,
										},

									},
								},
								{
									field : 'loginname',
									title : '登录名',
									width : 180,
									editor : {
										type : 'validatebox',
										options : {
											required : true,
										},

									},
								},
								{
									field : 'age',
									title : '年龄',
									width : 180,
									editor : {
										type : 'validatebox',
										options : {
											required : true,
										},

									},
								},
								{
									field : 'serialnumber',
									title : '工号',
									width : 180,
									editor : {
										type : 'validatebox',
										options : {
											required : true,
										},

									},
								},
								{
									id : "edit",
									title : '编辑',
									field : 'redact',
									width : 50,
									align : 'left',
									formatter : function(value, row, index) {
										return '<img src="'
												+ ctx
												+ '/views/js/easyUI/themes/icons/pencil.png"/>编辑';
									},
								},
								{
									id : "organization",
									title : '部门',
									field : 'department',
									width : 50,
									align : 'left',
									formatter : function(value, rowData, index) {
										return '<img src="'
												+ ctx
												+ '/views/js/easyUI/themes/icons/department.png"/>机构';
									},
								},
								{
									id : "role",
									title : '角色',
									field : 'role',
									width : 50,
									align : 'left',
									formatter : function(value, rowData, index) {
										return '<img src="'
												+ ctx
												+ '/views/js/easyUI/themes/icons/department.png"/>角色';
									},
								}, ] ],
						pagination : true,
						pageSize : 30,
						pageList : [ 30, 60, 90 ],
						pageNumber : 1,
						pagePosition : 'bottom',
						onAfterEdit : function(rowIndex, rowData, changes) {
							saveUser(rowData);// 向服务器端传输数据，新增用户
						},
						onClickCell : function(rowIndex, field, value) {
							if (field == "redact") {
								redact(ctx, field, rowIndex);// 编辑用户
							} else if (field == "department") {
								department(ctx, rowIndex);
							} else if (field == "role") {
								roletree(ctx, rowIndex);
							}
						},
					});
	for (var i = 0; i < global.length; i++) {
		if (global[i].trim() == "user:update") {
			$('#btnadd').show();
			break;
		} else {
			$('#btnadd').hide();
		}
	}
	$('#btsave').hide();

	// 向服务器端传输数据，新增用户
	function saveUser(rowData) {
		addRows = true;
		$('#btsave').hide();
		$.ajax({
			url : ctx + '/user/register.action',// 提交的地址
			type : "post",// 提交的方式
			data : {
				text : rowData["text"],
				number : rowData["number"],
				loginname : rowData["loginname"],
				age : rowData["age"],
				serialnumber : rowData["serialnumber"],
			},// 提交的参数
			success : function(data) {
				if (data == "success") {
					$.messager.confirm('提示', '新增用户成功！');
				}
				$('#test').datagrid('load');

			}
		});
	}
}
// 编辑处理
function redact(ctx, field, rowIndex) {
	var row = $('#test').datagrid('getData').rows[rowIndex];
	$('<div id="redact"></div>').dialog(
					{
						id : 'newDialog',
						title : '用户编辑',
						width : 450,
						height : 250,
						inline : false,
						shadow : false,
						modal : true,
						href : ctx
								+ '/user/redactUser.action?address=user/redactuser&uid='
								+ row['id'],
						buttons : [ {
							id : 'saveUserbut',
							text : '保存',
							handler : function(data) {
								$('#redactForm').submit();

							}

						}, {
							text : '关闭',
							handler : function() {
								$("#redact").dialog("destroy");
							}
						} ],
					});
	for (var i = 0; i < global.length; i++) {
		if (global[i].trim() == "user:update") {
			$('#saveUserbut').linkbutton('enable');
			break;
		} else {
			$('#saveUserbut').linkbutton('disable');
		}
	}
}
// 部门查看
function departmentChange(ctx, field, rowIndex) {
	var row = $('#test').datagrid('getData').rows[rowIndex];
	$('<div id="showdepartment"></div>').dialog(
					{
						id : 'departmentDialog',
						title : '部门查看',
						width : 450,
						height : 250,
						inline : false,
						shadow : false,
						modal : true,
						href : ctx
								+ '/user/roletree.action?address=user/shaowdepartment&uid='
								+ row['id'],
						buttons : [ {
							id : 'optionD',
							text : '确定',
							handler : function(data) {
								$('#redactForm').submit();

							}

						}, {
							text : '取消',
							handler : function() {
								$("#redact").dialog("destroy");
							}
						} ],
					});
	for (var i = 0; i < global.length; i++) {
		if (global[i].trim() == "user:update") {
			$('#optionD').linkbutton('enable');
			break;
		} else {
			$('#optionD').linkbutton('disable');
		}
	}
}
// 动态构造dialog及部门树
function department(ctx, rowIndex) {
	// 获取指定用户的部门集
	var userRoleId = null;
	var row = $('#test').datagrid('getData').rows[rowIndex];
	$.ajax({
		url : ctx + '/user/getUDid.action?uid=' + row['id'],// 提交的地址
		type : "post",// 提交的方式
		success : function(data) {
			userRoleId = data;
		}
	});
	// 构造dialog弹出框
	var row = $('#test').datagrid('getData').rows[rowIndex];
	$('<div id="showdepartment"></div>').dialog(
					{
						id : 'departmentDialog',
						title : '所属部门',
						width : 450,
						height : 250,
						inline : false,
						shadow : false,
						modal : true,
						closable : false,
						href : ctx
								+ '/user/redirct.action?address=user/showtree',
						buttons : [
								{
									id : 'dpsava',
									text : '确定',
									handler : function(data) {
										var did = [];
										var childId = $('#userroles').tree("getChecked", 'checked');
										for (var a = 0; a < childId.length; a++) {
											if ($('#userroles').tree("getChildren",childId[a].target).length != 0) {
												continue;
											} else {
												did[a] = childId[a].id;
											}
										}
										savaUDrelations(ctx, row['id'], did);
										$("#showdepartment").dialog("destroy");
									}
								}, {
									text : '取消',
									handler : function() {
										$("#showdepartment").dialog("destroy");
									}
								} ],
						onLoad : function(data) {
							// 构造tree
							$('#userroles').tree({
												checkbox : true,
												lines : true,
												url : ctx+ '/department/departmentTree.action?ranks=1',
												onLoadSuccess : function(node) {
													$.each(userRoleId,function(n,value) {
																		var node = $('#userroles').tree('find',value.id);
																		$('#userroles').tree('select',node.target);
																		$('#userroles').tree('check',node.target);
																	});
													var nodes = $('#userroles')
															.tree('getChecked');
												}
											});
						},
					});
	for (var i = 0; i < global.length; i++) {
		if (global[i].trim() == "department:updata") {
			$('#dpsava').linkbutton('enable');
			break;
		} else {
			$('#dpsava').linkbutton('disable');
		}
	}
}
// 通过选择的数节点为用户添加部门关系表
function savaUDrelations(ctx, uid, did) {
	var dids = [];
	if (did.length == 0) {
		dids[0] = 0;
	} else {
		for (var i = 0; i < did.length; i++) {
			if (typeof (did[i]) != "undefined") {
				dids.push(did[i]);
			}
		}
	}
	$.ajax({
		url : ctx + '/user/savaUDrelations.action',// 提交的地址
		data : {
			did : dids.join(','),
			uid : uid
		},
		type : "post",// 提交的方式
		success : function(data) {
			if (data.trim() == "success") {
				$.messager.confirm('确认', '已成功为该用户分配部门!');
			} else {
				$.messager.confirm('确认', '操作失败!');
			}

		}
	});
}
// 用户角色树
function roletree(ctx, rowIndex) {
	// 获取指定用户的角色集
	var userRoleId = null;
	var row = $('#test').datagrid('getData').rows[rowIndex];
	$.ajax({
		url : ctx + '/role/getRoles.action?uid=' + row['id'],// 提交的地址
		type : "post",// 提交的方式
		success : function(data) {
			userRoleId = data;
		}
	});
	// 构造dialog弹出框
	var row = $('#test').datagrid('getData').rows[rowIndex];
	$('<div id="solediv"></div>').dialog({
				id : 'roleDialog',
				title : '所属部门',
				width : 450,
				height : 300,
				inline : false,
				shadow : false,
				modal : true,
				closable : false,
				href : ctx + '/user/redirct.action?address=user/showtree',
				buttons : [
						{
							id : 'rolesava',
							text : '确定',
							handler : function(data) {
								var rid = [];
								var childId = $('#userroles').tree(
										"getChecked", 'checked');
								for (var a = 0; a < childId.length; a++) {
									if ($('#userroles').tree("getChildren",
											childId[a].target).length == 0) {
										rid[a] = childId[a].id;

									} else {
										continue;
									}
								}
								savaURrelations(ctx, row['id'], rid);
								$("#solediv").dialog("destroy");

							}
						}, {
							text : '取消',
							handler : function() {
								$("#solediv").dialog("destroy");
							}
						} ],
				onLoad : function(data) {
					// 构造tree
					$('#userroles').tree({
								checkbox : true,
								url : ctx + '/role/roleTree.action?ranks=1',
								onLoadSuccess : function(node) {
									$.each(userRoleId, function(n, value) {
										var node = $('#userroles').tree('find',
												value.id);
										$('#userroles').tree('select',
												node.target);
										$('#userroles').tree('check',
												node.target);
									});
								}

							});
				}
			});
}
// 通过选择的数节点为用户添加角色关系表
function savaURrelations(ctx, uid, rid) {
	var rids = [];
	for (var i = 0; i < rid.length; i++) {
		if (typeof (rid[i]) != "undefined") {
			rids.push(rid[i]);
		}
	}
	$.ajax({
		url : ctx + '/user/savaURrelations.action',// 提交的地址
		data : {
			rid : rids.join(','),
			uid : uid
		},
		type : "post",// 提交的方式
		success : function(data) {
		}
	});
}