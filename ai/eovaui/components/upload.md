# EvUpload 上传

**标签:** `<ev-upload>` · **源码:** `src/components/upload/`

## 基础用法

```vue
<ev-upload v-model="fileCodes" />
<ev-upload
  v-model="imgs"
  :auto_upload="true"
  :show_info="false"
  accept=".jpg,.png,.webp"
  :size="2048"
  @success="onOk"
/>
```

`modelValue` = 服务端文件 **code 逗号串**（多文件）。

## API

### Props

| 属性 | 说明 | 类型 | 默认值 |
|------|------|------|--------|
| modelValue | 文件 codes | `string` | — |
| disabled | 禁用 | `boolean` | false |
| file_upload | 上传 URL | `string` | 内置默认上传地址 |
| file_query | 查询 URL | `string` | 内置默认查询地址 |
| file_down | 下载基址 | `string` | `''` |
| size | 大小上限 **KB** | `number` | `1024 * 50`（约 50MB） |
| accept | 扩展名 | `string` | `.xlsx,.docx,.pdf,…,.webp` |
| show_info | 选中文件信息 | `boolean` | true |
| show_record | 历史上传列表 | `boolean` | true |
| auto_upload | 选完即传 | `boolean` | false |
| onError / onProgress / onSelect / onSuccess | 回调 | Function | — |

> 注意：`.ts` 注释写的是 M，实际默认按 **KB** 计量。

### Events

| 事件 | 说明 |
|------|------|
| update:modelValue | codes 串 |
| select / progress / success / error | 生命周期 |

### 扩展

可通过 `me.render` 注册 `eova-upload-record` 自定义记录区渲染。

## 表单映射

元字段「文件」「图片」：`auto_upload=true`，`show_info=false`，accept/size 来自 `f.conf`。
