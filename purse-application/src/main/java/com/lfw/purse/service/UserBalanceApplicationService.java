package com.lfw.purse.service;

import java.math.BigDecimal;

/**
 * @author 龙发文
 * @ClassName UserBalanceApplicationService
 * @Description TODO
 * @date 2022/11/22 0022 2:14
 */
public interface UserBalanceApplicationService {

    BigDecimal queryBalance(Integer userId);

    Boolean increase(Integer userId, BigDecimal price, String orderId);

    Boolean decrease(Integer userId, BigDecimal price, String orderId);

}
