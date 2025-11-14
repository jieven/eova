/*
 * Copyright (c) 2020 Wuhan JianMa Technology Co. LTD. All rights reserved.
 */

(function ($) {
	/**
	 * 新开窗口
	 */
	$.eovatags1 = {
		// 添加标签
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
			var arrs = $.eovatags1.getData(ID);
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
			if ($input.val()){
				$input.val($input.val() +","+ o.id);
			}else {
				$input.val(o.id);
			}

			return true;
		},
		// 取值 [{id : 1, cn : 'a'}]
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
		// 给值
		setData : function (ID, arrs){
			if(!ID)alert('eova tags ID is null');
			$.each(arrs, function(i, o){
				$.eovatags1.add(ID, o);
			});
		},
		clear:function (ID){
			if(!ID)alert('eova tags ID is null');

			var $this = $('#' + ID);
			$this.find(".eova-tag-item").remove()
			var $input = $this.find('input');
			$input.val("");
		},
		remove:function (ID, val){
			if(!ID)alert('eova tags ID is null');

			var $this = $('#' + ID);
			console.log($this.find(".eova-tag-item [data-id="+val+"]"))
			$this.find(".eova-tag-item [data-id="+val+"]").parent().remove()
			var $input = $this.find('input');
			let list = $input.val().split(",")
			list.splice(list.indexOf(val),1)
			$input.val(list.join(","))
		}
	}
})(jQuery);