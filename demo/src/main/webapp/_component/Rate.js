const {defineComponent, watch, ref} = Vue;

export default defineComponent({
    name: 'Rate',
    props: {
        modelValue: {
            type: Number,
            default: 0,
        },
        size: {
            type: String,
            default: '1em'
        },
        color: {
            type: String,
            default: 'var(--eova-color_main)'
        },
    },
    emits: ['update:modelValue'],
    setup(props, {emit}) {
        const currentRating = ref(props.modelValue)

        // 监听 props.modelValue 变化，同步内部状态
        watch(
            () => props.modelValue,
            (value) => {
                currentRating.value = value
            }
        )

        const handleStarClick = (index) => {
            const rating = index + 1
            currentRating.value = rating
            emit('update:modelValue', rating)
        }

        const isStarSelected = (index) => {
            if (currentRating.value === 0) return false;
            return currentRating.value > index
        }

        return {
            currentRating,
            handleStarClick,
            isStarSelected
        }
    },
    template: `
      <div class="rate-container">
      <span
          v-for="n in 5"
          :key="n"
          class="star"
          :style="{ fontSize: size, cursor: 'pointer', color: color}"
          @click="handleStarClick(n - 1)"
      >
        <i :class="isStarSelected((n - 1)) ? 'eova-icon-rate-solid' : 'eova-icon-rate'"></i>
      </span>
      </div>
    `
})