package com.thf.common.oo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "统一请求的返回对象")
public class ResultVO<T> {
    @ApiModelProperty(value = "错误代码")
    private Integer code;
    @ApiModelProperty(value = "消息")
    private String msg;
    @ApiModelProperty(value = "对应返回数据")
    private T data;

    public ResultVO(int code, String mesage) {
        setCode(code);
        setMsg(mesage);
    }

    public ResultVO(IErrorCode errorCode, T data) {
        setCodeMessage(errorCode);
        setData(data);
    }

    public ResultVO setCodeMessage(IErrorCode codeMessage) {
        setCode(codeMessage.getCode());
        setMsg(codeMessage.getMsg());
        return this;
    }
}