package com.thf.common.oo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO {
    private Integer code;
    private String msg;
    private Object data;

    public ResultVO(Integer code,String msg){
        this.code=code;
        this.msg=msg;
    }
}