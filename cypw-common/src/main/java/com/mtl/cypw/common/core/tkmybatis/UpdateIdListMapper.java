package com.mtl.cypw.common.core.tkmybatis;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.additional.idlist.IdListMapper;

import java.util.List;

/**
 * @author Johnathon.Yuan
 * @date 2019-11-17 19:27
 */
@tk.mybatis.mapper.annotation.RegisterMapper
public interface UpdateIdListMapper<T, PK> extends IdListMapper<T, PK> {

    /**
     * 批量更新
     *
     * @param list
     * @return
     */
    @SelectProvider(type = UpdateIdListProvider.class, method = "dynamicSQL")
    void bulkUpdateByExampleSelective(@Param("list") List<T> list);
}
