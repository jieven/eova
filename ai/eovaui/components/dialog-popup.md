# EvDialog / EvPopup

---

## EvDialog 对话框

可拖拽标题栏的模态窗，Teleport 到 `layerContainer`。

**标签:** `<ev-dialog>` · **源码:** `src/components/dialog/`

### 基础用法

```vue
<button @click="visible = true">打开</button>
<ev-dialog
  title="自定义窗口"
  width="500px"
  height="300px"
  v-model:visible="visible"
>
  <p>内容</p>
  <template #footer>
    <button @click="visible = false">关闭</button>
  </template>
</ev-dialog>
```

### Props

| 属性 | 说明 | 类型 | 默认值 |
|------|------|------|--------|
| title | 标题 | string | `'EovaDialog'` |
| width | 宽 | string | `'1000px'` |
| height | 高 | string | `'600px'` |
| visible / v-model:visible | 显隐 | boolean | false |
| layerContainer | Teleport 目标 | string | `'body'` |

### Slots

| 插槽 | 说明 |
|------|------|
| default | 主体 |
| footer | 底栏（有内容才渲染） |

### Events

`update:visible`（点关闭发出）。类型里还有 `update:modelValue`/`change`，当前 vue **未 emit**。

---

## EvPopup 弹出层

锚定触发器的浮层（下拉菜单、气泡等）。**没有 `visible` prop**，用 ref 方法控制。

**标签:** `<ev-popup>` · **源码:** `src/components/popup/`

### 基础用法

```vue
<ev-popup ref="popupRef" :click-outside-to-close="true">
  <button>打开菜单</button>
  <template #content>
    <ul @click="popupRef.hide()">
      <li>选项1</li>
    </ul>
  </template>
</ev-popup>

<ev-popup trigger="hover" placement="bottom">
  <button class="eova-btn_icon"><i class="eova-icon-set"></i></button>
  <template #content>…</template>
</ev-popup>
```

**约束：**

1. 默认插槽必须是**真实元素**（事件代理到第一个子节点）。
2. `#content` 内不要直接依赖未暴露的内部状态；关闭请调父级 `ref.hide()`。

### Props（常用）

| 属性 | 说明 | 默认 |
|------|------|------|
| trigger | `'click'\|'hover'\|'focus'\|'contextMenu'` 或数组 | `'click'` |
| placement | 方位（含兼容旧值） | `'bottom-start'` |
| disabled | 禁用 | false |
| autoFitPosition | 视口内翻转 | true |
| autoFitWidth | 宽度对齐触发器 | false |
| autoFitMinWidth | 最小宽度=触发器 | true |
| clickToClose / blurToClose / clickOutsideToClose | 关闭策略 | true |
| contentOffset | 间距 px | 2 |
| mouseEnterDelay / mouseLeaveDelay / focusDelay | ms | 150 |
| alignPoint | 跟鼠标点 | false |
| contentClass / contentStyle | 面板样式 | — |
| layerContainer | Teleport | `'body'` |

### Events

| 事件 | 时机 |
|------|------|
| show | show() 后 |
| hide | hide() 后 |

### Methods

| 方法 | 说明 |
|------|------|
| show(delay?) | 打开 |
| hide(delay?) | 关闭 |
| toggle() | 切换 |
| addChildRef(ref) | 嵌套 popup |

## 演示

`src/view/widget/index.vue` · `src/view/crud/index.vue`（工具栏 hover 菜单）
