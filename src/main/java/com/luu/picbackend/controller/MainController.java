package com.luu.picbackend.controller;

import com.luu.picbackend.common.BaseResponse;
import com.luu.picbackend.common.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainController {

    @GetMapping("/hello")
    public BaseResponse<String> hello() {
        return ResultUtils.success("hello");
    }
}
