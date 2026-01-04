package com.project.todoappbackend.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
public class PerformanceMonitorAspect {

    @Around("execution(* com.project.todoappbackend.service.TaskService.*(..))")
    public Object monitorTime(ProceedingJoinPoint jp) throws Throwable{

        long startTime = System.currentTimeMillis();

        // Execute the target method
        Object obj =jp.proceed();

        long endTime = System.currentTimeMillis();

        long executionTime = endTime - startTime;

        log.info("{}: takes : {} millisecond execution time", jp.getSignature().getName(), executionTime);

        return obj;

    }

}
