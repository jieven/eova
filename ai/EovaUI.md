# EovaUI 规范

> 主题样式演示：`demo/src/main/webapp/_view/theme/`（线上：`/main`）  
> 组件演示：`E:\project\eova-ui\eova-ui\src\view\widget`（`index.vue`）  
> 组件源码：`E:\project\eova-ui\eova-ui\src\components`  
> 样式入口：`/eova/lib/eova/eovaui.css`　JS 入口：`/eova/lib/eova/eovaui.js`  
> 全局实例：`const { me } = EovaUI`，`app.use(EovaUI)` 后可用 `<ev-*>`

## 创作原则（必读）

### 1. 基础控件与样式：优先复用 EovaUI

以下属于「控件 / 视觉皮肤」，**必须优先用 EovaUI 现成实现**，禁止平行造轮子：

| 类别 | 复用对象 | 反例 |
|------|----------|------|
| 颜色 / Token | `--eova-color_*` | 自写一套主色、成功色 |
| 按钮外观 | `button` / `eova-btn_*` | `xxx__btn`、自写 border/hover |
| 面板皮肤 | `eova-panel` | 自写白底边框阴影卡片 |
| 提示文案 | `eova-notes` / `me.layer.msg` | 自写 alert 条样式 |
| 徽章 / 图标 | `eova-badge*` / `eova-icon-*` | 自写圆点、标签皮 |
| 表格 / 原生表单 | `eova-table` / `eova-form-native` | 无必要重写控件皮 |
| 弹层交互 | `me.layer.*` / `ev-dialog` / `ev-popup` | 另起一套 toast/dialog |
| Vue 组件 | 下表已注册的 `<ev-*>` | 需求能对上时仍自写开关、选项卡、进度条、树、查找框 |

**需求能对上已有组件时，必须复用。** 对不上再确认，只补缺口，不重做皮肤。

### 2. 布局：按场景自由发挥（重点）

EovaUI **几乎不提供通用布局体系**（仅有简单栅格 `eova-row` / `eova-col-*`，以及业务页骨架 `eova-layout`）。  
因此：**排版、定位、间距、区块结构可以按产品需要自行设计**，不要为了「复用」去硬套场景专用容器。

硬套的典型问题：

| 误用 | 真实用途 | 为何不适合硬套 |
|------|----------|----------------|
| `eova-tools` / `eova-tools_box` | 列表/表格顶栏横向工具条 | 水平 `margin-right`、为工具栏定制；设计面板、设置抽屉、表单项列表等会显得呆板，也难扩展开关/分组 |
| `eova-form-native_row` | 简易原生表单行 | 不是通用「竖向操作区」 |
| `eova-row` + 多列强拆 | 12 栅格内容区 | 小浮层、侧栏面板不必硬塞栅格 |

**正确做法：**

- **控件**：继续用 `button` / `eova-btn_error` / `ev-bool` 等。
- **布局**：业务自己写结构 class（如 `eova-design-panel__body`），用 flex/grid、padding、gap 排版。
- **判断口诀**：问「这是控件皮，还是排版结构？」——皮复用，结构自定。

```html
<!-- ❌ 工具栏容器硬套进设计面板 -->
<div class="eova-panel">
  <div class="eova-tools">
    <div class="eova-tools_box">
      <button>移除查询表单</button>
      <button class="eova-btn_error">退出</button>
    </div>
  </div>
</div>

<!-- ✅ 控件复用 EovaUI，布局按面板场景自定（可继续加开关行） -->
<div class="eova-panel eova-design-panel">
  <div class="eova-design-panel__body">
    <button type="button">移除查询表单</button>
    <button type="button" class="eova-btn_error">退出设计模式</button>
    <!-- 后续：开关行、分组标题等，继续在 body 里排，不必塞进 tools_box -->
  </div>
</div>
```

### 3. 其它

- **能原生就原生**：简单表单优先 `eova-form-native` + 原生控件。
- **参考源码**：主题 Demo 是控件规范样例，不是所有页面都必须长成工具栏。
- **场景专用 class 先认场景**：名称带 `tools` / `toolbar` / `menu` 的，先确认是否真是该场景，再决定用不用。

---

## 设计 Token（CSS 变量）

主题色可切换：`eova-theme_weixin` / `meituan` / `taobao` / `bilibili`（挂在根节点）。

| 类别 | 变量 |
|------|------|
| 功能色 | `--eova-color_main` `--eova-color_success` `--eova-color_error` `--eova-color_warn` `--eova-color_info` `--eova-color_hover` |
| 背景 | `--eova-color_bg` `--eova-color_bg1` `--eova-color_bg2` `--eova-color_bg3` |
| 边框 | `--eova-color_border` `--eova-color_border1` `--eova-color_border2` `--eova-color_border3` |
| 文本 | `--eova-color_txt` `--eova-color_txt1` `--eova-color_txt2` `--eova-color_txt3` |
| 其它 | `--eova-color_w` `--eova-color_b` `--eova-border_radius` |

页面底色示例：`body { background-color: var(--eova-color_bg); }`

---

## 栅格（可选，非强制）

适合内容区、卡片墙；**不适合**小浮层、设计面板内部排版强行套用。

```html
<div class="eova-row eova-col_space10">
  <div class="eova-col-1">1/12</div>
  <div class="eova-col-2">2/12</div>
</div>
```

业务列表页骨架：`eova-layout` + `.zone` + `.box`。

---

## 按钮（控件皮）

**工具栏场景**才用 `eova-tools` / `eova-tools_box`：

```html
<div class="eova-tools">
  <div class="eova-tools_box">
    <button>默认按钮</button>
    <button class="eova-btn_success">成功</button>
    <button class="eova-btn_error">危险</button>
    <button class="eova-btn_warn">警告</button>
    <button class="eova-btn_info">信息</button>
    <button class="eova-btn_base">原始</button>
  </div>
</div>
```

非工具栏场景：直接使用按钮 class，外层布局自定。

| 用途 | class |
|------|--------|
| 默认 | `button` 或 `eova-btn`（`a`/`span`/`div` 也可加 `eova-btn`） |
| 主题 | `eova-btn_success` `eova-btn_error` `eova-btn_warn` `eova-btn_info` `eova-btn_base` |
| 尺寸 | `eova-btn_s45` 大 / `s40` 中 / `s30` 小 / `s20` 迷你 |
| 圆角 | `eova-btn_radius` |
| 禁用 | `eova-btn_disabled` |
| 图标钮 | `eova-btn_icon`（超管工具条） |
| 图标 | `<i class="eova-icon-add-1">` 等，与文案并列 |

组合示例：`<button class="eova-btn_error eova-btn_s30 eova-btn_radius">…</button>`

---

## 面板 / 提示 / 徽章（控件皮）

```html
<div class="eova-panel">…</div>

<div class="eova-notes">💡 普通提示</div>
<div class="eova-notes eova-notes__error">错误</div>
<div class="eova-notes eova-notes__success">成功</div>
<div class="eova-notes eova-notes__info">信息</div>

<span class="eova-badge">99</span>
<span class="eova-badge-dot"></span>
<span class="eova-badge eova-badge_hollow">Hot</span>
<span class="eova-badge eova-badge_round">3</span>
<span class="eova-badge eova-badge_tag">苹果 <i class="eova-icon-close"></i></span>
<!-- 色：eova-bg-orange / green / blue / black / gray -->
```

间距辅助：`<div class="eova-sp"></div>`（行内分隔，非通用布局方案）。

---

## 静态皮肤（无交互时）

主题页里的选项卡、静态表格、原生表单是 **CSS 皮肤**。Vue 页面要交互时，改用下一节的组件，不要再手写切换逻辑。

```html
<div class="eova-tab eova-tab_base"><!-- 或 eova-tab_card；选中 eova-this，内容 eova-show --></div>
<table class="eova-table" ev-size="s30"><!-- 或 s40 --></table>
<form class="eova-form-native">
  <div class="eova-form-native_row">…原生 input/select/textarea…</div>
</form>
```

---

## Vue 组件

注册表：`src/components/index.ts`。演示：`src/view/widget/index.vue`。

| 标签 | 何时用 | 关键属性 |
|------|--------|----------|
| `ev-progress` | 进度条 | `:value` `text` `color`（可 `a,b` 渐变）`size` 高度 |
| `ev-tab` + `ev-tab-item` | 选项卡 | `v-model` 当前 id；`type`：`base` / `card` / `sys`；item：`id` `title` |
| `ev-popup` | 挂在触发器上的弹出（菜单、提示、任意内容） | `trigger`：`click`/`hover`/`focus`/`contextMenu`；`placement`：`top` `bottom` `left` `right` 及 `*-start` `*-end`；`clickOutsideToClose`；`ref.hide()`；`#content` |
| `ev-dialog` | 当前页内弹窗，直接绑本页变量 | `v-model:visible` `title` `width` `height`；`layerContainer`：`body` / `parent` / `top` |
| `ev-tree` | 树 | `:data` 静态节点，或 `object` + `conf`（`id/pid/title/root`）；`@node-click`；`v-model:checked` |
| `ev-find` | 按选项表达式查找 | `option` `multiple` `mode`：`input`/`a`/`button`/`text`；`v-model` |
| `ev-form` | 元数据表单 | `mode`：`query`/`create`/`update`/`read`；`object` `biz` `v-model`；`@submit` `@ready` `@resize` |
| `ev-table` | 元数据表格 | `object` `biz` `:page` `:where` `:design` `@row-click` |
| `ev-bool` | 开关 / 勾选 | `type`：`bool`（是/否）或 `check`；`v-model` `@change` `label` `disabled` |
| `ev-input` | 单行输入 | `type` `v-model` `placeholder` `disabled` |
| `ev-text` | 多行文本 | `name` `v-model` |
| `ev-edit` | 富文本/编辑框 | `v-model` `multiple` |
| `ev-icon` | 选图标 | `type` `v-model` |
| `ev-value` | 隐藏值 | `name` `v-model` |
| `ev-check` | 多选组 | `:items` `v-model` |
| `ev-radio` | 单选组 | `:items` 或 `option` `v-model` |
| `ev-select` | 下拉 | `option` 或 `:items`；`multiple` `showSearch` `allowClear` `v-model` |
| `ev-time` | 单个日期/时间 | `type`：`date` `datetime` `time` `year` `month` `yearmonth`；`v-model` |
| `ev-times` | 时间段 | 同上 `type`；`placeholder` 为数组；`v-model` |
| `ev-upload` | 上传 | `file_upload` 必填；`v-model`；`accept` `multiple` `auto_upload` |
| `ev-sortable` | 拖拽排序 | `v-model` 数组；`text_field` `order_field` |
| `ev-diy` | 元字段 DIY HTML | `html` `object` `field` `v-model` |

弹出内容的皮肤继续用现成 class，不要另写菜单皮：

```html
<ev-popup ref="pop" placement="bottom-start">
  <button>操作</button>
  <template #content>
    <div class="eova-select-content">
      <ul class="eova-select_items" @click="pop.hide()">
        <li>返回上级</li>
      </ul>
    </div>
  </template>
</ev-popup>

<ev-popup placement="top">
  <button><i class="eova-icon-question"></i> 说明</button>
  <template #content>
    <div class="eova-tooltip">
      <div class="eova-tooltip_content">提示文案</div>
    </div>
  </template>
</ev-popup>

<ev-tab v-model="currTab">
  <ev-tab-item id="tab1" title="选项一">内容</ev-tab-item>
</ev-tab>
<ev-tab type="card" v-model="currTab">…</ev-tab>

<ev-progress :value="90" text="默认进度条"></ev-progress>
<ev-progress :value="60" text="渐变" color="#1ea0fc,#67c23a"></ev-progress>

<ev-dialog title="自定义窗口" width="500px" height="300px" v-model:visible="visible">
  …当前页内容，可直接绑变量…
</ev-dialog>
```

选择口诀：

| 需求 | 用 |
|------|----|
| 轻提示 / 确认 / 输入 / 通知 / 打开 URL / 图册 | `me.layer.*` |
| 弹层就在当前页，要绑本页数据 | `ev-dialog` |
| 从按钮、图标旁弹出菜单或说明 | `ev-popup` |
| 日期时间 | `ev-time` / `ev-times`（不要直接用 `lay-date-picker`，除非 `ev-time` 能力不够并已确认） |
| 选项卡、进度、树、查找、下拉、上传、排序 | 上表对应 `ev-*` |

---

## JS API（`me.layer`）

源码：`src/core/src/LayerTool.ts`。

| API | 用途 |
|-----|------|
| `me.layer.msg(text)` | 轻提示 |
| `me.layer.ok(text)` / `no(text)` / `wa(text)` | 成功 / 失败 / 警告，约 3 秒 |
| `me.layer.notify(title, content, type)` | 通知。`type`：`ok` `no` `wa` `info`（默认） |
| `me.layer.confirm(text, okFn)` | 二次确认 |
| `me.layer.input(placeholder, formType, okFn)` | 输入框。`formType`：`text` `password` `textarea` |
| `me.layer.open(title, url, w, h, okFn, noFn, {offset})` | iframe 弹层。宽高 `< 1` 为视口比例，`1` 为铺满 |
| `me.layer.photos([{src, alt}])` | 图册 |
| `me.layer.loading(text)` | 加载提示 |
| `me.layer.close(id)` | 关闭指定层 |

---

## 已知缺口

| 缺口 | 现状 | 建议 |
|------|------|------|
| 通用布局体系 | 仅栅格 + 业务骨架 | **布局自定**，勿硬套工具栏等场景容器 |
| 右下角悬浮定位 | 无 utility | 业务补 `position:fixed`；皮肤仍用 `eova-panel` |

控件皮缺口：**先确认，再加最薄一层**。

---

## 反例 → 正例

```html
<!-- ❌ 自造按钮皮肤 -->
<button class="xxx__btn xxx__btn--exit">退出</button>
<!-- ✅ 复用按钮皮 -->
<button class="eova-btn_error">退出设计模式</button>
```

```html
<!-- ❌ 把工具栏布局硬套进设计面板 -->
<div class="eova-tools_box">…</div>
<!-- ✅ 面板布局自定，按钮仍用 EovaUI -->
<div class="eova-design-panel__body">
  <button>移除查询表单</button>
  <button class="eova-btn_error">退出设计模式</button>
</div>
```
