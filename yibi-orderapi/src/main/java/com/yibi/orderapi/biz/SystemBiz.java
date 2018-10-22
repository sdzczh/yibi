package com.yibi.orderapi.biz;

import com.yibi.core.entity.User;

/**
 * Created by ZhaoHe on 2018/7/17 0017.
 */
public interface SystemBiz {
    /**
     * 检查更新
     * @param syetemType
     * @param verInt
     * @return
     */
    String checkUpdate(Integer syetemType,Integer version);

    /**
     * 关于信息
     * @return
     */
    String aboutInfo();

    /**
     * 获取配置文件
     * @param versionCode
     * @return
     */
    String getStartupParam(Integer versionCode);

    /**
     * 获取海报
     * @param user
     * @return
     */
    String getPoster(User user);
}
