# EvSelect 选择器

下拉列表 / 下拉树 / DIY 面板。支持本地 `items`、远程 `option`、树 `object`。

- **组件名:** `EvSelect`
- **标签:** `<ev-select>`
- **源码:** `src/components/select/`

## 基础用法

### 远程选项

```vue
<ev-select option="eova_users;10" v-model="userId" placeholder="选择用户" />
```

### 本地选项

```vue
<script setup>
const items = [
  { val: 1, txt: '苹果' },
  { val: 2, txt: '香蕉', disabled: true },
]
const v = ref('1')
</script>
<template>
  <ev-select :items="items" v-model="v" />
  <ev-select :items="items" v-model="ids" multiple />
</template>
```

### 下拉树

```vue
<ev-select
  type="tree"
  object="eova_menu_code"
  :tree-conf="menuConf"
  v-model="checked"
  v-model:items="flatItems"
  @node-click="onNode"
/>
```

### DIY 面板

```vue
<ev-select type="diy" v-model="v">
  <!-- 自定义下拉内容 -->
  <div>…</div>
</ev-select>
```

## 值规则（AI 必读）

| 场景 | modelValue |
|------|------------|
| 单选 | `string \| number` |
| 多选 + 外部传入 **Array** | 保持 **Array** |
| 多选 + 外部非 Array | **`"1,2,3"` 逗号串** |
| 树多选 | 同上（id 列表） |

## API

### Props

| 属性 | 说明 | 类型 | 默认值 |
|------|------|------|--------|
| name | 隐藏域 name | `string` | `'select'` |
| type | 模式 | `'select' \| 'tree' \| 'diy'` | `'select'` |
| modelValue | 绑定值 | `any` | `null` |
| items / v-model:items | 本地选项 | `SelectItem[]` | — |
| option | 选项表达式 | `string` | — |
| object | 树元对象 | `string` | — |
| treeConf | 树字段映射 | `TreeConf` | — |
| multiple | 多选 | `boolean` | `false` |
| disabled | 禁用 | `boolean` | `false` |
| placeholder | 占位 | `string` | — |
| allowClear | 可清空 | `boolean` | `false` |
| showSearch | 搜索 | `boolean` | `false` |
| autoFitWidth | 弹层跟触发宽度 | `boolean` | `true` |
| contentClass / contentStyle | 弹层样式 | — | — |

`SelectItem`: `{ val, txt, icon?, info?, disabled?, selected? }`

### Events

| 事件 | 说明 | 参数 |
|------|------|------|
| update:modelValue | 值变化 | value |
| update:items | 选项变化 | SelectItem[] |
| change | 同值变化 | value |
| search | 搜索 | keyword |
| node-click | 树节点点击 | node |

### Slots

| 插槽 | 说明 |
|------|------|
| default | 仅 `type="diy"` 时作为下拉体 |

### Methods

| 方法 | 说明 |
|------|------|
| setDisabled(boolean) | 禁用切换 |
| setOption(option) | 重载远程选项 |
| getItems() | 全部选项 |
| getSelectItems | 已选 SelectItem（computed） |
| getSelectItemVal | 已选值串 `"1,2"` |
| getSelectItemTxt | 已选文案串 |

## 演示

`src/view/form/index.vue` · `src/view/widget/index.vue`
