/**
 * 全局弹窗 本函数即将废弃, 请使用$.open.dialog
 */
function dialog(ID, name, url, width, height) {
	$.open.dialog(name, url, width, height, ID);
}

/**
 * Eova 全局统一 新开窗口
 */
(function ($) {

	/**
	 * 全局临时数据缓存
	 */
	$.cache = {
		loadIndex: 0
	}

	/**
	 * 拓展全局静态方法
	 */
	$.extend({
		/**
		 * 全局统一关闭弹窗
		 */
		dialogClose: function (layer, window) {
			layer.close(layer.getFrameIndex(window.name));
		},
		/**
		 * 关闭当前Tab窗口
		 */
		closeTab : function(){
			parent.$("#LAY_app_tabsheader > li.layui-this").find('.layui-tab-close').trigger('click');
		},
		/**
		 * 全局统一打开Loding(只应该存在一个)
		 */
		load: function (msg, time) {
			$.cache.loadIndex = layer.msg(msg, {icon: 16 ,shade: 0.01, time: time || 0});
		},
		/**
		 * 全局统一关闭Loding
		 */
		loadClose: function () {
			layer.close($.cache.loadIndex);
		},
		/**
		 * 全局统一提示
		 */
		msg: function (s) {
			layer.msg(s, {
				zIndex: layer.zIndex, //重点1
				success: function (layero) {
					layer.setTop(layero); //重点2
				}
			});
		},
		/**
		 * 全局统一弹窗
		 */
		alert: function (msg) {
			if (!msg || msg == null || msg == "") {
				msg = "未知错误，请联系管理员";
			}

			msg = msg.toString();

			if (msg.startsWith('info')) {
				msg = msg.replace("info:", "");
				layer.alert(msg, {
					icon: 1
				});
			} else if (msg.startsWith('warn')) {
				msg = msg.replace("warn:", "");
				layer.alert(msg, {
					icon: 0
				});
			} else if (msg.startsWith('error')) {
				msg = msg.replace("error:", "");
				layer.alert(msg, {
					icon: 2
				});
			} else {
				layer.alert(msg, {
					icon: 1
				});
			}
		},
		error: function (msg) {
			layer.alert(msg, {
				icon: 2,
				zIndex: layer.zIndex, //重点1
				success: function (layero) {
					layer.setTop(layero); //重点2
				}
			});
		},
	});

	/**
	 * 新开窗口
	 */
	$.open = {
		/**
		 * 打开一个首页的EovaTab
		 */
		tab: function (url, title) {
			parent.$.admin.openTabsPage(url, title);
		},
		/**
		 * 打开一个纯显示窗口
		 */
		show: function (name, url, width, height) {

			var defWidth = $.sys.autoWidth(width),
				defHeight = $.sys.autoHeight(height);

			var index = layer.open({
				type: 2,
				id: 'open2',
				title: name,
				content: url,
				maxmin: true,
				resize: true,
				area: [defWidth + 'px', defHeight + 'px'],
				btnAlign: 'c',
				shade: 0.3,
				zIndex: layer.zIndex,
				success: function (layero, index) {}
			});
		},
		/**
		 * 打开一个对话框
		 * ID两种用法, 传ID或传回调函数(o)
		 */
		dialog: function (name, url, width, height, ID) {
			// 自动根据URL进行ID命名
			let id = 'eova_dialog';
			try {
				id = url.replace(/\//g, '_');
				let i = id.indexOf('?');
				if (i != -1) {
					id = id.substring(0, i - 1);
				}
			} catch (e) {}

			function bindKey(layero, index, ID) {
				var $layer = layer;

				// 注册键盘事件
				var win = layero.find('iframe').get(0).contentWindow;
				$(win).keyup(function (event) {
					switch (event.which) {
						case 27: {$layer.close(index);}break;// ESC
					}
				}).keypress(function (event) {
					if (event.ctrlKey && (event.which == 10 || event.which == 13)) {
						// Ctrl + 回车
						win.btnSaveCallback(layero, ID, parent.$);
					}
				});
			}

			var defWidth = $.sys.autoWidth(width),
				defHeight = $.sys.autoHeight(height);

			var index = layer.open({
				type: 2, //0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
				id: id,
				title: name,
				content: url,
				maxmin: true,
				resize: true,
				area: [defWidth + 'px', defHeight + 'px'],
				btnAlign: 'c',
				shade: 0.3,
				zIndex: layer.zIndex,
				success: function (layero, index) {
					// layer.setTop(layero);// 窗口置顶
					bindKey(layero, index, ID);
					// 设置焦点
					var $win = layero.find('iframe').get(0).contentWindow;
					$win.focus();

					// 获取弹窗元素 var body = layer.getChildFrame('body', index);
					if (!height) {
						$.open.dialogAuto(index);
						// 强制修正 添加DOM 到 body 出现滚动条
						var $win = $(layero).find('iframe');
						$(layero).height($(layero).height() + 0.5);
						$win.height($win.height() + 0.5);
					}
				},
				btn: ['确定', '取消'], // , '自定义'
				btn1: function (index, layero) {
					$.open.countDown("#layui-layer"+ index +" .layui-layer-btn0", 1000, function () {
						layero.find('iframe')[0].contentWindow.btnSaveCallback(layero, ID, $.autoTopWindow().$);// 自动获取可用弹出层
					});
				},
				btn2: function (index, layero) {
					return true;
				}
				// ,
				// btn3: function (index, layero) {
				// alert('自定义业务逻辑');
				// return false;// 不关闭
				// }
			});
		},
		/**
		 * 弹出选择框
		 * @param name 窗口名称
		 * @param width 宽度
		 * @param height 高度
		 * @param exp 选择内容EOVA表达式
		 * @param multiple 是否多选
		 * @param callbak 回调函数
		 */
		select: function (name, width, height, exp, multiple, callbak){

			var url = $.str.format('/widget/find?mod=2&exp={0}&multiple={1}', exp, multiple);

			// $.open.dialog(name, url, width, height, callbak);

			// var index = layer.zIndex;
			// if (!index) {
			// 	// 顶层窗口打开时默认索引
			// 	index = 20000000;
			// }

			var defWidth = $.sys.autoWidth(width),
				defHeight = $.sys.autoHeight(height);

			layer.open({
				type: 2,
				id: 'eova_select_dialog',
				title: name,
				content: url,
				maxmin: true,
				area: [defWidth + 'px', defHeight + 'px'],
				btnAlign: 'c',
				shade: 0.3,
				zIndex: layer.zIndex,
				btn: [$.I18N('确定')],
				btn1: function (index, layero) {
					layero.find('iframe')[0].contentWindow.select(layero, callbak);
					window.focus();
					layer.setTop(layero);
				}
			});

		},
		// buildWidth: function (width) {
		// 	// 默认大小(用于初始占位)
		// 	let num = width ? width : 720;
		// 	let max = $(window).width();
		// 	if (width && width > 0 && width <= 1) {
		// 		// 支持百分比0-1=0%-100%
		// 		num = width * max;
		// 		if($.sys.isMobile()) return max;// 移动端, 强制最大化
		// 	}
		// 	// 移动端 显示溢出 最大化
		// 	if ($.sys.isMobile() && num > max){
		// 		num = max;
		// 	}
		// 	return num;
		// },
		// buildHeight: function (height) {
		// 	// 默认大小(用于初始占位)
		// 	let num = height ? height : 500;
		// 	let max = $(window).height();
		// 	if (height && height > 0 && height <= 1) {
		// 		num = height * max;
		// 		if($.sys.isMobile()) return max;// 移动端, 强制最大化
		// 	}
		// 	// 移动端 显示溢出 显示最大化
		// 	if ($.sys.isMobile() && num > max){
		// 		num = max;
		// 	}
		// 	return num;
		// },
		// 弹窗高度自适应
		dialogAuto: function (index) {
			var doms = ['layui-layer', '.layui-layer-title', '.layui-layer-main', '.layui-layer-dialog', 'layui-layer-iframe', 'layui-layer-content', 'layui-layer-btn', 'layui-layer-close'];
			if (!index) return;
			var heg = layer.getChildFrame('html', index).outerHeight();
			var layero = $('#' + doms[0] + index);
			var titHeight = layero.find(doms[1]).outerHeight() || 0;
			var btnHeight = layero.find('.' + doms[6]).outerHeight() || 0;

			var totalHeight = heg + titHeight + btnHeight;

			// 弹窗不超过父页面最大高度
			var winHeight = $(window).height();
			// console.log(heg + " - " + winHeight);
			if (totalHeight > winHeight) {
				// 计算最大显示范围
				heg = winHeight - (titHeight + btnHeight) - 10; // 再留个10的缝隙
			}
			// 居中定位
			layero.css({
				top: (winHeight - (heg + titHeight + btnHeight)) / 2
			});
			layero.css({
				height: heg + titHeight + btnHeight
			});
			layero.find('iframe').css({
				height: heg
			});
		},
		// 选择器,倒数毫秒,可用回调
		countDown: function (selector, millis, callback) {
			var $btn = $(selector);
			if (!$btn.hasClass('layui-btn-disabled')) {
				$btn.addClass('layui-btn-disabled');
				var i = 3;

				function count() {
					i--;
					if (i == 0) {
						$btn.removeClass('layui-btn-disabled');
						return;
					}
					setTimeout(function () {
						count()
					}, millis)
				}
				count();

				callback.call();
			}
		}
	}

})(jQuery);