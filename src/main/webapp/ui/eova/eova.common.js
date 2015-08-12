/**
 * jQuery Eova Common
 */
(function ($) {

    /**
     * 拓展全局静态方法
     */
    $.extend({
        // 继承原型
        extendPrototype: function (SubType, SuperType) {
            var temp = function () {
            };
            temp.prototype = SuperType.prototype;
            SubType.prototype = new temp();
            SubType.prototype.constructor = SubType;
        },
        /**
         * 初始Eova控件
         * @param EovaType
         * @param eovaKey
         * @param options
         * @returns {*}
         */
        eovaInit: function (jq ,EovaType, eovaKey, options) {
            var list = [];
            jq.each(function () {
                // 单例实例化
                var obj = $.data(this, eovaKey);
                if (!obj) {
                    obj = new EovaType(this, options);
                    $.data(this, eovaKey, obj);
                }

                list.push(obj);
            });

            if (list.length == 1) {
                return list[0];
            }

            return list;
        },
        // Tip 警告
        tipwarn: function($el , msg){
            var $tip = $el.tooltip({
                position: 'bottom',
                deltaY : -6,
                showEvent: null,
                hideEvent: 'mousemove',
                content: '<span><span class="pd5 icon-exclamation">&nbsp;</span>'+ msg +'</span>',
                onShow: function () {
                    $(this).tooltip('tip').css({
                        'border': '1px solid transparent',
                        'background-color': '#fffcef',
                        'border-color': '#ffbb76',
                        'color': '#db7c22'});
                }
            }).tooltip('show');
            setTimeout(function () {
                $tip.tooltip('destroy');
            }, 5000);
            return $tip;
        },
        // 右下角弹窗提示
        slideMsg: function(str,$pjq){
        	var $jq = $;
        	if($pjq){
        		$jq = $pjq;
        	}
            $jq.messager.show({
                title:'操作提示',
                msg:str,
                timeout:1500,
                showType:'slide'
            });
        }
    });

    /**
     * 拓展EasyUI DataGrid编辑器
     */
    $.extend($.fn.datagrid.defaults.editors, {
        eovatext: {
            init: function(container, options) {
                var editor = $("<div class=\"eova-text\"></div>").appendTo(container);
                editor.eovatext(options);
                return editor;
            },
            getValue: function(jq) {
                return $(jq).eovatext().getValue();
            },
            setValue: function(jq, value) {
                $(jq).eovatext().setValue(value);
            },
            resize: function(jq, value) {
                $(jq).eovatext().setWidth(value);
            }
        },
        eovatime: {
            init: function(container, options) {
                var editor = $("<div class=\"eova-time\"></div>").appendTo(container);
                editor.eovatime(options);
                return editor;
            },
            getValue: function(jq) {
                return $(jq).eovatime().getValue();
            },
            setValue: function(jq, value) {
                $(jq).eovatime().setValue(value);
            },
            resize: function(jq, value) {
                $(jq).eovatime().setWidth(value);
            }
        },
        eovacombo: {
            init: function(container, options) {
                var editor = $("<div class=\"eova-combo\"></div>").appendTo(container);
                editor.eovacombo(options);
                return editor;
            },
            destroy: function(jq) {
                $(jq).eovacombo().destroy();
            },
            getText: function(jq) {
                return $(jq).eovacombo().getText();
            },
            getValue: function(jq) {
                return $(jq).eovacombo().getValue();
            },
            setValue: function(jq, value) {
                $(jq).eovacombo().setText(value);
            },
            resize: function(jq, value) {
                $(jq).eovacombo().setWidth(value);
            }
        },
        eovafind: {
            init: function(container, options) {
                var editor = $("<div class=\"eova-find\"></div>").appendTo(container);
                editor.eovafind(options);
                return editor;
            },
            getText: function(jq) {
                return $(jq).eovafind().getText();
            },
            getValue: function(jq) {
                return $(jq).eovafind().getValue();
            },
            setValue: function(jq, value) {
                $(jq).eovafind().setText(value);
            },
            resize: function(jq, value) {
                $(jq).eovafind().setWidth(value);
            }
        },
        eovacheck: {
            init: function(container, options) {
                var editor = $("<div class=\"eova-check\"></div>").appendTo(container);
                editor.eovacheck(options);
                return editor;
            },
            getValue: function(jq) {
                return $(jq).eovacheck().getChecked();
            },
            setValue: function(jq, value) {
                $(jq).eovacheck().setChecked(value);
            },
            resize: function(jq, value) {
                //$(jq).eovatext().setWidth(value);
            }
        }
    });

    /**
     * Grid Cell Edit extend
     */
    $.extend($.fn.datagrid.methods, {
        editCell: function (jq, param) {
            return jq.each(function () {
                var opts = $(this).datagrid('options');
                var fields = $(this).datagrid('getColumnFields', true).concat($(this).datagrid('getColumnFields'));
                for (var i = 0; i < fields.length; i++) {
                    var col = $(this).datagrid('getColumnOption', fields[i]);
                    col.editor1 = col.editor;
                    if (fields[i] != param.field) {
                        col.editor = null;
                    }
                }
                $(this).datagrid('beginEdit', param.index);
                var ed = $(this).datagrid('getEditor', param);
                if (ed) {
                    if ($(ed.target).hasClass('textbox-f')) {
                        $(ed.target).textbox('textbox').focus();
                    } else {
                        $(ed.target).focus();
                    }
                }
                for (var i = 0; i < fields.length; i++) {
                    var col = $(this).datagrid('getColumnOption', fields[i]);
                    col.editor = col.editor1;
                }
            });
        },
        enableCellEditing: function (jq) {
            return jq.each(function () {
                var dg = $(this);
                var opts = dg.datagrid('options');
                opts.oldOnClickCell = opts.onClickCell;
                opts.onClickCell = function (index, field) {
                    if (opts.editIndex != undefined) {
                        if (dg.datagrid('validateRow', opts.editIndex)) {
                            dg.datagrid('endEdit', opts.editIndex);
                            opts.editIndex = undefined;
                        } else {
                            return;
                        }
                    }
                    dg.datagrid('selectRow', index).datagrid('editCell', {
                        index: index,
                        field: field
                    });
                    opts.editIndex = index;
                    opts.oldOnClickCell.call(this, index, field);
                }
            });
        }
    });

    // 屏蔽控件
    $.fn.mask = function () {
        var divHtml = '<div class="divMask" style="position: absolute; width: 100%; height: 22px; left: 0px; top: -3px; background: #DADADA; opacity: 0.5; filter: alpha(opacity=0)"> </div>';
        $(this).wrap('<span style="position: relative"></span>');
        $(this).parent().append(divHtml);
        $(this).data("mask","true");
    }
    //取消屏蔽
    $.fn.unmask = function () {
        $(this).parent().find(".divMask").remove();
        $(this).unwrap();
        $(this).data("mask", "false");
    }

})(jQuery);