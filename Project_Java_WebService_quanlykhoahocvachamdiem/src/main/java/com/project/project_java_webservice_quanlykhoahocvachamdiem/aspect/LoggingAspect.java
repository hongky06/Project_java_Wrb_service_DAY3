package com.project.project_java_webservice_quanlykhoahocvachamdiem.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* com.project.project_java_webservice_quanlykhoahocvachamdiem.service.*.*(..))")
    public void serviceLayer() {}

    @Pointcut("execution(* com.project.project_java_webservice_quanlykhoahocvachamdiem.controller.*.*(..))")
    public void controllerLayer() {}

    @Around("serviceLayer()")
    public Object logServiceExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String method = joinPoint.getSignature().getDeclaringTypeName()
                + "." + joinPoint.getSignature().getName();
        log.info("[SERVICE] --> {} | Args: {}", method, Arrays.toString(joinPoint.getArgs()));
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        log.info("[SERVICE] <-- {} | {}ms", method, System.currentTimeMillis() - start);
        return result;
    }

    @Before("controllerLayer()")
    public void logControllerBefore(JoinPoint joinPoint) {
        log.info("[REQUEST] {}.{}() | Args: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(
            pointcut = "execution(* com.project.project_java_webservice_quanlykhoahocvachamdiem.service.SubmissionService.gradeSubmission(..))",
            returning = "result")
    public void logAfterGrading(JoinPoint joinPoint, Object result) {
        Object[] args = joinPoint.getArgs();
        log.info("[GRADING] Submission ID: {} | Score: {} | Feedback: {}",
                args.length > 0 ? args[0] : "?",
                args.length > 1 ? args[1] : "?",
                args.length > 2 ? args[2] : "?");
    }

    @AfterThrowing(pointcut = "serviceLayer() || controllerLayer()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        log.error("[EXCEPTION] {}.{}() | {}: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                ex.getClass().getSimpleName(),
                ex.getMessage());
    }
}