/**
 * fddsfsd
 */
(function ($) {

    // import
    var TextBox = $.fn.eovatext.TextBox;

    const COMBOBOX = "combobox";

    $.fn.eovacombo = function (options) {
//        $.eovaInit(this, FindBox, FINDBOX, options);

        var list = [];
        this.each(function () {
            // 单例实例化
            var obj = $.data(this, COMBOBOX);
            if (!obj) {
                obj = new ComboBox(this, options);
                // 初始化UI
                obj.render();
                obj.reload();
                obj.bindEvents();
                $.data(this, COMBOBOX, obj);
            }

            list.push(obj);
        });

        if (list.length == 1) {
            return list[0];
        }

        return list;

    };

    /**
     * 下拉框 继承 文本框
     * @type {ComboBox}
     */
    var ComboBox = $.fn.eovacombo.ComboBox = function (dom, options) {
        TextBox.apply(this, arguments);
        this.defaults = {
            btnTitle: '点击选择内容',
            btnIcon: '',
            isReadonly: true,
            valueField: 'value',
            textField: 'text'
        };
        // 参数优先级：JS参数 > HTML参数 > 默认参数 > 父类参数
        this.options = $.extend({}, this.options, this.defaults, options);

    }
    /**
     * 下拉框 继承 文本框 原型方法
     */
    $.extendPrototype(ComboBox, TextBox);

    /**
     * 重写渲染
     */
    ComboBox.prototype.render = function () {
        // 添加隐藏框
        var $valuebox = $("<input type='hidden' />").appendTo(this.$dom);
        var $textbox = $("<input type='text' />").appendTo(this.$dom);// 文本显示
        var $btn = $("<i></i>").appendTo(this.$dom);// 添加按钮
        var $panel = $("<div class='eova-combo-panel'></div>").appendTo("body"); // 添加到body保证不被遮盖
//        $("<div value=''>&nbsp;</div>").appendTo($panel); 屏蔽默认项，默认项由服务端控制默认第一项

        $btn.attr('title', this.options.btnTitle);
        $panel.css('cursor', 'pointer');
        if (this.name) {
            $valuebox.attr("name", this.name);
        }
        if (this.value) {
            $valuebox.attr("value", this.value);
        }
        if(this.options.placeholder){
            $textbox.attr('placeholder', this.options.placeholder);
        }
        if(this.options.required){
            $textbox.attr('required', 'required');
        }
        if (this.options.isReadonly) {
            $textbox.attr('readonly', 'readonly');
            $textbox.css('cursor', 'pointer');
            $textbox.attr('title', this.options.btnTitle);
        }

        this.$valuebox = $valuebox;
        this.$textbox = $textbox;
        this.$btn = $btn;
        this.$panel = $panel;

        // 初始宽度
        this.setWidth(this.options.width);
    };
    /**
     * 重写事件绑定
     */
    ComboBox.prototype.bindEvents = function () {
        var comboBox = this;
        var $textbox = this.$textbox;
        var $panel = this.$panel;
        var $btn = this.$btn;

        // 弹出下拉面板Panel
        this.$dom.click(function (e) {
            var offset = $textbox.offset();
            $panel.css({
                position: 'absolute',
                left: offset.left - 1,
                top: offset.top + 21,
                width: $(this).width()
            });
            $panel.show();

            console.log('.eova-combo click');
            e.stopPropagation();
        });


        // 选择下拉项
        $panel.children('div').click(function (e) {

            comboBox.setValue($(this).attr('value'));
            $panel.hide();

            console.log('bindEvents combo panel div click');
            e.stopPropagation();
        });

        // 点击其它部分关闭Panel
        $(document).click(function (e) {
            if ($('.eova-combo-panel').is(":visible")) {
                $('.eova-combo-panel').hide();
                console.log('.eova-combo ul hide');
            }
        });
    };
    /**
     * 销毁控件
     */
    ComboBox.prototype.destroy = function () {
        // 删除控件DOM
        this.$dom.remove();
        // 删除面板(面板添在body里，需要单独删除)
        this.$panel.remove();
    };
    /**
     * 重写值操作，自动更新text和css
     * @param value
     * @returns {*}
     */
    ComboBox.prototype.reload = function (value) {
        var options = this.options;
        var url = options.url;
        if (!url) {
            return;
        }
        var comboBox = this;
        // 同步请求，保证数据先加载，再赋值
        $.ajax({
            type: 'get',
            async: false,
            url: url,
            dataType: "json",
            success: function (json) {
                comboBox.$panel.empty();// 清空下拉面板选项
//                $("<div value=\"\">&nbsp;</div>").appendTo(comboBox.$panel);
                $.each(json, function (index, obj) {
                    $("<div value=\"" + obj[options.valueField] + "\">" + obj[options.textField] + "</div>").appendTo(comboBox.$panel);
                });
                // 选择下拉项
                comboBox.$panel.children('div').click(function (e) {

                    comboBox.setValue($(this).attr('value'));

                    comboBox.$panel.hide();

                    console.log('reload combo panel div click');
                    e.stopPropagation();
                });

                // 根据值刷新选中项
                if(comboBox.getValue()){
                    comboBox.setValue(comboBox.getValue());
                }

                // 更新面板到缓存
                $.data(this, COMBOBOX, comboBox);
            },
            error: function () {
                alert('下拉框加载数据失败,URL=' + url);
            }
        });
    };
    /**
     * 获取值
     * @returns {*}
     */
    ComboBox.prototype.getValue = function () {
        return this.$valuebox.val();
    };
    /**
     * 设置值
     * @param value
     */
    ComboBox.prototype.setValue = function (value) {
        this.$valuebox.val(value);

        // 命中值所在选项
        var $item = this.$panel.children("div[value='"+ value +"']");
        // 更新文本
        this.$textbox.val($item.text());
        // 修改选中样式
        $item.siblings().removeClass('eova-combo-selected');
        $item.addClass('eova-combo-selected');
    };
    /**
     * 获取显示文本
     * @returns {*}
     */
    ComboBox.prototype.getText = function () {
        return this.$textbox.val();
    };
    /**
     * 设置显示文本
     * @param text
     */
    ComboBox.prototype.setText = function (text) {
        this.$textbox.val(text);

        // 命中文本所在选项
        var $item = this.$panel.find("div:contains('"+ text +"')");
        // 更新值
        this.$valuebox.val($item.attr('value'));
        // 修改选中样式
        $item.siblings().removeClass('eova-combo-selected');
        $item.addClass('eova-combo-selected');
    };
    function loadData($dom, json) {

    }

})(jQuery);