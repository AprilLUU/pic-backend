package com.luu.picbackend.api.aliyunai;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.luu.picbackend.api.aliyunai.model.*;
import com.luu.picbackend.exception.BusinessException;
import com.luu.picbackend.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class AliYunAiApi {

    // 读取配置文件
    @Value("${aliYunAi.apiKey}")
    private String apiKey;

    // 创建任务地址
    public static final String CREATE_OUT_PAINTING_TASK_URL = "https://dashscope.aliyuncs.com/api/v1/services/aigc/image2image/out-painting";

    // 查询任务状态
    public static final String GET_OUT_PAINTING_TASK_URL = "https://dashscope.aliyuncs.com/api/v1/tasks/%s";

    // 创建任务地址
    public static final String CREATE_TEXT2IMAGE_TASK_URL = "https://dashscope.aliyuncs.com/api/v1/services/aigc/text2image/image-synthesis";

    // 查询任务状态
    public static final String GET_TEXT2IMAGE_TASK_URL = "https://dashscope.aliyuncs.com/api/v1/tasks/%s";

    // 调用大模型进行情感分析
    private static final String CHAT_COMPLETION_URL = "https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions";


    /**
     * 创建任务
     *
     * @param createOutPaintingTaskRequest
     * @return
     */
    public CreateOutPaintingTaskResponse createOutPaintingTask(CreateOutPaintingTaskRequest createOutPaintingTaskRequest) {
        if (createOutPaintingTaskRequest == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "扩图参数为空");
        }
        // 发送请求
        HttpRequest httpRequest = HttpRequest.post(CREATE_OUT_PAINTING_TASK_URL)
                .header("Authorization", "Bearer " + apiKey)
                // 必须开启异步处理
                .header("X-DashScope-Async", "enable")
                .header("Content-Type", "application/json")
                .body(JSONUtil.toJsonStr(createOutPaintingTaskRequest));
        // 处理响应
        try (HttpResponse httpResponse = httpRequest.execute()) {
            if (!httpResponse.isOk()) {
                log.error("请求异常：{}", httpResponse.body());
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "AI 扩图失败");
            }
            CreateOutPaintingTaskResponse createOutPaintingTaskResponse = JSONUtil.toBean(httpResponse.body(), CreateOutPaintingTaskResponse.class);
            if (createOutPaintingTaskResponse.getCode() != null) {
                String errorMessage = createOutPaintingTaskResponse.getMessage();
                log.error("请求异常：{}", errorMessage);
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "AI 扩图失败，" + errorMessage);
            }
            return createOutPaintingTaskResponse;
        }
    }

    /**
     * 查询创建的任务结果
     *
     * @param taskId
     * @return
     */
    public GetOutPaintingTaskResponse getOutPaintingTask(String taskId) {
        if (StrUtil.isBlank(taskId)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "任务 ID 不能为空");
        }
        // 处理响应
        String url = String.format(GET_OUT_PAINTING_TASK_URL, taskId);
        try (HttpResponse httpResponse = HttpRequest.get(url)
                .header("Authorization", "Bearer " + apiKey)
                .execute()) {
            if (!httpResponse.isOk()) {
                log.error("请求异常：{}", httpResponse.body());
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "获取任务结果失败");
            }
            return JSONUtil.toBean(httpResponse.body(), GetOutPaintingTaskResponse.class);
        }
    }

    /**
     * 创建任务
     *
     * @param createText2ImageTaskRequest
     * @return
     */
    public CreateText2ImageTaskResponse createText2ImageTask(CreateText2ImageTaskRequest createText2ImageTaskRequest) {
        if (createText2ImageTaskRequest == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "文生图参数为空");
        }
        // 发送请求
        HttpRequest httpRequest = HttpRequest.post(CREATE_TEXT2IMAGE_TASK_URL)
                .header("Authorization", "Bearer " + apiKey)
                // 必须开启异步处理
                .header("X-DashScope-Async", "enable")
                .header("Content-Type", "application/json")
                .body(JSONUtil.toJsonStr(createText2ImageTaskRequest));
        // 处理响应
        try (HttpResponse httpResponse = httpRequest.execute()) {
            if (!httpResponse.isOk()) {
                log.error("请求异常：{}", httpResponse.body());
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "文生图任务失败");
            }
            CreateText2ImageTaskResponse createText2ImageTaskResponse = JSONUtil.toBean(httpResponse.body(), CreateText2ImageTaskResponse.class);
            if (createText2ImageTaskResponse.getCode() != null) {
                String errorMessage = createText2ImageTaskResponse.getMessage();
                log.error("请求异常：{}", errorMessage);
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "文生图任务失败，" + errorMessage);
            }
            return createText2ImageTaskResponse;
        }
    }

    /**
     * 查询创建的任务结果
     *
     * @param taskId
     * @return
     */
    public GetText2ImageTaskResponse getText2ImageTask(String taskId) {
        if (StrUtil.isBlank(taskId)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "任务 ID 不能为空");
        }
        // 处理响应
        String url = String.format(GET_TEXT2IMAGE_TASK_URL, taskId);
        try (HttpResponse httpResponse = HttpRequest.get(url)
                .header("Authorization", "Bearer " + apiKey)
                .execute()) {
            if (!httpResponse.isOk()) {
                log.error("请求异常：{}", httpResponse.body());
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "获取任务结果失败");
            }
            return JSONUtil.toBean(httpResponse.body(), GetText2ImageTaskResponse.class);
        }
    }

    /**
     * 调用 DashScope 大模型（通用）
     */
    public String chatCompletionStream(SentimentAnalysisRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "AI 请求参数为空");
        }

        HttpRequest httpRequest = HttpRequest.post(CHAT_COMPLETION_URL)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .body(JSONUtil.toJsonStr(request));

        try (HttpResponse response = httpRequest.execute()) {

            if (!response.isOk()) {
                log.error("DashScope 请求失败：{}", response.body());
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "AI 调用失败");
            }

            // 读取流式响应
            return parseStreamResponse(response);

        } catch (Exception e) {
            log.error("DashScope 流式调用异常", e);
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "AI 调用异常");
        }
    }


    /**
     * 解析大模型返回内容
     */
    private String parseStreamResponse(HttpResponse response) throws IOException {

        StringBuilder result = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(response.bodyStream(), StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {

                // SSE 以 data: 开头
                if (!line.startsWith("data:")) {
                    continue;
                }

                String jsonStr = line.substring(5).trim();

                // 结束标志
                if ("[DONE]".equals(jsonStr)) {
                    break;
                }

                JSONObject json = JSONUtil.parseObj(jsonStr);
                JSONArray choices = json.getJSONArray("choices");
                if (CollUtil.isEmpty(choices)) {
                    continue;
                }

                JSONObject delta = choices.getJSONObject(0).getJSONObject("delta");
                if (delta == null) {
                    continue;
                }

                String content = delta.getStr("content");
                if (StrUtil.isNotBlank(content)) {
                    result.append(content);
                }
            }
        }

        return result.toString().trim();
    }

}