package com.example.backend.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class JobServiceLogger {

    private static final Logger logger = LoggerFactory.getLogger(JobServiceLogger.class);

    @Before("execution(* com.example.backend.service.impl.JobServiceImpl.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Method called: " + joinPoint.getSignature().getName());
        logger.info("Arguments: " + getArguments(joinPoint.getArgs()));
    }

    private String getArguments(Object[] args) {
        StringBuilder arguments = new StringBuilder();
        for (Object arg : args) {
            arguments.append(arg).append(", ");
        }
        if (arguments.length() > 2) {
            arguments.setLength(arguments.length() - 2);
        }
        return arguments.toString();
    }
}