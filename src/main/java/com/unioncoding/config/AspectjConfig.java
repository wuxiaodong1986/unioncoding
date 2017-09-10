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
import org.springframework.transaction.TransactionSystemException;
import org.springframework.util.StringUtils;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        catch (ConstraintViolationException e)//获取参数校验异常
        {
            List<String> msgList = new ArrayList<>();
            for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations())
            {
                msgList.add(constraintViolation.getMessage());
            }
            String messages = StringUtils.arrayToDelimitedString(msgList.toArray(new String[msgList.size()]), ";");
            response = new Response(ErrorTypeEnum.SYSTEM.key(), messages);
        }
        catch (TransactionSystemException transactionSystemException)
        {
            if (transactionSystemException.getOriginalException() instanceof RollbackException)
            {
                RollbackException rollbackException = (RollbackException)transactionSystemException.getOriginalException();

                if (rollbackException.getCause() instanceof ConstraintViolationException)
                {
                    ConstraintViolationException e = (ConstraintViolationException)rollbackException.getCause();

                    List<String> msgList = new ArrayList<>();
                    for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations())
                    {
                        msgList.add(constraintViolation.getMessage());
                    }
                    String messages = StringUtils.arrayToDelimitedString(msgList.toArray(new String[msgList.size()]), ";");
                    response = new Response(ErrorTypeEnum.SYSTEM.key(), messages);
                }
                else
                {
                    throw transactionSystemException;
                }
            }
            else
            {
                throw transactionSystemException;
            }
        }
        catch (CustomException e)//获取自定义异常
        {
            response = new Response(e);
        }
        catch (Throwable e)//其他异常都返回系统异常
        {
            logger.error("controller error ", e);
            response = new Response(ErrorTypeEnum.SYSTEM.key(), ErrorTypeEnum.SYSTEM.value());
        }

        return response;
    }

}
