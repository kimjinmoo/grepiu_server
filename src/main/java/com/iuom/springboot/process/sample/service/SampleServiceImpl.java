package com.iuom.springboot.process.sample.service;

import com.iuom.springboot.common.crawler.CrawlerHelper;
import com.iuom.springboot.common.crawler.node.LotteCinemaNode;
import com.iuom.springboot.process.sample.dao.SampleDAO;
import com.iuom.springboot.process.sample.dao.SampleMapper;
import com.iuom.springboot.process.sample.domain.TestMongoDBRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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

    /**
     *
     * 롯데시네마 크롤링
     *
     */
    @Async
    @Override
    public void lotteRun() {
        CrawlerHelper ch = new CrawlerHelper();
        ch.addNode(new LotteCinemaNode());
        ch.execute();
        log.debug("data : {}", ch.getData());
    }
}
