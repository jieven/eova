const {createApp, onMounted, onBeforeMount, ref, reactive, watch, computed} = Vue;
const {me} = EovaUI
const {x} = EovaTools

const setup = () => {

    const props = uzoo.page;

    const test = ref('测试信息');

    // 表单数据
    const data = ref({
        ds: '',
        type: 'table',
        name: '测试xxx',
        code: 'test_xxx',
    })

    const rules = ref({
        type: {label: '元类型', rules: ['required']},
        ds: {label: '数据源', rules: ['required']},
        name: {label: '名称', rules: ['required', 'len[2~15]']},
        code: {label: '编码', rules: ['required', 'eova_code']},
    })


    // 数据源
    const dss = ref([])

    const types = ref([
        {
            val: 'table',
            txt: 'table'
        },
        {
            val: 'view',
            txt: 'view'
        }
    ])

    // 表名
    const tables = ref([])

    // 多值监听
    watch(() => [data.value.ds, data.value.type], (val, oldVal) => {
        console.log(`ds changed from ${oldVal} to ${val}`);
        if (data.value.ds && data.value.type) {
            // 获取表
            let url = `/meta/findJson/${data.value.ds}-${data.value.type}`;
            axios.post(url, {}).then((res) => {
                let ret = res.data;
                tables.value.length = 0

                ret.data.forEach(o => {
                    tables.value.push({
                        val: o.table_name,
                        txt: o.table_name
                    })
                })

            }).catch((error) => {
                me.layer.msg('客户端请求异常')
            })
        }
    });


    const selectTemplate = computed(() => {
        return templates.value.find(i => i.val === data.value.template)
    })

    onBeforeMount(() => {
        console.log("app.js onBeforeMount...")
        // templates.value = [
        //     {val:'table', txt : '单表1'},
        //     {val:'tree', txt : '单树1'},
        // ]


        uzoo.vue.mountBefore();

    })

    onMounted(() => {
        console.log("app.js onMounted...")

        // 监听提交通知
        me.cross.on('eova-layer-ok', onSubmit)

    })

    const onSubmit = (id) => {
        if (!x.validate.start(rules.value, data.value)) {
            x.validate.showMsg(rules.value);
            return
        }

        // 更新
        let url = '/meta/doImports'
        axios
            .post(url, data.value)
            .then((res) => {
                // console.log(res)
                let ret = res.data

                console.log(JSON.stringify(ret))

                if (ret.state == 'ok') {
                    // 操作成功后 关闭弹窗
                    me.cross.emit('eova-layer-ok_done', id)
                } else {
                    me.layer.no(ret.msg)
                }

            })
            .catch((error) => {
                me.layer.msg('客户端请求异常: ' + error.message);
            })
        return
    }

    return uzoo.app = {
        test,
        props,
        data,
        rules,

        dss,
        types,
        tables,

        onSubmit,


        selectTemplate,

    };
}


const app = createApp({
    setup
});
app.use(EovaUI);
app.mount('#app');
