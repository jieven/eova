/**
 * Eova 公共 CSS/JS 注入（charset/meta 请写死在 HTML head 最前，避免乱码）
 * 用法：<script src="/eova/ui/meta/eova.base.js"></script>
 * 就绪：await eovaBase.ready 或 eovaBase.ready.then(...)
 */
(function (global) {
    'use strict';

    var STYLES = [
        '/eova/lib/eova/eovaui.css'
    ];

    var SCRIPTS = [
        '/eova/lib/vue/vue.global.js',
        '/eova/lib/vue/axios.min.js',
        '/eova/lib/eova/lib/layui.umd.js?v=1.0',
        '/eova/lib/eova/lib/eova-tools.umd.js?v=1.0.0.10101',
        '/eova/lib/eova/eovaui.js?v=1.0.0'
    ];

    var HEAD = document.head || document.getElementsByTagName('head')[0];
    var loaded = Object.create(null);

    function injectStyle(href) {
        if (loaded[href] || document.querySelector('link[href="' + href + '"]')) {
            loaded[href] = true;
            return Promise.resolve();
        }
        return new Promise(function (resolve, reject) {
            var link = document.createElement('link');
            link.rel = 'stylesheet';
            link.href = href;
            link.onload = function () {
                loaded[href] = true;
                resolve();
            };
            link.onerror = function () {
                reject(new Error('load css failed: ' + href));
            };
            HEAD.appendChild(link);
        });
    }

    function injectScript(src) {
        if (loaded[src] || document.querySelector('script[src="' + src + '"]')) {
            loaded[src] = true;
            return Promise.resolve();
        }
        return new Promise(function (resolve, reject) {
            var script = document.createElement('script');
            script.type = 'text/javascript';
            script.src = src;
            script.async = false;
            script.onload = function () {
                loaded[src] = true;
                resolve();
            };
            script.onerror = function () {
                reject(new Error('load js failed: ' + src));
            };
            HEAD.appendChild(script);
        });
    }

    function loadAll(list, loader) {
        return list.reduce(function (p, url) {
            return p.then(function () {
                return loader(url);
            });
        }, Promise.resolve());
    }

    var ready = loadAll(STYLES, injectStyle)
        .then(function () {
            return loadAll(SCRIPTS, injectScript);
        });

    global.EovaBase = {
        ready: ready
    };

    /**
     * “虫洞”是连接Web宇宙区域间的时空通道。
     * @type {{app: {}, vue: {}, page: {}}}
     * @app Vue App 模版作用域
     * @vue Vue 全局作用域
     * @page Web 页面, JS全局作用域
     */
    window.uzoo = {
        page: {},
        vue: {},
        app: {}
    }

})(window);
