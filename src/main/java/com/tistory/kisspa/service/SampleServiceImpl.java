package com.tistory.kisspa.service;

import org.springframework.stereotype.Service;

@Service
public class SampleServiceImpl implements SampleService {

    public Integer plus(String args1, String args2) throws Exception {
        return Integer.parseInt(args1) + Integer.parseInt(args2);
    }
}
