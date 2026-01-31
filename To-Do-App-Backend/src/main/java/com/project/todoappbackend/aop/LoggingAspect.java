package com.project.todoappbackend.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    // Logger without using lombok SLF4j
//    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    // returnType classNameWithPackage.methodName(args)

//    @Before("execution(* com.project.todoappbackend.service.TaskService.*(..))")
//    public void logMethodCall(JoinPoint jp){
//        LOGGER.info("Method called "+jp.getSignature().getName());
//    }

//    @After("execution(* com.project.todoappbackend.service.TaskService.*(..))")
//    public void logMethodExecution(JoinPoint jp){
//        log.info("Method executed "+jp.getSignature().getName());
//    }

    @AfterThrowing(value = "execution(* com.project.todoappbackend.service.TaskService.*(..))",
            throwing = "ex")
    public void logMethodException(JoinPoint jp, Throwable ex) {
        log.error(
                "Exception in {}.{}() : {}",
                jp.getSignature().getDeclaringTypeName(),   // Class Name
                jp.getSignature().getName(),                // Method Name
                ex.getMessage(),                            // Exception Message
                ex                                          // Exception type, Stack trace(method calls, line numbers)
        );
    }

    @AfterReturning("execution(* com.project.todoappbackend.service.TaskService.*(..))")
    public void logMethodSuccess(JoinPoint jp) {
        log.info("Method execution success {}", jp.getSignature().getName());
    }


}
