package com.lfw.purse.application;

import com.lfw.purse.enums.BalanceDetailType;
import com.lfw.purse.infrastructure.dao.BalanceDetailDao;
import com.lfw.purse.infrastructure.dao.UserBalanceDao;
import com.lfw.purse.infrastructure.dto.BalanceDetailDto;
import com.lfw.purse.infrastructure.dto.UserBalance;
import com.lfw.purse.service.UserBalanceApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * @author 龙发文
 * @ClassName UserBalanceApplicationService
 * @Description TODO
 * @date 2022/11/22 0022 1:08
 */
@Service
public class UserBalanceApplicationServiceImpl implements UserBalanceApplicationService {

    @Resource
    private BalanceDetailDao balanceDetailDao;

    @Resource
    private UserBalanceDao userBalanceDao;

    /**
     * 随机生成Id
     * @return
     */
    public static String UUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 查询余额
     * @param userId 用户Id
     * @return
     */
    public BigDecimal queryBalance(Integer userId) {
        UserBalance userBalance = userBalanceDao.queryByUserId(userId);
        return Optional.ofNullable(userBalance)
                .map(UserBalance::getPrice)
                //一般来说为空的可能性不存在，但是这里也做下返回，或者直接抛异常也可以
                .orElse(new BigDecimal("0.0000"));
    }

    /**
     * 充值
     * @param userId
     * @param price
     * @param orderId
     * @return
     */
    public Boolean increase(Integer userId, BigDecimal price, String orderId) {
        //1.增加余额
        UserBalance userBalance = userBalanceDao.queryByUserId(userId);
        userBalanceDao.increaseBalance(userBalance, price);

        //2.创建余额明细
        //todo: 调用订单微服务，创建出充值的订单数据，获取返回的订单Id，我这里就先随机生成订单Id，
        Date date = new Date();
        BalanceDetailDto balanceDetailDto = new BalanceDetailDto();
        balanceDetailDto.setAmount(price);
        balanceDetailDto.setCreateTime(date);
        balanceDetailDto.setUpdateTime(date);
        balanceDetailDto.setUserId(userId);
        balanceDetailDto.setCreator(userId);
        balanceDetailDto.setModifier(userId);
        balanceDetailDto.setOrderId(orderId);
        balanceDetailDto.setDelFlag(false);

        if (StringUtils.isEmpty(orderId)) {
            balanceDetailDto.setOrderId(UUID());
        }
        balanceDetailDto.setType(BalanceDetailType.INCREASE.name());
        balanceDetailDao.insertBalanceDetail(balanceDetailDto);

        //todo: 3.调用第三方接口，比如支付宝、微信，从支付宝微信的余额扣除相应的钱，
        // 如果有回调接口，我们这里需要在开发个回调接口，保证两边数据的一致性

        return Boolean.TRUE;

    }

    /**
     * 消费-比如买东西，买东西的场景，肯定在订单服务那边先生成订单，生成之后把订单号传过来
     * @param userId
     * @param price
     * @param orderId
     * @return
     */
    public Boolean decrease(Integer userId, BigDecimal price, String orderId) {
        //1、校验，包含两种情况
            //1.1、钱包的钱够，直接减
            //1.2、钱包的钱不够，应直接抛异常
        UserBalance userBalance = userBalanceDao.queryByUserId(userId);
        BigDecimal balancePrice = userBalance.getPrice();
        //如果商品价格大于余额
        if (price.compareTo(balancePrice) == 1) {
            throw new RuntimeException("钱包余额不足");
        }

        //2.减少余额
        userBalanceDao.decreaseBalance(userBalance, price);

        //3.创建余额明细
        //todo: 调用订单微服务，创建出充值的订单数据，获取返回的订单Id，我这里就先随机生成订单Id，
        Date date = new Date();
        BalanceDetailDto balanceDetailDto = new BalanceDetailDto();
        balanceDetailDto.setAmount(price);
        balanceDetailDto.setCreateTime(date);
        balanceDetailDto.setUpdateTime(date);
        balanceDetailDto.setUserId(userId);
        balanceDetailDto.setCreator(userId);
        balanceDetailDto.setModifier(userId);
        balanceDetailDto.setOrderId(orderId);
        balanceDetailDto.setDelFlag(false);

        if (StringUtils.isEmpty(orderId)) {
            balanceDetailDto.setOrderId(UUID());
        }
        balanceDetailDto.setType(BalanceDetailType.DECREASE.name());
        balanceDetailDao.insertBalanceDetail(balanceDetailDto);

        return Boolean.TRUE;

    }

}
