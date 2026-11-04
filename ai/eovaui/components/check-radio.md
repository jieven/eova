# EvCheck / EvRadio

选项结构统一为 `SelectItem`：`{ val, txt, icon?, info?, disabled?, selected? }`。

---

## EvCheck 多选组

**标签:** `<ev-check>` · **源码:** `src/components/check/`

### 基础用法

```vue
<script setup>
const items = [
  { val: '1', txt: 'A' },
  { val: '2', txt: 'B' },
]
const v = ref('1')
const ck = ref()
</script>
<template>
  <ev-bool type="check" v-model="all" @change="(c) => ck.selectAlls(c)" />
  <ev-check ref="ck" name="cks" :items="items" v-model="v" />
</template>
```

### 值规则

`update:modelValue` / `change` → **已选 val 的逗号串**，如 `"1,2"`。

### Props

| 属性 | 说明 | 类型 | 默认 |
|------|------|------|------|
| items | 选项（会被写入 selected） | `SelectItem[]` | **必填** |
| modelValue | 绑定值 | string\|number\|object | `''` |
| value | 静态初值 | 同上 | — |
| name | 隐藏域 | string | — |
| disabled | 全部禁用 | boolean | false |

### Methods

| 方法 | 说明 |
|------|------|
| selectAlls(checked) | 全选/全不选 |
| txt | 文案相关 |

---

## EvRadio 单选组

**标签:** `<ev-radio>` · **源码:** `src/components/radio/`

### 基础用法

```vue
<ev-radio :items="items" v-model="v" />
<ev-radio option="eova_role" v-model="role" />
```

无 `option` 时必须有 `items`。`modelValue` 为空时默认选中 **第一项 val**。

### Props

| 属性 | 说明 | 类型 | 默认 |
|------|------|------|------|
| items | 本地选项 | SelectItem[] | — |
| option | 远程选项表达式 | string | — |
| modelValue | 选中 val | string\|number | `''` |
| value | 静态 | string\|number | — |
| name | 分组名 | string | — |
| disabled | 禁用 | boolean | false |

### Events

`update:modelValue`, `change`
