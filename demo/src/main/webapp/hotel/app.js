const {onMounted, watch} = Vue; // 使用全局 Vue 对象
const {me} = EovaUI
const {x} = EovaTools

// 可以暂存字段组件实例, 如果有其他需求可以直接调用.
let $city, $region;

uzoo.vue.setup = () => {
    onMounted(() => {
        console.log("hotel app setup onMounted")
    })

    return {}
}

/**
 * 表单渲染完成(DOM正式加载完成)
 * @param fields 字段组件实例
 */
uzoo.vue.onReady = (fields) => {
    console.log("eova.form.js onReady...")

    // 判断当前表单类型(例如有的搞, 有的不搞)
    if (uzoo.page.form === 'create') {
        me.layer.notify('自定义逻辑', '/hotel/app.js form:create', 'ok')
    } else if (uzoo.page.form === 'update') {
        me.layer.notify('自定义逻辑', '/hotel/app.js form:update', 'ok')
    } else if (uzoo.page.form === 'read') {
        me.layer.notify('自定义逻辑', '/hotel/app.js form:read', 'ok')
    } else if (uzoo.page.form === 'query') {
        me.layer.notify('自定义逻辑', '/hotel/app.js form:query', 'ok')
    }

    $city = fields.get('city');
    $region = fields.get('region');

    // 初始禁用市，区
    $city.setDisabled(true)
    $region.setDisabled(true)

    // 省 > 市
    watch(() => uzoo.app.data.province, (val) => {
        if (!x.isEmpty(val)) {
            me.layer.msg(`省选择: ${val}`);
            $city.setDisabled(false)
            $city.setOption(`area;2,${val}`)

            $region.setDisabled(true)
        }
    });

    // 市 > 区
    watch(() => uzoo.app.data.city, (val) => {
        if (!x.isEmpty(val)) {
            me.layer.msg(`市选择: ${val}`);

            $region.setDisabled(false)
            $region.setOption(`area;3,${val}`)
        }
    });

}