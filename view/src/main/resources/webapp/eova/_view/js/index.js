layui.use(['element', 'layer'], function () {
    var $ = layui.$,
        layer = layui.layer,
        element = layui.element;

    //var $ = layui.jquery,
    // laytpl = layui.laytpl,

    var $win = $(window),
        $body = $('body'),
        container = $('#LAY_app'),

        MOD_NAME = 'admin',
        TABS_HEADER = '#LAY_app_tabsheader>li',
        IFRAME = 'layadmin-iframe',

        SHOW = 'layui-show',
        HIDE = 'layui-hide',
        THIS = 'layui-this',
        DISABLED = 'layui-disabled',
        TEMP = 'template',
        APP_BODY = '#LAY_app_body',
        APP_FLEXIBLE = 'LAY_app_flexible',
        FILTER_TAB_TBAS = 'layadmin-layout-tabs',
        APP_SPREAD_SM = 'layadmin-side-spread-sm',
        TABS_BODY = 'layadmin-tabsbody-item',
        ICON_SHRINK = 'layui-icon-shrink-right',
        ICON_SPREAD = 'layui-icon-spread-left',
        SIDE_SHRINK = 'layadmin-side-shrink',
        SIDE_MENU = 'LAY-system-side-menu';


    //记录最近一次点击的页面标签数据
    var tabsPage = {};

    // 首页相关
    var admin = $.admin = {
        //事件监听
        on: function (events, callback) {
            return layui.onevent.call(this, MOD_NAME, events, callback);
        },
        /**
         * 新开Tab页
         * @param {*} url
         * @param {*} text
         */
        openTabsPage: function (url, text) {
            // 遍历页签选项卡
            var matchTo, tabs = $('#LAY_app_tabsheader>li'),
                path = url.replace(/(^http(s*):)|(\?[\s\S]*$)/g, '');

            tabs.each(function (index) {
                var li = $(this),
                    layid = li.attr('lay-id');

                if (layid === url) {
                    matchTo = true;
                    tabsPage.index = index;
                }
            });

            text = text || '新标签页';

            if (!matchTo) {
                $(APP_BODY)
                    .append(
                        ['<div class="' + TABS_BODY + ' layui-show">', '<iframe src="' + url + '" frameborder="0" class="' + IFRAME + '"></iframe>', '</div>']
                            .join(''));
                tabsPage.index = tabs.length;
                element.tabAdd(FILTER_TAB_TBAS, {
                    title: '<span>' + text + '</span>',
                    id: url,
                    attr: path
                });
            }

            // 为指定Tab 追加title属性,方便查看长文本
            $('#LAY_app_tabsheader>li').last().attr('title', text);

            // 定位当前tabs
            element.tabChange(FILTER_TAB_TBAS, url);
            admin.tabsBodyChange(tabsPage.index, {
                url: url,
                text: text
            });

            // admin.cacheTabs();
        }
        // 缓存打开页面
        ,
        cacheTabs: function(){
            // let historyPage = [];
            // let menus = $('#LAY_app_tabsheader>li');
            //
            // let start = menus.length > 10 ? menus.length - 10 : 0;
            // for (let i = start; i < menus.length; i++) {
            //     let url = $(menus[i]).attr("lay-id");
            //     let txt = $(menus[i]).find('span').text();
            //
            //     if (url === 'main'){
            //         continue;
            //     }
            //
            //     // console.log(url + '-' + txt);
            //
            //     historyPage.push({
            //         url: url,
            //         text: txt
            //     })
            // }

            // $.cookie.set("eova_page_history", $.json.toStr(historyPage));
        }
        //获取页面标签主体元素
        ,
        tabsBody: function (index) {
            return $(APP_BODY).find('.' + TABS_BODY).eq(index || 0);
        }

        //切换页面标签主体
        ,
        tabsBodyChange: function (index, options) {
            options = options || {};

            admin.tabsBody(index).addClass(SHOW).siblings().removeClass(SHOW);
            events.rollPage('auto', index);

            //执行 {MOD_NAME}.tabsPage 下的事件
            layui.event.call(this, MOD_NAME, 'tabsPage({*})', {
                url: options.url,
                text: options.text
            });
        },
        //关闭当前 pageTabs
        closeThisTabs: function () {
            if (!tabsPage.index) return;
            $(TABS_HEADER).eq(tabsPage.index).find('.layui-tab-close').trigger('click');

            // admin.cacheTabs();
        }

        //全屏
        ,

        // 左侧伸缩
        sideFlexible: function (status) {
            var app = container,
                iconElem = $('#' + APP_FLEXIBLE),
                screen = admin.screen();

            //设置状态，PC：默认展开、移动：默认收缩
            if (status === 'spread') {
                //切换到展开状态的 icon，箭头：←
                iconElem.removeClass(ICON_SPREAD).addClass(ICON_SHRINK);

                //移动：从左到右位移；PC：清除多余选择器恢复默认
                if (screen < 2) {
                    app.addClass(APP_SPREAD_SM);
                } else {
                    app.removeClass(APP_SPREAD_SM);
                }

                app.removeClass(SIDE_SHRINK)
            } else {
                //切换到搜索状态的 icon，箭头：→
                iconElem.removeClass(ICON_SHRINK).addClass(ICON_SPREAD);

                //移动：清除多余选择器恢复默认；PC：从右往左收缩
                if (screen < 2) {
                    app.removeClass(SIDE_SHRINK);
                } else {
                    app.addClass(SIDE_SHRINK);
                }

                app.removeClass(APP_SPREAD_SM)
            }

            // layui.event.call(this, setter.MOD_NAME, 'side({*})', {
            // 	status: status
            // });
        },

        fullScreen: function () {
            var ele = document.documentElement,
                reqFullScreen = ele.requestFullScreen || ele.webkitRequestFullScreen ||
                    ele.mozRequestFullScreen || ele.msRequestFullscreen;
            if (typeof reqFullScreen !== 'undefined' && reqFullScreen) {
                reqFullScreen.call(ele);
            }
            ;
        }

        //退出全屏
        ,
        exitScreen: function () {
            var ele = document.documentElement
            if (document.exitFullscreen) {
                document.exitFullscreen();
            } else if (document.mozCancelFullScreen) {
                document.mozCancelFullScreen();
            } else if (document.webkitCancelFullScreen) {
                document.webkitCancelFullScreen();
            } else if (document.msExitFullscreen) {
                document.msExitFullscreen();
            }
        }

        , //屏幕类型
        screen: function () {
            var width = $win.width()
            if (width > 1200) {
                return 3; //大屏幕
            } else if (width > 992) {
                return 2; //中屏幕
            } else if (width > 768) {
                return 1; //小屏幕
            } else {
                return 0; //超小屏幕
            }
        }
    };

    //事件
    var events = {
        //伸缩
        flexible: function (othis) {
            var iconElem = othis.find('#' + APP_FLEXIBLE),
                isSpread = iconElem.hasClass(ICON_SPREAD);
            admin.sideFlexible(isSpread ? 'spread' : null);
            if (isSpread) {
                $.cookie.set("isSpread", 1);
            } else {
                $.cookie.set("isSpread", 0);
            }
        }

        //刷新
        ,
        refresh: function () {
            var ELEM_IFRAME = '.' + IFRAME + '',
                length = $('.' + TABS_BODY).length;

            if (tabsPage.index >= length) {
                tabsPage.index = length - 1;
            }

            var iframe = admin.tabsBody(tabsPage.index).find(ELEM_IFRAME);
            try {
                iframe[0].contentWindow.location.reload(true);
            } catch (e) {
                try {
                    //跨域iframe 刷新
                    var tmpUrl = iframe[0].src
                    iframe[0].src = 'about:blank'
                    // 当url 携带#hash参数时， 必须有变化才能刷新
                    var _timeout = setTimeout(function () {
                        iframe[0].src = tmpUrl
                        clearTimeout(_timeout)
                    }, 300)
                } catch (e) {
                    console.log('页面刷新失败!');
                }
            }
        }

        //点击消息
        ,
        message: function (othis) {
            othis.find('.layui-badge-dot').remove();
        }

        //弹出主题面板
        ,
        theme: function () {
            admin.popupRight({
                id: 'LAY_adminPopupTheme',
                success: function () {
                    view(this.id).render('system/theme')
                }
            });
        }


        //全屏
        ,
        fullscreen: function (othis) {
            var SCREEN_FULL = 'layui-icon-screen-full',
                SCREEN_REST = 'layui-icon-screen-restore',
                iconElem = othis.children("i");

            if (iconElem.hasClass(SCREEN_FULL)) {
                admin.fullScreen();
                iconElem.addClass(SCREEN_REST).removeClass(SCREEN_FULL);
            } else {
                admin.exitScreen();
                iconElem.addClass(SCREEN_FULL).removeClass(SCREEN_REST);
            }
        }

        //返回上一页
        ,
        back: function () {
            history.back();
        }

        //主题设置
        ,
        setTheme: function (othis) {
            var index = othis.data('index'),
                nextIndex = othis.siblings('.layui-this').data('index');

            if (othis.hasClass(THIS)) return;

            othis.addClass(THIS).siblings('.layui-this').removeClass(THIS);
            admin.initTheme(index);
        }

        //左右滚动页面标签
        ,
        rollPage: function (type, index) {
            var tabsHeader = $('#LAY_app_tabsheader'),
                liItem = tabsHeader.children('li'),
                scrollWidth = tabsHeader.prop('scrollWidth'),
                outerWidth = tabsHeader.outerWidth(),
                tabsLeft = parseFloat(tabsHeader.css('left'));

            //右左往右
            if (type === 'left') {
                if (!tabsLeft && tabsLeft <= 0) return;

                //当前的left减去可视宽度，用于与上一轮的页标比较
                var prefLeft = -tabsLeft - outerWidth;

                liItem.each(function (index, item) {
                    var li = $(item),
                        left = li.position().left;

                    if (left >= prefLeft) {
                        tabsHeader.css('left', -left);
                        return false;
                    }
                });
            } else if (type === 'auto') { //自动滚动
                (function () {
                    var thisLi = liItem.eq(index),
                        thisLeft;

                    if (!thisLi[0]) return;
                    thisLeft = thisLi.position().left;

                    //当目标标签在可视区域左侧时
                    if (thisLeft < -tabsLeft) {
                        return tabsHeader.css('left', -thisLeft);
                    }

                    //当目标标签在可视区域右侧时
                    if (thisLeft + thisLi.outerWidth() >= outerWidth - tabsLeft) {
                        var subLeft = thisLeft + thisLi.outerWidth() - (outerWidth - tabsLeft);
                        liItem.each(function (i, item) {
                            var li = $(item),
                                left = li.position().left;

                            //从当前可视区域的最左第二个节点遍历，如果减去最左节点的差 > 目标在右侧不可见的宽度，则将该节点放置可视区域最左
                            if (left + tabsLeft > 0) {
                                if (left - tabsLeft > subLeft) {
                                    tabsHeader.css('left', -left);
                                    return false;
                                }
                            }
                        });
                    }
                }());
            } else {
                //默认向左滚动
                liItem.each(function (i, item) {
                    var li = $(item),
                        left = li.position().left;

                    if (left + li.outerWidth() >= outerWidth - tabsLeft) {
                        tabsHeader.css('left', -left);
                        return false;
                    }
                });
            }
        }

        //向右滚动页面标签
        ,
        leftPage: function () {
            events.rollPage('left');
        }

        //向左滚动页面标签
        ,
        rightPage: function () {
            events.rollPage();
        }

        //关闭当前标签页
        ,
        closeThisTabs: function () {
            var topAdmin = parent === self ? admin : parent.layui.admin;
            topAdmin.closeThisTabs();
        }

        //关闭其它标签页
        ,
        closeOtherTabs: function (type) {
            var TABS_REMOVE = 'LAY-system-pagetabs-remove';
            if (type === 'all') {
                $(TABS_HEADER + ':gt(0)').remove();
                $(APP_BODY).find('.' + TABS_BODY + ':gt(0)').remove();

                $(TABS_HEADER).eq(0).trigger('click');
            } else {
                $(TABS_HEADER).each(function (index, item) {
                    if (index && index != tabsPage.index) {
                        $(item).addClass(TABS_REMOVE);
                        admin.tabsBody(index).addClass(TABS_REMOVE);
                    }
                });
                $('.' + TABS_REMOVE).remove();

            }

            // admin.cacheTabs();
        }

        //关闭全部标签页
        ,
        closeAllTabs: function () {
            events.closeOtherTabs('all');
            //location.hash = '';
        }
        //保持打开最近10个菜单
        ,
        autoLoadTabs: function () {
            // layer.confirm('系统加载时将自动打开最近的10个功能', {
            //     title: '是否启用历史记录',
            //     icon: 3,
            //     btn: ['确定', '取消'],
            //     yes: function (index, layero) {
            //         $.cookie.set("eova_page_keep", 1);
            //         layer.close(index);
            //     },
            //     btn2: function (index) {
            //         $.cookie.set("eova_page_keep", 0);
            //     }
            // });
        }

        //遮罩
        ,
        shade: function () {
            admin.sideFlexible();
        }
    };

    // 点击事件(按钮)
    $body.on('click', '*[layadmin-event]', function () {
        var othis = $(this),
            attrEvent = othis.attr('layadmin-event');
        events[attrEvent] && events[attrEvent].call(this, othis);
    });

    // 菜单跳转
    $body.on('click', '*[lay-href]', function () {
        var othis = $(this),
            href = othis.attr('lay-href'), // url跳链
            text = othis.attr('title'); // 非Left Menu 按钮点击 open tab


        tabsPage.elem = othis;

        //执行跳转
        //var topLayui = parent === self ? layui : top.layui;
        admin.openTabsPage(href, text || othis.html());
    });

    // 监听侧边导航点击事件
    element.on('nav(layadmin-system-side-menu)', function (elem) {
        if (elem.siblings('.layui-nav-child')[0] && container.hasClass(SIDE_SHRINK)) {
            admin.sideFlexible('spread');
            layer.close(elem.data('index'));
        }
        ;
        tabsPage.type = 'nav';
    });

    // 监听 tab 组件切换，同步 index
    element.on('tab(' + FILTER_TAB_TBAS + ')', function (data) {
        tabsPage.index = data.index;
    });

    // 监听选项卡 多层选择效果的处理??
    // element.on('nav(layadmin-pagetabs-nav)', function (elem) {
    // 	var dd = elem.parent();
    // 	dd.removeClass(THIS);
    // 	dd.parent().removeClass(SHOW);
    // });

    // 同步路由
    var setThisRouter = function (othis) {
        var layid = othis.attr('lay-id'),
            attr = othis.attr('lay-attr'),
            index = othis.index();

        admin.tabsBodyChange(index, {
            url: attr
        });
    };

    // Tabs 标题点击,切换到选择界面
    $body.on('click', TABS_HEADER, function () {
        var othis = $(this),
            index = othis.index();

        tabsPage.type = 'tab';
        tabsPage.index = index;

        setThisRouter(othis);
    });

    // 监听选项卡切换，改变菜单选中状态
    admin.on('tabsPage(setMenustatus)', function (router) {
        var pathURL = router.url,
            getData = function (item) {
                return {
                    list: item.children('.layui-nav-child'),
                    a: item.children('*[lay-href]')
                }
            },
            sideMenu = $('#' + SIDE_MENU),
            SIDE_NAV_ITEMD = 'layui-nav-itemed'

            //捕获对应菜单
            ,
            matchMenu = function (list) {
                list.each(function (index1, item1) {
                    var othis1 = $(item1),
                        data1 = getData(othis1),
                        listChildren1 = data1.list.children('dd'),
                        matched1 = pathURL === data1.a.attr('lay-href');

                    listChildren1.each(function (index2, item2) {
                        var othis2 = $(item2),
                            data2 = getData(othis2),
                            listChildren2 = data2.list.children('dd'),
                            matched2 = pathURL === data2.a.attr('lay-href');

                        listChildren2.each(function (index3, item3) {
                            var othis3 = $(item3),
                                data3 = getData(othis3),
                                matched3 = pathURL === data3.a.attr('lay-href');

                            if (matched3) {
                                var selected = data3.list[0] ? SIDE_NAV_ITEMD : THIS;
                                othis3.addClass(selected).siblings().removeClass(selected); //标记选择器
                                return false;
                            }

                        });

                        if (matched2) {
                            var selected = data2.list[0] ? SIDE_NAV_ITEMD : THIS;
                            othis2.addClass(selected).siblings().removeClass(selected); //标记选择器
                            return false
                        }

                    });

                    if (matched1) {
                        var selected = data1.list[0] ? SIDE_NAV_ITEMD : THIS;
                        othis1.addClass(selected).siblings().removeClass(selected); //标记选择器
                        return false;
                    }

                });
            }

        //重置状态
        sideMenu.find('.' + THIS).removeClass(THIS);

        //移动端点击菜单时自动收缩
        if (admin.screen() < 2) admin.sideFlexible();

        //开始捕获
        matchMenu(sideMenu.children('li'));
    });

    // 监听 tabspage 删除
    element.on('tabDelete(' + FILTER_TAB_TBAS + ')', function (obj) {
        var othis = $(TABS_HEADER + '.layui-this');

        obj.index && admin.tabsBody(obj.index).remove();
        setThisRouter(othis);

        // 移除resize事件
        // admin.delResize();
    });

    // Left Menu 鼠标移入Tip
    $body.on('mouseenter', '*[lay-tips]', function () {
        var othis = $(this);

        if (othis.parent().hasClass('layui-nav-item') && !container.hasClass(SIDE_SHRINK)) return;

        var tips = othis.attr('lay-tips'),
            offset = othis.attr('lay-offset'),
            direction = othis.attr('lay-direction'),
            index = layer.tips(tips, this, {
                tips: direction || 1,
                time: -1,
                success: function (layero, index) {
                    if (offset) {
                        layero.css('margin-left', offset + 'px');
                    }
                }
            });
        othis.data('index', index);
    }).on('mouseleave', '*[lay-tips]', function () {
        layer.close($(this).data('index'));
    });


    // 用户本地设置&收缩菜单
    let isSpread = $.cookie.get("isSpread") || 1;
    // console.log("是否收缩:" + isSpread);
    if (isSpread == 1) {
        admin.sideFlexible('spread');
    } else {
        admin.sideFlexible(null);
    }

    // 打开当前已固定菜单功能
    let historyPage = $.json.toObj($.cookie.get("eova_page_history") || "[]");
    for (let i = 0; i < historyPage.length; i++) {
        let o = historyPage[i];
        admin.openTabsPage(o.url, o.text);
    }

    // 初始加载全标记为固定
    $('#LAY_app_tabsheader>li').addClass("tab-ding");

    // 右键菜单
    let $rightClickMenu = $('#rightClickMenu');
    let $thisTab;

    // 给Tab绑定右键事件
    $('#LAY_app_tabsheader').on('contextmenu', 'li', function (e) {
        e.preventDefault(); // 阻止默认右键菜单
        let $that = $(this);

        $thisTab = $that;

        let url = $thisTab.attr("lay-id");
        if (url === 'main'){
            // 忽略主页
            return;
        }

        // 获取Tab的坐标
        var y = $that.offset().top - 20;
        var x = $that.offset().left - 20;
        // console.log(y);
        // console.log(x);

        $rightClickMenu.css({
            'display': 'block',
            'top': y + 'px',
            'left': x + 'px'
        }).find('.layui-nav-child').addClass("layui-show");
    });

    // 菜单项事件
    $('#rightClickMenu a').on('click', function () {
        var eventType = $(this).attr('lay-event');
        console.log(eventType)
        // 去掉选中样式
        $rightClickMenu.find('.layui-this').removeClass("layui-this");

        let url = $thisTab.attr("lay-id");
        let txt = $thisTab.find('span').text();

        console.log(url + '-' + txt);

        switch (eventType) {
            case 'ding':
                if (historyPage.some(item => item.text === txt)) {
                    console.log("已固定过, 不能重复添加")
                    return;
                }

                historyPage.push({
                    url: url,
                    text: txt
                });

                // 编辑操作
                layer.msg('已固定成功');
                // 标记固定效果
                $thisTab.addClass("tab-ding");
                break;
            case 'unding':

                // 移除功能
                historyPage = historyPage.filter(item => item.text !== txt);

                // 删除操作
                $thisTab.removeClass("tab-ding");
                layer.msg('已取消固定');
                break;
        }

        $.cookie.set("eova_page_history", $.json.toStr(historyPage));
    });

});

// 修改密码
function updatePwd(title, url) {
    // 第三方服务修改密码
    if (!$.isNull(userServiceUrl)) {
        addTab('修改密码', userServiceUrl);
        return;
    }

    $.open.dialog(title, url, 385, 260, null);
}

// 一键重登
function relogin(title, url) {
    $.getJSON('/eova/admin/reLogin', function (o) {
        if (o.state == "ok") {
            $('#SU').text('');
            $.msg('重新登录到：' + o.name);
        } else {
            $.error('重新登录失败, 请手工重新登录');
        }
    });
}


// 即将弃用, 请使用 $.open.tab(url, title);
function addTab(title, url, icon) {
    $.admin.openTabsPage(url, title);
}

$(function () {

    // 用户本地设置&全屏和迷你皮肤的
    let isFull = $.cookie.get("is_full") || 1;
    if (screen.height < 1080) {
        console.log("浏览器的分辨率:" + screen.height);
        // 首次建议全屏浏览, 拒绝后不再弹
        if (isFull == 1) {
            layer.confirm('是否全屏预览获得最佳体验', {
                title: '您当前屏幕的分辨率小于1080P',
                icon: 3,
                btn: ['确定', '取消'],
                yes: function (index, layero) {
                    $.admin.fullScreen()
                    layer.close(index);
                },
                btn2: function (index) {
                    $.cookie.set("is_full", 0)
                }
            });
        }
        console.log("自动适配skin mini")
        // 小分辨率, 适配迷你皮肤
        $.cookie.set("skin", "mini");
    } else {
        // 分辨率变大, 自动取消mini
        $.cookie.delete("skin");
    }

})

// 清空所有缓存(未知场景的清理浏览器端缓存)
// window.localStorage.clear();