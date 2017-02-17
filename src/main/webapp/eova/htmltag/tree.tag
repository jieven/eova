<script type="text/javascript">
	(function($) {
		// 获取几个主要的全局对象
		var zt = $.fn.zTree,
			//  tools = zt._z.tools,
			//  data = zt._z.data,
			//  event = zt._z.event,
			consts = zt.consts,
			view = zt._z.view;
		// 重写ICON处理
		view.makeNodeIcoClass = function(setting, node) {
			console.info('diy eova style icon');
			
			var iconKey = "iconskip";
			<%if(!isEmpty(iconKey)){%>iconKey = "${iconKey}";<%}%>
			var icon = node[iconKey];
			if (!icon) {
				icon = 'eova-icon234';
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
			console.info('eova no replace ico class');
		}
	})(jQuery);
	$(function() {
		// 初始化参数
		var $tree = $("#${id}");
		var masterId = "${masterId!}";
		var $master;
		if (masterId != "") {
			$master = $("#" + masterId);
		}
		var menuCode = '${menuCode!}';
		var objectCode = '${objectCode!}';
		var objectJson = '${objectJson!}'; // object is json
		var fieldsJson = '${fieldsJson!}'; // fiedlds is json
		var configJson = '${configJson!}'; // config is json

		var url = "${url!}";
		var idKey = "${idKey!'id'}";
		var nameKey = "${nameKey!'name'}";
		var pidKey = "${pidKey!'parent_id'}";
		var isExpand = "${isExpand!false}";
		var rootPid = "${rootPid!0}";

		// 初始化组件
		EovaWidget.init(objectCode, objectJson, fieldsJson, configJson);
		var config = EovaWidget.data.config,
			object = EovaWidget.data.object,
			fields = EovaWidget.data.fields;
		// 初始化URL
		if (url == '') {
			url = '/tree/query/' + objectCode;
			if(menuCode != ''){
	        	url = url + '-' + menuCode;
	        }
		}
		// 根据URL获取JSON
		var data;
		$.syncGetJson(url, function(json) {
			data = json;
		});
		var curMenu = null,
			zTree_Menu = null;
		var setting = {
			data: {
				simpleData: {
					enable: true,
					idKey: idKey,
					pIdKey: pidKey,
					rootPId: rootPid
				}
			}
		};
		$.fn.zTree.init($tree, setting, data);

		<%if(!isEmpty(expandAll!)){%>$.fn.zTree.getZTreeObj("${id}").expandAll(${expandAll});<%}%>
	});
</script>
<div class="zTreeDemoBackground left">
	<ul id="${id!}" class="ztree"></ul>
</div>