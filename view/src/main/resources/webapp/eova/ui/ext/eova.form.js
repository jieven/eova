/**
 * 提交表单给服务器处理
 * 
 * @param {*} $$ 当前窗口上层JQuery.$
 * @param {*} ID 组件 ID
 * @param {*} $form 当前提交表单
 * @param {*} action 提交后服务端处理URL
 * @param {*} msg 处理成功提示信息
 * @param {*} data 自定义业务数据
 */
function submitForm($$, ID, $form, action, msg, data) {
	if ($form.size() < 1) console.log("表单内无元素, 请检查表单选择器:"+ $form.selector);
	// nice validator 校验通过就提交表单
	$form.isValid(function(isValied) {
		if (isValied) {
			var o = $.getFormParasObj($form);
			
			if(data){
				// 特殊命名防止与正常业务重名 兼容 或传 集合数据
				let s = $.json.toStr(data);
				o['eova_data'] = s;
				// 为了方便获取, 如果是Object并且长度小于1000(如果对象嵌套集合也会导致参数项过长异常), 直接合并参数.
				if (s.length < 800 && Object.prototype.toString.call(data) === '[object Object]'){
					o = $.extend(o, data);
				}
			}
			
			$.ajax({
				async: false,
				type: 'POST',
				url: action,
				data: o,
				dataType: "json",
				// error: function(xhr, textStatus, errorThrown){
				// 	$$.error("500:服务端异常,请查看日志,排查异常");
				// },
				success: function (result) {
					if (result.success || result.state == 'ok') {
						if(msg) $$.msg(msg);
						if(typeof ID === 'function') {
							// 复杂场景自定义刷新 You Can You Up
							ID(result.data);
							// 既然如此以后有时间,应该优化为,确定成功回调函数,并且返回result,可以做更多.
						} else {
							// Form模式基本都是更新父页面的组件
							$.parentTableReLoad(ID);
						}
						$.dialogClose(parent.layer, window);
					} else {
						$$.error(result.msg);
					}
				}
			});
		}
	});
}

/**
 * 二次确认提交
 */
function submitFormConfirm($$, ID, $form, action, msg, data) {
	layer.confirm($.I18N('您确认执行当前操作'), {
		title : $.I18N('操作确认'),
		icon : 3,
		btn : [ '确定', '取消' ]
	}, function() {
		submitForm($$, ID, $form, action, msg, data);
	});
}