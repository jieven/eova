// 导出元数据
function exportMeta() {
    let ids = uzoo.app.refTable.value.getSelectPks()
    window.open('/meta/doExport/' + ids);
}