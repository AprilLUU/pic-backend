package com.luu.picbackend.common;

import lombok.Data;
import java.io.Serializable;

@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 432904223731109601L;
}
