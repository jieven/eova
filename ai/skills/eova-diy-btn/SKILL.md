---

## name: eova-diy-btn

description: 为 EovaMeta 开发自定义功能按钮（btn.js 扩展）。覆盖简单 axios 交互与复杂旁挂
  ev-dialog（app.js/app.vue/app.css + EovaMeta.include/dom/single）。在用户提到
  EovaMeta自定义功能、自定义按钮、DIY 按钮、btn.js、自定义弹窗、功能扩展类交互时使用。

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
复杂示例：`_view/menu/auth/`、`_view/auth/`（字段权限）

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
- 开发调试可加 `?v=${x.time.now()}` 防缓存，上线前由开发者自行去掉

### 代码风格

- **文件顶部不要写注释**（`btn.js` / `app.js` 开头直接 import / 常量 / 函数）
- **无参时** `open()` **不传参**；有参再 `open(id)` / `open({…})`
- **axios 地址统一提到顶部**，用 `const urls = { key: '/path' }` 按 key 管理，调用处写 `urls.list` / `urls.save` 等，禁止散落字面量
- **常规表单布局优先用** `eova-form`（`eova-form-field` / `eova-form-label` / `eova-form-msg`）；默认不写 `width` / `max-width`；不满足再补最少自定义样式；**除非用户指定其它布局**
- **前端校验提示默认用** `me.layer.msg()`（未选行、必填为空等）；接口失败用 `me.layer.no`，成功可用 `me.layer.ok` / `msg`，二次确认用 `confirm`
- **旁挂弹窗底栏**：右下角绝对定位「确认 / 取消」；确认走保存（成功后关窗），取消只关窗 `visible = false`；保存中 `:disabled="saving"`，取消勿绑成保存

```js
const urls = {
    save: '/auth/fieldSave',
}

axios.post(urls.save, payload)
```

```html
<form method="post" class="eova-form" @submit.prevent>
  <div class="eova-form-field">
    <label class="eova-form-label required">文本</label>
    <ev-input name="text" v-model="txt"></ev-input>
    <div class="eova-form-msg">特别提示</div>
  </div>
</form>

<button
  type="button"
  class="eova-btn eova-btn_success"
  style="position: absolute; bottom: 5px; right: 60px"
  :disabled="saving"
  @click="saveRule"
>
  确认
</button>
<button
  type="button"
  class="eova-btn eova-btn_base"
  style="position: absolute; bottom: 5px; right: 5px"
  :disabled="saving"
  @click="visible = false"
>
  取消
</button>
```

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
const urls = {
    run: '/task/run',
}

function run() {
    const rows = uzoo.app.refTable.value.getSelectRows()
    if (x.isEmpty(rows)) {
        me.layer.msg('请先选择一行数据')
        return
    }
    axios.post(urls.run, rows[0])
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
- [ ] btn.js 顶部无文件头注释；有接口则 urls = {…}
- [ ] btn.js 定义与 event 同名的全局 function
- [ ] 校验选中行/节点
- [ ] axios 用 urls.key + ret.state 分支
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
// 无参最简（开发期可带 ?v= 防缓存）
function xxxx() {
    import(`/eova/_view/auth/app.js?v=${x.time.now()}`).then(app => app.open())
}

// 有参
function xxxx() {
    const rows = uzoo.app.refTable.value.getSelectRows()
    if (x.isEmpty(rows)) {
        me.layer.msg('请先选择一行数据')
        return
    }
    // 如何取参根据业务和模版略有不同。
    const id = rows[0].id;
    import(`/eova/_view/menu/auth/app.js?v=${x.time.now()}`).then(app => app.open(id))
}
```

### app.js 骨架

```js
const {createApp, ref} = Vue
const {me} = EovaUI
const {include, dom, single} = EovaMeta

const ID = 'eova-<biz>-<action>'
const TP = '/eova/_view/<biz>/<action>/app.vue'
const CSS = '/eova/_view/<biz>/<action>/app.css'

const urls = {
    list: '/api/...',
    save: '/api/...',
}

async function mount() {
    include(CSS)

    const visible = ref(false)

    const app = createApp({
        setup() {
            return {visible}
        },
        template: await include(TP),
    })
    app.use(EovaUI)
    app.mount(dom(ID))

    return {
        show(...args) {
            visible.value = true
        },
    }
}

export const open = single(mount)
```

### app.vue 骨架

```vue
<ev-dialog
  title="标题"
  width="760px"
  height="500px"
  layer-container="body"
  v-model:visible="visible"
>
  <div style="padding: 12px 16px 20px;">
    <form method="post" class="eova-form" @submit.prevent>
      <!-- eova-form-field …；html 内联属性用 kebab-case -->
    </form>
    <button
      type="button"
      class="eova-btn eova-btn_success"
      style="position: absolute; bottom: 5px; right: 60px"
      :disabled="saving"
      @click="saveRule"
    >确认</button>
    <button
      type="button"
      class="eova-btn eova-btn_base"
      style="position: absolute; bottom: 5px; right: 5px"
      :disabled="saving"
      @click="visible = false"
    >取消</button>
  </div>
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
- [ ] btn.js / app.js 顶部无文件头注释
- [ ] app.js 顶部 urls = {…}，axios 不写散落字面量
- [ ] btn.js：校验 + 绝对路径 import + open(传参)
- [ ] app.js：include(CSS) / await include(TP) / createApp + use(EovaUI) / mount(dom(ID))
- [ ] export const open = single(mount)；show 内打开并加载数据
- [ ] app.vue：ev-dialog + layer-container="body" + v-model:visible
- [ ] 底栏确认=保存（成功关窗）/ 取消=`visible=false`，勿都绑保存
- [ ] app.css：用 #ID 限定，避免污染外层（无额外样式可省略）
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


