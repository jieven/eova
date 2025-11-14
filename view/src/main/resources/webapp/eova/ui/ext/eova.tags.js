(function ($) {
	/**
	 * 多数据选择
	 */
	$.eovatags = {
		/**
		 * 添加标签
		 * @param ID
		 * @param o
		 * @returns {boolean}
		 */
		add : function (ID, o){
			if(!ID)alert('eova tags ID is null');

			var $this = $('#' + ID);
			// 隐藏提示
			var $phd = $this.find('.placeholder');
			if($phd.length == 1){
				$phd.hide();
			}
			// 重复检查
			var flag = true;
			var arrs = $.eovatags.getData(ID);
			for (var i = 0; i < arrs.length; i++) {
				var a = arrs[i];
				if(a.id == o.id){
					return false;
				}
			}
			// 更新DOM
			$this.append($.render('template-' + ID, o));
			// 更新值域(主要用于统一校验)
			var $input = $this.find('input');
			$input.val($input.val() + o.id);
			return true;
		},
		/**
		 * 直接添加选中行数据
		 * @param ID
		 * @param rows 选中数据对象 {id:1, name:'老王'}
		 */
		addRows(ID, rows){
			if (rows.length < 1)
				return;

			let keys = Object.keys(rows[0]);
			// 按字段顺序 首列为id , 次列为 cn
			$.each(rows, function(i, o){
				$.eovatags.add(ID, {
					id : o[keys[0]],
					cn : o[keys[1]]
				});
			});
		},
		/**
		 * 给值
		 * @param ID
		 * @param arrs
		 */
		setData : function (ID, arrs){
			if(!ID)alert('eova tags ID is null');

			$.each(arrs, function(i, o){
				$.eovatags.add(ID, o);
			});
		},
		/**
		 * 取值 [{id : 1, cn : 'a'}]
		 * @param ID
		 * @returns {[]}
		 */
		getData: function (ID){
			if(!ID)alert('eova tags ID is null');

			var as = [];
			$('#' + ID +' .eova-tag-item .eova-tag-name').each(function(i, o){
				as.push({
					id : $(o).data('id'),
					cn : $(o).text()
				});
			});
			return as;
		},
		/**
		 * 取值 1,10,21
		 * @param ID
		 * @returns {string}
		 */
		getDataStr: function (ID){
			let data = $.eovatags.getData(ID);
			if (data.length < 1)
				return "";

			let vals = [];
			let keys = Object.keys(data[0]);
			$.each(data, function(i, o){vals.push(o[keys[0]])});
			return vals.join(",");
		}
	}
})(jQuery);