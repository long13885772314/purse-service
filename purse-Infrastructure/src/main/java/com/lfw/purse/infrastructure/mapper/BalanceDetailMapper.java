package com.lfw.purse.infrastructure.mapper;

import java.util.List;

import com.lfw.purse.infrastructure.dto.BalanceDetailDto;
import com.lfw.purse.infrastructure.dto.BalanceDetailDtoExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
public interface BalanceDetailMapper {
    long countByExample(BalanceDetailDtoExample example);

    int deleteByExample(BalanceDetailDtoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BalanceDetailDto record);

    int insertSelective(BalanceDetailDto record);

    List<BalanceDetailDto> selectByExample(BalanceDetailDtoExample example);

    BalanceDetailDto selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BalanceDetailDto record, @Param("example") BalanceDetailDtoExample example);

    int updateByExample(@Param("record") BalanceDetailDto record, @Param("example") BalanceDetailDtoExample example);

    int updateByPrimaryKeySelective(BalanceDetailDto record);

    int updateByPrimaryKey(BalanceDetailDto record);
}