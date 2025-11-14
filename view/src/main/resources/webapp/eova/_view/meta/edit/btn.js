// 编辑元数据
function edit() {
    let rows = uzoo.app.refTable.value.getSelectRows()
    if (x.isEmpty(rows)) {
        me.layer.msg('请先选择一行数据')
        return
    }

    let code = rows[0].code
    window.open(`/meta/edit?object=${code}`)
}