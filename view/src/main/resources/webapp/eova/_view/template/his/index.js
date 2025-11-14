

// 添加自定义方法到Vue实例
// window.uzoo.app.config.globalProperties.test = function() {
//     alert('这是一个自定义方法');
// };
//
// const test = () => {
//     me.layer.msg('测试方法')
// }

//
// const update = () => {
//     let rows = refTable.value.getSelectRows()
//     if (x.isEmpty(rows)) {
//         me.layer.msg('请先选择一行数据')
//         return
//     }
//
//     let url = x.str.template('/update?object={{object}}&id={{id}}', {
//         object: uzoo.page.object_code, id: rows[0].id
//     });
//     me.layer.open('修改数据', url, () => {
//         onQuery()
//         me.layer.msg('操作成功！')
//     })
// }