package com.lfw.purse.infrastructure.impl;

import com.lfw.purse.infrastructure.dao.BalanceDetailDao;
import com.lfw.purse.infrastructure.dto.BalanceDetailDto;
import com.lfw.purse.infrastructure.dto.BalanceDetailDtoExample;
import com.lfw.purse.infrastructure.mapper.BalanceDetailMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 龙发文
 * @ClassName BalanceDetailDaoImpl
 * @Description TODO
 * @date 2022/11/22 0022 0:03
 */
@Repository
public class BalanceDetailDaoImpl implements BalanceDetailDao {

    @Resource
    private BalanceDetailMapper balanceDetailMapper;

    /**
     * 查询指定用户的消费明细
     *
     * @param userId
     */
    @Override
    public List<BalanceDetailDto> queryByUserId(Integer userId) {
        if (null == userId) {
            return new ArrayList<>();
        }
        BalanceDetailDtoExample example = new BalanceDetailDtoExample();
        BalanceDetailDtoExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);

        List<BalanceDetailDto> balanceDetailDtos = balanceDetailMapper.selectByExample(example);
        return CollectionUtils.isEmpty(balanceDetailDtos) ? new ArrayList<>() : balanceDetailDtos;
    }

    /**
     * 根据订单Id查询消费明细
     *
     * @param id
     */
    @Override
    public BalanceDetailDto queryById(Integer id) {
        if (null == id) {
            return null;
        }
        return balanceDetailMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insertBalanceDetail(BalanceDetailDto dto) {
        if (null == dto) {
            return 0;
        }
        return balanceDetailMapper.insert(dto);
    }
}
