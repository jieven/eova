const {createApp, ref, reactive, watch, onMounted, nextTick} = Vue; // 使用全局 Vue 对象
const {me} = EovaUI
const {x} = EovaTools

const app = createApp({
    setup() {

        const isEdit = ref(true)

        const refTable = ref()

        const page = reactive({page: 1, limit: 99999, total: 0})

        // 表格 = 窗口 - from 40 - padding10*2
        const tableHeight = ref(x.dom.getViewSize().height - 40 - 20)
        console.log(tableHeight.value)

        const form = reactive({
            object_code: uzoo.page.object_code
        })

        onMounted(() => {
            console.log("cell.js init")
            // query()
        })

        const onQuery = () => {
            console.log('快速查询')
            refTable.value.query(form)
        }

        // 字段排序
        const onReorder = () => {
            me.layer.open('元字段排序', `/meta/reorder?object=${uzoo.page.object_code}`, 250, 0.99, () => {
                me.layer.msg('操作成功！')
                onQuery()
            }, () => {
            }, {
                offset: 'r'
            })
        }

        const onDelete = () => {
            let rows = refTable.value.getSelectRows()
            if (x.isEmpty(rows)) {
                me.layer.msg('请先选择元字段')
                return
            }

            me.layer.confirm('确认彻底删除, 不可恢复', () => {

                let url = x.str.template(urls.form.delete, {
                    object_code: 'eova_field_code'
                })
                axios
                    .post(url, {rows})
                    .then((res) => {
                        let ret = res.data
                        if (ret.state === 'ok') {
                            let pks = rows.map((row) => row["id"])
                            refTable.value.removeRows(pks)
                            me.layer.msg('删除成功')
                        } else {
                            me.layer.msg(ret.msg)
                        }
                    })
                    .catch((e) => {
                        me.layer.msg('客户端请求异常: ' + error.message);
                    })
            })

        }


        // 添加虚拟字段
        const onVirtual = () => {
            me.layer.input('请输入虚拟字段名', 'text', (val) => {
                // me.layer.msg('input:' + val)

                // 兼容元对象与字段
                let code = uzoo.page.object_code;
                let url = `/meta/addVirtualField?object_code=${code}`
                axios.post(url, {input: val})
                    .then((res) => {
                        let ret = res.data
                        if (ret.state === 'ok') {
                            me.layer.msg('同步成功')
                            onQuery()
                        } else {
                            me.layer.no(ret.msg)
                        }
                    })
                    .catch((e) => {
                        me.layer.msg('客户端请求异常: ' + error.message);
                    })

            })
        }

        // 增量导入
        const onSyncnew = () => {
            me.layer.confirm('导入新增字段的元数据', () => {

                    let url = '/meta/syncnew/' + uzoo.page.object_code

                    axios.post(url, {})
                        .then((res) => {
                            let ret = res.data
                            if (ret.state === 'ok') {
                                me.layer.msg('同步成功')
                                onQuery()
                            } else {
                                me.layer.no(ret.msg)
                            }
                        })
                        .catch((e) => {
                            me.layer.msg('客户端请求异常: ' + error.message);
                        })

                }
            )

        }

        // 覆盖同步
        const onOverride = () => {
            me.layer.confirm('删除所有元字段并重新导入', () => {
                    let url = '/meta/override/' + uzoo.page.object_code

                    axios.post(url, {})
                        .then((res) => {
                            let ret = res.data
                            if (ret.state === 'ok') {
                                me.layer.msg('同步成功')
                                onQuery()
                            } else {
                                me.layer.no(ret.msg)
                            }
                        })
                        .catch((e) => {
                            me.layer.msg('客户端请求异常: ' + error.message);
                        })

                }
            )
        }

        return uzoo.app = {
            refTable,
            tableHeight,
            isEdit,
            page,

            onQuery,
            onReorder,
            onDelete,

            onSyncnew,
            onOverride,
            onVirtual
        }
    }
});

// 挂载 Vue 应用
app.use(EovaUI);
app.mount('#app');
