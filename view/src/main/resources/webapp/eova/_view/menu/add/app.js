const {createApp, onMounted, onBeforeMount, ref, reactive, watch, computed} = Vue;
const {x} = EovaTools


const setup = () => {

    // Dom Ref
    const refForm = ref()

    const props = uzoo.page;

    // 表单数据
    const data = ref({
        parent_id: 0,
        num: 9,
        type: 'app',
        icon: 'eova-icon-app',
        code: 'meta_test_001',
        name: '测试功能1',
        template: 'table',
        url: '',
        objects: [],
    })

    // config: x.json.toStr({
    //     objectCode: 'meta_product'
    // }, 4)

    // 模版配置
    const config = ref({})


    const test = ref('测试信息');

    const types = ref([
        {val: 'app', txt: '应用'},
        {val: 'dir', txt: '目录'},
        {val: 'diy', txt: '链接'},
        {val: 'open', txt: '弹窗'},
    ])

    const templates = ref([])
    const templateProps = ref([])

    // const valueProps = computed(() => {
    //     return templates.value.find(i => i.val === data.value.template)
    // })
    /**
     * 逆向监听
     */
    watch(data.value, (val) => {
        console.log(val)
        if (val.type === 'app') {
            // 默认为单表模版
            // data.value.template = 'table'

        }
    })

    // 模版选择
    watch(() => data.value.template, (val, oldVal) => {
        console.log(`changed from ${oldVal} to ${val}`);
        getTemplateProps(val);
    });

    /**
     * 获取模版配置
     */
    function getTemplateProps(val) {
        let url = `/menu/props/${val}`
        axios
            .post(url, {})
            .then((res) => {
                let ret = res.data

                console.log(ret)

                if (ret.state === 'ok') {
                    templateProps.value = ret.data;
                    // 自动解析值属性为对象
                    templateProps.value.forEach(o => {
                        if (o.props) o.props = x.json.toObj(o.props)

                        // 自动解析默认值
                        config.value[o.key] = o.value
                    })

                    x.log(templateProps.value)
                } else {
                    me.layer.no(ret.msg)
                }

            })
            .catch((error) => {
                me.layer.msg('客户端请求异常: ' + error.message);
            })
    }


    const selectTemplate = computed(() => {
        return templates.value.find(i => i.val === data.value.template)
    })


    const txt = ref('张三')

    onBeforeMount(() => {
        console.log("menu/add/app.js onBeforeMount...")
        // templates.value = [
        //     {val:'table', txt : '单表1'},
        //     {val:'tree', txt : '单树1'},
        // ]

        uzoo.vue.mountBefore();

    })
    onMounted(() => {
        console.log("menu/add/app.js onMounted...")
        console.log(data.value)

        // 获取当前模版参数
        getTemplateProps(data.value.template)

        // 监听提交通知
        me.cross.on('eova-layer-ok', onSubmit)
    })


    const rules = ref({
        type: {label: '类型', rules: ['required']},
        name: {label: '名称', rules: ['required', 'len[2~15]']},
        code: {label: '编码', rules: ['required', 'eovacode']},
        icon: {label: '图标', rules: ['required',]},
        num: {label: '序号', rules: ['required', 'range[1~9999]']},
    })

    const onSubmit = (id) => {
        if (!x.validate.start(rules.value, data.value)) {
            let txt = x.validate.showMsg(rules.value);
            if (txt !== '') me.layer.wa(txt);
            return
        }

        // 更新配置
        data.value.config = x.json.toStr(config.value)
        // 更新
        let url = '/menu/add'
        axios
            .post(url, data.value)
            .then((res) => {
                // console.log(res)
                let ret = res.data

                console.log(ret)

                if (ret.state === 'ok') {
                    parent.uzoo.app.refTree.value.reload();
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

    /**
     * 必填
     * @param field
     */
    const required = (field) => {
        // 表单可编辑 && 字段有校验规则 => 必填
        if (field.required) {
            return 'required'
        }
        return undefined
    }

    return uzoo.app = {
        props,
        refForm,
        onSubmit,
        required,

        data,
        config,

        types,
        templates,
        selectTemplate,
        templateProps,

        test,
        txt,

    };
}

const app = createApp({
    setup
});
app.use(EovaUI);
app.mount('#app');
