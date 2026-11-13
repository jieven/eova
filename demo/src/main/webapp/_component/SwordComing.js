const {ref, reactive, watch, onMounted, nextTick, computed, defineComponent} = Vue; // 使用全局 Vue 对象
const {me} = EovaUI
const {x} = EovaTools

/**
 * 剑来 🗡
 *
 * 组件「SwordComing」，名曰「剑来」。
 * 召之即来，挥之即去，如臂使指，迅捷无匹。
 * 代码如剑意，一念动，万法随！
 *
 */
const SwordComing = defineComponent({
    template: `
      <link rel="stylesheet" href="/ui/css/SwordComing.css">
      <div ref="sword1Ref" id="imageContainer" style="display: none">
        <img src="/ui/img/sword1.png" style="width: 70vw;" alt="剑来">
      </div>
      <div ref="sword2Ref" id="imageContainer" style="display: none">
        <img src="/ui/img/sword2.webp" style="width: 70vw;" alt="剑来">
      </div>
      <div ref="swordRef" class="sword-go" v-show="show"
           style="position: fixed; top: 50px; bottom: 20px; right: 0; width: 400px; z-index: 999999999999;background-color: white; border: 1px solid #878484">
        <h3 style="margin: 5px; color: var(--eova-color_main)"> 🗡 {{ row.name }}</h3>
        <div style="position: absolute;right: 5px;top: 5px;" @click="$emit('update:show', false)" title="隐藏窗口">
          <i class="eova-icon-close"></i>
        </div>
        <h3>&nbsp;酒店布草库存</h3>
        <div class="eova-tools">
          <div class="eova-tools_box">
            <button @click="onAdd">新增库存</button>
            <button @click="onUpdate">修改库存</button>
          </div>
        </div>
        <ev-table
            ref="refTable1"
            object="hotel_stock"
            biz="hotel_stock"
            :page="page"
            :height="200"
            :init-query="false"
        >
        </ev-table>
        <h3>&nbsp;酒店床位</h3>

        <ev-table
            ref="refTable2"
            object="hotel_bed"
            biz="hotel_bed"
            :page="page"
            height="200"
            :init-query="false"
        >
        </ev-table>
        <ev-form
            ref="refForm"
            mode="read"
            name="stock_from"
            biz="hotel_stock"
            object="hotel_stock"
            pk="1"
        ></ev-form>
        <div class="eova-notes" style="margin: 10px">💡 手中有剑，心中有道！剑来！</div>
      </div>
    `,
    props: {
        modelValue: { // 选中行
            type: Object,
            required: true
        },
        show: {// 双向绑定变量，方便内外控制显示/隐藏
            type: Boolean,
            required: true,
        },
    },
    emit: ['update:show'],
    setup(props, ctx) {
        // const currentRow = ref(modelValue)

        const row = computed(() => props.modelValue);
        console.log(props.modelValue)

        // 子表1~子表N
        const refTable1 = ref()
        const refTable2 = ref()

        // 子表分页 （limit > 99999 不显示分页组件）
        const page = reactive({page: 1, limit: 99999})

        // 子表高度 动态计算:总高 - bottom - top - margin
        let table1Height = x.dom.getViewSize().height - 50 - 100 - 15 - 300 - 30;
        // PS:之前这块被平台锁死， 导致不灵活， 现在完成开放， 自行计算。结合Vue响应式，效果自行脑补。

        const sword1Ref = ref()
        const sword2Ref = ref()
        const swordRef = ref()
        const isFirst = ref(true)

        /**
         * 选中行实时监听
         */
        watch(
            () => props.modelValue,
            () => {
                console.log("选中行:")
                console.log(row.value)

                hideImageAndRemoveClass();
                handlerRowClick();

                // 进场动画
                // if (isFirst.value) {
                //     isFirst.value = false;
                //     // 显示图
                //     sword1Ref.value.style.display = 'flex';
                //     setTimeout(() => {
                //         sword1Ref.value.style.display = 'none';
                //         sword2Ref.value.style.display = 'flex';
                //     }, 500)
                //     // 页面动画
                //     const bodyElement = document.body;
                //     bodyElement.classList.add('quake');
                //     setTimeout(() => {
                //         bodyElement.classList.remove('quake');
                //         hideImageAndRemoveClass()
                //         handlerRowClick();
                //     }, 3000)
                // } else {
                //     handlerRowClick();
                // }
            }
        )

        function hideImageAndRemoveClass() {
            if (sword2Ref.value) {
                sword2Ref.value.style.display = 'none';
            }

            // 移除 sword 元素上的 sword-go 类
            if (swordRef.value) {
                swordRef.value.classList.remove('sword-go');
            }
        }

        onMounted(() => {
            // 业务初始化加载。。。
        });

        // 行点击事件（实现主子的核心API， 主表点击事件处理 @row-click="onRowClick"）
        const handlerRowClick = () => {
            let id = row.value.id;

            me.layer.msg('🗡 剑来... ' + row.value.name)

            // 子表1重新查询
            refTable1.value.query({
                hotel_id: id
            });
            // 子表2重新查询
            refTable2.value.query({
                hotel_id: id
            });

            ctx.emit('update:show', true)
        }

        // const onClose = (id) => {
        //     ctx.emit('update:show', false)
        // } 可直接调佣$emit

        // 监听提交通知(优先使用值监听模式， 更简单。。。)
        me.cross.on('eova-table-row_click', (id) => {
            // me.layer.msg('选择ID' + id)
            // handlerRowClick()
        })


        const LW = 720, LH = 660

        // 新增
        const onAdd = () => {
            let url = `/app/add/hotel_stock?biz=create&ref=hotel_id:${row.value.id}`;
            // 上述是自定义带参用法1：显示带入新增页面，参与页面展示。

            // 自定义带参用法2：更灵活的提交表单时覆盖表单参数，隐式操作。
            sessionStorage.setItem('hotel_stock', JSON.stringify({
                hotel_id: row.value.id
            }));

            me.layer.open('新增库存', url, LW, LH, () => {
                me.layer.msg('操作成功！')
                refTable1.value.query({hotel_id: row.value.id})
            })
        }

        // 修改
        const onUpdate = () => {
            let rows = refTable1.value.getSelectRows()
            if (x.isEmpty(rows)) {
                me.layer.msg('请先选择一行数据')
                return
            }
            let url = `/app/update/hotel_stock?id=${rows[0].id}&biz=update`;
            me.layer.open('修改库存', url, LW, LH, () => {
                me.layer.msg('操作成功！')
                refTable1.value.query({hotel_id: row.value.id})
            })
        }

        return {
            row,
            swordRef,
            sword1Ref,
            sword2Ref,

            refTable1,
            refTable2,
            page,

            onAdd,
            onUpdate
        };

    }
});

export default SwordComing;