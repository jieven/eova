<table id="${id}" width="auto" height="auto"
			data-options="
					fit : true,
					border : false,
					striped : true,
					singleSelect : ${obj.isSingleSelect!true},
					align : 'right',
					collapsible : false,
					remoteSort : true,
					multiSort : false,
					rownumbers : true,
					pagination : true,
					<%if(isEmpty(url)){%>
					<%// 默认模式%>
					url : '/grid/query/${obj.code}',
					<%}else{%>
					<%// 自定义URL%>
					url : '${url}',
					<%}%>
					method : 'post',
					<%//title : '${obj.title}',%>
					idField : '${obj.pkName}',
					<%// 默认主键排序%>
					<%if(obj.isDefaultPkDesc){%>
					sortName : '${obj.pkName}',
					sortOrder : 'desc',
					<%}%>
					pageSize : 15,
					pageList : [ 15, 30, 50, 100, 200, 500, 1500, 2000],
					<%//frozenColumns : [[{field:'nickName',title:'昵称',width:80}]],%>
					toolbar : '#toolbar'
				">
			<thead>
				<tr>
				<%if(obj.isSingleSelect == false){%>
					<th data-options="field:'ck',checkbox:true,"></th>
				<%}%>

				</tr>
			</thead>
		</table>
<script>
	$(function(){
        var selectIndex;
		var gridId = "#${id}";
		var myGrid = $(gridId).datagrid({
			columns:[[
				<%if(obj.isSingleSelect == false){%>
					{field:'ck',checkbox:true,},
				<%}%>
				<%for(item in items){%>
					<%if(item.isShow!true){%>
						{
                        field:'${item.en}',
						title:'${item.cn}',
                        <%if(item.isOrder!true){%>
                        sortable:true,
                        <%}%>
						<%if(item.type == '复选框'){%>
							align:'center',
							formatter:function(value, row, index, field){
							    var ck = '<input class="eova-check-input" type="checkbox" disabled="true" ';
							    if(value){
							        ck += ' checked';
							    }
							    ck += ' value="'+ value +'"';
							    ck += ' />';
							 	return ck;
							},
						<%}%>
						<%if(item.type == '文本框'){%>
							formatter:function(value, row, index, field){
                                if(value && value.length > 10){
                                    return '<span title="' + value + '">' + value + '</span>';
                                }
                                return value;
							},
						<%}%>
                        <%// Grid Cell Editor,对象和字段允许行内编辑自增，自增长禁止编辑%>
                        <%if(isTrue(obj.isCellEdit) && isTrue(item.isEdit) && !isTrue(item.isAuto)){%>
                            editor:{type:'${item.editor}',options:{
                                url: '/widget/comboJson/${item.objectCode}-${item.en}',
                                valueField : 'ID',
                                textField : 'CN'
                            }},
                        <%}%>
                        width:${isEmpty(item.width!)?"150":item.width}
                        },
					<%}%>
				<%}%>
			]],
			onHeaderContextMenu: function(e, field){
                e.preventDefault();
                if (!cmenu){
                    createColumnMenu();
                }
                cmenu.menu('show', {
                    left:e.pageX,
                    top:e.pageY
                });
            },
            onRowContextMenu: function(e, rowIndex, rowData){
                e.preventDefault();
                if (!rowMenu){
                    createRowMenu();
                }
                selectIndex = rowIndex;
                rowMenu.menu('show', {
                    left:e.pageX,
                    top:e.pageY
                });
            }
		});

        var slideMsg = function(str,$pjq){
            $pjq.messager.show({
                title:'操作提示',
                msg:str,
                timeout:1500,
                showType:'slide'
            });
        };

        <%if(isTrue(obj.isCellEdit)){%>
		// 开启编辑模式
    	myGrid.datagrid('enableCellEditing');
    	<%}%>
        
		var rowMenu;
		function createRowMenu(){
			rowMenu = $('<div/>').appendTo('body');
			rowMenu.menu({
				id: 'rowMenu',
				onClick: function(item){
					console.log('click menu'+ item.text);
				}
			});
			rowMenu.menu('appendItem', {
				text: '刷新',
				name: 'reload',
				iconCls: 'pagination-load',
				onclick: function(){
					myGrid.datagrid('reload');
				}
			});
			rowMenu.menu('appendItem', {
				text: '导出所有数据',
				name: 'exportAll',
				iconCls: 'icon-pageexcel',
				onclick: function(){
					window.location.href='/grid/export/${obj.code}';
				}
			});
			<%if(isTrue(obj.isCellEdit)){%>
			
			rowMenu.menu('appendItem', {
				text: '新增行',
				name: 'add',
				iconCls: 'icon-tableadd',
				onclick: function(){
					myGrid.datagrid('insertRow',{
						index: 0,
						row: {}
					});
				}
			});
			rowMenu.menu('appendItem', {
				text: '删除行',
				name: 'delete',
				iconCls: 'icon-tabledelete',
				onclick: function(){
					// var row = myGrid.datagrid('getSelected');
					// index = myGrid.datagrid('getRowIndex', row);
                    console.log('删除行，索引=' +  selectIndex);
					myGrid.datagrid('deleteRow', selectIndex);
				}
			});
			rowMenu.menu('appendItem', {
				text: '保存数据',
				name: 'save',
				iconCls: 'icon-tablesave',
				onclick: function(){
                    var inserted = myGrid.datagrid('getChanges', 'inserted');
                    var deleted = myGrid.datagrid('getChanges', 'deleted');
                    var updated = myGrid.datagrid('getChanges', 'updated');

                    var isOk = true;

                    var json1 = JSON.stringify(inserted);
                    console.log('保存add数据' + json1);
                    $.mypost('/grid/add/${obj.code}',{rows : json1},
                    function(result , status){
                        if (!result.success) {
                            isOk = false;
                        }
                    },false);
                    var json2 = JSON.stringify(deleted);
                    console.log('保存delete数据' + json2);
                    $.mypost('/grid/delete/${obj.code}',{rows : json2},
                    function(result , status){
                        if (!result.success) {
                            isOk = false;
                        }
                    },false);
                    var json3 = JSON.stringify(updated);
                    console.log('保存update数据' + json3);
                    $.mypost('/grid/update/${obj.code}',{rows : json3},
                    function(result , status){
                        if (!result.success) {
                            isOk = false;
                        }
                    },false);

                    if (isOk) {
                        slideMsg("保存成功！", $);
                    } else {
                        $.messager.alert('提示', result.msg, 'error');
                    }

                    myGrid.datagrid('acceptChanges');
                    console.log('标记更改');
				}
			});
			rowMenu.menu('appendItem', {
				text: '回滚数据',
				name: 'reject',
				iconCls: 'icon-undo',
				onclick: function(){
					myGrid.datagrid('rejectChanges');
                    console.log('回滚数据');
				}
			});
			rowMenu.menu('appendItem', {
				text: '其它功能',
				name: 'other',
				onclick: function(){
                    alert('Eova is So Easy');
				}
			});
			<%}%>
		}

		var cmenu;
		function createColumnMenu(){
			cmenu = $('<div/>').appendTo('body');
			cmenu.menu({
				// 点击隐藏显示列
				onClick: function(item){
					if (item.iconCls == 'icon-ok'){
						myGrid.datagrid('hideColumn', item.name);
						cmenu.menu('setIcon', {
							target: item.target,
							iconCls: 'icon-empty'
						});
					} else {
						myGrid.datagrid('showColumn', item.name);
						cmenu.menu('setIcon', {
							target: item.target,
							iconCls: 'icon-ok'
						});
					}
				}
			});
			// 动态加载列作为菜单项目
			var fields = myGrid.datagrid('getColumnFields');
			for(var i=0; i<fields.length; i++){
				var field = fields[i];
				var col = myGrid.datagrid('getColumnOption', field);
				cmenu.menu('appendItem', {
					text: col.title,
					name: field,
					iconCls: 'icon-ok'
				});
			}
		}

	});

</script>