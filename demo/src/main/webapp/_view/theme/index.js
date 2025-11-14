
const {createApp, ref, reactive, watch, onMounted, nextTick} = Vue; // 使用全局 Vue 对象


const app = createApp({
    setup() {

        // 主题颜色
        const themeColors = ref([
            {label: '主色', value: 'var(--eova-color_main)', hex: ''},
            {label: '成功', value: 'var(--eova-color_success)', hex: ''},
            {label: '错误', value: 'var(--eova-color_error)', hex: ''},
            {label: '警告', value: 'var(--eova-color_warn)', hex: ''},
            {label: '普通', value: 'var(--eova-color_info)', hex: ''}
        ])

        // 背景颜色
        const backgroundColors = ref([
            {label: '默认背景', value: 'var(--eova-color_bg)', hex: ''},
            {label: '浅色背景', value: 'var(--eova-color_bg1)', hex: ''},
            {label: '中色背景', value: 'var(--eova-color_bg2)', hex: ''},
            {label: '深色背景', value: 'var(--eova-color_bg3)', hex: ''}
        ])

        // 边框颜色
        const borderColor = ref([
            {label: '默认边框', value: 'var(--eova-color_border)', hex: ''},
            {label: '浅色边框', value: 'var(--eova-color_border1)', hex: ''},
            {label: '中色边框', value: 'var(--eova-color_border2)', hex: ''},
            {label: '深色边框', value: 'var(--eova-color_border3)', hex: ''}
        ])

        // 文本颜色
        const textColors = ref([
            {label: '默认文本', value: 'var(--eova-color_txt)', hex: ''},
            {label: '浅色文本', value: 'var(--eova-color_txt1)', hex: ''},
            {label: '中色文本', value: 'var(--eova-color_txt2)', hex: ''},
            {label: '深色文本', value: 'var(--eova-color_txt3)', hex: ''}
        ])

        // 将RGB颜色值转换为十六进制字符串的函数
        const rgb2hex = (rgb) => {
            const [r, g, b] = rgb.match(/\d+/g)?.map(Number) || [0, 0, 0]
            return `#${((1 << 24) + (r << 16) + (g << 8) + b).toString(16).slice(-6).toUpperCase()}`
        }

        onMounted(() => {
            console.log("theme.js init")
            const updateHexValues = (colors) => {
                colors.forEach((color) => {
                    const computedStyle = window.getComputedStyle(
                        document.querySelector(`.color-card[style*="${color.value.replace('var(--', '').replace(')', '')}"]`)
                    )
                    const bgColor = computedStyle.backgroundColor
                    color.hex = rgb2hex(bgColor)
                })
            }

            updateHexValues(themeColors.value)
            updateHexValues(backgroundColors.value)
            updateHexValues(borderColor.value)
            updateHexValues(textColors.value)
            eovaTabJs()
        })

        return {
            themeColors,
            backgroundColors,
            borderColor,
            textColors,
        };
    }
});

// 挂载 Vue 应用
app.mount('#app');

// Tabs 动态切换原生JS实现
function eovaTabJs() {
    // Tabs 动态切换原生JS实现
    document.querySelectorAll('.eova-tab').forEach(function (tabContainer) {
        const tabTitles = tabContainer.querySelectorAll('.eova-tab_title li')
        const tabItems = tabContainer.querySelectorAll('.eova-tab_item')

        tabTitles.forEach(function (tab, index) {
            tab.addEventListener('click', function (e) {
                // 移除所有标签的 'eova-this' 类
                tabTitles.forEach((t) => t.classList.remove('eova-this'))
                // 给当前点击的标签添加 'eova-this' 类
                this.classList.add('eova-this')

                // 移除所有内容项的 'eova-show' 类
                tabItems.forEach((item) => item.classList.remove('eova-show'))

                // 给对应的内容项添加 'eova-show' 类
                if (tabItems[index]) {
                    tabItems[index].classList.add('eova-show')
                }
            })
        })
    })
}