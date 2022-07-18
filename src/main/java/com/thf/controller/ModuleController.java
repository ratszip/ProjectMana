package com.thf.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/module")
@ResponseBody
@Api(value = "项目模块",tags = "项目模块")
public class ModuleController {
}
