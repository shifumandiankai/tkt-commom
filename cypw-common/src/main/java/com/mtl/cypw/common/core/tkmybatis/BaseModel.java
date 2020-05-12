package com.mtl.cypw.common.core.tkmybatis;

import com.mtl.cypw.common.enums.DeleteEnum;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Johnathon.Yuan
 * @date 2019-11-18 18:04
 */
@Data
public class BaseModel implements Serializable {

    /**
     * 创建时间
     */
    @Column(name = "create_time", insertable = false)

    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time", insertable = false)

    private Date updateTime;

    /**
     * 逻辑删除[0-有效, 1-删除]
     */
    @Column(name = "deleted", insertable = false)

    private Integer deleted;

    public void delete() {
        this.setDeleted(DeleteEnum.DELETED.getCode());
    }

    public Integer getIdentify() {
        return null;
    }

    public boolean brandNew() {
        return getIdentify() == null || getIdentify().equals(0);
    }


}
