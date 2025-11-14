const {createApp, ref, reactive, watch, onMounted, onBeforeMount, nextTick, provide} = Vue; // 使用全局 Vue 对象
const {me} = EovaUI
const {x} = EovaTools


const app = createApp({
    setup() {
        // 页面配置
        const props = uzoo.page;
        // 表单数据
        const data = reactive({})

        // 菜单配置
        const conf = uzoo.page.menu_conf;
        // 菜单弹窗宽高
        const LW = conf.layer_width || 720, LH = conf.layer_height || 720

        // 参数
        const treeId = ref(0)


        const page = reactive({page: 1, limit: 15})

        // 表格
        const refTable = ref()
        // 查询容器实时高度(默认1行88)
        let queryHeight = ref(0)
        let tableHeight = ref(600)
        // 权限
        const auths = ref()

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

        // Tree配置
        let treeConf = {
            root: conf.root,
            id: conf.id,
            pid: conf.pid,
            title: conf.name,
            icon: conf.icon || '',
            spread: conf.spread || '',
        }
        x.log(treeConf);

        onMounted(() => {
            console.log("tree_table js Mounted")

        })

        const onTeeClick = (node) => {
            // console.log('Click Node:' + JSON.stringify(node))
            me.layer.msg(`选择 ${node.name}`)

            // 获取树查询字段名
            data[conf.tree_query_field] = node.id

            // Tree多选模式SQL条件相对复杂，待后续自定义模版定制
            // form.checks = treeChecked

            // 点击树后 自动进行查询
            onQuery();

        }

        const refTree = ref()
        const refForm = ref()
        const treeChecked = ref([])

        const onAdd = () => {
            let url = x.str.template('/app/add/{{object_code}}', uzoo.page);
            me.layer.open('新增数据', url, LW, LH, () => {
                me.layer.msg('操作成功！')
                onQuery()
            })
        }
        const onUpdate = () => {
            let rows = refTable.value.getSelectRows()
            if (x.isEmpty(rows)) {
                me.layer.msg('请先选择一行数据')
                return
            }

            let url = x.str.template('/app/update/{{object_code}}?id={{id}}', {
                object_code: uzoo.page.object_code, id: rows[0].id
            });
            me.layer.open('修改数据', url, LW, LH, () => {
                me.layer.msg('操作成功！')
                onQuery()
            })
        }
        const onDetail = () => {
            let rows = refTable.value.getSelectRows()
            if (x.isEmpty(rows)) {
                me.layer.msg('请先选择一行数据')
                return
            }

            let url = x.str.template('/app/detail/{{object_code}}?id={{id}}', {
                object_code: uzoo.page.object_code, id: rows[0].id
            });
            me.layer.open('查看数据', url, LW, LH, () => {
            })
        }
        const onDelete = () => {
            let rows = refTable.value.getSelectRows()
            if (x.isEmpty(rows)) {
                me.layer.msg('请先选择数据')
                return
            }

            me.layer.confirm('确认彻底删除, 不可恢复', () => {

                let url = x.str.template(urls.form.delete, uzoo.page)
                axios
                    .post(url, {rows})
                    .then((res) => {
                        let ret = res.data
                        if (ret.state === 'ok') {
                            let pks = rows.map((row) => row[uzoo.page.object_pk])
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
        const onHide = () => {
            let rows = refTable.value.getSelectRows()
            if (x.isEmpty(rows)) {
                me.layer.msg('请先选择数据')
                return
            }

            me.layer.confirm('确认删除', () => {

                let url = x.str.template(urls.form.hide, uzoo.page)
                axios
                    .post(url, {rows})
                    .then((res) => {
                        let ret = res.data
                        if (ret.state === 'ok') {
                            let pks = rows.map((row) => row[uzoo.page.object_pk])
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

        const onImport = () => {
            console.log('导入')
            me.layer.msg('待实现...');
        }


        return uzoo.app = {
            data,
            conf,
            
            auths,
            refTable,
            queryHeight,
            tableHeight,
            page,

            refTree,
            refForm,
            treeId,
            treeConf,
            treeChecked,

            onQuery,
            onTeeClick,
            onAdd,
            onUpdate,
            onDelete,
            onDetail,
            onHide,
            onImport,

            doResize,

        }

    }
});

// 挂载 Vue 应用
app.use(EovaUI);
app.mount('#app');