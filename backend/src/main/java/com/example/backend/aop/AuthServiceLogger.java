package com.example.backend.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.example.backend.utils.AspectUtils.getArguments;

@Aspect
@Component
public class AuthServiceLogger {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceLogger.class);

    @Before("execution(* com.example.backend.service.impl.AuthServiceImpl.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Method called: " + joinPoint.getSignature().getName());
        logger.info("Arguments: " + getArguments(joinPoint.getArgs()));
    }

}
