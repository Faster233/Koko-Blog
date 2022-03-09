package com.koko.blog.service.impl;

import com.koko.blog.utils.PageQueryUtil;
import com.koko.blog.utils.PageUtils;
import com.koko.blog.utils.Query;
import com.koko.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.koko.blog.dao.BlogCategoryDao;
import com.koko.blog.entity.BlogCategoryEntity;
import com.koko.blog.service.BlogCategoryService;


@Service("blogCategoryService")
public class BlogCategoryServiceImpl extends ServiceImpl<BlogCategoryDao, BlogCategoryEntity> implements BlogCategoryService {

    @Autowired
    private BlogCategoryDao categoryDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BlogCategoryEntity> page = this.page(
                new Query<BlogCategoryEntity>().getPage(params),
                new QueryWrapper<BlogCategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public int getTotalCategoies() {
        return categoryDao.getTotalCategoies();
    }

    @Override
    public PageUtils queryBlogCategoryPage(PageQueryUtil queryUtil) {
        List<BlogCategoryEntity> blogCategorys=categoryDao.getBlogCategoryLimit(queryUtil);
        int totalCategoies = categoryDao.getTotalCategoies();
        PageUtils pageUtils = new PageUtils(blogCategorys,totalCategoies,queryUtil.getLimit(),queryUtil.getPage());
       return  pageUtils;

    }

    @Override
    public boolean saveBlogCategory(String categoryName, String categoryIcon) {
        BlogCategoryEntity blogCategory=categoryDao.findBlogCategoryByName(categoryName);
        if (blogCategory==null){
            blogCategory=new BlogCategoryEntity();
            blogCategory.setCategoryName(categoryName);
            blogCategory.setCategoryIcon(categoryIcon);
            int insert = categoryDao.insert(blogCategory);
            return insert>0;
        }
        return false;
    }

    @Override
    public boolean updateBlogCategory(Integer categoryId, String categoryName, String categoryIcon) {
        BlogCategoryEntity blogCategory=categoryDao.selectById(categoryId);
        if (blogCategory!=null) {
            blogCategory.setCategoryName(categoryName);
            blogCategory.setCategoryIcon(categoryIcon);
            categoryDao.updateBlogCategoryForBlog(categoryId,categoryName,new Integer[] {categoryId});
            return categoryDao.updateBlogCategory(blogCategory)>0;
        }
        return false;
    }

    @Override
    public boolean deleteBlogCategory(Integer[] ids) {
        categoryDao.updateBlogCategoryForBlog(0,"默认分类",ids);
        return categoryDao.deleteBlogCategory(ids)>0;
    }

    @Override
    public List<BlogCategoryEntity> getAllCategory() {
        return categoryDao.getBlogCategoryLimit(null);
    }

}