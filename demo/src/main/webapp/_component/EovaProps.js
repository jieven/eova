const {defineComponent, watch, ref, computed} = Vue;

export default defineComponent({
    name: 'EovaProps',
    props: {
        modelValue: {
            type: String,
            default: '[]',
        }
    },
    emits: ['update:modelValue'],
    setup(props, ctx) {
        const val_ = ref(props.modelValue)
        
        // 解析JSON参数
        const params = computed(() => {
            try {
                return JSON.parse(val_.value)
            } catch (e) {
                return []
            }
        })

        // 实时更新值
        watch(
            () => props.modelValue,
            (value) => {
                val_.value = value
            }
        )

        // 参数值变化处理
        const handleParamChange = (index, newValue) => {
            const newParams = [...params.value]
            newParams[index].value = newValue
            
            // 更新组件值并触发事件
            val_.value = JSON.stringify(newParams)
            ctx.emit('update:modelValue', val_.value)
        }

        // 根据类型渲染不同的输入控件
        const renderInput = (param, index) => {
            switch (param.type) {
                case 'text':
                    return `<input type="text" 
                           class="eova-props-input" 
                           value="${param.value || ''}" 
                           @input="handleParamChange(${index}, $event.target.value)">`
                case 'number':
                    return `<input type="number" 
                           class="eova-props-input" 
                           value="${param.value || ''}" 
                           @input="handleParamChange(${index}, $event.target.value)">`
                case 'select':
                    const options = param.options || []
                    const optionsHtml = options.map(opt => 
                        `<option value="${opt.value}" ${opt.value === param.value ? 'selected' : ''}>${opt.label}</option>`
                    ).join('')
                    return `<select class="eova-props-select" 
                            @change="handleParamChange(${index}, $event.target.value)">
                            ${optionsHtml}
                            </select>`
                case 'textarea':
                    return `<textarea class="eova-props-textarea" 
                              @input="handleParamChange(${index}, $event.target.value)">${param.value || ''}</textarea>`
                case 'checkbox':
                    return `<input type="checkbox" 
                           class="eova-props-checkbox" 
                           ${param.value ? 'checked' : ''} 
                           @change="handleParamChange(${index}, $event.target.checked)">`
                default:
                    return `<input type="text" 
                           class="eova-props-input" 
                           value="${param.value || ''}" 
                           @input="handleParamChange(${index}, $event.target.value)">`
            }
        }

        return {
            val_,
            params,
            handleParamChange,
            renderInput
        }
    },
    template: `
      <link rel="stylesheet" href="/_component/EovaProps.css">
      <div class="eova-props">
        <div class="eova-props-container">
          <div v-if="params.length === 0" class="eova-props-empty">
            暂无参数配置
          </div>
          <div v-else class="eova-props-list">
            <div v-for="(param, index) in params" :key="index" class="eova-props-item">
              <label class="eova-props-label">{{ param.label || param.name }}</label>
              <div class="eova-props-control" v-html="renderInput(param, index)"></div>
            </div>
          </div>
        </div>
      </div>
    `
})