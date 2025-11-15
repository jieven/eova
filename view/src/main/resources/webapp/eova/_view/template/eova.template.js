/**
 * EovaTool全局精简命名空间(如果命名冲突, 可自创模版Base)
 */
const x = EovaTools.x
/**
 * EovaUI 全局实例
 */
const me = EovaUI.me;
// 优先在上层进行弹出
const pme = parent ? parent.EovaUI.me : me;

console.log(`load eovatools:${x.time.now()}`)
console.log(`load eovaui:${me.ver}`)

// 按钮事件处理(需提取为公共模块, 各模版复用, 如方法不存在会导致白屏)
const handlerButtonEvent = (event) => {
    // 检查方法是否存在且为函数
    if (window.hasOwnProperty(event) && typeof window[event] === 'function') {
        // 函数存在，可以安全地调用
        window[event]();
    } else {
        me.layer.msg(`function ${event}() 无法调用, 请确认该函数已确定义`, {icon: 2, time: 3000})
    }
}


/**
 * 元对象编辑
 */
const onMetaObject = () => {
    let url = '/app/update/eova_object_code?id=' + uzoo.page.object_id
    me.layer.open('编辑元对象', url, 720, 720, () => {
        location.reload()
    })

}

/**
 * 元字段编辑
 */
const onMetaField = () => {
    window.open(`/meta/edit?object=${uzoo.page.object_code}`)
}

/**
 * 菜单编辑
 */
const onMenu = () => {

    let url = x.str.template('/app/update/{{object}}?id={{id}}', {
        object: 'eova_menu_code',
        id: uzoo.page.menu_id
    });

    me.layer.open('菜单配置', url, 720, 720, () => {
        location.reload()
    })
}

/**
 * 快速添加按钮
 */
const onButtonAdd = () => {

    let url = x.str.template('/button/add/{{menu_code}}', {
        menu_code: uzoo.page.menu_code,
    });

    me.layer.open('添加按钮', url, 1000, 520, () => {
        location.reload()
    })
}

/**
 * 元字段编辑
 */
const onMetaFieldDiy = (mode) => {
    window.open(`/meta/field?object=${uzoo.page.object_code}&mode=${mode}`)
}

/**
 * 表格列排序
 */
const onReorderList = () => {
    me.layer.open('字段排序', `/meta/reorder?object=${uzoo.page.object_code}&biz=field`, 250, 1, () => {
        me.layer.msg('操作成功')
        location.reload()
    }, () => {
    }, {
        offset: 'r'
    })
}

/**
 * 表单列排序
 */
const onReorderForm = (mode) => {
    let win = window
    pme.layer.open('字段排序', `/meta/reorder?object=${uzoo.page.object_code}&biz=field_diy&mode=${mode}`, 250, 1, () => {
        me.layer.msg('操作成功')
        win.location.reload()
    }, () => {
    }, {
        offset: 'r'
    })
}

/**
 * 保存窗口宽高
 */
const onLayerSize = (mode) => {
    let lay = window.parent.document.querySelector('.layui-layer-iframe');

    if (!lay) {
        me.layer.error('无法获取窗口信息');
        return;
    }


    let url = `/menu/updateLayerSize/${uzoo.page.biz}`;
    axios
        .post(url, {
            width: lay.clientWidth,
            height: lay.clientHeight
        })
        .then((res) => {
            let ret = res.data
            if (ret.state === 'ok') {
                me.layer.msg(`更新成功，窗口大小：${lay.clientWidth} * ${lay.clientHeight}`)
            } else {
                me.layer.msg(ret.msg)
                console.log('加载错误')
            }
        })
        .catch((error) => {
            me.layer.msg('客户端请求异常: ' + error.message);
        })
}

