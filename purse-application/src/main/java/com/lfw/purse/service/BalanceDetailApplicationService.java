package com.lfw.purse.service;

import com.lfw.purse.infrastructure.dto.BalanceDetailDto;

import java.util.List;

/**
 * @author 龙发文
 * @ClassName BalanceDetailApplicationService
 * @Description TODO
 * @date 2022/11/22 0022 2:12
 */
public interface BalanceDetailApplicationService {

    List<BalanceDetailDto> queryByUserId(Integer userId);

    BalanceDetailDto queryById(Integer id);

}
