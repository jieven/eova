const {createApp, onMounted, ref, reactive, watch, computed} = Vue;
const { me } = EovaUI
const { x } = EovaTools




const app = createApp({
    setup() {
        const test = ref('测试信息');

        onMounted(() => {
            console.log("menu app init")

        })

        return uzoo.app = {
            test,
        }
    }
});

app.use(EovaUI);
app.mount('#app');