package com.unioncoding.utils;

/**
 * Created by 吴晓冬 on 2017/9/9.
 */
public class Response
{
    /**
     * 返回码
     */
    private String retCode;

    /**
     * 返回描述
     */
    private String retMsg;

    private Object body;

    public Response()
    {

    }

    public Response(String retCode, String retMsg)
    {
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    public Response (CustomException customException)
    {
        this.retCode = customException.getRetCode();
        this.retMsg = customException.getRetMsg();
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

    public Object getBody()
    {
        return body;
    }

    public void setBody(Object body)
    {
        this.body = body;
    }
}
