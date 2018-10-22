package com.yibi.extern.api.aliyun.cloudauth;

public class MaterialModel {
	//姓名
	public String name; 
	
	 //证件号
	public String identificationNumber;
	
	//证件类型。identityCard代表身份证。
	public String idCardType;
	
	//证件有效期。格式：yyyyMMdd。证件有效期依赖于算法识别，非必返回字段，可能存在无法识别的情况。
	public String idCardExpiry;
	
	//证件地址。通过 OCR 算法识别出的地址（JSON 数据格式），请参考 Address 参数示例。证件地址依赖于算法识别，非必返回字段，可能存在无法识别或识别不全的情况。
	public String address;
	
	//证件性别。返回值的集合为{m, f}，m 代表男性，f 代表女性。
	public String sex;
	
	//证件照正面图片 URL（有时效性的 HTTP 图片地址）。若证件类型为身份证，则为身份证人像面照片。
	public String idCardFrontPic;
	
	//证件照背面图片 URL（有时效性的 HTTP 图片地址）。若证件类型为身份证，则为身份证国徽面照片。
	public String idCardBackPic;
	
	//认证过程中拍摄的人像正面照图片 URL（有时效性的 HTTP 图片地址）。
	public String facePic;
	
	//请求返回代码
	public String code;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentificationNumber() {
		return identificationNumber;
	}

	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

	public String getIdCardType() {
		return idCardType;
	}

	public void setIdCardType(String idCardType) {
		this.idCardType = idCardType;
	}

	public String getIdCardExpiry() {
		return idCardExpiry;
	}

	public void setIdCardExpiry(String idCardExpiry) {
		this.idCardExpiry = idCardExpiry;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIdCardFrontPic() {
		return idCardFrontPic;
	}

	public void setIdCardFrontPic(String idCardFrontPic) {
		this.idCardFrontPic = idCardFrontPic;
	}

	public String getIdCardBackPic() {
		return idCardBackPic;
	}

	public void setIdCardBackPic(String idCardBackPic) {
		this.idCardBackPic = idCardBackPic;
	}

	public String getFacePic() {
		return facePic;
	}

	public void setFacePic(String facePic) {
		this.facePic = facePic;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "MaterialModel [name=" + name + ", identificationNumber="
				+ identificationNumber + ", idCardType=" + idCardType
				+ ", idCardExpiry=" + idCardExpiry + ", address=" + address
				+ ", sex=" + sex + ", idCardFrontPic=" + idCardFrontPic
				+ ", idCardBackPic=" + idCardBackPic + ", facePic=" + facePic
				+ ", code=" + code + "]";
	}
	
	
	
}
