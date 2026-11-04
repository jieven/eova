-- ----------------------------
-- 元数据版本管理：各纳入表增加 updated（DB 自动刷新）
-- 用途：业务表 updated 与 eova_version.updated/created 时间窗比对
-- 说明：eova_version 只记版本本身，不存行级快照；历史靠时间窗回放（数据再改会漂移）
-- ----------------------------

-- ========== 数据源 eova ==========

ALTER TABLE `eova_object`
  ADD COLUMN `updated` datetime NOT NULL DEFAULT '2026-01-01 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间';

ALTER TABLE `eova_field`
  ADD COLUMN `updated` datetime NOT NULL DEFAULT '2026-01-01 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间';

ALTER TABLE `eova_field_diy`
  ADD COLUMN `updated` datetime NOT NULL DEFAULT '2026-01-01 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间';

ALTER TABLE `eova_option`
  ADD COLUMN `updated` datetime NOT NULL DEFAULT '2026-01-01 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间';

ALTER TABLE `eova_menu`
  ADD COLUMN `updated` datetime NOT NULL DEFAULT '2026-01-01 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间';

ALTER TABLE `eova_button`
  ADD COLUMN `updated` datetime NOT NULL DEFAULT '2026-01-01 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间';

-- ========== 数据源 main ==========
-- 业务字典在 main 库执行

ALTER TABLE `dicts`
  ADD COLUMN `updated` datetime NOT NULL DEFAULT '2026-01-01 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间';

-- ========== 数据源 eova：版本表（仅版本记录） ==========

CREATE TABLE IF NOT EXISTS `eova_version` (
	`id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`status` tinyint NOT NULL DEFAULT '1' COMMENT '状态:1=正常,2=作废',
	`ver` varchar(20) NOT NULL COMMENT '版本号 yyyyMMddHHmmss',
	`updated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据更新时间(时间窗起点)',
	`created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '版本创建时间(时间窗终点)',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='元数据导出版本';

-- 初始化基线：updated=created，之后待导出 = 业务.updated > created
INSERT INTO `eova_version` (`status`, `ver`, `updated`, `created`)
VALUES (1, '20260101100000', '2026-01-01 00:00:00', '2026-01-01 10:00:00');
