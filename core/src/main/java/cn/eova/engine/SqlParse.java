/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.engine;

import java.util.List;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLMethodInvokeExpr;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLJoinTableSource;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectOrderByItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.alibaba.druid.sql.dialect.postgresql.ast.stmt.PGSelectQueryBlock;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.util.JdbcUtils;
import cn.eova.sql.dql.TableSource;

/**
 * Druid SQL 解析器
 *
 * @author Jieven
 *
 */
public class SqlParse {

    private DbType dbType;

    public List<SQLStatement> stmtList;
    public SQLSelectStatement selectStatement;
    public SQLSelect sqlselect;
    public SQLSelectQueryBlock query;

    public SqlParse() {
    }

    public SqlParse(DbType dbType, String sql) {
        this.dbType = dbType;

        SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sql, dbType);
        this.stmtList = parser.parseStatementList();
        this.selectStatement = (SQLSelectStatement) stmtList.get(0);
        this.sqlselect = selectStatement.getSelect();
        this.query = (SQLSelectQueryBlock) sqlselect.getQuery();
    }

    public List<SQLSelectItem> getSelectItem() {
        return query.getSelectList();
    }

    public List<SQLSelectOrderByItem> getOrderItem() {
        if (dbType.equals(JdbcUtils.MYSQL)) {
            MySqlSelectQueryBlock block = (MySqlSelectQueryBlock) query;
            if (block.getOrderBy() == null) {
                return null;
            }
            return block.getOrderBy().getItems();
        } else if (dbType.equals(JdbcUtils.ORACLE)) {
            if (sqlselect.getOrderBy() == null) {
                return null;
            }
            return sqlselect.getOrderBy().getItems();
        } else if (dbType.equals(JdbcUtils.POSTGRESQL)) {
            PGSelectQueryBlock block = (PGSelectQueryBlock) query;
            if (block.getOrderBy() == null) {
                return null;
            }
            return block.getOrderBy().getItems();
        } else {
            // SQL_SERVER
            if (sqlselect.getOrderBy() == null) {
                return null;
            }
            return sqlselect.getOrderBy().getItems();
        }
    }

    /**
     * 获取字段名
     *
     * @param expr
     * @return
     */
    public static String getExprName(SQLExpr expr) {
        // 有别名
        if (expr instanceof SQLPropertyExpr) {
            return ((SQLPropertyExpr) expr).getName();
        }
        // 无别名
        else if (expr instanceof SQLIdentifierExpr) {
            return ((SQLIdentifierExpr) expr).getName();
        }
        // 函数表达式
        else if (expr instanceof SQLMethodInvokeExpr) {
            return ((SQLMethodInvokeExpr) expr).toString();
        }
        return "";
    }

    /**
     * 获取别名
     *
     * @param expr
     * @return
     */
    public static String getExprAlias(SQLExpr expr) {
        return getExprOw(((SQLPropertyExpr) expr));
    }

    /**
     * 获取表名
     *
     * @param expr 表名.字段
     * @return
     */
    public static String getExprOw(SQLExpr expr) {
        return getExprName(((SQLPropertyExpr) expr).getOwner());
    }

    public static void main(String[] args) {

        System.out.println("------------mysql");
        print(DbType.mysql);

        System.out.println("------------ORACLE");
        print(DbType.oracle);
        System.out.println("------------POSTGRESQL");
        print(DbType.postgresql);
        System.out.println("------------SQL_SERVER");
        print(DbType.sqlserver);
    }

    private static void print(DbType dbType) {
        String sql = "select t1.a 呵呵, t1.b , t2.c from t1 left join t2 where t1.id = t2.pid and a.id = 1 order by id,indexNum desc";

        SqlParse sp = new SqlParse(dbType, sql);
        System.out.println("select ");
        {
            List<SQLSelectItem> items = sp.getSelectItem();
            for (SQLSelectItem s : items) {
                // 没有别名
                // SQLIdentifierExpr exp = (SQLIdentifierExpr) x.getExpr();
                // 如果有 用了别名 才是此类型SQLPropertyExpr exp = (SQLPropertyExpr) x.getExpr();
                SQLPropertyExpr exp = (SQLPropertyExpr) s.getExpr();
                System.out.println(exp.getOwner().toString() + '.' + exp.getName() + ' ' + s.getAlias());
            }
        }

        System.out.println(" form " + sp.query.getFrom().toString());
        System.out.println(" where " + sp.query.getWhere().toString());

        System.out.println("order by ");

        {
            List<SQLSelectOrderByItem> items = sp.getOrderItem();
            for (SQLSelectOrderByItem s : items) {
                SQLIdentifierExpr exp = (SQLIdentifierExpr) s.getExpr();
                System.out.print(exp.getName() + ' ');
                if (s.getType() != null) {
                    System.out.println(s.getType().name());
                }
            }
        }

        System.out.println();
    }

    /**
     * 递归处理join条件，分别获取 表名 别名 关联字段名
     *
     * @param formTs form 条件
     * @param sources 表 字段映射集
     * @throws Exception
     */
    public static void parseTableSource(SQLTableSource formTs, List<TableSource> sources) throws Exception {
        if (formTs instanceof SQLJoinTableSource) {
            SQLJoinTableSource join = (SQLJoinTableSource) formTs;
            if (join.getCondition() == null) {
                throw new Exception("Eova当前仅支持Left Join查询方式的View进行自动持久化操作，请手工自定义新增，修改等操作！");
            }

            parseTableSource(join.getLeft(), sources);

            SQLExprTableSource right = (SQLExprTableSource) join.getRight();
            SQLBinaryOpExpr condition = (SQLBinaryOpExpr) join.getCondition();

            String table = getExprName(right.getExpr());
            String alias = right.getAlias();

            TableSource source = buildTableSource(condition, table, alias);
            sources.add(source);

        } else {
            SQLExprTableSource join = (SQLExprTableSource) formTs;
            SQLJoinTableSource parent = (SQLJoinTableSource) join.getParent();
            SQLBinaryOpExpr condition = (SQLBinaryOpExpr) parent.getCondition();

            String table = getExprName(join.getExpr()).toString();
            String alias = join.getAlias();

            TableSource source = buildTableSource(condition, table, alias);
            sources.add(source);
        }
    }

    private static TableSource buildTableSource(SQLBinaryOpExpr condition, String table, String alias) {
        TableSource source = new TableSource();
        source.setTable(table);
        source.setAlias(alias);
        source.setLeftField(getExprName(condition.getLeft()));
        source.setLeftAlias(getExprOw(condition.getLeft()));
        source.setRigthField(getExprName(condition.getRight()));
        source.setRigthAlias(getExprOw(condition.getRight()));
        return source;
    }

}