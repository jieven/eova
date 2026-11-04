---
name: eova-component
description: 为 Eova Meta 开发 Vue3 自定义表单控件（_component/*.js），并完成全局注册、vue.app 绑定、eova_widget 入库与元字段配置。在用户提到 Eova 组件、自定义控件、DIY 控件、_component、vue.app、eova_widget、GroupBuy、TaskEffect 时使用。
---

# Eova 自定义组件开发

基于 Vue3 `defineComponent` 为 Eova 表单开发可复用控件，存放于 `app-web/src/main/webapp/_component/`。

## 完整流程清单

```
- [ ] 1. 编写组件：_component/ComponentName.js（+ 可选 ComponentName.css）
- [ ] 2. 全局注册：_eova/assets/eova.vue.config.js → vue.component(...)
- [ ] 3. 【系统控件】vue.app 声明 components
- [ ] 4. 【系统控件】INSERT eova_widget SQL
- [ ] 5. 【系统控件】元字段配置 > 选择控件
```

步骤 1–2 即可在自定义页面中 `<ev-xxx>` 使用；步骤 3–5 才能在元对象表单字段里作为系统控件选用。

---

## 组件开发规范

### 文件与命名

| 项 | 规范 |
|---|---|
| JS 文件 | `_component/GroupBuy.js`（PascalCase，与 `name` 一致） |
| CSS 文件 | `_component/GroupBuy.css`（可选，模板内 `<link>` 引入） |
| 组件 `name` | `'GroupBuy'` |
| HTML 标签 | `<ev-group-buy>`（驼峰 → 连字符，前缀 `ev-`） |

### 最小骨架

```javascript
const {defineComponent, watch, ref} = Vue;

export default defineComponent({
    name: 'MyWidget',
    props: {
        modelValue: {
            type: String,   // 按业务选 String / Number / Object
            default: '',
        },
    },
    emits: ['update:modelValue'],
    setup(props, ctx) {
        const emitValue = (val) => ctx.emit('update:modelValue', val);

        watch(() => props.modelValue, (val) => {
            // 外部回填时同步内部状态
        });

        return { /* ... */ };
    },
    template: `
      <link rel="stylesheet" href="/_component/MyWidget.css">
      <div class="my-widget">
        <!-- 控件 UI -->
      </div>
    `,
});
```

### 必守约定

1. **双向绑定**：`props.modelValue` + `emits: ['update:modelValue']`
2. **不引 npm 包**：使用全局 `Vue`、`EovaUI`、`axios` 等
3. **样式**：灰白调（`#f5f5f5` 容器、`#fff` 行、`#dcdcdc` 边框）；参考 `GroupBuy.css`、`TaskEffect.css`
4. **注释头**：写明 `<ev-xxx>` 用法与 `v-model` 值格式
5. **改动最小**：只写组件所需逻辑，不碰无关文件

### 值类型建议

| 场景 | modelValue 类型 | 示例 |
|---|---|---|
| 结构化多行数据 | `String`（JSON） | `[{"platform":1,"groupId":"xxx"}]` |
| 表单生成文本 | `String`（多行文本） | `生效日期: 2026-01-01\n星期范围: 1 2 3` |
| 简单数值 | `Number` | `Rate` 评分 1–5 |

---

## 步骤 2：全局注册

文件：`_eova/assets/eova.vue.config.js`

```javascript
const {vue} = EovaUI.me

vue.component('GroupBuy', '/_component/GroupBuy.js');   // 团购ID组件
vue.component('TaskEffect', '/_component/TaskEffect.js'); // 团购效期
```

`vue.component` 第一个参数 = 组件 `name`，第二个参数 = JS 路径。

---

## 步骤 3–5：作为系统控件使用

### 步骤 3：vue.app 绑定元对象

在 `eova.vue.config.js` 中，按**元对象编码**声明该表单可用的组件：

```javascript
vue.app('it_group_buy', {
    components: ['GroupBuy', 'TaskEffect'],
});
```

- `it_group_buy`：元对象编码（与数据库 `eova_object.code` 一致）
- `components`：该表单允许使用的自定义组件 `name` 列表
- 也可同时配 `template` / `script` 做整页自定义

### 步骤 4：eova_widget 入库 SQL

DIY 控件 `type = 2`，`value` = 组件 `name`（与 `vue.component` 一致）：

```sql
INSERT INTO `eova_widget` (`type`, `value`, `name`, `version`, `path`, `description`, `config`)
VALUES (2, 'TaskEffect', '团购效期', '1.0', NULL, '设置团购生效策略', NULL);

```

| 字段 | 说明 |
|---|---|
| `type` | `1`=EOVA 内置控件，`2`=DIY 自定义控件 |
| `value` | 组件名，如 `GroupBuy`、`TaskEffect` |
| `name` | 控件在元字段配置里显示的中文名 |
| `id` | 自增，新控件取当前最大 id + 1 |

### 步骤 5：元字段配置

> Tips：元字段配置 > 选择控件。

在 Eova 后台打开对应元字段，**控件类型**选刚入库的 DIY 控件（如「团购效期」「团购ID」），保存后新增/编辑表单即渲染该组件。

---

## 页面内直接使用（无需步骤 3–5）

已在步骤 2 全局注册后，自定义模板中：

```html
<ev-group-buy v-model="data.group_buy_ids"></ev-group-buy>
<ev-task-effect v-model="data.task_effect"></ev-task-effect>
```

---

## 参考示例

详细代码见 [examples.md](examples.md)。

| 组件 | 用途 | v-model 格式 |
|---|---|---|
| `GroupBuy` | 多平台团购 ID，动态行 +/- | 多行文本 `美团 xxxid\n抖音 xxxid` |
| `TaskEffect` | 效期规则，表单生成文本 / 自由编辑 | 多行文本（见下方） |
| `Rate` | 星级评分 | `Number` 1–5 |
| `InputNums` | 数字步进器 | `Number` |

`TaskEffect` 输出文本格式：

```
生效日期: 2026-01-01
星期范围: 1 2 3 4 5 6 7
售卖时段: 00:00 ~ 23:59
```

---

## 新建组件时的交付清单

向用户交付时一并给出：

1. `_component/Xxx.js`（+ 可选 `.css`）
2. `eova.vue.config.js` 中 `vue.component` 注册行
3. 若作系统控件：`vue.app` 片段 + `INSERT eova_widget` SQL
4. 元字段配置提示：**元字段配置 > 选择控件**
