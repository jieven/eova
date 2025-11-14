// 导出菜单脚本
function exports() {
    let ids = uzoo.app.treeChecked.value;
    if(ids.length === 0) {
        me.layer.msg('请勾选菜单')
        return
    }

    window.open('/menu/doExport/' + ids.join(','));

}