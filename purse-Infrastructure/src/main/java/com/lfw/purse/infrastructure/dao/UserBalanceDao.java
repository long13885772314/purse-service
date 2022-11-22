package com.lfw.purse.infrastructure.dao;

import com.lfw.purse.infrastructure.dto.UserBalance;

import java.math.BigDecimal;

/**
 * @author 龙发文
 * @ClassName UserBalanceDao
 * @Description TODO
 * @date 2022/11/22 0022 0:30
 */
public interface UserBalanceDao {

    /**查询用户余额*/
    UserBalance queryByUserId(Integer userId);

    /**
     * 增加余额（充值）
     * @param userBalance userBalance
     * @param price 金额
     * @return 修改的条数
     */
    int increaseBalance(UserBalance userBalance, BigDecimal price);

    /**
     * 减少余额（消费）
     * @param userBalance userBalance
     * @param price 金额
     * @return 修改的条数
     */
    int decreaseBalance(UserBalance userBalance, BigDecimal price);

}
