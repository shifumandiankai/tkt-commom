package com.mtl.cypw.common.core.tkmybatis;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * @author Johnathon.Yuan
 * @date 2019-11-17 19:23
 */
@tk.mybatis.mapper.annotation.RegisterMapper
public interface BaseMapper<T> extends Mapper<T>, InsertListMapper<T>, ExpandedIdListMapper<T, Integer>, UpdateIdListMapper<T, Integer> {

}
