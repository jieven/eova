# EvFind 查找框

弹层内嵌表格，按 Eova `option` 选数据；显示文案经 `widget_text` 翻译。

- **组件名:** `EvFind`
- **标签:** `<ev-find>`
- **源码:** `src/components/find/`

## 基础用法

```vue
<ev-find option="users" v-model="userId" placeholder="选择用户" />
<ev-find option="users" :multiple="true" v-model="userIds" />
```

### 触发形态

```vue
<ev-find mode="input" option="users" v-model="v" />
<ev-find mode="a" title="用户切换" option="users" v-model="v" />
<ev-find mode="button" title="选择" option="users" v-model="v" />
```

单选选中后关闭弹层；中键点查找图标可清空标签。

## 值规则

`modelValue` / `change` 均为 **逗号分隔的 val 字符串**（多选亦然）。

## API

### Props

| 属性 | 说明 | 类型 | 默认值 |
|------|------|------|--------|
| mode | 触发 UI | `'input' \| 'a' \| 'button' \| 'text'` | `'input'` |
| title | 标题/链接文案 | `string` | `'请选择'` |
| name | 隐藏域名 | `string` | `'find'` |
| option | 选项/查找编码 | `string` | — |
| modelValue | 绑定值 | `any` | `null` |
| value | 静态初值 | `string` | `''` |
| multiple | 多选 | `boolean` | `false` |
| disabled | 禁用 | `boolean` | `false` |
| placeholder | 占位 | `string` | — |
| style | 输入样式 | `string` | — |

### Events

| 事件 | 参数 |
|------|------|
| update:modelValue / change | 逗号串 |
| search | keyword |

### Methods

| 方法 | 说明 |
|------|------|
| setDisabled(boolean) | 禁用 |
| setOption(option) | 切换 option 并重载 |

## 演示

`src/view/form/index.vue`
