const {createApp, ref, reactive, watch, onMounted, onBeforeMount, nextTick} = Vue; // 使用全局 Vue 对象
const {me} = EovaUI
const {x} = EovaTools

const app = createApp({
    setup() {
        const currTab = ref('')

        const isEdit = ref(true)

        const tableRef = ref()

        // 子表分页 （limit > 99999 不显示分页组件）
        const page = reactive({page: 1, limit: 99999})

        // TODO 下拉框获取的值是数组, 需要进行转换处理.
        const tableHeight = ref(x.dom.getViewSize().height - 65)
        console.log(tableHeight)

        const form = reactive({
            object_code: uzoo.page.object_code
        })

        const query = () => {
            tableRef.value.query(form)
        }

        onBeforeMount(() => {
            console.log("app.js onBeforeMount...")
            uzoo.vue.mountBefore();
        })
        onMounted(() => {
        })

        return uzoo.app = {
            currTab,
            tableRef,
            tableHeight,
            isEdit,
            page,
            query,
        };
    }
});

// 挂载 Vue 应用
app.use(EovaUI);
app.mount('#app');
