package com.iuom.springboot.process.sample.service;

import com.iuom.springboot.process.sample.dao.SampleDAO;
import com.iuom.springboot.process.sample.dao.SampleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Api 서비스
 */
@Service
public class SampleServiceImpl implements SampleService {

    @Autowired
    private SampleDAO sampleDAO;

    @Autowired
    private SampleMapper sampleMapper;

    @Override
    public List<String> getSampleList() {
        List<String> samples = new ArrayList<>();
        samples.add("List");
        samples.add("Sample");
        samples.add("Test");
        samples.add("Hello");
        samples.add(sampleDAO.getSampleData());
        samples.add(sampleMapper.getSampleData());

        return samples;
    }
}
