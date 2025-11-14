// 运行一次
function run() {

    let url = '/task/run';
    let rows = uzoo.app.refTable.value.getSelectRows()
    if (x.isEmpty(rows)) {
        me.layer.msg('请先选择一行数据')
        return
    }
    axios.post(url, rows[0])
        .then((res) => {
            let ret = res.data
            if (ret.state === 'ok') {
                me.layer.msg('运行成功')

                uzoo.app.refTable.value.query();
            } else {
                me.layer.no(ret.msg)
            }

        })
        .catch((error) => {
            me.layer.msg('客户端请求异常: ' + error.message);
        })

}