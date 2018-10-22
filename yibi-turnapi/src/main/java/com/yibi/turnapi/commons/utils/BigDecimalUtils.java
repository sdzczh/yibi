package com.yibi.turnapi.commons.utils;

import com.yibi.turnapi.commons.enums.CoinType;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;


/**
 * 金额计算工具类
* @author lina 
* @date 2017-12-29
* @version V1.0
 */
public class BigDecimalUtils {

	public static BigDecimal add(BigDecimal bd1,BigDecimal bd2){
		BigDecimal bd3 = bd1.add(bd2);
		return bd3;
	}
	
	/**
	 * @描述 加法<br>
	 * @return 和
	 * @author 
	 * @版本 v1.0.0
	 * @日期 2017-6-19
	 */
	public static BigDecimal add(BigDecimal bd1,BigDecimal bd2,Integer scale){
		BigDecimal bd3 = bd1.add(bd2);
		return round(bd3,scale);
	}
	
	public static BigDecimal subtract(BigDecimal bd1,BigDecimal bd2){
		BigDecimal bd3 = bd1.subtract(bd2);
		return bd3;
	}
	/**
	 * @描述 减法<br>
	 * @return 差
	 * @author 
	 * @版本 v1.0.0
	 * @日期 2017-6-19
	 */
	public static BigDecimal subtract(BigDecimal bd1,BigDecimal bd2,Integer scale){
		BigDecimal bd3 = bd1.subtract(bd2);
		return round(bd3,scale);
	}


	
	/**
	 * @描述 乘法<br>
	 * @return 积
	 * @author 
	 * @版本 v1.0.0
	 * @日期 2017-6-19
	 */
	public static BigDecimal multiply (BigDecimal bd1,BigDecimal bd2,Integer scale){
		BigDecimal bd3 = bd1.multiply(bd2);
		return  round(bd3,scale);
	}
	
	public static BigDecimal multiply (BigDecimal bd1,BigDecimal bd2){
		BigDecimal bd3 = bd1.multiply(bd2);
		return  bd3;
	}
	
	
	/**
	 * 
	 * @param bd1
	 * @param bd2
	 * @return
	 * @return BigDecimal
	 * @date 2017-12-29
	 * @author lina
	 */
	public static BigDecimal divide(BigDecimal bd1,BigDecimal bd2,Integer scale){
		BigDecimal d3 = bd1.divide(bd2,scale,BigDecimal.ROUND_HALF_UP);
		return d3;
	}
	
	/**
	 * 四舍五入
	 * @param v
	 * @param scale
	 * @return BigDecimal
	 * @date 2018-1-4
	 * @author lina
	 */
	public static BigDecimal round(BigDecimal v,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal one = new BigDecimal("1");
        return v.divide(one,scale,BigDecimal.ROUND_HALF_UP);
    }
	
	/**
	 * 截取指定小数位数
	 * @param v
	 * @param scale
	 * @return
	 * @return BigDecimal
	 * @date 2018-5-7
	 * @author lina
	 */
	public static BigDecimal roundDown(BigDecimal v,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal one = new BigDecimal("1");
        return v.divide(one,scale,BigDecimal.ROUND_DOWN);
    }
	
	/**
	 * 正负转换
	 * @param v
	 * @return BigDecimal
	 * @date 2018-1-4
	 * @author lina
	 */
	public static BigDecimal plusMinus(BigDecimal v){
        return v.multiply(new BigDecimal(-1));
    }
	
	public static String toCoinString(BigDecimal amount,Integer coinType){
		if(amount==null){
			return "0"+CoinType.getName(coinType);
		}
		return amount.stripTrailingZeros().toPlainString()+CoinType.getName(coinType);
	}
	
	public static String toString(BigDecimal amount,Integer coinType){
		if(amount==null || amount.compareTo(BigDecimal.ZERO) ==0){
			return "0";
		}
		BigDecimal bd = round(amount, CoinType.getScale(coinType));
		return bd.stripTrailingZeros().toPlainString();
	}
	
	/**
	 * 小数点精确到八位
	 * @param bigDecimal
	 * @return
	 */
	public static String toMarketPriceString(BigDecimal bigDecimal){
		if(bigDecimal == null)return "0";
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(8);
		nf.setRoundingMode(RoundingMode.DOWN);
		nf.setGroupingUsed(false);
		return nf.format(bigDecimal);
	}
	
	/**
	 * 小数点精确到两位
	 * @param bigDecimal
	 * @return
	 */
	public static String toMarketAmountString(BigDecimal bigDecimal){
		if(bigDecimal == null)return "0";
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		nf.setRoundingMode(RoundingMode.HALF_UP);
		nf.setGroupingUsed(false);
		return nf.format(bigDecimal);
	}
	
	/**
	 * 获取bigDecimal精确到具体小数位的字符串
	 * @param bigDecimal
	 * @param scale
	 * @return
	 */
	public static String getPrecisionString(BigDecimal bigDecimal, Integer scale){
		if(bigDecimal == null)return "0";
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(scale);
		nf.setRoundingMode(RoundingMode.HALF_UP);
		nf.setGroupingUsed(false);
		return nf.format(bigDecimal);
	}
	
	public static void main(String[] args) {
		BigDecimal zero = new BigDecimal(-10);
		System.out.println(getPrecisionString(zero, 6));
	}
}
