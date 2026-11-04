# @eova/eova-tools — AI 使用知识库

> 面向 AI 编码助手：在 Eova / Vue 项目中**优先复用本工具箱**，避免重复造轮子。  
> 源码：`packages/tools` · 包名：`@eova/eova-tools` · 全局单例：`x`

---

## 核心约定（必读）


| 项    | 说明                                                                         |
| ---- | -------------------------------------------------------------------------- |
| 包名   | `@eova/eova-tools`                                                         |
| 入口   | `x`（`EovaTools` 单例）                                                        |
| 调用风格 | `x.模块.方法(...)`，例如 `x.time.now()`、`x.validate.start(...)`                   |
| 依赖   | `axios`、`xe-utils`（挂在 `x.xe`）                                              |
| 环境   | 浏览器侧为主（`cookie` / `url` / `dom` / `axios.download` 依赖 `window`/`document`） |



### AI 决策原则

1. **已有能力优先用** `x.`*，不要再写同类 `isEmpty` / 日期格式化 / URL 参数解析 / Cookie 读写。
2. **表单校验优先** `x.validate`，规则用字符串声明，不要手写一堆 `if`。
3. **通用集合/对象操作可用** `x.xe`（xe-utils），无需再引 lodash。
4. **服务端 / Node 环境**：不要用 `x.cookie`、`x.url`、`x.dom`、`x.axios.download`（依赖浏览器 API）。
5. **改工具库本身**：实现改 `packages/tools/src/`，测例放 `src/__test/`，改完跑 `pnpm test:run`。

---

## 资源引入

在EovaMeta工程中的统一CDN方式引入

```
<script type="text/javascript" src="/eova/lib/eova/lib/eova-tools.umd.js?v=1.0.0.10101"></script>
```

## 模块地图

```
x
├── isEmpty(o)          # 空值判断（required 的根基）
├── log(s)              # console.log 快捷
├── xe                  # xe-utils 全量能力
├── str                 # StringTool  字符串
├── json                # JsonTool    JSON 安全转换
├── url                 # UrlTool     当前页 URL 参数
├── time                # TimeTool    时间戳 / 格式化 / 星期
├── cookie              # CookieTool  Cookie
├── dom                 # DomTool     DOM / 视口 / 动态加载资源
├── axios               # AxiosTool   文件下载（blob）
└── validate            # ValidateTool 表单校验
```


| 模块           | 典型场景            | AI 应优先使用的方法                                         |
| ------------ | --------------- | --------------------------------------------------- |
| `x.isEmpty`  | 判空、开关字段、数字 0    | 见「`x.isEmpty` — 空值语义」                               |
| `x.str`      | 模板 URL、截断展示、编解码 | `template` / `format` / `clip`                      |
| `x.json`     | 后端字符串 ↔ 对象      | `toObj` / `toStr`                                   |
| `x.url`      | 读当前页 query      | `get` / `getObj` / `appendPara`                     |
| `x.time`     | 展示时间、打点         | `now` / `format` / `formatNowTime`                  |
| `x.cookie`   | Token / 偏好      | `set` / `get` / `remove`                            |
| `x.dom`      | 布局尺寸、动态插脚本样式    | `getViewSize` / `loadJS` / `loadCSS`                |
| `x.axios`    | 导出 Excel 等文件下载  | `download`                                          |
| `x.validate` | 表单提交前校验         | `start` / `showMsg` / `addRules`                    |
| `x.xe`       | 深拷贝、去重、树、日期扩展等  | 查 [xe-utils](https://x-extends.github.io/xe-utils/) |


---



## `x.isEmpty` — 空值语义（高频踩坑）

**实现位置**：`EovaTools.isEmpty`（`src/index.ts`），**不是** `BaseTool.isEmpty`（后者未挂到 `x`）。

### 什么算空 / 不算空


| 值                        | `x.isEmpty`     | 说明                  |
| ------------------------ | --------------- | ------------------- |
| `undefined` / `null`     | `true`          |                     |
| `''` / 纯空白串              | `true`          | `trim` 后为空          |
| `'undefined'`            | `true`          | 哨兵字符串               |
| `false` / `true`         | `false`**（非空）** | 布尔都是已填合法值           |
| `0` / 普通数字 / `Infinity`  | `false`**（非空）** | 数字含 0 已填；仅 `NaN` 为空 |
| `NaN`                    | `true`          |                     |
| `[]` / 空 `Map` / 空 `Set` | `true`          |                     |
| `{}`                     | `false`**（非空）** | 不把空对象当空             |
| `'0'` / `'false'`        | `false`         | 普通非空字符串             |




### AI 必须遵守

- 开关 / 是否类字段（`enabled`、`is_hide` 等）常见值：`0` / `1` / `true` / `false` → **都不是空**，`required` 应通过。
- **禁止**用 `!val`、`val == ''`、`val == null` 替代 `x.isEmpty` 做业务判空（会误杀 `0`、`false`）。
- `ValidateTool` 的 `required` **内部调用** `x.isEmpty`，行为与上表一致。

```ts
import { x } from '@eova/eova-tools'

if (x.isEmpty(form.enabled)) {
  // 不会把 false / 0 当成空
}
```

---



## `x.validate` — 表单校验



### 基本用法

```ts
import { x, type ValidateRules } from '@eova/eova-tools'

const formRules: ValidateRules = {
  username: { label: '用户名', rules: ['required', 'username'], msg: '' },
  password: { label: '密码', rules: ['required', 'password'], msg: '' },
  confirm:  { label: '确认密码', rules: ['required', 'match[password]'], msg: '' },
  mobile:   { label: '手机号', rules: ['required', 'mobile'], msg: '' },
}

const ok = x.validate.start(formRules, formData)
if (!ok) {
  const html = x.validate.showMsg(formRules) // 多字段用 <br> 拼接
  // 交给 UI 弹层 / Toast 展示
  return
}
// 提交...
```

要点：

- `start` 同步返回 `boolean`；失败时把文案写到各字段的 `msg`。
- 同一字段多规则**按数组顺序短路**（先 `required` 再格式规则）。
- `showMsg` 返回 HTML 片段（含 `<br>`），不是纯文本数组。



### 内置规则一览


| 规则                                        | 含义                              |
| ----------------------------------------- | ------------------------------- |
| `required`                                | 必填（走 `x.isEmpty`）               |
| `num`                                     | 数字（可小数、可负）                      |
| `int`                                     | 整数（可负）                          |
| `intzs`                                   | 正整数（含 0 串形式 `^\d+$`）            |
| `intfs`                                   | 负整数（实现正则疑似有转义问题，慎用；优先自定义 `diy`） |
| `abc`                                     | 纯字母                             |
| `cn`                                      | 纯中文                             |
| `money`                                   | 金额，最多两位小数，非 `0.00` 特例见源码        |
| `date`                                    | `yyyy-mm-dd`                    |
| `time`                                    | `HH:mm` 或 `HH:mm:ss`            |
| `email` / `url` / `qq` / `tel` / `mobile` | 联系方式                            |
| `zipcode` / `bankcard` / `idcard`         | 邮编 / 银行卡 / 身份证（**格式**校验，非校验位算法） |
| `ip` / `port`                             | IPv4 / 端口                       |
| `username`                                | 3–12 位 `\w`                     |
| `password`                                | 6–16 位非空白                       |
| `eovacode`                                | 3–50 位 `\w`（Eova 编码）            |




### 参数化规则

```ts
'len[6]'           // 定长 6
'len[2~10]'        // 长度区间
'range[1~120]'     // 数值区间（比较用 <=，注意字符串数字会走 JS 弱比较）
'match[password]'  // 与另一字段相等
'match[oldPwd,!=]' // 与另一字段不等
'diy[odd]'         // 自定义（需先 addRules）
'api[/user/check]' // 远程异步：POST `url?val=`，返回非空文本即失败
```



### 自定义规则

```ts
x.validate.addRules('odd', (val: number) => {
  if (val % 2 !== 1) return '必须是奇数' // 失败文案（会拼到 label 后）
  return null // 通过
})

// rules: ['diy[odd]']
```



### AI 写校验时的约束

1. 规则名用上表字符串，**不要自创**未注册规则名（未知规则只会 `console.log`）。
2. 字段 key 必须与 `formData` 属性一致。
3. `match[field]` 的 `field` 必须也在 `formRules` 里（要读 `.label`）。
4. `api[...]` 是**异步**的：`start` 可能先返回 `true`，msg 稍后才写入 —— 业务上若依赖远程校验，需单独处理，不要假设同步完成。
5. 单测 / 调用请走 `x.validate`，不要直接 `new ValidateTool()`（与 `index` 循环依赖，测例已注明）。

---



## 其它模块速查



### `x.str`

```ts
x.str.format('Hello {0}, {1}', 'Eova', 2024)
// → "Hello Eova, 2024"

x.str.template('/api/meta/table/{{page.object}}-{{biz}}', {
  page: { object: 123 },
  biz: 'list',
})
// → "/api/meta/table/123-list"  （支持点路径；缺值保留原 {{...}}）

x.str.clip('中文ABC', 5)  // 中文计 2、英文计 1，超出加 ...
x.str.isNum('12.3')       // 注意：内部会调 value.trim()，非字符串易炸
x.str.isCn('你好')
x.str.enurl / x.str.deurl // encodeURIComponent / decodeURIComponent
```



### `x.json`

```ts
x.json.toObj('{"a":1}')  // 失败返回 null，并 console.error
x.json.toStr(obj, 2)     // JSON.stringify，space 默认 0
```



### `x.url`（当前页）

```ts
x.url.get('id')          // 单个 query
x.url.getParas()         // `a=1&b=2` 原始串
x.url.getObj()           // { a: '1', b: '2' }
x.url.appendPara(url, 'k', 'v') // 自动 ? / &
```



### `x.time`

```ts
x.time.now()                 // 毫秒时间戳
x.time.format(ms)            // 本地时区 → "yyyy-MM-dd HH:mm:ss"
x.time.formatNowTime()
x.time.toWeek(new Date())    // "星期一" … "星期天"；非 Date 抛 TypeError
```

**注意**：`format` 用本地时区 API，测试/断言勿用 UTC 字符串硬比。

### `x.cookie`

```ts
x.cookie.set('token', value, 7) // 默认过期 30 天，path=/
x.cookie.get('token')           // 没有返回 ''
x.cookie.remove('token')        // 同 delete
```

值会 `encodeURIComponent` / `decodeURIComponent`。

### `x.dom`

```ts
x.dom.getViewSize()           // { width, height } 视口（含默认 window）
x.dom.parseHeight(100 | '100px' | '50%')
x.dom.getTop(el) / getLeft(el)
x.dom.on / off / once(el, events, handler)
x.dom.loadCSS(url)
x.dom.loadJS(url, () => { /* onload */ })
```



### `x.axios.download`

```ts
await x.axios.download(
  '/api/export',
  JSON.stringify(payload), // POST body
  '导出.xlsx',
  'application/msexcel'
)
```

POST + `blob` + 临时 `<a download>`；失败只 `console.error`，不抛给调用方（注意自行兜底 UI）。

### `x.xe`（xe-utils）

```ts
x.xe.clone(obj)
x.xe.isEqual(a, b)
// 其它：数组、对象、日期、树等，按 xe-utils 文档使用
```

---



## 未从主入口导出的能力

以下文件存在于包内，**但** `src/index.ts` **未 re-export**，业务侧默认**不要依赖**（除非显式深路径引用并确认构建配置允许）：


| 文件             | 说明                                             |
| -------------- | ---------------------------------------------- |
| `axios-ext.ts` | `postCache` / `postMerge` / `wait` — 短时请求合并与缓存 |
| `BaseTool.ts`  | 旧版工具基类；`x.isEmpty` **不是**它                     |


AI 生成业务代码时：只用 `import { x } from '@eova/eova-tools'` 的公开表面。

---



## 代码生成检查清单（给 AI）

生成或修改前端代码前自检：

- [ ] 是否可用 `x.isEmpty` 代替手写判空？
- [ ] 表单是否可用 `x.validate.start` + 规则字符串？
- [ ] 开关字段是否错误地把 `0`/`false` 当空？
- [ ] 时间展示是否用 `x.time.format` / `formatNowTime`？
- [ ] URL 模板是否用 `x.str.template`（`{{a.b}}`）？
- [ ] Cookie / 当前页 query 是否分别用 `x.cookie` / `x.url`？
- [ ] 是否错误 `import x from ...`（UMD 场景应 `eova.x`）？
- [ ] 是否在 Node 环境误用了 DOM/Cookie API？

---



## 最小可运行示例



### TypeScript / ESM

```ts
import { x, type ValidateRules } from '@eova/eova-tools'

export function submitOrder(form: Record<string, unknown>) {
  const rules: ValidateRules = {
    goodsName: { label: '商品名称', rules: ['required', 'len[1~50]'], msg: '' },
    mobile:    { label: '收货手机', rules: ['required', 'mobile'], msg: '' },
    amount:    { label: '金额', rules: ['required', 'money'], msg: '' },
    qty:       { label: '数量', rules: ['required', 'intzs', 'range[1~999]'], msg: '' },
    enabled:   { label: '是否开票', rules: ['required'], msg: '' }, // false/0 也通过
  }

  if (!x.validate.start(rules, form)) {
    return { ok: false, msg: x.validate.showMsg(rules) }
  }

  return {
    ok: true,
    at: x.time.formatNowTime(),
  }
}
```



### HTML（CDN / UMD）

构建时 `axios` 为 external，CDN 页须**先加载 axios，再加载 eova-tools**。挂载点为 `window.eova.x`。

```html
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8" />
  <title>eova-tools CDN 示例</title>
  <!-- 1. 外部依赖 -->
  <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
  <!-- 2. 工具库 UMD（也可换成 unpkg / 自建静态路径） -->
  <script src="./lib/eova-tools.umd.js"></script>
</head>
<body>
  <p id="now"></p>
  <p id="msg"></p>
  <button id="btn" type="button">校验并提交</button>

  <script>
    const x = window.eova.x

    document.getElementById('now').textContent = x.time.formatNowTime()

    document.getElementById('btn').onclick = function () {
      const form = {
        goodsName: 'Eova 授权',
        mobile: '13912345678',
        amount: '199.00',
        qty: 2,
        enabled: false, // 开关 false / 0 均非空，required 通过
      }

      const rules = {
        goodsName: { label: '商品名称', rules: ['required', 'len[1~50]'], msg: '' },
        mobile:    { label: '收货手机', rules: ['required', 'mobile'], msg: '' },
        amount:    { label: '金额', rules: ['required', 'money'], msg: '' },
        qty:       { label: '数量', rules: ['required', 'intzs', 'range[1~999]'], msg: '' },
        enabled:   { label: '是否开票', rules: ['required'], msg: '' },
      }

      if (!x.validate.start(rules, form)) {
        document.getElementById('msg').innerHTML = x.validate.showMsg(rules)
        return
      }

      document.getElementById('msg').textContent =
        '通过 @ ' + x.time.formatNowTime()
    }
  </script>
</body>
</html>
```


