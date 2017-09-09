package com.unioncoding.utils;

/**
 * 错误码枚举
 * Created by 吴晓冬 on 2017/9/9.
 */
public enum ErrorTypeEnum
{
    /* 系统级异常 */
    SYSTEM("0001", "系统异常"),
    MYSQL("0002", "Mysql异常"),
    MONGO("0003", "Mongo异常"),
    REDIS("0004", "Redis异常"),
    MQ("0005", "MQ异常"),
    OTHER("0009", "其他异常");

    private String key;
    private String value;

    private ErrorTypeEnum(String key, String value)
    {
        this.key = key;
        this.value = value;
    }

    public String value() {
        return value;
    }

    public String key() {
        return key;
    }

    public static ErrorTypeEnum getErrorTypeEnumByKey(String key)
    {
        for (ErrorTypeEnum Status : ErrorTypeEnum.values())
        {
            if (Status.key.equals(key))
            {
                return Status;
            }
        }
        return null;
    }

    public static String getValueByKey(String key)
    {
        String value = "";
        ErrorTypeEnum errorTypeEnum = getErrorTypeEnumByKey(key);
        if (null != errorTypeEnum)
        {
            value = errorTypeEnum.value();
        }
        return value;
    }
}
