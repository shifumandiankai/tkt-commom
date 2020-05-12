package com.mtl.cypw.common.core.tkmybatis;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.additional.idlist.IdListMapper;

import java.util.List;

/**
 * @author Johnathon.Yuan
 * @date 2019-11-17 19:24
 */
@tk.mybatis.mapper.annotation.RegisterMapper
public interface ExpandedIdListMapper<T, PK> extends IdListMapper<T, PK> {

    /**
     * 根据主键字符串进行查询，类中只有存在一个带有 @Id 注解的字段
     *
     * @param idList
     * @return
     */
    @SelectProvider(type = ExpandedIdListProvider.class, method = "dynamicSQL")
    List<T> selectByIdListWithoutBlob(@Param("idList") List<PK> idList);
}
