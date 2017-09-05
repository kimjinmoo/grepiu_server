package com.iuom.springboot.process.api.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Api 서비스
 */
@Service
public class ApiServiceImpl implements ApiService {

    @Override
    public List<String> getSampleList() {
        List<String> samples = new ArrayList<>();
        samples.add("List");
        samples.add("Sample");
        samples.add("Test");
        samples.add("Hello");

        return samples;
    }
}
