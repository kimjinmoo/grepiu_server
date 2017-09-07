package com.iuom.springboot.process.api.service;

import com.iuom.springboot.process.api.dao.ApiDAO;
import com.iuom.springboot.process.api.dao.ApiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Api 서비스
 */
@Service
public class ApiServiceImpl implements ApiService {

    @Autowired
    private ApiDAO apiDAO;

    @Autowired
    private ApiMapper apiMapper;

    @Override
    public List<String> getSampleList() {
        List<String> samples = new ArrayList<>();
        samples.add("List");
        samples.add("Sample");
        samples.add("Test");
        samples.add("Hello");
        samples.add(apiDAO.getSampleData());
        samples.add(apiMapper.getSampleData());

        return samples;
    }
}
