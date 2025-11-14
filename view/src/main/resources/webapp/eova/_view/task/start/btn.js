// 启用任务
function start() {

    let url = '/task/start';
    let rows = uzoo.app.refTable.value.getSelectRows()
    if (x.isEmpty(rows)) {
        me.layer.msg('请先选择一行数据')
        return
    }
    // let clazz = rows[0].clazz
    axios.post(url, rows[0])
        .then((res) => {
            let ret = res.data
            if (ret.state === 'ok') {
                me.layer.msg('启用成功')

                uzoo.app.refTable.value.query();
            } else {
                me.layer.no(ret.msg)
            }

        })
        .catch((error) => {
            me.layer.msg('客户端请求异常: ' + error.message);
        })

}