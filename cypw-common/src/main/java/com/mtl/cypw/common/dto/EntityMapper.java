package com.mtl.cypw.common.dto;

import java.util.Collection;

/**
 * @author Johnathon.Yuan
 * @date 2019-11-13 16:27
 */
public interface EntityMapper<D, E> {

    /**
     * dto to entity
     * @param dto
     * @return
     */
    E toEntity(D dto);

    /**
     * entity to dto
     * @param entity
     * @return
     */
    D toDto(E entity);

    /**
     * DTO集合转Entity集合
     *
     * @param dtoList
     * @return
     */
    Collection<E> toEntity(Collection<D> dtoList);

    /**
     * Entity集合转DTO集合
     *
     * @param entityList
     * @return
     */
    Collection<D> toDto(Collection<E> entityList);
}
