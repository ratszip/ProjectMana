package com.thf.controller;

import com.thf.common.oo.ResultVO;
import com.thf.config.MultiRequestBody;
import com.thf.entity.Module;
import com.thf.service.ModuleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;

@Controller
@RequestMapping("/module")
@ResponseBody
@CrossOrigin
public class ModuleController {
    @Resource
    ModuleService moduleService;

    @RequestMapping("/create")
    public ResultVO createModule(@RequestHeader String token,
                                  @MultiRequestBody int pId,
                                  @MultiRequestBody Module module){
        ResultVO resultVO= moduleService.createModule(token,pId,module);
        return resultVO;
    }


    @RequestMapping("/update")
    public ResultVO updateModule(@RequestHeader String token,
                                  @MultiRequestBody Module module){
        ResultVO resultVO= moduleService.updateModule(token,module);
        return resultVO;
    }

    @RequestMapping("/delete")
    public ResultVO deleteModule(@RequestHeader String token,
                                   @MultiRequestBody long[] midArr) {
        ResultVO resultVO = moduleService.deleteModule(token,midArr);
        return resultVO;
    }

    @RequestMapping("/list/detail")
    public ResultVO getMList(@RequestHeader String token,
                                  @MultiRequestBody long pid) {

        ResultVO resultVO = moduleService.searchByPid(token,pid);
        return resultVO;
    }

    @RequestMapping("/detail")
    public ResultVO getMDtl(@RequestHeader String token,
                              @MultiRequestBody long mid) {
        ResultVO resultVO = moduleService.searchByIdDtl(token,mid);
        return resultVO;
    }

}
