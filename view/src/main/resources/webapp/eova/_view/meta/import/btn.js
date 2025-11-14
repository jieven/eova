// 导入元数据
function importMeta() {
    let url = '/meta/imports';
    me.layer.open('导入元数据', url, 400, 580, () => {
        me.layer.msg('操作成功！')
        uzoo.app.refTable.value.query()
    })

}