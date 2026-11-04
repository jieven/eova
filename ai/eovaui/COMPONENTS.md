# EovaUI 组件 API 总表

> 完整细项见 `docs/components/*.md`。本页供 AI **一次加载** 做决策；改具体组件时再打开对应分册。

## 快速索引

[Form](#evform) · [Input](#evinput) · [Text](#evtext) · [Bool](#evbool) · [Edit](#evedit) · [Icon](#evicon) · [Value](#evvalue) · [Check](#evcheck) · [Radio](#evradio) · [Select](#evselect) · [Find](#evfind) · [Time](#evtime) · [Times](#evtimes) · [Upload](#evupload) · [Table](#evtable) · [Page](#evpage) · [Tree](#evtree) · [Dialog](#evdialog) · [Popup](#evpopup) · [Tab](#evtab) · [Sortable](#evsortable) · [Progress](#evprogress) · [Widget](#evwidget) · [Button](#evbutton) · [Render](#evrender)

---

## EvForm

- **Tag:** `ev-form` · **文件:** `src/components/form/`
- **作用:** 按元对象拉 `meta_form`，映射字段到内置控件；`v-model` 整表数据。

| Prop | Type | Default | 说明 |
|------|------|---------|------|
| name | string | `'eova_form'` | 表单名 |
| mode | `'create'\|'read'\|'update'\|'query'` | `'create'` | 场景 |
| modelValue | Record | — | 表单数据 |
| object | string | — | 元对象编码 |
| pk | string | — | 主键（update/read） |
| biz | string | — | 业务码 |
| height / maxHeight / size | — | — | 尺寸 |

| Event | Payload |
|-------|---------|
| update:modelValue | data |
| ready | `Map<fieldEn, instance>` |
| resize | height |
| submit | data（回车） |

| Expose | |
|--------|--|
| getData() | 当前数据 |
| reset() | 清空 |
| validate(diyRules?) | boolean |
| getFieldInstance(name) | 子控件实例 |

```vue
<ev-form mode="update" object="user" :pk="id" v-model="data" ref="f" @ready="…" />
```

---

## EvInput

- **Tag:** `ev-input` · **文件:** `src/components/input/input/`
- **type:** `text` \| `texts` \| `number` \| `password` \| `color`（及被 Time/Edit 等包装的 `time`/`edit`…）

| Prop | Type | Default |
|------|------|---------|
| modelValue | string\|number\|null | `''` |
| value | string\|number | — |
| type | string | `'text'` |
| lang | `'text'\|'json'\|'xml'\|'shell'` | `'text'`（texts 用） |
| name | string | `'input'` |
| placeholder / disabled / readonly / style / class | | |
| min / max | number | `0` / `MAX`（number） |

**Events:** `update:modelValue`, `input`, `change`, `focus`, `blur`  
**Slots:** `default` 可替换原生 input  
**Expose:** `getValue`（注意：expose 时点快照）

```vue
<ev-input v-model="t" />
<ev-input type="number" :min="0" :max="100" v-model="n" />
<ev-input type="texts" lang="json" v-model="j" />
```

---

## EvText

- **Tag:** `ev-text` · **只读展示向 textarea**
- Props: `name`(必填), `modelValue`(必填), `placeholder`, `disabled`, `style`(默认宽高约 552×70)
- **无 emit** — 不要当双向输入用；长文本编辑用 `EvInput type="texts"` 或 `EvEdit`。

---

## EvBool

- **Tag:** `ev-bool`
- Props: `modelValue`(default false), `type` `'bool'\|'check'`, `name`, `label`, `disabled`
- Events: `update:modelValue`, `change` → boolean
- 真值判定含 `1` / `'1'` / `true` / `'true'`

```vue
<ev-bool v-model="on" />
<ev-bool type="check" label="全选" v-model="all" @change="…" />
```

---

## EvEdit

- **Tag:** `ev-edit` — 只读输入 + 弹窗 textarea
- Props: `title` 默认 `'快速编辑'`, `modelValue`, `disabled`, `name`
- Events: `update:modelValue`, `change`

---

## EvIcon

- **Tag:** `ev-icon`
- Props: `type` 默认 `'eova-icon'`, `icons[]`, `title`, `modelValue`, `disabled`
- Events: `update:modelValue`, `change` — 值为图标 class，如 `eova-icon-leaf`

---

## EvValue

- **Tag:** `ev-value` — 把 `modelValue`/`value` 同步到隐藏 input（有 `name` 时）便于原生提交；无 emit。

---

## EvCheck

- **Tag:** `ev-check`
- Props: `items: SelectItem[]`(必填), `modelValue`, `value`, `name`, `disabled`
- Events: `update:modelValue` / `change` → **逗号串**
- Expose: `selectAlls(checked: boolean)`, `txt`

```vue
<ev-check :items="items" v-model="v" ref="ck" />
```

---

## EvRadio

- **Tag:** `ev-radio`
- Props: `items` 或 `option`(远程), `modelValue`, `value`, `name`, `disabled`
- 无 option 时空值默认选中第一项
- Events: `update:modelValue`, `change`

```vue
<ev-radio option="eova_role" v-model="role" />
```

---

## EvSelect

- **Tag:** `ev-select` · **分册:** [components/select.md](./components/select.md)
- Props 要点: `type` `'select'\|'tree'\|'diy'`, `option`, `items`+`v-model:items`, `object`+`treeConf`(树), `multiple`, `disabled`, `placeholder`, `allowClear`, `showSearch`, `autoFitWidth`
- 多选值：外部 array → 保持 array；否则 `"1,2,3"`
- Events: `update:modelValue`, `update:items`, `change`, `search`, `node-click`
- Slots: `default`（仅 `type="diy"`）
- Expose: `setDisabled`, `setOption`, `getItems`, `getSelectItems`, `getSelectItemVal`, `getSelectItemTxt`

```vue
<ev-select option="eova_users;10" v-model="id" />
<ev-select :items="items" multiple v-model="ids" />
<ev-select type="tree" object="eova_menu_code" :treeConf="conf" v-model="c" v-model:items="items" />
```

---

## EvFind

- **Tag:** `ev-find` — 弹表格选择
- Props: `mode` `'input'\|'a'\|'button'\|'text'`, `option`, `modelValue`, `multiple`, `title`, `placeholder`, `disabled`
- 值：逗号串；展示文案走 `widget_text`
- Expose: `setDisabled`, `setOption`

```vue
<ev-find option="users" v-model="uid" />
<ev-find mode="a" title="切换用户" option="users" v-model="uid" />
```

---

## EvTime / EvTimes

| | EvTime | EvTimes |
|--|--------|---------|
| Tag | `ev-time` | `ev-times` |
| modelValue | 单值字符串 | 范围（常为数组） |
| type | date/datetime/year/month/time/yearmonth，默认 **datetime** | 同左，范围 |
| placeholder | string | **string[]** 如 `['开始','结束']` |
| Expose | setDisabled | setDisabled |

底层：LayUI `lay-date-picker` 塞进 `ev-input` 默认插槽。

---

## EvUpload

- **Tag:** `ev-upload`
- `modelValue`: 文件 code **逗号串**
- Props: `file_upload`/`file_query`/`file_down`(有内置默认), `size`(KB, 默认 50*1024), `accept`, `auto_upload`, `show_info`, `show_record`, `disabled`
- Events: `update:modelValue`, `select`, `progress`, `success`, `error`

---

## EvTable

- **Tag:** `ev-table` · **分册:** [components/table.md](./components/table.md)
- 核心 Props: `object`, `biz`, `cols`, `rows`, `page` `{page,limit,total,limits?}`, `where`, `height`, `isEdit`, `initQuery`, `resize`, `loading`
- Events(已触发): `sort-change`, `update:selectedRows`, `selection-change`, `row-click`
- Slot: `toolbar`
- Expose: `query(data)`, `getSelectPks()`, `getSelectRows()`, `removeRows(ids)`, `getColWith()`

```vue
<ev-table ref="t" object="user" biz="crud" :page="page" @row-click="…">
  <template #toolbar>…</template>
</ev-table>
```

内置 `EvPage`（`ev-page`）：`v-model` 页码，`@change="{current,limit}"`。

---

## EvTree

- **Tag:** `ev-tree`
- Props: `data` **扁平** `{id,pid,…}`, `conf` 字段映射, `object` 远程, `multiple`, `checked` / `checkedNode` / `items`（均可 v-model:）
- Events: `update:checked`, `update:checkedNode`, `update:items`, `node-click`
- Expose: `reload`, `getChecked`, `getCheckedNode`, `getItems`, `filter`

默认 `conf`: `{ id:'id', title:'name', pid:'pid', root:'0' }`

---

## EvDialog

- **Tag:** `ev-dialog`
- Props: `title`, `width` `'1000px'`, `height` `'600px'`, `visible`, `layerContainer`
- **控制:** `v-model:visible`
- Slots: `default`, `footer`

---

## EvPopup

- **Tag:** `ev-popup`
- Props: `trigger` click/hover/focus/contextMenu, `placement`, 定位/关闭相关布尔与 delay
- Slots: **default=触发器元素**, `#content`=面板
- Expose: `show`, `hide`, `toggle`, `addChildRef`
- **无 visible v-model**

```vue
<ev-popup ref="p">
  <button>打开</button>
  <template #content><div @click="p.hide()">…</div></template>
</ev-popup>
```

---

## EvTab

```vue
<ev-tab v-model="id" type="base|card|sys">
  <ev-tab-item id="a" title="A">…</ev-tab-item>
</ev-tab>
```

Events: `update:modelValue`, `change`（`close` 类型已声明，标题关闭 UI 未接全）

---

## EvSortable

- **Tag:** `ev-sortable`
- Props: `modelValue: any[]`, `text_field` `'name'`, `order_field` `'num'`
- Event: `update:modelValue`

---

## EvProgress

- Props: `value` 0–100, `color`（可 `"#a,#b"` 渐变）, `text`, `size`, `text_inside`, `text_align`

---

## EvWidget

- **Tag:** `ev-widget`（barrel 别名 EvDiy，注册名仍 EvWidget）
- Props: `id`(必填), `html`, `modelValue`, `type`, `object`, `field`, `disabled`
- 值通道：`me.msg` → `ev-widget-value-${id}`

---

## EvButton

⚠️ **WIP**：当前渲染为内部 `ev-input`，**不要**当操作按钮。用 `<button class="eova-btn">`。

---

## EvRender

未全局注册。Props: `data: (string|VNode)[]`。字符串含标签 → `v-html`。

---

## 共享类型

```ts
SelectItem { val, txt, icon?, info?, disabled?, selected? }
TreeConf { id, title, pid, root, spread?, icon?, children? }
TablePage { page, limit, total?, limits? }
Kv = Record<string, any>
```
