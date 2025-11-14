const {createApp, onMounted, onBeforeMount, ref, watch, computed} = Vue;
const {me} = EovaUI
const {x} = EovaTools

const setup = () => {

    // 表单数据
    const data = ref({
        login_id: '',
        login_pwd: '',
        captcha: '',
        msg: ''
    })

    // 登录配置
    const conf = ref({
        is_captcha: false, // 是否显示验证码
        app_name: '', // 应用名
    })

    onBeforeMount(() => {
        console.log("onBeforeMount...")
        uzoo.vue.mountBefore();

    })

    onMounted(() => {
        // console.log("login.js init...")
    })

    // 表单校验规则
    const rules = ref({
        login_id: {label: '账号', rules: ['required']},
        login_pwd: {label: '密码', rules: ['required']},
    })

    const onSubmit = (id) => {

        if (!x.validate.start(rules.value, data.value)) {
            data.value.msg = x.validate.showMsg(rules.value);
            return
        }

        // 更新
        let url = '/user/doLogin'
        axios
            .post(url, data.value)
            .then((res) => {
                // console.log(res)
                let ret = res.data

                console.log(JSON.stringify(ret))

                if (ret.state === 'ok') {
                    // 登录成功 跳转首页
                    location.href = '/'
                } else {
                    data.value.msg = ret.msg
                }

            })
            .catch((error) => {
                data.value.msg = '客户端请求异常'
            })
    }

    return uzoo.app = {
        onSubmit,

        data,
        conf,

    };
}


const app = createApp({
    setup
});
app.use(EovaUI);
app.mount('#app');