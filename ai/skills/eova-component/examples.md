# Eova 组件示例

## GroupBuy — 多平台团购 ID

**文件**：`_component/GroupBuy.js`、`_component/GroupBuy.css`

**用法**：

```html
<ev-group-buy v-model="data.group_buy_ids"></ev-group-buy>
```

**值格式**（多行文本）：

```
美团 MT123456
抖音 DY789012
高德 GD345678
```

**平台枚举**：`1`=美团，`2`=抖音，`3`=高德

**注册与接入**：

```javascript
// eova.vue.config.js
vue.component('GroupBuy', '/_component/GroupBuy.js');

vue.app('it_group_buy', {
    components: ['GroupBuy', 'TaskEffect'],
});
```

```sql
INSERT INTO `eova_widget` (`id`, `type`, `value`, `name`, `version`, `path`, `description`, `config`)
VALUES (47, 2, 'GroupBuy', '团购ID', '1.0', NULL, '多平台团购ID收集', NULL);
```

---

## TaskEffect — 团购效期

**文件**：`_component/TaskEffect.js`、`_component/TaskEffect.css`

**用法**：

```html
<ev-task-effect v-model="data.task_effect"></ev-task-effect>
```

**交互**：

- 表单模式：日期、星期复选框、售卖时段 → 自动生成文本
- 「自由编辑」：切换文本域手动修改；「返回表单」尝试解析回填

**值格式**（多行文本）：

```
生效日期: 2026-01-01
星期范围: 1 2 3 4 5 6 7
售卖时段: 00:00 ~ 23:59
```

**注册与接入**：

```javascript
vue.component('TaskEffect', '/_component/TaskEffect.js');
```

```sql
INSERT INTO `eova_widget` (`id`, `type`, `value`, `name`, `version`, `path`, `description`, `config`)
VALUES (48, 2, 'TaskEffect', '团购效期', '1.0', NULL, '设置团购生效策略', NULL);
```

---

## Rate — 最小双向绑定参考

```javascript
const {defineComponent, watch, ref} = Vue;

export default defineComponent({
    name: 'Rate',
    props: { modelValue: { type: Number, default: 0 } },
    emits: ['update:modelValue'],
    setup(props, {emit}) {
        const currentRating = ref(props.modelValue)
        watch(() => props.modelValue, (v) => { currentRating.value = v })
        const handleStarClick = (index) => {
            const rating = index + 1
            currentRating.value = rating
            emit('update:modelValue', rating)
        }
        return { currentRating, handleStarClick }
    },
    template: `<!-- 星级 UI -->`
})
```

---

## MyDemo — 最简结构参考

```javascript
const {defineComponent, computed, ref} = Vue;

const MyDemo = defineComponent({
    template: `<!-- 固定浮层 + textarea 示例 -->`,
    props: {
        modelValue: { type: Object, required: true },
    },
    setup(props, ctx) {
        const text = ref('');
        const wordCount = computed(() => text.value.length);
        return { text, wordCount };
    }
});

export default MyDemo;
```
