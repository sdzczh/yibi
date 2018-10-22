package com.yibi.orderapi.dto;

import com.yibi.core.entity.Banner;
import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2018/7/20 0020.
 */
@Data
public class SoulForceTaskDto {
    private int signDays;
    private List<Banner> banners;
    private SoulTask daySignTask; //每日签到任务
    private SoulTask tenSignTask;//联系10天签到
    private SoulTask monthSignTask;//连续30天签到
    private SoulTask dealTask;	//法币/现货交易
    private SoulTask inviteTask;	//邀请好友
    private SoulTask qqShareTask;		//每日Q分享
    private SoulTask qzoneShareTask;	//每日空间分享
    private SoulTask wechatShareTask; //微信分享
    private SoulTask circleShareTask; //朋友圈分享
    private SoulTask joinWechatTask; //加入微信群
    private SoulTask joinPublicTask; // 关注公众号
    private SoulTask joinQGroupTask; //加入Q群
    private SoulTask realNameTask;//实名任务
    private String instruUrl;//帮助文档
    private String shareTitle;//分享标题
    private String shareDes;//分享内容
    private String shareUrl;//分享注册链接

    public class SoulTask{
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
