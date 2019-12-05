/*
 * Copyright (c) 2000-2019 Short Consulting AG. All Rights Reserved.
 */
package com.ds.aspectjspring521;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Denis Spiridonov
 * Date: 04.12.2019
 * Time: 17:41
 */
@Aspect
public class DSAspect {

    private final static Logger logger = LoggerFactory.getLogger(DSAspect.class);

    @Pointcut("@annotation(DSAspectAnnotation)")
    public void annotated() {
    }

    @Pointcut(value = "within(com.ds.aspectjspring521..*)")
    public void inOurPackage() {
    }

    @Pointcut(value = "execution(public * *(..))")
    public void executionOfPublicMethod() {
    }

//    @Before("annotated()")
//    public void beforeAdvice() {
//        executeAspect("beforeAdvice worked fine.");
//    }

    private void executeAspect(String s) {
        logger.info(s);
    }

    @Around(value = "inOurPackage() && executionOfPublicMethod() && annotated()", argNames = "pjp")
    public Object aroundAdvice(final ProceedingJoinPoint pjp) throws Throwable {
        executeAspect("aroundAdvice worked fine.");
        final String returnValue = (String) pjp.proceed();
        return "AroundAdvice >>>>" + returnValue + "<<<< AroundAdvice";
    }

}