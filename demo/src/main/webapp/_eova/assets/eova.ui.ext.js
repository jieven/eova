/**
 * EovaUI 相关扩展
 */
const {h} = Vue; // 使用全局 Vue 对象
const {me, EvBool} = EovaUI
const {x} = EovaTools

/**
 * 表格单元格渲染
 * @type {Map<any, any>}
 */
const eovaTableCell = new Map();

const eovaTableIsland = new Map();
me.render.add('eova-table-island', {
    render(props, rows) {
        const render = eovaTableIsland.get(props.object.code);
        if (render) {
            return render(rows)
        }
    }
})

// 系统消息.发信人 格式化颜色
eovaTableCell.set('eova_msg.from_uid', (val) => {
    return val === '0' ? '系统' : val
})

// 导入记录.状态 格式化颜色
eovaTableCell.set('eova_import.status', (val) => {
    const map = {
        '导入中': 'orange',
        '成功': 'green',
        '失败': 'red'
    };
    const color = map[val] || 'gray';
    return `<span class="eova-badge eova-badge_round eova-bg-${color}">${val}</span>`;
})

// 酒店管理.状态 格式化颜色
eovaTableCell.set('meta_hotel.state', (val) => {
    const map = {
        '普通商户': 'red',
        '签约商户': 'gray',
    };
    const color = map[val];
    return `<span class="eova-badge eova-badge_round eova-bg-${color}">${val}</span>`;
})

// 产品.积分 格式化进度条
eovaTableCell.set('meta_product.score', (val) => {
    // 使用Eova组件 根据标准语法转成虚拟节点写法 <ev-progress :value="80" color="#67C23A"></ev-progress>
    if (val > 30) {
        return h(EovaUI.EvProgress, {value: (val), color: '#f56c6c', text_align: 'left'});
    }
    if (val > 10) {
        return h(EovaUI.EvProgress, {value: (val), color: '#67C23A', text_align: 'left'});
    }
    if (val > 5) {
        return h(EovaUI.EvProgress, {value: (val), color: '#e6a23c', text_align: 'left'});
    }
    return h(EovaUI.EvProgress, {value: 0, color: '#e6a23c', text_align: 'left'});
})

// 产品.图片 格式化图片
eovaTableCell.set('meta_product.img', (val) => {
    // return `<img src="/${val}" style="width:25px;" alt="${val}"></img>`
    if (x.isEmpty(val)) return '';
    // 将字符串按逗号分割成数组
    let imgFiles = val.split(',');
    let cdn = me.conf.get('web_cdn');

    // 遍历数组，生成 img 标签
    let imgs = imgFiles.map(img => {
        let url = `${cdn}/img/${img}`;
        let show = `me.layer.open('查看图片', '${url}', 600, 600);`

        return `<img src="${url}" style="width:30px; height:30px;margin-left: 2px" onclick="${show}">`;
    }).join('');

    return imgs;
})


/**
 * 表格 ~ 灵动岛
 *
 * Table作为系统的核心组件, 仅仅只是在表格内展示数据往往是不够的, 总会遇到一些特殊数据需要显示
 *
 * 灵动岛的应用场景:
 * 1.实时计算显示: 汇总 计算 统计...
 * 2.固定文档特别提示: 店铺还有7天到期, 请注意续费!
 * 3.实时信息流显示 eg.沪指最新价格 3000 实时更新.
 * 4.其他特殊功能触发入口, 这就是和手机一样的真正的灵动岛了, 可以打开 客服,助手,云盘,笔记等.
 * 5.有没有可能是灵动界(动态图表实时展示) 你的心有多大, 你的灵动岛就有多大!
 *
 * 灵动岛默认样式:
 * .eova-table-island_left 默认 分页栏靠左
 * .eova-table-island_right 分页栏靠右
 * .eova-table-island_top 顶部按钮栏靠右
 * .eova-table-island_xxx 自行编写样式
 *
 */

// 产品管理-库存数
eovaTableIsland.set('meta_product', (rows) => {
    let sum = rows.value.reduce((acc, r) => acc + r.stock, 0)
    return `<div class="eova-table-island eova-table-island_left">🎯库存总数: ${sum}</div>`
})

// 酒店管理-灵动岛
eovaTableIsland.set('meta_hotel', (rows) => {
    return `<div class="eova-table-island eova-table-island_left">😊 灵动岛 🧭</div>`
})

me.render.add('eova-table-cell', {
    //     参数(field), 单元格值
    render(props, value) {

        // 自定义格式化执行
        const key = `${props.field.object_code}.${props.field.en}`;
        const fm = eovaTableCell.get(key);
        if (fm) {
            return fm(value);
        } else {
            return value;
        }

        // 其他通用渲染逻辑
        // 格式化数字
        if (props.field.cn.endsWith('率')) {
            return `${value}%`
        }
    }
})

// 自定义图片上传详情显示， 渲染
// me.render.add('eova-upload-record', {
//     render(props, data) {
//         console.log(data)
//         return `
//         <div class="eova-upload_record__warp">
//            <div class="eova-upload_record__img">
//              <img src="${data.code}" alt="自定义预览图"  style="width:480px;height:240px"/>
//            </div>
//         </div>
//       `
//     }
// })