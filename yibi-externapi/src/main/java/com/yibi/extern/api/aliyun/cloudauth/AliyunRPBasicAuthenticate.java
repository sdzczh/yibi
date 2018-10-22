package com.yibi.extern.api.aliyun.cloudauth;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.cloudauth.model.v20171117.GetMaterialsRequest;
import com.aliyuncs.cloudauth.model.v20171117.GetMaterialsResponse;
import com.aliyuncs.cloudauth.model.v20171117.GetMaterialsResponse.Data;
import com.aliyuncs.cloudauth.model.v20171117.GetStatusRequest;
import com.aliyuncs.cloudauth.model.v20171117.GetStatusResponse;
import com.aliyuncs.cloudauth.model.v20171117.GetVerifyTokenRequest;
import com.aliyuncs.cloudauth.model.v20171117.GetVerifyTokenResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import lombok.Setter;

public class AliyunRPBasicAuthenticate {

	public static final String  biz = "RPBasic";//采用RPBasic认证方案的认证场景标识(biz)

	@Setter
	private String accessKeyId;
	@Setter
	private String accessKeySecret;
	
	/**
	 *  服务端发起认证请求, 获取到token
	 * @return
	 * @return String
	 * @date 2018-1-18
	 * @author lina
	 */
	public  String getVerifyToken (String ticketId){
		IAcsClient client = initClient(accessKeyId,accessKeySecret);
		String token = null; // 认证token, 用来串联认证请求中的各个接口
		
		// 1. 服务端发起认证请求, 获取到token
		// GetVerifyToken接口文档：https://help.aliyun.com/document_detail/57050.html
		GetVerifyTokenRequest getVerifyTokenRequest = new GetVerifyTokenRequest();
		getVerifyTokenRequest.setBiz(biz);
		getVerifyTokenRequest.setTicketId(ticketId);
		try {
		    GetVerifyTokenResponse response = client.getAcsResponse(getVerifyTokenRequest);
		    token = response.getData().getVerifyToken().getToken();
		} catch (ServerException e) {
		    e.printStackTrace();
		} catch (ClientException e) {
		    e.printStackTrace();
		}
		return token;
	}

	/**
	 * 服务端查询认证状态(客户端回调中也会携带认证状态, 但建议以服务端调接口确认的为准)
	 * @return int -1 未认证, 0 认证中, 1 认证通过, 2 认证不通过
	 * @date 2018-1-18
	 * @author lina
	 */
	public  int getStatus(String ticketId){
		int statusCode = -1; // -1 未认证, 0 认证中, 1 认证通过, 2 认证不通过
		IAcsClient client = initClient(accessKeyId,accessKeySecret);
		
		GetStatusRequest getStatusRequest = new GetStatusRequest();
		getStatusRequest.setBiz(biz);
		getStatusRequest.setTicketId(ticketId);
		try {
		    GetStatusResponse response = client.getAcsResponse(getStatusRequest);
		    
		    statusCode = response.getData().getStatusCode();
		} catch (ServerException e) {
		    e.printStackTrace();
		} catch (ClientException e) {
		    e.printStackTrace();
		}
		
		return statusCode;
	}
	/**
	 * 服务端获取认证资料
	 * @return
	 * @return MaterialModel
	 * @date 2018-1-18
	 * @author lina
	 */
	public  MaterialModel getMaterials(String ticketId,int statusCode){
		IAcsClient client = initClient(accessKeyId,accessKeySecret);
		
		MaterialModel mate = new MaterialModel();
		GetMaterialsRequest getMaterialsRequest = new GetMaterialsRequest();
		getMaterialsRequest.setBiz(biz);
		getMaterialsRequest.setTicketId(ticketId);
		if( 1 == statusCode || 2 == statusCode ) { // 认证通过 or 认证不通过
		    try {
		        GetMaterialsResponse response = client.getAcsResponse(getMaterialsRequest);
		        mate.setCode(response.getCode());
		        Data data = response.getData();
		        mate.setName( data.getName());
		        mate.setIdentificationNumber( data.getIdentificationNumber());
		        mate.setIdCardType(data.getIdCardType());
		        mate.setIdCardExpiry( data.getIdCardExpiry());
		        mate.setAddress(  data.getAddress());
		        mate.setSex( data.getSex());
		        mate.setIdCardFrontPic( data.getIdCardFrontPic());
		        mate.setIdCardBackPic(data.getIdCardBackPic());
		        mate.setFacePic( data.getFacePic());
		        
		    } catch (ServerException e) {
		        e.printStackTrace();
		    } catch (ClientException e) {
		        e.printStackTrace();
		    }
		}
		
		return mate;
	}
	
	/**
	 * 创建DefaultAcsClient实例并初始化
	 * @param AccessKeyId
	 * @param AccessKeySecret
	 * @return IAcsClient
	 * @date 2018-1-18
	 * @author lina
	 */
	public static IAcsClient initClient(String AccessKeyId,String AccessKeySecret){
		DefaultProfile profile = DefaultProfile.getProfile(
		        "cn-hangzhou",             // 默认
		        AccessKeyId,         // 您的Access Key ID
		        AccessKeySecret);    // 您的Access Key Secret
		return new DefaultAcsClient(profile);
	}
}
