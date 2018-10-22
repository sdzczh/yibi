package com.yibi.orderapi.controller;

import com.yibi.common.utils.PatternUtil;
import com.yibi.common.utils.RedisUtil;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.Doc;
import com.yibi.core.entity.Notice;
import com.yibi.core.service.CoinIntroductionService;
import com.yibi.core.service.DocService;
import com.yibi.core.service.SysparamsService;
import com.yibi.core.service.UserService;
import com.yibi.orderapi.biz.NoticeBiz;
import com.yibi.orderapi.biz.SmsCodeBiz;
import com.yibi.orderapi.biz.UserBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 银行管理
* @author zhaohe
* @date 2018-7-16
* @version V1.0
 */
@Controller
@RequestMapping("/web")
public class WebController extends BaseController{
	@Autowired
	private UserBiz userBiz;
	@Autowired
	private SmsCodeBiz smsCodeBiz;
	@Autowired
	private NoticeBiz noticeBiz;
	@Autowired
	private DocService docService;
	@Autowired
	private UserService userService;
	@Autowired
	private SysparamsService sysparamsService;
	@Autowired
	private CoinIntroductionService coinIntroductionService;

	@Autowired
	private RedisTemplate<String, String> redis;


	/**
	 * 跳转注册页
	 * @param map
	 * @param phone
     * @return
     */
	@RequestMapping(value="register",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	public String toRegister(Map<String, Object> map, String phone){
		map.put("phone", phone);
		return "regiest";
	}

	/**
	 * 用户WEB注册
	 * @param phone
	 * @param userPassword
	 * @param code
	 * @param codeId
	 * @param referPhone
     * @return
     */
	@ResponseBody
	@RequestMapping(value="submitRegister",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String register(String phone,String userPassword,String code,Integer codeId,String referPhone){
		/*正则校验*/
		if(!PatternUtil.isPhone(phone)){
			return Result.toResult(ResultCode.PHONE_TYPE_ERROR);
		}
		if(!PatternUtil.isPhone(referPhone)){
			return Result.toResult(ResultCode.PHONE_TYPE_ERROR);
		}
		if(!PatternUtil.isVerificationCode(code)){
			return Result.toResult(ResultCode.SMSCODE_TYPE_ERROR);
		}
		if(!PatternUtil.isDigitalAndWord(userPassword)){
			return Result.toResult(ResultCode.PASSWORD_TYPE_ERROR);
		}
		try {
			return userBiz.register(phone, code, codeId, userPassword, referPhone, "", 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Result.toResult(ResultCode.REGISTER_ERROR);
	}

	/**
	 * 获取校验码
	 * @param phone
	 * @param type
     * @return
     */
	@ResponseBody
	@RequestMapping(value="smscode",method=RequestMethod.POST ,produces = "application/json;charset=utf-8")
	public String getValidateCode(String phone ,Integer type,String VerificationCode){
		try {
			String randCode = RedisUtil.searchString(redis,"RandCode"+phone);
			if (!VerificationCode.equalsIgnoreCase(randCode)){
				return Result.toResult(ResultCode.VCODE_FALSE);
			}
			//获取校验码
			String returnStr = smsCodeBiz.getValidateCode(phone, type);
			return returnStr;
		}catch (Exception e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.SMS_ERROR);
		}
	}

	/**
	 * 公告
	 * @param id
	 * @param map
     * @return
     */
	@RequestMapping(value="article/{id}",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	public String notice(@PathVariable("id")Integer id, Map<String, Object> map){
		try {
			//查询文章
			Notice notice = noticeBiz.getNoticeById(map, id);
			String url = sysparamsService.getValStringByKey(SystemParams.SYSTEM_URL);
			Integer count = 6;
			for(int i = 1; i < count; i++){
				Notice not = noticeBiz.getNoticeById(map, id - i);
				if(id - i < 0){
					break;
				}
				if(not == null){
					count ++;
					continue;
				}
				map.put("notice" + i, not);
				map.put("url" + i, url + "/web/article/" + (id -i) + ".action");
			}
			map.put("notice", notice);
			return "index";
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "index";
	}

	/**
	 * 分享
	 * @param map
	 * @param phone
     * @return
     */
	@RequestMapping(value = "share", method = RequestMethod.GET)
	public String share(Map<String, Object> map, String phone) {
		if(StrUtils.isBlank(phone)){
			return "share";
		}
		map.put("url", sysparamsService.getValStringByKey(SystemParams.APP_CONFIG_LOGGED_SHARE_URL) + phone);
		return "share";
	}

	/**
	 * 文档
	 * @param type
	 * @param map
     * @return
     */
	@RequestMapping(value="doc/{type}",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	public String doc(@PathVariable("type")Integer type, Map<String, Object> map){
		try {
			//查询文章
			Map param = new HashMap();
			param.put("type", type);
			List<Doc> list = docService.selectAll(param);
			map.put("doc", list == null || list.isEmpty() ? null : list.get(0));
			return "article";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "article";
	}

	/**
	 * 币种介绍
	 * @param map
	 * @param coinType
     * @return
     */
	@RequestMapping(value="coin/intro",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	public String queryCoinInroduction(Map<String, Object> map, Integer coinType){
		coinIntroductionService.queryCoinInroduction(map,coinType);
		return "coinIntroduction";
	}
}
