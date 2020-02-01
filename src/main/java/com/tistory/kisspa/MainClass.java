package com.tistory.kisspa;

import com.tistory.kisspa.service.SampleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Slf4j
public class MainClass {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "root-context.xml");

        SampleService service = (SampleService) applicationContext.getBean("SampleService");

        try {

            log.info(String.valueOf(service.plus("123", "456")));

            log.info(String.valueOf(service.plus("AAA", "456")));

        } catch (Exception e) {
            //e.printStackTrace();
        }

    }

}
