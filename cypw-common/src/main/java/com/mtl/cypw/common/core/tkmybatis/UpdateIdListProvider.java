package com.mtl.cypw.common.core.tkmybatis;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.additional.idlist.IdListProvider;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

/**
 * @author Johnathon.Yuan
 * @date 2019-11-17 19:29
 */
public class UpdateIdListProvider extends IdListProvider {

    public UpdateIdListProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String columns(Class<?> entityClass) {
        return ExpandedSqlHelper.selectColumnsWithoutBlob(entityClass);
    }

    /**
     * 根据 Example 更新非 null 字段
     *
     * @param ms
     * @return
     */
    public String bulkUpdateByExampleSelective(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(" <foreach collection=\"list\" item=\"item\" index=\"index\" open=\"\" close=\"\" separator=\";\">");
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.updateSetColumns(entityClass, "item", true, isNotEmpty()));
        sql.append(SqlHelper.wherePKColumns(entityClass, "item", true));
        sql.append("</foreach>");
        return sql.toString();
    }


}
