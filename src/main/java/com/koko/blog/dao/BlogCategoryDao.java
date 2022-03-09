package com.koko.blog.dao;

import com.koko.blog.entity.BlogCategoryEntity;
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
 * @date 2022-01-28 15:07:26
 */
@Mapper
public interface BlogCategoryDao extends BaseMapper<BlogCategoryEntity> {

    int getTotalCategoies();

    List<BlogCategoryEntity> getBlogCategoryLimit(PageQueryUtil queryUtil);

    BlogCategoryEntity findBlogCategoryByName(@Param("categoryName") String categoryName);



    int updateBlogCategoryForBlog(@Param("categoryId")Integer categoryId,@Param("categoryName") String categoryName,@Param("ids") Integer[] ids);

    int updateBlogCategory(BlogCategoryEntity blogCategory);

    int deleteBlogCategory(@Param("ids") Integer[] ids);

    List<BlogCategoryEntity> findBlogCategoryByIds(@Param("categoryIds") List<Integer> categoryIds);
    // BlogCategoryDao.xml
}
