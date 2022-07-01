package com.thf.common.oo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO<T> {
    @ApiModelProperty(value = "状态码")
    private Integer code;
    @ApiModelProperty(value = "说明")
    private String msg;
    @ApiModelProperty(value = "数据")
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