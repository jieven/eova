// 功能授权给角色
function auth() {
    let id = uzoo.app.treeId.value;
    if (id === 0) {
        me.layer.msg('请选择一个菜单')
        return
    }
    let url = '/menu/auth/' + id
    me.layer.open('功能授权给角色', url, 0.7, 0.7, () => {
        me.layer.msg('操作成功')
    })

}