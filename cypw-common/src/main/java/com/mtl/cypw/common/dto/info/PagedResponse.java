package com.mtl.cypw.common.dto.info;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Johnathon.Yuan
 * @date 2019-11-13 15:04
 */
@Data
@NoArgsConstructor
public class PagedResponse<T> extends Response<List<T>> {

    /**
     * 总共命中记录数
     */
    private int totalHits;

    /**
     * 是否有后续分页
     */
    private boolean hasNext;
}
