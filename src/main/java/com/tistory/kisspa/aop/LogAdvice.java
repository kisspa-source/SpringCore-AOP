package com.tistory.kisspa.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

@Aspect
@Slf4j
@Component
public class LogAdvice {

    /**
     * SampleService로 시작하는 클래스의 실행전에 수행
     */
    @Before( "execution(* com.tistory.kisspa.service.SampleService*.*(..))")
    public void logBefore() {
        log.info("before..");
    }

    /**
     * SampleService로 시작하는 plus 메서드 실행전에 수행
     * 인자값을 제어할 수 있다.
     * @param str1
     * @param str2
     */
    @Before("execution(* com.tistory.kisspa.service.SampleService*.plus(String, String)) && args(str1, str2)")
    public void logBeforeWithParam(String str1, String str2) {
        log.info("str1 : "+str1);
        log.info("str2 : "+str2);
    }

    /**
     * SampleService로 시작하는 클래스에서 Exception 발생 시 수행
     * @param exception
     */
    @AfterThrowing(pointcut = "execution(* com.tistory.kisspa.service.SampleService*.*(..))", throwing = "exception")
    public void logExceptoin(Exception exception) {
        log.info("AfterThrowing 에서 Exception...........");
        log.info(getPrintStackTrace(exception));
    }

    /**
     * SampleService로 시작하는 클래스의 전, 후에 수행
     * 인자값이나 오류시 처리, 처리 이후를 제어 할 수 있다.
     * @param pjp
     * @return
     */
    @Around("execution(* com.tistory.kisspa.service.SampleService*.*(..))")
    public Object logTime(ProceedingJoinPoint pjp) {
        long start = System.currentTimeMillis();

        log.info("Target : "+pjp.getTarget());
        log.info("param :"+ Arrays.toString(pjp.getArgs()));

        //invoke method
        Object result = null;

        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            log.info("Around 에서 Exception Catch...........");
            log.info(getPrintStackTrace(e));
        }

        long end = System.currentTimeMillis();

        log.info("TIME (sec) : "+ (end-start)/1000);

        return result;

    }

    /**
     * [UTIL] Exception의 printStackTrace를 문자열로 저장하여 리턴
     * @param e
     * @return
     */
    public static String getPrintStackTrace(Exception e) {

        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));

        return errors.toString();

    }

    /**
     * [UTIL] Throwable의 printStackTrace를 문자열로 저장하여 리턴
     * @param e
     * @return
     */
    public static String getPrintStackTrace(Throwable e) {

        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));

        return errors.toString();

    }
}
