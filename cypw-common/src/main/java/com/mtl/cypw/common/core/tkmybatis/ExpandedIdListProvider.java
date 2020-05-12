package com.mtl.cypw.common.core.tkmybatis;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.MapperException;
import tk.mybatis.mapper.additional.idlist.IdListProvider;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import java.util.Set;

/**
 * @author Johnathon.Yuan
 * @date 2019-11-17 19:24
 */
public class ExpandedIdListProvider extends IdListProvider {

    public ExpandedIdListProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String columns(Class<?> entityClass) {
        return ExpandedSqlHelper.selectColumnsWithoutBlob(entityClass);
    }

    public String selectByIdListWithoutBlob(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        //将返回值修改为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(columns(entityClass));
        sql.append("       ");
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        appendWhereIdList(sql, entityClass, false);
        return sql.toString();
    }

    /**
     * 拼接条件
     *
     * @param sql
     * @param entityClass
     */
    private void appendWhereIdList(StringBuilder sql, Class<?> entityClass, boolean notEmpty) {
        Set<EntityColumn> columnList = EntityHelper.getPKColumns(entityClass);
        if (columnList.size() == 1) {
            EntityColumn column = columnList.iterator().next();
            if (notEmpty) {
                sql.append("<bind name=\"notEmptyListCheck\" value=\"@tk.mybatis.mapper.additional.idlist.IdListProvider@notEmpty(");
                sql.append("idList, 'idList 不能为空')\"/>");
            }
            sql.append("<where>");
            sql.append("<foreach collection=\"idList\" item=\"id\" separator=\",\" open=\"");
            sql.append(column.getColumn());
            sql.append(" in ");
            sql.append("(\" close=\")\">");
            sql.append("#{id}");
            sql.append("</foreach>");
            sql.append("</where>");
        } else {
            throw new MapperException("继承 ByIdList 方法的实体类[" + entityClass.getCanonicalName() + "]中必须只有一个带有 @Id 注解的字段");
        }
    }
}
