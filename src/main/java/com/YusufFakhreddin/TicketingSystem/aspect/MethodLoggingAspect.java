package com.YusufFakhreddin.TicketingSystem.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MethodLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(MethodLoggingAspect.class);

    @Before("execution(* com.YusufFakhreddin.TicketingSystem..*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Executing method: " + joinPoint.getSignature().getName());
    }

    @After("execution(* com.YusufFakhreddin.TicketingSystem..*(..))")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("Method execution completed: " + joinPoint.getSignature().getName());
    }
}
