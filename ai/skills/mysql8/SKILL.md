---
name: mysql8
description: 按统一规范为 MySQL 8 编写建表 DDL（小写下划线字段名、标准审计字段、字典/布尔/金额/字符串约定、不主动建索引）。在用户要求建表、表结构、DDL、迁移或数据库设计时使用本技能。
---

# MySQL 8 建表规范

作为 MySQL 8 表结构设计助手：输出可直接执行的 `CREATE TABLE`，严格遵守下列约定。

## 必须遵守

### 命名

- **字段名**：全小写，单词间用下划线 `_` 分隔（如 `user_id`、`order_no`）。
- **表名**：与项目习惯一致；若未约定，使用小写下划线。
- **索引**：默认**不创建**任何索引（含唯一索引）；除非用户明确要求。

### 标准模板

新表以以下骨架为起点，在注释 `-- 其他字段 放这里` 处插入业务字段；`PRIMARY KEY` 仅 `id`。

```sql
CREATE TABLE `表名` (
	`id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`status` tinyint NOT NULL DEFAULT '1' COMMENT '状态:1=启用,2=停用',
	`type` int NOT NULL DEFAULT '1' COMMENT '类型',
	-- 其他字段 放这里
	`creator` int NOT NULL COMMENT '创建人',
	`created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`updated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='[表注释]';
```
- 有逻辑删除刚需的业务加上 	`is_hide` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否隐藏', 一般不需要。
- 表注释将 `[表注释]` 替换为实际中文说明。
- 若业务需要「更新时自动刷新 `updated`」，可为 `updated` 增加 `ON UPDATE CURRENT_TIMESTAMP`（仅当用户或场景需要时再写）。

## 字段类型约定

| 场景 | 写法 |
|------|------|
| **布尔** | `tinyint(1) NOT NULL DEFAULT 0`，列名建议 `is_xxx`；`COMMENT` 说明 0/1 含义。 |
| **字典 / 枚举码** | 用 `tinyint` 或 `int`（与 `status`/`type` 一致）；**取值含义写在 COMMENT**，格式：`状态:1=启用,2=停用`。 |
| **普通数值** | 整数用 `int`（或更小范围如 `smallint` 若明显足够）；与模板中 `type` 风格一致。 |
| **金额** | `double(9,2) ... DEFAULT NULL`（或 `NOT NULL DEFAULT 0` 按业务），`COMMENT` 标明币种或单位若需要。 |
| **字符串** | `varchar(长度) DEFAULT NULL COMMENT '说明'`，长度按业务合理选取（常见如名称 64–255，编码 32–64，备注 255–500 等）。 |

## 输出时注意

1. 每个业务字段都应有中文 `COMMENT`。
2. 不添加 KEY/UNIQUE/FULLTEXT 等索引语句，除非用户明确要求。
3. 保持与模板相同的引擎、字符集、排序规则。
4. 直接输出ddl语法,无需输出其他内容.

## 可选参考

- 更细的示例或变体可放在 [examples.md](examples.md).
