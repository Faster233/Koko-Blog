package com.koko.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.koko.blog.entity.BlogCategoryEntity;
import com.koko.blog.utils.PageQueryUtil;
import com.koko.blog.utils.PageUtils;
import com.koko.blog.utils.Result;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Faster
 * @email Faster@shgogo.com
 * @date 2022-01-28 15:07:26
 */
public interface BlogCategoryService extends IService<BlogCategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    int getTotalCategoies();

    PageUtils queryBlogCategoryPage(PageQueryUtil queryUtil);

    boolean saveBlogCategory(String categoryName, String categoryIcon);

    boolean updateBlogCategory(Integer categoryId, String categoryName, String categoryIcon);

    boolean deleteBlogCategory(Integer[] ids);

    List<BlogCategoryEntity> getAllCategory();
}

