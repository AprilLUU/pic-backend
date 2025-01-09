package com.luu.picbackend.model.dto.space;

import lombok.Data;

import java.io.Serializable;

/**
 * 编辑空间请求
 */
@Data
public class SpaceEditRequest implements Serializable {

    /**
     * 空间 id
     */
    private Long id;

    /**
     * 空间名称
     */
    private String spaceName;

    private static final long serialVersionUID = -2496439546482058261L;
}