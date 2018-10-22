package com.yibi.orderapi.dto;

import com.yibi.core.entity.Banner;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 算力详情，当前算力、排名、登录获取算力、算力排行榜
 * @author xuqingzhong
 */

@Data
public class CalculateForceDetail{
    private int signDays;
    private List<Banner> banners;
    private soulTask daySignTask; //每日签到任务
    private soulTask tenSignTask;//联系10天签到
    private soulTask monthSignTask;//连续30天签到
    private soulTask dealTask;	//每日C2C/现货交易
    private soulTask qqShareTask;		//每日Q分享
    private soulTask qzoneShareTask;	//每日空间分享
    private soulTask wechatShareTask; //微信分享
    private soulTask circleShareTask; //朋友圈分享
    private soulTask joinWechatTask; //加入微信群
    private soulTask joinPublicTask; // 关注公众号
    private soulTask joinQGroupTask; //加入Q群
    private soulTask realNameTask;//实名任务
    private String instruUrl;//帮助文档
    private String shareTitle;//分享标题
    private String shareDes;//分享内容
    private String shareUrl;//分享注册链接

    private List<Map<String, Object>> userForces;
    private Integer level;//称号等级
    private boolean worldCupFinished;//是否已参加世界杯竞猜
    private boolean displayWorldCup;//是否显示世界杯竞猜奖励


    class soulTask{
        boolean isFinished;
        int soulForce;

        public boolean isFinished() {
            return isFinished;
        }

        public void setFinished(boolean finished) {
            isFinished = finished;
        }

        public int getSoulForce() {
            return soulForce;
        }

        public void setSoulForce(int soulForce) {
            this.soulForce = soulForce;
        }
    }
}
