# EvTime / EvTimes

基于 LayUI `lay-date-picker`，外包 `ev-input type="time"`。

---

## EvTime 单点时间

**标签:** `<ev-time>` · **源码:** `src/components/time/`

```vue
<ev-time v-model="datetime" placeholder="请选择时间" />
<ev-time type="date" v-model="day" />
<ev-time type="month" v-model="ym" />
```

### Props

| 属性 | 说明 | 类型 | 默认值 |
|------|------|------|--------|
| modelValue | 绑定值 | any | null |
| value | 静态 | string | `''` |
| type | 选择器类型 | `'date'\|'datetime'\|'year'\|'month'\|'time'\|'yearmonth'` | **`'datetime'`** |
| name | name | string | `'time'` |
| placeholder | 占位 | string | `'请选择时间'` |
| disabled | 禁用 | boolean | false |
| size | 尺寸 | `'lg'\|'md'\|'sm'\|'xs'` | `'sm'` |

### Events / Methods

Events: `update:modelValue`, `change`  
Methods: `setDisabled(boolean)`

快捷项：明天 / 后天 / 7天后 … / 1年后。

---

## EvTimes 范围

**标签:** `<ev-times>` · **源码:** `src/components/times/`

```vue
<ev-times
  v-model="range"
  type="date"
  :placeholder="['开始日期', '结束日期']"
/>
<ev-times
  v-model="rangeDt"
  type="datetime"
  :placeholder="['开始时间', '结束时间']"
/>
```

### 与 EvTime 差异

| 项 | EvTimes |
|----|---------|
| modelValue | 范围，通常为 `[start, end]` |
| placeholder | **`string[]`** |
| 快捷 | 清空 / 近1天 / 近3天 … / 近1年 |

其余 Props（`type`/`disabled`/`size`/`setDisabled`）同族。

## 表单映射

| 元字段 | 控件 |
|--------|------|
| 日期框/日期 | `ev-time type="date"` |
| 时间框/时间 | `ev-time` |
| 日期段；query+日期 | `ev-times type="date"` |
| 时间段；query+时间 | `ev-times` |
