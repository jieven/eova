(function ($) {

    // import
    var TextBox = $.fn.eovatext.TextBox;

    $.fn.eovainfo = function (options) {

        var list = [];
        this.each(function () {
            // 单例实例化
            var obj = $.data(this, "infobox");
            if (!obj) {
                obj = new InfoBox(this, options);
                // 初始化UI
                obj.render();
                obj.bindEvents();
                $.data(this, "infobox", obj);
            } else {
            	// 覆盖参数
            	$.extend(obj.options, options);
            	$.data(this, "infobox", obj);
            }

            list.push(obj);
        });

        if (list.length == 1) {
            return list[0];
        }

        return list;

    };


    var InfoBox = $.fn.eovainfo.InfoBox = function (dom, options) {
        TextBox.apply(this, arguments);
        this.defaults = {
            btnTitle: '点击编辑信息',
            isReadonly: true
        };
        // 用户参数 覆盖 默认参数 覆盖父类参数
        this.options = $.extend({}, this.options, this.defaults, options);
    }

    $.extendPrototype(InfoBox, TextBox);

    var eovaInfoDialog = function(input) {
    	var info = input.val();
    	if(info && info.indexOf('function') != -1 && info.indexOf('return') != -1){
    		// 格式化js代码
    		info = js_beautify(info, 4);
    	}
    	var $dialog = parent.sy.modalDialog({
    		id : 'eova-info-text-dlg',
    		title : '快速编辑',
    		content: '<textarea id="eova-info-text" class="eova-info-text">'+ info +'</textarea>',
    		buttons : [ {
    			text : ' 保 存 ',
    			handler : function() {
    				info = parent.$('#eova-info-text').val();
    				if(info && info.indexOf('function') != -1 && info.indexOf('return') != -1){
    					// 压缩js空格
    		    		info = $.compressWhiteSpace(info);
    		    	}
    				// 获取编辑后的内容
    				input.val(info);
    				// 关闭Dialog
    		    	$dialog.dialog('destroy');
    			}
    		} ]
    	}, 650, 390);
    };

    /**
     * 重写事件绑定
     */
    InfoBox.prototype.bindEvents = function () {
        var $textbox = this.$textbox;
        var $btn = this.$btn;
        $btn.bind('click', function () {
        	eovaInfoDialog($textbox);
        });
        // 点按钮和文本框都触发事件
        this.$textbox.bind('click', function(){
            $btn.trigger('click');
        });
    };

})(jQuery);