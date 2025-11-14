const {createApp, onMounted, ref, watch, computed, nextTick} = Vue;
const {me} = EovaUI
const {x} = EovaTools

const setup = () => {

    const props = uzoo.page;

    const menu = ref();
    const btnCats = ref();
    const roles = ref();
    const auths = ref();


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


    // 初始化勾选状态
    const initCheckedStatus = () => {

        axios
            .post('/menu/authData', {id: props.id})
            .then((res) => {
                let ret = res.data
                console.log(ret)

                if (ret.state === 'ok') {
                    menu.value = ret.menu
                    console.log(menu)
                    btnCats.value = groupByCat(ret.btns)
                    // console.log(btnCats.value)
                    roles.value = ret.roles
                    auths.value = ret.auths

                    // 默认勾选已授权(等待DOM更新后)
                    nextTick(() => {
                        auths.value.forEach(auth => {
                            const checkbox = document.getElementById(`CK_${auth.bid}_${auth.rid}`);
                            if (checkbox) checkbox.checked = true;
                        });
                    })
                } else {
                    me.layer.no(ret.msg)
                }

            })
            .catch((error) => {
                me.layer.msg('客户端请求异常: ' + error.message);
            })


    };

    onMounted(() => {
        // 在组件挂载完成后初始化复选框的状态
        initCheckedStatus();

        // 监听提交通知
        me.cross.on('eova-layer-ok', (id) => {
            me.cross.emit('eova-layer-ok_done', id)
        })
    });

    return uzoo.app = {
        menu,
        btnCats,
        roles,
        auths,
        onAuth,
    };
}


const app = createApp({
    setup
});
app.use(EovaUI);
app.mount('#app');
