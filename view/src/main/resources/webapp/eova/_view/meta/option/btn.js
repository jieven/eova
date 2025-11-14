// 刷新表达式
function option() {
    let rows = uzoo.app.refTable.value.getSelectRows()
    if (x.isEmpty(rows)) {
        me.layer.msg('请先选择一行数据')
        return
    }

    let code = rows[0].code

    window.open('/meta/option/' + code);
}