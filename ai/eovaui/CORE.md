# EovaUI 核心 API（`me`）

全局实例：`import { me } from 'eovaui'` 或 `window.EovaUI`。  
类型：`src/core/types/core.d.ts` → `EovaUIExport`。  
构造：`src/core/index.ts`。

```ts
me = {
  ver,      // '1.0.0'
  conf,     // 全局配置 Record
  layer,    // LayerTool 弹层消息
  render,   // DIY/单元格渲染器注册表
  msg,      // 页内事件总线
  cross,    // 跨页/跨窗消息
  vue       // 自定义 app / 异步组件注册
}
```

---

## `me.layer` — 消息与弹窗

实现：`src/core/src/LayerTool.ts`（封装 `@layui/layui-vue` layer）。

| 方法 | 说明 |
|------|------|
| `msg(s)` | 普通消息（可 HTML） |
| `ok(s)` | 成功 |
| `no(s)` | 失败 |
| `wa(s)` | 警告 |
| `notify(title, s, type?)` | 通知；`type`: `ok\|no\|wa\|info` |
| `confirm(s, okCallback)` | 二次确认 |
| `input(placeholder, formType, okCallback)` | 输入；`formType`: `text\|password\|textarea` |
| `photos(imgs)` | 相册 |
| `open(title, url, width?, height?, okCallback)` | iframe/URL 模态窗 |

```ts
me.layer.ok('保存成功')
me.layer.confirm('确认删除？', () => doDelete())
```

---

## `me.render` — 渲染器注册表

实现：`src/core/src/renderer.ts`  
内置 DIY 入口：`src/diy/render/index.jsx`（如 select 默认/多选渲染）。

| 方法 | 说明 |
|------|------|
| `add(name, options)` | 注册/合并 |
| `get(name)` | 取渲染器，无则 `null` |
| `mixin(opts)` | 批量 add |
| `delete(name)` | 删除 |
| `forEach(cb)` | 遍历 |

常见 name（表格/上传可扩展）：

- `eova-table-cell` — 单元格
- `eova-table-island` — 表格岛屿区
- `eova-upload-record` — 上传记录自定义

```ts
me.render.add('eova-table-cell', {
  render(field, row, value) { /* return VNode[] | string */ }
})
```

`EvRender`（未全局注册）把 `(string | VNode)[]` 混排输出：含 `<>` 的字符串走 `v-html`。

---

## `me.vue` — 业务页 / DIY 组件

实现：`src/core/src/EovaVueApp.ts`（业务侧也有 `src/global/eova.vue.app.js` 镜像）。

| 方法 | 说明 |
|------|------|
| `component(name, url)` | 注册异步组件 JS 地址 |
| `app(code, { template?, script?, components? })` | 按元对象/业务码声明自定义页 |
| `created(app)` | VueApp 创建时 `app.use(EovaUI)` |
| `mount(app, code, container?)` | 加载 script/components 后挂载 |

```js
EovaUI.me.vue.component('GroupBuy', '/_component/GroupBuy.js')
EovaUI.me.vue.app('it_group_buy', {
  components: ['GroupBuy'],
})
```

> 自定义表单控件（`_component/*.js` + `eova_widget`）流程见业务 skill；本库只提供宿主 `ev-widget` 与挂载钩子。

---

## `me.msg` — 页内总线

实现：`src/utils/eova.msg.ts`

```ts
me.msg.on('event', (data) => {})
me.msg.emit('event', data)
me.msg.off('event', handler?)
```

`EvWidget` 用频道：`ev-widget-value-${id}` 回传值。

---

## `me.cross` — 跨上下文消息

实现：`src/core/src/EovaMsgCross.ts`  
用于弹层/iframe 与父页通信（打开窗、回传选中等）。写跨页联调时先读源码再发事件名。

---

## `me.conf`

全局可变配置对象 `Record<string, any>`（`EovaConfig`）。改主题、开关等前先查现有键，避免盲写。

---

## AI 调用清单

写业务代码时优先：

1. 用户反馈 → `me.layer.ok/no/confirm`  
2. 表格单元格定制 → `me.render.add`  
3. DIY 控件注册 → `me.vue.component` + `me.vue.app`  
4. 同页解耦 → `me.msg`  
