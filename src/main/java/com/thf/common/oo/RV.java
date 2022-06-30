package com.thf.common.oo;

public class RV {

    /***
     * 成功的返回对象
     * @param data
     * @return
     */
    public static ResultVO success(Object data) {
        return new ResultVO(ErrorCode.SUCCESS,data);
    }

    /**
     * 失败的返回对象
     * @Param: ErrCodeInterface
     * @return: [ResultVO]
     *
     **/
    public static ResultVO fail(IErrorCode errorCode) {
        return new ResultVO().setCodeMessage(errorCode);
    }

    /**
     * 描述: 通过errorCode和数据对象参数，构建一个新的对象
     * @param [errorCode, data]
     * @return: [ResultVO]
     **/
    public static ResultVO result(IErrorCode errorCode,Object data){
        return new ResultVO(errorCode,data);
    }
}
