const {createApp, ref, reactive, onMounted} = Vue; // 使用全局 Vue 对象
const {me} = EovaUI
const {x} = EovaTools

const app = createApp({
    setup() {

        const props = uzoo.page;
        // 表单数据
        const data = reactive({
            ...props.fixed
        })

        const refForm = ref()
        const onSubmit = (id) => {

            // 操作成功后 关闭弹窗
            me.cross.emit('eova-layer-ok_done', id)

        }

        // 在组件挂载完成后设置初始引用并监听 input 事件
        onMounted(() => {
            console.log('detail init')

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
            onSubmit,
            onReady,
        }
    }
});

me.vue.created(app);
me.vue.mount(app, uzoo.page.code);