# EvInput 及输入族

包含：`EvInput` / `EvText` / `EvBool` / `EvEdit` / `EvIcon` / `EvValue`  
源码：`src/components/input/`

---

## EvInput

基础输入框；`type="texts"` 为多行；可被 Time/Edit 等包一层。

**标签:** `<ev-input>`

### 基础用法

```vue
<ev-input v-model="txt" placeholder="请输入" />
<ev-input type="number" v-model="n" :min="0" :max="100" />
<ev-input type="password" v-model="pwd" />
<ev-input type="color" v-model="color" />
<ev-input type="texts" lang="json" v-model="jsonStr" />
```

静态初值（非受控偏好）可用 `value`；与 `modelValue` 并存时以 **modelValue** 为准。

### Props

| 属性 | 说明 | 类型 | 默认值 |
|------|------|------|--------|
| modelValue | 绑定值 | `string \| number \| null` | `''` |
| value | 静态初值 | `string \| number` | — |
| type | 类型 | `string` | `'text'` |
| lang | texts 语言；`json` 会格式化 | `'text'\|'json'\|'xml'\|'shell'` | `'text'` |
| name | name | `string` | `'input'` |
| placeholder | 占位 | `string` | — |
| disabled / readonly | 禁用 / 只读 | `boolean` | `false` |
| min / max | number 边界 | `number` | `0` / `MAX` |
| style / class | 样式 | `string` | — |

### Events

| 事件 | 参数 |
|------|------|
| update:modelValue / input / change | value |
| focus / blur | FocusEvent |

### Slots

| 插槽 | 说明 |
|------|------|
| default | 替换原生 `<input>`（日期控件等使用） |

### Methods

| 方法 | 说明 |
|------|------|
| getValue | 取值（expose 瞬间快照） |

---

## EvText

只读向 textarea，**不发出** `update:modelValue`。

```vue
<ev-text name="desc" :model-value="text" />
```

| 属性 | 类型 | 默认 |
|------|------|------|
| name | string | 必填 |
| modelValue | string \| null | 必填 |
| placeholder | string | — |
| disabled | boolean | false |
| style | string | `'width: 552px; height: 70px'` |

---

## EvBool

开关（`type="bool"`）或勾选外观（`type="check"`）。

```vue
<ev-bool v-model="flag" />
<ev-bool type="check" label="同意" v-model="ok" @change="onChange" />
```

| 属性 | 类型 | 默认 |
|------|------|------|
| modelValue | any | false |
| type | `'bool'\|'check'` | `'bool'` |
| name / label | string | |
| disabled | boolean | false |

Events: `update:modelValue`, `change` → `boolean`  
真值：`true` / `'true'` / `1` / `'1'`

---

## EvEdit

输入框 + 弹窗编辑长文本。

```vue
<ev-edit v-model="content" title="编辑备注" />
```

| 属性 | 默认 |
|------|------|
| title | `'快速编辑'` |
| modelValue | `''` |
| disabled | false |

Events: `update:modelValue`, `change`

---

## EvIcon

图标选择器。

```vue
<ev-icon type="eova-icon" v-model="icon" placeholder="请选择图标" />
```

| 属性 | 默认 | 说明 |
|------|------|------|
| type | `'eova-icon'` | 图标集 |
| icons | 内置列表 | type=eova-icon 时 |
| title | `'选择图标'` | |
| modelValue | `''` | 如 `eova-icon-leaf` |

Events: `update:modelValue`, `change`

---

## EvValue

把值写入隐藏域（有 `name` 时），便于原生 form 提交；无双向 emit。

```vue
<ev-value name="id" v-model="id" />
```
