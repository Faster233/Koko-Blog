package com.koko.blog.service.impl;

import com.koko.blog.controller.vo.BlogDetailVO;
import com.koko.blog.controller.vo.BlogVO;
import com.koko.blog.controller.vo.SimpleBlogVO;
import com.koko.blog.dao.*;
import com.koko.blog.entity.BlogCategoryEntity;
import com.koko.blog.entity.BlogTagEntity;
import com.koko.blog.entity.BlogTagRelationEntity;
import com.koko.blog.utils.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.koko.blog.entity.BlogEntity;
import com.koko.blog.service.BlogService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Transactional
@Service("blogService")
public class BlogServiceImpl extends ServiceImpl<BlogDao, BlogEntity> implements BlogService {

    @Autowired
    private BlogDao blogDao;
    @Autowired
    private BlogCategoryDao categoryDao;
    @Autowired
    private BlogTagDao blogTagDao;
    @Autowired
    private BlogTagRelationDao blogTagRelationDao;
    @Autowired
    private BlogCommentDao blogCommentDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BlogEntity> page = this.page(
                new Query<BlogEntity>().getPage(params),
                new QueryWrapper<BlogEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public int getTotalBlogs() {
        return blogDao.getTotalBlogs();
    }

    @Override
    public String saveBlog(BlogEntity blog) {
        // 查询分类信息&&设置分类信息
        BlogCategoryEntity blogCategory = categoryDao.selectById(blog.getBlogCategoryId());
        if (blogCategory==null){
            blog.setBlogCategoryId(0);
            blog.setBlogCategoryName("默认分类");
        }else{
            blog.setBlogCategoryName(blogCategory.getCategoryName());
            blogCategory.setCategoryRank(blogCategory.getCategoryRank()+1);
        }
        //处理标签数据
        String[] tags = blog.getBlogTags().split(",");
        if (tags.length>6){
            return "标签数量不能超过6个";
        }
        //保存文章
        if (blogDao.insert(blog)>0){
            //新增的tag对象
            List<BlogTagEntity> insertBlogTags = new ArrayList();
            for (int i=0;i<tags.length;i++){
                BlogTagEntity tag = blogTagDao.selectTagByName(tags[i]);
                if (tag==null){
                    BlogTagEntity blogTag = new BlogTagEntity();
                    blogTag.setTagName(tags[i]);
                    insertBlogTags.add(blogTag);
                }
            }
            if (!CollectionUtils.isEmpty(insertBlogTags)){
                blogTagDao.batchInsert(insertBlogTags);
            }
            if (blogCategory!=null){
                categoryDao.updateBlogCategory(blogCategory);
            }
            //所有的tag对象，用于建立关系数据
            List<BlogTagEntity> AllBlogTags = blogTagDao.selectTagByNamearray(tags);
            // 处理关系中间表数据
            List<BlogTagRelationEntity> blogTagRelationlist = new ArrayList<>();
            for (BlogTagEntity tag : AllBlogTags) {
                BlogTagRelationEntity blogTagRelation = new BlogTagRelationEntity();
                blogTagRelation.setBlogId(blog.getBlogId());
                blogTagRelation.setTagId(tag.getTagId());
                blogTagRelationlist.add(blogTagRelation);
            }
            if (blogTagRelationDao.batchInsert(blogTagRelationlist)>0){
                return "success";
            }

        }
            return "保存失败";
    }

    @Override
    public PageUtils queryBlogPage(PageQueryUtil queryUtil) {
        List<BlogEntity> blogs=blogDao.queryBlogPage(queryUtil);
        int totalBlogs = blogDao.getTotalBlogs();
        PageUtils pageUtils = new PageUtils(blogs, totalBlogs, queryUtil.getLimit(), queryUtil.getPage());
        return pageUtils;
    }

    @Override
    public BlogEntity getBlogById(Long blogId) {
        return blogDao.selectById(blogId);
    }

    @Override
    public String updateBlog(BlogEntity blog) {
        BlogEntity blogForUpdate = blogDao.selectById(blog.getBlogId());
        if (blogForUpdate==null){
            return "数据不存在";
        }

        blogForUpdate.setBlogTitle(blog.getBlogTitle());
        blogForUpdate.setBlogContent(blog.getBlogContent());
        blogForUpdate.setBlogCoverImage(blog.getBlogCoverImage());
        blogForUpdate.setBlogSubUrl(blog.getBlogSubUrl());
        blogForUpdate.setEnableComment(blog.getEnableComment());
        blogForUpdate.setBlogStatus(blog.getBlogStatus());
        blogForUpdate.setUpdateTime(new Date());
        BlogCategoryEntity blogCategory = categoryDao.selectById(blog.getBlogCategoryId());
        if (blogCategory==null){
            blogForUpdate.setBlogCategoryId(0);
            blogForUpdate.setBlogCategoryName("默认分类");
        }else{
            blogForUpdate.setBlogCategoryName(blogCategory.getCategoryName());
            blogForUpdate.setBlogCategoryId(blogCategory.getCategoryId());
        }

        String[] tags = blog.getBlogTags().split(",");
        if (tags.length>6){
            return "标签数量不能超过6个";
        }
        blogForUpdate.setBlogTags(blog.getBlogTags());

        List<BlogTagEntity> tagForInsertList = new ArrayList();
        for (int i=0;i< tags.length;i++){
            BlogTagEntity tag = blogTagDao.selectTagByName(tags[i]);
            if (tag==null){
                BlogTagEntity insertTag = new BlogTagEntity();
                insertTag.setTagName(tags[i]);
                tagForInsertList.add(insertTag);
            }
        }
        if (!CollectionUtils.isEmpty(tagForInsertList)){
            blogTagDao.batchInsert(tagForInsertList);
        }
        List<BlogTagEntity> AllBlogTag = blogTagDao.selectTagByNamearray(tags);
        ArrayList<BlogTagRelationEntity> relationList = new ArrayList();
        for (BlogTagEntity tag : AllBlogTag) {
            BlogTagRelationEntity relationForUpdate = new BlogTagRelationEntity();
            relationForUpdate.setBlogId(blogForUpdate.getBlogId());
            relationForUpdate.setTagId(tag.getTagId());
            relationList.add(relationForUpdate);
        }
        blogTagRelationDao.deleteByBlogId(blogForUpdate.getBlogId());
        blogTagRelationDao.batchInsert(relationList);
        if (blogDao.updateBlog(blogForUpdate)>0){
            return "success";
        }

        return "修改失败";
    }

    @Override
    public boolean batchDelete(Integer[] ids) {

        return blogDao.batchDelete(ids)>0;
    }

    /**
     * 根据 页码 获取 页面数据信息
     * @param pageNum
     * @return
     */
    @Override
    public PageUtils getBlogsForIndexPage(int pageNum) {
        Map<String,Object> param = new HashMap();
        param.put("limit",8);
        param.put("page",pageNum);
        param.put("blogStatus",1);
        PageQueryUtil queryUtil = new PageQueryUtil(param);
        List<BlogEntity> blogList = blogDao.queryBlogPage(queryUtil);
        List<BlogVO> blogVoList = getBlogVOListByBlogs(blogList);
        int totalBlogs=blogDao.getTotalBlogsByQueryUtil(queryUtil);
        PageUtils pageUtils = new PageUtils(blogVoList, totalBlogs, queryUtil.getLimit(), queryUtil.getPage());
        return pageUtils;
    }

    @Override
    public List<SimpleBlogVO> getBlogListForIndexPage(int type) {
        List<SimpleBlogVO> simpleBlogVOList = new ArrayList<>();
        List<BlogEntity> blogs=blogDao.findBlogListByType(type,9);
        if (!CollectionUtils.isEmpty(blogs)){
            for (BlogEntity blog : blogs) {
                SimpleBlogVO simpleBlogVO = new SimpleBlogVO();
                BeanUtils.copyProperties(blog,simpleBlogVO);
                simpleBlogVOList.add(simpleBlogVO);
            }
        }
        return simpleBlogVOList;
    }

    /**
     * 通过blogid获取 博客详情值对象
     * @param blogId
     * @return
     */
    @Override
    public BlogDetailVO getBlogDetailById(Long blogId) {
        BlogEntity blog = blogDao.selectById(blogId);
        BlogDetailVO blogDetailVO=getBlogDetail(blog);
        if (blogDetailVO!=null){
            return blogDetailVO;
        }
        return null;
    }


    @Override
    public PageUtils getBlogPageByTag(String tagName, int page) {
        if (PatternUtil.validKeyword(tagName)&&page>0) {
            BlogTagEntity tag = blogTagDao.selectTagByName(tagName);
            if (tag != null) {
                Map param = new HashMap<>();
                param.put("limit", 9);
                param.put("page", page);
                param.put("tagId", tag.getTagId());
                PageQueryUtil queryUtil = new PageQueryUtil(param);
                List<BlogEntity> blogs = blogDao.getBlogsPageByTagId(queryUtil);
                List<BlogVO> blogVOList = getBlogVOListByBlogs(blogs);
                int totalBlogs = blogDao.getTotalBlogsByTagId(tag.getTagId());
                PageUtils pageUtils = new PageUtils(blogVOList, totalBlogs, queryUtil.getLimit(), queryUtil.getPage());
                return pageUtils;
            }
        }
        return null;
    }

    @Override
    public PageUtils getBlogPageByCategory(String categoryName, int page) {
        if (PatternUtil.validKeyword(categoryName)) {
            BlogCategoryEntity blogCategory = categoryDao.findBlogCategoryByName(categoryName);
            if (blogCategory == null) {
                blogCategory = new BlogCategoryEntity();
                blogCategory.setCategoryId(0);
                blogCategory.setCategoryName("默认分类");
            }
            if (blogCategory != null && page > 0) {
                Map param = new HashMap<>();
                param.put("limit", 9);
                param.put("page", page);
                param.put("categoryId", blogCategory.getCategoryId());
                param.put("blogStatus", 1);
                PageQueryUtil queryUtil = new PageQueryUtil(param);
                List<BlogEntity> blogEntities = blogDao.queryBlogPage(queryUtil);
                List<BlogVO> blogVOList = getBlogVOListByBlogs(blogEntities);
                int totalBlogs = blogDao.getTotalBlogsByQueryUtil(queryUtil);
                PageUtils pageUtils = new PageUtils(blogVOList, totalBlogs, queryUtil.getLimit(), queryUtil.getPage());
                return pageUtils;
            }
        }
        return null;
    }

    @Override
    public PageUtils getBlogPageBySearch(String keyword, int page) {
        if (PatternUtil.validKeyword(keyword)&&page>0){
            Map param = new HashMap<>();
            param.put("limit",9);
            param.put("page",page);
            param.put("keyword",keyword);
            param.put("blogStatus",1);
            PageQueryUtil queryUtil = new PageQueryUtil(param);
            List<BlogEntity> blogs = blogDao.queryBlogPage(queryUtil);
            List<BlogVO> blogVOList = getBlogVOListByBlogs(blogs);
            int totalBlogs = blogDao.getTotalBlogsByQueryUtil(queryUtil);
            PageUtils pageUtils = new PageUtils(blogVOList, totalBlogs, queryUtil.getLimit(), queryUtil.getPage());
            return pageUtils;
        }
        return null;
    }

    @Override
    public BlogDetailVO getBlogDetailBySubUrl(String subUrl) {
       BlogEntity blog= blogDao.selectBySubUrl(subUrl);
        BlogDetailVO blogDetailVO = getBlogDetail(blog);
        return blogDetailVO;
    }

    /**
     * 封装博客详情值对象
     * @param blog
     * @return
     */
    private BlogDetailVO getBlogDetail(BlogEntity blog) {

        if (blog!=null && blog.getBlogStatus()==1){
            //增加浏览量
            blog.setBlogViews(blog.getBlogViews()+1);
            blogDao.updateBlog(blog);
            //新建博客详情对象
            BlogDetailVO blogDetailVO = new BlogDetailVO();
            BeanUtils.copyProperties(blog,blogDetailVO);
            //转换md格式为html
            blogDetailVO.setBlogContent(MarkDownUtil.mdToHtml(blogDetailVO.getBlogContent()));
            //设置分类信息
            BlogCategoryEntity blogCategory = categoryDao.selectById(blog.getBlogCategoryId());
            if (blogCategory==null){
                blogCategory=new BlogCategoryEntity();
                blogCategory.setCategoryId(0);
                blogCategory.setCategoryName("默认分类");
                blogCategory.setCategoryIcon("/admin/dist/img/category/00.png");
            }
            blogDetailVO.setBlogCategoryIcon(blogCategory.getCategoryIcon());
            //设置标签信息
            if (!StringUtils.isEmpty(blog.getBlogTags())){
                List<String> taglist = Arrays.asList(blog.getBlogTags().split(","));
                blogDetailVO.setBlogTags(taglist);
            }
            //设置评论数
            Map<String, Object> params = new HashMap<>();
            params.put("blogId",blog.getBlogId());
            params.put("commentStatus",1);
            int totalComments=blogCommentDao.getTotalBlogComments(params);
            blogDetailVO.setCommentCount(totalComments);
            return blogDetailVO;

        }
        return null;
    }

    /**
     * 封装blog值对象集合    里面包含了博客类数据信息 与 博客分类数据信息
     * @param blogList
     * @return List<BlogVO>
     */
    private List<BlogVO> getBlogVOListByBlogs(List<BlogEntity> blogList) {
        List<BlogVO> blogVOList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(blogList)){
             List<Integer> categoryIds=blogList.stream().map(BlogEntity::getBlogCategoryId).collect(Collectors.toList());
            Map<Integer, String> blogCategoryMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(blogList)){
                 List<BlogCategoryEntity> blogCategories=categoryDao.findBlogCategoryByIds(categoryIds);
                if (!CollectionUtils.isEmpty(blogCategories)){
                    blogCategoryMap=blogCategories.stream().collect(Collectors.toMap(BlogCategoryEntity::getCategoryId, BlogCategoryEntity::getCategoryIcon,(key1,key2)->key2));
                }
            }
            for (BlogEntity blog : blogList) {
                BlogVO blogVO = new BlogVO();
                BeanUtils.copyProperties(blog,blogVO);
                if (blogCategoryMap.containsKey(blog.getBlogCategoryId())){
                    blogVO.setBlogCategoryIcon(blogCategoryMap.get(blog.getBlogCategoryId()));
                }else{
                    blogVO.setBlogCategoryId(0);
                    blogVO.setBlogCategoryName("默认分类");
                    blogVO.setBlogCategoryIcon("/admin/dist/img/category/00.png");
                }
                blogVOList.add(blogVO);
            }
        }
        return blogVOList;
    }

}