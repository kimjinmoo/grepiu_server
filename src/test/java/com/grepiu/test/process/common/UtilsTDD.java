package com.grepiu.test.process.common;

import com.grepiu.test.process.config.LocalBaseConfig;
import com.grepiu.www.process.common.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class UtilsTDD extends LocalBaseConfig {

    @Override
    public void setUp() {

    }

    @Test
    public void Date() {
        log.info("getYear {}", DateUtil.getYear());
        log.info("getMonth {}", DateUtil.getMonth());
        log.info("getDay {}", DateUtil.getDay());
    }
}
