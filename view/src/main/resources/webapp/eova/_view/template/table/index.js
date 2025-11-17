const {createApp, ref, reactive, watch, onMounted, nextTick, computed, defineAsyncComponent} = Vue; // 使用全局 Vue 对象
const {me} = EovaUI
const {x} = EovaTools

// import OBJ from 'util.js';
//
// console.log(OBJ.msg)

const app = createApp({
    setup() {

        const setting = ref({})

        // 菜单配置
        const conf = uzoo.page.menu_conf;
        // 弹窗宽高
        const LW = conf.layer_width || 720, LH = conf.layer_height || 660

        const page = reactive({page: 1, limit: 15})

        const data = reactive({})
        // 表格
        const refTable = ref()
        // 表单
        const refForm = ref()
        // 查询容器实时高度(默认1行88)
        let queryHeight = ref(0)
        let tableHeight = ref(600)
        // 权限
        const auths = ref()

        const onQuery = () => {
            refTable.value.query(data)

            // 查询后关闭级联显示
            showLinking.value = false;
        }

        // 查询表单大小变化
        // const doResize = (height) => {
        //     queryHeight.value = height
        //     // 总高度 - form - 间距
        //     tableHeight.value = window.innerHeight - height - 30
        //     console.log('form resize:' + height)
        // }

        // 查询表单大小变化
        const doResize = (height) => {
            nextTick(() => {
                queryHeight.value = height
                // 总高度 - form - 间距
                tableHeight.value = x.dom.getViewSize().height - height - 30
                console.log('form resize:' + height)
            })
        }
        // 表单动态构建完成(DOM构建后)
        const onReady = (fieldInstances) => {
            if (typeof uzoo.vue.onReady === 'function') {
                uzoo.vue.onReady(fieldInstances);
            }
        }

        const onAdd = () => {
            let url = x.str.template('/app/add/{{object_code}}?biz={{code}}', uzoo.page);
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

            let url = x.str.template('/app/update/{{object_code}}?id={{id}}&biz={{menu_code}}', {
                object_code: uzoo.page.object_code,
                menu_code: uzoo.page.code,
                id: rows[0].id
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

            let url = x.str.template('/app/detail/{{object_code}}?id={{id}}&biz={{menu_code}}', {
                object_code: uzoo.page.object_code,
                menu_code: uzoo.page.code,
                id: rows[0].id
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

        const submitForm = (url, data) => {
            // 创建一个隐藏的 <form> 元素
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = url;
            form.target = '_blank'; // 如果需要在新标签页打开

            // 添加表单字段
            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = data.name;
            input.value = data.value;
            form.appendChild(input);

            // 将 <form> 添加到 DOM 中
            document.body.appendChild(form);

            // 提交表单
            form.submit();

            // 移除 <form>
            document.body.removeChild(form);
        };

        const onExport = (type) => {
            let url = `/excel/export/${uzoo.page.object_code}?type=${type}&biz=${uzoo.page.menu_code || ''}`;
            let fileName = `${uzoo.page.object_name}.${type}`
            x.axios.download(url, data, fileName, type);
            // window.open(url)
        }

        const onImport = () => {
            console.log('导入')
            me.layer.msg('待实现...');
        }


        // 子表1~子表N
        const refTable1 = ref()
        const refTable2 = ref()
        // 显示子表
        const showLinking = ref(false)
        // 子表高度 动态计算:总高 - bottom - top - margin   PS:之前这块被平台锁死， 导致不灵活， 现在完成开放， 自行计算。结合Vue响应式，效果自行脑补。
        let table1Height = x.dom.getViewSize().height - 50 - 100 - 15 - 300 - 30;
        // 子表分页 （limit > 99999 不显示分页组件）
        const table1Page = reactive({page: 1, limit: 99999})

        // 当前选中行
        const currentRow = ref({});

        // 行点击事件（实现主子的核心API， 主表点击事件处理 @row-click="onRowClick"）
        const onRowClick = (row, event) => {
            console.log(row)

            currentRow.value = row;

            // // 子表1重新查询
            // refTable1.value.query({
            //     hotel_id: currentRow.value.id
            // });
            // // 子表2重新查询
            // refTable2.value.query({
            //     hotel_id: currentRow.value.id
            // });
            // // 单个子和多子没什么区别，主要在于通过外键联动和界面展示样式。

            showLinking.value = true;

            // 监听提交通知
            me.cross.emit('eova-table-row_click', currentRow.value.id)
        }

        onMounted(() => {
            console.log("onMounted")


            // console.log(uzoo.page.conf)


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

        let data_ = {}
        if (typeof uzoo.vue.setup === 'function') data_ = uzoo.vue.setup();
        return uzoo.app = {
            ...data_,

            data,

            auths,
            refForm,
            refTable,
            queryHeight,
            tableHeight,
            page,

            currentRow,

            onQuery,
            onAdd,
            onUpdate,
            onDetail,
            onHide,
            onDelete,
            onImport,
            onExport,

            doResize,
            onReady,

            onRowClick,
            showLinking,
        }

    },
    components: {
        // EvDemo,// 固定组件
        // [diyComponent]: defineAsyncComponent(() =>
        //     import(`./components/${diyComponent}.js`)
        // )
    }
});

// 挂载 Vue 应用
app.use(EovaUI);
// me.vue.created(app);
// 业务编码=模版类型_菜单编码
me.vue.mount(app, `${uzoo.page.template}_${uzoo.page.code}`);