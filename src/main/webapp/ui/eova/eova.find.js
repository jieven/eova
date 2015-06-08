(function ($) {

    // import
    var TextBox = $.fn.eovatext.TextBox;

    const FINDBOX = "findbox";

    $.fn.eovafind = function (options) {
//        $.eovaInit(this, FindBox, FINDBOX, options);

        var list = [];
        this.each(function () {
            // 单例实例化
            var obj = $.data(this, FINDBOX);
            if (!obj) {
                obj = new FindBox(this, options);
                // 初始化UI
                obj.render();
                obj.bindEvents();
                obj.init();
                $.data(this, FINDBOX, obj);
            }

            list.push(obj);
        });

        if (list.length == 1) {
            return list[0];
        }

        return list;

    };

    var FindBox = $.fn.eovafind.FindBox = function (dom, options) {
        TextBox.apply(this, arguments);
        this.defaults = {
            btnTitle: '点击查找内容',
            isReadonly: true
        };
        // 参数优先级：JS参数 > HTML参数 > 默认参数 > 父类参数
        this.options = $.extend({}, this.options, this.defaults, options);
        // 编码
        this.code = this.$dom.attr('code');
        this.$dom.removeAttr('code');
        // 字段名
        this.field = this.$dom.attr('field');
        this.$dom.removeAttr('field');
    }

    $.extendPrototype(FindBox, TextBox);

    /**
     * 重写渲染
     */
    FindBox.prototype.render = function () {
        // 添加隐藏框
        var $valuebox = $("<input type='hidden' />").appendTo(this.$dom);
        var $textbox = $("<input type='text' />").appendTo(this.$dom);// 文本显示
        var $btn = $("<i></i>").appendTo(this.$dom);// 添加按钮

        $btn.attr('title', this.options.btnTitle);
        if (this.name) {
            $valuebox.attr("name", this.name);
        }
        if (this.value) {
            $valuebox.val(this.value);
        }
        if(this.options.placeholder){
            $textbox.attr('placeholder', this.options.placeholder);
        }
        if(this.options.required){
            $textbox.attr('required', 'required');
        }
        // 查找框禁止编辑
        $textbox.attr('readonly', 'readonly');
        $textbox.css('cursor', 'pointer');
        $textbox.attr('title', this.options.btnTitle);

        this.$valuebox = $valuebox;
        this.$textbox = $textbox;
        this.$btn = $btn;

        // 初始宽度
        this.setWidth(this.options.width);
    };

    /**
     * 初始化数据，将值转换成对应字符串
     */
    FindBox.prototype.init = function() {
        var $valuebox = this.$valuebox;
        var $textbox = this.$textbox;

        if ($valuebox.val()) {
            var url = '/widget/findCnByEn/' + this.code + '-' + this.field + '-' + this.value;
            $.ajax({
                url: url,
                dataType: 'text',
                success: function (o) {
                    $textbox.val(o);
                }
            });
        }
    }

    /**
     * 重写事件绑定
     */
    FindBox.prototype.bindEvents = function () {
        var $valuebox = this.$valuebox;
        var $textbox = this.$textbox;
        var $dom = this.$dom;
        this.$textbox.bind('click', function () {
            eova_findDialog($valuebox, $textbox, $dom.attr('url') );
        });
        // 点按钮和文本框都触发事件
        this.$btn.bind('click', function(){
            $textbox.trigger('click');
        });
    };

})(jQuery);