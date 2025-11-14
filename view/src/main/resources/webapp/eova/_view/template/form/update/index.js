const {createApp, getCurrentInstance, ref, reactive, watch, onMounted, nextTick, defineAsyncComponent} = Vue; // 使用全局 Vue 对象
const {me} = EovaUI
const {x} = EovaTools

const app = createApp({
    setup() {
        // 页面配置
        const props = uzoo.page;
        // 表单数据
        const data = reactive({})

        // 获取页面传参
        // props.pk = x.url.get(props.pk)

        const refForm = ref()

        const onSubmit = (id) => {
            // if (!refForm.value) {
            //   console.log('refForm 不存在')
            //   return
            // }
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
                        // 操作成功后 关闭弹窗
                        me.cross.emit('eova-layer-ok_done', id)
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

        // 在组件挂载完成后设置初始引用并监听 input 事件
        onMounted(() => {
            console.log('form update init')

            // 监听提交通知
            me.cross.on('eova-layer-ok', onSubmit)
        })
        // 表单动态构建完成(DOM构建后)
        const onReady = (fieldInstances) => {
            if (typeof uzoo.vue.onReady === 'function') {
                uzoo.vue.onReady(fieldInstances);
            }
        }

        let data_ = {}
        if (typeof uzoo.vue.setup === 'function') data_ = uzoo.vue.setup();
        return uzoo.app = {
            ...data_,
            props,
            data,
            refForm,
            onReady,
            onSubmit,
        }
    }
});

me.vue.created(app);
me.vue.mount(app, uzoo.page.code);