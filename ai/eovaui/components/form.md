# EvForm 表单

元数据驱动表单。根据 `object` 请求 `meta_form`，按字段类型渲染内置控件；`pk` 存在时再拉 `form_data` 回填。

- **组件名:** `EvForm`
- **标签:** `<ev-form>`
- **源码:** `src/components/form/`

## 基础用法

```vue
<template>
  <ev-form
    ref="refForm"
    mode="create"
    object="meta_product"
    biz="test"
    v-model="data"
    @ready="onReady"
    @submit="onSave"
  />
</template>

<script setup>
import { ref } from 'vue'
const data = ref({})
const refForm = ref()
const onReady = (fieldMap) => { /* Map<fieldEn, instance> */ }
const onSave = (formData) => { /* 回车触发 */ }
</script>
```

### 编辑 / 详情

```vue
<ev-form mode="update" :object="code" :pk="pk" v-model="data" />
<ev-form mode="read" :object="code" :pk="pk" v-model="data" />
```

### 查询条

```vue
<ev-form mode="query" :object="code" v-model="query" @submit="doQuery" @resize="onResize" />
```

## 字段类型映射

| 元字段 type | 控件 |
|-------------|------|
| 文本框/数字框/精准框/密码框/颜色 | `ev-input` |
| 布尔框 | `ev-bool` |
| 图标框 | `ev-icon` |
| 文本域/编辑框/JSON | `ev-input type="texts"` |
| 单选框 | `ev-radio` |
| 下拉框 | `ev-select` |
| 下拉树 | `ev-select type="tree"` |
| 查找框 | `ev-find` |
| 日期/日期框 | `ev-time type="date"` |
| 时间/时间框 | `ev-time` |
| 日期段；query+日期 | `ev-times type="date"` |
| 时间段；query+时间 | `ev-times` |
| 文件/图片 | `ev-upload` |
| 其他 | `<component :is="f.type">` |
| diy.status==3 | 隐藏域 |
| diy.status==2 或 mode=read | 禁用 |

## API

### Props

| 属性 | 说明 | 类型 | 默认值 |
|------|------|------|--------|
| name | 表单名 | `string` | `'eova_form'` |
| mode | 场景 | `'create' \| 'read' \| 'update' \| 'query'` | `'create'` |
| modelValue / v-model | 表单数据对象 | `Record<string, any>` | — |
| object | 元对象编码 | `string` | — |
| pk | 主键（update/read 拉数） | `string` | — |
| biz | 业务编码 | `string` | — |
| height / maxHeight / size | 尺寸 | `string \| number` | — |

### Events

| 事件 | 说明 | 回调参数 |
|------|------|----------|
| update:modelValue | 数据变更（深度 watch） | `data` |
| ready | 元数据就绪、子实例收集完毕 | `Map<fieldEn, componentInstance>` |
| resize | 表单高度变化 | `height` |
| submit | 回车提交 | `data` |

### Methods（ref）

| 方法 | 说明 |
|------|------|
| getData() | 当前表单数据 |
| reset() | 清空为 `{}` |
| validate(diyRules?) | 校验，返回 boolean |
| getFieldInstance(fieldName) | 取字段对应组件实例 |

## 演示

`src/view/add` · `src/view/update` · `src/view/detail` · `src/view/crud`
