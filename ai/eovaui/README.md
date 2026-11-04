# EovaUI 组件文档

> **读者优先级：AI > 人**  
> 本文档面向在 Cursor / Agent 中编写、改写、联调 Eova 页面的 AI；人类可读是次要目标。  
> **格式：Markdown（推荐）**，不要改成 HTML——AI 解析 MD 标题/表格更稳、更省 token。

## 文档地图（给 AI 的加载顺序）

| 顺序 | 文件 | 何时读取 |
|------|------|----------|
| 1 | [GUIDE.md](./GUIDE.md) | 安装、标签命名、`v-model` 约定、元字段→控件映射 |
| 2 | [CORE.md](./CORE.md) | `EovaUI.me` / `layer` / `render` / `vue` / `msg` |
| 3 | [COMPONENTS.md](./COMPONENTS.md) | **主参考**：全部组件 Props / Events / Slots / Expose |
| 4 | [components/](./components/) | 按组件深读；与 COMPONENTS 内容同源，便于 `@` 单文件 |

## 一句话定位

EovaUI 是基于 **Vue 3** 的元数据驱动 UI 库：表单/表格/树/查找等控件通过 `object`（元对象编码）+ 后端 `/api/*` 拉元数据与数据；也可纯本地 `items` / `rows` 使用。

- 包名：`eova-ui`（UMD 全局：`EovaUI`）
- 版本：`1.0.0`
- 入口：`src/eovaui.ts` → `app.use(EovaUI)`
- 运行时：`window.EovaUI` / 命名导出 `me`

## 组件总览

| 标签 | 组件名 | 类别 | 全局注册 |
|------|--------|------|----------|
| `ev-form` | EvForm | 元数据表单 | ✅ |
| `ev-input` | EvInput | 输入 | ✅ |
| `ev-text` | EvText | 只读文本域 | ✅ |
| `ev-bool` | EvBool | 开关/勾选 | ✅ |
| `ev-edit` | EvEdit | 弹窗编辑 | ✅ |
| `ev-icon` | EvIcon | 图标选择 | ✅ |
| `ev-value` | EvValue | 隐藏值 | ✅ |
| `ev-check` | EvCheck | 多选组 | ✅ |
| `ev-radio` | EvRadio | 单选组 | ✅ |
| `ev-select` | EvSelect | 下拉/下拉树 | ✅ |
| `ev-find` | EvFind | 查找框 | ✅ |
| `ev-time` | EvTime | 日期时间 | ✅ |
| `ev-times` | EvTimes | 时间范围 | ✅ |
| `ev-upload` | EvUpload | 上传 | ✅ |
| `ev-table` | EvTable | 元数据表格 | ✅ |
| `ev-tree` | EvTree | 树 | ✅ |
| `ev-dialog` | EvDialog | 对话框 | ✅ |
| `ev-popup` | EvPopup | 弹出层 | ✅ |
| `ev-tab` / `ev-tab-item` | EvTab / EvTabItem | 选项卡 | ✅ |
| `ev-sortable` | EvSortable | 拖拽排序 | ✅ |
| `ev-progress` | EvProgress | 进度条 | ✅ |
| `ev-widget` | EvWidget | DIY HTML 控件 | ✅ |
| `ev-button` | EvButton | **未完成**（内部仍是 input） | ✅ |

## AI 编码硬规则

1. **标签**：`EvSelect` → `<ev-select>`（PascalCase → kebab-case，前缀 `ev-`）。
2. **双向绑定**：优先 `v-model` / `modelValue`；与静态 `value` 并存时 **`modelValue` 优先**。
3. **多选值**：`EvCheck` / `EvFind` / 多数多选场景输出 **逗号分隔字符串**；`EvSelect` 若外部传入 **Array** 则保持 Array，否则也是逗号串。
4. **元数据优先**：`EvForm` / `EvTable` / `EvSelect(option|object)` / `EvFind` / `EvTree(object)` 依赖后端元数据与数据接口（组件内部请求，业务侧传 `object`/`option` 即可）。
5. **弹层控制**：`EvDialog` 用 `v-model:visible`；`EvPopup` 用 `ref.show()` / `ref.hide()`（无 visible prop）。
6. **不要把 EvButton 当按钮用**：业务按钮用原生 `<button class="eova-btn…">`。
7. **改组件时**：Props 定义在同目录 `*.ts`，模板在 `*.vue`，注册名以 `name: 'EvXxx'` 为准。

## 源码锚点

| 主题 | 路径 |
|------|------|
| 安装入口 | `src/eovaui.ts` |
| 组件清单 | `src/components/index.ts` |
| 全局实例 | `src/core/index.ts` → `me` |
| DIY 渲染注册 | `src/diy/render/` |
| 表单演示 | `src/view/form/index.vue` |
| 控件演示 | `src/view/widget/index.vue` |
| CRUD 演示 | `src/view/crud/index.vue` |
