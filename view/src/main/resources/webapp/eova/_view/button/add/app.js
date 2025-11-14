const {createApp, onMounted, onBeforeMount, ref, reactive, watch, computed} = Vue;
const {x} = EovaTools


const setup = () => {

    // Dom Ref
    const refForm = ref()

    const props = uzoo.page;

    // 表单数据
    const data = ref({
        menu_code: '',
        num: 1,
        icon: 'eova-icon-ok',
        name: '测试',
        event: 'test',
        style: '',
        ui: '/demo/test/btn.js',
        auth: '/demo/xxx',
        role: null,
    })

    // 表单校验规则
    const rules = ref({
        num: {label: '序号', rules: ['required']},
        icon: {label: '图标', rules: ['required']},
        name: {label: '名称', rules: ['required']},
        event: {label: '事件编码', rules: ['required']},
        ui: {label: '事件逻辑', rules: ['required']},
        auth: {label: '权限配置', rules: ['required']},
    })


    onBeforeMount(() => {
        console.log("button/add/app.js onBeforeMount...")

        uzoo.vue.mountBefore();

    })
    onMounted(() => {
        console.log("button/add/app.js onMounted...")
        console.log(data.value)

        // 监听提交通知
        me.cross.on('eova-layer-ok', onSubmit)

        me.cross.on

        me.cross

        
    })


    const onSubmit = (id) => {
        if (!x.validate.start(rules.value, data.value)) {
            let txt = x.validate.showMsg(rules.value);
            if (txt !== '') me.layer.wa(txt);
            return
        }

        // 更新
        let url = '/button/doAdd'
        axios
            .post(url, data.value)
            .then((res) => {
                // console.log(res)
                let ret = res.data

                console.log(ret)

                if (ret.state === 'ok') {
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

    /**
     * 选择按钮样式
     * @param txt
     */
    const onSelectBtn = (txt) => {
        data.value.style = txt
    }

    return uzoo.app = {
        props,
        refForm,
        onSubmit,
        required,

        onSelectBtn,

        data,

    };
}

const app = createApp({
    setup
});
app.use(EovaUI);
app.mount('#app');
