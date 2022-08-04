package com.thf.controller;

import com.thf.common.oo.ResultVO;
import com.thf.config.MultiRequestBody;
import com.thf.entity.Function;
import com.thf.service.FunctionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/function")
@ResponseBody
@CrossOrigin
public class FunctionController {

    @Resource
    FunctionService functionService;

    @RequestMapping("/create")
    public ResultVO createModule(@RequestHeader String token,
                                 @MultiRequestBody long mId,
                                  @MultiRequestBody Function function) {
        ResultVO resultVO = functionService.createFunc(token, mId, function);
        return resultVO;
    }

    @RequestMapping("/update")
    public ResultVO updateFunction(@RequestHeader String token,
                                    @MultiRequestBody Function function) {
        ResultVO resultVO = functionService.updateFunc(token, function);
        return resultVO;
    }


    @RequestMapping("/delete")
    public ResultVO deleteFunction(@RequestHeader String token,
                                    @MultiRequestBody long[] fidArr) {
        ResultVO resultVO = functionService.deleteFunc(token, fidArr);
        return resultVO;
    }


    @RequestMapping("/list")
    public ResultVO listFunc(@RequestHeader String token,
                                    @MultiRequestBody long mid) {
        ResultVO resultVO = functionService.searchAllFunc(token, mid);
        return resultVO;
    }

}
