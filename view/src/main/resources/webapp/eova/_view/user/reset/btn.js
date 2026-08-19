// 重置密码
function reset() {

    let url = '/user/reset';
    let rows = uzoo.app.refTable.value.getSelectRows()
    if (x.isEmpty(rows)) {
        me.layer.msg('请先选择一个用户')
        return
    }

    me.layer.input("请输入新密码", "password", (val) => {
        axios.post(url, {
            id: rows[0].id,
            val: val
        }).then((res) => {
            let ret = res.data
            if (ret.state === 'ok') {
                me.layer.msg('密码重置成功')
            } else {
                me.layer.no(ret.msg)
            }
        }).catch((error) => {
            me.layer.msg('客户端请求异常: ' + error.message);
        })
    })


}