package org.geeksforgeeks.my_spring_app.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
@Slf4j
public class MethodLoggingAspect {

    @Around("@annotation(org.geeksforgeeks.my_spring_app.annotations.LogExecution)")
    public Object logMethodStartAndEnd(ProceedingJoinPoint joinPoint) throws Throwable {
        String functionName = joinPoint.getSignature().getName();
        log.info("{} has started", functionName);
        Object result = joinPoint.proceed();
        log.info("{} has ended", functionName);
        return result;
    }
}
