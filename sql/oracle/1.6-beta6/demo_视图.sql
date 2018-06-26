-- ----------------------------
-- View
-- ----------------------------
CREATE VIEW users_total AS SELECT
	users.status AS status,
	count(users.status) AS num
FROM
	users
GROUP BY
	users.status;


CREATE VIEW v_orders AS SELECT
	o.id AS id,
	o.state AS state,
	o.money AS money,
	o.memo AS memo,
	o.update_user_id AS update_user_id,
	o.create_user_id AS create_user_id,
	o.create_time AS create_time,
	o.update_time AS update_time,
	o.address_id AS address_id,
	a.name AS name,
	a.full AS full,
	a.mobilephone AS mobilephone,
	u.login_id AS login_id,
	u.nickname AS nickname,
	u.info AS info
FROM
	(
		(
			orders o
			LEFT JOIN users u ON (
				(
					o.create_user_id = u.id
				)
			)
		)
		LEFT JOIN address a ON (
			(o.address_id = a.id)
		)
	);

	
CREATE VIEW v_userinfo AS SELECT
  u.id AS id,
  u.status AS status,
  u.login_id AS login_id,
  u.login_pwd AS login_pwd,
  u.nickname AS nickname,
  u.reg_time AS reg_time,
  u.info AS info,
  u.tag AS tag,
  ue.exp AS exp,
  ue.AVG AS AVG,
  ue.qq AS qq
FROM
    users u left JOIN users_exp ue ON u.id = ue.users_id
