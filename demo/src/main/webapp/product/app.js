const {onMounted, ref, watch, useTemplateRef} = Vue; // 使用全局 Vue 对象
const {me} = EovaUI
const {x} = EovaTools

uzoo.vue.setup = () => {

    const inputRef = useTemplateRef('inputRef');

    onMounted(() => {
        console.log("hotel app setup onMounted")
        if (inputRef.value) {
            me.layer.ok(inputRef.value.value)
        }
    })

    const txt = ref(123)

    const num = ref(1)
    const field = ref({en: 'test'})

    const rate = ref(0);
    const tags = ref('1,2,3,4,5,6,7,8');

    // 定义变量
    const rangeTime = ref([])

    const confs = me.conf.getAll();
    return {
        confs,
        num,
        rate,
        tags,
        field,
        txt,
        rangeTime
    }
}