//var wx = 0;
//var wy = 0;

$(document).ready(function(){
//	console.log("document ready ...");

	// layout north 面板收缩时添加标题
	$(".layout-expand-north .panel-header .panel-title").html("快速查询");

	$('body').layout('panel','north').panel({
		onCollapse:function(){
			console.log('onCollapse redraw');
			// layout north 面板收缩时添加标题
			$(".layout-expand-north .panel-header .panel-title").html("快速查询");
		}
//		,onExpand:function(){
//			console.log('onExpand redraw');
//		},
//		onResize:function(width,height){
//			console.log('onResize redraw');
//		}
	});

	// 记录窗口初始大小
	wx = $(window).width();
	wy = $(window).height();

	// 监听 浏览器窗口 变动 事件,对子layout进行重绘
	$(window).resize(function(){
//		console.log('window resize');
		//winredraw();
	});
});


//窗口重绘方法
var winredraw = function(){
	// 获取当前窗口的高度
	var x = $(window).width();
	var y = $(window).height();

//	console.log('window resize ok, x-wx=[' + x + ',' + wx + '],y-wy=[' + y + ',' + wy +']');
	// 当窗口大小变动时，重绘子layout
	$('#gridLayout').layout('resize');
//	console.log('gridLayout resize ok');

	// 储存当前窗口高度
	wx = x;
	wy = y;
};

// 有且只有一行数据被选中
var isSelected = function($widget) {
	var type = $.getWidgetType($widget);

	var num = 0;
	if (type == "datagrid") {
		num = $widget.datagrid('getSelections').length;
	} else if (type == "treegrid") {
		num = $widget.treegrid('getSelections').length;
	}

	if (num == 0) {
		$.slideMsg("请选择一行数据！");
		return;
	}
	if (num > 1) {
		$.slideMsg("请勿选择多行数据！");
		return;
	}

	return true;
};

// 弹出Dialog
var loadDialog = function($widget, name, url, width, height) {
	if(!width){
		width = 945;
	}
	if(!height){
		height = $(window).height() * 0.9;
	}
	// 自动根据URL进行ID命名
	var id = url.replace(/\//g, '_');
	var i = id.indexOf('?');
	if(i != -1){
		id = id.substring(0, i - 1);
	}
	// 弹出窗口
 	var dialog = parent.sy.modalDialog({
 		id : id,
 		title : name,
 		url : url,
 		buttons : [ {
 			text : '&nbsp;&nbsp;保&nbsp;存&nbsp;&nbsp;',
 			handler : function() {
 				dialog.find('iframe').get(0).contentWindow.btnSaveCallback(dialog, $widget, parent.$);
 			}
 		},
  		{
 			text : '&nbsp;&nbsp;取&nbsp;消&nbsp;&nbsp;',
 			handler : function() {
 				dialog.dialog('close');
 			}
 		}  ]
 	}, width, height);

 	// 注册键盘事件
 	var win = dialog.find('iframe').get(0).contentWindow;
	$(win).keyup(function(event) {
		switch (event.keyCode) {
			case 27: {
				// ESC
				dialog.dialog('close');
			}
			break;
		}
	}).keypress(function(event){
		if(event.ctrlKey && (event.keyCode == 10 || event.keyCode == 13)){
			// Ctrl + 回车
			win.btnSaveCallback(dialog, $widget, parent.$);
		}
	});
};

// 确认删除
var confirmDel = function($widget, objectCode, rows){
	$.messager.confirm('请确认', '您要删除当前所选数据？', function(o) {
		if (o) {
			var json2 = JSON.stringify(rows);
			$.syncPost('/grid/delete/' + objectCode, {
				rows : json2
			}, function(result, status) {
				if (result.success) {
					$.slideMsg("删除成功！");
					$.widgetReLoad($widget);
				} else {
					$.alert($, result.msg);
				}
			});
			$.widgetClearSelections($widget);
		}
	});
};

// 确认隐藏
var confirmHide = function($widget, objectCode, rows){
	$.messager.confirm('请确认', '您要隐藏当前所选数据？', function(o) {
		if (o) {
			var json2 = JSON.stringify(rows);
			$.syncPost('/grid/hide/' + objectCode, {
				rows : json2
			}, function(result, status) {
				if (result.success) {
					$.slideMsg("隐藏成功！");
					$.widgetReLoad($widget);
				} else {
					$.alert($, result.msg);
				}
			});
			$.widgetClearSelections($widget);
		}
	});
};

