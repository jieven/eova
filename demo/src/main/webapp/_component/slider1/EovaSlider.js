const { defineComponent, watch, ref } = Vue;

/**
 * EovaSlider 组件定义
 * 
 * 横向滑块组件，用于通过滑动控制数字值的增减
 * 支持最小值、最大值、步长配置，可自定义样式
 */
export default defineComponent({
    name: 'EovaSlider',
    props: {
        modelValue: {
            type: Number,
            default: 0
        },
        min: {
            type: Number,
            default: 0
        },
        max: {
            type: Number,
            default: 100
        },
        step: {
            type: Number,
            default: 1
        },
        disabled: {
            type: Boolean,
            default: false
        },
        showValue: {
            type: Boolean,
            default: true
        }
    },
    emits: ['update:modelValue', 'change'],
    setup(props, ctx) {
        // 组件核心值保持如下命名
        const val_ = ref(props.modelValue);

        // 实时更新值 - 当外部修改modelValue时同步到内部状态
        watch(
            () => props.modelValue,
            (value) => {
                val_.value = value;
            }
        );

        // 滑块值变化处理
        const handleSlide = (event) => {
            if (props.disabled) return;
            
            const slider = event.target;
            let newValue = parseFloat(slider.value);
            
            // 确保值在范围内
            newValue = Math.max(props.min, Math.min(props.max, newValue));
            
            // 按步长对齐
            const roundedValue = Math.round(newValue / props.step) * props.step;
            val_.value = roundedValue;
            
            // 触发更新事件
            ctx.emit('update:modelValue', roundedValue);
            ctx.emit('change', roundedValue);
        };

        // 获取滑块的百分比位置（用于样式显示）
        const getSliderPercentage = () => {
            if (props.max === props.min) return 0;
            return ((val_.value - props.min) / (props.max - props.min)) * 100;
        };

        // 滑块样式计算
        const sliderStyle = () => {
            return {
                '--slider-value': getSliderPercentage() + '%'
            };
        };

        // 模版所需变量
        return {
            val_,
            handleSlide,
            sliderStyle
        };
    },
    // 实现组件的模版代码(样式文件需要在模版中引入)
    template: `
      <link rel="stylesheet" href="/_component/EovaSlider.css">
      <div class="eova-slider-container">
        <div class="eova-slider-wrapper">
          <input 
            type="range" 
            class="eova-slider" 
            :min="min" 
            :max="max" 
            :step="step" 
            :value="val_" 
            :disabled="disabled"
            @input="handleSlide"
            :style="sliderStyle()"
          >
          <div class="eova-slider-track"></div>
        </div>
        <div class="eova-slider-value" v-if="showValue">
          {{ val_ }}
        </div>
      </div>
    `
});