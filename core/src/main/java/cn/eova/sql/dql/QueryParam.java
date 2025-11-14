package cn.eova.sql.dql;

/**
 * 查询参数
 * @author Jieven
 *
 */
public class QueryParam {

	/**逻辑运算法**/
	public String cond;
	/**单个值**/
	public Object value;
	/**范围开始值**/
	public String start;
	/**范围结束值**/
	public String end;

	public QueryParam(String cond, Object value, String start, String end) {
		super();
		this.cond = cond;
		this.value = value;
		this.start = start;
		this.end = end;
	}

}
