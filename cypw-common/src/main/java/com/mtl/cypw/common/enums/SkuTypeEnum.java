package com.mtl.cypw.common.enums;

import com.juqitech.entity.EntityEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单商品类别【1-普通票品, 2-选座票品, 3-衍生品】
 * @author Johnathon.Yuan
 * @date 2019-11-26 09:56
 */
@Getter
@AllArgsConstructor
public enum SkuTypeEnum implements EntityEnum {

    TICKET(1, "普通票品"),
    SEAT_TICKET(2, "选座票品"),
    GOODS(3, "衍生品");

    public static SkuTypeEnum getObject(int code) {
        for (SkuTypeEnum skuTypeEnum : values()) {
            if (skuTypeEnum.getCode() == code) {
                return skuTypeEnum;
            }
        }
        return null;
    }

    private int code;
    private String name;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }
}
