package com.lfw.purse.infrastructure.mapper;

import java.util.List;

import com.lfw.purse.infrastructure.dto.UserBalance;
import com.lfw.purse.infrastructure.dto.UserBalanceExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
public interface UserBalanceMapper {
    long countByExample(UserBalanceExample example);

    int deleteByExample(UserBalanceExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(UserBalance record);

    int insertSelective(UserBalance record);

    List<UserBalance> selectByExample(UserBalanceExample example);

    UserBalance selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") UserBalance record, @Param("example") UserBalanceExample example);

    int updateByExample(@Param("record") UserBalance record, @Param("example") UserBalanceExample example);

    int updateByPrimaryKeySelective(UserBalance record);

    int updateByPrimaryKey(UserBalance record);
}