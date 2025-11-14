const {defineComponent} = Vue;

/**
 * 作者: Qwen3 深度思考 AI
 * 提示词1: 帮我写一个基于Vue3 defineComponent, 独立InputNums.js文件的. 文本框计数器. 就是有个+- 可以调整数字的那种.
 * 提示词2: 帮我重写一下样式, +-号按钮在后面. 叠在一起.
 * input 的样式 height: 30px; border:1px solid #1e9fff 其他按钮 高度 30/2
 */
export default defineComponent({
    name: 'InputNums',
    props: {
        modelValue: {
            type: [Number, String],
            required: true,
        },
        min: {
            type: Number,
            default: 0,
        },
        max: {
            type: Number,
            default: Infinity,
        },
        step: {
            type: Number,
            default: 1,
        },
    },
    emits: ['update:modelValue'],
    setup(props, ctx) {
        // 处理加法逻辑
        const handleIncrement = () => {
            const newValue = Math.min(props.modelValue + props.step, props.max);
            ctx.emit('update:modelValue', newValue);
        };

        // 处理减法逻辑
        const handleDecrement = () => {
            const newValue = Math.max(props.modelValue - props.step, props.min);
            ctx.emit('update:modelValue', newValue);
        };

        return {
            handleIncrement,
            handleDecrement,
        };
    },
    template: `
      <div>
        <div class="input-num">
          <input
              type="number"
              :value="modelValue"
              @input="(e) => emit('update:modelValue', parseFloat(e.target.value) || 0)"
              :step="step"
              :min="min"
              :max="max"
          />
          <div class="btn-group">
            <button
                class="btn-up"
                @click="handleIncrement"
                :disabled="modelValue >= max"
            >+
            </button>
            <button
                class="btn-down"
                @click="handleDecrement"
                :disabled="modelValue <= min"
            >−
            </button>
          </div>
        </div>
      </div>
    `,
});