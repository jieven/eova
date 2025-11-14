const {defineComponent, watch, ref, computed, onMounted} = Vue;

export default defineComponent({
    name: 'EovaTags',
    props: {
        modelValue: {
            type: String,
            default: ''
        }
    },
    emits: ['update:modelValue'],
    setup(props, ctx) {
        const val_ = ref(props.modelValue)
        // 所有列表数据
        const data = ref([])

        // 实时渲染当前选中值
        const tags_ = computed(() => {
            let arrs = data.value.filter(o => val_.value.indexOf(o.val) !== -1);
            return arrs;
        });

        onMounted(() => {

            // 动态数据获取.
            axios.get('/api/widget/data?option=eova_org')
                .then(function (res) {
                    let ret = res.data;
                    data.value = ret.data

                    // updateTags();
                })
                .catch(function (error) {
                    me.layer.msg('客户端请求异常: ' + error.message);
                })
        })

        watch(
            () => val_.value,
            (value) => {
                val_.value = value
                // updateTags()
            }
        )

        const removeTag = (index) => {
            // 获取原始数字值
            const originalTags = val_.value.split(',').filter(tag => tag.trim() !== '')
            originalTags.splice(index, 1)

            const newValue = originalTags.join(',')
            val_.value = newValue
            ctx.emit('update:modelValue', newValue)
        }

        // 添加数据
        const addTag = () => {
            // 获取原始数字值
            val_.value = val_.value += ",11"

            // 弹出选择数据 进行添加.

            ctx.emit('update:modelValue', val_.value)
        }

        return {
            val_,
            tags_,
            removeTag,
            addTag
        }
    },
    template: `
      <link rel="stylesheet" href="/_component/EovaTags.css">
      <div class="tags-container">
        <span
            v-for="(tag, index) in tags_"
            :key="index"
            class="eova-badge eova-badge_tag"
        >
            {{ tag.txt }}[{{ tag.val }}]
          <i class="eova-icon-close" @click="removeTag(index)"></i>
        </span>
        <button>
          <i class="eova-icon-add-1" @click="addTag()"></i>
        </button>
      </div>
    `
})