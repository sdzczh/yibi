package com.yibi.core;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/8/5
 * version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring-core-common.xml"})
@ActiveProfiles("develop")
@Ignore
public class BaseTest extends AbstractJUnit4SpringContextTests {
}
