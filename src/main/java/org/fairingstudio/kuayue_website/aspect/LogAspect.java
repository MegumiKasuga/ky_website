package org.fairingstudio.kuayue_website.aspect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LogAspect {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    //对所有控制器方法进行织入
    @Pointcut("execution(* org.fairingstudio.kuayue_website.controller..*.*(..))")
    public void log() {}

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        //获取request对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        //获取请求url
        String url = request.getRequestURL().toString();
        //获取请求ip地址
        String ip = request.getRemoteAddr();
        //获取调用的类名与方法名
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        //获取传入的参数
        Object[] args = joinPoint.getArgs();

        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        //写入日志
        LOGGER.info("前置通知-请求信息 : " + requestLog);
    }

    @After("log()")
    public void doAfter() {
        //LOGGER.info("===doAfter===");
    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturning(Object result) {
        //将执行结果写入日志
        LOGGER.info("后置通知-返回结果 : " + result);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    private class RequestLog {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;
    }
}
