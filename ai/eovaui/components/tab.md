# EvTab 选项卡

**标签:** `<ev-tab>` + `<ev-tab-item>`  
**源码:** `src/components/tab/`

## 基础用法

```vue
<script setup>
const curr = ref('tab1')
</script>

<template>
  <ev-tab v-model="curr">
    <ev-tab-item id="tab1" title="选项一">内容1</ev-tab-item>
    <ev-tab-item id="tab2" title="选项2">内容2</ev-tab-item>
  </ev-tab>

  <ev-tab type="card" v-model="curr">…</ev-tab>
  <ev-tab type="sys" v-model="curr">…</ev-tab>
</template>
```

`v-model` = 当前激活的 `EvTabItem.id`。

## API

### EvTab Props

| 属性 | 说明 | 类型 | 默认 |
|------|------|------|------|
| modelValue | 激活 id | string | — |
| type | 样式 | `'base' \| 'card' \| 'sys'` | `'base'` |

### EvTab Events

| 事件 | 参数 |
|------|------|
| update:modelValue / change | tab id |
| close | tab id（类型已有；标题关闭 UI 未完全接通） |

### EvTab Slots

| 插槽 | 说明 |
|------|------|
| default | 放置 `ev-tab-item` |

### EvTabItem Props

| 属性 | 说明 | 类型 |
|------|------|------|
| id | Tab key（必填） | string |
| title | 标题 | string \| Function |
| icon | 声明有，当前标题区未渲染 | string \| Function |
| closable | 声明有，关闭 UI 未展示 | boolean \| string |

### EvTabItem Slots

| 插槽 | 说明 |
|------|------|
| default | 面板内容（`active == id` 时显示） |

## 演示

`src/view/widget/index.vue`
