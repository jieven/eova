const {createApp, ref, reactive, watch, onMounted} = Vue; // 使用全局 Vue 对象
const {me} = EovaUI
const {x} = EovaTools

const app = createApp({
    setup() {
        // 表单数据
        const data = reactive({})

        const page = reactive({page: 1, limit: 15})

        // 表格
        const refTable = ref()
        // 查询容器实时高度(默认1行88)
        let queryHeight = ref(0)
        let tableHeight = ref(500)

        // 切换后的用户信息
        const SU = ref()

        const onQuery = () => {
            console.log('快速查询')
            refTable.value.query(data)
        }

        // 查询表单大小变化
        const doResize = (height) => {
            queryHeight.value = height
            // 总高度 - form - 间距
            tableHeight.value = x.dom.getViewSize().height - height - 30
            console.log('form resize:' + height)
        }

        const onSubmit = (id) => {
            let rows = refTable.value.getSelectRows()
            if (x.isEmpty(rows)) {
                me.layer.msg('请选择一个用户')
                return
            }
            let user = rows[0];
            // 更新
            let url = '/eova/admin/doSu'
            axios.post(url, user)
                .then((res) => {
                    // console.log(res)
                    let ret = res.data

                    console.log(user)

                    if (ret.state === 'ok') {
                        // 发送数据
                        me.cross.emit('eova-layer-ok_done_data', `${user.rid}【${user.name}】`)
                        // 操作成功后 关闭弹窗
                        me.cross.emit('eova-layer-ok_done', id)
                    } else {
                        me.layer.no(ret.msg)
                    }

                })
                .catch((error) => {
                    me.layer.msg('客户端请求异常: ' + error.message);
                })
        }

        onMounted(() => {
            console.log("su/app.js onMounted...")

            // 监听提交通知
            me.cross.on('eova-layer-ok', onSubmit)
        })

        return uzoo.app = {
            data,
            refTable,
            queryHeight,
            tableHeight,
            page,
            onQuery,
            doResize,
        }

    }
});

// 挂载 Vue 应用
app.use(EovaUI);
app.mount('#app');