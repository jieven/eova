-- ----------------------------
-- View structure for v_orders
-- ----------------------------

CREATE VIEW v_orders AS SELECT
	o. ID AS ID,
	o.state AS state,
	o.money AS money,
	o.memo AS memo,
	o.update_user_id AS update_user_id,
	o.create_user_id AS create_user_id,
	o.create_time AS create_time,
	o.update_time AS update_time,
	o.address_id AS address_id,
	A . NAME AS NAME,
	A . FULL AS FULL,
	A .mobilephone AS mobilephone,
	U .login_id AS login_id,
	U .nickname AS nickname,
	U .info AS info
FROM
	(
		(
			orders o
			LEFT JOIN USERS U ON ((o.create_user_id = U . ID))
		)
		LEFT JOIN address A ON ((o.address_id = A . ID))
	);

-- ----------------------------
-- View structure for v_userinfo
-- ----------------------------

CREATE VIEW v_userinfo AS SELECT
	U . ID AS ID,
	U .status AS status,
	U .login_id AS login_id,
	U .login_pwd AS login_pwd,
	U .nickname AS nickname,
	U .reg_time AS reg_time,
	U .info AS info,
	U .tag AS tag,
	ue. EXP AS EXP,
	ue. AVG AS AVG,
	ue.qq AS qq
FROM
	USERS U,
	users_exp ue
WHERE
	U . ID = ue.users_id;