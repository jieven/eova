<table id="${id}" width="auto" height="auto"></table>
<script>
// var ${'$'+id};
$(function () {

    // init param
    var id = "${id!}";
    var masterId = "${masterId!}";
    var $grid = $("#" + id);
    var $masterGrid;
    if(masterId != ""){
    	$masterGrid = $("#" + masterId);
    }

    var menuCode = '${menuCode!}';
    var objectCode = '${objectCode!}';
    var toolbar = '${toolbar!}';// grid ref toolbar
    var isPaging = eval('${isPaging!true}');// is show pagination
    var url = '${url!}';// diy grid load data url
    var objectJson = '${objectJson!}';// object is json
    var fieldsJson = '${fieldsJson!}';// fiedlds is json
    var configJson = '${configJson!}';// config is json

    if (url == '') {
        url = '/grid/query/' + objectCode;
        if(menuCode != ''){
        	url = url + '-' + menuCode;
        }
    }
    var paras = $.getUrlParas();
    // 自动传递所有参数
    // 是否含有关联查询条件
    if(paras && (paras.indexOf('query_') != -1 || paras.indexOf('filter_') != -1)){
    	url = url + '?' + paras;
    }

    // console.log(objectCode + 'isFirstLoad' + isFirstLoad);

	// 初始化组件
	EovaWidget.init(objectCode, objectJson, fieldsJson, configJson);
    var config = EovaWidget.data.config,
    	object = EovaWidget.data.object,
    	fields = EovaWidget.data.fields;

    // 当前对象是否允许初始加载数据
    var isFirstLoad = false;
    var isFirstLoadNow = eval('${isFirstLoad!true}');
	// 必须当前业务和对象都允许加载数据
    if(isFirstLoadNow && object.is_first_load){
    	isFirstLoad = true;
    }

    var cols = [];
    var validators = {};
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
            attr.formatter = new Function('return ' + f.formatter + ';')();
        } else {
            // 默认格式化处理
            if (f.type == '布尔框') {
                attr.align = 'center';
                attr.formatter = function (value, row, index, field) {
                    var ck = '<span class="ck0">□</span>';
                    if (value) {
                        ck = '<span class="ck1">☑</span>';
                    }
                    return ck;
                };
            }
            if (f.type == '文本框' || f.type == '编辑框' || f.type == '文本域') {
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
        if (object.is_celledit && f.is_edit) {
        	if(object.is_auto && object.is_auto == true){
        		return;
        	}
            var editor = new Object;
            editor.type = f.editor;
            // 构造参数
            editor.options = {};
            if (f.type == '下拉框') {
                editor.options = {
                    url: '/widget/comboJson/' + objectCode + '-' + f.en, valueField: 'id', textField: 'cn', multiple: f.is_multiple
                }
            } else if (f.type == '查找框') {
                editor.options = {
                    url: '/widget/find?code=' + objectCode + '&field=' + f.en + '&multiple=' + f.is_multiple
                }
            } else if (f.type == '日期框') {
                editor.options = {
            		format: 'yyyy-MM-dd'
                }
            }
            editor.options['name'] = f.en;

            attr.editor = editor;

         	// validator
            var rule = '';
            if(f.is_required){
                rule = f.cn + ':required;';
            }
            if(f.validator){
                rule = rule + f.validator;
            }
            if(rule != ''){
    	        validators[f.en] = { rule: rule };
            }
        }

        cols.push(attr);
    });

//  console.log(cols);

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
    var $myGrid = $grid.datagrid({
        fit: true,
        border: false,
        striped: true,
        align: 'right',
        autoRowHeight: true,
        collapsible: false,
        remoteSort: true,
        multiSort: false,
        rownumbers: object.is_show_num,
        showFooter: true,

        ctrlSelect: true,
        singleSelect: object.is_single,

        toolbar: toolbar ? '#' + toolbar : null,
        pagination: isPaging,
        pageSize: 15,
        pageList: [15, 30, 50, 100, 200, 500, 1000, 2000],

        idField: object.pk_name,
        sortName: sortName,
        sortOrder: sortOrder,

        url: url,
        method: 'post',
        columns: [cols],
        // frozenColumns: [[{field:'diy_fun',title:'操作'}]],

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
                $myGrid.datagrid('insertRow', {index: 0, row: {}});
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

//     ${'$'+id} = $myGrid;

    // 开启编辑模式
    if (object.is_celledit) {
        $myGrid.datagrid('enableCellEditing');
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
                $myGrid.datagrid('reload');
            }
        });
        rowMenu.menu('appendItem', {
            text: '导出当前查询数据',
            name: 'exportAll',
            iconCls: 'eova-icon779',
            onclick: function () {
            	var exportUrl = '/grid/export/' + objectCode;
    	        if(menuCode != ''){
    	        	exportUrl = exportUrl + '-' + menuCode;
    	        }
    	        var queryParas = $.getFormParasStr($('#queryForm'));
    	        if (queryParas) {
	    	        exportUrl = exportUrl + '?' + queryParas;
				}
                window.location.href = exportUrl;
            }
        });
//         rowMenu.menu('appendItem', {
//             text: '导出本页数据',
//             name: 'exportAll',
//             iconCls: 'eova-icon779',
//             onclick: function () {
//             	// 导出Xls
//                 $.gridToExcel($myGrid, objectCode);
//             }
//         });
        if (object.is_celledit) {
        	var rowData = {};
        	if($masterGrid){
	        	// 获取主表选中行
	        	var gridSelectRow = $masterGrid.datagrid('getSelected');
        		if(gridSelectRow){
        			// 初始添加关键字段
                    var val = gridSelectRow[config.objectField];
        			rowData[config.fields[0]] = val;
        			rowData[config.fields[0]+'_val'] = val;
        		}
        	}

            rowMenu.menu('appendItem', {
                text: '保存数据',
                name: 'save',
                iconCls: 'eova-icon1089',
                onclick: function () {

                    var inserted = $myGrid.datagrid('getChanges', 'inserted');
                    var deleted = $myGrid.datagrid('getChanges', 'deleted');
                    var updated = $myGrid.datagrid('getChanges', 'updated');

                    var isOk = true;
                    var errorMsg = '';
                    if (inserted.length > 0) {
                        var json1 = JSON.stringify(inserted);
                        // console.log('保存add数据' + json1);
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
                        // console.log('保存update数据' + json3);
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
                        // console.log('保存delete数据' + json2);
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
                        $myGrid.datagrid('acceptChanges');
                        console.log('标记更改');
                    } else {
                        $.alert($, errorMsg);
                    }
                }
            });
            rowMenu.menu('appendItem', {
                text: '删除行',
                name: 'delete',
                iconCls: 'eova-icon1050',
                onclick: function () {
                	console.log('删除行，索引=' +  selectIndex);
					$myGrid.datagrid('deleteRow', selectIndex);
                }
            });
            rowMenu.menu('appendItem', {
                text: '新增行',
                name: 'add',
                iconCls: 'eova-icon1044',
                onclick: function () {
                    $myGrid.datagrid('insertRow', {
                        index: 0,
                        row: rowData
                    });
                }
            });
//            rowMenu.menu('appendItem', {
//                text: '回滚数据',
//                name: 'reject',
//                iconCls: 'eova-icon4',
//                onclick: function () {
//                    //$myGrid.datagrid('rejectChanges');
//                    console.log('回滚数据');
//                }
//            });
//             rowMenu.menu('appendItem', {
//                 text: '其它功能',
//                 name: 'other',
//                 onclick: function () {
//                     alert('Eova is So Easy');
//                 }
//             });
        }
    }

    var cmenu;
    function createColumnMenu() {
        cmenu = $('<div/>').appendTo('body');
        // 初始化菜单
        cmenu.menu();
        <%// 仅超级管理员可见%>
        <%if(session.user.isAdmin){%>
        cmenu.menu('appendItem', {
            text: '编辑元字段',
            name: 'editmeta',
            iconCls: 'eova-icon1051',
            onclick: function () {
                window.open('/meta/edit/' + objectCode);
            }
        });
        cmenu.menu('appendItem', {
            text: '编辑元对象',
            name: 'editmeta',
            iconCls: 'eova-icon1051',
            onclick: function () {
                loadDialog($myGrid, '修改元对象', '/form/update/eova_object_code-' + object.id);
            }
        });
        <%}%>
        // 动态加载列作为菜单项目
        cmenu.menu('appendItem', {
			text: 'other',
			name: 'other',
			iconCls: ''
        });
    }

    if (object.is_celledit) {
    	// validator init
        var $form = $('#${id}').parent();// get datagrid-view dom is validata zone
        $form.validator({
            debug: false,
            stopOnError: true,
            focusInvalid : false,
            showOk: false,
            timely: 0,
            msgMaker: false,
            fields: validators
        });
        $form.on("validation", $.validation);
    }
//    var pager = $myGrid.datagrid('getPager');
//    pager.pagination({
//        buttons: [
//            {
//                iconCls: 'eova-icon4',
//                handler: function () {
//                    $myGrid.datagrid('insertRow', {
//                        index: 0,
//                        row: {}
//                    });
//                }
//            },
//            {
//                iconCls: 'eova-icon4',
//                handler: function () {
//                    alert('tabledelete');
//                }
//            },
//            {
//                iconCls: 'eova-icon4',
//                handler: function () {
//                    alert('save');
//                }
//            }
//        ]
//    });

});

</script>