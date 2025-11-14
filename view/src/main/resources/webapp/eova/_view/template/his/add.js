const onAdd = () => {
    let url = x.str.template('/app/add?object={{object}}', {
        object: uzoo.page.object_code
    });
    me.layer.open('新增数据', url, () => {
        onQuery()
        me.layer.msg('操作成功！')
    })
}