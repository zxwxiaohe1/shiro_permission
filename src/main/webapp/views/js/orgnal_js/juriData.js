/**
 * @author Administrator
 */

// data展示用户信息
function authoritygrid(ctx) {
	clickHtml = '';
	addRows = true;
	$('#authority').datagrid({
						iconCls : 'icon-save',
						url : ctx + '/role/getAllRoles.action',
						method : 'POST',
						width : 1150,
						rownumbers : true,
						striped : true,// 是否显示斑马线效果
						collapsible : true,
						sortName : 'id',// 定义哪些列可以进行排序
						sortOrder : 'desc',// 定义列的排序顺序
						remoteSort : false,// 定义从服务器对数据进行排序
						idField : 'id',// 指明哪个字段为标识字段
						toolbar : [ {
							id : 'btnadd',
							text : '添加职位',
							iconCls : 'icon-add',
							handler : function() {
								addUser();// 调用添加函数
							}
						}, {
							id : 'btndelete',
							text : '删除职位',
							iconCls : 'icon-cancel',
							handler : function() {
								deleteUser(ctx);// 调用删除用户
							}
						}, {
							id : 'btsave',
							text : '保存',
							iconCls : 'icon-save',
							handler : function() {
								$('#test').datagrid('endEdit', 0);
							},
						} ],
						columns : [ [
								{
									field : 'id',
									title : '职位ID',
									hidden : true,
								},
								{
									title : '编号',
									sortable : true,
									width : 150,
									checkbox : true,
								},
								{
									field : 'text',
									title : '职位名称',
									width : 180,
									editor : {
										type : 'validatebox',
										options : {
											required : true,
										},

									},
								},
								{
									field : 'rank',
									title : '职位等级',
									width : 180,
									editor : {
										type : 'validatebox',
										options : {
											required : true,
										},

									},
								},
								{
									field : 'type',
									title : '职位从事方向',
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
									title : '职位编号',
									width : 180,
									editor : {
										type : 'validatebox',
										options : {
											required : true,
										},

									},
								},
								{
									id : "addauthority",
									title : '添加权限',
									field : 'addauthor',
									width : 100,
									align : 'left',
									formatter : function(value, row, index) {
										return '<img src="'
												+ ctx
												+ '/views/js/easyUI/themes/icons/pencil.png"/>权限编辑';
									},
								}, ] ],
						pagination : true,
						pageSize : 30,
						pageList : [ 30, 60, 90 ],
						pageNumber : 1,
						pagePosition : 'bottom',
						onClickCell : function(rowIndex, field, value) {
							var roleid = $('#authority').datagrid('getData').rows[rowIndex]; // 全局变量记录点击的行的权限ID
							if (field == "addauthor") {
								getAuthority(ctx, roleid['id']); // 编辑用户
							}

						},

					});
	$('#btsave').hide();
}
function getAuthority(ctx, roleid) {
	strNum=0;
	strNums=0;
	var _this = $('<div id="adddialog"></div>').dialog({
						id : 'newAuDialog',
						title : '权限添加',
						width : 532,
						height : 350,
						inline : false,
						closable : false,
						modal : true,
						href : ctx
								+ '/jurisdiction/redirct.action?address=jurisdiction/authorpanel&math='
								+ Math.random(),
						buttons : [
								{
									text : '保存',
									handler : function(data) {
										var authorId = [];
										var inputs = $(".auhidden>input");
										// alert(inputs.length);
										$.each(inputs, function(n, value) {
											authorId.push(value.value);
										});

										$.ajax({url : ctx+ '/jurisdiction/saverRJrelation.action?math='+ Math.random(),// 提交的地址
													type : "post",// 提交的方式
													data : {
														authorId : authorId
																.join(','),
														roleid : roleid,
													},// 提交的参数
													success : function(sudata) {
														$.messager.confirm('提示','已为该角色分配权限！',
																		function(flag) {
																			if (flag) {_this.dialog("destroy");
																			}
																		});
													}
												});

									}

								}, {
									text : '关闭',
									handler : function() {
										_this.dialog("destroy");
									}
								} ],
						onLoad : function() {
							$.ajax({
									url : ctx+ '/jurisdiction/getRoleAuthor.action?math='+ Math.random(),// 得到除了当前角色权限id的所有权限id
										type : "post",// 提交的方式
										data : {
											rid : roleid,
										},// 提交的参数
										success : function(roleauthor) {
											strNums+=1;
											$.each(roleauthor,function(n, value) {	
												if(strNums==2){
													return false;}
																	var newLine = $("#addauthorTable").length;
																	var row = addauthorTable.insertRow(newLine);
																	var col = row.insertCell(0);
																	col.innerHTML = '>><a href="#" style="text-decoration:none;color:black;" class="auhidden" onClick="removeAuthor(this)">'
																			+ value.text
																			+ '<input type="hidden" name="authorinid" value="'
																			+ value.id
																			+ '"> </a>';																
															});
										}
									});
							$.ajax({
										url : ctx+ '/jurisdiction/getAllauthors.action?math='+ Math.random(),// 得到除了当前角色权限id的所有权限id
										type : "post",// 提交的方式
										data : {
											rid : roleid,
										},// 提交的参数
										success : function(authordata) {
											strNum+=1;		
											$.each(authordata,function(k, values) {	
												if(strNum==2){return false;}
												var newLine = $("#authorTable").length;
												var row = authorTable.insertRow(newLine);
												var col = row.insertCell(0);
												col.innerHTML = '>><a href="#" style="text-decoration:none;color:black;" onClick="chosseAuthor(this)">'
																+ values.text+ '<input type="hidden" name="authorinid" value="'
																+ values.id+ '"> </a>';
																	$('#addauthor').linkbutton({
																						iconCls : 'icon-add',
																						disabled : true,
																					});
																	$( '#removeauthor').linkbutton( {
																						iconCls : 'icon-remove',
																						disabled : true,
																					});
																								
											});
						}
					});
						}});	}
function chosseAuthor(htmls) {
	clickHtml = $(htmls).html();
	removepHtml = $(htmls).parent().parent();
	$('#addauthor').linkbutton('enable');
}
function addAuthor() {
	var newLine = $("#addauthorTable").length;
	var row = addauthorTable.insertRow(newLine);
	var col = row.insertCell(0);
	col.innerHTML = '>><a href="#" style="text-decoration:none;color:black;" class="auhidden" onClick="removeAuthor(this)">'
			+ clickHtml + '</a>';
	removepHtml.remove();
	$('#addauthor').linkbutton('disable');
}
function removeAuthor(obg) {
	$('#removeauthor').linkbutton('enable');
	removeHtmls = $(obg).parent().parent();
}
function removeTrueAuthor() {
	removeHtmls.remove();
	removeHtmls.children().children().attr("onClick", "chosseAuthor(this)");
	removeHtmls.children().children().attr("class", " ");
	var newLine = $("#authorTable").length;
	var row = authorTable.insertRow(newLine);
	var col = row.insertCell(0);
	col.innerHTML = removeHtmls.html();
	$('#removeauthor').linkbutton('disable');
}
