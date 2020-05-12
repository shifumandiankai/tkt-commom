package com.mtl.cypw.common.dto.param;

import lombok.Data;

/**
 * @author Johnathon.Yuan
 * @date 2019-11-13 15:03
 */
@Data
public class PagedRequest extends Request {

    /**
     * 页码
     */
    private int pageNo;

    /**
     * 每页长度
     */
    private int pageSize;
}
