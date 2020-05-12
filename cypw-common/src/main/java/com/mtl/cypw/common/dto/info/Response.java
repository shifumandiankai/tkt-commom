package com.mtl.cypw.common.dto.info;

import com.mtl.cypw.common.dto.BaseDTO;
import com.mtl.cypw.common.enums.ResponseCode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Johnathon.Yuan
 * @date 2019-11-13 15:02
 */
@Data
@NoArgsConstructor
public class Response<T> extends BaseDTO {

    /**
     * code
     */
    private int code;

    /**
     * error msg
     */
    private String msg;

    /**
     * result object
     */
    private T data;

    public boolean isSuccess() {
        return this.getCode() == ResponseCode.SUCCESS.getCode();
    }
}
