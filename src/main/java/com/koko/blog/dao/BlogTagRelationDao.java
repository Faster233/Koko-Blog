package com.koko.blog.dao;

import com.koko.blog.entity.BlogTagRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author Faster
 * @email Faster@shgogo.com
 * @date 2022-01-28 15:07:26
 */
@Mapper
public interface BlogTagRelationDao extends BaseMapper<BlogTagRelationEntity> {

    List<Long> selectDistinctTagIds(Integer[] ids);

    int batchInsert(@Param("relationList") List<BlogTagRelationEntity> blogTagRelationlist);

    int deleteByBlogId(@Param("blogId") Long blogId);
}
