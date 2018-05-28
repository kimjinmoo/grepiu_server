package com.grepiu.www.process.sample.service;

import com.grepiu.www.process.sample.dao.SampleDAO;
import com.grepiu.www.process.sample.dao.SampleMapper;
import com.grepiu.www.process.sample.dao.TestMongoDBRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Api 서비스
 */
@Service
@Slf4j
public class SampleServiceImpl implements SampleService {

    @Autowired
    private SampleDAO sampleDAO;

    @Autowired
    private SampleMapper sampleMapper;

    @Autowired
    private TestMongoDBRepository repository;

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
