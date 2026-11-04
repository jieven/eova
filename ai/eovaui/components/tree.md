# EvTree 树

封装 LayUI `LayTree`。数据为 **扁平列表**（`id` + `pid`），组件内组装；也可 `object` 远程加载。

- **组件名:** `EvTree`
- **标签:** `<ev-tree>`
- **源码:** `src/components/tree/`

## 基础用法

```vue
<script setup>
const cityData = [
  { id: 1, pid: 0, name: '浙江', open: true },
  { id: 2, pid: 1, name: '杭州' },
]
const checked = ref([])
</script>

<template>
  <ev-tree :data="cityData" @node-click="(n) => console.log(n)" />
  <ev-tree
    :data="cityData"
    multiple
    v-model:checked="checked"
    v-model:checked-node="nodes"
    v-model:items="items"
  />
  <ev-tree object="eova_menu_code" />
</template>
```

## API

### Props

| 属性 | 说明 | 类型 | 默认值 |
|------|------|------|--------|
| data | 扁平节点 | `TreeNode[]` | `[]` |
| conf | 字段映射 | `TreeConf` | `{ id:'id', title:'name', pid:'pid', root:'0' }` |
| object | 远程元对象 | `string` | — |
| multiple | 多选复选框 | `boolean` | false |
| disabled | 禁用 | `boolean` | false |
| selected | 选中 key | number\|string | — |
| checked / v-model:checked | 勾选 id 列表 | array | `[]` |
| checkedNode / v-model:checkedNode | 勾选节点 | `{[id],[title]}[]` | `[]` |
| items / v-model:items | `{val,txt}[]` | array | `[]` |
| name | name | string | `'tree'` |
| option | 预留表达式 | string | —（尚未支持） |

`TreeConf`: `{ id, title, pid, root, spread?, icon?, children? }`

### Events

| 事件 | 参数 |
|------|------|
| update:checked | id[] |
| update:checkedNode | CheckedNode[] |
| update:items | TreeItem[] |
| node-click | node |

### Methods

| 方法 | 说明 |
|------|------|
| reload | 重载 |
| getChecked | 勾选 id |
| getCheckedNode | 勾选节点 |
| getItems | `{val,txt}[]` |
| filter(value) | 按标题过滤 |

传 `object` 时由组件内部拉取树数据。

## 演示

`src/view/widget/index.vue`；下拉树场景见 `EvSelect type="tree"`。
