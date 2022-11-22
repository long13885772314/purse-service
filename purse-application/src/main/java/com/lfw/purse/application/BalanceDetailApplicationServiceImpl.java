package com.lfw.purse.application;

import com.lfw.purse.infrastructure.dao.BalanceDetailDao;
import com.lfw.purse.infrastructure.dto.BalanceDetailDto;
import com.lfw.purse.service.BalanceDetailApplicationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 龙发文
 * @ClassName BalanceDetailApplicationService
 * @Description TODO
 * @date 2022/11/22 0022 0:18
 */
@Service
public class BalanceDetailApplicationServiceImpl implements BalanceDetailApplicationService {

    @Resource
    private BalanceDetailDao balanceDetailDao;

    public List<BalanceDetailDto> queryByUserId(Integer userId) {
        return balanceDetailDao.queryByUserId(userId);
    }

    public BalanceDetailDto queryById(Integer id) {
        return balanceDetailDao.queryById(id);
    }

}
