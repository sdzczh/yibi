package com.yibi.batch.biz;

import com.yibi.batch.BaseTest;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.service.SysparamsService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ZhaoHe on 2018/7/24 0024.
 */
public class PushTest extends BaseTest{
    @Autowired
    private PushBiz pushBiz;
    @Autowired
    private KlineBiz klineBiz;
    @Autowired
    private SysparamsService sysparamsService;

    private Long lastTime1;


    @Test
    public void test(){
        String str = sysparamsService.getValStringByKey(SystemParams.SYSTEM_PUSH_DIG);
        pushBiz.start(str);
    }
    @Test
    public void test1(){
        Long timeInteval = 60000l;
        lastTime1 = klineBiz.klineJob(timeInteval, lastTime1);
    }
}
