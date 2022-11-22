package com.lfw.purse.infrastructure.dao;

import com.lfw.purse.infrastructure.dto.BalanceDetailDto;

import java.util.List;

/**
 * @author 龙发文
 * @ClassName BalanceDetailDao
 * @Description TODO
 * @date 2022/11/21 0021 23:59
 */
public interface BalanceDetailDao {

    /**查询指定用户的消费明细*/
    List<BalanceDetailDto> queryByUserId(Integer userId);

    /**根据订单Id查询消费明细*/
    BalanceDetailDto queryById(Integer id);

    int insertBalanceDetail(BalanceDetailDto dto);

}
