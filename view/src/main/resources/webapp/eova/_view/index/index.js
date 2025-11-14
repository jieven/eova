const {createApp, ref, reactive, watch, computed, onMounted, nextTick} = Vue; // 使用全局 Vue 对象
const {me} = EovaUI
const {x} = EovaTools

const app = createApp({
    setup() {

        let showAll = false // 是否显示所有菜单

        // 菜单分类
        const cats = ref()
        // 菜单
        const menus = ref()

        // 已打开菜单选项
        let tabMenus = ref([
            {
                name: '首页',
                active: true,
                url: '/theme',
                link: '/theme',
                id: 0
            }
        ])


        // 所有可用菜单
        const showMenu = ref(true) // 菜单显示
        const isFull = ref(false) // 全屏
        const currentUser = ref() // 当前用户信息
        let showDrop = false // 显示用户菜单下拉

        // 弹出
        const popUserRef = ref()
        const refSelectItems = ref < HTMLElement > (null);
        const hideUserMenu = () => {
            setTimeout(() => {
                popUserRef.value.hide()
            }, 300)
        }

        // 切换显示
        const toggleShow = () => {
            showAll = !showAll
        }

        // 虚拟用户切换
        const switchUser = () => {
            me.cross.on('eova-layer-ok_done_data', (data) => {
                currentUser.value = data
                // 使用后移除消息监听(否则会重复执行)
                // me.cross.off('eova-layer-ok_done_data')
            })
            me.layer.open('超级用户切换', '/eova/admin/su', 1200, 0.9, () => {
                me.layer.msg('切换为:' + currentUser.value)
                init();
            })
        }

        // 虚拟用户还原
        const resetUser = () => {
            axios.post('/eova/admin/reLogin')
                .then((res) => {
                    let ret = res.data
                    if (ret.state === 'ok') {
                        currentUser.value = undefined
                        me.layer.msg('虚拟用户还原')
                        init();
                        // setTimeout(() => {
                        //     location.reload()
                        // }, 500)
                    } else {
                        me.layer.no(ret.msg)
                    }

                })
                .catch((error) => {
                    me.layer.msg('客户端请求异常: ' + error.message);
                })
        }

        // 退出登录
        const logout = () => {
            location.href = '/user/logout'
        }

        // 虚拟用户还原
        const updatePwd = () => {
            me.layer.open('修改密码', '/user/password', 400, 300, () => {
                me.layer.msg('修改成功')
            })
        }

        const openEovaMsg = () => {
            me.layer.open('系统消息', '/app/eova_msg', 0.9, 0.95, () => {
                me.layer.msg('修改成功')
            })

        }

        // 打开菜单
        const openTab = (m) => {
            // 新窗口打开
            if (m.type == 2) {
                window.open(m.url)
                return
            }
            let old = tabMenus.value.find((o) => o.id == m.id)
            if (old) {
                toTab(old)
            } else {
                tabMenus.value.push(m)
                toTab(m)
            }

            // 关闭菜单分类
            // if (closeMenuCat) {
            //   showAll = false
            // }
        }

        // 关闭当前选项卡
        const closeTab = (m) => {
            // 阻止事件冒泡到, 触发Tab点击事件
            event.stopPropagation()

            // 去掉当前选项
            tabMenus.value = tabMenus.value.filter((o) => o.id != m.id)

            // 切换到最后一个Tab
            let size = tabMenus.value.length
            if (size > 0) {
                toTab(tabMenus.value[size - 1])
            }
        }

        // 切换选项卡
        const toTab = (m) => {
            console.log('切换到:' + m.name)
            tabMenus.value.forEach((o) => {
                o.active = o.id == m.id
            })
        }

        // // 根据标题关闭选项卡
        // const closeTabByName(name) {
        //     let m = tabMenus.value.find((o => o.name == name));
        //     // 去掉当前选项
        //     tabMenus = tabMenus.value.filter(o => o.name != name)
        //     // 如果当前选项是选中状态, 选中最后一个.
        //     if (m.active) {
        //         tabMenus[tabMenus.value.length - 1].active = true;
        //     }
        // }

        // 关闭所有选项卡
        const closeAllTab = () => {
            // 排除首页
            tabMenus.value.splice(1)
            toTab(tabMenus.value[0])
        }

        // 折叠Left菜单面板
        const foldMenu = () => {
            showMenu.value = !showMenu.value
        }

        // 全屏切换
        const toggleFullScreen = () => {
            if (isFull.value) {
                if (document.exitFullscreen) {
                    document.exitFullscreen()
                    isFull.value = false
                }
            } else {
                let ele = document.documentElement,
                    reqFullScreen =
                        ele.requestFullScreen ||
                        ele.webkitRequestFullScreen ||
                        ele.mozRequestFullScreen ||
                        ele.msRequestFullscreen
                if (typeof reqFullScreen !== 'undefined' && reqFullScreen) {
                    reqFullScreen.call(ele)
                    isFull.value = true
                }
            }
        }

        // 刷新页面
        const refresh = () => {
            let menu = tabMenus.value.find((m) => m.active)
            var iframe = document.getElementById('IF' + menu.id)
            try {
                iframe.contentWindow.location.reload(true)
            } catch (e) {
                try {
                    //跨域iframe 刷新
                    const tmpUrl = iframe.src
                    iframe.src = 'about:blank'
                    // 当url 携带#hash参数时， 必须有变化才能刷新
                    const _timeout = setTimeout(function () {
                        iframe.src = tmpUrl
                        console.log('刷新' + tmpUrl)
                        clearTimeout(_timeout)
                    }, 300)
                } catch (e) {
                    console.log(e)
                    console.log('页面刷新失败!')
                }
            }
        }

        const init = () => {
            let url = '/api/home/menu';
            axios.post(url, {}).then(res => {
                let ret = res.data;
                if (ret.state === 'ok') {
                    menus.value = ret.menus;
                    // cats.value = ret.cats;

                    // 使用 Set 来存储有子菜单的 cat.id，避免重复
                    const pids = new Set()
                    menus.value.forEach(m => {
                        if (m.parent_id !== null && m.parent_id >= 0) {
                            pids.add(m.parent_id);
                        }
                    })
                    cats.value = ret.cats.filter(cat => pids.has(cat.id))

                    // 筛选出有子菜单的目录
                    // console.log(cats)

                    // 触发自定义事件，通知Vue已加载完毕
                    nextTick(() => {
                        document.dispatchEvent(new Event('EovaMenuNextTick'));
                    })
                } else {
                    me.layer.msg("加载菜单错误, 请稍候再试");
                }
            }).catch(error => {
                me.layer.msg("请求异常");
            });
        }

        // 在组件挂载完成后设置初始引用并监听 input 事件
        onMounted(() => {
            console.log('home...')


            // popUserRef.value.addChildRef(refSelectItems)
            //
            // if (refSelectItems.value) {
            //     console.log('div元素已挂载:', refSelectItems.value);
            //     // 你可以在mounted钩子或其他地方对这个div进行操作
            // }

            init();
        })

        return {
            // props,
            tabMenus,
            menus,
            cats,
            showMenu,
            isFull,
            currentUser,
            popUserRef,
            refSelectItems,
            hideUserMenu,
            toggleShow,
            switchUser,
            resetUser,
            openTab,
            toTab,
            closeTab,
            closeAllTab,
            foldMenu,
            toggleFullScreen,
            refresh,
            logout,
            updatePwd,
            openEovaMsg

        };
    }
});

// 挂载 Vue 应用
app.use(EovaUI);
app.mount('#app');