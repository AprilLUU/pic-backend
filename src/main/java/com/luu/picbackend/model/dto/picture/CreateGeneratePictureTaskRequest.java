package com.luu.picbackend.model.dto.picture;

import com.luu.picbackend.api.aliyunai.model.CreateText2ImageTaskRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * 创建扩图任务请求
 */
@Data
public class CreateGeneratePictureTaskRequest implements Serializable {

    /**
     * 生成提示
     */
    private String prompt;

    /**
     * 扩图参数
     */
    private CreateText2ImageTaskRequest.Parameters parameters;

    private static final long serialVersionUID = -1L;
}