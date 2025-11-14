const {createApp, ref, reactive, watch, onMounted, onBeforeMount, nextTick} = Vue; // 使用全局 Vue 对象
const {me} = EovaUI
const {x} = EovaTools

const app = createApp({
    setup() {

        const data = ref([])

        onBeforeMount(() => {
            console.log("app.js onBeforeMount...")
            uzoo.vue.mountBefore();
        })

        onMounted(() => {
            // 监听提交通知
            me.cross.on('eova-layer-ok', onSubmit)
        })

        // const init = () => {
        //     console.log('快速查询')
        //     refTable.value.query(form)
        //
        //     axios.post('/meta/reorder_data', {
        //         object: ''
        //     }).then((res) => {
        //         let ret = res.data;
        //         tables.value.length = 0
        //
        //         ret.data.forEach(o => {
        //             tables.value.push({
        //                 val: o.table_name,
        //                 txt: o.table_name
        //             })
        //         })
        //
        //     }).catch((error) => {
        //         me.layer.msg('客户端请求异常')
        //     })
        // }

        const onSubmit = (id) => {

            // 更新
            let url = `/meta/updateReorder?biz=${uzoo.page.biz}&mode=${uzoo.page.mode}`;
            axios
                .post(url, data.value)
                .then((res) => {
                    // console.log(res)
                    let ret = res.data

                    console.log(JSON.stringify(ret))

                    if (ret.state === 'ok') {
                        // 操作成功后 关闭弹窗
                        me.cross.emit('eova-layer-ok_done', id)
                    } else {
                        me.layer.msg(ret.msg)
                        console.log('加载错误')
                    }
                })
                .catch((error) => {
                    me.layer.msg('客户端请求异常: ' + error.message);
                })

        }

        return uzoo.app = {
            data,
        }
    }
});

// 挂载 Vue 应用
app.use(EovaUI);
app.mount('#app');
