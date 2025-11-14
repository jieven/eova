// useCounter.js
const {createApp, ref, reactive, watch, onMounted, nextTick} = Vue; // 使用全局 Vue 对象

export function module_demo() {
    const count = ref(0);

    // 表格
    const refForm = ref()

    const increment = () => {
        count.value++;
    };

    const decrement = () => {
        count.value--;
    };

    return { count, increment, decrement, refForm };
}