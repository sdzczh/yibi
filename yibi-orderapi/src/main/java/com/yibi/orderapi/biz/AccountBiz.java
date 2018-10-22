package com.yibi.orderapi.biz;

import java.math.BigDecimal;

/**
 * Created by ZhaoHe on 2018/7/17 0017.
 */
public interface AccountBiz {


    BigDecimal queryByUser(Integer id, int accountTypeSpot);
}
