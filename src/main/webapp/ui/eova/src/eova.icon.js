(function ($) {

    // import111111111111111111111111111
    var TextBox = $.fn.eovatext.TextBox;

    ICONBOX = "iconbox";

    $.fn.eovaicon = function (options) {

        var list = [];
        this.each(function () {
            // 单例实例化
            var obj = $.data(this, ICONBOX);
            if (!obj) {
                obj = new IconBox(this, options);
                // 初始化UI
                obj.render();
                obj.bindEvents();
                $.data(this, ICONBOX, obj);
            } else {
            	// 覆盖参数
            	$.extend(obj.options, options);
            	$.data(this, ICONBOX, obj);
            }

            list.push(obj);
        });

        if (list.length == 1) {
            return list[0];
        }

        return list;

    };


    var IconBox = $.fn.eovaicon.IconBox = function (dom, options) {
        TextBox.apply(this, arguments);
        this.defaults = {
            btnTitle: '点击选择图标',
            isReadonly: true
        };
        // 用户参数 覆盖 默认参数 覆盖父类参数
        this.options = $.extend({}, this.options, this.defaults, options);

    }

    $.extendPrototype(IconBox, TextBox);

    // ICON选择窗
    var eova_iconDialog = function(input, span) {
    	// 获取选中行
    	// 弹出窗口
    	var dialog = parent.sy.modalDialog({
    		id : 'icon_dialog',
    		title : '修改ICON',
    		url : '/toIcon',
    		buttons : [ {
    			id : 'icon_ok',
    			text : '修改',
    			handler : function() {
    				dialog.find('iframe').get(0).contentWindow.selectIcon(dialog, input, span);
    				// 初始化焦点
    				window.focus();
    			}
    		} ]
    	}, 572, 390);
    };

    /**
     * 重写事件绑定
     */
    IconBox.prototype.bindEvents = function () {
        var $textbox = this.$textbox;
        var $btn = this.$btn;
        $btn.bind('click', function () {
            $btn.addClass("ext-icon-zoom");
            eova_iconDialog($textbox, $btn);
        });
        // 点按钮和文本框都触发事件
        this.$textbox.bind('click', function(){
            $btn.trigger('click');
        });
    };

})(jQuery);