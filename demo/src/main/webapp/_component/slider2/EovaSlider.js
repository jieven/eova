const {defineComponent, watch, ref} = Vue;

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
        }
    },
    emits: ['update:modelValue'],
    setup(props, ctx) {
        const val_ = ref(props.modelValue);
        const updateValue = (event) => {
            const newValue = parseFloat(event.target.value);
            val_.value = newValue;
            ctx.emit('update:modelValue', newValue);
        };

        watch(
            () => props.modelValue,
            (value) => {
                val_.value = value;
            }
        );

        return {
            val_,
            updateValue
        }
    },
    template: `
      <link rel="stylesheet" href="/EovaSlider/EovaSlider.css">
      <div class="eova-slider">
        <input
            type="range"
            :min="min"
            :max="max"
            :step="step"
            v-model="val_"
            @input="updateValue"
            class="slider-input"
        />
        <div class="value-display">{{ val_ }}</div>
      </div>
    `
})