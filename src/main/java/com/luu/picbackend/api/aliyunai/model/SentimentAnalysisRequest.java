package com.luu.picbackend.api.aliyunai.model;

import lombok.Data;

import java.util.List;

/**
 * DashScope 多模态情感分析请求
 */
@Data
public class SentimentAnalysisRequest {

    /**
     * 模型名称
     */
    private String model = "qwen3-omni-flash";

    /**
     * 是否流式
     */
    private Boolean stream = true;

    /**
     * 响应模态
     */
    private String[] MODALITIES = {"text"};


    /**
     * 对话消息
     */
    private List<DashScopeMessage> messages;

    @Data
    public static class DashScopeMessage {

        /**
         * user / assistant
         */
        private String role = "user";

        private List<DashScopeContent> content;

        @Data
        public static class DashScopeContent {

            /**
             * text / image_url
             */
            private String type;

            /**
             * 文本内容（type=text 时使用）
             */
            private String text;

            /**
             * 图片内容（type=image_url 时使用）
             */
            private ImageUrl image_url;

            @Data
            public static class ImageUrl {
                private String url;
            }
        }
    }
}
