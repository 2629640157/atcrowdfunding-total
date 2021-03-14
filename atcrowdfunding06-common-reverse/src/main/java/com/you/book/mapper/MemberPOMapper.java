package com.you.book.mapper;

import com.you.book.entity.po.MemberPO;
import com.you.book.entity.po.MemberPOExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface MemberPOMapper {
    int countByExample(MemberPOExample example);

    int deleteByExample(MemberPOExample example);

    int deleteByPrimaryKey(Integer mId);

    int insert(MemberPO record);

    int insertSelective(MemberPO record);

    List<MemberPO> selectByExample(MemberPOExample example);

    MemberPO selectByPrimaryKey(Integer mId);

    int updateByExampleSelective(@Param("record") MemberPO record, @Param("example") MemberPOExample example);

    int updateByExample(@Param("record") MemberPO record, @Param("example") MemberPOExample example);

    int updateByPrimaryKeySelective(MemberPO record);

    int updateByPrimaryKey(MemberPO record);
}