# 校验规则

来源：`eova-core/packages/tools/src/ValidateTool.ts`（`ValidateTool`）。

表单规则是「字段 → 规则数组」。`start(formRules, formData)` 按数组顺序校验，某字段命中第一条失败就停在该字段，其它字段继续。全部字段的 `msg` 都是空字符串时返回 `true`。

```javascript
const rules = {
  code: { label: '编码', rules: ['required', 'eovacode'] },
  email: { label: '邮箱', rules: ['email'] },
}

if (!x.validate.start(rules, data)) {
  const txt = x.validate.showMsg(rules) // 各字段 msg 用 <br> 拼接
}
```

失败文案是 `label` 直接拼上规则说明，例如 `编码必须是3-50位数字、字母、下划线组成`。

空值只有 `required` 会拦截。其它规则对值做 `String(val)` 再测，空字符串过不了 `email`、`mobile` 这类规则。非必填又允许留空时，不要再挂这些规则。

未识别的规则名只打日志，不判失败。

## 内置规则

规则名就是 `rules` 数组里的字符串。

| 规则 | 含义 | 匹配 |
|------|------|------|
| `num` | 数字，可负、可小数 | `^-?\d*\.?\d+$` |
| `int` | 整数，可负 | `^-?\d+$` |
| `intzs` | 正整数，含 0 | `^\d+$` |
| `intfs` | 负整数 | `^-\d+$` |
| `abc` | 字母，忽略大小写 | `^[a-z]+$` |
| `cn` | 中文 | `^[\u4e00-\u9fa5]+$` |
| `money` | 金额，最多两位小数，拒绝 `0.00` | `^(?!0\.00)(?:0\|[1-9]\d*)(?:\.\d{1,2})?$` |
| `date` | 日期，只查格式 | `^\d{4}-\d{2}-\d{2}$` |
| `time` | 时间 `HH:mm` 或 `HH:mm:ss` | `^([01]\d\|2[0-3])(:[0-5]\d){1,2}$` |
| `email` | 邮箱 | 本地部分、域名、2–4 位后缀 |
| `url` | 网址，`http` / `https` / `ftp` / `sftp` | `^(https?\|s?ftp):\/\/\S+$` |
| `qq` | QQ 号，至少 5 位，不以 0 开头 | `^[1-9]\d{4,}$` |
| `tel` | 座机（区号）或 400 / 800 | 见源码 |
| `mobile` | 大陆手机号 | `^1[3-9]\d{9}$` |
| `zipcode` | 邮政编码，6 位数字 | `^\d{6}$` |
| `bankcard` | 银行卡号，16–19 位数字 | `^\d{16,19}$` |
| `idcard` | 身份证号，只查形态，不查校验位 | 见源码 |
| `ip` | IPv4 | 四段 0–255 |
| `port` | 端口 | 0–65535 |
| `username` | 用户名 | `^\w{3,12}$` |
| `password` | 密码，不能含空格 | `^\S{6,16}$` |
| `eovacode` | 编码 | `^\w{3,50}$` |

`date` 不检查 2 月 31 日这类非法日期。`idcard` 不检查最后一位校验码。

## 带参数的规则

参数写在方括号里，规则名先 `trim`。

| 写法 | 作用 |
|------|------|
| `required` | 空值失败，文案 `{label}不能为空` |
| `len[6]` | 恰好 6 个字符 |
| `len[2~20]` | 2 到 20 个字符 |
| `range[0~100]` | 数值落在 0 到 100，含两端。必须带 `~` |
| `match[password]` | 与字段 `password` 的值相同 |
| `match[password,!=]` | 不能与字段 `password` 的值相同 |

`match` 的第一段是对方字段名，文案用对方的 `label`。对方字段必须出现在同一份 `formRules` 里。比较符只认省略、`=`、`!=`。

## 自定义与远程

```javascript
x.validate.addRules('test', (val) => {
  if (val == null) return '自定义为空校验!'
  return null // 或 '' 表示通过
})
```

表单里写 `diy[test]`。函数返回非空字符串时失败，文案是 `{label} {返回值}`。没注册的 `diy[...]` 会抛错。

`api[/user/validate?type=id]` 会 `POST` 到该地址，并附上 `val`。响应正文非空则失败，空字符串则通过。请求是异步的，`start()` 不会等它结束，返回值里还看不到这次远程结果。
