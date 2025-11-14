const {createApp, ref, reactive, watch, onMounted, nextTick} = Vue; // 使用全局 Vue 对象


const app = createApp({
    setup() {


        return {};
    }
});

// 挂载 Vue 应用
app.use(EovaUI);
app.mount('#app');
