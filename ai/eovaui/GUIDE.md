# EovaUI 使用指南

## 安装与挂载

### Vue 工程（ESM）

```ts
import { createApp } from 'vue'
import EovaUI, { me, EvTable, EvForm } from 'eovaui' // 或相对路径 / UMD 构建产物

const app = createApp(App)
app.use(EovaUI) // 注册全部 withInstall 组件
app.mount('#app')
```

### UMD / 业务页（Eova 后台常见）

```html
<script src="vue.js"></script>
<script src="eova-ui.umd.js"></script>
<script>
  const { createApp } = Vue
  const app = createApp({ /* ... */ })
  // window.EovaUI 即 me；default export 为 install
  app.use(EovaUI)           // 或 EovaUI.install(app)
  // 命名组件：EovaUI.EvSelect 等
</script>
```

安装实现：`src/eovaui.ts` 遍历 `src/components/index.ts`，对每个组件 `app.use(com)`；`withInstall` 用组件 `name` 做 `app.component`。

### 局部按需

```ts
import { EvSelect, EvForm } from 'eovaui'
app.use(EvSelect)
app.use(EvForm)
```

未进全局清单的组件（`EvPage`、`EvRender`）需手动：

```ts
import EvPage from '@/components/pager'
app.component('EvPage', EvPage)
```

---

## 命名约定

| 项 | 规则 | 例 |
|----|------|-----|
| 组件 `name` | `Ev` + PascalCase | `EvSelect` |
| HTML 标签 | kebab-case | `<ev-select>` |
| Props 文件 | 同目录 `xxx.ts` | `select.ts` |
| CSS | 同目录 `index.css` / `xxx.css` | |
| DIY 业务控件（Meta） | 无 `Ev` 前缀，标签 `ev-xxx` | 见业务 skill，非本库内置 |

---

## v-model 约定

```
优先：modelValue + update:modelValue
兼容：部分控件有静态 value；同时存在时 modelValue 覆盖 value
```

| 组件 | 绑定类型 | 备注 |
|------|----------|------|
| EvInput / EvEdit / EvIcon | `string`（数字以字符串进出常见） | |
| EvBool | `boolean`（可把 `1`/`'1'`/`true` 当真） | |
| EvText | 显示值 | **无** `update:modelValue` |
| EvValue | 任意 → 隐藏域 | 单向 |
| EvCheck | `string` `"1,2"` | |
| EvRadio | `string \| number` | 空则默认首项 |
| EvSelect | `string \| number \| array` | 多选：外部是数组则保持数组，否则逗号串 |
| EvFind | `string` 逗号串 | |
| EvTime | 日期字符串 | |
| EvTimes | 通常为 `[start, end]` | LayDate 范围 |
| EvUpload | `string` 文件 code 逗号串 | |
| EvForm | `Record<string, any>` | 深度同步 |
| EvTable | 选中态用事件/`ref`，非整表 v-model | |
| EvTree | `v-model:checked` / `checkedNode` / `items` | |
| EvSortable | `any[]` | |
| EvDialog | `v-model:visible` | |
| EvTab | `v-model` = 当前 tab `id` | |
| EvPopup | 无 v-model；用 expose | |

---

## 选项数据结构 `SelectItem`

```ts
// src/types/select.ts
interface SelectItem {
  val: string | number   // 值
  txt: string            // 展示文案
  icon?: string
  info?: string
  disabled?: boolean
  selected?: boolean
}
```

`EvCheck` / `EvRadio` / `EvSelect` 本地模式都吃这个结构。远程模式传 `option`（Eova 选项表达式，如 `eova_users;10`），由组件内部拉取选项数据。

---

## 元字段类型 → 表单控件（EvForm 内置映射）

来源：`src/components/form/form.vue`（`status ≤ 2` 才渲染为控件）。

| 元字段 `f.type` | 渲染 |
|-----------------|------|
| 文本框 / 数字框 / 精准框 / 密码框 / 颜色 | `ev-input`（type 对应） |
| 布尔框 | `ev-bool` |
| 图标框 | `ev-icon` |
| 文本域 / 编辑框 / JSON | `ev-input type="texts"`（JSON 带 `lang="json"`） |
| 单选框 | `ev-radio` + `option` |
| 下拉框 | `ev-select` |
| 下拉树 | `ev-select type="tree"` |
| 查找框 | `ev-find` |
| 日期框 / 日期 | `ev-time type="date"` |
| 时间框 / 时间 | `ev-time`（默认 datetime） |
| 日期段；query 模式下的日期 | `ev-times type="date"` |
| 时间段；query 模式下的时间 | `ev-times type="datetime"` |
| 文件 / 图片 | `ev-upload` |
| 其他字符串 | `<component :is="f.type">`（自定义/DIY） |
| `diy.status == 3` | 隐藏 input |

`mode`：

| mode | 场景 |
|------|------|
| `create` | 新增 |
| `update` | 编辑（需 `pk` 拉 `form_data`） |
| `read` | 详情只读 |
| `query` | 查询条；部分类型被 `queryRules` 降级为文本/日期段 |

---

## 典型页面模式

### 1. CRUD 列表

```vue
<ev-table ref="refTable" :object="objectCode" biz="crud" :page="page" :height="h">
  <template #toolbar>
    <button class="eova-btn" @click="refTable.query(form)">查询</button>
  </template>
</ev-table>
```

### 2. 元数据表单

```vue
<ev-form
  ref="refForm"
  mode="create"
  object="meta_product"
  v-model="data"
  @ready="onReady"
  @submit="onSubmit"
/>
```

### 3. 本地选项下拉

```vue
<ev-select :items="items" v-model="val" :multiple="true" />
```

### 4. 选项表达式下拉

```vue
<ev-select option="eova_users;10" v-model="userId" ref="sel" />
<!-- 运行时切换：sel.setOption('other_opt') / sel.setDisabled(true) -->
```

---

## 依赖与样式

- Vue `^3.5`
- `@layui/layui-vue` / `@layui/layer-vue`（日期、树、layer）
- `vuedraggable`（排序）
- `@vueuse/core`（拖拽弹窗等）
- 样式随包引入：`src/eovaui.ts` → `./assets/index.css`

主题变量见 `src/assets/css/eova.var.css`。按钮样式类：`eova-btn`、`eova-btn_icon` 等（原生 button，非 EvButton）。

---

## 演示入口

| View | 覆盖 |
|------|------|
| `src/view/form/index.vue` | 输入类、select/find/time、check/radio API |
| `src/view/widget/index.vue` | progress/tab/popup/dialog/tree/select-tree |
| `src/view/crud/index.vue` | table + 查询 |
| `src/view/add` / `update` / `detail` | form 各 mode |
| `src/view/sort/index.vue` | sortable |
