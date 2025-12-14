package com.luu.picbackend.model.dto.picture;

import lombok.Data;

import java.io.Serializable;

@Data
public class AnalyzePictureEmotionResponse {

    /**
     * 情感分类：积极 / 中性 / 消极
     */
    private String category;

    /**
     * AI 原始分析文本（可选，方便调试或前端展示）
     */
    private String aiResult;

    private static final long serialVersionUID = -1L;
}

