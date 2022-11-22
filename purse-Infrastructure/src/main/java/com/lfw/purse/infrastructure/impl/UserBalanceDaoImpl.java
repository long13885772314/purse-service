package com.lfw.purse.infrastructure.impl;

import com.lfw.purse.infrastructure.dao.UserBalanceDao;
import com.lfw.purse.infrastructure.dto.UserBalance;
import com.lfw.purse.infrastructure.mapper.UserBalanceMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 龙发文
 * @ClassName UserBalanceDaoImpl
 * @Description TODO
 * @date 2022/11/22 0022 0:47
 */
@Service
public class UserBalanceDaoImpl implements UserBalanceDao {

    @Resource
    private UserBalanceMapper userBalanceMapper;

    /**
     * 查询用户余额
     *
     * @param userId
     */
    @Override
    public UserBalance queryByUserId(Integer userId) {
        if (null == userId) {
            return null;
        }
        return userBalanceMapper.selectByPrimaryKey(userId);
    }

    /**
     * 增加余额（充值）
     *
     * @param price   金额
     * @return 修改的条数
     */
    @Override
    public int increaseBalance(UserBalance userBalance, BigDecimal price) {
        Date date = new Date();

        BigDecimal oldPrice = userBalance.getPrice();
        BigDecimal newPrice = oldPrice.add(price);
        userBalance.setPrice(newPrice);
        userBalance.setUpdateTime(date);

        return userBalanceMapper.updateByPrimaryKeySelective(userBalance);
    }

    /**
     * 减少余额（消费）
     *
     * @param price   金额
     * @return 修改的条数
     */
    @Override
    public int decreaseBalance(UserBalance userBalance, BigDecimal price) {
        Date date = new Date();

        BigDecimal oldPrice = userBalance.getPrice();
        BigDecimal newPrice = oldPrice.subtract(price);
        userBalance.setPrice(newPrice);
        userBalance.setUpdateTime(date);

        return userBalanceMapper.updateByPrimaryKeySelective(userBalance);
    }
}
