package com.you.book.mapper;

import com.you.book.entity.po.StorePO;
import com.you.book.entity.po.StorePOExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface StorePOMapper {
    int countByExample(StorePOExample example);

    int deleteByExample(StorePOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StorePO record);

    int insertSelective(StorePO record);

    List<StorePO> selectByExample(StorePOExample example);

    StorePO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") StorePO record, @Param("example") StorePOExample example);

    int updateByExample(@Param("record") StorePO record, @Param("example") StorePOExample example);

    int updateByPrimaryKeySelective(StorePO record);

    int updateByPrimaryKey(StorePO record);
}