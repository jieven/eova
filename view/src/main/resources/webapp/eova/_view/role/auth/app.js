const {createApp, onMounted, ref, watch, computed, nextTick} = Vue;
const {me} = EovaUI
const {x} = EovaTools

const setup = () => {

    const props = uzoo.page;

    // 可分配功能
    const menus = ref();
    // 可分配按钮
    const btns = ref();
    // 继承角色
    const extendRids = ref([]);

    // const btnCats = ref();
    // const roles = ref();
    // const auths = ref();


    function groupByCat(array) {
        return array.reduce((acc, item) => {
            if (!acc[item.cat]) {
                acc[item.cat] = [];
            }
            acc[item.cat].push(item);
            return acc;
        }, {});
    }

    //  授权
    const onAuth = (event, bid, rid) => {
        const status = event.target.checked;
        console.log('Bid:', bid, 'Rid:', rid, 'Status:', status);
        axios
            .post('/auth/update', {
                is_check: status,
                bid: bid,
                rid: rid
            })
            .then((res) => {
                let ret = res.data
                console.log(ret)

                if (ret.state === 'ok') {
                    me.layer.msg('授权成功')
                } else {
                    me.layer.no(ret.msg)
                }

            })
            .catch((error) => {
                me.layer.msg('客户端请求异常: ' + error.message);
            })
    };


    const sptBtnName = (str) => {
        if (str) {
            return str.split(',');
        }
        return [];
    }

    // 全选/反选
    const onSelectAll = (status) => {
        btns.value.forEach(b => {
            b.checked = status
        })
    }

    // 批量选中所有的XX功能
    const onSelectType = (name) => {
        btns.value.filter(b => {
            if (b.name === name) b.checked = true
        })
    }

    // 选中菜单功能
    const onSelectMenu = (menuCode, status) => {
        btns.value.filter(b => {
            if (b.menu_code === menuCode) b.checked = !status
        })
    }

    onMounted(() => {
        init();

        // 监听提交通知
        me.cross.on('eova-layer-ok', onSubmit)
    });

    /**
     * 初始化获取授权数据
     */
    const init = () => {
        axios
            .post('/auth/data', {rid: props.rid})
            .then((res) => {
                let ret = res.data
                console.log(ret)

                if (ret.state === 'ok') {
                    menus.value = ret.menus
                    btns.value = ret.btns
                } else {
                    me.layer.no(ret.msg)
                }

            })
            .catch((error) => {
                me.layer.msg('客户端请求异常: ' + error.message);
            })
    };

    /**
     * 确认提交
     * @param id
     */
    const onSubmit = (id) => {
        // 更新
        let url = '/auth/doAuth'

        // 勾选的按钮ID
        let authBtns = btns.value
            .filter(b => b.checked) // 筛选出checked为true的按钮
            .map(b => b.id) // 提取每个按钮的id
            .join(',');

        axios
            .post(url, {
                rid: props.rid,
                auth_btns: authBtns
            })
            .then((res) => {
                // console.log(res)
                let ret = res.data

                console.log(ret)

                if (ret.state === 'ok') {
                    //parent.uzoo.app.refTable.value.reload();
                    // 操作成功后 关闭弹窗
                    me.cross.emit('eova-layer-ok_done', id)
                } else {
                    me.layer.no(ret.msg)
                }

            })
            .catch((e) => {
                console.log(e)
                me.layer.msg('客户端请求异常: ' + e.message);
            })
        // me.layer.msg('update auth')
    }


    return uzoo.app = {
        menus,
        btns,
        extendRids,
        // roles,
        sptBtnName,
        onAuth,
        onSelectAll,
        onSelectMenu,
        onSelectType
    };
}


const app = createApp({
    setup
});
app.use(EovaUI);
app.mount('#app');
