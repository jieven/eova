/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.core.meta;

public class Test {

    public static void main(String[] args) {
        //@formatter:off
				String sql = "select sku, product_name, num1, num2, (sum1-sum2) total  from (\r\n" + 
						"	select \r\n" + 
						"		sku,product_name,\r\n" + 
						"		IFNULL(sum(case when type=1 then num end), 0) as num1,\r\n" + 
						"		IFNULL(sum(case when type=2 then num end), 0) as num2,\r\n" + 
						"		IFNULL(sum(case when type=1 then price*num end), 0) as sum1,\r\n" + 
						"		IFNULL(sum(case when type=2 then price*num end), 0) as sum2\r\n" + 
						"	from\r\n" + 
						"	(\r\n" + 
						"	SELECT style, sku, num, price, type, product_name\r\n" + 
						"	FROM `v_settle_items` vsi where vsi.supplier_id = {0} and '{1}' <= update_time and update_time < '{2}'\r\n" + 
						"	) T \r\n" + 
						"	GROUP BY sku,product_name\r\n" + 
						") S order by total desc";
				//@formatter:on
        int i1 = sql.indexOf("select") + 6;
        int i2 = sql.indexOf("from");

        String select = sql.trim().toLowerCase().substring(i1, i2);
        String[] ss = select.split(",");
        System.out.println(ss);
    }

}
