(function ($) {

    BOOLBOX = "boolbox";

    $.fn.eovabool = function (options) {
        var list = [];
        this.each(function () {
            // 单例实例化
            var obj = $.data(this, BOOLBOX);
            if (!obj) {
                obj = new BoolBox(this, options);
                // 初始化UI
                obj.render();
                $.data(this, BOOLBOX, obj);
            } else {
            	// 覆盖参数
            	$.extend(obj.options, options);
            	$.data(this, BOOLBOX, obj);
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
    var BoolBox = $.fn.eovabool.BoolBox = function (dom, options) {
        this.$dom = $(dom);
        this.defaults = {
            isCheck: false,
            disable: false
        };
        // HTML参数覆盖覆盖默认参数
        var htmlOptions = undefined;
        var strOptions = this.$dom.data('options');
        if(strOptions){
            //console.log(strOptions);
            htmlOptions = eval('({' + strOptions + '})');

            // 获取参数后移除参数
            this.$dom.removeAttr('data-options');
        }
        // 参数优先级：JS参数 > HTML参数 > 默认参数
        this.options = $.extend({}, this.defaults, htmlOptions, options);

        this.name = this.$dom.attr('name');
        this.value = this.$dom.attr('value');
    };

    /**
     * 渲染UI
     */
    BoolBox.prototype.render = function () {
        var $boolbox = $("<input type='checkbox' />").appendTo(this.$dom);

        if (this.name) {
            $boolbox.attr("name", this.name);
        }
        if (this.value) {
            $boolbox.attr("value", this.value);
        }
        if (this.options.isCheck) {
            this.setChecked(true);
        }
        if (this.options.disable) {
            $boolbox.attr('disabled', 'disabled');
        }

        this.$boolbox = $boolbox;
    };

    /**
     * 获取值
     * @returns {*}
     */
    BoolBox.prototype.getChecked = function () {
        return this.$boolbox.prop('checked');
    };
    /**
     * 设置值
     * @param value
     */
    BoolBox.prototype.setChecked = function (flag) {
        this.$boolbox.prop('checked', flag);
    };
    /**
     * 设置只读
     * @param width
     */
    BoolBox.prototype.readonly = function (flag) {
        if (flag) {
            this.$boolbox.attr("readonly", true);
        }

    };

})(jQuery);