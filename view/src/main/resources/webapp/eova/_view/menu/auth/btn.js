// 功能授权给角色
function auth() {
    const id = uzoo.app.treeId.value
    if (!id) {
        me.layer.msg('请选择一个菜单')
        return
    }

    import('/eova/_view/menu/auth/app.js').then(app => app.open(id))// 可以直接任意传参
}
