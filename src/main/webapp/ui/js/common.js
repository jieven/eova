/**
 * jQuery Eova Common
 */
(function ($) {

    /**
     * 拓展全局静态方法
     */
    $.extend({
        /** 同步Post **/
        syncPost: function (url, data, success) {
            $.ajax({
                async: false,
                type: 'POST',
                url: url,
                data: data,
                success: success,
                dataType: "json"
            });
        },
        /** 同步获取JSON **/
        syncGetJson: function (url, success) {
            $.ajax({
                async: false,
                type: 'GET',
                url: url,
                success: success,
                dataType: "json"
            });
        },
        /** Html转义 **/
        encodeHtml: function (s) {
            return (typeof s != "string") ? s :
                s.replace(/"|&|'|<|>|[\x00-\x20]|[\x7F-\xFF]|[\u0100-\u2700]/g,
                    function ($0) {
                        var c = $0.charCodeAt(0), r = ["&#"];
                        c = (c == 0x20) ? 0xA0 : c;
                        r.push(c);
                        r.push(";");
                        return r.join("");
                    });
        }

    });
})(jQuery);
