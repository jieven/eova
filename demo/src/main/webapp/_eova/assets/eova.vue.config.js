/**
 * Eova Vue 配置中心
 * 可以在此进行Vue相关的配置
 * 注册组件
 * 配置扩展
 */
const {vue} = EovaUI.me

// 全局组件注册
vue.component('MyDemo', '/_component/MyDemo.js');// 最简组件定义案例
vue.component('SwordComing', '/_component/SwordComing.js');// 复杂组件定义案例
vue.component('InputNum', '/_component/InputNum.js');// 计数器(丐版)
vue.component('InputNums', '/_component/InputNums.js');// 计数器(AI版)
vue.component('Rate', '/_component/Rate.js');// 评分组件(AI版)
vue.component('EovaTags', '/_component/EovaTags.js');// 评分组件(AI版)

// 自定义模版(模版类型_菜单编码) 干预列表页的查询表单
vue.app('table_meta_hotel', {
    template: '/hotel/app.vue',
    script: '/hotel/app.js',
    components: ['SwordComing']
});

// 自定义表单(元对象编码) 干预新增,修改, 查看的表单
vue.app('meta_hotel', {
    template: '/hotel/app.vue',
    script: '/hotel/app.js'
});

// 自定义表单(元对象编码)
vue.app('meta_product', {
    template: '/product/app.vue',
    script: '/product/app.js',
    components: ['MyDemo', 'InputNum', 'InputNums', 'EovaTags']
});