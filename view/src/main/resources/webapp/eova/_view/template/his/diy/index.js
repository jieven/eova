const {createApp, ref, reactive, watch, onMounted, nextTick, provide} = Vue; // 使用全局 Vue 对象
const {me} = EovaUI
const {x} = EovaTools

// 子表1~子表N
const refTable1 = ref()
const refTable2 = ref()
// 显示子表
const showSubTable = ref(false)
// 子表高度 动态计算:总高 - bottom - top - margin   PS:之前这块被平台锁死， 导致不灵活， 现在完成开放， 自行计算。结合Vue响应式，效果自行脑补。
let table1Height = x.dom.getViewSize().height - 50 - 100 - 15 - 300 - 30;
// 子表分页 （limit > 99999 不显示分页组件）
const table1Page = reactive({page: 1, limit: 99999})

// 当前选中行
const currentRow = ref({});

// 行点击事件（实现主子的核心API， 主表点击事件处理 @row-click="onRowClick"）
const onRowClick = (row, event) => {
    console.log(row)

    currentRow.value = row;
    me.layer.msg(currentRow.value.name)

    // 子表1重新查询
    refTable1.value.query({
        hotel_id: currentRow.value.id
    });
    // 子表2重新查询
    refTable2.value.query({
        hotel_id: currentRow.value.id
    });
    // 单个子和多子没什么区别，主要在于通过外键联动和界面展示样式。

    showSubTable.value = true;
}
const onRowClick1 = (row, event) => {
    alert(1111)
}

// function useFeature2() {
//     return {
//         feature2Data: 'Feature 2 Data'
//     };
// }
//
// export function init() {
//     console.log('用户自定义模块已加载并执行');
//     return "index2.js"
// }

// 监听提交通知
// me.cross.on('eova-table-row_click', (id) => {
//     alert('table click..:' + id)
//     onRowClick()
// })


let num = 10086
const msg = ref('Hello from ESM module')
export const app = {
    msg,
    num,
    onRowClick1
}