// 导入数据
function imports() {
    me.layer.open('导入数据', '/excel/imports/sys_hotel', 700, 630, () => {
        me.layer.msg('导入执行中，请查看导入记录！')
        // 刷新表格
        uzoo.app.refTable.value.query();
    })
}