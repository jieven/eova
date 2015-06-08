(function ($) {

    // import
    var TextBox = $.fn.eovatext.TextBox;

    const TIMEBOX = "timebox";

    $.fn.eovatime = function (options) {
//        $.eovaInit(this, FindBox, FINDBOX, options);

        var list = [];
        this.each(function () {
            // 单例实例化
            var obj = $.data(this, TIMEBOX);
            if (!obj) {
                obj = new TimeBox(this, options);
                // 初始化UI
                obj.render();
                obj.bindEvents();
                obj.$dom.attr('onClick', "WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})");
                $.data(this, TIMEBOX, obj);
            }

            list.push(obj);
        });

        if (list.length == 1) {
            return list[0];
        }

        return list;

    };


    var TimeBox = $.fn.eovatime.TimeBox = function (dom, options) {
        TextBox.apply(this, arguments);
        this.defaults = {
            btnTitle: '点击选择时间',
            isReadonly: true
        };
        // 用户参数 覆盖 默认参数 覆盖父类参数
        this.options = $.extend({}, this.options, this.defaults, options);
    }

    $.extendPrototype(TimeBox, TextBox);

    /**
     * 重写事件绑定
     */
    TimeBox.prototype.bindEvents = function () {
        var $textbox = this.$textbox;
        // 点按钮和文本框都触发事件
        this.$btn.bind('click', function(){
            $textbox.trigger('click');
        });
    };


})(jQuery);