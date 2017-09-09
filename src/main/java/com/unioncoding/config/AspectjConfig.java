package com.unioncoding.config;

import com.unioncoding.utils.CustomException;
import com.unioncoding.utils.ErrorTypeEnum;
import com.unioncoding.utils.Response;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 切面配置
 * Created by 吴晓冬 on 2017/9/9.
 */
@Component
@Aspect
public class AspectjConfig
{
    private final static Logger logger = LoggerFactory.getLogger(AspectjConfig.class);

    @Around("execution(* com.unioncoding.controller..*.*(..))")
    public Object controllerError(ProceedingJoinPoint jp) throws Throwable
    {
        Response response = null;

        try
        {
            return jp.proceed();
        }
        catch (CustomException customException)
        {
            response = new Response(customException);
        }
        catch (Throwable e)
        {
            logger.error("controller error ", e);
            response = new Response(ErrorTypeEnum.SYSTEM.key(), ErrorTypeEnum.SYSTEM.value());
        }

        return response;
    }

}
