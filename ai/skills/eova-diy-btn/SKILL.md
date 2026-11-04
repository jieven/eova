---

## name: eova-diy-btn
description: 
  为 EovaMeta 开发自定义功能按钮（btn.js 扩展）。覆盖简单 axios 交互与复杂旁挂
  ev-dialog（app.js/app.vue/app.css + EovaMeta.include/dom/single）。在用户提到
  EovaMeta自定义功能、自定义按钮、DIY 按钮、btn.js、旁挂弹窗、功能扩展类交互时使用。

# EovaMeta 自定义功能（eova-diy-btn）

基于列表/树等业务页工具栏按钮的 **UI 脚本扩展**（`btn.ui` → 静态 `btn.js`）。  
入口函数名须与按钮 **event** 一致，由模版 `handlerButtonEvent` 调用。

**核心约束（复杂场景）：**

- 注入点只从 `btn.js` 开始，**不侵入外层页面**（不改 tree/table 模版、不塞 `uzoo.vue.setup`）
- **不要**用 `eova.vue.config.js` 的 `vue.app` 注册（那是全局元对象/列表模版扩展）
- **不要**再做整页 `app.html` + `me.layer.open(url)` iframe（旧写法）；复杂交互用本页旁挂 `ev-dialog`
- 打开传参用 `open(...args)` → `show(...args)`，**不要**往 `uzoo` 塞临时 payload

参考实现：`view/src/main/resources/webapp/eova/_view/menu/auth/`

---



## 场景选型


| 场景        | 文件                                             | 何时用                                      |
| --------- | ---------------------------------------------- | ---------------------------------------- |
| **1. 简单** | 仅 `btn.js`                                     | 选行/节点后一次 `axios.post`（或 `window.open`）即可 |
| **2. 复杂** | `btn.js` + `app.js` + `app.vue` + 可选 `app.css` | 需要表单/矩阵/多步交互弹窗                           |


简单示例：`_view/task/run/btn.js`  
复杂示例：`_view/menu/auth/`

---



## 公共约定



### 目录与按钮配置

```
view/.../eova/_view/<biz>/<action>/
  btn.js          # 必填：window 上的 event 函数
  app.js          # 复杂：旁挂 Vue 根 + export open
  app.vue         # 复杂：模板片段（通常含 ev-dialog）
  app.css         # 可选：样式
```

- 按钮 `ui` 填：`/eova/_view/<biz>/<action>/btn.js`
- 按钮 `event` 填：与 `btn.js` 里函数名相同（如 `auth`、`run`）
- 动态 `import` **必须用绝对路径**（`btn.js` 是经典 script，相对路径相对的是页面 URL）



### 可用全局


| 全局                  | 用途                                                       |
| ------------------- | -------------------------------------------------------- |
| `me` / `EovaUI.me`  | `me.layer.msg/ok/no/confirm` 等                           |
| `x` / `EovaTools.x` | 工具（判空等）                                                  |
| `uzoo.app`          | **只读**外层页状态：`treeId`、`refTable`、勾选等                      |
| `uzoo.page`         | 菜单/对象等页面参数                                               |
| `axios`             | 接口                                                       |
| `EovaMeta`          | `include` / `dom` / `single`（列表页经 `_block/meta.html` 引入） |


---



## 场景 1：简单 — 仅 btn.js

```js
// 运行一次（示意）
function run() {
    const rows = uzoo.app.refTable.value.getSelectRows()
    if (x.isEmpty(rows)) {
        me.layer.msg('请先选择一行数据')
        return
    }
    axios.post('/task/run', rows[0])
        .then((res) => {
            const ret = res.data
            if (ret.state === 'ok') {
                me.layer.msg('运行成功')
                uzoo.app.refTable.value.query()
            } else {
                me.layer.no(ret.msg)
            }
        })
        .catch((e) => me.layer.msg('客户端请求异常: ' + e.message))
}
```

清单：

```
- [ ] btn.js 定义与 event 同名的全局 function
- [ ] 校验选中行/节点
- [ ] axios.post + ret.state 分支
- [ ] 成功后按需刷新表格/树
```

---



## 场景 2：复杂 — 旁挂 ev-dialog



### 文件职责


| 文件        | 职责                                                                              |
| --------- | ------------------------------------------------------------------------------- |
| `btn.js`  | 校验 → `import('/…/app.js').then(m => m.open(...args))`                           |
| `app.js`  | `single(mount)`；`mount` 内 `include` css/vue、`createApp`、`dom(id)`；返回 `{ show }` |
| `app.vue` | 模板片段（根上 `<ev-dialog v-model:visible="visible">`）                                |
| `app.css` | 用挂载点 id 限定作用域，如 `#eova-menu-auth …`                                             |




### btn.js

```js
function auth() {
    const id = uzoo.app.treeId.value
    if (!id) {
        me.layer.msg('请选择一个菜单')
        return
    }
    // 参数直接传给 open → show，可多个或对象
    import('/eova/_view/menu/auth/app.js').then((app) => app.open(id))
}
```



### app.js 骨架

```js
const {createApp, ref } = Vue
const {me} = EovaUI
const {include, dom, single} = EovaMeta

const ID = 'eova-<biz>-<action>'           // 唯一挂载点 id
const TP = '/eova/_view/<biz>/<action>/app.vue'
const CSS = '/eova/_view/<biz>/<action>/app.css'

async function mount() {
    include(CSS)

    const visible = ref(false)
    // …业务 refs 与方法…

    const app = createApp({
        setup() {
            return { visible /*, … */ }
        },
        template: await include(TP),
    })
    app.use(EovaUI)
    app.mount(dom(ID))

    return {
        show(/* 与 open 实参一致 */) {
            visible.value = true
            // 拉数 / 重置表单…
        },
    }
}

export const open = single(mount)
```



### app.vue 骨架

```vue
<ev-dialog
  title="标题"
  width="1200px"
  height="700px"
  layer-container="body"
  v-model:visible="visible"
>
  <!-- 业务内容；html 内联属性用 kebab-case -->
</ev-dialog>
```



### EovaMeta API（必会）


| API               | 作用                                                  |
| ----------------- | --------------------------------------------------- |
| `include(url)`    | `.css` 注入 link（同 href 一次）；其它（如 `.vue`）`fetch` 文本    |
| `dom(id)`         | body 下取/建挂载点，供 `app.mount(dom(id))`                 |
| `single(factory)` | 懒加载单例；`factory` 返回 `{ show(...args) }`；并发共用一次 mount |


源码：`view/src/main/resources/webapp/eova/ui/meta/eova.meta.js`

### 复杂场景清单

```
- [ ] btn.js：校验 + 绝对路径 import + open(传参)
- [ ] app.js：include(CSS) / await include(TP) / createApp + use(EovaUI) / mount(dom(ID))
- [ ] export const open = single(mount)；show 内打开并加载数据
- [ ] app.vue：ev-dialog + layer-container="body" + v-model:visible
- [ ] app.css：用 #ID 限定，避免污染外层
- [ ] 不写 uzoo.app = …（会冲掉列表页作用域）
- [ ] 不引入 eova.base.js / 不做第二套整页 html
```

---



## 反例


| 不要                                               | 要                                  |
| ------------------------------------------------ | ---------------------------------- |
| `me.layer.open(title, '/…/app.html')` 再 boot Vue | `ev-dialog` + `EovaMeta` 旁挂        |
| `vue.app('tree_xxx', { template })` 干按钮弹窗        | 只从 `btn.js` 动态 import              |
| `import('./app.js')`（经典 script 相对路径）             | `import('/eova/_view/.../app.js')` |
| 打开前 `uzoo.xxx = payload`                         | `open(id)` / `open({ id, mode })`  |
| 弹窗 `setup` 里 `return uzoo.app = {…}`             | 独立闭包 + `show`                      |


---



## 与其它技能边界


| 技能               | 区别                                     |
| ---------------- | -------------------------------------- |
| `eova-component` | 表单 DIY 控件 `_component` + `eova_widget` |
| **eova-diy-btn** | 工具栏/按钮 **功能扩展**（简单请求或旁挂弹窗）             |


