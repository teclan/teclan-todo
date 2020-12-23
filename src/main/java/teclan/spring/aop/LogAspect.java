package teclan.spring.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import teclan.spring.filter.BodyReaderHttpServletRequestWrapper;
import teclan.spring.filter.StatusExposingServletResponse;
import teclan.spring.model.Log;
import teclan.spring.service.LogService;
import teclan.spring.util.HttpTool;
import teclan.spring.util.ResultUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Aspect
public class LogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    private LogService logService;

    //配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
    @Pointcut("execution(* teclan.spring.ctrl..*(..))")
    public void aspect(){	}


    /*
     * 配置前置通知,使用在方法aspect()上注册的切入点
     * 同时接受JoinPoint切入点对象,可以没有该参数
     */
    @Before("aspect()")
    public void before(JoinPoint joinPoint){

    }

    //配置后置通知,使用在方法aspect()上注册的切入点
    @After("aspect()")
    public void after(JoinPoint joinPoint){

        for(Object o: joinPoint.getArgs()){
            LOGGER.info("==== after " + o);
        }
    }

    //配置环绕通知,使用在方法aspect()上注册的切入点
    @Around("aspect()")
    public void around(JoinPoint joinPoint){
        try {

            Object[] args = joinPoint.getArgs();

            HttpServletRequest httpServletRequest = (HttpServletRequest)args[0];

            HttpServletResponse httpServletResponse = (HttpServletResponse)args[1];
            StatusExposingServletResponse response = new StatusExposingServletResponse(httpServletResponse);

            Object result =  ((ProceedingJoinPoint) joinPoint).proceed();

            Log log =Log.parse(httpServletRequest);
            log.setResult(JSON.toJSONString(result));
            log.setStatus(((JSONObject) result).getString("code"));
            HttpTool.setResponse(response,200, (JSONObject) result);

            logService.create(log);

            LOGGER.info("操作日志:{}",log);
        } catch (Throwable e) {
            LOGGER.error(e.getMessage(),e);
        }
    }

    //配置后置返回通知,使用在方法aspect()上注册的切入点
    @AfterReturning("aspect()")
    public void afterReturn(JoinPoint joinPoint){

       for(Object o: joinPoint.getArgs()){
           LOGGER.info("==== afterReturn " + o);
       }
    }

    //配置抛出异常后通知,使用在方法aspect()上注册的切入点
    @AfterThrowing(pointcut="aspect()", throwing="ex")
    public void afterThrow(JoinPoint joinPoint, Exception ex){
        LOGGER.info("afterThrow " + joinPoint);
    }


}
