package com.luu.picbackend.api.aliyunai.model;

import cn.hutool.core.annotation.Alias;
import lombok.Data;
import java.io.Serializable;

/**
 * 创建扩图任务请求
 */
@Data
public class CreateText2ImageTaskRequest implements Serializable {

    /**
     * 必选，模型名称，例如 "wanx2.1-t2i-turbo"
     */
    private String model = "wanx2.1-t2i-turbo";

    /**
     * 必选，输入信息
     */
    private Input input;

    /**
     * 可选，图像处理参数
     */
    private Parameters parameters;

    @Data
    public static class Input {
        /**
         * 必选，正向提示词（长度限制500字符）
         */
        private String prompt;

        /**
         * 可选，反向提示词（长度限制500字符）
         */
        @Alias("negative_prompt")
        private String negativePrompt;
    }

    @Data
    public static class Parameters implements Serializable {
        /**
         * 可选，输出图像分辨率（默认"1024*1024"）
         * 格式示例："768*1024", "1280*720"
         */
        private String size = "1024*1024";

        /**
         * 可选，生成数量（1-4，默认4）
         */
        private Integer n = 1;

        /**
         * 可选，随机数种子（0~4294967290）
         */
        private Long seed;

        /**
         * 可选，是否开启Prompt智能改写（默认true）
         */
        @Alias("prompt_extend")
        private Boolean promptExtend = true;

        /**
         * 可选，是否添加水印（默认false）
         */
        private Boolean watermark = false;
    }
}