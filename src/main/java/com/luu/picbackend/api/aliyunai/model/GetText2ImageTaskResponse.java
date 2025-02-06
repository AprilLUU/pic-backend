package com.luu.picbackend.api.aliyunai.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 查询文生图任务响应类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetText2ImageTaskResponse {

    /**
     * 请求唯一标识
     */
    private String requestId;

    /**
     * 输出信息
     */
    private Output output;

    /**
     * 使用统计
     */
    private Usage usage;

    /**
     * 表示任务的输出信息
     */
    @Data
    public static class Output {

        /**
         * 任务 ID
         */
        private String taskId;

        /**
         * 任务状态
         * <ul>
         *     <li>PENDING：排队中</li>
         *     <li>RUNNING：处理中</li>
         *     <li>SUSPENDED：挂起</li>
         *     <li>SUCCEEDED：执行成功</li>
         *     <li>FAILED：执行失败</li>
         *     <li>UNKNOWN：任务不存在或状态未知</li>
         * </ul>
         */
        private String taskStatus;

        /**
         * 提交时间
         * 格式：YYYY-MM-DD HH:mm:ss.SSS
         */
        private String submitTime;

        /**
         * 调度时间
         * 格式：YYYY-MM-DD HH:mm:ss.SSS
         */
        private String scheduledTime;

        /**
         * 结束时间
         * 格式：YYYY-MM-DD HH:mm:ss.SSS
         */
        private String endTime;

        /**
         * 任务结果列表
         */
        private List<Result> results;

        /**
         * 任务指标信息
         */
        private TaskMetrics taskMetrics;
    }

    /**
     * 任务结果
     */
    @Data
    public static class Result {

        /**
         * 原始输入 prompt
         */
        private String origPrompt;

        /**
         * 实际使用的 prompt
         */
        private String actualPrompt;

        /**
         * 生成图片的 URL
         */
        private String url;

        /**
         * 图像错误码
         */
        private String code;

        /**
         * 图像错误信息
         */
        private String message;
    }

    /**
     * 表示任务的统计信息
     */
    @Data
    public static class TaskMetrics {

        /**
         * 总任务数
         */
        private Integer total;

        /**
         * 成功任务数
         */
        private Integer succeeded;

        /**
         * 失败任务数
         */
        private Integer failed;
    }

    /**
     * 表示任务的使用统计信息
     */
    @Data
    public static class Usage {

        /**
         * 生成的图片数量
         */
        private Integer imageCount;
    }
}
