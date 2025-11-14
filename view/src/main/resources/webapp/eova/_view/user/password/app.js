const {createApp, onMounted, onBeforeMount, ref, reactive, watch, computed} = Vue;
const {x} = EovaTools


const setup = () => {

    // Dom Ref
    const refForm = ref()

    // 表单数据
    const data = ref({
        oldPwd: '',
        newPwd: '',
        confirm: '',
    })

    // 表单校验规则
    const rules = ref({
        oldPwd: {label: '旧密码', rules: ['required']},
        newPwd: {label: '新密码', rules: ['required']},
        confirm: {label: '确认密码', rules: ['required']},
    })

    onMounted(() => {
        // 监听提交通知
        me.cross.on('eova-layer-ok', onSubmit)
    })


    const onSubmit = (id) => {
        if (!x.validate.start(rules.value, data.value)) {
            let txt = x.validate.showMsg(rules.value);
            if (txt !== '') me.layer.wa(txt);
            return
        }

        // 更新
        let url = '/user/doPassword'
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

    return uzoo.app = {
        refForm,
        data,
        rules,
        onSubmit,

    };
}

const app = createApp({
    setup
});
app.use(EovaUI);
app.mount('#app');
