/**
 * Eova Layui Table 常用操作扩展
 */

// 确认删除
var confirmDel = function(ID, code) {
	tableConfirm(ID, $.I18N('请确认'), $.I18N('您要删除当前所选数据'), $.str.format('/grid/delete/{0}', code), $.I18N('删除成功'));
}

// 确认隐藏
var confirmHide = function(ID, code) {
	tableConfirm(ID, $.I18N('请确认'), $.I18N('您要隐藏当前所选数据'), $.str.format('/grid/hide/{0}', code), $.I18N('隐藏成功'));
}

// 有且只有一行数据被选中
function isSelected(ID) {
	var num = getSelectRows(ID).length;
	if (num == 0) {
		$.msg($.I18N('请选择一行数据'));
		return false;
	}
	if (num > 1) {
		$.msg($.I18N('请勿选择多行数据'));
		return false;
	}
	return true;
}

// 获取选中行字段值
function getSelectVal(ID, fieldName) {
	if (!ID) {
		$.msg('ID is null');
		return;
	}
		
	if (!fieldName){
		$.msg('获取选中值,字段名不能为空');
		return;
	}
		
	var row = getSelectRow(ID);
	if(row)
		return row[fieldName];
	return undefined;
}

// 获取选中行数据
function getSelectRow(ID) {
	if(isSelected(ID)){
		return getSelectRows(ID)[0];
	}
}

// 获取所有选中行数据
function getSelectRows(ID) {
	var rows;
	layui.use('table', function() {
		rows = layui.table.checkStatus(ID).data;
	});
	return rows;
}

// 获取所有选中行的ID集合字符串逗号拼接
function getSelectIds(ID, idKey){
	if (!idKey) idKey = 'id';
	var rows = getSelectRows(ID);
	var vals = [];
    for (var i = 0; i < rows.length; i++) { //组成一个字符串，ID主键用逗号隔开
    	vals.push(rows[i][idKey]);
    }
	return vals.join(',');
}

// 表格二次确认操作
function tableConfirm(ID, title, info, url, msg) {
	layui.use([ 'table' ], function() {
		var $ = layui.$, table = layui.table;
		var rows = getSelectRows(ID);
		if(!rows || rows.length == 0){
			$.msg($.I18N('请选择一行数据'));
			return;
		}

		layer.confirm(info, {
			title : title,
			icon : 3,
			btn : [ '确定', '取消' ]
		}, function(index) {
			$.syncPost(url, {
				rows : JSON.stringify(rows)
			}, function(result, status) {
				if (result.success) {
					$.msg(msg);
					table.reload(ID);
				} else {
					$.alert(result.msg);
				}
			});
			layer.close(index);
		});
	});
}

/**
 * Eova Layui Table 高级拓展
 */
;(function ($) {
	var SP = "★";
	$.table = {
		buildDiyToolbar: function(isAdmin, isExport, isEovaDiy){
			let t = [];

			// 管理员功能
			if (isAdmin){
				t = [{
					title: '元字段编辑',
					layEvent: 'TABLE_FIELDS',
					icon: 'layui-icon-util'
				}, {
					title: '元对象编辑',
					layEvent: 'TABLE_OBJECT',
					icon: 'layui-icon-circle-dot'
				}, {
					title: '保存列宽',
					layEvent: 'SAVE_WIDTH',
					icon: 'eova-icon-table'
				}];
			}

			// 用户导出
			if (isExport){
				t.push({
					isAdmin: false,
					title: '导出当前查询数据',
					layEvent: 'EOVA_EXPORT',
					icon: 'layui-icon-export'
				});
			}

			// 用户个性化
			if (isEovaDiy){
				t.push({
					isAdmin: false,
					title: '表格显示设置',
					layEvent: 'EOVA_TABLE',
					icon: 'layui-icon-cols'
				});
				t.push({
					isAdmin: false,
					title: '查询条件设置',
					layEvent: 'EOVA_QUERY',
					icon: 'layui-icon-search'
				});
			}

		    return t;
		},
		/**
		 * Toolbar 弹出菜单
		 */
		openMenu: function ($this, sets) {
			var list = $(sets.list),
				panel = $('<ul class="layui-table-tool-panel"></ul>');
			panel.html(list);
			$this.find('.layui-table-tool-panel')[0] || $this.append(panel);
			panel.on('click', function (e) {
				layui.stope(e);
			});
			sets.done && sets.done(panel, list)
		},
		/**
		 * 表格右上侧自定义Toolbar 事件
		 * @param {*} obj 按钮
		 */
		diyToolbarEvent: function ($this, obj, object, menuCode, ID, table) {
			function doExport(){
				
				$.table.openMenu($this, {
					list: function () {
						return [
							'<li data-type="xls" title="适合少量(3W内)数据导出">导出到XLS文件</li>',
							'<li data-type="csv" title="适合大量数据导出">导出到CSV文件</li>'
						].join('')
					}(),
					done: function (panel, list) {
						list.on('click', function () {
							var type = $(this).data('type')
							exec(type);
						});
					}
				});
				
				function exec(type){
					var url = '/grid/export/' + object.code;
					url += '?type=' + type;
					if (menuCode != '') {
						url +='&menu_code=' + menuCode;
					}
	
					// URL参数
					var urlParas = $.getUrlParas();
					if (urlParas) {
						url += '&' + urlParas;
					}
					// 单主表按查询条件过滤
					var data = $('#queryForm').serializeArray();
					// 主表排序条件
					
					// 子表仅根据关联条件过滤
					try {
						if (MASTER_OBJECT_CODE && MASTER_OBJECT_CODE != object.code) {
							if (!SLAVE_FILTER) {
								$.msg('请先选中行');
								return;
							}
							data = [];
							$.each(Object.keys(SLAVE_FILTER), function(i, o){
								data.push({
									"name" : o,
									"value" : SLAVE_FILTER[o]
								});
							})
						}
					} catch(e) {}
	
					// 构建Form表单模拟Post提交
					var $form = $($.str.format("<form method='post' action='{0}' target='_blank'></form>", url));
					$.each(data, function (i, o) {
						$form.append($.str.format("<input type='hidden' name='{0}' value='{1}'/>", o.name, o.value));
					});
					$(document.body).append($form);
					$form.submit();
				}
			}
			
			switch (obj.event) {
				case 'EOVA_TABLE':
					var url = '/meta/diy/' + object.code + '?type=1';
					$.open.show('表格显示设置', url, 500, 0.9);
					break;
				case 'EOVA_QUERY':
					var url = '/meta/diy/' + object.code + '?type=2';
					$.open.show('查询条件设置', url, 500, 0.9);
					break;
				case 'TABLE_FIELDS':
					window.open('/meta/edit/' + object.code);
					break;
				case 'TABLE_OBJECT':
					var url = '/form/update/eova_object_code-' + object.id;
					dialog(ID, '元对象编辑' + object.code, url, 720, 550);
					break;
				case 'SAVE_WIDTH':
					var $header = table.getHeader(ID);
					var widths = [];
					$.each($header.find('th'), function (i, o) {
						var $o = $(o);
						if ($o.data('field') != "0") {
							widths.push(parseInt($o.width()));
						}
					});
					$.getJSON('/grid/updateWidths/' + object.code + '-' + widths.join(','), function () {
						$.msg("当前表格宽度已保存");
					});
					break;
				case 'EOVA_EXPORT':
					doExport();
					break;
			};
		},
		/**
		 * 构建表头合并
		 * 
		 * @param {*} object 元对象
		 * @param {*} cols 列集合
		 */
		buildMergeColumn: function (objectConfig, cols) {
			// 元对象配置格式:[{"head": "A组", "cols": ["aaa", "bbb"]}, 
			//	{"head": "B组", "cols": ["ccc", "ddd"]}]
			
			var json = $.json.toObj(objectConfig);
			if (!json.merge) {
				return cols = [cols];
			}
			var mergeConfig = json.merge;
			
			var row1 = [];
			var row2 = [];
			
			// 合并检查 -1=无需合并 i=合并组第几个位置
			function merge(field){
				for (var i = 0; i < mergeConfig.length; i++) {
					var m = mergeConfig[i] 
					var sum = m.cols.length;
					for (var j = 0; j < m.cols.length; j++) {
						if(m.cols[j] == field){
							return {
								title : m.head,	// 表头
								pos : j,		// 当前是第几项
								sum : sum		// 本组一共几项
							};
						}
					}
					
				}
				return undefined;
			}
			
			$.each(cols, function(i, o){
				var mr = merge(o.field);
				
				// 无需合并 显示在第一行 占1列2行
				if(!mr){
					o.colspan = 1;
					o.rowspan = 2;
					row1.push(o)
					return;
				}
				
				// 被合并首项替换为表头
				if (mr.pos == 0) {
					var t = new Object;
					t.title = mr.title;
					t.align = 'center';
					t.colspan = mr.sum;
					t.rowspan = 1;
					row1.push(t);
				}
				
				// 把被合并项 移入第二行
				o.colspan = 1;
				o.rowspan = 1;
				row2.push(o)
			});
			
			// 多表头为二维数组, 为了简化逻辑暂时仅支持二级合并, 可满足大部分需求.
			return [row1, row2];
		},
		/**
		 * 构建列
		 * 
		 * @param {*} object 元对象
		 * @param {*} cols 列集合
		 */
		buildFirstColumn: function (object, fields, cols) {
			var first = new Object;
			first.fixed = 'left';
			first.type = 'checkbox';
			if (object.is_single) {
				// 单选显示行号
				first.type = 'numbers';
				// normal（常规列，无需设定）
				// checkbox（复选框列）
				// radio（单选框列，layui 2.4.0 新增）
				// numbers（序号列）
				// space（空列）
			}

			// 移动端Table列自适应
			if ($.sys.isMobile()) {
				cols.forEach(x => {
					x.width = undefined;
					// x.minWidth = 40;
				})
			}


			return [first].concat(cols);
		},
		/**
		 * 构建列
		 * 
		 * @param {*} object 元对象
		 * @param {*} fields 元字段
		 * @param {*} cols 列集合
		 */
		buildColumn: function (object, fields, cols) {
			var num = 1;// 第几个显示行
			var leftFiexdCol = [];// 靠左固定列
			
			$.each(fields, function (i, f) {
				if (!f.is_show) {
					// continue;
					return;
				}

				var col = new Object;
				
				// TODO 临时增加所有EOVA 元对象 宽度宽 + easyui->layui过渡,V3.x还是在DB中修改
				if (object.code.indexOf('eova_field_code') != -1) {
					f.width = f.width + 15;
				}
				col.field = f.en;
				col.title = f.cn;
				col.width = (f.width ? f.width : 150)
				// 默认=0, eova=15
				if (col.width < 16) {
					// 自适应
					col.width = undefined;
				}

				if(f.config){
					var conf = $.json.toObj(f.config);
					if('totalRowText' in conf){
						col.totalRowText = conf.totalRowText;
					} else {
						col.totalRow = conf.totalRow;
					}
				}
				
				// 是否可排序
				if (f.is_order) {
					col.sort = true;
				}

				// 单元格编辑模式单独增加列宽,用于 padding: 5px
				if (object.is_celledit && f.is_edit) {
					col.width = col.width + 10;
					// 禁用排序,避免格式化被重新渲染
					col.sort = false;
					// 禁止拖拽列宽
					col.unresize = true;
				}

				// 列的格式化处理
				$.table.formatColumn(object, f, col);
				
				// 固定列停靠的处理:
				// 注意：如果是固定在左，该列必须放在表头最前面；如果是固定在右，该列必须放在表头最后面。
				// 否则会出现固定列遮盖活动列
				if (f.is_fixed) {
					col.fixed = 'left';
					leftFiexdCol.push(col);
				} else {
					cols.push(col);
				}
				num++;
			});
			
			// 固定列+活动列
			return leftFiexdCol.concat(cols);
		},
		/**
		 * 构建手工模式行初始化对象模型(格式化会自动根据此对象渲染UI)
		 * 
		 * @param {*} tableIns 表格
		 */
		buildEditRow: function (tableIns, fields) {
			var row = new Object;
			$.each(fields, function (i, f) {
				// 单元格编辑模式
				if (f.is_show && f.is_edit) {
					var fieldName = f.en;
					if (f.type == '下拉框' || f.type == '查找框') {
						// 下拉和查找 有两种值, text 和 value, 行编辑模式需要给值域字段进行初始赋值,因为格式化取的是值域
						fieldName = fieldName + '_val';
					}
					row[fieldName] = '';
				}
			});
			// 把准备好的对象存放在表格实例的配置中,方便外界取用
			tableIns.config.editrow = row;
		},
		/**
		 * 格式化列
		 * 
		 * @param {*} object 元对象
		 * @param {*} f 元字段
		 * @param {*} col 当前列
		 */
		formatColumn: function (object, f, col) {
			
			function getKey(row){
				return f.en + SP + (row.pk_val || row.LAY_INDEX);// 主键 || 行索引
			}
			
			if (object.is_celledit && f.is_edit) {
				// 单元格格式化的处理
				if (f.type == '布尔框') {
					col.align = 'center';
					col.templet = function (val, row, index, field) {
						var key = getKey(row);
						return $.str.format('<div class="eova-bool" id="{0}" name="{1}" value="{2}"></div>', key, key, val);
					};
				} else if (f.type == '下拉框') {
					col.templet = function (vlaue, row, index, field) {
						var key = getKey(row);
						var val = row[f.en + '_val'];

						var url = '/widget/comboJson/' + object.code + '-' + field;
						var options = $.str.format("width: {0},url: '{1}',valueField: '{2}', textField: '{3}', multiple: {4}", f.width, url, 'id', 'cn', f.is_multiple);

						return $.str.format('<div class="eova-form-field"><div class="eova-combo" id="{0}" name="{1}" value="{2}" data-options="{3}"></div></div>', key, key, val, options)
					};
				} else if (f.type == '查找框') {
					col.templet = function (value, row, index, field) {
						var key = getKey(row);
						var val = row[f.en + '_val'];

						var options = $.str.format("width: {0},multiple: '{1}'", f.width, f.is_multiple);

						return $.str.format('<div class="eova-form-field"><div class="eova-find" id="{0}" name="{1}" value="{2}" code="{3}" field="{4}" data-options="{5}"></div></div>',
							key, key, val, object.code, f.en, options)
					};
				} else if (f.type == '日期框' || f.type == '时间框') {
					col.templet = function (val, row, index, field) {
						var key = getKey(row);
						var options = $.str.format("width: {0},format: '{1}'", f.width, f.type == '时间框' ? 'yyyy-MM-dd HH:mm:ss' : 'yyyy-MM-dd');
						return $.str.format('<div class="eova-form-field"><div class="eova-time" id="{0}" name="{1}" value="{2}" data-options="{3}"></div></div>', key, key, val, options)
					};
				} else if (f.type == '文本域' || f.type == '编辑框') {
					col.templet = function (val, row, index, field) {
						var key = getKey(row);
						if (val == undefined)
							val = '';
						// 特殊处理,有JS函数,会破坏DOM || JSON 数据丢失
						if (f.en == 'formatter' || f.en == 'config') {
							val = $.str.encodeUrl(val);
						}
						return $.str.format('<div class="eova-form-field"><div class="eova-info" id="{0}" name="{1}" value="{2}" data-options="width: {3}, placeholder: \'{4}\'"></div></div>', key, key, val,
							f.width, f.placeholder ? f.placeholder : '')
					};
				}
				// 默认文本框
				else {
					col.templet = function (value, row, index, field) {
						var key = getKey(row);
						var val = row[f.en];
						if (val == undefined)
							val = '';
						return $.str.format('<div class="eova-form-field"><div class="eova-text" id="{0}" name="{1}" value="{2}" data-options="width: {3}, isBtn: false"></div></div>',
							key, key, val, f.width)
					};
				}
			} else {
				// 常规格式化处理
				if (f.formatter != null && f.formatter != '') {
					col.templet = new Function('return ' + f.formatter + ';')();
				} else {
					// 默认格式化处理
					if (f.type == '布尔框') {
						col.align = 'center';
						col.templet = function (value, row, index, field) {
							var ck = '<i class="eova-icon">&#xe63f;</i>';
							if (value || value == 1) {
								ck = '<i class="eova-icon">&#x1005;</i>';
							}
							return ck;
						};
					}
					else if (f.type == '日期框') {
						col.templet = function (value, row, index, field) {
							if(value && value.length == 19){
								return value.replace(' 00:00:00', '');
							} else {
								return value;
							}
						};
					}
					// else if (f.type == '图片') {
					// 	col.templet = function (value, row, index, field) {
					// 		if(value){
					// 			return '<img width=40 title="点击查看大图" src="' + value + '"/>';;
					// 		}
					// 		return '';
					// 	};
					// }
					// else if (f.type == '文件') {
					// 	col.templet = function (value, row, index, field) {
					// 		if(value){
					// 			return '<a href="' + value + '">' + value + '</a>';
					// 		}
					// 		return '';
					// 	};
					// }

					// if (f.type == '文本框' || f.type == '编辑框' || f.type == '文本域') {
					// col.formatter = function (row) {
					// if (value && value.length > 10) {
					// //alert($.encodeHtml(value));
					// return '<span title="' + $.encodeHtml(value) + '">' + $.encodeHtml(value) + '</span>'
					// }
					// return value;
					// }
					// }
				}
			}
		},
		// 单元格绑定值变更监听事件 event(值, 字段名, 所在行索引值)
		change: function (eova, event) {
			$(eova + 'bool').eovabool({
				onChange: function (val) {
					var name = $(this).children().attr('name');
					var index = $(this).parents('tr').data('index');
					event(val, name, index);
				}
			});
			$(eova + 'text').eovatext({
				onChange: function (val) {
					var name = $(this).children().attr('name');
					var index = $(this).parents('tr').data('index');
					event(val, name, index, true);
					// 事件覆盖, 以最后一次事件为准
				}
			});
			$(eova + 'combo').eovacombo({
				onChange: function (oldVal, newVal) {
					var name = $(this).children().attr('name');
					var index = $(this).parents('tr').data('index');
					event(newVal, name, index);
				}
			});
			$(eova + 'find').eovafind({
				onChange: function (oldVal, newVal) {
					var name = $(this).children().attr('name');
					var index = $(this).parents('tr').data('index');
					event(newVal, name, index);
				}
			});
			$(eova + 'info').eovainfo({
				onChange: function (oldVal, newVal) {
					var name = $(this).children().attr('name');
					var index = $(this).parents('tr').data('index');
					event(newVal, name, index);
				}
			});
			$(eova + 'time').eovatime({
				onChange: function (oldVal, newVal) {
					var name = $(this).children().attr('name');
					var index = $(this).parents('tr').data('index');
					event(newVal, name, index);
				}
			});

		},
		// 单元格数据处理
		process: function (ID, code) {
			// Table 单元格内 Eova控件 值变化 事件绑定
			var eova = 'div[lay-id="' + ID + '"] .layui-table-body .eova-';
			
			var T;
			$.table.change(eova, function event(val, name, index, isOverwrite) {
				if (code != '') {
					if(isOverwrite){
						// 允许事件覆盖
						clearTimeout(T);
						T = setTimeout(function () {
							$.table.save(ID, code, val, name);
					    }, 500);
					} else {
						// 直接保存
						$.table.save(ID, code, val, name);
					}
					
				} else {
					$.table.update(ID, index, val, name);
				}
			});
		},
		// 保存在线数据
		save: function (ID, code, val, name) {
			var t = name.split(SP);
			var field = t[0],
				pk = t[1];

			$.post('/grid/updateCell', {
				code: code,
				pk: pk,
				field: field,
				val: val,
			}, function (result) {
				if (result.success) {
					// 右下提示
					layer.msg('自动保存成功', {offset:'rb'}); 
				} else {
					$.msg(result.msg);
				}
			}, 'json');
		},
		// 更新本地数据
		update: function (ID, index, val, name) {
			var t = name.split(SP);
			var field = t[0],
				pk = t[1];

			layui.use(['table'], function () {
				var table = layui.table;
				// 当前表格实例
				var tableIns = table.getIns(ID);
				// 当前表格本地数据
				var data = tableIns.config.data;

				$.each(data, function (i, o) {
					// 判断行
					if (index == o.LAY_TABLE_INDEX) {
						// 如果存在值域,把值更新到值域上
						var valueFieldName = field + '_val';
						if (valueFieldName in o) {
							o[valueFieldName] = val;
						} else {
							o[field] = val;
						}
					}
				});

				// 更新数据
				//tableIns = table.reload(ID, { data: data });
			});
		},
		// 新增多行
		addRows: function (ID, rows) {
			for (var i = 0; i < rows.length; i++) {
				$.table.addRow(ID, rows[i]);
			}
		},
		// 新增单行(row is null 初始化空行)
		addRow: function (ID, row) {
			layui.use(['table'], function () {
				var $ = layui.$,
					table = layui.table;

				var tableIns = table.getIns(ID);
				var data = tableIns.config.data;
				var editrow = row || tableIns.config.editrow; // 主动添加数据 or 添加空行

				var data = data || [];
				data.push(editrow);
				// data.push({category_val: '',num: 1,}); 手工构造行对象

				// 更新数据
				tableIns = table.reload(ID, {
					data: data
				});
			});
		},
		// 删除行
		delRow: function (ID) {
			layui.use(['table'], function () {
				var $ = layui.$,
					table = layui.table;

				var tableIns = table.getIns(ID);
				var data = tableIns.config.data;

				// 获取选中行的索引
				var index = table.checkIndex(ID);
				if (index == -1) {
					$.msg($.I18N('请选择一行数据'));
					return;
				}

				// 逆向删除(防止下标异常)
				for (var i = data.length - 1; i >= 0; i--) {
					var o = data[i];
					if (index == o.LAY_TABLE_INDEX) {
						data.splice(i, 1);
					}
				}

				// 更新数据
				tableIns = table.reload(ID, {
					data: data
				});

			});
		},
		// 获取表格中正在编辑的数据(仅限手工编辑模式使用)
		getData: function (ID) {
			var data;
			layui.use('table', function () {
				data = layui.table.getIns(ID).config.data;
			});
			return data;
		},
		// 清空数据
		clearData: function (ID) {
			layui.use('table', function () {
				layui.table.reload(ID, {
					data: []
				});
			});
		},
		// 更新数据
		updateData: function (ID, data) {
			layui.use('table', function () {
				layui.table.getIns(ID).config.data = data;
			});
		}
	};
})(jQuery);