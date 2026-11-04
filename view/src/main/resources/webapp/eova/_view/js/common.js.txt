/**
 * jQuery Eova Common
 */
(function ($) {

    /**
     * 拓展全局静态方法
     */
    $.extend({
    	/**
		 * Eova Post Json
		 * @param url 
		 * @param P2 post成功的回调字符串提示文案或函数回调
		 * @param P3 post的自定义参数 
		 * @returns
		 */
    	eovaPost: function(url, P2, P3){
    		$.ajax({
    			async: true,
    			type: 'POST',
    			data: P3 || {},
    			url: url,
    			dataType: "json",
    			error: function(){
    				$.error("服务端异常");
    			},
    			success: function(ret){
    				if(typeof P2 === 'function') {
    					P2(ret);
					} else {
						if (ret.state == 'ok') {
	    					$.msg(P2 || '操作成功');
	    				} else {
	    					$.error(ret.msg);
	    				}
					}
    			}
    		});
    	},
        /** 同步Post **/
        syncPost: function (url, data, success) {
            $.ajax({
                async: false,
                type: 'POST',
                url: url,
                data: data,
                success: success,
                dataType: "json"
            });
        },
        /** 同步获取JSON **/
        syncGetJson: function (url, success) {
            $.ajax({
                async: false,
                type: 'GET',
                url: url,
                success: success,
                dataType: "json"
            });
        },
        /** Html转义 **/
        encodeHtml: function (s) {
            return (typeof s != "string") ? s :
                s.replace(/"|&|'|<|>|[\x00-\x20]|[\x7F-\xFF]|[\u0100-\u2700]/g,
                    function ($0) {
                        var c = $0.charCodeAt(0), r = ["&#"];
                        c = (c == 0x20) ? 0xA0 : c;
                        r.push(c);
                        r.push(";");
                        return r.join("");
                    });
        },
        /** Html过滤 **/
        filterHtml: function (s) {
        	return s.replace(/<[^>]+>/g,"");
        },
        /** 追加URL参数 **/
        appendUrlPara: function (url, key, val) {
			return $.url.appendPara(url, key, val);
        },
        /** 获取URL参数 **/
        getUrlPara: function (name) {
			return $.url.getPara(name);
        },
        /** 获取URL QueryString **/
        getUrlParas: function () {
			return $.url.getParas();
        },
        /** 获取Form参数对象-用于Post请求 **/
        getFormParasObj: function (form) {
        	var o = {};
    		$.each(form.serializeArray(), function(index) {
    			if (o[this['name']]) {
    				o[this['name']] = o[this['name']] + "," + this['value'];
    			} else {
    				o[this['name']] = this['value'];
    			}
    		});
    		return o;
        },
        /** 获取Form参数字符-用于get请求 **/
        getFormParasStr: function (form) {
        	var o = "";
        	$.each(form.serializeArray(), function(index) {
        		var key = this['name'], val = this['value'];
        		if(val && val.length > 0){
        			o = o + key + "=" + val + "&";        			
        		}
        	});
        	return o.substring(0, o.length-1); 
        },
        /** 获取浏览器类型 **/
        getBrowser: function() {
        	var explorer = window.navigator.userAgent;
			if (explorer.indexOf("MSIE") >= 0) {
				return 'ie';
			} else if (explorer.indexOf("Firefox") >= 0) {
				return 'firefox';
			} else if (explorer.indexOf("Chrome") >= 0) {
				return 'chrome';
			} else if (explorer.indexOf("Opera") >= 0) {
				return 'opera';
			} else if (explorer.indexOf("Safari") >= 0) {
				return 'safari';
			}
        },
        /** 格式化自动2位补零，制保留2位小数，如：2，会在2后面补上00.即2.00 **/
		formatDouble : function(x) {
			var f = Math.round(x * 100) / 100;
			var s = f.toString();
			var rs = s.indexOf('.');
			if (rs < 0) {
				rs = s.length;
				s += '.';
			}
			while (s.length <= rs + 2) {
				s += '0';
			}
			return s;
		},
		/**
		 * 格式化JSON
		 * @param txt 
		 * @param compress 是否压缩
		 * @returns
		 */
		jsonformat : function(json, compress) {
			var indentChar = '    ';
			if (/^\s*$/.test(json)) {
				alert('数据为空,无法格式化! ');
				return;
			}
			try {
				var data = eval('(' + json + ')');
			} catch (e) {
				return;
			}
			var draw = [], last = false, This = this, line = compress ? '' : '\n', nodeCount = 0, maxDepth = 0;
			var notify = function(name, value, isLast, indent, formObj) {
				nodeCount++;
				for ( var i = 0, tab = ''; i < indent; i++)
					tab += indentChar;
				tab = compress ? '' : tab;
				maxDepth = ++indent;
				if (value && value.constructor == Array) {
					draw.push(tab + (formObj ? ('"' + name + '":') : '') + '[' + line);
					for ( var i = 0; i < value.length; i++)
						notify(i, value[i], i == value.length - 1, indent, false);
					draw.push(tab + ']' + (isLast ? line : (',' + line)));
				} else if (value && typeof value == 'object') {
					draw.push(tab + (formObj ? ('"' + name + '":') : '') + '{' + line);
					var len = 0, i = 0;
					for ( var key in value)
						len++;
					for ( var key in value)
						notify(key, value[key], ++i == len, indent, true);
					draw.push(tab + '}' + (isLast ? line : (',' + line)));
				} else {
					if (typeof value == 'string')
						value = '"' + value + '"';
					draw.push(tab + (formObj ? ('"' + name + '":') : '') + value + (isLast ? '' : ',') + line);
				}
				;
			};
			var isLast = true, indent = 0;
			notify('', data, isLast, indent, false);
			return draw.join('');
		},
		/**
		 * 自动获取焦点
		 * @param $input
		 * @returns
		 */
		autoFocus : function($input) {
			$input.focus();
			var s = $input.val();
			$input.val("");
			$input.val(s);
		},
		/**
		 * 异步文件上传
		 * @param $input
		 * @returns
		 */
		ajaxUpload : function(url, fileId, fileName, success) {
			var file = document.getElementById(fileId).files[0];
			
			var data = new FormData();
			data = new FormData();
			data.append(fileName, file);
			
			$.ajax({
			    url: url,
			    type: 'POST',
			    cache: false,
			    data: data,
			    processData: false,
			    contentType: false,
			}).done(success);
		},
		/**
		 * Form Validator By Nice
		 */
		configValidator: function($form, fields){
			$form.validator({
	            debug: false,
	            stopOnError: true,
	            focusInvalid: true,
	            showOk: false,
	            timely: false,
	            msgMaker: false,
	            fields: fields
	        });

	        $form.on("validation", $.validation);
		},
		/**
		 * 父页面Table刷新
		 * @param Table ID
		 */
		parentTableReLoad: function (ID) {
			parent.layui.use(['table'], function () {
				parent.layui.table.reload(ID);
			});
		},
		/**
		 * 本页面Table刷新
		 */
		tableReLoad: function (ID) {
			layui.use(['table'], function () {
				layui.table.reload(ID);
			});
		},
		/*
		 * @tpl  模版ID 变量语法 @{x.xx} @{x.xx.xxx}
		 * @dic  数据 {a : 1, b : 2}
		 * @return
		 */
		render: function (ID, dic) {
			var tpl = document.getElementById(ID).innerHTML;
			// 将tpl中所有的找出来
			var reg = /\@\{((\w+)(\.\w+)*)\}/g;
			// 替换tpl中符合正则表达式描述的那些内容
			return tpl.replace(reg, function (match, $1, $2, $3, $4) {
				var arr = $1.split(".");
				var result = dic;
				for (var i = 0; i < arr.length - 1; i++) {
					result = result[arr[i]];
				}
				return result[arr[i]];
			});
		},
		renderList: function (ID, list) {
			let s = '';
			$.each(list, function(i, o){
				s += render(ID, o);
			});
			return s;
		},
		/*
		 * @data Table 数据
		 * @names 需要行合并的列 [字段名...]
		 * @return 需要行合并的列 [字段列索引...]
		 */
		mergeRow: function (data, names, indexs) {
			var mergeIndex = 0;// 从第几行开始做合并
			var mark = 1; // 这里涉及到简单的运算，mark是计算每次需要合并的格子数
			
			for (var k = 0; k < names.length; k++) { //这里循环所有要合并的列
				var trArr = $(".layui-table-body>.layui-table").find("tr");//所有行
				for (var i = 1; i < data.length; i++) { //这里循环表格当前的数据
					var tdCurArr = trArr.eq(i).find('td[data-field="'+ names[k] +'"]');//获取当前行的当前列
					var tdPreArr = trArr.eq(mergeIndex).find('td[data-field="'+ names[k] +'"]');//获取相同列的第一列
					
					if (data[i][names[k]] === data[i-1][names[k]]) { //后一行的值与前一行的值做比较，相同就需要合并
						mark += 1;
						tdPreArr.each(function () {//相同列的第一列增加rowspan属性
							$(this).attr("rowspan", mark);
						});
						tdCurArr.each(function () {//当前行隐藏
							$(this).css("display", "none");
						});
					}else {
						mergeIndex = i;
						mark = 1;//一旦前后两行的值不一样了，那么需要合并的格子数mark就需要重新计算
					}
				}
				mergeIndex = 0;
				mark = 1;
			}
			
		},
		/**
		 * EOVA Radio 给值
		 * @param id
		 * @param val
		 */
		eovaRadioSetValue: function(id, val){
			$($.str.format('input[id="{0}_{1}"]', id, val)).prop("checked",true);
		},
		/**
		 *  IFrame加载完成后高度自适应
		 * @param id
		 */
		iframeHeightAuto: function(id){
			let iframe = document.getElementById(id);
			iframe.onload = function() {
				try{
					let bHeight = iframe.contentWindow.document.body.scrollHeight;
					let dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
					let height = Math.min(bHeight, dHeight);
					iframe.height = height+20;
				}catch (ex){}
			};
		},
		/**
		 * eova.layout 自适应算法
		 * 1.只需要等Eova控件完成渲染即可,如果Box 大小不确定会影响组件的自适应填充
		 */
		eovaLayoutResize: function resize() {
			// 更新目标宽或高
			function updateStyle($ele, newPx) {
				if (!$ele) return;
				var ns = $ele.attr('style');
				if(!ns || ns == '')return;
				// 大于10px才进行替换
				let re = /((1[1-9])|([2-9]\d)|([1-9]\d{2,}))px/g;

				// console.log('变更前' + ns);

				ns = ns.replace(re, newPx + 'px');
				// JS加载页面时 QueryForm高度为0
				if (ns === 'height: 0px;') {
					ns = 'height: '+ newPx +'px;';
				}

				// console.log(newPx + '变更后' + ns);
				$ele.attr('style', ns); // 宽高和边距一起替换
			}

			// 区块高度自适应计算
			$('.box.resize').each(function (i, o) {
				// 我自己
				var $me = $(o),
					ms = $me.attr('style');
				// 没有高度 || 100%高度 || 自适应高度 -> 不会成为需要动态计算的主体
				if (ms.indexOf('height') == -1 || ms.indexOf('height: 100%') != -1 || ms.indexOf('height: calc') != -1) {
					return;
				}

				var meHeight = $me.height()
				var childrenHeight = $me.children().height();

				if (childrenHeight === 0) childrenHeight = 15 // 强制纠正最小值为15

				// 大小差距小于15px 无需变更, 控件最少20px, 差距会受 margin padding 等属性影响
				if (Math.abs(meHeight - childrenHeight) < 15) {
					return;
				}
				// 既然改变不了你就选择适应你
				updateStyle($me, childrenHeight);

				// 我兄弟呢?不是弟弟就是哥哥
				var $brother = $me.next().length == 1 ? $me.next() : $me.prev();
				// 如果我没了(子没了我也会没)我的位置腾出来由我兄弟继承
				if (childrenHeight == 0) {
					childrenHeight = childrenHeight - 10;
				}
				// 通知我兄弟也适应我的仔仔
				updateStyle($brother, childrenHeight);
			});
		}
    });
})(jQuery);

$(function(){
	/**
	 * 全局ajaxerror方法：（如果ajax请求中，有error的处理方法，此处不执行）
 	 */
	$(document).ajaxError(
		function(event,xhr,options,exc ){
			if(xhr.status == 'undefined'){
				return;
			}
			switch(xhr.status){
				case 200:
					// 登录失效重定向到登录页
					let txt = xhr.responseText;
					if (txt.indexOf("EOVA-LOGIN") != -1 || txt.indexOf("doLogin") != -1){
						console.log("登录过期, 请重新登录");
						top.location.href = '/';
						// location.reload();// 兼容嵌套模式
					}
					break;
				case 0:
					console.log("网络故障")
					parent.$.msg("网络故障, 请检查网络状态");
					break;
				case 404:
					console.log("HTTP 404 URL不存在")
					parent.$.error("您访问的资源不存在");
					break;
				case 403:
					console.log("HTTP 403 您没有访问权限系统");
					console.log(xhr.responseText);
					break;
				case 500:
					console.log("HTTP 500 服务端代码异常")
					parent.$.error("服务异常，请稍后再试！");
					break;
				case 502:
					console.log("HTTP 502 服务已停止");
					parent.$.msg("服务无响应");
					break;
				default:
					console.error(xhr.status + "：未知HTTP状态");
					console.error(xhr.responseText);
			}
		}
	);
});