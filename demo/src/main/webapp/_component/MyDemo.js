const {defineComponent, computed, ref} = Vue; // 使用全局 Vue 对象
const {me} = EovaUI

/**
 * 注册组件后，在html中使用
 * <ev-demo v-model="选中行"></ev-demo>
 * （Vue3自定义组件名在使用时需将驼峰转为xxx-xxx）
 */
const MyDemo = defineComponent({
    template: `
      <div style="position: fixed; top: 40px; right: 1px; width: 400px; z-index: 999;background-color: #f5f5f5; border: 1px solid #878484;padding: 10px">
        <div>
          当前选中数据:
          {{ modelValue }}
          <textarea v-model="text" placeholder="请输入内容" style="width: 90%;"></textarea>
          <p>字数: {{ wordCount }}</p>
          <button @click="onYes()">同步输入值到表单</button>
        </div>
      </div>
    `,
    props: {
        modelValue: { // 自定义参数，传入对象
            type: Object,
            required: true
        },
    },
    setup(props, ctx) {
        const text = ref('');
        const wordCount = computed(() => text.value.length);
        const onYes = () => {
            uzoo.app.data.mydemo = text.value
            me.layer.msg('给你一个支点， 你能翘起地球！' + text.value)
        }
        return {text, wordCount, onYes};
    }
});

// 导出组件
export default MyDemo;