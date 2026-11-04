# EvSortable / EvProgress / EvWidget / EvButton / EvRender

---

## EvSortable 拖拽排序

**标签:** `<ev-sortable>` · **依赖:** `vuedraggable` · **源码:** `src/components/sortable/`

```vue
<ev-sortable v-model="list" text_field="name" order_field="num" />
```

| 属性 | 说明 | 默认 |
|------|------|------|
| modelValue | 列表 | — |
| text_field | 展示字段 | `'name'` |
| order_field | 排序/key 字段 | `'num'` |

Event: `update:modelValue`  
演示：`src/view/sort/index.vue`

---

## EvProgress 进度条

**标签:** `<ev-progress>` · **源码:** `src/components/progress/`

```vue
<ev-progress :value="90" text="默认进度条" />
<ev-progress :value="60" text="渐变" color="#1ea0fc,#67c23a" />
<ev-progress :value="60" color="#67C23A" size="10" />
```

| 属性 | 说明 | 默认 |
|------|------|------|
| value | 0–100 | 50 |
| color | 单色或逗号渐变 | `var(--eova-color_main),#1abc9c` |
| text | 文案前缀 | `''` |
| size | 高度 px | `'18'` |
| text_inside | 条内文字 | true |
| text_align | `'left'\|'right'` | `'right'` |

---

## EvWidget DIY 宿主

**标签:** `<ev-widget>`（安装别名 EvDiy，注册名仍 `EvWidget`）  
**源码:** `src/components/widget/`

注入 HTML，经 `me.msg` 频道回传值：`ev-widget-value-${id}`。

```vue
<ev-widget id="w1" :html="html" v-model="val" />
```

| 属性 | 说明 |
|------|------|
| id | **必填**，实例/频道 id |
| html | 注入 HTML |
| modelValue | 绑定值 |
| type / name / object / field / disabled | 元数据上下文 |

Events: `update:modelValue`, `input`, `change`（及类型中的 focus/blur）

---

## EvButton（勿用）

**标签:** `<ev-button>` — **WIP**，模板内部是 `ev-input`，不是操作按钮。

业务请用：

```html
<button class="eova-btn">确定</button>
<button class="eova-btn_icon"><i class="eova-icon-set"></i></button>
```

---

## EvRender 混合渲染

**未全局注册。** 源码：`src/components/render/`

```vue
<ev-render :data="['纯文本', '<b>HTML</b>', someVNode]" />
```

| 项 | 规则 |
|----|------|
| 字符串含 `<` `>` | `v-html` |
| 普通字符串 | 文本节点 |
| 其它 | `<component :is="vn" />` |

表格单元格、上传记录等通过 `me.render.get(name).render(...)` 产出后交给 EvRender。
