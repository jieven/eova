console.log("menu.add这个方法已加载。。。")

function add() {
    console.log("menu.add这个方法被调用。。。。")
    // 默认新增到根下
    let menuId = uzoo.app.treeId.value;
    if (menuId === 0) {
        me.layer.confirm('未选择父目录,确认添加到根目录下?', () => {
            addMenu();
        })
    } else {
        addMenu();
    }

    function addMenu() {
        let url = '/menu/toAdd?parent_id=' + menuId;
        me.layer.open('新增菜单', url, 1000, 700, () => {
            me.layer.msg('操作成功！')
        })
    }


}