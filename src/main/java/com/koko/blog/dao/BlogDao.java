package com.koko.blog.dao;

import com.koko.blog.entity.BlogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.koko.blog.utils.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author Faster
 * @email Faster@shgogo.com
 * @date 2022-01-28 15:07:27
 */
@Mapper
public interface BlogDao extends BaseMapper<BlogEntity> {

    int getTotalBlogs();

    List<BlogEntity> queryBlogPage(PageQueryUtil queryUtil);

    int updateBlog(BlogEntity blogForUpdate);

    int batchDelete(@Param("ids") Integer[] ids);

    int getTotalBlogsByQueryUtil(PageQueryUtil queryUtil);

    List<BlogEntity> findBlogListByType(@Param("type") int type,@Param("limit") int limit);

    List<BlogEntity> getBlogsPageByTagId(PageQueryUtil queryUtil);

    int getTotalBlogsByTagId(@Param("tagId") Integer tagId);

    BlogEntity selectBySubUrl(@Param("subUrl") String subUrl);
}
