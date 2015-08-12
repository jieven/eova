<table id="${id}" width="auto" height="auto"></table>
<script>
$(function () {

    // init param
    var objectCode = '${objectCode!}';// medaobject code
    var toolbar = '${toolbar!}';// grid ref toolbar
    var isFirstLoad = eval('${isFirstLoad!true}');// first is load data,default=true
    var isPaging = eval('${isPaging!true}');// is show pagination
    var url = '${url!}';// diy grid load data url
    var objectJson = '${objectJson!}';// object is json
    var fieldsJson = '${fieldsJson!}';// fiedlds is json

    if (url == '') {
        url = '/grid/query/' + objectCode;
    }

    // console.log(objectCode + 'isFirstLoad' + isFirstLoad);

    var object, fields;

    if(objectJson != '') {
        object = JSON.parse(objectJson);
    } else {
        $.syncGetJson('/meta/object/' + objectCode, function (json) {
            object = json;
        });
    }
    if(fieldsJson != '') {
        fields = JSON.parse(fieldsJson);
    } else  {
        $.syncGetJson('/meta/fields/' + objectCode, function (json) {
            fields = json;
        });
    }
    console.log(object);
    console.log(fields);


    var cols = [];
    // 批量选择框
    if (!object.is_single) {
        var attr = new Object;
        attr.field = 'ck';
        attr.checkbox = true;
        cols.push(attr);
    }
    // 字段属性
    $.each(fields, function (i, f) {
        if (!f.is_show) {
            // continue;
            return true;
        }

        var attr = new Object;
        attr.field = f.en;
        attr.title = f.cn;
        attr.width = f.width ? f.width : 150;

        if (f.is_order) {
            attr.sortable = true;
        }

        if (f.formatter != null) {
            attr.formatter = new Function('return ' + f.formatter)();
        } else {
            // 默认格式化处理
            if (f.type == '复选框') {
                attr.align = 'center';
                attr.formatter = function (value, row, index, field) {
                    var ck = '<span class="ck0">□</span>';
                    if (value) {
                        ck = '<span class="ck1">☑</span>';
                    }
                    return ck;
                };
            }
            if (f.type == '编辑框' || f.type == '文本域') {
                attr.formatter = function (value, row, index, field) {
                    if (value && value.length > 10) {
                    	//alert($.encodeHtml(value));
                        return '<span title="' + $.encodeHtml(value) + '">' + $.encodeHtml(value) + '</span>'
                    }
                    return value;
                }
            }
        }
        // Grid Cell Editor,对象和字段允许行内编辑自增，自增长禁止编辑
        if (object.is_celledit && f.is_edit && !object.is_auto) {
            var editor = new Object;
            editor.type = f.editor;
            if (f.type == '下拉框') {
                editor.options = {
                    url: '/widget/comboJson/' + objectCode + '-' + f.en, valueField: 'id', textField: 'cn', multiple: f.is_multiple
                }
            } else if (f.type == '查找框') {
                editor.options = {
                    url: '/widget/find?code=' + objectCode + '&field=' + f.en
                }

            }
            attr.editor = editor;
        }

        cols.push(attr);
    });

    console.log(cols);

	// 默认排序
	var sortName = null,sortOrder = 'asc';
	if(object.default_order){
		var defaultOrder = object.default_order.split(' ');
		sortName = defaultOrder[0];
		if(defaultOrder.length > 1){
			sortOrder = defaultOrder[1];
		}
	}

    var selectIndex;
    var gridId = "#${id}";
    var myGrid = $(gridId).datagrid({
        fit: true,
        border: false,
        striped: true,
        align: 'right',
        collapsible: false,
        remoteSort: true,
        multiSort: false,
        rownumbers: true,

        toolbar: toolbar ? '#' + toolbar : null,
        pagination: isPaging,
        pageSize: 15,
        pageList: [15, 30, 50, 100, 200, 500, 1000, 2000],

        singleSelect: object.is_single,
        idField: object.pk_name,
        sortName: sortName,
        sortOrder: sortOrder,

        url: url,
        method: 'post',
        columns: [cols],

        onHeaderContextMenu: function (e, field) {
            e.preventDefault();
            if (!cmenu) {
                createColumnMenu();
            }
            cmenu.menu('show', {
                left: e.pageX,
                top: e.pageY
            });
        },
        onBeforeLoad: function () {
            // 阻止初始化加载数据
            if (!isFirstLoad) {
                isFirstLoad = true;
                return false;
            }
        },
        onLoadSuccess: function (data) {
            if (object.is_celledit && data.total < 1) {
                // 暂时禁用，初始化不加载空行，使用Grid 分页栏按钮添加！
                myGrid.datagrid('insertRow', {index: 0, row: {}});
            }
        },
        onRowContextMenu: function (e, rowIndex, rowData) {
            e.preventDefault();
            if (!rowMenu) {
                createRowMenu();
            }
            selectIndex = rowIndex;
            rowMenu.menu('show', {
                left: e.pageX,
                top: e.pageY
            });
        }
    });

    // 开启编辑模式
    if (object.is_celledit) {
        myGrid.datagrid('enableCellEditing');
    }

    var rowMenu;
    function createRowMenu() {
        rowMenu = $('<div/>').appendTo('body');
        rowMenu.menu({
            id: 'rowMenu',
            onClick: function (item) {
                console.log('click menu' + item.text);
            }
        });
        rowMenu.menu('appendItem', {
            text: '刷新',
            name: 'reload',
            iconCls: 'pagination-load',
            onclick: function () {
                myGrid.datagrid('reload');
            }
        });
        rowMenu.menu('appendItem', {
            text: '导出所有数据',
            name: 'exportAll',
            iconCls: 'icon-pageexcel',
            onclick: function () {
                window.location.href = '/grid/export/' + objectCode;
            }
        });
        if (object.is_celledit) {
            rowMenu.menu('appendItem', {
                text: '新增行',
                name: 'add',
                iconCls: 'icon-tableadd',
                onclick: function () {
                    myGrid.datagrid('insertRow', {
                        index: 0,
                        row: {}
                    });
                }
            });
            rowMenu.menu('appendItem', {
                text: '删除行',
                name: 'delete',
                iconCls: 'icon-tabledelete',
                onclick: function () {
                	console.log('删除行，索引=' +  selectIndex);
					myGrid.datagrid('deleteRow', selectIndex);
                }
            });
            rowMenu.menu('appendItem', {
                text: '保存数据',
                name: 'save',
                iconCls: 'icon-tablesave',
                onclick: function () {

                    var inserted = myGrid.datagrid('getChanges', 'inserted');
                    var deleted = myGrid.datagrid('getChanges', 'deleted');
                    var updated = myGrid.datagrid('getChanges', 'updated');

                    var isOk = true;
                    var errorMsg = '';
                    if (inserted.length > 0) {
                        var json1 = JSON.stringify(inserted);
                        console.log('保存add数据' + json1);
                        $.syncPost('/grid/add/' + objectCode, {rows: json1},
                                function (result, status) {
                                    if (!result.success) {
                                        isOk = false;
                                        errorMsg += result.msg + '<br>';
                                    }
                                });
                    }
                    if (updated.length > 0) {
                        var json3 = JSON.stringify(updated);
                        console.log('保存update数据' + json3);
                        $.syncPost('/grid/update/' + objectCode, {rows: json3},
                                function (result, status) {
                                    if (!result.success) {
                                        isOk = false;
                                        errorMsg += result.msg + '<br>';
                                    }
                                });
                    }
                    if (deleted.length > 0) {
                        var json2 = JSON.stringify(deleted);
                        console.log('保存delete数据' + json2);
                        $.syncPost('/grid/delete/' + objectCode, {rows: json2},
                                function (result, status) {
                                    if (!result.success) {
                                        isOk = false;
                                        errorMsg += result.msg + '<br>';
                                    }
                                });
                    }

                    if (isOk) {
                        $.slideMsg("保存成功！");
                        // 确认改动
                        myGrid.datagrid('acceptChanges');
                        console.log('标记更改');
                    } else {
                        $.messager.alert('提示', errorMsg, 'error');
                    }
                }
            });
//            rowMenu.menu('appendItem', {
//                text: '回滚数据',
//                name: 'reject',
//                iconCls: 'icon-undo',
//                onclick: function () {
//                    //myGrid.datagrid('rejectChanges');
//                    console.log('回滚数据');
//                }
//            });
            rowMenu.menu('appendItem', {
                text: '其它功能',
                name: 'other',
                onclick: function () {
                    alert('Eova is So Easy');
                }
            });
        }
    }

    var cmenu;
    function createColumnMenu() {
        cmenu = $('<div/>').appendTo('body');
        cmenu.menu({
            // 点击隐藏显示列
            onClick: function (item) {
                if (item.iconCls == 'icon-ok') {
                    myGrid.datagrid('hideColumn', item.name);
                    cmenu.menu('setIcon', {
                        target: item.target,
                        iconCls: 'icon-empty'
                    });
                } else {
                    if (item.iconCls == 'icon-tableedit') {
                        return;
                    }
                    myGrid.datagrid('showColumn', item.name);
                    cmenu.menu('setIcon', {
                        target: item.target,
                        iconCls: 'icon-ok'
                    });
                }
            }
        });
        cmenu.menu('appendItem', {
            text: '编辑元字段',
            name: 'editmeta',
            iconCls: 'icon-tableedit',
            onclick: function () {
                //loadDialog(myGrid, '编辑元字段', '/meta/edit/' + object.code);
                window.open('/meta/edit/' + object.code);
            }
        });
        // 动态加载列作为菜单项目
        var fields = myGrid.datagrid('getColumnFields');
        for (var i = 0; i < fields.length; i++) {
            var field = fields[i];
            var col = myGrid.datagrid('getColumnOption', field);
            cmenu.menu('appendItem', {
                text: col.title,
                name: field,
                iconCls: 'icon-ok'
            });
        }
    }

//    var pager = myGrid.datagrid('getPager');
//    pager.pagination({
//        buttons: [
//            {
//                iconCls: 'icon-tableadd',
//                handler: function () {
//                    myGrid.datagrid('insertRow', {
//                        index: 0,
//                        row: {}
//                    });
//                }
//            },
//            {
//                iconCls: 'icon-tabledelete',
//                handler: function () {
//                    alert('tabledelete');
//                }
//            },
//            {
//                iconCls: 'icon-tablesave',
//                handler: function () {
//                    alert('save');
//                }
//            }
//        ]
//    });

});
</script>