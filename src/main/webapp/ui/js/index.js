(function($) {
	var zt = $.fn.zTree,
		consts = zt.consts,
		view = zt._z.view;

	// 重写ICON处理
	view.makeNodeIcoClass = function(setting, node) {
//		console.info('diy eova style icon');
		var icon = node.iconskip;
		if (!icon) {
			icon = 'eova-icon4';
		}
		var icoCss = [icon];
		if (!node.isAjaxing) {
			if (node.isParent) {
				icoCss.push("tree-folder");
				// icoCss.push(node.open ? consts.folder.OPEN : consts.folder.CLOSE);
			} else {
				icoCss.push("tree-file");
			}
		}
		return "tree-icon " + icoCss.join(' ');
	};

	// 重写节点ICOClass切换 不需要
	view.replaceIcoClass = function(node, obj, newName) {
		// onsole.info('eova no replace ico class');
	};
})(jQuery);

var curMenu = null,
	zTree_Menu = null;

function beforeClick(treeId, node) {
	if (node.isParent) {
		if (node.level === 0) {
			var pNode = curMenu;
			while (pNode && pNode.level !== 0) {
				pNode = pNode.getParentNode();
			}
			if (pNode !== node) {
				var a = $("#" + pNode.tId + "_a");
				a.removeClass("cur");
				zTree_Menu.expandNode(pNode, false);
			}
			a = $("#" + node.tId + "_a");
			a.addClass("cur");
			var isOpen = false;
			for (var i = 0, l = node.children.length; i < l; i++) {
				if (node.children[i].open) {
					isOpen = true;
					break;
				}
			}
			if (isOpen) {
				zTree_Menu.expandNode(node, true);
				curMenu = node;
			} else {
				zTree_Menu.expandNode(node.children[0].isParent ? node.children[0] : node, true);
				curMenu = node.children[0];
			}
		} else {
			zTree_Menu.expandNode(node);
		}
	}
	return !node.isParent;
}
// Eova Tabs
var mainTabs;
var getOpts = function(title, url, icon) {
	var opts = {
		title : title,
		closable : true,
		iconCls : icon,
		content : sy.formatString('<iframe src="{0}" allowTransparency="true" style="border:0;width:100%;height:99.3%;" frameBorder="0"></iframe>', url),
		border : false,
		fit : true
	};
	return opts;
};
var addTab = function(title, url, icon){
	mainTabs.tabs('add', getOpts(title, url, icon));
};

$(document).ready(function() {

	// 选项卡
	mainTabs = $('#mainTabs');

	// 选项卡菜单
	var tabsMenu = $('#tabsMenu').menu({
		onClick: function(item) {
			var curTabTitle = $(this).data('tabTitle');
			var type = $(item.target).attr('type');
			if (type === 'close') {
				var t = mainTabs.tabs('getTab', curTabTitle);
				if (t.panel('options').closable) {
					mainTabs.tabs('close', curTabTitle);
				}
				return;
			}
			var allTabs = mainTabs.tabs('tabs');
			var closeTabsTitle = [];
			$.each(allTabs, function() {
				var opt = $(this).panel('options');
				if (opt.closable && opt.title != curTabTitle && type === 'closeOther') {
					closeTabsTitle.push(opt.title);
				} else if (opt.closable && type === 'closeAll') {
					closeTabsTitle.push(opt.title);
				}
			});
			for (var i = 0; i < closeTabsTitle.length; i++) {
				mainTabs.tabs('close', closeTabsTitle[i]);
			}
		}
	});
	// 初始化选项卡
	mainTabs.tabs({
		fit: true,
		border: false,
		tools: [{
			text: '最大化',
			iconCls: 'eova-icon73',
			handler: function() {
				$('#mainLayout').layout("collapse", "north").layout("collapse", "west");
			}
		}, {
			text: '刷新',
			iconCls: 'eova-icon79',
			handler: function() {
				var panel = mainTabs.tabs('getSelected').panel('panel');
				var frame = panel.find('iframe');
				try {
					if (frame.length > 0) {
						for (var i = 0; i < frame.length; i++) {
							frame[i].contentWindow.document.write('');
							frame[i].contentWindow.close();
							frame[i].src = frame[i].src;
						}
						if (navigator.userAgent.indexOf("MSIE") > 0) { // IE特有回收内存方法
							try {
								CollectGarbage();
							} catch (e) {}
						}
					}
				} catch (e) {}
			}
		}],
		onContextMenu: function(e, title) {
			e.preventDefault();
			tabsMenu.menu('show', {
				left: e.pageX,
				top: e.pageY
			}).data('tabTitle', title);
		}
	});
	// 初始化添加默认主页
	var mainOpts = {
		fit: true,
		title: 'EOVA V1.6 Alpha12',
		closable: false,
		border: false,
		iconCls: 'eova-icon475',
		content: '<iframe id="mainFrame" name="mainFrame" src="http://www.eova.cn:18080/main/main.html" allowTransparency="true" style="border: 0; width: 100%; height: 99%;" frameBorder="0"></iframe>'
	};
	mainTabs.tabs('add', mainOpts);

	// Tree Node OnClick
	var nodeClick = function(e, treeId, node) {
		if (!node.isParent) {
			if (node.type == 'dir') {
				alert('目录下必须有子菜单！');
				return;
			}
			var opts = getOpts(node.name, node.link, node.iconskip);
			if (mainTabs.tabs('exists', opts.title)) {
				mainTabs.tabs('select', opts.title);
			} else {
				mainTabs.tabs('add', opts);
			}
		}
	};
	
	// 根据URL获取JSON
	var data;
	$.syncGetJson("/menu", function(json) {
		data = json;
	});

	var setting = {
		view: {
			showLine: true,
			selectedMulti: false,
			dblClickExpand: false
		},
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "parent_id",
				rootPId: 0
			}
		},
		callback: {
			//			onNodeCreated: onNodeCreated,
			beforeClick: beforeClick,
			onClick: nodeClick
		}
	};

	$.fn.zTree.init($("#menu"), setting, data);
	zTree_Menu = $.fn.zTree.getZTreeObj("menu");
	if(zTree_Menu){
		if(zTree_Menu.getNodes().length < 1){
			alert("当前角色没有权限查看任何菜单");
			return;
		}
		// 默认选中节点
		curMenu = zTree_Menu.getNodes()[0].children[0];
		zTree_Menu.selectNode(curMenu);
		zTree_Menu.selectNode(curMenu);
		// 选中当前大类
		var a = $("#" + zTree_Menu.getNodes()[0].tId + "_a");
		a.addClass("cur");
	}
});