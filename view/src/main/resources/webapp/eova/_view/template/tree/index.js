const {createApp, ref, reactive, watch, onMounted, nextTick, provide} = Vue; // 使用全局 Vue 对象
const {me} = EovaUI
const {x} = EovaTools


const app = createApp({
    setup() {
        // let object_code = null;

        // 续传参数
        const props = uzoo.page;
        // 菜单配置
        const conf = uzoo.page.menu_conf;
        // 弹窗宽高
        const LW = conf.layer_width || 720, LH = conf.layer_height || 720

        // 参数
        const treeId = ref(0)


        const page = reactive({page: 1, limit: 15})

        const form = reactive({})
        // 表格
        const refTable = ref()
        // 查询容器实时高度(默认1行88)
        let queryHeight = ref(0)
        let tableHeight = ref(600)
        // 权限
        const auths = ref()

        // Tree配置
        let treeConf = {
            root: conf.root,
            id: conf.id,
            pid: conf.pid,
            title: conf.name,
            icon: conf.icon || '',
            spread: conf.spread || '',
        }

        // 菜单
        // let treeConf = {
        //     id: 'id',
        //     pid: 'pid',
        //     title: 'name',
        //     spread: '',
        //     root: 0
        // }

        x.log(treeConf);


        onMounted(() => {
            console.log("crud.js init")

            // 获取设置
            // let url = `/api/meta/setting?biz=${uzoo.page.menu_code}`;
            // axios.post(url, {}).then((res) => {
            //     let data = res.data;
            //     console.log("crud setting")
            //     console.log(data)
            //     setting.value = data;
            //     objectCode = data.object_code
            // }).catch((error) => {
            //     me.layer.msg('获取权限异常')
            // })


            // 获取权限
            // let url2 = `/api/meta/auth?biz=${uzoo.page.menu_code}`;
            // axios.post(url2, {}).then((res) => {
            //     let data = res;
            //     console.log("crud auth")
            //     console.log(data)
            //
            //     auths.value = data
            //     // _object.value = res.data.object
            // }).catch((error) => {
            //     me.layer.msg('获取权限异常')
            // })


        })

        const onTeeClick = (node) => {
            // console.log('Click Node:' + JSON.stringify(node))
            me.layer.msg(`编辑 ${node.name}`)

            treeId.value = node.id
        }

        const cityData = [
            {id: 1, pid: 0, name: '中国', lv: 1, spread: true},
            {id: 2, pid: 1, name: '北京', lv: 2},
            {id: 3, pid: 1, name: '上海', lv: 2},
            {id: 4, pid: 1, name: '广州', lv: 2},
            {id: 5, pid: 1, name: '深圳', lv: 2},
            {id: 6, pid: 1, name: '武汉', lv: 2},
            {id: 7, pid: 6, name: '武昌', lv: 3},
            {id: 8, pid: 6, name: '光谷', lv: 3},
            {id: 9, pid: 6, name: '汉口', lv: 3},
            {id: 10, pid: 1, name: '台湾', lv: 2},
            {id: 11, pid: 1, name: '海南', lv: 2}
        ]

        const refTree = ref()
        const refForm = ref()
        const treeChecked = ref([])

        const onAdd = () => {
            let url = x.str.template('/app/add/{{object_code}}?ref={{ref}}', {
                object_code: uzoo.page.object_code,
                // Tree Pid 自动传参.
                ref: `${props.menu_conf.pid}:${treeId.value}`
            });

            me.layer.open('新增数据', url, LW, LH, () => {
                me.layer.msg('操作成功！')
                onQuery()
            })
        }

        const onSave = (id) => {
            if (treeId.value == 0) {
                me.layer.msg('请选择需要编辑的树节点')
                return
            }
            if (refForm.value.validate() == false) {
                return
            }

            // nextTick()
            // 强制等待校验msg 渲染成功

            let data = refForm.value.getData()

            // 更新
            let url = me.urls.url('form_update', props)
            axios
                .post(url, data)
                .then((res) => {
                    // console.log(res)
                    let ret = res.data

                    console.log(JSON.stringify(ret))

                    if (ret.state === 'ok') {
                        refTree.value.reload();
                        me.layer.msg('操作成功！')
                    } else {
                        me.layer.msg(ret.msg)
                        console.log('加载错误')
                    }

                    // buildColumnsByFields()
                    // console.log('fields load ok')
                    // loadDone()
                })
                .catch((error) => {
                    me.layer.msg('客户端请求异常: ' + error.message);
                })
        }


        // TODO 需支持多选...
        const onHide = () => {
            let size = treeChecked.value.length;
            if (size == 0) {
                me.layer.msg('请先勾选需要删除的数据')
                return
            }

            me.layer.confirm('确认删除', () => {
                // let checked = refTree.value.getChecked();
                // me.layer.msg(checked)

                // me.urls.form.hide
                let url = x.str.template('/api/form/hide/{{object_code}}', props)

                // 构造统一rows结构
                // let rows = [{[props.object_pk]: treeId.value }];

                // 构造统一rows结构
                let rows = [];
                treeChecked.value.forEach((id) => {
                    rows.push({[props.object_pk]: id});
                })

                axios
                    .post(url, {rows})
                    .then((res) => {
                        let ret = res.data
                        if (ret.state === 'ok') {
                            refTree.value.reload();
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

        const onDelete = () => {
            let size = treeChecked.value.length;
            if (size == 0) {
                me.layer.msg('请先勾选需要删除的数据')
                return
            }

            me.layer.confirm('确认彻底删除', () => {
                let url = x.str.template(urls.form.delete, props)


                // 构造统一rows结构
                let rows = [];
                treeChecked.value.forEach((id) => {
                    rows.push({[props.object_pk]: id});
                })

                axios
                    .post(url, {rows})
                    .then((res) => {
                        let ret = res.data
                        if (ret.state === 'ok') {
                            refTree.value.reload();
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

        const onQuery = () => {
            console.log('快速查询')
            refTree.value.reload();
        }

        const onImport = () => {
            console.log('导入')
            me.layer.msg('待实现...');
        }

        const json = ref(`{"objectCode":"goods_style","params":{}}`)


        return uzoo.app = {
            conf,

            auths,
            refTable,
            queryHeight,
            tableHeight,
            page,
            form,

            json,

            cityData,


            refTree,
            refForm,
            treeId,
            treeConf,
            treeChecked,

            onQuery,
            onAdd,
            onTeeClick,
            onSave,
            onDelete,
            onHide,
            onImport,

        }

    }
});

// 挂载 Vue 应用
app.use(EovaUI);
// 业务编码=模版类型_菜单编码
me.vue.mount(app, `${uzoo.page.template}_${uzoo.page.code}`);