##本path 是在eova库中执行，看下面的表名就知道了！

ALTER TABLE `eova_menu`
ADD COLUMN `url`  varchar(255) NULL DEFAULT '' COMMENT '自定义URL' AFTER `bizIntercept`;

update eova_button set ui = '/eova/template/crud/btn/update.html' where menuCode='eova_menu' and name = '修改';

UPDATE `eova_menu` SET `url`='/toIcon' WHERE (`id`='9')

ALTER TABLE `eova_button`
DROP INDEX `menuCode_index`;