package com.mtl.cypw.common.core.tkmybatis;

import com.google.common.base.Splitter;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Johnathon.Yuan
 * @date 2019-11-17 19:24
 */
public class ExpandedSqlHelper {

    /**
     * 获取简单列，如id,name,code...
     *
     * @param entityClass
     * @return
     */
    public static String selectColumnsWithoutBlob(Class<?> entityClass) {
        Set<EntityColumn> columnSet = EntityHelper.getColumns(entityClass);
        StringBuilder sql = new StringBuilder();
        columnSet.stream().filter(e -> !e.isBlob()).forEach(entityColumn -> sql.append(entityColumn.getColumn()).append(","));
        if (sql.length() == 0) {
            return sql.toString();
        }
        return sql.substring(0, sql.length() - 1);
    }

    public static List<String> columnsExcludeBlob(Class<?> entityClass) {
        String blobs = selectColumnsWithoutBlob(entityClass);
        return Splitter.on(",").splitToList(blobs);
    }

    public static List<String> blobProps(Class<?> entityClass) {
        Set<EntityColumn> columnSet = EntityHelper.getColumns(entityClass);
        return columnSet.stream().filter(EntityColumn::isBlob).map(EntityColumn::getProperty).collect(Collectors.toList());
    }

    public static String selectBlobColumns(Class<?> entityClass) {
        Set<EntityColumn> columnSet = EntityHelper.getColumns(entityClass);
        StringBuilder sql = new StringBuilder();
        columnSet.stream().filter(EntityColumn::isBlob).forEach(entityColumn -> sql.append(entityColumn.getColumn()).append(","));
        if (sql.length() == 0) {
            return sql.toString();
        }
        return sql.substring(0, sql.length() - 1);
    }

    public static boolean isBlobColumn(EntityColumn column) {
        String typeName = column.getJdbcType().name();
        return "BINARY".equals(typeName) || "BLOB".equals(typeName)
                || "CLOB".equals(typeName) || "LONGNVARCHAR".equals(typeName)
                || "LONGVARBINARY".equals(typeName) || "LONGVARCHAR".equals(typeName)
                || "NCLOB".equals(typeName) || "VARBINARY".equals(typeName);
    }
}
