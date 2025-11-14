const {createApp, onMounted, onBeforeMount, ref, reactive, watch, computed} = Vue;
const {x} = EovaTools

const setup = () => {

    // Dom Ref
    const refForm = ref()

    const props = uzoo.page;

    // 上传....
    const upload_server = ref()

    // 表单数据
    const data = ref({
        code: '',
        name: '测试',
        type: 'add',
        file: ''
    })

    // 导入模式
    const types = ref([
        {val: 'add', txt: '仅新增数据'},
        {val: 'update', txt: '仅更新数据'},
    ])

    const handleSuccess = (response) => {
        console.log(response)
        // me.layer.msg('上传成功:' + response)
        let ret = JSON.parse(response);

        data.value.file = ret.fileName;
        data.value.old_file = ret.oldFileName;
    }

    const handleError = (error) => {
        console.error(error)
        me.layer.msg('上传失败:' + error)
        data.value.file = '';
    }

    onBeforeMount(() => {
        console.log("app.js onBeforeMount...")
        uzoo.vue.mountBefore();
    })

    onMounted(() => {
        console.log("button/add/app.js onMounted...")
        console.log(data.value)

        // 监听提交通知
        me.cross.on('eova-layer-ok', onSubmit)

    })

    const onSubmit = (id) => {
        if (x.isEmpty(data.value.file)) {
            me.layer.wa('请先上传文件');
            return
        }

        // 更新
        let url = '/excel/doImport'
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
    }

    return uzoo.app = {
        types,

        props,
        refForm,
        onSubmit,

        data,

        upload_server,
        handleSuccess,
        handleError


    };
}

const app = createApp({
    setup
});
app.use(EovaUI);
app.mount('#app');
