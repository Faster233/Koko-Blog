package com.koko.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.koko.blog.controller.vo.BlogDetailVO;
import com.koko.blog.controller.vo.SimpleBlogVO;
import com.koko.blog.entity.BlogEntity;
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
 * @date 2022-01-28 15:07:27
 */
public interface BlogService extends IService<BlogEntity> {

    PageUtils queryPage(Map<String, Object> params);

    int getTotalBlogs();

    String saveBlog(BlogEntity blog);

    PageUtils queryBlogPage(PageQueryUtil queryUtil);

    BlogEntity getBlogById(Long blogId);

    String updateBlog(BlogEntity blog);

    boolean batchDelete(Integer[] ids);
    // 获取首页文章列表
    PageUtils getBlogsForIndexPage(int pageNum);

    /**
     * 首页侧边栏数据列表
     * @param type=0点击最多 1最新发布
     * @return
     */
    List<SimpleBlogVO> getBlogListForIndexPage(int type);

    /**
     * 文章详图
     * @param blogId
     * @return
     */
    BlogDetailVO getBlogDetailById(Long blogId);

    /**
     * 通过标签获取 博客页面信息
     * @param tagName
     * @param page
     * @return
     */
    PageUtils getBlogPageByTag(String tagName, int page);

    /**
     * 通过分类获取 博客页面信息
     * @param categoryName
     * @param page
     * @return
     */
    PageUtils getBlogPageByCategory(String categoryName, int page);

    /**
     *  根据搜索获取文章列表
     * @param keyword
     * @param page
     * @return
     */
    PageUtils getBlogPageBySearch(String keyword, int page);

    BlogDetailVO getBlogDetailBySubUrl(String subUrl);
}

