const onUpdate = () => {
    let rows = uzoo.app.refTable.value.getSelectRows()
    if (x.isEmpty(rows)) {
        me.layer.msg('请先选择一行数据')
        return
    }

    let url = x.str.template('/app/update/{{object}}?id={{id}}', {
        object: uzoo.page.object_code, id: rows[0].id
    });
    me.layer.open('修改数据', url, () => {
        onQuery()
        me.layer.msg('操作成功！')
    })
}