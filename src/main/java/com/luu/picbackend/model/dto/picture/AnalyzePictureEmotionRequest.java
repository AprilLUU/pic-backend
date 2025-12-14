package com.luu.picbackend.model.dto.picture;

import lombok.Data;

import java.io.Serializable;

@Data
public class AnalyzePictureEmotionRequest {

    private Long pictureId;

    /**
     * 文本描述（用户输入 or OCR / ASR 结果）
     */
    private String text;

    private static final long serialVersionUID = -1L;
}

