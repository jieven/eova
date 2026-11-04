# EvTable 表格

元数据表格：`object` → `meta_table` + `table_query`；也支持本地 `cols`/`rows`。内嵌分页 `EvPage`。

- **组件名:** `EvTable`
- **标签:** `<ev-table>`
- **源码:** `src/components/table/`

## 基础用法

```vue
<script setup>
const page = reactive({ page: 1, limit: 15, total: 0 })
const refTable = ref()
const onRowClick = (row, e) => {}
const query = () => refTable.value.query({ name: 'x' })
</script>

<template>
  <ev-table
    ref="refTable"
    object="user"
    biz="crud"
    :page="page"
    :height="500"
    @row-click="onRowClick"
    @selection-change="(rows) => {}"
  >
    <template #toolbar>
      <button class="eova-btn" @click="query">查询</button>
    </template>
  </ev-table>
</template>
```

## API

### Props

| 属性 | 说明 | 类型 | 默认值 |
|------|------|------|--------|
| object | 元对象编码 | `string` | — |
| biz | 业务码 | `string` | `'eova_table'` |
| cols | 本地列 | `Kv[]` | `[]` |
| rows | 本地行 | `Kv[]` | `[]` |
| page | 分页 | `TablePage` | `{ page:1, limit:15, total:0 }` |
| where | 查询条件 | `Kv` | `{}` |
| height | 高度 px | `number` | `500` |
| margin | 外边距 | `number` | `5` |
| maxHeight | 最大高度 | `string` | — |
| size | 行高类 | `string` | `'s40'` |
| sort | 默认排序方向 | `string` | `'asc'` |
| initQuery | 元数据后立刻查 | `boolean` | `true` |
| isEdit | 单元格编辑 | `boolean` | `false` |
| loading | 加载中 | `boolean` | `false` |
| resize | 列宽拖拽 | `boolean` | `true` |
| selectedKey / selectedKeys / selectedRows | 选中态 | — | — |
| rowClassName / cellClassName / rowStyle / cellStyle | 行/单元格样式 | string\|Function | |
| emptyDescription | 空数据文案 | `string` | — |
| design | 设计态 | `boolean` | false |
| id | 表 id | `string` | `'id'` |
| parentRef | 父容器 | HTMLElement\|null | — |

`TablePage`: `{ page, limit, total?, limits? }`

### Events（当前会触发）

| 事件 | 参数 |
|------|------|
| sort-change | `(field, sortAttr)` |
| update:selectedRows | rows |
| selection-change | rows |
| row-click | `(row, event)` |

> `row-dblclick` / `row-contextmenu` / `cell-double` / 若干 `update:*` 在类型中声明，实现可能未接全——以源码 `emit` 为准。

### Slots

| 插槽 | 说明 |
|------|------|
| toolbar | 表头上方工具栏 |

### Methods

| 方法 | 说明 |
|------|------|
| query(data) | 合并 where 并重新查询 |
| getSelectPks() | 选中主键 |
| getSelectRows() | 选中行 |
| removeRows(ids, idField?) | 本地删行 |
| getColWith() | 拖拽后的列宽信息 |

## EvPage 分页

**标签:** `<ev-page>` · **未全局注册**，由 Table 内部引用。

| 属性 | 默认 |
|------|------|
| modelValue（页码） | 1 |
| total | 0 |
| limit | 15 |
| limits | `[15,20,30,50,100,200,500]` |

Events: `update:modelValue`, `change` → `{ current, limit }`

显示条件：`page && pageTotal > 0 && page.limit < 99999`。

## 单元格

内部 `EvTableCell`：布尔显示 ✔/✖；其它走 `me.render.get('eova-table-cell')` → `EvRender`。可选岛屿区 `eova-table-island`。

## 演示

`src/view/crud/index.vue` · `src/view/cell/index.vue`
