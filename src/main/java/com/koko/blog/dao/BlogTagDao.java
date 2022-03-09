package com.koko.blog.dao;

import com.koko.blog.entity.BlogTagCount;
import com.koko.blog.entity.BlogTagEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.koko.blog.utils.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @author Faster
 * @email Faster@shgogo.com
 * @date 2022-01-28 15:07:26
 */
@Mapper
public interface BlogTagDao extends BaseMapper<BlogTagEntity> {

    int getTotalTags();

    BlogTagEntity selectTagByName(@Param("tagName") String tagName);

    int batchDelete(Integer[] ids);

    List<BlogTagEntity> selectTagList(PageQueryUtil queryUtil);

    int batchInsert(@Param("BlogTags") List<BlogTagEntity> insertBlogTags);

    List<BlogTagEntity> selectTagByNamearray(@Param("BlogTagNames")String[] tags);

    List<BlogTagCount> gethotTotalTags();
}
