const {defineComponent, watch, ref} = Vue; // 使用全局 Vue 对象
const {me} = EovaUI

/**
 * 计数器组件
 */
const InputNum = defineComponent({
    name: 'InputNum',
    template: `
      <div style="display: flex">
        <ev-input v-model="val"></ev-input>
        <button class="eova-btn eova-btn_success" @click="plus()">➕</button>
        <button class="eova-btn eova-btn_success" @click="sub()">➖</button>
      </div>
      <!--      <div class="vm-data">-->
      <!--        字段属性:-->
      <!--        <pre>{{ field }}</pre>-->
      <!--      </div>-->
    `,
    props: {
        modelValue: {
            type: Number,
            required: true
        },
        msg: {// 双向绑定变量，方便内外控制显示/隐藏
            type: String,
            required: false,
        },
        field: {
            type: Object,
            required: false
        },

    },
    emit: ['update:modelValue'],
    setup(props, ctx) {
        const val = ref(props.modelValue || 0)
        /**
         * 出参数实时更新 <==
         */
        watch(val, (v) => {
            console.log('值变化:' + v)
            ctx.emit('update:modelValue', v)
            // 反向PUA
        })

        /**
         * +1
         */
        const plus = () => {
            val.value++
        }
        /**
         * -1
         */
        const sub = () => {
            val.value--
        }
        return {
            val,
            plus,
            sub
        };
    }
});

export default InputNum;