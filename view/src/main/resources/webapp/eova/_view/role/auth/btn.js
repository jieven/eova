// 角色功能权限分配
function auth() {

    let rows = uzoo.app.refTable.value.getSelectRows()
    if (x.isEmpty(rows)) {
        me.layer.msg('请选择一个角色')
        return
    }
 
    let url = '/auth/' + rows[0].id
    me.layer.open('角色功能权限分配', url, 0.9, 0.9, () => {
        me.layer.msg('操作成功')
    })

}