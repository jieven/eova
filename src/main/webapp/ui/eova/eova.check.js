/**
 * jQuery Eova CheckBox
 */
(function ($) {

    const CHECKBOX = "checkbox";

    $.fn.eovacheck = function (options) {
        var list = [];
        this.each(function () {
            // 单例实例化
            var obj = $.data(this, CHECKBOX);
            if (!obj) {
                obj = new CheckBox(this, options);
                // 初始化UI
                obj.render();
                $.data(this, CHECKBOX, obj);
            }

            list.push(obj);
        });

        if (list.length == 1) {
            return list[0];
        }

        return list;
    };

    /**
     * 构造函数
     * @param {Object} dom 选择
     * @param {Object} options 参数
     */
    var CheckBox = $.fn.eovacheck.CheckBox = function (dom, options) {
        this.$dom = $(dom);
        this.defaults = {
            isCheck: false
        };
        // 用户参数覆盖默认参数
        this.options = $.extend({}, this.defaults, options);

        this.name = this.$dom.attr('name');
        this.value = this.$dom.attr('value');
    };

    /**
     * 渲染UI
     */
    CheckBox.prototype.render = function () {
        var $checkbox = $("<input type='checkbox' />").appendTo(this.$dom);

        if (this.name) {
            $checkbox.attr("name", this.name);
        }
        if (this.value) {
            $checkbox.attr("value", this.value);
        }
        if (this.options.isCheck) {
            this.setChecked(true);
        }

        this.$checkbox = $checkbox;
    };

    /**
     * 获取值
     * @returns {*}
     */
    CheckBox.prototype.getChecked = function () {
        return this.$checkbox.prop('checked');
    };
    /**
     * 设置值
     * @param value
     */
    CheckBox.prototype.setChecked = function (flag) {
        this.$checkbox.prop('checked', flag);
    };
    /**
     * 设置只读
     * @param width
     */
    CheckBox.prototype.readonly = function (flag) {
        if (flag) {
            this.$checkbox.attr("readonly", true);
        }

    };

})(jQuery);