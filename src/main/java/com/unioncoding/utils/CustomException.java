package com.unioncoding.utils;

/**
 * Created by 吴晓冬 on 2017/9/8.
 */
public class CustomException extends RuntimeException
{
    /**
     * 错误码
     */
    private String retCode;

    /**
     * 错误描述
     */
    private String retMsg;

    public CustomException()
    {

    }

    public CustomException(String retCode, String retMsg)
    {
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    public String getRetCode()
    {
        return retCode;
    }

    public void setRetCode(String retCode)
    {
        this.retCode = retCode;
    }

    public String getRetMsg()
    {
        return retMsg;
    }

    public void setRetMsg(String retMsg)
    {
        this.retMsg = retMsg;
    }
}
