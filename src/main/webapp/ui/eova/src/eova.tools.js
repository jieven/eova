/**
 * Eova JS工具箱
 */
(function($) {

	/**
	 * 拓展全局静态方法
	 */
	$.extend({
		isNull : function(s) {
			if (s == undefined || s == null || s == "" || s == '' || s == "undefined" || s == "null" || s == "NULL") {
				return true;
			}
			return false;
		}
	});

	/**
	 * 字符串
	 */
	$.str = {
		/**
		 * 格式化('您的验证码是{0},{1}分钟后失效!', '123456', 30)
		 */
		format : function(s) {
			for (var i = 0; i < arguments.length - 1; i++) {
				s = s.replace("{" + i + "}", arguments[i + 1]);
			}
			return s;
		},
		/**
		 * 截取字符串，多余的部分用...代替
		 * @param s 待截取字符串
		 * @param len 字符串实际长度，中文2，英文1
		 * @returns {String}
		 */
		clip : function(str, max) {

			var t = "", len = 0;
			if (!str) return t;
			
			for (var i = 0; i < str.length; i++) {
				var ts = str.charAt(i);
				if (ts.charCodeAt() >= 0 && ts.charCodeAt() <= 128)len += 1;
	            else len += 2;
				
				if (len > max) {
					return t + "...";
				} else {
					t += ts;
				}
			}
			return t;
		},
		/**
		 * 判断是否是数字
		 * 
		 * @parasalue
		 * @returns {Boolean}
		 */
		isNum : function(value) {
			if (value != null && value.length > 0 && isNaN(value) == false) {
				return true;
			} else {
				return false;
			}
		},
		/**
		 * 判断是否是中文
		 * 
		 * @param str
		 * @returns {Boolean}
		 */
		isCn : function(str) {
			var reg = /^([u4E00-u9FA5]|[uFE30-uFFA0])*$/;
			if (reg.test(str)) {
				return false;
			}
			return true;
		},
		/**
		 * URL编码
		 * @param url
		 * @returns
		 */
		encodeUrl : function(url) {
			return encodeURIComponent(url);
		}
	};

	/**
	 * 数学
	 */
	$.math = {
		/**
		 * 计算百分比,保留小数点后两位百分比
		 */
		percent : function(num, num2) {
			return (Math.round(num / num2 * 10000) / 100.00 + "%"); //
		},
		/**
		 * 获取随机数
		 * 
		 * @param min 最小值
		 * @param max 最大值
		 * @returns
		 */
		random : function(min, max) {
			var r = Math.random() * (max - min);
			var re = Math.round(r + min);
			re = Math.max(Math.min(re, max), min)
			return re;
		}
	};

	/**
	 * JSON
	 */
	$.json = {
		toObj : function(s) {
			return JSON.parse(s);
		},
		toStr : function(o) {
			return JSON.stringify(o);
		}

	};

	/**
	 * 时间
	 */
	$.time = {
		/**
		 * 时间戳转成时间
		 * 
		 * @param time
		 * @returns
		 */
		format : function(ms) {
			var d = new Date();
			d.setTime(ms);
			var year = d.getFullYear();
			var month = d.getMonth() + 1 < 10 ? "0" + (d.getMonth() + 1) : d.getMonth() + 1;
			var date = d.getDate() < 10 ? "0" + d.getDate() : d.getDate();
			var hour = d.getHours() < 10 ? "0" + d.getHours() : d.getHours();
			var minute = d.getMinutes() < 10 ? "0" + d.getMinutes() : d.getMinutes();
			var second = d.getSeconds() < 10 ? "0" + d.getSeconds() : d.getSeconds();
			return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
		},
		/**
		 * Date to Week
		 * 
		 * @param date 当前日期对象
		 * @returns {星期几}
		 */
		toWeek : function(date) {
			if (date instanceof Date) {
				var dayNames = new Array("星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
				return dayNames[date.getDay()];
			} else {
				return "Param error,date type!";
			}
		},

	};

	/**
	 * 校验
	 */
	$.verify = {
		rules : {
			number : [ /^\d+$/, "请填写数字" ],
			a_z : [ /^[a-z]+$/i, "请填写字母" ],
			date : [ /^\d{4}-\d{2}-\d{2}$/, "请填写有效的日期，格式:yyyy-mm-dd" ],
			time : [ /^([01]\d|2[0-3])(:[0-5]\d){1,2}$/, "请填写有效的时间，00:00到23:59之间" ],
			email : [ /^[\w\+\-]+(\.[\w\+\-]+)*@[a-z\d\-]+(\.[a-z\d\-]+)*\.([a-z]{2,4})$/i, "请填写有效的邮箱" ],
			url : [ /^(https?|s?ftp):\/\/\S+$/i, "请填写有效的网址" ],
			qq : [ /^[1-9]\d{4,}$/, "请填写有效的QQ号" ],
			idcard : [ /^\d{6}(19|2\d)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)?$/, "请填写正确的身份证号码" ],
			tel : [ /^(?:(?:0\d{2,3}[\- ]?[1-9]\d{6,7})|(?:[48]00[\- ]?[1-9]\d{6}))$/, "请填写有效的电话号码" ],
			mobile : [ /^1[3-9]\d{9}$/, "请填写有效的手机号" ],
			zipcode : [ /^\d{6}$/, "请检查邮政编码格式" ],
			chinese : [ /^[\u0391-\uFFE5]+$/, "请填写中文字符" ],
			account : [ /^\w{3,12}$/, "请填写3-12位数字、字母、下划线" ],
			password : [ /^[\S]{6,16}$/, "请填写6-16位字符，不能包含空格" ],
			money : [ /^(?!0\.00)(?:0|[1-9]\d*)(?:\.\d{1,2})?$/, "请填写有效的金额" ]
		},
		rule : function(ruleName) {
			alert(ruleName);
		}
	};

})(jQuery);