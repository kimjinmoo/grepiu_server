package com.grepiu.test.process.common;

import com.grepiu.test.process.config.LocalBaseConfig;
import com.grepiu.www.process.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class UtilsTDD extends LocalBaseConfig {

    @Override
    public void setUp() {

    }

    @Test
    public void Date() {
        log.info("getYear {}", DateUtils.getYear());
        log.info("getMonth {}", DateUtils.getMonth());
        log.info("getDay {}", DateUtils.getDay());
    }
}
