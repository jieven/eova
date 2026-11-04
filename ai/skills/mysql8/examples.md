# 建表示例

## 订单表示例

```sql
CREATE TABLE `sales_order` (
	`id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`status` tinyint NOT NULL DEFAULT '1' COMMENT '状态:1=启用,2=停用',
	`type` int NOT NULL DEFAULT '1' COMMENT '类型:1=普通,2=加急',
	`order_no` varchar(32) DEFAULT NULL COMMENT '订单号',
	`amount` double(9,2) NOT NULL DEFAULT 0.00 COMMENT '订单金额',
	`is_paid` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否已支付:0=否,1=是',
	`creator` int NOT NULL COMMENT '创建人',
	`created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`updated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='销售订单';
```
