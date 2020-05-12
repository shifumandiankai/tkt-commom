package com.mtl.cypw.common.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Johnathon.Yuan
 * @date 2019-11-13 17:22
 */
@Data
@NoArgsConstructor
public class Paging {
    /**
     * 页码
     */
    private int pageNo;
    /**
     * 页数
     */
    private int pageSize;
    /**
     * 总记录数
     */
    private int totalHits;
    /**
     * 是否还有下一页
     */
    private boolean hasMore;

    public Paging(int pageNo, int pageSize, int totalHits,boolean hasMore) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalHits = totalHits;
        this.hasMore = hasMore;
    }
}
