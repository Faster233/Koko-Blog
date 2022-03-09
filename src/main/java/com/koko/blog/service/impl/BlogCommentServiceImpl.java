package com.koko.blog.service.impl;

import com.koko.blog.utils.PageQueryUtil;
import com.koko.blog.utils.PageUtils;
import com.koko.blog.utils.Query;
import com.koko.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.koko.blog.dao.BlogCommentDao;
import com.koko.blog.entity.BlogCommentEntity;
import com.koko.blog.service.BlogCommentService;
import org.springframework.util.CollectionUtils;


@Service("blogCommentService")
public class BlogCommentServiceImpl extends ServiceImpl<BlogCommentDao, BlogCommentEntity> implements BlogCommentService {

    @Autowired
    private BlogCommentDao blogCommentDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BlogCommentEntity> page = this.page(
                new Query<BlogCommentEntity>().getPage(params),
                new QueryWrapper<BlogCommentEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public int getTotalComments() {
        return blogCommentDao.getTotalComments();
    }

    @Override
    public PageUtils queryCommentPage(PageQueryUtil queryUtil) {
        List<BlogCommentEntity> blogComments=blogCommentDao.getCommentList(queryUtil);
        int totalComments = blogCommentDao.getTotalComments();
        PageUtils pageUtils = new PageUtils(blogComments, totalComments, queryUtil.getLimit(), queryUtil.getPage());
        return pageUtils;
    }

    @Override
    public boolean checkDone(Integer[] ids) {
        return blogCommentDao.checkDone(ids)>0;
    }

    @Override
    public boolean reply(Integer commentId, String replyBody) {
        BlogCommentEntity blogComment = blogCommentDao.selectById(commentId);
        if (blogComment!=null && blogComment.getCommentStatus().intValue()==1){
            blogComment.setReplyBody(replyBody);
            blogComment.setReplyCreateTime(new Date());
           return blogCommentDao.reply(blogComment)>0;
        }
        return false;
    }

    @Override
    public boolean deleteBatch(Integer[] ids) {
        return blogCommentDao.deleteBatch(ids)>0;
    }

    @Override
    public PageUtils getCommentPageByBlogIdAndPageNum(Long blogId, Integer commentPage) {
        if (commentPage<1){
            return null;
        }
        Map param = new HashMap<>();
        param.put("limit",8);
        param.put("page",commentPage);
        param.put("blogId",blogId);
        param.put("commentStatus",1);
        PageQueryUtil queryUtil = new PageQueryUtil(param);
        List<BlogCommentEntity> commentEntities=blogCommentDao.queryCommentPage(queryUtil);
        if (!CollectionUtils.isEmpty(commentEntities)){
            int totalBlogComments = blogCommentDao.getTotalBlogComments(queryUtil);
            PageUtils pageUtils = new PageUtils(commentEntities, totalBlogComments,
                    queryUtil.getLimit(), queryUtil.getPage());
            return pageUtils;
        }
        return null;
    }

    @Override
    public boolean saveBlogComment(BlogCommentEntity blogComment) {
        return blogCommentDao.insertBlogComment(blogComment)>0;
    }

}